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
        
        FileWriter id_report = new FileWriter("id_report.csv");
       
        Connection connection = DriverManager.getConnection(SQLurl, SQLuser, SQLpassword);
        System.out.println("College Database Connection Established!");
       
        String CourseReportQuery = "SELECT * FROM Courses";
        PreparedStatement c_Statement = connection.prepareStatement(CourseReportQuery);
        ResultSet c_result = c_Statement.executeQuery();
              
        String GradesReportQuery = "SELECT * FROM grades";
        PreparedStatement g_Statement = connection.prepareStatement(GradesReportQuery);
        ResultSet g_result = g_Statement.executeQuery();
        
        String StudentReportQuery = "SELECT * FROM students";
        PreparedStatement s_Statement = connection.prepareStatement(StudentReportQuery);
        ResultSet s_result = s_Statement.executeQuery();
       
        String LecturerReportQuery = "SELECT * FROM Lecturers";
        PreparedStatement l_Statement = connection.prepareStatement(LecturerReportQuery);
        ResultSet l_result = l_Statement.executeQuery();
       
        
        
        
        
        
        
        
        

//-- SELECT student_name FROM students where student_id=+student_id+ LIMIT 1
//-- SELECT student_id from enrollments;
//-- SELECT module_name from courses where programme_name = (SELECT programme_name from courses where programme_id=(select programme_id from enrollments where student_id=(SELECT student_id FROM students where student_name='Michael Young' LIMIT 1) LIMIT 1) LIMIT 1)
//-- SELECT passed_module_id, grades_passed FROM grades where student_id = (SELECT student_id FROM students where student_name='Michael Young' LIMIT 1)
//-- SELECT passed_module_id FROM grades where student_id = (SELECT student_id FROM students where student_name='Michael Young' LIMIT 1)
// -- SELECT module_name from courses where module_id = (SELECT passed_module_id FROM grades where student_id = (SELECT student_id FROM students where student_name='Michael Young' LIMIT 1))
        
        
        while (true){
            System.out.println("Enter \"1 to generate a Course Report\", \"2 to generate a Student Report\", \"3 to generate a Lecturer Report\" , \"4 to generate report by student ID\",\"5 to change password\", \"6 to Log out\"");
            if(terminaluser.equals("admin")){
                System.out.println("\"6 to Manage Users\"");
            }
            System.out.println("Select an option: ");
            int user_choice = user_input.nextInt();
            user_input.nextLine();
       
        switch (user_choice) {
        case 1:
// GENERATES A COURSE REPORT, COURSE REPORT CONTAINS PROGRAMME NAME, 
            String c_heading = "Module,Programme,Students Enrolled,Classroom,Lecturer\n";
            c_report.write(c_heading);
            
            while (c_result.next()) {
                c_report.append((c_result.getString("module_name"))).append(",");          
                c_report.append((c_result.getString("programme_name"))).append(",");
                c_report.append(String.valueOf(c_result.getInt("module_capacity"))).append(",");
                c_report.append(c_result.getString("location")).append(",");
                String lecturerid = c_result.getString("lecturer_id");
                    if (lecturerid != null) {
                        String lecturernamequery = "SELECT lecturer_name FROM lecturers WHERE lecturer_id = '"  + lecturerid + "'";                      
                        PreparedStatement PreparedLecturerNameStatement = connection.prepareStatement(lecturernamequery);               
                        ResultSet lecturerNameSet = PreparedLecturerNameStatement.executeQuery();                       
                        if(lecturerNameSet.next()){
                            String lecturerName = lecturerNameSet.getString("lecturer_name");
                            c_report.append((lecturerName)).append("\n");
                        }                        
                    }                
            }
        //write here outside while

            c_report.flush();
            c_report.close();
            break;
            
        case 2:
            String s_heading = "Student ID,Student Name,Programme,Current Module,Completed Module,Grades,Module To Repeat\n";
            s_report.write(s_heading);
              while(g_result.next()){
                s_report.append((g_result.getString("student_id"))).append(",");
                String studentId = g_result.getString("student_id");
                    if (studentId != null) {
                        String studentnamequery = "SELECT student_name FROM students WHERE student_id = '"  + studentId + "'";                      
                        PreparedStatement PreparedStudentNameStatement = connection.prepareStatement(studentnamequery);               
                        ResultSet studentNameSet = PreparedStudentNameStatement.executeQuery();                       
                        if(studentNameSet.next()){
                            String studentName = studentNameSet.getString("student_name");
                            s_report.append((studentName)).append(",");
                        }                        
                    } 
                String moduleId = g_result.getString("module_id");
                    if (moduleId != null) {
                        String modulenamequery = "SELECT module_name, programme_name FROM courses WHERE module_id = '"  + moduleId + "'";                      
                        PreparedStatement PreparedModuleNameStatement = connection.prepareStatement(modulenamequery);               
                        ResultSet moduleNameSet = PreparedModuleNameStatement.executeQuery();                       
                        if(moduleNameSet.next()){
                            String programmeName = moduleNameSet.getString("programme_name");
                            s_report.append((programmeName)).append(","); 
                            String moduleName = moduleNameSet.getString("module_name");
                            s_report.append((moduleName)).append(",");
                           
                        }                        
                    }                              
                s_report.append((g_result.getString("grades_passed"))).append("\n");
             }
              
             
                    
             s_report.flush();
             s_report.close();
            break;
                                
                
                
                
                
                
                
                
                
                
                
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
