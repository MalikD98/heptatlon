package client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.List;

import shared.Article;
import client.service.ClientService;

/**
 * Point d'entrée de l'application JavaFX.
 */
public class Main extends Application {
    private ClientService clientService = new ClientService();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Client Heptathlon");

        VBox root = new VBox();
        ListView<String> listView = new ListView<>();
        listView.getItems().add("€ - money");

        /*try {
            List<Article> articles = clientService.rechercherArticlesParFamille("Sport");
            for (Article article : articles) {
                listView.getItems().add(article.getLibelle() + " - " + article.getPrix() + "€");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }*/

        root.getChildren().add(listView);
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
