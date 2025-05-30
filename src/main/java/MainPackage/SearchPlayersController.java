package MainPackage;

import PlayerData.Player;
import PlayerData.PlayerList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class SearchPlayersController {

    private PlayerList playerlist;

    public void setPlayerlist(PlayerList list) {
        this.playerlist = list;
    }

    Stage stage;

    Main application;

    public Main getApplication() {
        return application;
    }

    public void setApplication(Main application) {
        this.application = application;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void OnSearchNameClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("search-name.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        SearchNameController searchNameController = fxmlLoader.getController();

        searchNameController.setPlayerlist(playerlist);
        searchNameController.setApplication(application);
        searchNameController.setStage(this.stage);

        stage.setTitle("Search Players By Name");
        stage.setScene(scene);
        stage.show();
    }

    public void OnSearchClubCountClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("search-club-country.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        SearchClubCountryController searchccController = fxmlLoader.getController();

        searchccController.setApplication(application);
        searchccController.setPlayerlist(playerlist);
        searchccController.setStage(this.stage);

        stage.setTitle("Search Players By club and country");
        stage.setScene(scene);
        stage.show();
    }

    public void OnSearchPositionClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("search-position.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        SearchPositionController searchposController = fxmlLoader.getController();

        searchposController.setApplication(application);
        searchposController.setPlayerlist(playerlist);
        searchposController.setStage(this.stage);

        stage.setTitle("Search Players By Position");
        stage.setScene(scene);
        stage.show();
    }

    public void OnSearchSalaryClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("search-salary.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        SearchSalaryController searchsalController = fxmlLoader.getController();

        searchsalController.setApplication(application);
        searchsalController.setPlayerlist(playerlist);
        searchsalController.setStage(this.stage);

        stage.setTitle("Search Players By Position");
        stage.setScene(scene);
        stage.show();
    }

    public void OnCountryCountClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("countrywise-count.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        CountrywiseCountController countrycController = fxmlLoader.getController();

        countrycController.setApplication(application);
        countrycController.setPlayerlist(playerlist);
        countrycController.setStage(this.stage);

        HashMap<String, Integer> countryPlayerMap = playerlist.CountCountryPlayer();
        countrycController.setCountryPlayerMap(countryPlayerMap);

        stage.setTitle("Country wise player count");
        stage.setScene(scene);
        stage.show();
    }

    public void OnBackToMainClick(ActionEvent actionEvent) throws Exception {
        application.showMainMenu();
    }
}
