package com.library.repository;

import com.library.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByTitleContaining(String title);
    List<Book> findByAuthorName(String authorName);
    List<Book> findByCategory(String categoryName);
    Book save(Book book);
    void deleteById(Long id);
    boolean existsByIsbn(String isbn);
    long count();
}
