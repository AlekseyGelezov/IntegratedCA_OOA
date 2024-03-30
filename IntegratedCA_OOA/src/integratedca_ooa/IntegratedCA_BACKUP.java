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
public class IntegratedCA_BACKUP {

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
            System.out.println("Enter \"1 to generate a Course Report\", \"2 to generate a Student Report\", \"3 to generate a Lecturer Report\" , \"4 to change password\",\"5 to generate report by student ID\", \"6 to Log out\"");
            if(terminaluser.equals("admin")){
                System.out.println("\"7 to Manage Users\"");
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
            
            case 3:
            String l_heading = "Lecturer Name,Lecturer Role,Programme ID,Module Name,Students Enrolled\n";
            l_report.write(l_heading);
            while(c_result.next()){
                String lecturerid = c_result.getString("lecturer_id");
                    if (lecturerid != null) {
                        String lecturernamequery = "SELECT lecturer_name, lecturer_role FROM lecturers WHERE lecturer_id = '"  + lecturerid + "'";                      
                        PreparedStatement PreparedLecturerNameStatement = connection.prepareStatement(lecturernamequery);               
                        ResultSet lecturerNameSet = PreparedLecturerNameStatement.executeQuery();                       
                        if(lecturerNameSet.next()){
                            String lecturerName = lecturerNameSet.getString("lecturer_name");
                            l_report.append((lecturerName)).append(",");
                            String lecturerRole = lecturerNameSet.getString("lecturer_role");
                            l_report.append((lecturerRole)).append(",");
                        }                        
                    }                
                l_report.append((c_result.getString("programme_id"))).append(",");
                l_report.append((c_result.getString("module_name"))).append(",");
                l_report.append(String.valueOf(c_result.getInt("module_capacity"))).append("\n");
            }
            l_report.flush();
            l_report.close();
            break;
            
//            case 4:
//                collegeChangePassword();
//            break;
            /*
            case 5:
            System.out.println("Enter valid Student ID");
            String studentId = user_input.nextLine();
            String id_heading = "Student ID,Student Name,Programme,Current Module,Completed Module,Grades\n";
            id_report.write(id_heading);
              //while(g_result.next()){
                //s_report.append((g_result.getString("student_id"))).append(",");
                //String studentId = g_result.getString("student_id");
                id_report.append(studentId).append(",");

                String studentnamequery = "SELECT student_name FROM students WHERE student_id = '"  + studentId + "' LIMIT 1";
                //System.out.println(studentnamequery + "    <--------------------------");
                PreparedStatement PreparedStudentNameStatement = connection.prepareStatement(studentnamequery);               
                ResultSet studentNameSet = PreparedStudentNameStatement.executeQuery();                       
                if(studentNameSet.next()){
                    String studentName = studentNameSet.getString("student_name");
                    id_report.append((studentName)).append(",");
                    }                        

                String moduleId = g_result.getString("module_id");
                    if (moduleId != null) {
                        String modulenamequery = "SELECT module_name, programme_name FROM courses WHERE module_id = '"  + moduleId + "'";                      
                        PreparedStatement PreparedModuleNameStatement = connection.prepareStatement(modulenamequery);               
                        ResultSet moduleNameSet = PreparedModuleNameStatement.executeQuery();                       
                        if(moduleNameSet.next()){
                            String programmeName = moduleNameSet.getString("programme_name");
                            id_report.append((programmeName)).append(","); 
                            String moduleName = moduleNameSet.getString("module_name");
                            id_report.append((moduleName)).append(",");
                           
                        }                        
                    }                             
               // id_report.append((g_result.getString("grades_passed"))).append("\n");
             //}
              
             
                    
             id_report.flush();
             id_report.close();
            break;
            */
            // case 6:
            // System.out.println("Logging out...");
            //   System.exit(0);
            //break;
            
            // case 7:
            // collegeManageUsers();
            // break;
            
        }
        }
        }catch (SQLException | IOException e) {
            e.printStackTrace();
        }
}

