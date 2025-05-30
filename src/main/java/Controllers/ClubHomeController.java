package Controllers;

import DTO.BuyReqDTO;
import DTO.GetPlayerDTO;
import DTO.RefreshDTO;
import MainPackage.Main;
import PlayerData.Database;
import PlayerData.FileOperations;
import PlayerData.Player;
import DTO.SellReqDTO;
import Network.ReadThreadClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class ClubHomeController {

    public Button sellButton;
    public Button buyButton;
    public Button showButton;
    public Button showmarketButton;
    public Button logoutButton;
    public Label welcomelabel;
    private Main main;
    private String clubname;

    @FXML
    private TableView<Player> tableView;

    @FXML
    private TableView<Player> buyableView;

    @FXML
    private TableColumn<Player, String> nameColumn;

    @FXML
    private TableColumn<Player, String> countryColumn;

    @FXML
    private TableColumn<Player, Integer> ageColumn;

    @FXML
    private TableColumn<Player, Double> heightColumn;

    @FXML
    private TableColumn<Player, String> clubColumn;

    @FXML
    private TableColumn<Player, String> positionColumn;

    @FXML
    private TableColumn<Player, Integer> numberColumn;

    @FXML
    private TableColumn<Player, Integer> salaryColumn;

    @FXML
    private TableColumn<Player, String> nameColumn1;

    @FXML
    private TableColumn<Player, String> countryColumn1;

    @FXML
    private TableColumn<Player, Integer> ageColumn1;

    @FXML
    private TableColumn<Player, Double> heightColumn1;

    @FXML
    private TableColumn<Player, String> clubColumn1;

    @FXML
    private TableColumn<Player, String> positionColumn1;

    @FXML
    private TableColumn<Player, Integer> numberColumn1;

    @FXML
    private TableColumn<Player, Integer> salaryColumn1;

    private ObservableList<Player> playerData;
    private ObservableList<Player> buyableData;


    public void setMain(Main main) {
        this.main = main;
        buyableView.setVisible(false);
        buyButton.setVisible(false);
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

    @FXML
    public void initialize() {

        welcomelabel.setText("Welcome to " + clubname);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        clubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));

        numberColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer number, boolean empty) {
                super.updateItem(number, empty);
                if (empty || number == null) {
                    setText(null);
                } else if (number == -1) {
                    setText("No Provided Jersey Number");
                } else {
                    setText(number.toString());
                }
            }
        });

        playerData = FXCollections.observableArrayList(ReadThreadClient.clubplayers);
        tableView.setItems(playerData);


        nameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryColumn1.setCellValueFactory(new PropertyValueFactory<>("country"));
        ageColumn1.setCellValueFactory(new PropertyValueFactory<>("age"));
        heightColumn1.setCellValueFactory(new PropertyValueFactory<>("height"));
        clubColumn1.setCellValueFactory(new PropertyValueFactory<>("club"));
        positionColumn1.setCellValueFactory(new PropertyValueFactory<>("position"));
        numberColumn1.setCellValueFactory(new PropertyValueFactory<>("number"));
        salaryColumn1.setCellValueFactory(new PropertyValueFactory<>("salary"));

        numberColumn1.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer number, boolean empty) {
                super.updateItem(number, empty);
                if (empty || number == null) {
                    setText(null);
                } else if (number == -1) {
                    setText("No Provided Jersey Number");
                } else {
                    setText(number.toString());
                }
            }
        });

        buyableData = FXCollections.observableArrayList(ReadThreadClient.sellableplayers);
        buyableView.setItems(buyableData);
    }


    @FXML
    private void OnSellClick() {
        Player selectedPlayer = tableView.getSelectionModel().getSelectedItem();

        if (selectedPlayer == null) {
            showAlert(Alert.AlertType.ERROR, "No Selection", "Please select a player to sell.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Sell");
        confirmationAlert.setHeaderText("Sell Player: " + selectedPlayer.getName());
        confirmationAlert.setContentText("Do you really want to sell this player?");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {

                    SellReqDTO sellReqDTO = new SellReqDTO();
                    sellReqDTO.setPlayer(selectedPlayer);
                    sellReqDTO.setClubname(clubname);

                    main.getSocketWrapper().write(sellReqDTO);

                    System.out.println("Sell request sent: " + sellReqDTO);
                    showAlert(Alert.AlertType.INFORMATION, "Sell Successful", "Player " + selectedPlayer.getName() + " is up for sale.");

                    buyableData = FXCollections.observableArrayList(ReadThreadClient.sellableplayers);
                    buyableView.setItems(buyableData);

                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to send sell request.");
                }
            }
        });
    }


    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void OnBuyClick(ActionEvent actionEvent) throws IOException, InterruptedException {
        Player selectedPlayer = buyableView.getSelectionModel().getSelectedItem();

        if (selectedPlayer == null) {
            showAlert(Alert.AlertType.ERROR, "No Selection", "Please select a player to buy.");
            return;
        }

        if (clubname.equals(selectedPlayer.getClub())) {
            showAlert(Alert.AlertType.WARNING, "Player Already in Club",
                    "Player " + selectedPlayer.getName() + " is already a member of your club.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Purchase");
        confirmationAlert.setHeaderText("Buy Player: " + selectedPlayer.getName());
        confirmationAlert.setContentText("Do you really want to buy this player for " + selectedPlayer.getSalary() + "?");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    BuyReqDTO buyReqDTO = new BuyReqDTO();
                    buyReqDTO.setPlayer(selectedPlayer);
                    buyReqDTO.setClubname(clubname);
                    main.getSocketWrapper().write(buyReqDTO);

                    System.out.println("Buy request sent: " + buyReqDTO);
                    showAlert(Alert.AlertType.INFORMATION, "Purchase Successful", "Player " + selectedPlayer.getName() + " has been purchased.");

                    playerData.clear();
                    playerData = FXCollections.observableArrayList(ReadThreadClient.clubplayers);
                    //System.out.println("Playerdata size initialization in refresh" + playerData.size());
                    tableView.setItems(playerData);

                    buyableData.clear();
                    buyableData = FXCollections.observableArrayList(ReadThreadClient.sellableplayers);
                    //System.out.println("buyable data size initialization in refresh" + buyableData.size());
                    buyableView.setItems(buyableData);

                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to send buy request.");
                }
            }
        });
    }

    public void OnShowClick(ActionEvent actionEvent) {

        playerData.clear();
        playerData = FXCollections.observableArrayList(ReadThreadClient.clubplayers);
        //System.out.println("Playerdata size initialization in refresh" + playerData.size());
        tableView.setItems(playerData);

        tableView.setVisible(true);
        sellButton.setVisible(true);
        buyableView.setVisible(false);
        buyButton.setVisible(false);
    }


    public void OnShowMarketClick(ActionEvent actionEvent) {

        buyableData.clear();
        buyableData = FXCollections.observableArrayList(ReadThreadClient.sellableplayers);
        //System.out.println("buyable data size initialization in refresh" + buyableData.size());
        buyableView.setItems(buyableData);

        tableView.setVisible(false);
        sellButton.setVisible(false);
        buyableView.setVisible(true);
        buyButton.setVisible(true);
    }

    public void OnLogoutClick(ActionEvent actionEvent) throws Exception {
        FileOperations.savesellablePlayers(buyableData);
        FileOperations.savePlayers(Main.allplayerlist);
        main.showLoginPage();
    }

    public void OnRefreshClick(ActionEvent actionEvent) throws IOException {

        RefreshDTO refreshDTO = new RefreshDTO();
        refreshDTO.setClubname(clubname);

        main.getSocketWrapper().write(refreshDTO);

        playerData.clear();
        buyableData.clear();

        playerData = FXCollections.observableArrayList(ReadThreadClient.clubplayers);
        //System.out.println("Playerdata size initialization in refresh" + playerData.size());
        tableView.setItems(playerData);

        buyableData = FXCollections.observableArrayList(ReadThreadClient.sellableplayers);
        //System.out.println("buyable data size initialization in refresh" + buyableData.size());
        buyableView.setItems(buyableData);

    }
}

