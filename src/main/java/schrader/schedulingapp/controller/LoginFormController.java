package schrader.schedulingapp.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import schrader.schedulingapp.Utilities.UserDAO;
import schrader.schedulingapp.model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;


public class LoginFormController {
    public Button loginButton;
    public Button resetButton;
    public TextField usernameTextBox;
    public PasswordField passwordTextBox;
    public Label timeZoneLabel;
    public ComboBox languageSelector;
    public Label languageLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    ObservableList<String> languages = FXCollections.observableArrayList();
    public static User currentUser = null;
    public void createStage(ActionEvent event, String resource, String stageTitle) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(resource));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.show();
    }

    public void onLoginButtonClick(ActionEvent event) throws SQLException, IOException {
        String username = usernameTextBox.getText();
        String password = passwordTextBox.getText();

        String fileName = "login_activity.txt";
        File file = new File(fileName);
        FileWriter fw = new FileWriter(file, true);
        PrintWriter outputFile = new PrintWriter(fw);

        // Saving to login_activity.txt in UTC
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
        ZonedDateTime zdtToUtc = zdt.withZoneSameInstant(ZoneId.of("UTC"));

        ResultSet rs = UserDAO.getUser(username);
        rs.next();
        if (UserDAO.select(username, password) == 1) {
            currentUser = new User(rs.getInt("User_ID"), rs.getString("User_Name"), rs.getString("Password"),
                    rs.getTimestamp("Create_Date").toLocalDateTime(), rs.getString("Created_By"), rs.getTimestamp("Last_Update"), rs.getString("Last_Updated_By"));
            createStage(event, "/schrader/schedulingapp/view/AppointmentSchedule.fxml", "Appointment Schedule");

            // append to the file, date/time is in UTC but displayed in timestamp as it's more readable
            outputFile.print("Successful login by " + currentUser.getUsername() + " at " + Timestamp.valueOf(zdtToUtc.toLocalDateTime()) + "\n");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Failed Login");
            alert.setContentText("The username and/or password entered was incorrect");
            alert.showAndWait();

            // TODO - check with tutor, not sure if file should also be in UTC, but made sense to me since we store other values in UTC
            // append to the file, date/time is in UTC but displayed in timestamp as it's more readable
            outputFile.print("Unsuccessful login by " + username + " at " + Timestamp.valueOf(zdtToUtc.toLocalDateTime()) + "\n");
        }
        outputFile.close();
    }
    /**
     *
     */
    public void resetLoginFields() {
        usernameTextBox.clear();
        passwordTextBox.clear();
    }

    /**
     *
     */
    public void populateLanguageSelector() {
        languages.add("English");
        languages.add("French");
        languageSelector.setItems(languages);


        // if computers default locale starts with en set default value to English, if not set to French
        if (Locale.getDefault().toString().startsWith("en")) {
            languageSelector.setValue(languages.get(0));
        } else {
            languageSelector.setValue(languages.get(1));
        }
    }

    /**
     * Was receiving the error 'Can't find resource for bundle java.util.PropertyResourceBundle, key timeZoneLabel America/Los_Angeles'
     * after adding + " " + ZoneId.systemDefault() to the second line of this function. Realized I was adding it inside the getString function,
     * instead of inside the setText function/outside the getString function.
     */
    public void translateLoginScreenOnLoad() {
        ResourceBundle rb = ResourceBundle.getBundle("languages/login", Locale.getDefault());
        timeZoneLabel.setText(rb.getString("timeZoneLabel")+ " " + ZoneId.systemDefault());
        languageLabel.setText(rb.getString("languageLabel"));
        loginButton.setText(rb.getString("loginButton"));
        resetButton.setText(rb.getString("resetButton"));
        usernameLabel.setText(rb.getString("usernameLabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
    }

    /**
     *
     */
    public void translateLoginScreenOnSelection() {
        if (languageSelector.getValue().toString().equals("French")) {
            languageLabel.setText("Langue:");
            timeZoneLabel.setText("Fuseau horaire:"+ " " + ZoneId.systemDefault());
            loginButton.setText("Connexion");
            resetButton.setText("Réinitialiser");
            usernameLabel.setText("Nom d'utilisateur");
            passwordLabel.setText("Le mot de passe");
        } else {
            languageLabel.setText("Language:");
            timeZoneLabel.setText("Time Zone:" + " " + ZoneId.systemDefault());
            loginButton.setText("Login");
            resetButton.setText("Reset");
            usernameLabel.setText("Username");
            passwordLabel.setText("Password");
        }
    }

    /**
     * I was getting a 'can't find bundle for base name error' - After some trial and error I realized there wasn't anything wrong with my baseName value,
     * but the location of my languages bundle. I had created the bundle directly in my src folder, however since these files aren't Java source code they were being ignored by Maven.
     * Moving the bundle to the resources folder (where application resources that are not source code belong) resolved this error.
     * Also ran into this error again when I created my ResourceBundle outside the if statement checking if the language was French (but only in the case that my computers language was set to English)
     */
    public void initialize() {
        timeZoneLabel.setText(timeZoneLabel.getText() + " " + ZoneId.systemDefault());
        populateLanguageSelector();
        if (Locale.getDefault().getLanguage().equals("fr")) {
            translateLoginScreenOnLoad();
        }
    }
}