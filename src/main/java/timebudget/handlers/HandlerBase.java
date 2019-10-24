package timebudget.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.List;

public abstract class HandlerBase implements HttpHandler {
	
	/**
	 * Retrive the authentication token from the headers
	 * @param exchange httpexchange object from the request
	 * @return authentication token string
	 */
	protected String getAuthenticationToken(HttpExchange exchange){
		String token = null;
		if(exchange.getRequestHeaders().containsKey("Authentication")) {
			List<String> result = exchange.getRequestHeaders().get("Authentication");
			if(result.size() > 0)
				token = result.get(0);
		}
		return token;
	}
	
	/**
	 * turns bits from requestbody into a string
	 * @param exchange http exchange from the request
	 * @return request body in string format
	 * @throws IOException
	 */
	protected String getRequestBody(HttpExchange exchange) throws IOException {
		InputStream is = exchange.getRequestBody();
		InputStreamReader reader = new InputStreamReader(is);
		StringBuilder sb = new StringBuilder();
		int ch;
		while((ch = reader.read()) != -1){
			sb.append((char)ch);
		}
		return sb.toString();
	}
	
	/**
	 * sends response body string by serializing and then writing the bits to the stream
	 * @param exchange httpexchange object from the request
	 * @param result results object to respond with
	 * @throws IOException
	 */
	protected void sendResponseBody(HttpExchange exchange, Object result) throws IOException {
		OutputStream os = exchange.getResponseBody();
		OutputStreamWriter writer = new OutputStreamWriter(os);
		//writer.write(Serializer.getInstance().serialize(result));
		writer.close();
		os.close();
	}
}

