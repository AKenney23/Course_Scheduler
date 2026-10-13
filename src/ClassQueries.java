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
public class ClassQueries {
    private static Connection connection;
    private static PreparedStatement addClass;
    private static PreparedStatement getCurrentSemesterCourseCodes;
    private static PreparedStatement getClassSeats;
    private static PreparedStatement dropClass;
    private static ResultSet resultSet;
    
    public static void addClass(ClassEntry classEntry) {
       connection = DBConnection.getConnection();
       
       try {
           addClass = connection.prepareStatement("insert into class (semester, coursecode, seats) values (?, ?, ?)");
           addClass.setString(1, classEntry.getSemester());
           addClass.setString(2, classEntry.getCourseCode());
           addClass.setInt(3, classEntry.getSeats());
           addClass.executeUpdate();
       } catch (SQLException sqlException) {
           sqlException.printStackTrace();
       }
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodesList = new ArrayList<String>();
        
        try {
            getCurrentSemesterCourseCodes = connection.prepareStatement("select courseCode from class where semester = ?");
            getCurrentSemesterCourseCodes.setString(1, semester);
            resultSet = getCurrentSemesterCourseCodes.executeQuery();
                    
            while (resultSet.next()) {
                courseCodesList.add(resultSet.getString(1));
            }
        
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return courseCodesList;
        
    }
    
    public static int getClassSeats(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        int seatCount = 0;
        
        try {
            getClassSeats = connection.prepareStatement("select seats from class where semester = ? and courseCode = ?");
            getClassSeats.setString(1, semester);
            getClassSeats.setString(2, courseCode);
            resultSet = getClassSeats.executeQuery();
            
            while (resultSet.next()) {
                seatCount = resultSet.getInt(1);
            }
            
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return seatCount;
    }
    
    public static void dropClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        
        try {
            dropClass = connection.prepareStatement("delete from class where semester = ? and courseCode = ?");
            dropClass.setString(1, semester);
            dropClass.setString(2, courseCode);
            dropClass.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
