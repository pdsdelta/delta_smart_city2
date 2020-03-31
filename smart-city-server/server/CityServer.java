package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CityServer {
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
 
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String jsonClient = in.readLine();
            if ("Bonjour CityServer".equals(jsonClient)) {
                out.println("Bonjour Chère client");
            }
            else {
                out.println("Je ne vous connais pas");
            }
        String toSend ="Réponse";
        out.write(toSend);
        out.flush();
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
    
    public static void main(String[] args) {
        CityServer server=new CityServer();
        try {
			server.start(7000);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
    }
}
