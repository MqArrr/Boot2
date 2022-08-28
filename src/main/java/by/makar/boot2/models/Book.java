package by.makar.boot2.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {

    private static long TENDAYS = 10 * 1000 * 60 * 60 * 24;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "author")
    private String author;
    @Column(name = "name")
    @NotEmpty(message = "Название не должно быть пустым")
    @Size(min = 1, max = 50, message = "Длина названия должна быть не больше 50 знаков")
    private String name;
    @Column(name = "year")
    private int year;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Date getDateOfGet() {
        return dateOfGet;
    }

    public void setDateOfGet(Date dateOfGet) {
        this.dateOfGet = dateOfGet;
    }

    @Column(name = "date_of_get")
    @Temporal(TemporalType.DATE)
    private Date dateOfGet;

    public Book(){
    }

    public Book(String author, String name, int year) {
        this.author = author;
        this.name = name;
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (year != book.year) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        return name != null ? name.equals(book.name) : book.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + year;
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", dateOfGet=" + dateOfGet +
                '}';
    }

    public boolean isOverdue(){
        if(isFree()) return false;
        return ((System.currentTimeMillis() - dateOfGet.getTime()) > TENDAYS);
    }
    public boolean isFree(){
        return owner == null;
    }
}
