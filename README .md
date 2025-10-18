# 📚 Library Management System

A simple and user-friendly **Library Management System** built using HTML, CSS, and Node.js (Express backend).  
It allows users to **add new books** and **view all available books** through a clean web interface.

---

## 🌐 Project Overview

This web app provides an intuitive interface for managing library books locally.  
Users can:

✅ Add a new book by entering the **Title**, **Author**, and **Publication Year**  
✅ View all stored books in a separate page via `/books` endpoint  
✅ Manage library data easily through a **simple and responsive UI**

---

## 🧩 Project Structure

Library-Management-System/
│
├── index.html # Main interface for adding books
├── style.css # Custom styles for the web app
├── server.js # Node.js backend (Express server)
└── README.md # Project documentation (this file)

yaml
Copy code

---

## 🧠 How It Works

1. The **frontend (HTML)** form collects book details.
2. The form submits data to the **backend server** via `POST` request to `http://localhost:8080/addBook`.
3. The **server** stores the book in memory or database (if configured).
4. Visit `http://localhost:8080/books` to **view all added books**.

---

## 🚀 Getting Started

### 1️⃣ Clone the repository
```bash
git clone https://github.com/eldred16/Library-Management-System.git
cd Library-Management-System
2️⃣ Install dependencies (if backend is included)
npm install

3️⃣ Start the server
node server.js

4️⃣ Open in browser

Visit:

http://localhost:8080

💡 Example Form
<form action="http://localhost:8080/addBook" method="post" class="book-form">
  <label for="title">Book Title:</label>
  <input id="title" type="text" name="title" placeholder="Enter title" required>
  
  <label for="author">Author:</label>
  <input id="author" type="text" name="author" placeholder="Enter author name" required>
  
  <label for="year">Publication Year:</label>
  <input id="year" type="number" name="year" placeholder="e.g. 2022" required>

  <button type="submit">Add Book</button>
</form>

🖼️ Screenshot (Optional)

Add a screenshot of your project here!

👨‍💻 Author

Kubanja Elijah Eldred
📍 Bugema University
💻 Virtualized Software Engineering

⭐ Contribute

Feel free to fork this repository, open issues, or make pull requests to improve the project.