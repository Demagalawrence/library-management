import java.io.*;
import java.net.*;
import java.util.*;
import com.sun.net.httpserver.*;

public class LibraryServer {
    private static List<String> books = new ArrayList<>();
    private static List<String> members = new ArrayList<>();
    private static List<String> loans = new ArrayList<>();

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
                Map<String, String> params = parseForm(exchange.getRequestBody());
                String title = params.getOrDefault("title", "");
                String author = params.getOrDefault("author", "");
                String year = params.getOrDefault("year", "");

                if (!title.isEmpty() && !author.isEmpty() && !year.isEmpty()) {
                    String book = String.format("%s by %s (%s)", title, author, year);
                    books.add(book);
                }
            }

            String response = "<html><body><h3>Book added successfully!</h3><a href='/'><- Back</a></body></html>";
            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });

        // View all books
        server.createContext("/books", exchange -> {
            StringBuilder response = new StringBuilder("<html><body><h2>Books in Library:</h2><ul>");
            for (String b : books) {
                response.append("<li>").append(escapeHtml(b)).append("</li>");
            }
            response.append("</ul><a href='/'><- Back</a></body></html>");

            exchange.getResponseHeaders().add("Content-Type", "text/html");
            byte[] respBytes = response.toString().getBytes();
            exchange.sendResponseHeaders(200, respBytes.length);
            exchange.getResponseBody().write(respBytes);
            exchange.close();
        });

        // Add Member
        server.createContext("/addMember", exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                Map<String, String> params = parseForm(exchange.getRequestBody());
                String name = params.getOrDefault("name", "");
                String memberId = params.getOrDefault("memberId", "");

                if (!name.isEmpty() && !memberId.isEmpty()) {
                    String member = String.format("%s (ID: %s)", name, memberId);
                    members.add(member);
                }
            }

            String response = "<html><body><h3>Member added successfully!</h3><a href='/'><- Back</a></body></html>";
            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });

        // View all members
        server.createContext("/members", exchange -> {
            StringBuilder response = new StringBuilder("<html><body><h2>Library Members:</h2><ul>");
            for (String m : members) {
                response.append("<li>").append(escapeHtml(m)).append("</li>");
            }
            response.append("</ul><a href='/'><- Back</a></body></html>");

            exchange.getResponseHeaders().add("Content-Type", "text/html");
            byte[] respBytes = response.toString().getBytes();
            exchange.sendResponseHeaders(200, respBytes.length);
            exchange.getResponseBody().write(respBytes);
            exchange.close();
        });

        // Add Loan (Borrow)
        server.createContext("/borrow", exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                Map<String, String> params = parseForm(exchange.getRequestBody());
                String member = params.getOrDefault("member", "");
                String bookTitle = params.getOrDefault("bookTitle", "");

                if (!member.isEmpty() && !bookTitle.isEmpty()) {
                    String loan = String.format("%s borrowed '%s'", member, bookTitle);
                    loans.add(loan);
                }
            }

            String response = "<html><body><h3>Borrow record added!</h3><a href='/'><- Back</a></body></html>";
            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });

        // View all loans
        server.createContext("/loans", exchange -> {
            StringBuilder response = new StringBuilder("<html><body><h2>Borrowing Records:</h2><ul>");
            for (String l : loans) {
                response.append("<li>").append(escapeHtml(l)).append("</li>");
            }
            response.append("</ul><a href='/'><- Back</a></body></html>");

            exchange.getResponseHeaders().add("Content-Type", "text/html");
            byte[] respBytes = response.toString().getBytes();
            exchange.sendResponseHeaders(200, respBytes.length);
            exchange.getResponseBody().write(respBytes);
            exchange.close();
        });

        server.start();
        System.out.println("Server started at http://localhost:8080");
    }

    private static Map<String, String> parseForm(InputStream requestBody) throws IOException {
        InputStreamReader isr = new InputStreamReader(requestBody, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        StringBuilder bodyBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            bodyBuilder.append(line);
        }

        Map<String, String> params = new HashMap<>();
        String formData = bodyBuilder.toString();
        if (!formData.isEmpty()) {
            for (String pair : formData.split("&")) {
                String[] keyValue = pair.split("=", 2);
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = URLDecoder.decode(keyValue[1], "UTF-8");
                    params.put(key, value);
                }
            }
        }
        return params;
    }

    private static String escapeHtml(String input) {
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
