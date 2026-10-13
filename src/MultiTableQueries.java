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
public class MultiTableQueries {
    private static Connection connection;
    private static PreparedStatement getAllClassDescriptions;
    private static PreparedStatement getScheduledStudentsByClass;
    private static PreparedStatement getWaitlistedStudentsByClass;
    private static ResultSet resultSet;
    
    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<ClassDescription> classDescriptions = new ArrayList<ClassDescription>();
        
        try {
            getAllClassDescriptions = connection.prepareStatement("select course.courseCode, course.description, class.seats from course, class where course.courseCode = class.courseCode and semester = ?");
            getAllClassDescriptions.setString(1, semester);
            resultSet = getAllClassDescriptions.executeQuery();
            
            while (resultSet.next()) {
                classDescriptions.add(new ClassDescription(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3)));
            }
            
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return classDescriptions;
    }
    
    public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> scheduledStudents = new ArrayList<StudentEntry>();
        
        try {
            getScheduledStudentsByClass = connection.prepareStatement("select student.studentID, student.firstName, student.lastName from student, schedule where "
                    + "schedule.courseCode = ? and schedule.studentID = student.studentID and schedule.semester = ? and schedule.status = ?");
            getScheduledStudentsByClass.setString(1, courseCode);
            getScheduledStudentsByClass.setString(2, semester);
            getScheduledStudentsByClass.setString(3, "s");
            resultSet = getScheduledStudentsByClass.executeQuery();
            
            while (resultSet.next()) {
                scheduledStudents.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return scheduledStudents;
    }
    
    public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> waitlistedStudents = new ArrayList<StudentEntry>();
        
        try {
            getWaitlistedStudentsByClass = connection.prepareStatement("select student.studentID, student.firstName, student.lastName from student, schedule where "
                    + "schedule.courseCode = ? and schedule.studentID = student.studentID and schedule.semester = ? and schedule.status = ? order by schedule.timestamp asc");
            getWaitlistedStudentsByClass.setString(1, courseCode);
            getWaitlistedStudentsByClass.setString(2, semester);
            getWaitlistedStudentsByClass.setString(3, "w");
            resultSet = getWaitlistedStudentsByClass.executeQuery();
            
            while (resultSet.next()) {
                waitlistedStudents.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }           
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return waitlistedStudents;
    }
}
