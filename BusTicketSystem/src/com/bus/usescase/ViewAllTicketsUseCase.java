package com.bus.usescase;

import com.bus.dao.AdminDao;
import com.bus.dao.AdminImpl;
import com.bus.exception.BusException;

public class ViewAllTicketsUseCase {
	
public static void viewAllTicket() {
		
		AdminDao dao = new AdminImpl();
		try {
			dao.viewAllTickets();
		} catch (BusException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

}
