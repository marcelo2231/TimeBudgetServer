package timebudget.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;

import com.sun.net.httpserver.HttpExchange;

import timebudget.ServerFacade;
import timebudget.handlers.HandlerBase;
import timebudget.log.Corn;
import timebudget.model.Event;
import timebudget.model.User;


public class FakeItHandler extends HandlerBase {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		Corn.log(Level.FINEST, "Fake It Handler");
		try {
			String reqBody = getRequestBody(httpExchange);
			if(reqBody == null || reqBody.isEmpty()) {
				httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
				return;
			}

			
		Boolean pass = true;

		User u = new User(User.NO_USER_ID, "test_user", "test@gmail.com","password", User.NO_CREATED_AT);
		u = ServerFacade.getInstance().register(u);

		// books events
		Event ev1 = new Event(Event.NO_EVENT_ID, 3, "Reading War and Peace", u.getUserID(), 3600, 3600 * 3);
		Event ev2 = new Event(Event.NO_EVENT_ID, 3, "Reading Anna Karenina", u.getUserID(), 3600 * 3, 3600 * 9);

		// amusement events
		Event ev3 = new Event(Event.NO_EVENT_ID, 6, "Watching The Office", u.getUserID(), 3600 * 10, 3600 * 11);
		Event ev4 = new Event(Event.NO_EVENT_ID, 6, "Watching Stranger Thangs", u.getUserID(), 3600 * 12, 3600 * 15);
		
		Event[] events = {ev1, ev2, ev3, ev4};
		for (Event e : events)
			ServerFacade.getInstance().createEvent(u, e);


		httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		sendResponseBody(httpExchange, u);	
		} catch(Exception e){
			Corn.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
		}
	}
}
