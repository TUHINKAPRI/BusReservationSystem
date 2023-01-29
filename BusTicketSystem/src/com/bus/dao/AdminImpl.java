package com.bus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bus.customColor.ConsoleColor;
import com.bus.exception.AdminException;
import com.bus.exception.BusException;
import com.bus.models.BusDetails;
import com.bus.utility.DBUtility;

public class AdminImpl implements AdminDao {

	@Override
	public boolean adminLogin(String username, String password) throws AdminException {
		boolean flag = false;
		if(username.equals(AdminDao.username) && password.equals(AdminDao.password)) {
			flag = true;
			
		}
		else 
			throw new AdminException("Invalid username or password");
		
		return flag;
	}

	@Override
	public String addBusDetails(BusDetails bus) {
String message="Bus Details is not Added";
		
		try (Connection conn=DBUtility.provideConnection()){
			
		PreparedStatement ps=conn.prepareStatement("insert into busInfo values (?,?,?,?,?,?,?,?,?,?)");
		
		ps.setInt(1, bus.getBusNo());
		ps.setString(2, bus.getbName());
		ps.setString(3, bus.getRouteFrom());
		ps.setString(4, bus.getRouteTo());
		ps.setString(5, bus.getbType());
		ps.setString(6, bus.getArrival());
		ps.setString(7, bus.getDeparture());
		ps.setInt(8,bus.getTotalSeats());
		ps.setInt(9, bus.getAvailSeats());
		ps.setInt(10, bus.getTicketCharge());
		
		int x = ps.executeUpdate();
		
		if (x > 0)  message = "Bus Details added Successfully";
		
		} catch (SQLException e) {
			message=e.getMessage();
		}
		
		return message;
	}

	@Override
	public String confirmationSeatsStatus(int cId) throws BusException {
		
	       String message = "Seat not  confirm for customer Id : " + cId;
			
			try(Connection conn = DBUtility.provideConnection()){
				PreparedStatement ps = conn.prepareStatement("update TicketBooking set status = true where cId = ?");
				ps.setInt(1, cId);
				
				int x = ps.executeUpdate();
				if (x > 0) message = "Seat successfully Confirm for customer Id : " + cId;
				
			}
			catch (SQLException e) {
				throw new BusException(message);
				//message = e.getMessage();
			}
			
			return message;
	}

	@Override
	public void viewAllTickets() throws BusException {
		
		boolean flag = false;
		
		try(Connection conn = DBUtility.provideConnection()){
			PreparedStatement ps1 = conn.prepareStatement("select * from TicketBooking");
			
			ResultSet rs1 = ps1.executeQuery();
			
			while (rs1.next()) {
				flag = true;
				
				System.out.println(ConsoleColor.YELLOW_BACKGROUND +"----------------------------------------------------");
				System.out.println(ConsoleColor.YELLOW_BACKGROUND +"Bus Id : " + rs1.getInt("bId"));
				System.out.println(ConsoleColor.YELLOW_BACKGROUND +"Bus No : " + rs1.getInt("busNo"));
				System.out.println(ConsoleColor.YELLOW_BACKGROUND + "Total tickets : " + (rs1.getInt("seatTo") - rs1.getInt("seatFrom") + 1));
				if (rs1.getInt("status") == 1) System.out.println(ConsoleColor.GREEN_BACKGROUND+ "Status : Booked"+ ConsoleColor.RESET);
				else System.out.println(ConsoleColor.YELLOW_BOLD_BRIGHT + "Status : Pending" +ConsoleColor.RESET);
				
				System.out.println("----------------------------------------------------");
			}
			
			if (flag == false) throw new BusException(ConsoleColor.RED_BACKGROUND+"No ticket Exist" + ConsoleColor.RESET);
		}
		catch (SQLException s){
			System.out.println(s.getMessage());
		}
		
	}

	
	

}
