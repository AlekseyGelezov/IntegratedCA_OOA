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
        
//        String CourseReportQuery = "SELECT programme_id,programme_name,programme_capacity,module_id,module_name,module_capacity,location,lecturer_id FROM Courses";
//        PreparedStatement c_Statement = connection.prepareStatement(CourseReportQuery);
//        ResultSet c_result = c_Statement.executeQuery();

       
        String StudentReportQuery = "SELECT * FROM students";
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
                //String programmename = c_result.getString("programme_name");
                //c_report.append((programmename)).append(",");
                c_report.append((c_result.getString("programme_name"))).append(",");
                
                
                //String programmecapacity = c_result.getString("programme_capacity");
                //c_report.append((programmecapacity)).append(",");
                c_report.append((c_result.getString("programme_name"))).append(",");

                
                //String programmecapacity = c_result.getString("programme_capacity");
                //c_report.append((programmecapacity)).append(",");
                c_report.append((c_result.getString("programme_capacity"))).append(",");
                
                
                //String modulename = c_result.getString("module_name");
                //c_report.append((modulename)).append(",");
                c_report.append((c_result.getString("module_name"))).append(",");
                
                int modulecapacity = c_result.getInt("module_capacity");
                c_report.append(String.valueOf(modulecapacity)).append(",");
                
                //String location = c_result.getString("location");
                c_report.append(c_result.getString("location")).append(",");
                
                //------------------
                String lecturerid = c_result.getString("lecturer_id");
                    if (lecturerid != null) {
                                        //System.out.print(lecturerid);
                        String lecturernamequery = "SELECT lecturer_name FROM lecturers WHERE lecturer_id = '"  + lecturerid + "'";
                        //System.out.print(lecturernamequery + " - 1\n");
                        PreparedStatement PreparedLecturerNameStatement = connection.prepareStatement(lecturernamequery);
                        //System.out.print(PreparedLecturerNameStatement + " - 2\n");
                
                        ResultSet lecturerNameSet = PreparedLecturerNameStatement.executeQuery();
                        //System.out.print(lecturerNameSet + " - 3\n");

                        //System.out.print(lecturerNameSet.getString(1) + " - 4\n");
                        if(lecturerNameSet.next()){
                            //System.out.print(lecturerNameSet.getString(1) + " - 4\n");
                            String lecturerName = lecturerNameSet.getString("lecturer_name");
                            //System.out.print(lecturerName + " - 5\n");
                            c_report.append((lecturerName)).append("\n");
                        }
                        
                    }
                                
                
                
                
                
                
                
                
                
                
                
              /*  
                //System.out.print(lecturerid);
                String lecturernamequery = "SELECT lecturer_name FROM lecturers WHERE lecturer_id = '"  + lecturerid + "'";
                //String lecturernamequery = "SELECT lecturer_name FROM lecturers WHERE lecturer_id = 'L001'";
                System.out.print(lecturernamequery + " - 1\n");
                PreparedStatement PreparedLecturerNameStatement = connection.prepareStatement(lecturernamequery);
                System.out.print(PreparedLecturerNameStatement + " - 2\n");
                
                ResultSet lecturerNameSet = PreparedLecturerNameStatement.executeQuery();
                System.out.print(lecturerNameSet + " - 3\n");

                //System.out.print(lecturerNameSet.getString(1) + " - 4\n");
                if(lecturerNameSet.next()){
                    System.out.print(lecturerNameSet.getString(1) + " - 4\n");
                    String lecturerName = lecturerNameSet.getString("lecturer_name");
                    System.out.print(lecturerName + " - 5\n");
                    c_report.append((lecturerName)).append(",");
                }
                
                
                c_report.append((programmename)).append(",");
                
                c_report.append((programmecapacity)).append(",");
                
                c_report.append((moduleid)).append(",");
                
                c_report.append((modulename)).append(",");
                
                c_report.append(String.valueOf(modulecapacity)).append(",");
                
                c_report.append((location)).append(",");
                */
                //c_report.append((lecturerid)).append("\n");
                //c_report.append((lecturerName)).append("\n");
            }
            
        //write here outside while
            c_report.flush();
            c_report.close();
            break;
            
        case 2:
              while(s_result.next()){
                String studentname = s_result.getString("student_name");
                String studentid = s_result.getString("student_id");
           
                s_report.append((studentname)).append(",");
                s_report.append((studentid)).append("\n");
             }
             s_report.flush();
             s_report.close();
            break;
            
            case 3:
            while(l_result.next()){
               String lecturerid = l_result.getString("lecturer_id");
               String lecturername = l_result.getString("lecturer_name");
               String lecturerrole = l_result.getString("lecturer_role");
               
               l_report.append((lecturerid)).append(",");
               l_report.append((lecturername)).append(",");
               l_report.append((lecturerrole)).append("\n");
            }
            l_report.flush();
            l_report.close();
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
