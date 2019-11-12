package timebudget.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
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


public class FakeItHandler extends HandlerBase {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		Corn.log(Level.FINEST, "Fake It Handler");
		try {
			// String reqBody = getRequestBody(httpExchange);
			// if(reqBody == null || reqBody.isEmpty()) {
			// 	httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
			// 	return;
			// }

			User u = new User(User.NO_USER_ID, "test_user", "test@gmail.com","password", User.NO_CREATED_AT);
			try {
				u = ServerFacade.getInstance().register(u);
			} catch (UserCreationException e) {
				Random r = new Random();
				u.setUsername("test_user" + String.valueOf(r.nextInt(10000)));
				u = ServerFacade.getInstance().register(u);
			}

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


			if (httpExchange != null) {
				httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				// send the full user
				// UserDetails response = new UserDetails(u);
				sendResponseBody(httpExchange, u, false);
			}
		} catch(Exception e){
			Corn.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
		}
	}
}