//        private static void collegeChangePassword(){
//        System.out.println("Enter old password: ");
//        String oldPassword = user_input.nextLine();
//        if(oldPassword.equals(change_password)){
//            System.out.println("Enter new password: ");
//            String new_password = user_input.nextLine();
//            change_password = new_password;
//            System.out.println("Password was changed, try logging in again.");
//            collegeLoginInput();
//        }else{
//            System.out.println("Incorrect password. Please try again.");
//        }
//    }
//        
//        private static void collegeManageUsers(){
//        System.out.println("Manage users");
//        System.out.println("Enter \"1 to add a user\", \"2 to delete a user\", \"3 to update a user\" , \"4 to view users\", \"5 to go back to main menu\"");
//        int manageUserChoice = user_input.nextInt();
//        user_input.nextLine();
//       
//        switch(manageUserChoice){
//            case 1:
//                manage_AddUsers();
//                break;
//            case 2:
//                manage_removeUsers();
//                break;
//            case 3:
//                manage_UpdateUsers();
//                break;
//            case 4:
//                manage_ViewUsers();
//                break;
//            case 5:
//                System.out.println("Returning to the Main Menu...");
//                displayConsole();
//                break;
//            default:
//                System.out.println("Invalid Input: Please choose between 1 and 5.");
//        }
//    }
//        
//    private static void manage_AddUsers(){
//        System.out.println("Enter a user you wish to add: ");
//        String addedUser = user_input.nextLine();
//        userManagement.add(addedUser);
//        System.out.println("User " + addedUser + " added");
//    }
//    
//    private static void manage_removeUsers(){
//        System.out.println("Enter a user you wish to remove: ");
//        String user_removed = user_input.nextLine();
//        if(userManagement.contains(user_removed)){
//            userManagement.remove(user_removed);
//            System.out.println("User " + user_removed + " removed");
//        }else{
//            System.out.println("ERROR: User does not exist within the college's database.");
//        }
//    }
//    
//    private static void manage_UpdateUsers(){
//        System.out.println("Please enter a user name you wish to change: ");
//        String current_user = user_input.nextLine();
//        System.out.println("Please enter the username you wish to change your old user name to: ");
//        String user_new = user_input.nextLine();
//        if(userManagement.contains(current_user)){
//            userManagement.remove(current_user);
//            userManagement.add(user_new);
//            System.out.println("You have changed your old username to " + user_new);
//           
//        }else{
//            System.out.println("ERROR: This user does not exist within this College");
//        }
//    }
//    
// private static void manage_ViewUsers(){
//        for (String listOfUsers : userManagement){
//            System.out.println(listOfUsers);
        
        


public static void main(String[] args) {
        collegeLoginInput();
        displayConsole();
        //collegeManageUsers();
}
}
                /*
                // need to figure out how to deal with null values
                String failedModuleId = g_result.getString("failed_module_id");
                    if (failedModuleId != null) {
                        String failedmodulenamequery = "SELECT module_name FROM courses WHERE module_id = '"  + failedModuleId + "'";                      
                        PreparedStatement PreparedFailedModuleNameStatement = connection.prepareStatement(failedmodulenamequery);               
                        ResultSet failedModuleNameSet = PreparedFailedModuleNameStatement.executeQuery();                       
                        if(failedModuleNameSet.next()){
                            String moduleName = failedModuleNameSet.getString("module_name");
                            s_report.append((moduleName)).append("\n");                          
                        }                       
                    } 
                */

                
                //----------------
                //call courses by module_id to get module name
                //call courses by module_id to get programme name   
                //call courses by module_id to get pass module name
//===============================================           
/*
                String failedModuleId = g_result.getString("failed_module_id");
                    if (moduleId != null) {
                        String failedmodulenamequery = "SELECT module_name FROM courses WHERE module_id = '"  + failedModuleId + "'";                      
                        PreparedStatement PreparedFailedModuleNameStatement = connection.prepareStatement(failedmodulenamequery);               
                        ResultSet failedModuleNameSet = PreparedFailedModuleNameStatement.executeQuery();                       
                        if(failedModuleNameSet.next()){
                            String moduleName = failedModuleNameSet.getString("module_name");
                            s_report.append((moduleName)).append(",");                          
                        }                        
                    }  
                                
*/

                
 //==============================================                  
 //-- SELECT student_id FROM students where student_name='Michael Young' LIMIT 1
 //-- SELECT student_id from enrollments;
 //-- SELECT module_name from courses where programme_name = (SELECT programme_name from courses where programme_id=(select programme_id from enrollments where student_id=(SELECT student_id FROM students where student_name='Michael Young' LIMIT 1) LIMIT 1) LIMIT 1)
 //-- SELECT passed_module_id, grades_passed FROM grades where student_id = (SELECT student_id FROM students where student_name='Michael Young' LIMIT 1)
 //-- SELECT passed_module_id FROM grades where student_id = (SELECT student_id FROM students where student_name='Michael Young' LIMIT 1)
// -- SELECT module_name from courses where module_id = (SELECT passed_module_id FROM grades where student_id = (SELECT student_id FROM students where student_name='Michael Young' LIMIT 1))

//==============================================                
