//package MainPackage;
//
//import PlayerData.Player;
//import PlayerData.PlayerList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.util.List;
//
//public class ClubSearchController {
//
//    @FXML
//    private TextField namefield;
//
//    @FXML
//    private VBox Vbxfield;
//
//    @FXML
//    private Button searchButton;
//
//    @FXML
//    private Button clearButton;
//
//    private PlayerList playerlist;
//
//    private String currentAction;
//
//    Stage stage;
//
//    Main application;
//
//    public ClubSearchController() {
//    }
//
//    public void setStage(Stage stage) {
//        this.stage = stage;
//    }
//
//    public void setApplication(Main application) {
//        this.application = application;
//    }
//
//    @FXML
//    public void initialize() {
//        // Set initial visibility to false
//        namefield.setVisible(false);
//        Vbxfield.setVisible(false);
//        searchButton.setVisible(false);
//        clearButton.setVisible(false);
//    }
//
//    public void setPlayerlist(PlayerList playerlist) {
//        this.playerlist = playerlist;
//    }
//
//    public void OnMaxSalClick(ActionEvent actionEvent) {
//        namefield.clear();
//        Vbxfield.getChildren().clear();
//        currentAction = "MaxSalary";
//        showInputControls("Enter Club Name to find Player with Maximum Salary");
//    }
//
//    public void OnMaxAgeClick(ActionEvent actionEvent) {
//        namefield.clear();
//        Vbxfield.getChildren().clear();
//        currentAction = "MaxAge";
//        showInputControls("Enter Club Name to find Player with Maximum Age");
//    }
//
//    public void OnMaxHeightClick(ActionEvent actionEvent) {
//        namefield.clear();
//        Vbxfield.getChildren().clear();
//        currentAction = "MaxHeight";
//        showInputControls("Enter Club Name to find Player with Maximum Height");
//    }
//
//    public void OnYearSalClick(ActionEvent actionEvent) {
//        namefield.clear();
//        Vbxfield.getChildren().clear();
//        currentAction = "YearlySalary";
//        showInputControls("Enter Club Name to calculate Total Yearly Salary");
//    }
//
//    public void OnSearchClick(ActionEvent actionEvent) {
//        String clubName = namefield.getText().trim();
//        if (clubName.isEmpty() || playerlist == null) {
//            Vbxfield.getChildren().add(new Label("Please enter a valid club name or ensure player list is set."));
//            return;
//        }
//
//        Vbxfield.getChildren().clear();
//        List<Player> results = null;
//
//        switch (currentAction) {
//            case "MaxSalary":
//                results = playerlist.MaxSalaryPlayer(clubName);
//                break;
//            case "MaxAge":
//                results = playerlist.MaxAgePlayer(clubName);
//                break;
//            case "MaxHeight":
//                results = playerlist.MaxHeightPlayer(clubName);
//                break;
//            case "YearlySalary":
//                int yearlySalary = playerlist.YearlySalary(clubName);
//                Vbxfield.getChildren().add(new Label("Total Yearly Salary: " + yearlySalary));
//                return;
//            default:
//                Vbxfield.getChildren().add(new Label("Unknown action."));
//                return;
//        }
//
//        if (results == null || results.isEmpty()) {
//            Label noResultLabel = new Label("No results found for the given club name.");
//            noResultLabel.setWrapText(true); // Enable text wrapping
//            Vbxfield.getChildren().add(noResultLabel);
//            Vbxfield.getChildren().add(noResultLabel);
//        } else {
//            for (Player player : results) {
//                Label playerLabel = new Label(player.toString());
//                playerLabel.setWrapText(true); // Enable text wrapping
//                playerLabel.setMaxWidth(Vbxfield.getPrefWidth());
//                Vbxfield.getChildren().add(playerLabel);
//            }
//        }
//    }
//
//    public void OnClearClick(ActionEvent actionEvent) {
//        namefield.clear();
//        Vbxfield.getChildren().clear();
//    }
//
//    private void showInputControls(String promptText) {
//        namefield.setPromptText(promptText);
//        namefield.setVisible(true);
//        Vbxfield.setVisible(true);
//        searchButton.setVisible(true);
//        clearButton.setVisible(true);
//    }
//
//    public void OnBackToMainClick(ActionEvent actionEvent) throws Exception {
////        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-menu.fxml"));
////        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
////
////        MainMenuController mainMenuController = fxmlLoader.getController();
////        mainMenuController.setPlayerlist(playerlist);
////        mainMenuController.setStage(this.stage);
////
////        stage.setTitle("Main Menu");
////        stage.setScene(scene);
////        stage.show();
//        application.showMainMenu();
//    }
//
//
//}

