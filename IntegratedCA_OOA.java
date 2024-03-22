/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package integratedca_ooa;

import java.sql. * ;
import java.util.Scanner;

/**
 *
 * @author aleksey
 */
public class IntegratedCA_OOA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/col_database";
        String user = "aleksey";
        String password = "26325againA";
        String query = "SELECT * FROM courses";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Process the results
            while (resultSet.next()) {
                int id = resultSet.getInt("course_id");
                String name = resultSet.getString("course_name");
                //String programme = resultSet.getString("course_programme");
                System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
}

    
    
    
//public void menu(){    
//Scanner managerDisplayInput = new Scanner(System.in);
//int admin_input = 0;
//
//    
//    
//System.out.println("Press 1 to enter login details, 2 to view staff list, 3 to add new staff and 4 to exit the display");
//      admin_input = managerDisplayInput.nextInt();
//      String admin_name, admin_password;
//      admin_name = managerDisplayInput.nextLine();
//      
//      switch (admin_input) {
//          
//       case 1:
//           System.out.println("Enter username");
//           admin_name = managerDisplayInput.nextLine();
//           System.out.println("Enter password");
//           admin_password = managerDisplayInput.nextLine();
//           if(!admin_name.equals("admin") && !admin_password.equals("java")) {
//           System.out.println("Invalid Login Details");
//           break;
//        
//           }
//            break;
//      }
//}
    
    
}
