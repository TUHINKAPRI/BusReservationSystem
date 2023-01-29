package com.bus.usescase;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.bus.dao.CustomerDao;
import com.bus.dao.CustomerImpl;
import com.bus.exception.CustomerException;
import com.bus.models.Customer;

public class CustomerLoginUseCase {
	
public static Customer CustomerLogin() {
		
		Customer customer = null;
		
		try {
			
			Scanner sc = new Scanner (System.in);
			
			System.out.println("Enter Username :");
			String username = sc.next();
			
			System.out.println("Enter Password :");
			String password = sc.next();
			
			CustomerDao dao = new CustomerImpl();
			
			try {
				customer = dao.customerLogin(username, password);
				
				System.out.println("Welcome " + customer.getFirstName() + " " + customer.getLastName());
			} catch (CustomerException e) {
				
				System.out.println(e.getMessage());
			}
		}
		catch (InputMismatchException e) {
			System.out.println(e.getMessage());
		}
		
		return customer;

	}

}
