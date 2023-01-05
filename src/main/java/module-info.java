module schrader.schedulingapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens schrader.schedulingapp to javafx.fxml;
    exports schrader.schedulingapp;
}