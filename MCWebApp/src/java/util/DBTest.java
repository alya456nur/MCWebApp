package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBTest {

    private static final String URL = "jdbc:derby://localhost:1527/MCDB";
    private static final String USER = "app";
    private static final String PASSWORD = "app";

    public static void main(String[] args) {

        try {
            // Load Derby Client Driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // Connect to DB
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database connected successfully!");
            System.out.println("Connection object: " + conn);

            // Simple test query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1 FROM SYSIBM.SYSDUMMY1");

            if (rs.next()) {
                System.out.println("✅ Test query executed successfully!");
            }

            // Close resources
            rs.close();
            stmt.close();
            conn.close();

            System.out.println("✅ Connection closed cleanly.");

        } catch (Exception e) {
            System.err.println("❌ Database connection failed!");
            e.printStackTrace();
        }
    }
}
