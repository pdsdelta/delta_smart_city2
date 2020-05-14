package functionality_server;

import java.awt.event.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

import javax.swing.*;

import java.awt.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import city.city;
import connectionPool.DataSource;
import user.Users;


public class functionalityServer{
	
	private ServerSocket serverSocket;
	
	public void start(int port) throws IOException, ClassNotFoundException, SQLException {
       
		serverSocket = new ServerSocket(port);
        while(true) {
        	new threadFunctionality(serverSocket.accept()).start();
        }


    }
	
    public void stop() throws IOException {
        serverSocket.close();
    }
	
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
    	try {
    		System.out.println("Je suis en écoute");
    		functionalityServer server = new functionalityServer();
    		server.start(2400);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    
    }
}

