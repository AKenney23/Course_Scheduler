/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author aidenkenney
 */
public class StudentQueries {
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getStudent;
    private static PreparedStatement findStudentID;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student) {
        connection = DBConnection.getConnection();
        
        try {
            addStudent = connection.prepareStatement("insert into student (studentID, firstName, lastName) values (?, ?, ?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
                    
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<StudentEntry> getAllStudents() {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> studentList = new ArrayList<StudentEntry>();
        
        try {
            getAllStudents = connection.prepareStatement("select studentID, firstName, lastName from student");
            resultSet = getAllStudents.executeQuery();
            
            while (resultSet.next()) {
                studentList.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }
            
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return studentList;
    }
    
    
    public static StudentEntry getStudent(String studentID) {
        connection = DBConnection.getConnection();
        StudentEntry student = null;
        
        try {
            getStudent = connection.prepareStatement("select studentID, firstName, lastName from student where studentID = ?");
            getStudent.setString(1, studentID);
            resultSet = getStudent.executeQuery();
            
            while (resultSet.next()) {
                student = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return student;
    }
    
    
    public static String findStudentID(String first, String last) {
        connection = DBConnection.getConnection();
        String id = "";
        
        try {
            findStudentID = connection.prepareStatement("select studentID from student where firstName = ? and lastName = ?");
            findStudentID.setString(1, first);
            findStudentID.setString(2, last);
            resultSet = findStudentID.executeQuery();
           
            while (resultSet.next()) {
                id = resultSet.getString(1);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return id;
    }
    
    public static void dropStudent(String studentID) {
        connection = DBConnection.getConnection();
        
        try {
            dropStudent = connection.prepareStatement("delete from student where studentID = ?");
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
