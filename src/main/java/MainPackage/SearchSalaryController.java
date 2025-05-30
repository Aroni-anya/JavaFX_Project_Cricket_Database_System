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

public class SearchSalaryController {
    public TextArea resultarea;
    public TextField upfield;
    public TextField lowfield;
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
        for (Player p : playerlist.getPlayers()) {
            System.out.println(p);
        }
    }

    @FXML
    public void initialize() {
        // Enable text wrapping for the result TextArea
        resultarea.setWrapText(true);
    }

    @FXML
    public void OnSalSearchClick(ActionEvent actionEvent) {

        if (playerlist.getPlayers() == null) {
            resultarea.setText("Player data is not available.");
            return;
        }

        try {
            int upsal = Integer.parseInt(upfield.getText().trim());
            int lowsal = Integer.parseInt(lowfield.getText().trim());

            if (upsal < lowsal) {
                resultarea.setText("Upper limit must be greater than or equal to lower limit.");
                return;
            }

            List<Player> players = playerlist.Search_players(upsal, lowsal);

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
        } catch (NumberFormatException e) {
            resultarea.setText("Invalid input. Please enter numeric values for salaries.");
        }
    }

    public void OnBackToSearchClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("search-players.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        SearchPlayersController searchcontroller = fxmlLoader.getController();
        searchcontroller.setApplication(this.application);
        searchcontroller.setPlayerlist(this.playerlist);
        searchcontroller.setStage(this.stage);

        stage.setTitle("Search Players");
        stage.setScene(scene);
        stage.show();
    }
}

