package schrader.schedulingapp.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.time.ZoneId;
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


    public void onLoginButtonClick() {
        String username = usernameTextBox.getText().toString();
        String password = passwordTextBox.getText().toString();
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
        System.out.println(Locale.getDefault().getLanguage());
        if (Locale.getDefault().getLanguage().equals("fr")) {
            translateLoginScreenOnLoad();
        }
    }
}