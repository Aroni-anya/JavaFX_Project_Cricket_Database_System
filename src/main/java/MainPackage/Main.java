package MainPackage;

import Network.*;
import Controllers.*;
import PlayerData.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class Main extends Application {

    private Stage stage;
    private SocketWrapper socketWrapper;

    public static PlayerList allplayerlist = new PlayerList();

    public static List<Player> playerlist;
    public static List<Player> sellableplayers;

    public static List<Player> getSellableplayers() {
        return sellableplayers;
    }

    public static void setSellableplayers(List<Player> sellableplayers) {
        Main.sellableplayers = sellableplayers;
    }

    public static List<Player> getPlayerlist() {
        return playerlist;
    }

    public static void setPlayerlist(List<Player> playerlist) {
        Main.playerlist = playerlist;
        //System.out.println("main list size: "+ playerlist.size());
    }

    public static void setAllPlayerlist(PlayerList playerlist) {
        System.out.println("allplayerlist loading");
        allplayerlist = playerlist;
        System.out.println("main list size: "+ allplayerlist.getPlayers().size());
    }

    public Stage getStage() {
        return stage;
    }

    public SocketWrapper getSocketWrapper()  {
        return socketWrapper;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        connectToServer();
        showMainMenu();
    }

    private void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        socketWrapper = new SocketWrapper(serverAddress, serverPort);
        new ReadThreadClient(this);
    }

    public void showMainMenu() throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        MainMenuController mainMenuController = fxmlLoader.getController();
        mainMenuController.setPlayerlist(allplayerlist);
        mainMenuController.setStage(stage);
        mainMenuController.setApplication(this);

        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void showLoginPage() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        LoginController controller = loader.getController();
        controller.setStage(stage);
        controller.setMain(this);

        stage.setTitle("Login");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public void showHomePage(String userName) throws Exception {

        String clubname = userName;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("club-home.fxml"));
        Parent root = loader.load();

        ClubHomeController controller = loader.getController();
        controller.setClubname(clubname);
        controller.initialize();
        controller.setMain(this);

        stage.setTitle("Home");
        stage.setScene(new Scene(root, 1000, 750));
        stage.show();
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect Credentials");
        alert.setContentText("The username and password you provided is not correct.");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        // This will launch the JavaFX application
        launch(args);
    }
}
