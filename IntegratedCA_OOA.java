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
    public static void main(String[] args) {
        
    }
    
    
    // ALLOWS ACCESS OF MYSQL DATABASE & TABLES. ALSO DEFINES STRINGS AND SCANNER REQUIRED FOR TERMINAL USAGE
    private static String SQLurl = "jdbc:mysql://localhost:3306/col_database";
    private static String SQLuser = "aleksey";
    private static String SQLpassword = "26325againA";
    public static Scanner admininput = new Scanner(System.in);
    private static String terminaluser;

    
    // USER TERMINAL, ALLOWS USER TO LOG IN AS ADMIN, OFFICE OR LECTURER
public static void collegeLoginInput(){
        System.out.println("Please enter your username: ");
        String col_username = admininput.nextLine();
        System.out.println("Please enter your password: ");
        String col_password = admininput.nextLine();
       
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
}

//        
//        // PRINTS OUT A COURSE REPORT
//        String coursereportquery = "SELECT * FROM courses";
//        try (Connection connection = DriverManager.getConnection(url, user, password);
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(coursereportquery)) {
//
//            // Process the results
//            while (resultSet.next()) {
//                String programmeid = resultSet.getString("programme_id");
//                String programmename = resultSet.getString("programme_name");
//                String programmecapacity = resultSet.getString("programme_capacity");
//                String moduleid = resultSet.getString("module_id");
//                String modulename = resultSet.getString("module_name");
//                String modulecapacity = resultSet.getString("module_capacity");
//                String location = resultSet.getString("location");
//                String lecturerid = resultSet.getString("lecturer_id");
//                        
//                System.out.println("Programme ID: " + programmeid + ",Programme Name: " + programmename + ",Programme Capacity: " + programmecapacity + ",Module ID: " + moduleid + ",Module Name: " + modulename +",Module Capacity: " + modulecapacity + ",Located: " + location + ",Lecturer ID: " + lecturerid);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    
