package by.makar.boot2.service;

import by.makar.boot2.models.Book;
import by.makar.boot2.models.Person;
import by.makar.boot2.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(boolean sortByYear){
        return bookRepository.findAll();
    }
    public Book findOne(int id){
        return bookRepository.findById(id).orElse(null);
    }
    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }
    @Transactional
    public void update(Book book){
        bookRepository.save(book);
    }
    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }
    @Transactional
    public void setOwner(int bookId, Person person){
        Book book = bookRepository.findById(bookId).orElse(null);
        if(book != null) {
            book.setOwner(person);
            book.setDateOfGet(new Date());
        }
    }
    @Transactional(readOnly = true)
    public List<Book> findByNameLike(String like){
        return bookRepository.findByNameLikeIgnoreCase("%" + like + "%");
    }
    @Transactional(readOnly = true)
    public List<Book> findByOrderByYear(){
        return bookRepository.findByOrderByYear();
    }
    @Transactional
    public List<Book> findByNameLikeIgnoreCaseOrderByYear(String name){
        return bookRepository.findByNameLikeIgnoreCaseOrderByYear("%" + name + "%");
    }
    @Transactional(readOnly = true)
    public Page<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear)
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year")));
        else
            return bookRepository.findAll(PageRequest.of(page, booksPerPage));
    }
}
