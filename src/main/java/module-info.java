module schrader.schedulingapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens schrader.schedulingapp to javafx.fxml;
    exports schrader.schedulingapp;
    exports schrader.schedulingapp.controller;
    opens schrader.schedulingapp.controller to javafx.fxml;
}