package com.admin.service;

import java.util.List;

import com.admin.entity.AdminDetails;
import com.admin.entity.CustomerOverview;
import com.admin.entity.HistoryOfBookings;
import com.admin.entity.WasherOverview;

public interface AdminService {
		public AdminDetails addAdmin(AdminDetails adminDetails);
		public AdminDetails updateAdmin(String username, AdminDetails adminDetails);
		public void deleteAdmin(String userName);
		public List<String> viewAdmins();
		
		public List<CustomerOverview> viewCustomerOverview();
		public List<WasherOverview> viewWasherOverview();
		
		public List<HistoryOfBookings> viewBookingHistory();
}
