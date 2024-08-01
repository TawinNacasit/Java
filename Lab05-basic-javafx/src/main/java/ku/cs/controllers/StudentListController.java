package ku.cs.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ku.cs.models.Student;
import ku.cs.models.StudentList;
import ku.cs.services.StudentHardCodeDatasource;
import ku.cs.services.FXRouter;
import java.io.IOException;
import ku.cs.services.Datasource;
import ku.cs.services.StudentListHardCodeDataSource;
import ku.cs.services.StudentListFileDatasource;

public class StudentListController {
    @FXML private ListView<Student> studentListView;
    @FXML private Label idLabel;
    @FXML private Label nameLabel;
    @FXML private Label scoreLabel;

    @FXML private Label errorLabel;
    @FXML private TextField giveScoreTextField;

    private StudentList studentList;
    private Student selectedStudent;
    private Datasource<StudentList> datasource;
    @FXML
    public void initialize() {
        errorLabel.setText("");
        clearStudentInfo();
        //StudentHardCodeDatasource datasource = new StudentHardCodeDatasource();
        //Datasource<StudentList> datasource = new StudentListHardCodeDataSource();
        datasource = new StudentListFileDatasource("data", "student-list.csv");


        studentList = datasource.readData();
        showList(studentList);
        studentListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                if (newValue == null) {
                    clearStudentInfo();
                    selectedStudent = null;
                } else {
                    showStudentInfo(newValue);
                    selectedStudent = newValue;
                }
            }
        });
    }

    @FXML
    public void onBackButtonClick() {
        try {
            FXRouter.goTo("hello");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showList(StudentList studentList) {
        studentListView.getItems().clear();
        studentListView.getItems().addAll(studentList.getStudents());
    }

    private void showStudentInfo(Student student) {
        idLabel.setText(student.getId());
        nameLabel.setText(student.getName());
        scoreLabel.setText(String.format("%.2f", student.getScore()));
    }

    private void clearStudentInfo() {
        idLabel.setText("");
        nameLabel.setText("");
        scoreLabel.setText("");
    }
    @FXML
    public void onGiveScoreButtonClick() {
        if (selectedStudent != null) {
            String scoreText = giveScoreTextField.getText();
            String errorMessage = "";

            try {
                double score = Double.parseDouble(scoreText);
                studentList.giveScoreToId(selectedStudent.getId(), score);
                clearForm();
                showStudentInfo(selectedStudent);

                datasource.writeData(studentList);

                showList(studentList);
            } catch (NumberFormatException e) {
                errorMessage = "Please insert number value";
                errorLabel.setText(errorMessage);
                giveScoreTextField.setStyle("-fx-text-fill: red");
            } catch (IllegalArgumentException e){
                errorMessage = "Please enter a valid number";
                errorLabel.setText(errorMessage);
                giveScoreTextField.setStyle("-fx-text-fill: red");
            }
            finally {
                if (errorMessage.equals("")) {
                    giveScoreTextField.setText("");
                }
            }
        } else {
            giveScoreTextField.setText("");
            errorLabel.setText("");
        }
    }

    @FXML void clearForm(){
        errorLabel.setText("");
        giveScoreTextField.setText("");
        giveScoreTextField.setStyle("-fx-text-fill: black");
    }
}