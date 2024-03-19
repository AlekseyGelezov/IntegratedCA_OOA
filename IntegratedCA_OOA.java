/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package integratedca_ooa;

import java.sql. * ;

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
        String query = "SELECT * FROM students" + "SELECT * FROM courses";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Process the results
            while (resultSet.next()) {
                int id = resultSet.getInt("student_id");
                String name = resultSet.getString("student_fn");
                String programme = resultSet.getString("course_programme");
                System.out.println("ID: " + id + ", Name: " + name + ", Course:" + programme);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
}

}
