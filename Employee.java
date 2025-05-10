import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
class Employee {
    private String name;
    private String employeeId;

    public Employee(String name, String employeeId) {
        this.name = name;
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}