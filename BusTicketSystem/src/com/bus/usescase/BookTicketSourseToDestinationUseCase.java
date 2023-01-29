package com.bus.usescase;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.bus.customColor.ConsoleColor;
import com.bus.dao.CustomerDao;
import com.bus.dao.CustomerImpl;
import com.bus.exception.AdminException;
import com.bus.models.Customer;

public class BookTicketSourseToDestinationUseCase {

public static void bookTicketSourseToDestination(Customer customer) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter Bus Name: ");
		String bName = sc.next();
		
		CustomerDao dao = new CustomerImpl();
		try {
			System.out.println("Enter no. of Tickets to Book");
			int noseat = sc.nextInt();
			
			int cId = customer.getcId();
			String message = dao.bookTicketSourseToDestination(bName, cId, noseat);
			
			if (message.equals("Ticket Booked Successfully")) {
				System.out.println(ConsoleColor.GREEN + message + ConsoleColor.RESET);
			}
			else {
				System.out.println(message);
			}
			
		} catch (AdminException e) {
			System.out.println(e.getMessage());
		}
		catch (InputMismatchException e) {
			System.out.println(ConsoleColor.RED+"Invalid input");
		}
		
	}
}
