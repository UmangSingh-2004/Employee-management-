import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Employee {
    public static void main(String[] args) {
        // Create a frame
        JFrame f = new JFrame();

        // Set the frame properties
        ImageIcon EmployeeManagementIcons = new ImageIcon("src/assets/Employee Management.PNG");
        String[] columnNames = {"Name", "ID", "Email Address", "Phone No.", "Address", "Work Title", "Salary", "Working Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        f.setSize(600, 650);
        f.setLocationRelativeTo(null);
        f.setLayout(new BorderLayout());
        f.setResizable(false);
        f.setIconImage(EmployeeManagementIcons.getImage());
        f.setTitle("Employee Management System");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Input fields
        JLabel name = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(name, gbc);

        JTextField nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);

        JLabel email = new JLabel("E-mail:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(email, gbc);

        JTextField emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(emailField, gbc);

        JLabel phoneNo = new JLabel("Phone No:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(phoneNo, gbc);

        JTextField phoneNoField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(phoneNoField, gbc);

        JLabel address = new JLabel("Address:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(address, gbc);

        JTextField addressField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(addressField, gbc);

        JLabel workTitle = new JLabel("Work Title:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(workTitle, gbc);

        JTextField workTitleField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(workTitleField, gbc);

        JLabel salary = new JLabel("Salary:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(salary, gbc);

        JTextField salaryField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(salaryField, gbc);

        JLabel workingStatus = new JLabel("Working Status:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(workingStatus, gbc);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JRadioButton yes = new JRadioButton("Yes");
        JRadioButton no = new JRadioButton("No");
        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(yes);
        statusGroup.add(no);
        statusPanel.add(yes);
        statusPanel.add(no);

        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(statusPanel, gbc);
        // Add Employee Button
        JButton addEmploy = new JButton("Add Employee");
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(addEmploy, gbc);
        addEmploy.addActionListener(e -> {
            String Name = nameField.getText();
            UUID uuid = UUID.randomUUID();
            String ID = uuid.toString();
            String Email = emailField.getText();
            String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
            Pattern pattern = Pattern.compile(emailPattern);
            Matcher matcher = pattern.matcher(Email);
            if (!matcher.matches()) {
                JOptionPane.showMessageDialog(f, "Please enter a valid email address.", "Invalid Email", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String Phone_no = phoneNoField.getText();
            String Address = addressField.getText();
            String WorkTitle = workTitleField.getText();
            String Salary = salaryField.getText();
            String WorkingStatus = yes.isSelected() ? "Yes" : "No";

            boolean isAdded = DBConnection.addEmployee(ID, Name, Email, Phone_no, Address, WorkTitle, Salary, WorkingStatus);

            if (isAdded) {
                JOptionPane.showMessageDialog(f, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                tableModel.addRow(new Object[]{Name, ID, Email, Phone_no, Address, WorkTitle, Salary, WorkingStatus});
            } else {
                JOptionPane.showMessageDialog(f, "Error adding employee to the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        // See Employees Button
        JButton seeEmploy = new JButton("See Employees");
        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(seeEmploy, gbc);
        seeEmploy.addActionListener(e -> {
            tableModel.setRowCount(0);
            List<Object[]> employees = DBConnection.getAllEmployees();
            for (Object[] employee : employees) {
                tableModel.addRow(employee);
            }
        });
        // Update Employee Button
        JButton updateEmploy = new JButton("Update Employee");
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(updateEmploy, gbc);
        updateEmploy.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(f, "Please select an employee to update.", "No Employee Selected", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String ID = tableModel.getValueAt(selectedRow, 1).toString(); // Get selected employee ID
            String Name = nameField.getText();
            String Email = emailField.getText();
            String Phone_no = phoneNoField.getText();
            String Address = addressField.getText();
            String WorkTitle = workTitleField.getText();
            String Salary = salaryField.getText();
            String WorkingStatus = yes.isSelected() ? "Yes" : "No";
            boolean isUpdated = DBConnection.updateEmployee(ID, Name, Email, Phone_no, Address, WorkTitle, Salary, WorkingStatus);
            if (isUpdated) {
                JOptionPane.showMessageDialog(f, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                tableModel.setValueAt(Name, selectedRow, 0);
                tableModel.setValueAt(Email, selectedRow, 2);
                tableModel.setValueAt(Phone_no, selectedRow, 3);
                tableModel.setValueAt(Address, selectedRow, 4);
                tableModel.setValueAt(WorkTitle, selectedRow, 5);
                tableModel.setValueAt(Salary, selectedRow, 6);
                tableModel.setValueAt(WorkingStatus, selectedRow, 7);
            } else {
                JOptionPane.showMessageDialog(f, "Error updating employee in the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        // Delete Employee Button
        JButton deleteEmploy = new JButton("Delete Employee");
        gbc.gridx = 1;
        gbc.gridy = 8;
        panel.add(deleteEmploy, gbc);
        deleteEmploy.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(f, "Please select an employee to delete.", "No Employee Selected", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String ID = tableModel.getValueAt(selectedRow, 1).toString(); // Get selected employee ID
            boolean isDeleted = DBConnection.deleteEmployee(ID);
            if (isDeleted) {
                JOptionPane.showMessageDialog(f, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(f, "Error deleting employee from the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        f.add(panel, BorderLayout.NORTH);
        f.add(scrollPane, BorderLayout.CENTER);
        f.setVisible(true);
    }
}
