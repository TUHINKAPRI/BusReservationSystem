package com.bus.usescase;

import java.util.Scanner;

import com.bus.customColor.ConsoleColor;
import com.bus.dao.AdminDao;
import com.bus.dao.AdminImpl;
import com.bus.exception.BusException;

public class ConfirmationSeatsStatusUseCase {
	
public static void updateSeatStatus() {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter customer Id :");
		int cusId = sc.nextInt();
		
		AdminDao dao = new AdminImpl();
		
		try {
			String result = dao.confirmationSeatsStatus(cusId);
			boolean flag = true;
			
			for (int i = 0; i < result.length(); i++) {
				if (result.charAt(i) == 'n') flag = false;
			}
		
			if (flag) System.out.println(ConsoleColor.GREEN + result+ ConsoleColor.RESET);
			else System.out.println(result);
		} catch (BusException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		
	}
}


