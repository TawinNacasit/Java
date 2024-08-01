package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.models.Student;
import ku.cs.models.StudentList;
import ku.cs.services.Datasource;
import ku.cs.services.StudentListFileDatasource;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class StudentsTableController {

    @FXML private TableView<Student> studentsTableView;

    private StudentList studentList;

    private Datasource<StudentList> datasource;

    @FXML
    public void initialize() {
        datasource = new StudentListFileDatasource("data", "student-list.csv");
        studentList = datasource.readData();
        showTable(studentList);

        studentsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue observable, Student oldValue, Student newValue) {
                if (newValue != null) {
                    try {
                        // FXRouter.goTo สามารถส่งข้อมูลไปยังหน้าที่ต้องการได้ โดยกำหนดเป็น parameter ที่สอง
                        FXRouter.goTo("student-score", newValue.getId());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void showTable(StudentList studentList) {
        // กำหนด column ให้มี title ว่า ID และใช้ค่าจาก getter id ของ object Student
        TableColumn<Student, String> idColumn = new TableColumn<>("Student ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        // กำหนด column ให้มี title ว่า Name และใช้ค่าจาก attribute name ของ object Student
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Wrapper Class ex. : int --> Integer
        // กำหนด column ให้มี title ว่า Score และใช้ค่าจาก attribute score ของ object Student
        TableColumn<Student, Double> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        TableColumn<Student, String> gradeColumn = new TableColumn<>("Grade");
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        // ล้าง column เดิมทั้งหมดที่มีอยู่ใน table แล้วเพิ่ม column ใหม่
        studentsTableView.getColumns().clear();
        studentsTableView.getColumns().add(idColumn);
        studentsTableView.getColumns().add(nameColumn);
        studentsTableView.getColumns().add(scoreColumn);
        studentsTableView.getColumns().add(gradeColumn);

        studentsTableView.getItems().clear();

        // อีกรูปแบบ
//        for(Student student : studentList.getStudents()) {
//            studentsTableView.getItems().add(student);
//        }

        // ใส่ข้อมูล Student ทั้งหมดจาก studentList ไปแสดงใน TableView
        for (Student student: studentList.getStudents()) {
            studentsTableView.getItems().add(student);
        }
    }
}