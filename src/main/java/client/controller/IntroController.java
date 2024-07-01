package client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.application.Platform;

public class IntroController {

    @FXML
    private MediaView mediaView;

    @FXML
    public void initialize() {
        // Chemin vers la vidéo
        String videoPath = getClass().getResource("/logo.mp4").toExternalForm();
        
        // Créer un Media
        Media media = new Media(videoPath);
        
        // Créer un MediaPlayer
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        
        // Associer le MediaPlayer au MediaView
        mediaView.setMediaPlayer(mediaPlayer);
        
        // Démarrer la vidéo
        mediaPlayer.play();
        
        // Fermer l'intro et ouvrir la fenêtre principale après la fin de la vidéo
        mediaPlayer.setOnEndOfMedia(() -> {
            Platform.runLater(() -> {
                Stage stage = (Stage) mediaView.getScene().getWindow();
                stage.close();
                openMainWindow();
            });
        });

        mediaPlayer.setOnError(() -> {
            Platform.runLater(() -> {
                Stage stage = (Stage) mediaView.getScene().getWindow();
                stage.close();
                openMainWindow();
            });
            // System.err.println("Erreur lors du chargement de la vidéo: " + mediaPlayer.getError().getMessage());
        });
    }

    private void openMainWindow() {
        // Code pour ouvrir la fenêtre principale
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Connexion.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Connexion");
            stage.setScene(new Scene(root));
            Image image = new Image("icon.png");
            stage.getIcons().add(image);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
