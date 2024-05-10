package view;

import database.DatabaseHandler;

public class App {
	    public static void main(String[] args) {
	        DatabaseHandler dbHandler = new DatabaseHandler();
	       
	        LoginScreen loginScreen = new LoginScreen(dbHandler);
	    	
	    }
	}


