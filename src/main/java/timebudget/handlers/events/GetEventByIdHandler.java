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


public class GetEventByIdHandler extends HandlerBase {
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		Corn.log(Level.FINEST, "Get Event By ID Handler");
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
			
			Event results = ServerFacade.getInstance().getEventByID(eventInfo.getEventID());
			
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			sendResponseBody(httpExchange, results);
		} catch(Exception e){
			Corn.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
		}
	}
}
