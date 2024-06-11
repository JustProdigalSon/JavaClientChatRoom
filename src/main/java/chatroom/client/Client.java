

package chatroom.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket socket;
    InputStreamReader inPut;
    OutputStreamWriter outPut;
    BufferedReader bufferedIn;
    BufferedWriter bufferedBroadcast;
    String username;

    public Client(Socket socket, String username) {
        try {
            this.username = username;
            this.socket = socket;
            this.inPut = new InputStreamReader(socket.getInputStream());
            this.outPut = new OutputStreamWriter(this.socket.getOutputStream());
            this.bufferedIn = new BufferedReader(this.inPut);
            this.bufferedBroadcast = new BufferedWriter(this.outPut);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    public void postman() {
        try {
            bufferedBroadcast.write(username);
            bufferedBroadcast.newLine();
            bufferedBroadcast.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String msg = scanner.nextLine();
                this.bufferedBroadcast.write("username(" + username + "): " + msg);
                this.bufferedBroadcast.newLine();
                this.bufferedBroadcast.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String incomingMsg;
                while (socket.isConnected()) {
                    try {
                        incomingMsg = bufferedIn.readLine();
                        System.out.println(incomingMsg);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }).start();

    }
}