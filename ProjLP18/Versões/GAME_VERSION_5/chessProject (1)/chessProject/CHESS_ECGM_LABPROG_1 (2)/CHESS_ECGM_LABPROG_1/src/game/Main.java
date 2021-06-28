package game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


public class Main extends Application {
    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("view/prepare_screen.fxml"));
        primaryStage.setTitle("Jogo Xadrez");
        primaryStage.setScene(new Scene(root));
        
        primaryStage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
        primaryStage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }
}
