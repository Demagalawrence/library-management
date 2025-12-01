package com.library.repository.jpa;

import com.library.model.Book;
import com.library.repository.BookRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> findAll() {
        return entityManager.createNamedQuery("Book.findAll", Book.class).getResultList();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        try {
            Book book = entityManager.createQuery(
                    "SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
            return Optional.ofNullable(book);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findByTitleContaining(String title) {
        return entityManager.createNamedQuery("Book.findByTitleContaining", Book.class)
                .setParameter("title", title)
                .getResultList();
    }

    @Override
    public List<Book> findByAuthorName(String authorName) {
        return entityManager.createNamedQuery("Book.findByAuthorName", Book.class)
                .setParameter("authorName", authorName)
                .getResultList();
    }

    @Override
    public List<Book> findByCategory(String categoryName) {
        return entityManager.createNamedQuery("Book.findByCategory", Book.class)
                .setParameter("categoryName", categoryName)
                .getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            entityManager.persist(book);
            return book;
        } else {
            return entityManager.merge(book);
        }
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(book -> {
            // Remove associations
            book.getAuthors().forEach(author -> author.getBooks().remove(book));
            book.getCategories().forEach(category -> category.getBooks().remove(book));
            
            // Clear collections to avoid constraint violations
            book.getAuthors().clear();
            book.getCategories().clear();
            
            // Remove the book
            entityManager.remove(book);
        });
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(b) FROM Book b WHERE b.isbn = :isbn", Long.class)
                .setParameter("isbn", isbn)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(b) FROM Book b", Long.class)
                .getSingleResult();
    }
}
