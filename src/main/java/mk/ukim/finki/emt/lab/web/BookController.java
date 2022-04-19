package mk.ukim.finki.emt.lab.web;

import mk.ukim.finki.emt.lab.model.Book;
import mk.ukim.finki.emt.lab.model.dto.BookDto;
import mk.ukim.finki.emt.lab.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://emt-library-frontend-react.herokuapp.com")
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> findAll()
    {
        return this.bookService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id)
    {
        return this.bookService.findById(id)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<BookDto> findByIdDtp(@PathVariable Long id)
    {
        return this.bookService.findById(id)
                .map(book -> ResponseEntity.ok().body(new BookDto(book.getName(),book.getCategory(), book.getAuthor().getId(),
                        book.getAvailableCopies())))
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public  ResponseEntity<Book> save(@RequestBody BookDto bookDto)
    {
        return this.bookService.save(bookDto)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(()->ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    public  ResponseEntity<Book> edit(@PathVariable Long id, @RequestBody BookDto bookDto)
    {
        return this.bookService.edit(id,bookDto)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(()->ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity DeleteById(@PathVariable Long id)
    {
        this.bookService.deleteBook(id);
        if(this.bookService.findById(id).isEmpty())
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
//    return axios.get(`/books/mark/${id}`);
    @GetMapping("/mark/{id}")
    public ResponseEntity markById(@PathVariable Long id)
    {
        this.bookService.mark(id);
        return ResponseEntity.ok().build();
    }
}
