package by.makar.boot2.controllers;


import by.makar.boot2.models.Book;
import by.makar.boot2.models.Person;
import by.makar.boot2.service.BookService;
import by.makar.boot2.service.PeopleService;
import by.makar.boot2.util.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/library/books")
public class BookController {

    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) Boolean sortByYear) {
        if (page == null)
            page = 0;
        if(booksPerPage == null)
            booksPerPage = 15;
        if(sortByYear == null)
            sortByYear = true;

        Page<Book> bookPage = bookService.findWithPagination(page, booksPerPage, sortByYear);

        model.addAttribute("booksPage", bookPage);
        model.addAttribute("pageNum", new Counter(page, sortByYear, booksPerPage, bookPage.hasNext(), bookPage.isFirst()));
        model.addAttribute("is_pagination_on", true);

        return "/books/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);
        model.addAttribute("person_owner", book.getOwner());
        model.addAttribute("people", peopleService.findAll());
        return "/books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "/books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "books/new";
        bookService.save(book);
        return "redirect:/library/books/";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, @PathVariable("id") int id, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";
        Book oldBook = bookService.findOne(id);
        book.setDateOfGet(oldBook.getDateOfGet());
        book.setOwner(oldBook.getOwner());
        book.setId(id);
        bookService.update(book);
        return "redirect:/library/books/";
    }

    @PostMapping("/{id}/changeperson")
    public String setPerson(@ModelAttribute("person") Person person, @PathVariable("id") int bookId, Model model) {
        bookService.setOwner(bookId, person);
        return "redirect:/library/books/";
    }

    @PostMapping("/{id}/deleteperson")
    public String resetPerson(@ModelAttribute("person") Person person, @PathVariable("id") int bookId, Model model) {
        bookService.setOwner(bookId, null);
        return "redirect:/library/books/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
        bookService.delete(id);
        return "redirect:/library/books";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String body, @RequestParam Boolean isSorted, Model model){
        if(body.isEmpty()) {
            if (isSorted)
                model.addAttribute("books", bookService.findByOrderByYear());
            else
                return "redirect:/library/books";
        }
        else {
            if (isSorted)
                model.addAttribute("books", bookService.findByNameLikeIgnoreCaseOrderByYear(body));
            else model.addAttribute("books", bookService.findByNameLike(body));

        }

        model.addAttribute("pageNum", new Counter());
        model.addAttribute("is_pagination_on", false);
        model.addAttribute("booksPage", "");
        return "books/books";
    }
}
