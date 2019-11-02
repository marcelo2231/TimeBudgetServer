package timebudget.handlers.events;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;

import com.sun.net.httpserver.HttpExchange;
import timebudget.handlers.HandlerBase;
import timebudget.log.Corn;


public class CreateEventHandler extends HandlerBase {
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		Corn.log(Level.FINEST, "Create Event Handler");
		try {
			String reqBody = getRequestBody(httpExchange);
			if(reqBody == null || reqBody.isEmpty()) {
				httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
				return;
			}
			
			
			//DO STUFF HERE
			
			
			
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			//sendResponseBody(httpExchange, results);
		} catch(Exception e){
			Corn.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
		}
	}
}
