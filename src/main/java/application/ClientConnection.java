package application;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    // Constructor: Establishes connection to the server
    public ClientConnection(String host, int port) {
        try {
            socket = new Socket(host, port); // Connect to the server
            out = new ObjectOutputStream(socket.getOutputStream()); // Output stream
            in = new ObjectInputStream(socket.getInputStream()); // Input stream
        } catch (IOException e) {
            System.err.println("Failed to connect to server at " + host + ":" + port);
            e.printStackTrace();
        }
    }

    // Sends a request with one parameter and retrieves a response
    public String sendRequestWithOneParameter(String requestType, String parameter) {
        try {
            if (socket == null || socket.isClosed()) {
                return "Error: Connection to server is not established.";
            }
            out.writeObject(requestType); // Send request type
            out.writeObject(parameter); // Send parameter
            out.flush();
            return (String) in.readObject(); // Read response
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return "Error in communication with server.";
        }
    }

    // Sends a request with two parameters and retrieves a response
    public String sendRequestWithTwoParameter(String requestType, String parameter1, String parameter2) {
        try {
            if (socket == null || socket.isClosed()) {
                return "Error: Connection to server is not established.";
            }
            out.writeObject(requestType); // Send request type
            out.writeObject(parameter1); // Send first parameter
            out.writeObject(parameter2); // Send second parameter
            out.flush();
            return (String) in.readObject(); // Read response
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return "Error in communication with server.";
        }
    }

    // Sends a request to set user information and retrieves a response
    public String sendRequestToSetUserInformation(String requestType, String username, String encryptedPassword, String email, int indexQuestion, String answer) {
        try {
            if (socket == null || socket.isClosed()) {
                return "Error: Connection to server is not established.";
            }
            out.writeObject(requestType); // Send request type
            out.writeObject(username); // Send username
            out.writeObject(encryptedPassword); // Send encrypted password
            out.writeObject(email); // Send email
            out.writeObject(String.valueOf(indexQuestion)); // Send index of question
            out.writeObject(answer); // Send answer
            out.flush();
            return (String) in.readObject(); // Read response
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to send request for user information storage.");
            e.printStackTrace();
            return "Error in communication with server.";
        }
    }

    // Closes the connection and releases resources
    public void close() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.err.println("Error closing client connection.");
            e.printStackTrace();
        }
    }

    // Utility: Checks if the connection to the server is active
    public boolean isConnected() {
        return socket != null && !socket.isClosed();
    }
}
