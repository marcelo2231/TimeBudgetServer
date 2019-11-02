package timebudget.handlers;

import com.sun.net.httpserver.HttpExchange;
import timebudget.log.Corn;
import timebudget.model.User;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;

public class RegisterHandler extends HandlerBase {
	
	
	public void handle(HttpExchange httpExchange) throws IOException {
		Corn.log(Level.FINEST, "Register Handler");
		try {
			String reqBody = getRequestBody(httpExchange);
			if(reqBody == null || reqBody.isEmpty()) {
				httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
				return;
			}
			//User userInfo = Serializer.getInstance().deserializeUser(reqBody);
			
			
			
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			//sendResponseBody(httpExchange, results);
		} catch(Exception e) {
			Corn.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
		}
	}
}
