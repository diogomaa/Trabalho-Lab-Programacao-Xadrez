package game.network;

import game.controller.MultiplayerModeController;
import static game.controller.MultiplayerModeController.wantnodraw;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;



public class Client {
    private String ip;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private PrintWriter givupp;
    private PrintWriter draww;
    private PrintWriter winmessages;
    private PrintWriter pawnasks;
    public static Client Instance;
    
    private DataReceiveListener mListener;
  

    public Client(String ip) {
        Instance= this;
        this.ip = ip;
        try {
            socket = new Socket(ip, 5000);
            InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(isReader);
            writer = new PrintWriter(socket.getOutputStream());
            givupp = new PrintWriter(socket.getOutputStream());
            draww = new PrintWriter(socket.getOutputStream());
            winmessages = new PrintWriter(socket.getOutputStream());
            pawnasks = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnDataReceiveListener(DataReceiveListener listener) {
        this.mListener = listener;
    }

    public void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }

    public String receiveMessage() {
        String result = null;
        try {
            result = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void startDataThread() {
        Thread t = new Thread(new IncomingDataReader());
        t.start();
    }

    public void closeConnection(String messages) {
        givupp.println(messages);
        givupp.flush();
        try {
            socket.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
      public void draw(String messagess) {
         draww.println(messagess);
        draww.flush();
    
    }
   
        
        public void winyes(String winmessage) {
         winmessages.println(winmessage);
        winmessages.flush();
     
    }
         public void pawnask(String pawnmessage) {
         pawnasks.println(pawnmessage);
        pawnasks.flush();
     
    }
    
    private class IncomingDataReader implements  Runnable {

        @Override
        public void run() {
            String message;
            while ((message = receiveMessage()) != null) {
              
                final String s = message;
                if (message.contains("_")) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onMoveReceive(s);
                          
                        }
                    });
                } else if(message.contains("desistiu!")){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            
                            mListener.onChatReceive(s);
                            mListener.onGiveUpReceive(s);
                        }
                    });
                }else if(message.contains("Pedido de Empate")){
                    if(MultiplayerModeController.iwantdraw==true){
                    MultiplayerModeController.iwantdraw=false;
                    }else{
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onChatReceive(s);
                            mListener.onDrawReceive(s);
                        }
                    });}
                    
                }else if(message.contains(" aceitou o Empate!")){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onChatReceive(s);
                            mListener.onyesdrawReceive(s);
                        }
                    });
                }else if(message.contains("REJEITADO!")){
                       if(MultiplayerModeController.wantnodraw==true){
                    MultiplayerModeController.wantnodraw=false;
                    }else{
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onChatReceive(s);
                            mListener.onnodrawReceive(s);
                        }
                    });}
                }else if(message.contains("Adversário ganhou o jogo!")){
                       if(MultiplayerModeController.wantnodraw==true){
               
                    }else{
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onChatReceive(s);
                            mListener.onwinReceive(s);
                        }
                    });}
                }else if(message.contains("Adversário troca peão por outra peça.")){
                   
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onChatReceive(s);
                            mListener.onPawnAskReceive(s);
                        }
                    });
                }else{
                     mListener.onChatReceive(s);
                }
            
                }
        }
    }

    public interface DataReceiveListener {
        void onChatReceive(String message);
        void onMoveReceive(String move);
        void onGiveUpReceive(String messages);
        void onDrawReceive(String messagess);
        void onyesdrawReceive(String messagess);
        void onnodrawReceive(String messagessss);
        void onwinReceive(String winmessage);
        void onPawnAskReceive(String pawnmessage);
    }
}
