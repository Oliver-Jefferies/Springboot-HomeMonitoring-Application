package ie.tus.nm4.sensor_project.mqtt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

//Data Access Object for a college database
public class ReadingDAO {
	
	public static Connection con;
	public static Statement stmt;

	public ReadingDAO()
	{
		//Constructor does the connection to the database
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/springdb?useTimezone=true&serverTimezone=UTC";
			con = DriverManager.getConnection(url, "root", "root");
			stmt = con.createStatement();
		}
		catch(Exception e)
		{
			System.out.println("Error: Failed to connect to database\n" + e.getMessage());
		}
	}

	public void addReading(int roomNum, float tempF, float humF, float pressF) {
		
		// set up string for prepared statement
		// need 'null' for the auto increment
		String str = "INSERT INTO reading (room_id, temp, humidity, pressure) VALUES(?,?,?,?)";  

		try {
			PreparedStatement pstmt = con.prepareStatement(str);
			pstmt.setInt(1, roomNum);
			pstmt.setFloat(2, tempF);
			pstmt.setFloat(3, humF);
			pstmt.setFloat(4, pressF);
			
			System.out.println("\nAttempting to insert new row...");
			int count = pstmt.executeUpdate();
			System.out.println(count + " rows added successfully.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}
