package timebudget.handlers.events;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.Time;
import java.util.List;
import java.util.logging.Level;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;

import timebudget.ServerFacade;
import timebudget.TBSerializer;
import timebudget.exceptions.BadEventException;
import timebudget.exceptions.BadUserException;
import timebudget.handlers.HandlerBase;
import timebudget.log.Corn;
import timebudget.model.DateTimeRange;
import timebudget.model.Event;
import timebudget.model.EventList;
import timebudget.model.TimePeriod;
import timebudget.model.User;
import timebudget.model.request.EventListRequest;


public class GetListEventHandler extends HandlerBase {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		Corn.log(Level.FINEST, "Get Event List Handler");
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

			EventListRequest elr = (EventListRequest)TBSerializer.jsonToObj(reqBody, EventListRequest.class);

			if(elr == null)
				throw new BadEventException("Invalid request!");
			
			DateTimeRange dtr = new DateTimeRange(elr.getStartAt(), elr.getEndAt());

			if (dtr.getStartAt() == DateTimeRange.NO_START_AT || dtr.getEndAt() == DateTimeRange.NO_END_AT)
				throw new BadEventException("EventID or time period was null!");

			EventList results = ServerFacade.getInstance().getEventListOneCategory(new User(token), dtr, elr.getCategoryID());

			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			
			sendResponseBody(httpExchange, results, true);
		} catch(Exception e){
			Corn.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
		}
	}
}
