package com.bus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.bus.customColor.ConsoleColor;
import com.bus.exception.AdminException;
import com.bus.exception.CustomerException;
import com.bus.models.Customer;
import com.bus.utility.DBUtility;

public class CustomerImpl implements CustomerDao {

	@Override
	public String customerRegister(Customer customer) {
      String message = "Customer not Register";
		
		try(Connection conn = DBUtility.provideConnection()){
			
			PreparedStatement ps =  conn.prepareStatement("insert into customer(firstName, lastName, username, password, address, mobile) values (?,?,?,?,?,?)");
			
			
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setString(3, customer.getUsername());
			ps.setString(4, customer.getPassword());
			ps.setString(5, customer.getAddress());
			ps.setString(6, customer.getMobile());
			
			int x = ps.executeUpdate();
			
			if (x > 0) message = "Customer Register Successfull";
			
		}
		catch (SQLException e) {
			message = e.getMessage();
		}
		
		return message;
	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	@Override
	public Customer customerLogin(String username, String password) throws CustomerException {
       Customer customer = null;
		
		try (Connection conn = DBUtility.provideConnection()){
			
			PreparedStatement ps = conn.prepareStatement("select * from customer where username = ? and password = ?");
			
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet rs =  ps.executeQuery();
			
			if (rs.next()) {
				int cId = rs.getInt("cId");
				String firstName = rs.getString("firstName");		
				String lastName = rs.getString("lastName");	
				String usernamee =  rs.getString("username");
				String passwordd = rs.getString("password");	
				String address = rs.getString("address");			
				String mobile = rs.getString("mobile");
				
				customer = new Customer(cId, firstName, lastName,usernamee, passwordd, address, mobile);
				
			}
			else {
				throw new CustomerException("Invalid username or password");
				
			}
			
		}
		catch (SQLException e) {
			throw new CustomerException(e.getMessage());
		}
		
		return customer;
	}
//-------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public String bookTicketSourseToDestination(String bName, int cId, int noseat) throws AdminException {
       String message = "Ticket Booking fail";
		
		try (Connection conn = DBUtility.provideConnection()){
			
			PreparedStatement ps = conn.prepareStatement("select * from busInfo where bName = ?");
			ps.setString(1, bName);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				
				int busNo = rs.getInt("busNo");
				int totalSeats = rs.getInt("totalSeats");
				int availSeats = rs.getInt("availSeats");
				java.sql.Date departure = rs.getDate("departure");
				int ticketCharge = rs.getInt("TicketCharge");
				
				PreparedStatement ps1 = conn.prepareStatement("select datediff(?,current_date()) as date");
				ps1.setDate(1,departure);
				
				ResultSet rs1 = ps1.executeQuery();
				int days = 0;
				if (rs1.next()) {
					days = rs1.getInt("date");
				}
				
				if (days <= 0) {
					throw new AdminException("Booking is not available for this date");
				}
				else if (availSeats >= noseat) {
					int seatFrom = totalSeats - availSeats + 1;
					int seatTo = seatFrom + noseat -1;
					ticketCharge = ticketCharge * noseat;
					
					PreparedStatement ps2 = conn.prepareStatement("insert into TicketBooking(cId, busNo, seatFrom, seatTo) values (?, ?, ?, ?)");
					ps2.setInt(1, cId);
					ps2.setInt(2, busNo);
					ps2.setInt(3, seatFrom);
					ps2.setInt(4, seatTo);
					
					int x = ps2.executeUpdate();

					if (x > 0) {
						
						PreparedStatement ps3 = conn.prepareStatement("update busInfo set availseats = ? where busNo = ?");
						availSeats = availSeats - noseat;
						ps3.setInt(1, availSeats);
						ps3.setInt(2, busNo);
						int y = ps3.executeUpdate();
						
						if (y <= 0) throw new AdminException("Available Seat is not updated");
						
						
						System.out.println(ConsoleColor.LIGHT_BLUE_BACKGROUND +"--------------------------------------------" + "\n"
										                           + "Customer Id is : " + cId + "\n"
																   + "Bus No is : " + busNo + "\n"
																   + "Seat No is from : " + seatFrom + " to " + seatTo + "\n"
																   + "Bus TicketCharge is : " + ticketCharge+ "\n"
																   + "Booking yet to be confirm by Adminstrator" + "\n" 
																   + "---------------------------------------------"+ ConsoleColor.RESET);
						
						 message = "Ticket Booked Successfully";
					}
				
				}
	
			}
			else {
				throw new AdminException(bName +"--This Bus is not available");
			}
			
		}
		catch (SQLException e) {
			throw new AdminException(e.getMessage());
		}
		
