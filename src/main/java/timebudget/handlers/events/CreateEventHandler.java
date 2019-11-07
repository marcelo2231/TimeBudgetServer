package timebudget.handlers.events;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;

import com.sun.net.httpserver.HttpExchange;

import timebudget.ServerFacade;
import timebudget.TBSerializer;
import timebudget.exceptions.BadEventException;
import timebudget.exceptions.BadUserException;
import timebudget.handlers.HandlerBase;
import timebudget.log.Corn;
import timebudget.model.Event;
import timebudget.model.User;


public class CreateEventHandler extends HandlerBase {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		Corn.log(Level.FINEST, "Create Event Handler");
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

			Event eventInfo = (Event)TBSerializer.jsonToObj(reqBody, Event.class);

			if(eventInfo.getCategoryID() == -1 || eventInfo.getDescription() == null ||
				eventInfo.getStartAt() == -1 ||
				eventInfo.getEndAt() == -1){
				throw new BadEventException("CategoryID, Description, userID, startAt or endAt was null!");
			}
			
			Event results = ServerFacade.getInstance().createEvent(new User(token), eventInfo);
			
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			sendResponseBody(httpExchange, results);
		} catch(Exception e){
			Corn.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
		}
	}
}
