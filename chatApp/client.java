import java.net.*;
import java.io.*;

public class client {
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public client() {
        try {
            System.out.println("sending request to server.");
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("conection done.");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            startReading();
            startWritting();
        } catch (Exception e) {

        }
    }

    public void startReading() {
        Runnable r1 = () -> {
            System.out.println("reader started..");
            try {
                while (true) {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("server terminated the chat.");
                        socket.close();
                        break;
                    }
                    System.out.println("Client: " + msg);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(r1).start();

    }

    public void startWritting() {
        Runnable r2 = () -> {
            System.out.println("Writter started..");
            try {
                while (true && !socket.isClosed()) {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if (content.equals("exit")) {
                        socket.close();
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(r2).start();

    }

    public static void main(String[] args) {
        System.out.println("this is client.");
        new client();

    }
}
