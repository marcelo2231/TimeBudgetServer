package timebudget.handlers;

import com.sun.net.httpserver.HttpExchange;
import timebudget.ServerFacade;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;

import timebudget.TBSerializer;
import timebudget.exceptions.BadUserException;
import timebudget.log.Corn;
import timebudget.model.User;
import timebudget.model.request.LoginRequest;

public class LoginHandler extends HandlerBase {
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		Corn.log(Level.FINEST, "Login Handler");
		try {
			String reqBody = getRequestBody(httpExchange);
			if(reqBody == null || reqBody.isEmpty()) {
				httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
				return;
			}
			
			LoginRequest userToLogin = (LoginRequest)TBSerializer.jsonToObj(reqBody, LoginRequest.class);
			
			if(userToLogin.getUsername() == null || userToLogin.getPassword() == null){
				throw new BadUserException("Username or Password was null");
			}
			
			User results = ServerFacade.getInstance().login(userToLogin);
			
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			sendResponseBody(httpExchange, results);
		} catch(Exception e){
			Corn.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
		}
	}
}
