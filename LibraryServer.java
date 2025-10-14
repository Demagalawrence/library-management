import java.io.*;
import java.net.*;
import java.util.*;
import com.sun.net.httpserver.*;

public class LibraryServer {
    private static List<String> books = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Serve homepage
        server.createContext("/", exchange -> {
            File file = new File("index.html");
            byte[] response = java.nio.file.Files.readAllBytes(file.toPath());
            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.close();
        });

        // Add Book
        server.createContext("/addBook", exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();

                Map<String, String> params = new HashMap<>();
                for (String pair : formData.split("&")) {
                    String[] keyValue = pair.split("=");
                    params.put(keyValue[0], URLDecoder.decode(keyValue[1], "UTF-8"));
                }

                String book = String.format("%s by %s (%s)", params.get("title"), params.get("author"),
                        params.get("year"));
                books.add(book);
            }

            String response = "<html><body><h3>Book added successfully!</h3><a href='/'>Back</a></body></html>";
            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });

        // View all books
        server.createContext("/books", exchange -> {
            StringBuilder response = new StringBuilder("<html><body><h2>Books in Library:</h2><ul>");
            for (String b : books)
                response.append("<li>").append(b).append("</li>");
            response.append("</ul><a href='/'>Back</a></body></html>");

            exchange.getResponseHeaders().add("Content-Type", "text/html");
            byte[] respBytes = response.toString().getBytes();
            exchange.sendResponseHeaders(200, respBytes.length);
            exchange.getResponseBody().write(respBytes);
            exchange.close();
        });

        server.start();
        System.out.println("Server started at http://localhost:8080");
    }
}
