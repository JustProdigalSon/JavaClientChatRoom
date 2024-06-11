package chatroom.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main{
    public Main() {
    }

    public static void main(String[] args){
        String host = args[0];
        Integer port = Integer.valueOf(args[1]);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Username: ... ");
        String username = scanner.next();

        try {
            Socket socket = new Socket(host,port);
            Client client = new Client(socket, username);
            client.listener();
            client.postman();
        }catch (RuntimeException | IOException e){
            System.out.println("--------XXXXX--------[SERVER OFFLINE]--------XXXXX--------");
            throw new RuntimeException(e);

        }
    }
}