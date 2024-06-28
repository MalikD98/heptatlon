package client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import shared.Article;
import shared.Facture;

import java.io.IOException;
import java.util.List;

import client.service.ClientService;

public class CommandeController {
    @FXML
    private TextField articleField;
    @FXML
    private TextField quantiteField;
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
    private TableColumn<Integer, String> quantiteColumn;
    @FXML
    private TableColumn<Article, Void> actionColumn;
    @FXML
    private TableView<Article> commandeTable;
    @FXML
    private TableColumn<Article, String> refColumn1;
    @FXML
    private TableColumn<Article, String> libelleColumn1;
    @FXML
    private TableColumn<Article, String> familleColumn1;
    @FXML
    private TableColumn<Article, Double> prixColumn1;
    @FXML
    private TableColumn<Article, String> quantiteColumn1;
    @FXML
    private TableColumn<Article, Void> actionColumn1;

    private ClientService clientService;

    public CommandeController() {
        clientService = new ClientService();
    }

    @FXML
    private void initialize() {
        // Initialize table columns if necessary
        refColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));
        libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        familleColumn.setCellValueFactory(new PropertyValueFactory<>("famille"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));

        refColumn1.setCellValueFactory(new PropertyValueFactory<>("reference"));
        libelleColumn1.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        familleColumn1.setCellValueFactory(new PropertyValueFactory<>("famille"));
        prixColumn1.setCellValueFactory(new PropertyValueFactory<>("prix"));

        // Configurer quantiteColumn avec TextField
        quantiteColumn.setCellFactory(col -> new TableCell<Integer, String>() {
            private final TextField textField = new TextField();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    textField.setText(item);
                    setGraphic(textField);
                }
            }
        });

        actionColumn.setCellFactory(new Callback<TableColumn<Article, Void>, TableCell<Article, Void>>() {
            @Override
            public TableCell<Article, Void> call(final TableColumn<Article, Void> param) {
                final TableCell<Article, Void> cell = new TableCell<Article, Void>() {
                    private final Button btn = new Button("Ajouter");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Article article = getTableView().getItems().get(getIndex());
                            System.out.println(quantiteColumn.getCellData(getIndex()));
                            // commandeTable.getItems().add(article);
                            // System.out.println("Action sur l'article : " + article.toString());
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        });

        actionColumn1.setCellFactory(new Callback<TableColumn<Article, Void>, TableCell<Article, Void>>() {
            @Override
            public TableCell<Article, Void> call(final TableColumn<Article, Void> param) {
                final TableCell<Article, Void> cell = new TableCell<Article, Void>() {
                    private final Button btn = new Button("Retirer");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            // Article article = getTableView().getItems().get(getIndex());
                            commandeTable.getItems().remove(getIndex());
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
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
    private void handleGetArticle() {
        int articleId = Integer.parseInt(articleField.getText());
        List<Article> articles = (articleId > 0) ?
            clientService.rechercherArticlesParId(articleId)
            :
            clientService.consulterStock();
        articleTable.getItems().clear();
        if (articles.size() != 0) {
            for (Article article : articles) {
                articleTable.getItems().add(article);
            }
        }
    }

    /*@FXML
    private void initCommandeTable() {
        List<Article> articles = clientService.consulterStock();
        commandeTable.getItems().clear();
        if (articles.size() != 0) {
            for (Article article : articles) {
                commandeTable.getItems().add(article);
            }
        }
    }*/

    /*@FXML
    private void handlePasserCommande(ActionEvent event) {
        try {
            // Charge la nouvelle vue
            Parent commandesPage = FXMLLoader.load(getClass().getResource("/path/to/Commandes.fxml"));
            Scene commandesScene = new Scene(commandesPage);

            // Récupère la scène actuelle et le stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Change la scène du stage
            stage.setScene(commandesScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}