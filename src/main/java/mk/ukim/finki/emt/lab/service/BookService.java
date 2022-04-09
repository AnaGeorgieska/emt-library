package mk.ukim.finki.emt.lab.service;

import mk.ukim.finki.emt.lab.model.Book;
import mk.ukim.finki.emt.lab.model.dto.BookDto;
import mk.ukim.finki.emt.lab.model.enumerations.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface BookService {

    public List<Book> findAll();

    public Optional<Book> findById(Long id);

    public Book addBook(String name, Category category,
                        Long authorId, Integer availableCopies);

    public Optional<Book> save(BookDto bookDto);

    public Optional<Book> edit(Long id, BookDto bookDto);

    public Book editBook(Long id, String name, Category category,
                         Long authorId, Integer availablCopies);

    public void deleteBook(Long id);

    void mark(Long id);
}
