package timebudget;

import com.sun.net.httpserver.HttpServer;

import org.apache.commons.cli.*;
import timebudget.database.DAOFactory;
import timebudget.handlers.DefaultHandler;
import timebudget.handlers.FakeItHandler;
import timebudget.handlers.GetMetricsReportHandler;
import timebudget.handlers.LoginHandler;
import timebudget.handlers.RegisterHandler;
import timebudget.handlers.categories.GetAllCategoriesHandler;
import timebudget.handlers.categories.GetCategoryByIdHandler;
import timebudget.handlers.events.CreateEventHandler;
import timebudget.handlers.events.DeleteEventHandler;
import timebudget.handlers.events.EditEventHandler;
import timebudget.handlers.events.GetEventByIdHandler;
import timebudget.handlers.events.GetListEventHandler;
import timebudget.log.Corn;


import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerCommunicator {
	
	private HttpServer server;
	
	private final int MAX_WAITING_CONNECTIONS = 12;
	
	/**
	 * Creates an HttpServer on provided port
	 *
	 * @param port used as the post the timebudget.server listens on.
	 */
	public ServerCommunicator(String port, int maxDeltas){
		Corn.log("Initializing HTTP Server");
		try {
			server = HttpServer.create(new InetSocketAddress(Integer.parseInt(port)), MAX_WAITING_CONNECTIONS);
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		server.setExecutor(null); // use the default executor
		
		//Set the DAOFactory in the timebudget.server facade before contexts use the ServerFacade.
		ServerFacade.daoFactory = new DAOFactory();
		ServerFacade.max_commands = maxDeltas;
		ServerFacade.getInstance(); //Loads the database stuff
		
		Corn.log("Creating contexts");
		createContexts();
		
		Corn.log("Starting timebudget.server on port: " + port);
		server.start();
	}
	
	private void createContexts() {
		server.createContext(IServer.USER_LOGIN, new LoginHandler());
		server.createContext(IServer.USER_REGISTER, new RegisterHandler());
		server.createContext(IServer.DEFAULT, new DefaultHandler());
		server.createContext(IServer.EVENT_CREATE, new CreateEventHandler());
		server.createContext(IServer.EVENT_EDIT, new EditEventHandler());
		server.createContext(IServer.EVENT_DELETE, new DeleteEventHandler());
		server.createContext(IServer.EVENT_GET_LIST, new GetListEventHandler());
		server.createContext(IServer.EVENT_GET_BY_ID, new GetEventByIdHandler());
		server.createContext(IServer.REPORT_GET_TIME_METRICS, new GetMetricsReportHandler());
		server.createContext(IServer.FAKE_IT, new FakeItHandler());
		server.createContext(IServer.CATEGORIES_GET_ACTIVE, new GetAllCategoriesHandler());
		server.createContext(IServer.CATEGORIES_GET_BY_ID, new GetCategoryByIdHandler());
	}
	
	
	public static void main(String[] args) {
		Options options = new Options();
		
		Option portOpt = new Option("p", "port", true, "timebudget.server host port");
		portOpt.setRequired(false);
		options.addOption(portOpt);
		
		Option deltaOpt = new Option("d", "delta", true, "number of commands to be saved");
		deltaOpt.setRequired(false);
		options.addOption(deltaOpt);
		
		Option logFileOpt = new Option("l", "log", true, "log file name with .log extension");
		logFileOpt.setRequired(false);
		options.addOption(logFileOpt);
		
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;
		
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("utility-name", options);
			
			System.exit(1);
			return;
		}
		
		//Defaults
		int delta = -1;
		String port = "8080";
		String logFile = "timebudget.server.log";
		
		//Get arguments
		if(cmd.getOptionValue("port") != null)
			port = cmd.getOptionValue("port");
		
		String deltaStr = cmd.getOptionValue("delta");
		if(deltaStr != null)
			delta = Integer.parseInt(deltaStr);
		if(cmd.getOptionValue("log") != null && cmd.getOptionValue("log").endsWith(".log"))
			logFile = cmd.getOptionValue("log");
		System.out.println("Log File: " + logFile);
		//Create Server and Log
		new Corn(logFile);
		new ServerCommunicator(port, delta);
	}
}
