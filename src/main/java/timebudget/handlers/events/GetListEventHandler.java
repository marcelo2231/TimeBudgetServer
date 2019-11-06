package timebudget.handlers.events;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.Time;
import java.util.List;
import java.util.logging.Level;

import com.sun.net.httpserver.HttpExchange;

import timebudget.ServerFacade;
import timebudget.TBSerializer;
import timebudget.exceptions.BadEventException;
import timebudget.handlers.HandlerBase;
import timebudget.log.Corn;
import timebudget.model.Event;
import timebudget.model.TimePeriod;
import timebudget.model.User;


public class GetListEventHandler extends HandlerBase {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		Corn.log(Level.FINEST, "Get Event List Handler");
		try {
			String token = getAuthenticationToken(httpExchange);
			String reqBody = getRequestBody(httpExchange);
			if(reqBody == null || reqBody.isEmpty()) {
				httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
				return;
			}
			
			TimePeriod timePeriod = (TimePeriod)TBSerializer.jsonToObj(reqBody, TimePeriod.class);
			
			if(timePeriod == null || timePeriod.getStartAt() == TimePeriod.NO_START_AT || timePeriod.getEndAt() == TimePeriod.NO_END_AT){
				throw new BadEventException("EventID or time period was null!");
			}
			
			List<Event> results = ServerFacade.getInstance().getEventList(new User(token), timePeriod);
			
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			sendResponseBody(httpExchange, results);
		} catch(Exception e){
			Corn.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
		}
	}
}
