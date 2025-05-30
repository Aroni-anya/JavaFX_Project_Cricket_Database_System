package MainPackage;

import PlayerData.PlayerList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CountrywiseCountController {

    @FXML
    public TextArea resultarea;

    Main application;
    Stage stage;

    private HashMap<String, Integer> countrymap;
    private boolean isinitialized = false;

    @FXML
    public void initialize() {
        isinitialized = true;

        if (countrymap != null) {
            displayCountryPlayers();
        }
    }

    public void setCountryPlayerMap(HashMap<String, Integer> map) {
        this.countrymap = map;

        if (isinitialized) {
            displayCountryPlayers();
        }
    }

    public void setApplication(Main application) {
        this.application=application;
    }


    private void displayCountryPlayers() {
        if (countrymap == null || countrymap.isEmpty()) {
            resultarea.setText("No data available.");
            return;
        }

        resultarea.clear();
        for (var entry : countrymap.entrySet()) {
            String entryText = "Country: " + entry.getKey() + ", Player Count: " + entry.getValue() + "\n";
            resultarea.appendText(entryText);
        }
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private PlayerList playerlist;
    public Label resultlabel;




    public void setPlayerlist(PlayerList list){
        this.playerlist = list;
    }

    @FXML
    public void OnDisplayClick(ActionEvent actionEvent) {

        if (playerlist.getPlayers() == null) {
            resultarea.setText("Player data is not available.");
            return;
        }

        HashMap<String, Integer> resultmap = playerlist.CountCountryPlayer();

        if (resultmap.isEmpty()) {
            System.out.println("The map is empty.");
        } else {
            System.out.println("Country-wise Player Count:");
            for (Map.Entry<String, Integer> itr : resultmap.entrySet()) {
                System.out.println("Country: " + itr.getKey() + ", Number of Players: " + itr.getValue());
            }
        }


        if (resultmap == null || resultmap.isEmpty()) {
            resultarea.setText("No Match Found");
        } else {
            resultarea.clear();

            for (var entry : resultmap.entrySet()) {
                String entryText = "Country: " + entry.getKey() + ", Player Count: " + entry.getValue() + "\n\n";
                resultarea.appendText(entryText); // Append to the TextArea
            }
        }
    }

    public void OnBackToSearchClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("search-players.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        SearchPlayersController searchcontroller = fxmlLoader.getController();
        searchcontroller.setPlayerlist(this.playerlist);
        searchcontroller.setApplication(application);
        searchcontroller.setStage(this.stage);

        stage.setTitle("Search Players");
        stage.setScene(scene);
        stage.show();
    }
}

