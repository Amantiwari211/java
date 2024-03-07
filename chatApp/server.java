import java.io.*;
import java.net.*;

class server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    // constractor
    public server() {
        try {
            server = new ServerSocket(7777);
            System.out.println("Server is ready to accept connetion.");
            System.out.println("wating...");
            socket = server.accept();
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            startReading();
            startWritting();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startReading() {
        Runnable r1 = () -> {
            System.out.println("reader started..");
            try {
                while (true) {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("client terminated the chat.");
                        socket.close();
                        break;
                    }
                    System.out.println("server: " + msg);
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
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(r2).start();

    }

    public static void main(String[] args) {
        System.out.println("this is server.. going to start");
        new server();
    }
}