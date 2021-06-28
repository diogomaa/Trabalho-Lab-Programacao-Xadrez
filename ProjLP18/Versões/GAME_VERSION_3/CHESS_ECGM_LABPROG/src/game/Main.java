package game;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {
    
    
    
    private static Stage stage;
    
    
    
   /* int secondspassed = 0;
    
    Timer myTimer = new Timer();
    TimerTask task = new TimerTask(){
            public void run(){
            secondspassed++;
            System.out.println(secondspassed);
            }
        };*/
   
  
    
    
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        
      /*myTimer.scheduleAtFixedRate(task, 1000, 1000);*/
      
      
      
        
        
        
        
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("view/prepare_screen.fxml"));
        primaryStage.setTitle("Jogo Xadrez");
        primaryStage.setScene(new Scene(root));
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

