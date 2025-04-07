package ie.tus.nm4.sensor_project.location;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

//Data Access Object for a college database
public class LocationDAO {
	
	public static Connection con;
	public static Statement stmt;

	public LocationDAO()
	{
		//Constructor does the connection to the database
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/springdb?useTimezone=true&serverTimezone=UTC";
			con = DriverManager.getConnection(url, "oliver", "J0n4th4n");
			stmt = con.createStatement();
		}
		catch(Exception e)
		{
			System.out.println("Error: Failed to connect to database\n" + e.getMessage());
		}
	}

	public void addLocation(int customerId, String address, String locationType) {
		
		// set up string for prepared statement
		// need 'null' for the auto increment
		String str = "INSERT INTO reading (customer_id, address, location_type) VALUES(?,?,?)";  

		try {
			PreparedStatement pstmt = con.prepareStatement(str);
			pstmt.setInt(1, customerId);
			pstmt.setString(2, address);
			pstmt.setString(3, locationType);
			
			System.out.println("\nAttempting to insert new row...");
			int count = pstmt.executeUpdate();
			System.out.println(count + " rows added successfully.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}
