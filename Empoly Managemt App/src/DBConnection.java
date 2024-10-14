import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/employees";  // Replace 'employees' with your DB name
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "Umang1234"; // Replace with your password
    // SQL query to insert employee data
    private static final String INSERT_EMPLOYEE_SQL = "INSERT INTO employee (ID, Name, Email, Phone_no, Address, WorkTitle, Salary, WorkingStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    // SQL query to fetch all employees
    private static final String SELECT_ALL_EMPLOYEES_SQL = "SELECT * FROM employee";
    // SQL query to update employee data
    private static final String UPDATE_EMPLOYEE_SQL = "UPDATE employee SET Name=?, Email=?, Phone_no=?, Address=?, WorkTitle=?, Salary=?, WorkingStatus=? WHERE ID=?";
    // SQL query to delete employee data
    private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM employee WHERE ID=?";

    // Get a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    // Method to add employee to the database
    public static boolean addEmployee(String ID, String Name, String Email, String Phone_no, String Address, String WorkTitle, String Salary, String WorkingStatus) {
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_EMPLOYEE_SQL)) {
            // Set the values to insert
            preparedStatement.setString(1, ID);
            preparedStatement.setString(2, Name);
            preparedStatement.setString(3, Email);
            preparedStatement.setString(4, Phone_no);
            preparedStatement.setString(5, Address);
            preparedStatement.setString(6, WorkTitle);
            preparedStatement.setString(7, Salary);
            preparedStatement.setString(8, WorkingStatus);

            // Execute the query and check if one row was inserted
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // retrieve all employees from the database
    public static List<Object[]> getAllEmployees() {
        List<Object[]> employees = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_EMPLOYEES_SQL);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                String ID = rs.getString("ID");
                String Name = rs.getString("Name");
                String Email = rs.getString("Email");
                String Phone_no = rs.getString("Phone_no");
                String Address = rs.getString("Address");
                String WorkTitle = rs.getString("WorkTitle");
                String Salary = rs.getString("Salary");
                String WorkingStatus = rs.getString("WorkingStatus");
                employees.add(new Object[]{Name, ID, Email, Phone_no, Address, WorkTitle, Salary, WorkingStatus});
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
    // Udate employee form the database 
    public static boolean updateEmployee(String ID, String Name, String Email, String Phone_no, String Address, String WorkTitle, String Salary, String WorkingStatus) {
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_EMPLOYEE_SQL)) {
            preparedStatement.setString(1, Name);
            preparedStatement.setString(2, Email);
            preparedStatement.setString(3, Phone_no);
            preparedStatement.setString(4, Address);
            preparedStatement.setString(5, WorkTitle);
            preparedStatement.setString(6, Salary);
            preparedStatement.setString(7, WorkingStatus);
            preparedStatement.setString(8, ID);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
            }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
        // delete employee from the database
     public static boolean deleteEmployee(String ID){
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_EMPLOYEE_SQL)) {
            preparedStatement.setString(1, ID);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
            }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
