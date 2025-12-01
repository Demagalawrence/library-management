package com.library.service;

import com.library.dto.BookDTO;
import com.library.dto.CreateUpdateBookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    List<BookDTO> findAll();
    Page<BookDTO> findAll(Pageable pageable);
    BookDTO findById(Long id);
    BookDTO findByIsbn(String isbn);
    List<BookDTO> findByTitleContaining(String title);
    List<BookDTO> findByAuthorName(String authorName);
    List<BookDTO> findByCategory(String categoryName);
    BookDTO createBook(CreateUpdateBookDTO bookDTO);
    BookDTO updateBook(Long id, CreateUpdateBookDTO bookDTO);
    void deleteBook(Long id);
    long count();
}
