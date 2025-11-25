import java.time.LocalDate;

public class Loan {
    private final String member;
    private final String bookTitle;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private boolean returned;

    public Loan(String member, String bookTitle, LocalDate borrowDate, LocalDate dueDate) {
        this.member = member;
        this.bookTitle = bookTitle;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returned = false;
    }

    public String getMember() {
        return member;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void markReturned() {
        this.returned = true;
    }

    public boolean isOverdue(LocalDate today) {
        return !returned && today.isAfter(dueDate);
    }
}
