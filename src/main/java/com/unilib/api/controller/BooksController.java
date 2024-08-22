package com.unilib.api.controller;

import com.unilib.api.domain.book.Book;
import com.unilib.api.domain.book.BookRequestDTO;
import com.unilib.api.domain.book.BookResponseDTO;
import com.unilib.api.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BooksController {
    @Autowired
    private BooksService booksService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Book> create(@RequestParam("title") String title,
                                       @RequestParam("description") String description,
                                       @RequestParam("available") String available,
                                       @RequestParam("image") MultipartFile image,
                                       @RequestParam("pdf") MultipartFile pdf,
                                       @RequestParam("hasEbook") String hasEbook,
                                       @RequestParam(value = "categories", required = false) List<UUID> categories
                                       ){
        Optional<List<UUID>> optionalCategories = Optional.of(categories);

        BookRequestDTO bookRequestDTO = new BookRequestDTO(title, description, Boolean.valueOf(available), image, pdf, Boolean.valueOf(hasEbook), optionalCategories);
        Book newBook = this.booksService.createBook(bookRequestDTO);

        return ResponseEntity.ok(newBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable UUID id) {
        Book book = this.booksService.getByID(id);

        return ResponseEntity.ok(book);
    }

    @GetMapping("/")
    public ResponseEntity<List<BookResponseDTO>> getFiltered(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "DESC") String sort,
                                                             @RequestParam(defaultValue = "") String title
                                                             ) {
       List<BookResponseDTO> books = this.booksService.getBooks(title, sort, page, size);
       return ResponseEntity.ok(books);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID bookId) {
        this.booksService.deleteBook(bookId);

        return ResponseEntity.noContent().build();
    }
}
