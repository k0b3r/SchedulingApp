package schrader.schedulingapp.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import schrader.schedulingapp.DAO.AppointmentDAO;
import schrader.schedulingapp.DAO.UserDAO;
import schrader.schedulingapp.helper.Helpers;
import schrader.schedulingapp.model.Appointment;
import schrader.schedulingapp.model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class contains methods that are run when associated actions are taken on the Login Form screen.
 */

/**
 * @author Karoline Schrader
 */

public class LoginFormController implements Initializable {
    public Button loginButton;
    public Button resetButton;
    public TextField usernameTextBox;
    public PasswordField passwordTextBox;
    public Label timeZoneLabel;
    public ComboBox <String> languageSelector;
    public Label languageLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    ObservableList<String> languages = FXCollections.observableArrayList();
    public static User currentUser = null;

    /**
     * This method checks if the username and password entered by the user match any rows in the database, creates a User
     * object with the data from the matching row/redirects them to the Appointments screen (if there is a match), adds
     * login data to the login_activity file, and checks for any appointments in the next 15 minutes/displays the results in
     * an alert modal.
     * the Appointments screen
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    public void onLoginButtonClick(ActionEvent event) throws SQLException, IOException {
        String username = usernameTextBox.getText();
        String password = passwordTextBox.getText();
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime toUtc = zdt.withZoneSameInstant(ZoneId.of("UTC"));

        String fileName = "login_activity.txt";
        File file = new File(fileName);
        FileWriter fw = new FileWriter(file, true);
        PrintWriter outputFile = new PrintWriter(fw);

        ResultSet rs = UserDAO.getUser(username);
        rs.next();
        if (UserDAO.getUser(username, password) == 1) {
            currentUser = new User(rs.getInt("User_ID"), rs.getString("User_Name"), rs.getString("Password"),
                    rs.getTimestamp("Create_Date").toLocalDateTime(), rs.getString("Created_By"), rs.getTimestamp("Last_Update"), rs.getString("Last_Updated_By"));
            Helpers.createStage(event, "/schrader/schedulingapp/view/Appointments.fxml", "Appointment Schedule");

            outputFile.print("Successful login by " + currentUser.getUsername() + " at " + toUtc.toLocalDateTime() + "\n");

            StringBuilder appointmentInfo = new StringBuilder();
            ObservableList<Appointment> allApps = AppointmentDAO.getAppointments();
            for (Appointment a : allApps) {
                if ((a.getStartDate().isEqual(LocalDateTime.now()) || a.getStartDate().isAfter(LocalDateTime.now())) && (a.getStartDate().isBefore(LocalDateTime.now().plusMinutes(15)) || a.getStartDate().isEqual(LocalDateTime.now().plusMinutes(15)))) {
                    String startDateFormatted = new SimpleDateFormat("MM/dd/yyyy hh:mm a").format(Timestamp.valueOf(a.getStartDate()));
                    appointmentInfo.append(a.getAppointmentId()).append(", ").append(startDateFormatted).append("\n");
                }
            }
            if (!appointmentInfo.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming Appointments");
                alert.setHeaderText("The following appointments begin in the next 15 minutes.");
                alert.setContentText("Appointment ID, Date/Time: \n" + "\n" + appointmentInfo);
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming Appointments");
                alert.setHeaderText("No Upcoming Appointments");
                alert.setContentText("There are no appointments in the next 15 minutes.");
                alert.showAndWait();
            }
        }
        else {
            if (Locale.getDefault().getLanguage().equals("fr")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Connexion");
                alert.setHeaderText("Échec de la connexion");
                alert.setContentText("Le nom d'utilisateur et/ou le mot de passe saisis étaient incorrects");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login");
                alert.setHeaderText("Failed Login");
                alert.setContentText("The username and/or password entered was incorrect");
                alert.showAndWait();
            }

            // append to the file, date/time is in UTC but displayed in timestamp as it's more readable
            outputFile.print("Unsuccessful login by " + username + " at " + toUtc.toLocalDateTime() + "\n");
        }
        outputFile.close();
    }

    /**
     * This method clears the username and password fields when the User clicks the clear button.
     */
    public void resetLoginFields() {
        usernameTextBox.clear();
        passwordTextBox.clear();
    }

    /**
     * This method populates the Language combo box with options, and sets the default value to the users computer default language.
     * This combo box was not in requirements but was useful for testing.
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
     * This method translates the Login screen based on the language set on the Users computer when the screen is loaded.
     * I was receiving the error 'Can't find resource for bundle java.util.PropertyResourceBundle, key timeZoneLabel America/Los_Angeles'
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
     * This method translates the Login screen based on the language selected in the language combo box. This was not
     * part of requirements but implementing this made testing easier.
     */
    public void translateLoginScreenOnSelection() {
        if (languageSelector.getValue().equals("French")) {
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
     * This method sets up the Login Form screen to display the TimeZone set on the users computer, and translates the login
     * screen if their default language is french (see translateLoginScreenOnLoad for more information)
     * I was getting a 'can't find bundle for base name error' - After some trial and error I realized there wasn't anything wrong with my baseName value,
     * but the location of my languages bundle. I had created the bundle directly in my src folder, however since these files aren't Java source code they were being ignored.
     * Moving the bundle to the resources folder (where application resources that are not source code belong) resolved this error.
     * Also ran into this error again when I created my ResourceBundle outside the if statement checking if the language was French (but only in the case that my computers language was set to English)
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeZoneLabel.setText(timeZoneLabel.getText() + " " + ZoneId.systemDefault());
        populateLanguageSelector();
        if (Locale.getDefault().getLanguage().equals("fr")) {
            translateLoginScreenOnLoad();
        }
    }
}