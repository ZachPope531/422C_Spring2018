package assignment5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Critters.fxml"));
        primaryStage.setTitle("Critters");
        primaryStage.setScene(new Scene(root, 700, 420));
        primaryStage.show();
    }
}
