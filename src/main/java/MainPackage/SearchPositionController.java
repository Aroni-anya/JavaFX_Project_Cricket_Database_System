//package MainPackage;
//
//import PlayerData.Player;
//import PlayerData.PlayerList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.util.List;
//
//public class SearchPositionController {
//
//    public TextArea resultarea;
//    public TextField positionfield;
//    Main application;
//    Stage stage;
//
//    public Main getApplication() {
//        return application;
//    }
//
//    public void setApplication(Main application) {
//        this.application = application;
//    }
//
//    public void setStage(Stage stage) {
//        this.stage = stage;
//    }
//
//    private PlayerList playerlist;
//    public Label resultlabel;
//
//    public void setPlayerlist(PlayerList list){
//        this.playerlist = list;
//    }
//
//    @FXML
//    public void OnPositionSearchClick(ActionEvent actionEvent) {
//
//        if (playerlist.getPlayers() == null) {
//            resultarea.setText("Player data is not available.");
//            return;
//        }
//
//        String position = positionfield.getText().trim().toLowerCase();
//
//        List<Player> players = playerlist.Search_players(position, 3);
//
//        if (players == null || players.isEmpty()) {
//            resultarea.setText("No Match Found");
//        } else {
//            StringBuilder result = new StringBuilder();
//            for (Player p : players) {
//                result.append(p.toString()).append("\n"); // Append player details
//            }
//            resultarea.setText(result.toString()); // Set the text area with player details
//        }
//    }
//
//    public void OnBackToSearchClick(ActionEvent actionEvent) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("search-players.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
//
//        SearchPlayersController searchcontroller = fxmlLoader.getController();
//        searchcontroller.setApplication(application);
//        searchcontroller.setPlayerlist(this.playerlist);
//        searchcontroller.setStage(this.stage);
//
//        stage.setTitle("Search Players");
//        stage.setScene(scene);
//        stage.show();
//    }
//}

package MainPackage;

import PlayerData.Player;
import PlayerData.PlayerList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SearchPositionController {

    public TextArea resultarea;
    public TextField positionfield;
    Main application;
    Stage stage;

    public Main getApplication() {
        return application;
    }

    public void setApplication(Main application) {
        this.application = application;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private PlayerList playerlist;
    public Label resultlabel;

    public void setPlayerlist(PlayerList list) {
        this.playerlist = list;
    }

    @FXML
    public void initialize() {
        resultarea.setWrapText(true);
    }

    @FXML
    public void OnPositionSearchClick(ActionEvent actionEvent) {

        if (playerlist.getPlayers() == null) {
            resultarea.setText("Player data is not available.");
            return;
        }

        String position = positionfield.getText().trim().toLowerCase();

        List<Player> players = playerlist.Search_players(position, 3);

        if (players == null || players.isEmpty()) {
            resultarea.setText("No Match Found");
        } else {
            StringBuilder result = new StringBuilder();
            for (Player p : players) {
                result.append(p.toString())
                        .append("\n\n");
            }
            resultarea.setText(result.toString().trim());
        }
    }

    public void OnBackToSearchClick(ActionEvent actionEvent) throws IOException {
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
}

