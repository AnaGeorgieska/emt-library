package mk.ukim.finki.emt.lab.service;

import mk.ukim.finki.emt.lab.model.Author;
import mk.ukim.finki.emt.lab.model.Book;
import mk.ukim.finki.emt.lab.model.dto.BookDto;
import mk.ukim.finki.emt.lab.model.enumerations.Category;
import mk.ukim.finki.emt.lab.model.exceptions.AuthorNotFoundException;
import mk.ukim.finki.emt.lab.model.exceptions.BookNotFoundException;
import mk.ukim.finki.emt.lab.repository.AuthorRepository;
import mk.ukim.finki.emt.lab.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public Book addBook(String name, Category category, Long authorId, Integer availableCopies) {

        Author author=authorRepository.findById(authorId).orElseThrow(()->new AuthorNotFoundException(authorId));

        Book book=new Book(name, category, author, availableCopies);

        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> save(BookDto bookDto) {
        Author author=authorRepository.findById(bookDto.getAuthor())
                .orElseThrow(()->new AuthorNotFoundException(bookDto.getAuthor()));

        return Optional.of(this.bookRepository.save(new Book(bookDto.getName(), bookDto.getCategory(),
                author, bookDto.getAvailableCopies())));
    }
    @Override
    public Optional<Book> edit(Long id, BookDto bookDto) {
        Author author=authorRepository.findById(bookDto.getAuthor())
                .orElseThrow(()->new AuthorNotFoundException(bookDto.getAuthor()));
        Book book=bookRepository.findById(id)
                .orElseThrow(()->new BookNotFoundException(id));

        book.setName(bookDto.getName());
        book.setCategory(bookDto.getCategory());
        book.setAuthor(author);
        book.setAvailableCopies(bookDto.getAvailableCopies());

        return Optional.of(this.bookRepository.save(book));
    }

    @Override
    public Book editBook(Long id, String name, Category category, Long authorId, Integer availableCopies) {
        Book book=bookRepository.findById(id).orElseThrow(()->new BookNotFoundException(id));
        Author author=authorRepository.findById(authorId).orElseThrow(()->new AuthorNotFoundException(authorId));

        book.setName(name);
        book.setCategory(category);
        book.setAuthor(author);
        book.setAvailableCopies(availableCopies);

        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book=bookRepository.findById(id).orElseThrow(()->new BookNotFoundException(id));
        this.bookRepository.delete(book);
    }

    @Override
    public void mark(Long id) {
        Book book=bookRepository.findById(id).orElseThrow(()->new BookNotFoundException(id));
        if(book.getAvailableCopies()>0)
            book.setAvailableCopies(book.getAvailableCopies()-1);
        this.bookRepository.save(book);
    }
}
