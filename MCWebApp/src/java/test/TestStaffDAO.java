package test;

import dao.StaffDAO;
import model.Staff;

import java.util.List;

public class TestStaffDAO {

    public static void main(String[] args) {

        StaffDAO dao = new StaffDAO();

        System.out.println("Fetching all staff from database...");

        List<Staff> staffList = dao.getAllStaff();

        System.out.println("Staff loaded: " + staffList.size());
        System.out.println("====================================");

        for (Staff s : staffList) {
            System.out.println("Staff ID   : " + s.getStaffId());
            System.out.println("Name       : " + s.getStaffName());
            System.out.println("Email      : " + s.getStaffEmail());
            System.out.println("Phone      : " + s.getStaffPhone());
            System.out.println("Role       : " + s.getUserRole());
            System.out.println("Password   : " + s.getStaffPass());
            System.out.println("------------------------------------");
        }

        System.out.println("====================================");
        System.out.println("Done fetching staff.");
    }
}
