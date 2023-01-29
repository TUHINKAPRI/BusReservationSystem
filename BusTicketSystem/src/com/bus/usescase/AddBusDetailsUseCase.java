package com.bus.usescase;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.bus.customColor.ConsoleColor;
import com.bus.dao.AdminDao;
import com.bus.dao.AdminImpl;
import com.bus.models.BusDetails;

public class AddBusDetailsUseCase {
	
	public static void AddBusInfo() {

		Scanner sc = new Scanner(System.in);
		
		try {
			
			System.out.println(ConsoleColor.PURPLE+"Enter Bus Number:");

			int busNo = sc.nextInt();
			
			System.out.println(ConsoleColor.PURPLE+"Enter Bus Name:");
			String bName = sc.next();
			
			System.out.println(ConsoleColor.PURPLE+"Enter Route from");
			String routeFrom = sc.next();
			
			System.out.println(ConsoleColor.PURPLE+"Enter Routo To");
			String routeTo = sc.next();
			
			System.out.println(ConsoleColor.PURPLE+"Enter Bus Type - AC / NonAC");
			String bType = sc.next();
			
			sc.nextLine();
			System.out.println(ConsoleColor.PURPLE+"Enter Departure date and time in format (YYYY-MM-DD HH:MI:SS)");
			String departure = sc.nextLine();
			
			System.out.println(ConsoleColor.PURPLE+"Enter Arrival date and time in format (YYYY-MM-DD HH:MI:SS)");
			String arrival = sc.nextLine();
			
			System.out.println(ConsoleColor.PURPLE+"Enter Total Seats :");
			int totalSeats = sc.nextInt();
			
			System.out.println(ConsoleColor.PURPLE+"Enter Available Seats :");
			int availSeats = sc.nextInt();
			
		    System.out.println(ConsoleColor.PURPLE+"Enter Ticket Charge: ");
			int ticketCharge = sc.nextInt();
			
			BusDetails bus = new BusDetails(busNo, bName, routeFrom, routeTo, bType, departure, arrival, totalSeats, availSeats, ticketCharge);
			
			AdminDao dao = new AdminImpl();
			
			String result = dao.addBusDetails(bus);
			
			
				System.out.println(ConsoleColor.PURPLE+result+ConsoleColor.RESET);
		}
		catch (InputMismatchException e) {
			System.out.println(ConsoleColor.RED+"Invalid Input"+ConsoleColor.RESET);
		}
	}

}
