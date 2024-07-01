package client.controller;

import client.service.ClientService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import shared.Article;

import java.io.IOException;
import java.util.List;

public class StockController {
    @FXML
    private TextField articleField;
    @FXML
    private TableView<Article> articleTable;
    @FXML
    private TableColumn<Article, String> refColumn;
    @FXML
    private TableColumn<Article, String> libelleColumn;
    @FXML
    private TableColumn<Article, String> familleColumn;
    @FXML
    private TableColumn<Article, Double> prixColumn;
    @FXML
    private TableColumn<Article, Void> quantiteColumn;

    private ClientService clientService;

    // Constructeur sans argument
    public StockController() {
        clientService = new ClientService();
    }

    @FXML
    private void initialize() {
        refColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));
        libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        familleColumn.setCellValueFactory(new PropertyValueFactory<>("famille"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));

        quantiteColumn.setCellFactory(new Callback<TableColumn<Article, Void>, TableCell<Article, Void>>() {
            @Override
            public TableCell<Article, Void> call(final TableColumn<Article, Void> param) {
                final TableCell<Article, Void> cell = new TableCell<Article, Void>() {
                    private final TextField textField = new TextField();
                    private final Button btn = new Button("Ajouter");

                    {
                        btn.setDisable(true);
                        textField.textProperty().addListener(new ChangeListener<String>() {
                            @Override
                            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                btn.setDisable(newValue.trim().isEmpty());
                            }
                        });

                        btn.setOnAction((ActionEvent event) -> {
                            Article article = getTableView().getItems().get(getIndex());
                            String reference = article.getReference();
                            int quantite;
                            try {
                                quantite = Integer.parseInt(textField.getText());
                                if (clientService.ajouterProduitStock(reference, quantite)) {
                                    showInfo("Succès", "Le stock a été mis à jour.");
                                } else {
                                    showError("Erreur", "Impossible de mettre à jour le stock.");
                                }
                            } catch (NumberFormatException e) {
                                showError("Quantité invalide", "Veuillez entrer un nombre valide.");
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new HBox(5, textField, btn));
                        }
                    }
                };
                return cell;
            }
        });

        initArticleTable();
    }

    @FXML
    private void initArticleTable() {
        List<Article> articles = clientService.consulterStock();
        articleTable.getItems().clear();
        if (articles != null && !articles.isEmpty()) {
            articleTable.getItems().addAll(articles);
        }
    }

    @FXML
    private void handleRetourAccueil(ActionEvent event) {
        try {
            // Charge la nouvelle vue
            Parent commandesPage = FXMLLoader.load(getClass().getResource("/Consultation.fxml"));
            Scene commandesScene = new Scene(commandesPage);

            // Récupère la scène actuelle et le stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Image image = new Image("icon.png");
            stage.getIcons().add(image);
            stage.setTitle("Gestion de Stock et Facturation");

            // Change la scène du stage
            stage.setScene(commandesScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGetArticle() {
        try {
            int articleId = Integer.parseInt(articleField.getText());
            List<Article> articles = (articleId > 0) ?
                    clientService.rechercherArticlesParId(articleId) :
                    clientService.consulterStock();
            articleTable.getItems().clear();
            if (articles != null && !articles.isEmpty()) {
                articleTable.getItems().addAll(articles);
            }
        } catch (NumberFormatException e) {
            showError("ID d'article invalide", "Veuillez entrer un ID d'article valide.");
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
