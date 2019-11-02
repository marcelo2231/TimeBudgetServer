package timebudget.handlers.events;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;

import com.sun.net.httpserver.HttpExchange;

import timebudget.ServerFacade;
import timebudget.TBSerializer;
import timebudget.exceptions.BadEventException;
import timebudget.handlers.HandlerBase;
import timebudget.log.Corn;
import timebudget.model.Event;


public class DeleteEventHandler extends HandlerBase {
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		Corn.log(Level.FINEST, "Delete Event Handler");
		try {
			String reqBody = getRequestBody(httpExchange);
			if(reqBody == null || reqBody.isEmpty()) {
				httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
				return;
			}

			Event eventInfo = (Event)TBSerializer.jsonToObj(reqBody, Event.class);
			
			if(eventInfo.getEventID() == -1){
				throw new BadEventException("EventID was null!");
			}
			
			Boolean results = ServerFacade.getInstance().deleteEvent(eventInfo);
			
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			sendResponseBody(httpExchange, results);
		} catch(Exception e){
			Corn.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
		}
	}
}