		return message;
	}
//----------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public String cancelTicket(String bName, int cId) throws AdminException {
      String message = "Ticket cancellation failed";
		
		try (Connection conn = DBUtility.provideConnection()){
				
				PreparedStatement ps = conn.prepareStatement("select * from busInfo where bName = ?");
				ps.setString(1, bName);
				
				ResultSet rs = ps.executeQuery();
				
				if (rs.next()) {
					
					int busNo = rs.getInt("busNo");
					int availSeats = rs.getInt("availSeats");
					
					PreparedStatement ps1 = conn.prepareStatement("select * from TicketBooking where busNo = ? and cId = ?");
					ps1.setInt(1, busNo);
					ps1.setInt(2, cId);
					
					ResultSet rs1 = ps1.executeQuery();
					boolean flag = false;
					int count = 0;
					
					while (rs1.next()) {
						flag = true;
						int seatFrom = rs1.getInt("seatFrom");
						int seatTo = rs1.getInt("seatTo");
						count += seatTo - seatFrom + 1;
					}
					
				    if (flag) {
						
						PreparedStatement ps2 = conn.prepareStatement("delete from TicketBooking where busNo = ? and cId = ?");
						ps2.setInt(1, busNo);
						ps2.setInt(2, cId);
						
						int x = ps2.executeUpdate();
						if (x > 0) {
							
							PreparedStatement ps3 = conn.prepareStatement("update busInfo set availseats = ? where busNo = ?");
							availSeats = availSeats + count;
							ps3.setInt(1, availSeats);
							ps3.setInt(2, busNo);
							int y = ps3.executeUpdate();
							
							if (y <= 0) throw new AdminException("Available Seat is not updated");
							
							 message = "Ticket cancelled Successfully";
						}
					
					}
				    else message = "No booking found";
		
				}
				else {
					throw new AdminException("Bus with " + bName + " is not available");
				}
				
			}
			catch (SQLException e) {
				throw new AdminException(e.getMessage());
			}
		
		return message;
	}

	@Override
	public void viewTicketById(int cId) {
		 boolean flag = false;
			
			try(Connection conn = DBUtility.provideConnection()){
				PreparedStatement ps1 = conn.prepareStatement("select * from TicketBooking where cId = ?");
				ps1.setInt(1, cId);
				
				ResultSet rs1 = ps1.executeQuery();
				
				while (rs1.next()) {
					System.out.println(ConsoleColor.YELLOW_BACKGROUND +"----------------------------------------------------");
					System.out.println(ConsoleColor.YELLOW_BACKGROUND +"Bus Id : " + rs1.getInt("bId"));
					System.out.println(ConsoleColor.YELLOW_BACKGROUND +"Bus No : " + rs1.getInt("busNo"));
					System.out.println(ConsoleColor.YELLOW_BACKGROUND + "Total tickets : " + (rs1.getInt("seatTo") - rs1.getInt("seatFrom") + 1));
					if (rs1.getInt("status") == 1) System.out.println(ConsoleColor.GREEN_BACKGROUND+ "Status : Booked"+ ConsoleColor.RESET);
					else System.out.println(ConsoleColor.YELLOW_BOLD_BRIGHT + "Status : Pending" +ConsoleColor.RESET);
					
					System.out.println("----------------------------------------------------");
					flag = true;
				
				}
				
				if (flag == false) System.out.println(ConsoleColor.RED_BOLD+ "No tickets found"+ConsoleColor.RESET);
			}
			catch (SQLException s){
				System.out.println(s.getMessage());
			}
			
		
	}

}
