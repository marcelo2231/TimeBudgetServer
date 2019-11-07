package timebudget.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import com.sun.net.httpserver.HttpExchange;

import timebudget.ServerFacade;
import timebudget.TBSerializer;
import timebudget.log.Corn;
import timebudget.model.DateTimeRange;
import timebudget.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;


public class GetMetricsReportHandler extends HandlerBase {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		Corn.log(Level.FINEST, "Get Metrics Report Handler");
		try {
			String token = getAuthenticationToken(httpExchange);

			String reqBody = getRequestBody(httpExchange);
			if(reqBody == null || reqBody.isEmpty()) {
				httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
				return;
			}
			
			DateTimeRange dtr = (DateTimeRange)TBSerializer.jsonToObj(reqBody, DateTimeRange.class);

			Map<Integer, Float> result = ServerFacade.getInstance().getReport(new User(token), dtr);

			Map<Integer, Float> temp = new HashMap<>();
			temp.put(0, 1.5f);
			temp.put(1, 9f);
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

			// sendResponseBody(httpExchange, temp);
			sendResponseBody(httpExchange, result);
		} catch(Exception e){
			Corn.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			//sendResponseBody(httpExchange, e.getStackTrace());
		}
	}
}
