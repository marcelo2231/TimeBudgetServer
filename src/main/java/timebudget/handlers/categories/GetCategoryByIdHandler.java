package timebudget.handlers.categories;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;

import com.sun.net.httpserver.HttpExchange;

import timebudget.ServerFacade;
import timebudget.TBSerializer;
import timebudget.exceptions.BadUserException;
import timebudget.handlers.HandlerBase;
import timebudget.log.Corn;
import timebudget.model.Category;
import timebudget.model.User;
import timebudget.model.request.GenericIDRequest;


public class GetCategoryByIdHandler extends HandlerBase {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		Corn.log(Level.FINEST, "Get Category By ID Handler");
		try {
			String token = getAuthenticationToken(httpExchange);
			if (token == null) {
				Corn.log(Level.SEVERE, "Unable to retrieve user token!");
				throw new BadUserException("Unable to retrieve user token!");
			}

			String reqBody = getRequestBody(httpExchange);
			if(reqBody == null || reqBody.isEmpty()) {
				httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
				return;
			}

			int categoryId = ((GenericIDRequest)TBSerializer.jsonToObj(reqBody, GenericIDRequest.class)).getID();
			
			Category results = ServerFacade.getInstance().getCategoryByID(new User(token), categoryId);

			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			sendResponseBody(httpExchange, results);
		} catch(Exception e){
			Corn.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
		}
	}
}
