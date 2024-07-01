package client.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import shared.Article;
import shared.Commande;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import client.service.ClientService;

public class CommandeController {
    @FXML
    private TextField articleField;
    @FXML
    private TextField quantiteField;
    @FXML
    private TextField clientField;
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
    private TableColumn<Article, Integer> quantiteColumn1;
    @FXML
    private TableColumn<Article, Void> actionColumn;
    @FXML
    private Label montantTotal;
    @FXML
    private ComboBox<String> modePaiementBox;
    @FXML
    private Button validerCommande;

    private ClientService clientService;

    private List<Commande> commandes;

    public CommandeController() {
        clientService = new ClientService();
        commandes = new ArrayList<>();
    }

    @FXML
    private void initialize() {
        refColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));
        libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        familleColumn.setCellValueFactory(new PropertyValueFactory<>("famille"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));

        refColumn1.setCellValueFactory(new PropertyValueFactory<>("reference"));
        libelleColumn1.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        familleColumn1.setCellValueFactory(new PropertyValueFactory<>("famille"));
        prixColumn1.setCellValueFactory(new PropertyValueFactory<>("prix"));
        quantiteColumn1.setCellValueFactory(new PropertyValueFactory<>("quantite"));

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
                            try {
                                Article article = getTableView().getItems().get(getIndex());
                                BigDecimal quantiteBigDecimal = new BigDecimal(textField.getText());
                                BigDecimal montant = quantiteBigDecimal.multiply(article.getPrix());
        
                                // Vérifier si l'article existe déjà dans commandeTable
                                boolean articleExists = commandeTable.getItems().stream()
                                    .anyMatch(existingArticle -> existingArticle.getReference().equals(article.getReference()));
        
                                if (!articleExists) {
                                    Article commandeArticle = new Article(
                                        article.getReference(),
                                        article.getLibelle(),
                                        article.getFamille(),
                                        article.getPrix(),
                                        quantiteBigDecimal.intValue()
                                    );
        
                                    commandeTable.getItems().add(commandeArticle);
                                    commandes.add(new Commande(
                                        "",
                                        Integer.valueOf(article.getReference()),
                                        quantiteBigDecimal.intValue(),
                                        montant
                                    ));
        
                                    BigDecimal total = new BigDecimal(montantTotal.getText()).add(montant);
                                    montantTotal.setText(total.toString());
                                } else {
                                    // Afficher un message indiquant que l'article existe déjà
                                    showError("Article existant", "Cet article est déjà dans la commande.");
                                }
                            } catch (NumberFormatException e) {
                                // Afficher un message d'erreur ou gérer l'exception
                                showError("Quantité invalide", "Veuillez entrer une quantité valide.");
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
        
        actionColumn.setCellFactory(new Callback<TableColumn<Article, Void>, TableCell<Article, Void>>() {
            @Override
            public TableCell<Article, Void> call(final TableColumn<Article, Void> param) {
                final TableCell<Article, Void> cell = new TableCell<Article, Void>() {
                    private final Button btn = new Button("Retirer");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Article article = getTableView().getItems().get(getIndex());
                            BigDecimal montant = article.getPrix().multiply(new BigDecimal(article.getQuantite()));
                            commandeTable.getItems().remove(getIndex());
                            commandes.remove(getIndex());

                            BigDecimal total = new BigDecimal(montantTotal.getText()).subtract(montant);
                            montantTotal.setText(total.toString());
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

        ObservableList<String> list = FXCollections.observableArrayList("Carte de crédit", "Espèces");
        modePaiementBox.setItems(list);

        montantTotal.textProperty().addListener((observable, oldValue, newValue) -> {
            BigDecimal total = new BigDecimal(newValue);
            validerCommande.setDisable(total.compareTo(BigDecimal.ZERO) <= 0);
        });
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
        try {
            int articleId = Integer.parseInt(articleField.getText());
            List<Article> articles = (articleId > 0) ?
                clientService.rechercherArticlesParId(articleId)
                :
                clientService.consulterStock();
            articleTable.getItems().clear();
            if (articles != null && !articles.isEmpty()) {
                articleTable.getItems().addAll(articles);
            }
        } catch (NumberFormatException e) {
            showError("ID d'article invalide", "Veuillez entrer un ID d'article valide.");
        }
    }

    @FXML
    private void submitCommande(ActionEvent event) {
        String client = clientField.getText();
        String modePaiement = modePaiementBox.getSelectionModel().getSelectedItem();

        if (modePaiement == null || modePaiement.isEmpty()) {
            showError("Mode de paiement manquant", "Veuillez sélectionner un mode de paiement.");
            return;
        }

        if (clientService.passerCommande(commandes, client, modePaiement)) {
            showInfo("Succès", "Commande passée avec succès.");
            // Vider la table de commandes et réinitialiser le montant total
            commandeTable.getItems().clear();
            commandes.clear();
            montantTotal.setText("0");
        } else {
            showError("Échec de la commande", "Votre commande ne s'est pas enregistrée.");
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

            // Change la scène du stage
            stage.setScene(commandesScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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
