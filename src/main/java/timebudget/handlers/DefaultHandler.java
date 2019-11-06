package timebudget.handlers;

import com.sun.net.httpserver.HttpExchange;
import timebudget.ServerFacade;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class DefaultHandler extends HandlerBase {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
        // Create website path
        String filePathStr = "plugins/web/index.html";
        Path filePath = FileSystems.getDefault().getPath(filePathStr);

        // Send website as the response body
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        Files.copy(filePath, httpExchange.getResponseBody());
        httpExchange.getResponseBody().close();
	}
}
