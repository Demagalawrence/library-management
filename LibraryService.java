import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class LibraryService {
    private final List<Book> books = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private final List<Loan> loans = new ArrayList<>();

    public Book addBook(String title, String author, String yearStr) {
        if (title == null || author == null || yearStr == null) {
            return null;
        }
        title = title.trim();
        author = author.trim();
        yearStr = yearStr.trim();
        if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty()) {
            return null;
        }
        try {
            int year = Integer.parseInt(yearStr);
            String id = UUID.randomUUID().toString();
            Book book = new Book(id, title, author, year);
            books.add(book);
            return book;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Member addMember(String name, String memberId) {
        if (name == null || memberId == null) {
            return null;
        }
        name = name.trim();
        memberId = memberId.trim();
        if (name.isEmpty() || memberId.isEmpty()) {
            return null;
        }
        Member member = new Member(memberId, name);
        members.add(member);
        return member;
    }

    public Loan borrow(String member, String bookTitle) {
        if (member == null || bookTitle == null) {
            return null;
        }
        member = member.trim();
        bookTitle = bookTitle.trim();
        if (member.isEmpty() || bookTitle.isEmpty()) {
            return null;
        }
        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plusDays(14);
        Loan loan = new Loan(member, bookTitle, today, dueDate);
        loans.add(loan);
        return loan;
    }

    public void markLoanReturned(int index) {
        if (index >= 0 && index < loans.size()) {
            loans.get(index).markReturned();
        }
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public List<Member> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public List<Loan> getLoans() {
        return Collections.unmodifiableList(loans);
    }
}
