package com.example.timebudgetserver.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/*********
 * DefaultHandler: Handles default api calls by sending the caller to a website.
 *********/
public class DefaultHandler  implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        // Create website path
        String filePathStr = "libs/web/index.html";
        Path filePath = FileSystems.getDefault().getPath(filePathStr);

        // Send website as the response body
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        Files.copy(filePath, httpExchange.getResponseBody());
        httpExchange.getResponseBody().close();
    }
}