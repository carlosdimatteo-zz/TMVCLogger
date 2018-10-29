package tmvc.logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String className;
    private String methodName;
    private String level;
    private String message;

    public ClientHandler(Socket socket, String className, String methodName, String level, String message) {
        this.clientSocket = socket;
        this.className = className;
        this.methodName = methodName;
        this.level = level;
        this.message = message;
    }

    public void run() {
        try {

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            out.println(timeStamp + "-- Class :" + this.className + "-- Method: " + this.methodName + " -- Level: " + this.level
                    + "-- Message: " + this.message);
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }


    }
}
