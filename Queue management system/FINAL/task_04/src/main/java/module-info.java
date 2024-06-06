module com.example.task_04 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.task_04 to javafx.fxml;
    exports com.example.task_04;
}