package MainPackage;

import Network.Server;
import PlayerData.Database;
import PlayerData.FileOperations;
import PlayerData.PlayerList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddPlayerController {
    public TextField namefield;
    public TextField countryfield;
    public TextField agefield;
    public TextField heightfield;
    public TextField clubfield;
    public TextField positionfield;
    public TextField jersyfield;
    public TextField salfield;
    public Button submitButton;
    public Button backButton;


    private PlayerList playerlist;

    Main application;

    Stage stage;

    public void setPlayerlist(PlayerList playerlist)
    {
        this.playerlist = playerlist;
    }

    public Main getApplication() {
        return application;
    }

    public void setApplication(Main application) {
        this.application = application;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }


    public void OnSubmitClick(ActionEvent actionEvent) throws Exception {
        String name = namefield.getText();
        String country = countryfield.getText();
        String ageText = agefield.getText();
        String heightText = heightfield.getText();
        String club = clubfield.getText();
        String position = positionfield.getText();
        String jersyNumberText = jersyfield.getText();
        String salaryText = salfield.getText();

        // Input validation
        if (name.isEmpty() || country.isEmpty() || ageText.isEmpty() || heightText.isEmpty() ||
                club.isEmpty() || position.isEmpty() || jersyNumberText.isEmpty() || salaryText.isEmpty()) {
            showAlert("Error", "All fields must be filled!");
            return;
        }

        int age = 0;
        double height = 0;
        int jersyNumber = 0;
        int salary = 0;

        try {
            age = Integer.parseInt(ageText);
            height = Double.parseDouble(heightText);
            jersyNumber = Integer.parseInt(jersyNumberText);
            salary = Integer.parseInt(salaryText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid numbers for age, height, jersy number, and salary.");
            return;
        }

        playerlist.AddPlayer(name, country, age, height, club, position, jersyNumber, salary);
        Main.setAllPlayerlist(playerlist);
        //FileOperations.savePlayers(playerlist);

        showAlert("Success", "Player added successfully!");

        resetFields();
    }

    public void OnBackClick(ActionEvent actionEvent) throws Exception {
        application.showMainMenu();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void resetFields() {

        namefield.clear();
        countryfield.clear();
        agefield.clear();
        heightfield.clear();
        clubfield.clear();
        positionfield.clear();
        jersyfield.clear();
        salfield.clear();
    }
}
