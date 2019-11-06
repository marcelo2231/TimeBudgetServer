package timebudget.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import com.sun.net.httpserver.HttpExchange;

import timebudget.ServerFacade;
import timebudget.log.Corn;
import timebudget.model.DateTimeRange;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;


public class GetMetricsReportHandler extends HandlerBase {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		Corn.log(Level.FINEST, "Get Metrics Report Handler");
		try {
			String reqBody = getRequestBody(httpExchange);
			if(reqBody == null || reqBody.isEmpty()) {
				httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
				return;
			}
			
			// need to parse for the auth token, get the user id, and the date range
			
			// User u = 
			// DateTimeRange range = new DateTimeRange(Integer.valueOf(start_at), Integer.valueOf(end_at));
			
			// Map<Integer, Float> results = ServerFacade.getInstance().getReport(u, range);

			Map<Integer, Float> temp = new HashMap<>();
			temp.put(0, 1.5f);
			temp.put(1, 9f);
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			sendResponseBody(httpExchange, temp);
			//sendResponseBody(httpExchange, results);
		} catch(Exception e){
			Corn.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
		}
	}
}
