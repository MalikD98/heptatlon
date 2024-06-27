package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Chargement de l'interface FXML
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Interface.fxml"));

        // // Création de la scène et définition de ses propriétés
        primaryStage.setTitle("Gestion de Stock et Facturation");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
