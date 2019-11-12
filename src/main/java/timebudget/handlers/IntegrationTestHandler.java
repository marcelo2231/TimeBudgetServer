package timebudget.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

import com.sun.net.httpserver.HttpExchange;

import timebudget.ServerFacade;
import timebudget.exceptions.UserCreationException;
import timebudget.handlers.HandlerBase;
import timebudget.log.Corn;
import timebudget.model.Category;
import timebudget.model.Event;
import timebudget.model.User;
import timebudget.model.request.LoginRequest;


public class IntegrationTestHandler extends HandlerBase {

	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		Corn.log(Level.FINEST, "Integration Test Handler");
		try {
			new FakeItHandler().handle(null);
			
			User u = ServerFacade.getInstance().login(new LoginRequest("test_user", "password"));
			
			int schoolCategoryID = -1;
			int amusementCategoryID = -1;
			int healthCategoryID = -1;
			List<Category> categories = ServerFacade.getInstance().getAllActiveCategories(u);

			for (Category c: categories) {
				if (c.getDescription().equals("Amusement"))
					amusementCategoryID = c.getCategoryID();
				
				if (c.getDescription().equals("School"))
					schoolCategoryID = c.getCategoryID();

				if (c.getDescription().equals("Health/Wellness"))
					healthCategoryID = c.getCategoryID();
			}

			// books events
			Event ev1 = new Event(Event.NO_EVENT_ID, schoolCategoryID, "Reading War and Peace", u.getUserID(), 3600, 3600 * 3);
			Event ev2 = new Event(Event.NO_EVENT_ID, schoolCategoryID, "Reading Anna Karenina", u.getUserID(), 3600 * 3, 3600 * 9);

			// amusement events
			Event ev3 = new Event(Event.NO_EVENT_ID, amusementCategoryID, "Watching The Office", u.getUserID(), 3600 * 10, 3600 * 11);
			Event ev4 = new Event(Event.NO_EVENT_ID, amusementCategoryID, "Watching Stranger Thangs", u.getUserID(), 3600 * 12, 3600 * 15);

			// health events
			Event ev5 = new Event(Event.NO_EVENT_ID, healthCategoryID, "Work out", u.getUserID(), 3600 * 15, 3600 * 17);

			Event[] events = {ev1, ev2, ev3, ev4, ev5};
			for (Event e : events)
				ServerFacade.getInstance().createEvent(u, e);

			// any way to call JUnit tests right now?

			// invoke test 1
			

			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

			// send the full user
			// UserDetails response = new UserDetails(u);
			Map<String, String> response = new HashMap<>();
			response.put("result", "tests passed");
			sendResponseBody(httpExchange, response, false);
		} catch(Exception e){
			Corn.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
		}
	}
}
