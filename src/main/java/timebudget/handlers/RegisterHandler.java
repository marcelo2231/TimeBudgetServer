package timebudget.handlers;

import com.sun.net.httpserver.HttpExchange;
import timebudget.ServerFacade;
import timebudget.TBSerializer;
import timebudget.exceptions.BadUserException;
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
			
			User userInfo = (User)TBSerializer.jsonToObj(reqBody, User.class);
			
			if(userInfo.getUsername() == null || userInfo.getPassword() == null || userInfo.getEmail() == null){
				throw new BadUserException("Username, Password or Email was null!");
			}
			
			User results = ServerFacade.getInstance().register(userInfo);
			
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			sendResponseBody(httpExchange, results);
		} catch(Exception e) {
			Corn.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
		}
	}
}
