package com.library.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.model.Author;
import com.library.model.Category;
import com.library.model.Publisher;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BookDTO {
    private Long id;
    private String isbn;
    private String title;
    private Integer publicationYear;
    private String edition;
    private Integer totalCopies;
    private Integer availableCopies;
    private String description;
    private String thumbnailUrl;
    private Set<String> authorNames = new HashSet<>();
    private Set<String> categoryNames = new HashSet<>();
    private String publisherName;
    private Long publisherId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Integer getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(Integer totalCopies) {
        this.totalCopies = totalCopies;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Set<String> getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(Set<String> authorNames) {
        this.authorNames = authorNames;
    }

    public Set<String> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(Set<String> categoryNames) {
        this.categoryNames = categoryNames;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Helper method to convert entity to DTO
    public static BookDTO fromEntity(com.library.model.Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setIsbn(book.getIsbn());
        dto.setTitle(book.getTitle());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setEdition(book.getEdition());
        dto.setTotalCopies(book.getTotalCopies());
        dto.setAvailableCopies(book.getAvailableCopies());
        dto.setDescription(book.getDescription());
        dto.setThumbnailUrl(book.getThumbnailUrl());
        
        if (book.getAuthors() != null) {
            dto.setAuthorNames(book.getAuthors().stream()
                    .map(Author::getName)
                    .collect(Collectors.toSet()));
        }
        
        if (book.getCategories() != null) {
            dto.setCategoryNames(book.getCategories().stream()
                    .map(Category::getName)
                    .collect(Collectors.toSet()));
        }
        
        if (book.getPublisher() != null) {
            dto.setPublisherName(book.getPublisher().getName());
            dto.setPublisherId(book.getPublisher().getId());
        }
        
        dto.setCreatedAt(book.getCreatedAt());
        dto.setUpdatedAt(book.getUpdatedAt());
        
        return dto;
    }
}
