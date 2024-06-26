import javafx.application.Application; // Import de la classe Application de JavaFX
import javafx.stage.Stage; // Import de la classe Stage de JavaFX

public class Main extends Application { // Main doit étendre Application

    @Override // Indique que cette méthode redéfinit une méthode de la superclasse
    public void start(Stage primaryStage) { // Méthode obligatoire pour JavaFX
        primaryStage.setTitle("Hello World");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Méthode pour démarrer l'application JavaFX
    }
}
