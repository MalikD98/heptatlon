package client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.ServerImpl;

import client.service.ClientService;

import java.io.IOException;

public class ConnexionController {

    @FXML
    private TextField identifiantField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMessage;

    private ClientService clientService;


    public ConnexionController() {
        clientService = new ClientService();
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String identifiant = identifiantField.getText();
        String password = passwordField.getText();

        if (clientService.authenticate(identifiant, password) != -1) {
            // Rediriger vers la page de consultation
            try {
                Parent consultationPage = FXMLLoader.load(getClass().getResource("/Consultation.fxml"));
                Scene consultationScene = new Scene(consultationPage);

                // Récupère la scène actuelle et le stage
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Change la scène du stage
                stage.setScene(consultationScene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorMessage.setText("Identifiant ou mot de passe incorrect");
        }
    }
}
