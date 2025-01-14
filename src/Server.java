import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static Map<String, PrintWriter> clients = new HashMap<>();
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5000);
        while (true) {
            Socket socket = serverSocket.accept();
            Thread t = new Thread(new ClientHandler(socket));
            t.start();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private String username;
        private PrintWriter out;
        private Scanner in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println("введите имя");

                if (in.hasNextLine()) {
                    username = in.nextLine();
                    synchronized (clients) {
                        clients.put(username, out);
                    }
                }

                while (in.hasNextLine()) {
                    String clientMessage = in.nextLine();
                    System.out.println(username + clientMessage);

                    if (clientMessage.startsWith("@")) {
                        String[] parts = clientMessage.substring(1).split(" ", 2);
                        String recipient = parts[0];
                        String message = parts.length > 1 ? parts[1] : "";

                        synchronized (clients) {
                            PrintWriter recipientOut = clients.get(recipient);
                            if (recipientOut != null) {
                                recipientOut.println(username + " " + message);
                            } else {
                                out.println("пользователь с именем " + recipient + " не найден");
                            }
                        }
                    }
                }
            } catch (Exception e){}
        }
    }
}
