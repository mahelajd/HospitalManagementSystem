package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Patient {
	
public Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
			
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/healthservice?useTimezone=true&serverTimezone=UTC","root","");  
			
			
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	public String insertPatient(String name, String address, String phone, String email) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = " insert into patient(`pid`,`name`,`address`,`phone`,`email`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, address);
			preparedStmt.setString(4, phone);
			preparedStmt.setString(5, email);

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}
		return output;

	}

	public String readPatient() {

		String output = "";
		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// preapare the html table to be displayed
			output = "<table class=\"table\" border=\"1\">" + "<tr><th scope=\"col\">Name</th>"
					+ "<th scope=\"col\">address</th>" + "<th scope=\"col\">phone</th>"
					+ "<th scope=\"col\">email</th>" + "<th scope=\"col\">Update</th>"
					+ "<th scope=\"col\">Remove</th></tr>";

			String query = "Select * from patient";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {

				String pid = Integer.toString(rs.getInt("pid"));
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String email = rs.getString("email");

				// Add into the html table

				output += "<tr><td>" + name + "</td>";
				output += "<td>" + address + "</td>";
				output += "<td>" + phone + "</td>";
				output += "<td>" + email + "</td>";

				// buttons

				output += "<td><input name=\"btnUpdate\" "
						+ " type=\"button\" value=\"Update\" class=\"btn btn-danger\"></td>"
						+ "<td><form method=\"post\">" + "<input name=\"btnRemove\" "
						+ " type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
						+ "<input name=\"pid\" type=\"hidden\" " + " value=\"" + pid+ "\">"
						+ "</form></td></tr>";

			}

			con.close();

			// complete the html table
			output += "</table>";

		} catch (Exception e)

		{
			output = "Error while reading the users.";
			System.err.println(e.getMessage());

		}

		return output;

	}


	public String updatePatient(String pid, String name, String address, String phone, String email) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE patient SET name=?,address=?,phone=?,email=? WHERE pid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2,address);
			preparedStmt.setString(3, phone);
			preparedStmt.setString(4, email);
			preparedStmt.setInt(5,Integer.parseInt(pid));
			
			//execute the statement
			
			preparedStmt.execute();
			con.close();
			
			output = "Updated successfully";
		}
		catch (Exception e)
		{
			output = "Error while updating the patient.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePatient(String pid) {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from patient where pid=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(pid));
			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the user";
			System.err.println(e.getMessage());
		}

		return output;

	}

}

