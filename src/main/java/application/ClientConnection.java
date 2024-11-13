// ClientConnection.java
package application;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    //connection
    public ClientConnection(String host, int port) {
        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());//get
            in = new ObjectInputStream(socket.getInputStream());//send
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("failed to connect to server");
        }
    }
//send request
    public String sendRequestWithOneParameter(String requestType, String parameter) {
        try {
            out.writeObject(requestType);
            out.writeObject(parameter);
            out.flush();
            return (String) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("failed to send request in one parameters");
            return "Error in communication with server";
        }
    }
    public String sendRequestWithTwoParameter(String requestType, String parameter1, String parameter2) {
        try {
            out.writeObject(requestType);
            out.writeObject(parameter1);
            out.writeObject(parameter2);
            out.flush();
            return (String) in.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error in communication with server";
        }
    }
    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String sendRequestToSetUserInformation(String requestType, String username, String encryptedPassword, String email, int indexQuestoin, String answer, String generatedKey) {
        try {
            out.writeObject(requestType);
            out.writeObject(username);
            out.writeObject(encryptedPassword);
            out.writeObject(email);
            out.writeObject(indexQuestoin);
            out.writeObject(answer);
            out.writeObject(generatedKey);
            out.flush();
            return (String) in.readObject();
        } catch (Exception e) {
            System.out.println("failed to send request in user information store");
            return "Error in communication with server";
        }
    }
}
