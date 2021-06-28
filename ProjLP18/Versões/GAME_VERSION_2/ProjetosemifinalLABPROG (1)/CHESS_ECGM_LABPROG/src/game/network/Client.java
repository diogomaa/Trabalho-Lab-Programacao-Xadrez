package game.network;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



public class Client {
    private String ip;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private PrintWriter givupp;
    private DataReceiveListener mListener;
  

    public Client(String ip) {
        this.ip = ip;
        try {
            socket = new Socket(ip, 5000);
            InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(isReader);
            writer = new PrintWriter(socket.getOutputStream());
            givupp = new PrintWriter(socket.getOutputStream());
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
                } else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onChatReceive(s);
                            mListener.onGiveUpReceive(s);
                        }
                    });
                }
            
                }
        }
    }

    public interface DataReceiveListener {
        void onChatReceive(String message);
        void onMoveReceive(String move);
        void onGiveUpReceive(String messages);
    }
}
