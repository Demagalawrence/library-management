import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.util.*;
import com.sun.net.httpserver.*;

public class LibraryServer {
    private static List<Book> books = new ArrayList<>();
    private static List<Member> members = new ArrayList<>();
    private static List<Loan> loans = new ArrayList<>();

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
                    try {
                        int yearInt = Integer.parseInt(year);
                        String id = UUID.randomUUID().toString();
                        Book book = new Book(id, title, author, yearInt);
                        books.add(book);
                    } catch (NumberFormatException e) {
                        // ignore invalid year for now
                    }
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
            for (Book b : books) {
                response.append("<li>").append(escapeHtml(b.toString())).append("</li>");
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
                    Member member = new Member(memberId, name);
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
            for (Member m : members) {
                response.append("<li>").append(escapeHtml(m.toString())).append("</li>");
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
                    LocalDate today = LocalDate.now();
                    LocalDate dueDate = today.plusDays(14);
                    Loan loan = new Loan(member, bookTitle, today, dueDate);
                    loans.add(loan);
                }
            }

            String response = "<html><body><h3>Borrow record added!</h3><a href='/'><- Back</a></body></html>";
            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });

        // Mark loan as returned
        server.createContext("/return", exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                Map<String, String> params = parseForm(exchange.getRequestBody());
                String indexParam = params.getOrDefault("loanIndex", "");
                try {
                    int idx = Integer.parseInt(indexParam);
                    if (idx >= 0 && idx < loans.size()) {
                        loans.get(idx).markReturned();
                    }
                } catch (NumberFormatException e) {
                    // ignore invalid index
                }
            }

            String response = "<html><body><h3>Loan marked as returned (if index was valid).</h3><a href='/loans'><- Back to loans</a></body></html>";
            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });

        // View all loans
        server.createContext("/loans", exchange -> {
            StringBuilder response = new StringBuilder("<html><body><h2>Borrowing Records:</h2><ul>");
            LocalDate today = LocalDate.now();
            for (int i = 0; i < loans.size(); i++) {
                Loan l = loans.get(i);
                String status;
                if (l.isReturned()) {
                    status = "returned";
                } else if (l.isOverdue(today)) {
                    status = "OVERDUE";
                } else {
                    status = "active";
                }
                String item = String.format("[%d] %s borrowed '%s' on %s (due %s) - %s",
                        i,
                        l.getMember(),
                        l.getBookTitle(),
                        l.getBorrowDate(),
                        l.getDueDate(),
                        status);
                response.append("<li>").append(escapeHtml(item)).append("</li>");
            }
            response.append("</ul>");

            response.append("<h3>Mark Loan as Returned</h3>");
            response.append("<form method='post' action='/return'>");
            response.append("<label for='loanIndex'>Loan index:</label>");
            response.append("<input id='loanIndex' name='loanIndex' type='number' min='0'>");
            response.append("<button type='submit'>Return</button>");
            response.append("</form>");

            response.append("<br><a href='/'><- Back</a></body></html>");

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
