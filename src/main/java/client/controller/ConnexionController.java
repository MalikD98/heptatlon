package client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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

    public static int magasinReference;


    public ConnexionController() {
        clientService = new ClientService();
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String identifiant = identifiantField.getText();
        String password = passwordField.getText();

        if (clientService.authenticate(identifiant, password)) {

            showInfo("Succes", "Connexion réalisé avec succès");

            // Stocker l'ID du magasin dans la variable globale
            magasinReference = clientService.getMagasinReference();

            // Rediriger vers la page de consultation
            try {
                Parent consultationPage = FXMLLoader.load(getClass().getResource("/Consultation.fxml"));
                Scene consultationScene = new Scene(consultationPage);

                // Récupère la scène actuelle et le stage
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Image image = new Image("icon.png");
                stage.getIcons().add(image);
                stage.setTitle("Gestion de Stock et Facturation");

                // Change la scène du stage
                stage.setScene(consultationScene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showError("Erreur de saisie","Identifiant ou mot de passe incorrect");
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
