/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package integratedca_ooa;

import java.io.*;
import static java.lang.constant.ConstantDescs.NULL;
import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author aleksey student number: sba23107
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
    public static void collegeLoginInput(String SQLurl, String SQLuser, String SQLpassword) throws SQLException {
        Connection init_connection = DriverManager.getConnection(SQLurl, SQLuser, SQLpassword);
        System.out.println("College Database Connection Established!");
        System.out.println("Please enter your username: ");
        String col_username = user_input.nextLine();
        System.out.println("Please enter your password: ");
        String col_password = user_input.nextLine();
        //--------------------------------------------------------------------
        String loginNameQuery = "SELECT user_full_name, user_login_name, user_password FROM users WHERE user_login_name = '" + col_username + "'";
        PreparedStatement PreparedLoginNameStatement = init_connection.prepareStatement(loginNameQuery);
        ResultSet loginNameSet = PreparedLoginNameStatement.executeQuery();
        if (loginNameSet.next()) {
            String userFullName = loginNameSet.getString("user_full_name");
            String userLoginName = loginNameSet.getString("user_login_name");
            String userPassword = loginNameSet.getString("user_password");
            
            // not registered
            if (userLoginName.equals("") || userLoginName.equals(NULL)) {
                System.out.println("User is not registered");
            }
            // admin
            if (col_username.equals("admin") && col_password.equals(userPassword)) {
                System.out.println("Hello, " + userFullName);
                terminaluser = "admin";
            }
            // office
            else if (col_username.equals("office") && col_password.equals(userPassword)) {
                System.out.println("Hello, " + userFullName);
                terminaluser = "office";
            }
            // lecturer
            else if (col_username.equals("lecturer") && col_password.equals(userPassword)) {
                System.out.println("Hello, " + userFullName);
                terminaluser = "lecturer";
            }
            else {
                System.out.println("Login failed. Check if your username or password was correct.");
            }
            }
        }
    

    public static void displayConsole() {
        try {
            FileWriter c_report = new FileWriter("c_report.csv");

            FileWriter s_report = new FileWriter("s_report.csv");

            FileWriter l_report = new FileWriter("l_report.csv");

            FileWriter s_report_by_id = new FileWriter("s_report_by_id.csv");
            
            FileWriter l_report_by_id = new FileWriter("l_report_by_id.csv");
            
            

            //FileWriter er_report = new FileWriter("er_report.csv");
            //FileWriter fb_report = new FileWriter("fb_report.csv");
            Connection connection = DriverManager.getConnection(SQLurl, SQLuser, SQLpassword);
//            System.out.println("College Database Connection Established!");

            String CourseReportQuery = "SELECT * FROM courses";
            PreparedStatement c_Statement = connection.prepareStatement(CourseReportQuery);
            ResultSet c_result = c_Statement.executeQuery();

            String GradesReportQuery = "SELECT * FROM grades";
            PreparedStatement g_Statement = connection.prepareStatement(GradesReportQuery);
            ResultSet g_result = g_Statement.executeQuery();


            while (true) {
                if (terminaluser.equals("admin")) {
                    System.out.println("Enter \"6 to change password\", \"8 to create new user\", \"9 to delete user\", \"10 to update user password\", \"7 to Log out\"");
                }
                if (terminaluser.equals("office")) {
                    System.out.println("Enter \"1 to generate a Course Report\", \"2 to generate a Student Report\", \"3 to generate a Lecturer Report\" ,\"4 to generate report by lecturer ID\", \"5 to generate report by student ID\", \"6 to change password\", \"7 to Log out\"");

                }
                if (terminaluser.equals("lecturer")) {
                    System.out.println("Enter \"4 to generate a Lecturer Report\" ,\"6 to change password\", \"7 to Log out\"");

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
                            //c_report.append(c_result.getString("lecturer_id")).append(",");

                            String lecturerid = c_result.getString("lecturer_id");
//                System.out.println(lecturerid + " <--------Lecturer ID");
                            if (lecturerid != null) {
                                String lecturernamequery = "SELECT lecturer_name FROM lecturers WHERE lecturer_id = '" + lecturerid + "'";
                                PreparedStatement PreparedLecturerNameStatement = connection.prepareStatement(lecturernamequery);
                                ResultSet lecturerNameSet = PreparedLecturerNameStatement.executeQuery();
                                if (lecturerNameSet.next()) {
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
                        String s_heading = "Student ID,Student Name,Programme,Current Module,Completed Module,Grades,Module To Repeat,Grades for Failed Module\n";
                        s_report.write(s_heading);
                        while (g_result.next()) {
                            s_report.append((g_result.getString("student_id"))).append(",");
                            String studentId = g_result.getString("student_id");
                            if (studentId != null) {
                                String studentnamequery = "SELECT student_name FROM students WHERE student_id = '" + studentId + "'";
                                PreparedStatement PreparedStudentNameStatement = connection.prepareStatement(studentnamequery);
                                ResultSet studentNameSet = PreparedStudentNameStatement.executeQuery();
                                if (studentNameSet.next()) {
                                    String studentName = studentNameSet.getString("student_name");
                                    s_report.append((studentName)).append(",");
                                }
                            }
                            String moduleId = g_result.getString("module_id");
                            if (moduleId != null) {
                                String modulenamequery = "SELECT module_name, programme_name FROM courses WHERE module_id = '" + moduleId + "'";
                                PreparedStatement PreparedModuleNameStatement = connection.prepareStatement(modulenamequery);
                                ResultSet moduleNameSet = PreparedModuleNameStatement.executeQuery();
                                if (moduleNameSet.next()) {
                                    String programmeName = moduleNameSet.getString("programme_name");
                                    s_report.append((programmeName)).append(",");
                                    String moduleName = moduleNameSet.getString("module_name");
                                    s_report.append((moduleName)).append(",");

                                }
                            }

                            s_report.append((g_result.getString("passed_module_id"))).append(",");
                            s_report.append((g_result.getString("grades_passed"))).append(",");
                            String fModuleId = g_result.getString("failed_module_id");
                            if (fModuleId != "") {
                                s_report.append((g_result.getString("failed_module_id"))).append(",");
                                s_report.append((g_result.getString("grades_failed"))).append("\n");
                            }

                        }

                        s_report.flush();
                        s_report.close();
                        break;

                    case 3:
                        String l_heading = "Lecturer Name,Lecturer Role,Programme ID,Module Name,Students Enrolled\n";
                        l_report.write(l_heading);
                        while (c_result.next()) {
                            String lecturerid = c_result.getString("lecturer_id");
                            if (lecturerid != null) {
                                String lecturernamequery = "SELECT lecturer_name, lecturer_role FROM lecturers WHERE lecturer_id = '" + lecturerid + "'";
                                PreparedStatement PreparedLecturerNameStatement = connection.prepareStatement(lecturernamequery);
                                ResultSet lecturerNameSet = PreparedLecturerNameStatement.executeQuery();
                                if (lecturerNameSet.next()) {
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
                        
                    //--------------------------------------------------------
                    case 4:
                        System.out.println("Enter valid Lecturer ID");
                        String lecturerid = user_input.nextLine();
                        String l_heading_by_id = "Lecturer Name,Lecturer Role,Programme ID,Module Name,Students Enrolled\n";
                        l_report_by_id.write(l_heading_by_id);
                        while (c_result.next()) {
//                            String lecturerid = c_result.getString("lecturer_id");
//                            if (lecturerid != null) {
                            String lecturernamequery = "SELECT lecturer_name, lecturer_role FROM lecturers WHERE lecturer_id = '" + lecturerid + "'";
                            PreparedStatement PreparedLecturerNameStatement = connection.prepareStatement(lecturernamequery);
                            ResultSet lecturerNameSet = PreparedLecturerNameStatement.executeQuery();
                            if (lecturerNameSet.next()) {
                                String lecturerName = lecturerNameSet.getString("lecturer_name");
                                l_report_by_id.append((lecturerName)).append(",");
                                String lecturerRole = lecturerNameSet.getString("lecturer_role");
                                l_report_by_id.append((lecturerRole)).append(",");
                                }
//                            }
                            l_report_by_id.append((c_result.getString("programme_id"))).append(",");
                            l_report_by_id.append((c_result.getString("module_name"))).append(",");
                            l_report_by_id.append(String.valueOf(c_result.getInt("module_capacity"))).append("\n");
                        }
                        l_report_by_id.flush();
                        l_report_by_id.close();
                        break;    
                        
                        
                        
                    //--------------------------------------------------------    

                    case 5:
                        System.out.println("Enter valid Student ID");
                        String studentId = user_input.nextLine();
                        String s_heading_id = "Student ID,Student Name,Programme,Current Module,Completed Module,Grades,Module To Repeat,Feedback\n";
                        s_report_by_id.write(s_heading_id);
                        s_report_by_id.append((studentId)).append(",");
                        String studentnamequery = "SELECT student_name FROM students WHERE student_id = '" + studentId + "'";
                        PreparedStatement PreparedStudentNameStatement = connection.prepareStatement(studentnamequery);
                        ResultSet studentNameSet = PreparedStudentNameStatement.executeQuery();
                        if (studentNameSet.next()) {
                            String studentName = studentNameSet.getString("student_name");
                            s_report_by_id.append((studentName)).append(",");
                        }

                        String programmeNameQuery = "SELECT programme_name FROM courses WHERE programme_id = (SELECT programme_id FROM grades WHERE student_id = '" + studentId + "') limit 1";
                        PreparedStatement PreparedProgNameStatement = connection.prepareStatement(programmeNameQuery);
                        ResultSet progNameSet = PreparedProgNameStatement.executeQuery();
                        if (progNameSet.next()) {
                            String programmeName = progNameSet.getString("programme_name");
                            s_report_by_id.append((programmeName)).append(",");
                        }
                        String currentModuleNameQuery = "SELECT module_name FROM courses WHERE module_id = (SELECT module_id FROM grades WHERE student_id = '" + studentId + "')";
                        PreparedStatement PreparedModNameStatement = connection.prepareStatement(currentModuleNameQuery);
                        ResultSet modNameSet = PreparedModNameStatement.executeQuery();
                        if (modNameSet.next()) {
                            String moduleName = modNameSet.getString("module_name");
                            s_report_by_id.append((moduleName)).append(",");
                        }
                        String passedModuleNameQuery = "SELECT module_name FROM courses WHERE module_id = (SELECT passed_module_id FROM grades WHERE student_id = '" + studentId + "')";
                        PreparedStatement PreparedPassModNameStatement = connection.prepareStatement(passedModuleNameQuery);
                        ResultSet passModNameSet = PreparedPassModNameStatement.executeQuery();
                        if (passModNameSet.next()) {
                            String passedModuleName = passModNameSet.getString("module_name");
                            s_report_by_id.append((passedModuleName)).append(",");
                        }
                        String passedModuleGradesQuery = "SELECT grades_passed FROM grades WHERE student_id = '" + studentId + "'";
                        PreparedStatement PreparedPassModGradesStatement = connection.prepareStatement(passedModuleGradesQuery);
                        ResultSet passModGradesSet = PreparedPassModGradesStatement.executeQuery();
                        if (passModGradesSet.next()) {
                            String passedModuleGrades = passModGradesSet.getString("grades_passed");
                            s_report_by_id.append((passedModuleGrades)).append(",");
                        }
                        String failedModuleNameQuery = "SELECT module_name FROM courses WHERE module_id = (select failed_module_id from grades where student_id = '" + studentId + "')";
                        PreparedStatement PreparedFailedModNameStatement = connection.prepareStatement(failedModuleNameQuery);
                        ResultSet failedModNameSet = PreparedFailedModNameStatement.executeQuery();
                        if (failedModNameSet.next()) {
                            String failedModuleName = failedModNameSet.getString("module_name");
                            s_report_by_id.append((failedModuleName)).append(",");
                        }
                                                
                        String studentFeedbackQuery = "SELECT feedback FROM feedback WHERE student_id = '" + studentId + "'";
                        PreparedStatement PreparedFeedbackStatement = connection.prepareStatement(studentFeedbackQuery);
                        ResultSet feedbackSet = PreparedFeedbackStatement.executeQuery();
                        if (feedbackSet.next()) {
                            String feedback = feedbackSet.getString("feedback");
                            s_report_by_id.append((feedback)).append("\n");
                        }

                        s_report_by_id.flush();
                        s_report_by_id.close();
                        break;
                       

                    case 6:
                        // to change own password       
                        System.out.println("Please enter your new password, no more then 16 characters");
                        String newPassword = user_input.nextLine();
                        
                        String changeOwnPassword = "UPDATE users SET user_password = '" +newPassword+"' WHERE user_login_name = '" +terminaluser+"';";
                        PreparedStatement PreparedChangeOwnPasswordStatement = connection.prepareStatement(changeOwnPassword);
                        PreparedChangeOwnPasswordStatement.executeUpdate();
                        
                        System.out.println("Please, logout and login again");
                        break;
                    case 7:
                        // to logout
                        System.out.println("Logging out...");
                        System.exit(0);
                        break;
                    case 8:
//                      // create user
                        String lastUserIdquery = "SELECT user_id FROM users ORDER BY user_id DESC LIMIT 1;";
                        PreparedStatement PreparedLastuserIdStatement = connection.prepareStatement(lastUserIdquery);
                        ResultSet lastUserIdSet = PreparedLastuserIdStatement.executeQuery();
                        if (lastUserIdSet.next()) {
                            String lastUserId = lastUserIdSet.getString("user_id");
                            System.out.println("The last user ID registered in the system is: " + lastUserId);
                            System.out.println("Please, enter new User ID increment 1");
                            String newUserId = user_input.nextLine();
                            System.out.println("Please, enter full user name");
                            String newUserName = user_input.nextLine();
                            System.out.println("Please, enter new user role (lecturer, office)");
                            String newUserRole = user_input.nextLine();
                            System.out.println("Please, enter new user login name");
                            String newUserLoginName = user_input.nextLine();
                            System.out.println("Please, enter new user password (max 16 characters)");
                            String newUserPassword = user_input.nextLine();
                            String createNewUser = "INSERT INTO users(user_id,user_full_name,user_role,user_login_name,user_password) VALUES('" +newUserId+"','" +newUserName+"','" +newUserRole+"','" +newUserLoginName+ "','" +newUserPassword+ "');";
                            PreparedStatement PreparedCreateUserStatement = connection.prepareStatement(createNewUser);
                            PreparedCreateUserStatement.execute();
                        }
                        break;
                        
                        case 9:
                        // delete user
                        System.out.println("Please, enter user ID to delete");
                        String userIdToDelete = user_input.nextLine();
                        String deleteUser = "DELETE FROM users where user_id = '" +userIdToDelete+ "'";
                        PreparedStatement PreparedDeleteUserStatement = connection.prepareStatement(deleteUser);
                        PreparedDeleteUserStatement.execute();
                        break;    
                        
                        case 10:
                        // update user password
                        System.out.println("Please, enter user ID to update password");
                        String userIdToUpdate = user_input.nextLine();
                        System.out.println("Please, enter new password");
                        String userNewPassword = user_input.nextLine();
                        String updateUser = "UPDATE users SET user_password = '" +userNewPassword+ "' WHERE user_id = '" +userIdToUpdate+ "';";
                        PreparedStatement PreparedUpdateUserStatement = connection.prepareStatement(updateUser);
                        PreparedUpdateUserStatement.executeUpdate();
                        break;    
                        
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws SQLException {
        collegeLoginInput(SQLurl,SQLuser,SQLpassword);
        displayConsole();
        //collegeManageUsers();
    }
}


