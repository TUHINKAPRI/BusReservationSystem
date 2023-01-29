package com.bus.usescase;

import com.bus.dao.CustomerDao;
import com.bus.dao.CustomerImpl;
import com.bus.models.Customer;

public class ViewTicketUseCase {

public static void viewTicket(Customer customer) {
		
		CustomerDao dao = new CustomerImpl();
		
		dao.viewTicketById(customer.getcId());
	}
}
