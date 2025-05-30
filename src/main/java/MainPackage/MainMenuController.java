//package MainPackage;
//
//import javafx.event.ActionEvent;
//
//public class MainMenuController {
//    public void OnLoginClick(ActionEvent actionEvent) {
//    }
//
//    public void OnSearchPlayerClick(ActionEvent actionEvent) {
//    }
//}

package MainPackage;

import PlayerData.FileOperations;
import PlayerData.PlayerList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    Stage stage;

    private Main application;
    private PlayerList playerlist;

    public Main getApplication() {
        return application;
    }

    public void setApplication(Main application) {
        this.application = application;
    }

    public void setPlayerlist(PlayerList list)
    {
        this.playerlist = list;
//        for(Player p:playerlist.getPlayers())
//            {
//                System.out.println(p);
//            }
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }


    public void OnSearchPlayerClick(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("search-players.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        SearchPlayersController searchcontroller = fxmlLoader.getController();
        searchcontroller.setApplication(application);
        searchcontroller.setPlayerlist(this.playerlist);
        searchcontroller.setStage(this.stage);

        stage.setTitle("Search Players");
        stage.setScene(scene);
        stage.show();

    }

    public void OnSearchClubsClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("club-search.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        ClubSearchController searchclcontroller = fxmlLoader.getController();
        searchclcontroller.setApplication(application);
        searchclcontroller.setPlayerlist(this.playerlist);
        searchclcontroller.setStage(this.stage);

        stage.setTitle("Search Players");
        stage.setScene(scene);
        stage.show();
    }

    public void OnAddPlayersClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("add-player.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        AddPlayerController addpcontroller = fxmlLoader.getController();
        addpcontroller.setApplication(application);
        addpcontroller.setPlayerlist(this.playerlist);
        addpcontroller.setStage(this.stage);

        stage.setTitle("Add Players");
        stage.setScene(scene);
        stage.show();
    }

    public synchronized void OnExitSystemClick(ActionEvent actionEvent) {
        try {
            FileOperations.savePlayers(playerlist);
            System.out.println("Players saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving players: " + e.getMessage());
        }
        System.exit(0);
    }


    public void OnLoginClick(ActionEvent actionEvent) throws Exception {
            if (this.application == null) {
                System.out.println("Application is not set!");
                return;
            }
            this.application.showLoginPage();

    }
}



