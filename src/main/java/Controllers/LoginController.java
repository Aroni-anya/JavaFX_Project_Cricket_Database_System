package Controllers;

import DTO.GetPlayerDTO;
import DTO.LoginDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

import MainPackage.*;
import javafx.stage.Stage;


public class LoginController {

    public TextField usernamefield;
    public PasswordField passfield;
    private Main main;

    @FXML
    private Button resetButton;

    @FXML
    private Button loginButton;
    private Stage stage;

    public void setMain(Main main) {
        this.main = main;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void OnLoginClick(ActionEvent event) {
        new Thread(()->{
        String userName = usernamefield.getText();
        String password = passfield.getText();

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setUserName(userName);
        loginDTO.setPassword(password);
        try {
            main.getSocketWrapper().write(loginDTO);
            //main.getSocketWrapper().write(getplayerDTO);

            GetPlayerDTO getplayerDTO = new GetPlayerDTO();
            getplayerDTO.setClubname(userName);
            main.getSocketWrapper().write(getplayerDTO);


        } catch (IOException e) {
            e.printStackTrace();
        }
        }).start();

}

    @FXML
    public void OnResetClick(ActionEvent event) {
        usernamefield.setText(null);
        passfield.setText(null);
    }

    public void OnBackToMainClick(ActionEvent actionEvent) throws Exception {
        main.showMainMenu();
    }
}

