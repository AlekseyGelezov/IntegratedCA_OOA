/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package integratedca_ooa;

import java.io.*;
import java.sql. * ;
import java.util.Scanner;

/**
 *
 * @author aleksey
 * student number: sba23107
 * 
 */
public class IntegratedCA_OOA {

    /**
     * @param args the command line arguments
     */
    
    
    
    // ALLOWS ACCESS OF MYSQL DATABASE & TABLES. ALSO DEFINES STRINGS AND SCANNER REQUIRED FOR TERMINAL USAGE
    private static String SQLurl = "jdbc:mysql://localhost:3306/col_database";
    private static String SQLuser = "aleksey";
    private static String SQLpassword = "26325againA";
    private static Scanner user_input = new Scanner(System.in);
    private static String terminaluser;

    
    // USER TERMINAL, ALLOWS USER TO LOG IN AS ADMIN, OFFICE OR LECTURER
public static void collegeLoginInput(){
        System.out.println("Please enter your username: ");
        String col_username = user_input.nextLine();
        System.out.println("Please enter your password: ");
        String col_password = user_input.nextLine();
       
        if(col_username.equals("admin") && col_password.equals("java")){
            terminaluser = "admin";
            System.out.println("Hello, Administrator!");
        }else if(col_username.equals("office") && col_password.equals("java")){
            terminaluser = "Office";
            System.out.println("Hello, Office Employee!");
        }else if(col_username.equals("lecture") && col_password.equals("java")){
            terminaluser = "Lecturer";
            System.out.println("Hello, Lecturer!");
        }else{
            System.out.println("Login failed. Check if your username or password was correct.");
            collegeLoginInput();
        }

}


public static void displayConsole(){
        try {
        FileWriter c_report = new FileWriter("c_report.csv");
        
        FileWriter s_report = new FileWriter("s_report.csv");
        
        FileWriter l_report = new FileWriter("l_report.csv");
       
        Connection connection = DriverManager.getConnection(SQLurl, SQLuser, SQLpassword);
        System.out.println("College Database Connection Established!");
       
        String CourseReportQuery = "SELECT * FROM Courses";
        PreparedStatement c_Statement = connection.prepareStatement(CourseReportQuery);
        ResultSet c_result = c_Statement.executeQuery();
       
        String StudentReportQuery = "SELECT * FROM Students";
        PreparedStatement s_Statement = connection.prepareStatement(StudentReportQuery);
        ResultSet s_result = s_Statement.executeQuery();
       
        String LecturerReportQuery = "SELECT * FROM Lecturers";
        PreparedStatement l_Statement = connection.prepareStatement(LecturerReportQuery);
        ResultSet l_result = l_Statement.executeQuery();
       
        while (true){
            System.out.println("Enter \"1 to generate a Course Report\", \"2 to generate a Student Report\", \"3 to generate a Lecturer Report\" , \"4 to change password\", \"5 to Log out\"");
            if(terminaluser.equals("admin")){
                System.out.println("\"6 to Manage Users\"");
            }
            System.out.println("Select an option: ");
            int user_choice = user_input.nextInt();
            user_input.nextLine();
       
        switch (user_choice) {
        case 1:
// PRINTS OUT A COURSE REPORT
            while (c_result.next()) {
                String programmeid = c_result.getString("programme_id");
                
                String programmename = c_result.getString("programme_name");
                
                String programmecapacity = c_result.getString("programme_capacity");
                
                String moduleid = c_result.getString("module_id");
                
                String modulename = c_result.getString("module_name");
                
                int modulecapacity = c_result.getInt("module_capacity");
                
                String location = c_result.getString("location");
                
                String lecturerid = c_result.getString("lecturer_id");
                       
                
                c_report.append((programmeid)).append(",");
                
                c_report.append((programmename)).append(",");
                
                c_report.append((programmecapacity)).append(",");
                
                c_report.append((moduleid)).append(",");
                
                c_report.append((modulename)).append(",");
                
                c_report.append(String.valueOf(modulecapacity)).append(",");
                
                c_report.append((location)).append(",");
                
                c_report.append((lecturerid)).append(",");
            }
            
        //write here outside while
            c_report.flush();
            c_report.close();
            break;
        }
        }
        }catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        
}
public static void main(String[] args) {
        collegeLoginInput();
        displayConsole();
}
}
