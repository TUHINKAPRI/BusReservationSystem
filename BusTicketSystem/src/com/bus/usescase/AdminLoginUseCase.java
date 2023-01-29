package com.bus.usescase;

import java.util.Scanner;

import com.bus.customColor.ConsoleColor;
import com.bus.dao.AdminDao;
import com.bus.dao.AdminImpl;
import com.bus.exception.AdminException;

public class AdminLoginUseCase {

	public static boolean AdminLogin() {
		
	      Scanner sc = new Scanner(System.in);
			
			System.out.println("Enter username");
			String username = sc.next();
			
			System.out.println("Enter password");
			String password = sc.next();
			
			AdminDao dao = new AdminImpl();
			
			try {
			boolean flag = dao.adminLogin(username, password);
			if(flag) {
				System.out.println(ConsoleColor.GREEN+"Login Successfuly..."+ConsoleColor.RESET);
				return true;
			}
			
			} catch (AdminException e) {
				// TODO Auto-generated catch block
				System.out.println(ConsoleColor.RED_BACKGROUND + e.getMessage() + ConsoleColor.RESET);
			}
			return false;
			

		}
}
