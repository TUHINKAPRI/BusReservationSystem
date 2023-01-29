package com.bus.dao;

import com.bus.exception.AdminException;
import com.bus.exception.BusException;
import com.bus.models.BusDetails;

public interface AdminDao {
	 public final String username = "Tuhin";
		
		public final String password = "tuhin1234";

		public boolean adminLogin(String username, String password) throws AdminException;
		
	    public String addBusDetails(BusDetails bus);
	    
	    public String confirmationSeatsStatus(int cId) throws BusException;
		
		public void viewAllTickets() throws BusException;

}
