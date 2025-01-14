import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner userInput = new Scanner(System.in);

            System.out.println(in.nextLine());
            String username = userInput.nextLine();
            out.println(username);

            Thread readThread = new Thread(() -> {
                while (in.hasNextLine()) {
                    System.out.println(in.nextLine());
                }
            });
            readThread.start();
            while (userInput.hasNextLine()) {
                String message = userInput.nextLine();
                if (message.startsWith("@")) {
                    out.println(message);
                }
            }
            socket.close();
        } catch (Exception e) {
        }
    }
}