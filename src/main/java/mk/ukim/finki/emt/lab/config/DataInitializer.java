package mk.ukim.finki.emt.lab.config;

import mk.ukim.finki.emt.lab.model.Author;
import mk.ukim.finki.emt.lab.model.Country;
import mk.ukim.finki.emt.lab.model.enumerations.Category;
import mk.ukim.finki.emt.lab.repository.AuthorRepository;
import mk.ukim.finki.emt.lab.repository.CountryRepository;
import mk.ukim.finki.emt.lab.service.BookService;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class DataInitializer {


    private final BookService bookService;
    private final AuthorRepository authorRepository;
    private final CountryRepository countryRepository;

    public DataInitializer(BookService bookService, AuthorRepository authorRepository, CountryRepository countryRepository) {
        this.bookService = bookService;
        this.authorRepository = authorRepository;
        this.countryRepository = countryRepository;
    }

    @PostConstruct
    public void initData() {


        Country russia=this.countryRepository.save(new Country("Russia", "Asia"));
        Country GB=this.countryRepository.save(new Country("Great Britain", "Europe"));

        Author leoTolstoy=this.authorRepository.save(new Author("Leo", "Tolstoy", russia));
        Author JKRowling=this.authorRepository.save(new Author("J.K.", "Rowling", GB));
        Author Shakespeare=this.authorRepository.save(new Author("William", "Shakespeare", GB));

        this.bookService.addBook("Anna Karenina", Category.DRAMA, leoTolstoy.getId(), 3);
        this.bookService.addBook("War & Peace", Category.HISTORY, leoTolstoy.getId(), 6);
        this.bookService.addBook("Macbeth", Category.TRAGEDY, Shakespeare.getId(), 2);
        this.bookService.addBook("The Death of Ivan Ilyich", Category.NOVEL, leoTolstoy.getId(), 3);
        this.bookService.addBook("Harry Potter", Category.FANTASY, JKRowling.getId(), 8);
        this.bookService.addBook("The Ickabog", Category.FANTASY, JKRowling.getId(), 8);
        this.bookService.addBook("Hamlet", Category.TRAGEDY, Shakespeare.getId(), 10);
        this.bookService.addBook("Romeo and Juliet", Category.TRAGEDY, Shakespeare.getId(), 2);

    }

}