package MainPackage;

import PlayerData.Player;
import PlayerData.PlayerList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ClubSearchController {

    @FXML
    private TextField namefield;

    @FXML
    private VBox Vbxfield;

    @FXML
    private Button searchButton;

    @FXML
    private Button clearButton;

    @FXML
    private Label actionLabel;

    private PlayerList playerlist;

    private String currentAction;

    Stage stage;

    Main application;

    public ClubSearchController() {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setApplication(Main application) {
        this.application = application;
    }

    @FXML
    public void initialize() {
        namefield.setVisible(false);
        Vbxfield.setVisible(false);
        searchButton.setVisible(false);
        clearButton.setVisible(false);
        actionLabel.setVisible(false);
    }

    public void setPlayerlist(PlayerList playerlist) {
        this.playerlist = playerlist;
    }

    public void OnMaxSalClick(ActionEvent actionEvent) {
        namefield.clear();
        Vbxfield.getChildren().clear();
        currentAction = "MaxSalary";
        actionLabel.setText("Maximum Salary of Players in Club:");
        actionLabel.setVisible(true);
        showInputControls("Enter Club Name to find Player with Maximum Salary");
    }

    public void OnMaxAgeClick(ActionEvent actionEvent) {
        namefield.clear();
        Vbxfield.getChildren().clear();
        currentAction = "MaxAge";
        actionLabel.setText("Maximum Age of Players in Club:");
        actionLabel.setVisible(true);
        showInputControls("Enter Club Name to find Player with Maximum Age");
    }

    public void OnMaxHeightClick(ActionEvent actionEvent) {
        namefield.clear();
        Vbxfield.getChildren().clear();
        currentAction = "MaxHeight";
        actionLabel.setText("Maximum Height of Players in Club:");
        actionLabel.setVisible(true);
        showInputControls("Enter Club Name to find Player with Maximum Height");
    }

    public void OnYearSalClick(ActionEvent actionEvent) {
        namefield.clear();
        Vbxfield.getChildren().clear();
        currentAction = "YearlySalary";
        actionLabel.setText("Total Yearly Salary of Players in Club:");
        actionLabel.setVisible(true);
        showInputControls("Enter Club Name to calculate Total Yearly Salary");
    }

    public void OnSearchClick(ActionEvent actionEvent) {
        String clubName = namefield.getText().trim();
        if (clubName.isEmpty() || playerlist == null) {
            Vbxfield.getChildren().add(new Label("Please enter a valid club name or ensure player list is set."));
            return;
        }

        Vbxfield.getChildren().clear();
        List<Player> results = null;

        switch (currentAction) {
            case "MaxSalary":
                results = playerlist.MaxSalaryPlayer(clubName);
                break;
            case "MaxAge":
                results = playerlist.MaxAgePlayer(clubName);
                break;
            case "MaxHeight":
                results = playerlist.MaxHeightPlayer(clubName);
                break;
            case "YearlySalary":
                int yearlySalary = playerlist.YearlySalary(clubName);
                Vbxfield.getChildren().add(new Label("Total Yearly Salary: " + yearlySalary));
                return;
            default:
                Vbxfield.getChildren().add(new Label("Unknown action."));
                return;
        }

        if (results == null || results.isEmpty()) {
            Label noResultLabel = new Label("No results found for the given club name.");
            noResultLabel.setWrapText(true); // Enable text wrapping
            Vbxfield.getChildren().add(noResultLabel);
        } else {
            for (Player player : results) {
                Label playerLabel = new Label(player.toString());
                playerLabel.setWrapText(true); // Enable text wrapping
                playerLabel.setMaxWidth(Vbxfield.getPrefWidth());
                Vbxfield.getChildren().add(playerLabel);
            }
        }
    }

    public void OnClearClick(ActionEvent actionEvent) {
        namefield.clear();
        Vbxfield.getChildren().clear();
        actionLabel.setVisible(false);
    }

    private void showInputControls(String promptText) {
        namefield.setPromptText(promptText);
        namefield.setVisible(true);
        Vbxfield.setVisible(true);
        searchButton.setVisible(true);
        clearButton.setVisible(true);
    }

    public void OnBackToMainClick(ActionEvent actionEvent) throws Exception {
        application.showMainMenu();
    }
}

