# Course Scheduler

A Java desktop application for managing semester-based course registration. Built with **Java Swing**, **JDBC**, and **Apache Derby**, it lets users maintain a course catalog, create class offerings with seat limits, register students, and view scheduled and waitlisted enrollments.

The project demonstrates event-driven GUI programming, object-oriented data models, prepared SQL statements, and relational database queries. It is organized as a NetBeans Java application with an Ant build.

## Features

### Admin tools

- Add semesters, courses, and student records.
- Offer a course in the selected semester with a specified seat capacity.
- View scheduled and waitlisted students for a class.
- Remove a student and their enrollments across semesters.
- Remove a class offering and its enrollments from the selected semester.

### Student tools

- Browse class offerings, descriptions, and seat capacities for a semester.
- Register a selected student for a class, with scheduled (`s`) or waitlisted (`w`) status based on capacity.
- View a student's schedule and enrollment status.
- Drop a class; the application includes logic to promote a waitlisted student.

The semester selector controls which semester's classes and schedules are displayed. Admin and Student are tabs in the same application; there is no login or role-based access control.

## Technology

| Component | Technology |
| --- | --- |
| Language | Java; project source and target level set to Java 11 |
| Desktop interface | Swing, with a NetBeans GUI Builder form |
| Database access | JDBC and prepared statements |
| Database | Apache Derby running as a network server |
| Project/build tooling | NetBeans and Apache Ant |

## Project contents

```text
Course_Scheduler/
|-- src/
|   |-- MainFrame.java          # Main window, entry point, and enrollment workflows
|   |-- MainFrame.form          # NetBeans GUI Builder layout
|   |-- DBConnection.java       # Shared JDBC database connection
|   |-- CourseEntry.java        # Course code and description model
|   |-- ClassEntry.java         # Semester offering and seat capacity model
|   |-- StudentEntry.java       # Student ID and name model
|   |-- ScheduleEntry.java      # Enrollment status and timestamp model
|   |-- ClassDescription.java   # Combined course and offering display model
|   |-- CourseQueries.java      # Course insertion and retrieval
|   |-- ClassQueries.java       # Class offerings and seat capacities
|   |-- StudentQueries.java     # Student creation, lookup, and removal
|   |-- SemesterQueries.java    # Semester creation and retrieval
|   |-- ScheduleQueries.java    # Enrollment creation, retrieval, removal, and updates
|   `-- MultiTableQueries.java  # Joined class descriptions and student rosters
|-- nbproject/                  # NetBeans configuration and Ant build support
|   |-- private/                # Machine-specific IDE settings
|   |-- build-impl.xml          # Generated Ant build implementation
|   |-- genfiles.properties     # Generated build-file metadata
|   |-- project.properties      # Compiler, classpath, and run settings
|   `-- project.xml             # NetBeans project definition
|-- build/                      # Generated compilation output
|-- test/                       # Test source directory (currently empty)
|-- build.xml                   # Main Ant build script
|-- manifest.mf                 # Application JAR manifest template
|-- xzq789yy.txt                 # Base64-encoded author/date metadata for About
`-- README.md                   # Project overview and documentation
```

A **course** is a catalog entry. A **class** is that course offered in a particular semester with a seat limit. A **schedule entry** connects a student to that offering and records their enrollment status.
