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
public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getWaitlistedStudentsByClass;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        
        try {
            addScheduleEntry = connection.prepareStatement("insert into schedule (semester, courseCode, studentID, status, timestamp) values (?, ?, ?, ?, ?)");
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getCourseCode());
            addScheduleEntry.setString(3, entry.getStudentID());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimestamp());
            addScheduleEntry.executeUpdate();
            
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedules = new ArrayList<ScheduleEntry>();
        
        try {
            getScheduleByStudent = connection.prepareStatement("select semester, courseCode, studentID, status, timestamp from schedule where semester = ? and studentID = ?");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);
            resultSet = getScheduleByStudent.executeQuery();
            
            while (resultSet.next()) {
                schedules.add(new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5)));
            }
            
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return schedules;
    }
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode) {
        connection = DBConnection.getConnection();
        int studentCount = 0;
        
        try {
           getScheduledStudentCount = connection.prepareStatement("select studentID from schedule where semester = ? and courseCode = ?");
           getScheduledStudentCount.setString(1, currentSemester);
           getScheduledStudentCount.setString(2, courseCode);
           resultSet = getScheduledStudentCount.executeQuery();
           
           while (resultSet.next()) {
               studentCount += 1;
           }
            
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return studentCount;
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> students = new ArrayList<ScheduleEntry>();
        
        try {
            getWaitlistedStudentsByClass = connection.prepareStatement("select studentID, timestamp from schedule where semester = ? and courseCode = ? and status = ?");
            getWaitlistedStudentsByClass.setString(1, semester);
            getWaitlistedStudentsByClass.setString(2, courseCode);
            getWaitlistedStudentsByClass.setString(3, "w");
            resultSet = getWaitlistedStudentsByClass.executeQuery();
            
            while (resultSet.next()) {
                students.add(new ScheduleEntry(semester, courseCode, resultSet.getString(1), "w", resultSet.getTimestamp(2)));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return students;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode) {
        connection = DBConnection.getConnection();
        
        try {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from schedule where semester = ? and studentID = ? and courseCode = ?");
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3, courseCode);
            dropStudentScheduleByCourse.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        
        try {
            dropScheduleByCourse = connection.prepareStatement("delete from schedule where semester = ? and courseCode = ?");
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);
            dropScheduleByCourse.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        
        try {
            updateScheduleEntry = connection.prepareStatement("update schedule set status = 's' where semester = ? and courseCode = ? and studentID = ?");
            updateScheduleEntry.setString(1, entry.getSemester());
            updateScheduleEntry.setString(2, entry.getCourseCode());
            updateScheduleEntry.setString(3, entry.getStudentID());
            updateScheduleEntry.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
}
