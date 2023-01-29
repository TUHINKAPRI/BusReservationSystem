package com.bus.usescase;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.bus.customColor.ConsoleColor;
import com.bus.dao.CustomerDao;
import com.bus.dao.CustomerImpl;
import com.bus.models.Customer;

public class CustomerRegisterationUseCase {
	
public static boolean customerRegistration() {
		
		boolean flag = false;
		
		try {
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Enter First Name:");
			String firstName = sc.next();
			
			System.out.println("Enter Last Name:");
			String lastName = sc.next();
			
			System.out.println("Enter Username:");
			String username = sc.next();
			
			System.out.println("Enter Password :");
			String password = sc.next();
			
			sc.nextLine();
			System.out.println("Enter Address :");
			String address = sc.nextLine();
			
			System.out.println("Enter Mobile :" );
			String mobile = sc.next();
			
			CustomerDao dao = new CustomerImpl();
			Customer customer = new Customer();
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
			customer.setUsername(username);
			customer.setPassword(password);
			customer.setAddress(address);
			customer.setMobile(mobile);
			
			String result = dao.customerRegister(customer);
			
			
			if (result == "Customer Register Successfull") {
				flag = true;
				System.out.println(ConsoleColor.GREEN_BACKGROUND + result +ConsoleColor.RESET);
			}
			else {
				System.out.println(result);
			}
			
		}
		catch (InputMismatchException e) {
			System.out.println(ConsoleColor.RED + "Invalid Input" + ConsoleColor.RESET);
		}
		
		return flag;
	}


}
