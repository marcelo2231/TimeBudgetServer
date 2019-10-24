package timebudget.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import com.sun.net.httpserver.HttpExchange;
import timebudget.handlers.HandlerBase;


public class GetMetricsReportHandler extends HandlerBase {
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		//Corn.log(Level.FINEST, "Login Handler");
		try {
			String reqBody = getRequestBody(httpExchange);
			if(reqBody == null || reqBody.isEmpty()) {
				httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
				return;
			}
			
			
			//DO STUFF HERE
			
			
			
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			//sendResponseBody(httpExchange, results);
		} catch(Exception e){
			//Corn.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
		}
	}
}
