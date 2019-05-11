
/* Darsh Patel - Final Project - Spring 2019 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dao {

	// Code database URL
	static final String DB_URL = "jdbc:mysql://www.papademas.net:3307/tickets?autoReconnect=true&useSSL=false";
	// Database credentials
	static final String USER = "fp411", PASS = "411";

	public Connection connect() throws SQLException {

		return DriverManager.getConnection(DB_URL, USER, PASS);
	}

	// constructor
	Statement statement = null;

	// CRUD implementation
	public int insertRecords(String ticketName, Object ticketDesc) {
		int id = 0;
		try {
			statement = connect().createStatement();
			statement.executeUpdate("INSERT INTO dpatel_tickets" + "(ticket_name, ticket_desc) values(" + " '"
					+ ticketName + "','" + ticketDesc + "')", Statement.RETURN_GENERATED_KEYS);

			// retrieve ticket id number newly auto generated upon record insertion
			ResultSet resultSet = null;
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				// retrieve first field in table
				id = resultSet.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;

	}

	public ResultSet readRecords() {

		ResultSet results = null;
		try {

			statement = connect().createStatement();
			results = statement.executeQuery("SELECT * FROM dpatel_tickets");
			connect().close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return results;
	}

	// updating records

	public int updateRecords(String ticketId, String ticketUpdateDesc) {

		int id = 0;
		String query = "UPDATE dpatel_tickets SET ticket_desc = ? WHERE tid = ?";
		try (PreparedStatement stmt = connect().prepareStatement(query)) {
			stmt.setString(1, ticketUpdateDesc);
			stmt.setString(2, ticketId);

			id = stmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return id;

	}

	// deleting records

	public int deleteRecords(String ticketDelete) {
		int id = 0;
		String query = "DELETE FROM dpatel_tickets WHERE tid = ?";
		try (PreparedStatement stmt = connect().prepareStatement(query)) {
			stmt.setString(1, ticketDelete);

			id = stmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return id;
	}

	// updating status and end time

	public int updateClosedTicket(String ticketId, String ticketClosedDate) {

		int id = 0;
		String query = "UPDATE dpatel_tickets SET end_date = ?, status = 'Closed' WHERE tid = ?";
		try (PreparedStatement stmt = connect().prepareStatement(query)) {
			stmt.setString(1, ticketClosedDate);
			stmt.setString(2, ticketId);

			id = stmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return id;

	}

	// update new password

	public int updatePassword(String username, String confirmPass) {

		int id = 0;
		String query = "UPDATE dpatel_users SET upass = ? WHERE uname = ?";
		try (PreparedStatement stmt = connect().prepareStatement(query)) {
			stmt.setString(1, confirmPass);
			stmt.setString(2, username);

			id = stmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return id;

	}

}