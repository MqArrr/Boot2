package by.makar.boot2.repositories;


import by.makar.boot2.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByNameLikeIgnoreCase(String name);
    List<Book> findByNameLikeIgnoreCaseOrderByYear(String name);
    List<Book> findByOrderByYear();

    Page<Book> findAll(Pageable pageable);
}
