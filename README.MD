# Library Management Server

A simple **Java-based Library Management Server** that allows you to manage
books, members, and borrowing records through a web browser.

The project uses **plain Java**, the built-in `com.sun.net.httpserver.HttpServer`
for a lightweight HTTP server, and a single-page **HTML + CSS** frontend.

---

## Features

- **Book Management**  
  - Add a book with **title**, **author**, and **publication year**  
  - View a list of all books currently in the library

- **Member Management**  
  - Register a member with **name** and **member ID**  
  - View a list of all registered members

- **Borrowing Records**  
  - Record when a member borrows a book (member + book title)  
  - View all borrowing records

- **Simple Web UI**  
  - One page (`index.html`) with three sections: **Books**, **Members**, and **Borrowing**  
  - Links to separate pages that list books, members, and borrowing records

> Note: All data is stored **in memory only**. When the server restarts,
> books, members, and loans are cleared.

---

## Project Structure.

- `LibraryServer.java` – Java HTTP server, routes and in-memory storage
- `index.html` – Main UI for managing books, members, and borrowing
- `style.css` – Styling for the main page and cards

---

## Prerequisites

- **Java** (JDK 8+ recommended)

No external libraries are required; everything uses the standard Java runtime
and the built-in HTTP server.

---

## How to Run

1. **Compile** the Java server:

   ```bash
   javac LibraryServer.java
   ```

2. **Run** the server:

   ```bash
   java LibraryServer
   ```

3. You should see in the console:

   ```
    Server started at http://localhost:8080
   ```

4. **Open the web UI**:
   - Option A (recommended): Open a browser at `http://localhost:8080/`  
   - Option B: Open `index.html` directly in your browser (forms still post to
     `http://localhost:8080/...` so the server must be running).

---

## Using the Application

### 1. Books

- Go to the **Books** section on the main page.  
- Fill in **Book Title**, **Author**, and **Publication Year**.  
- Click **Add Book**.  
- To see all books, click **View All Books**.

### 2. Members

- Go to the **Members** section.  
- Fill in **Member Name** and **Member ID** (e.g. `M001`).  
- Click **Add Member**.  
- To see all members, click **View All Members**.

### 3. Borrowing

- Go to the **Borrowing** section.  
- Enter a **Member** (name or ID) and a **Book Title**.  
- Click **Record Borrow**.  
- To see all borrowing records, click **View Borrowing Records**.

---

## Limitations and Possible Improvements

- Data is **not persisted** (in-memory lists only).  
- Books, members, and loans are stored as plain strings (no rich data model).  
- No authentication or user roles.  

Possible improvements:

- Add a `Book` and `Member` class with IDs and richer fields.  
- Save data to a file or database so it survives restarts.  
- Add return dates, due dates, and a "returned" status for loans.  
- Add search, edit, and delete operations from the UI.

---

## Contributing



1. **Fork** the repository.  
2. **Clone** your fork locally:

   ```bash
   git clone https://github.com/your-username/LibraryManagementServer.git
   ```

