package by.makar.boot2.models;



import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 1, max = 50, message = "Длина имени должна быть не больше 50 знаков")
    private String name;
    @Column(name = "year")
    private int year;
    @OneToMany(mappedBy = "owner")
    private List<Book> books;
    @Column(name = "register_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfRegister;

    public Person(){}

    public Person(String name, int year, List<Book> books) {
        this.name = name;
        this.year = year;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public Date getDateOfRegister() {
        return dateOfRegister;
    }

    public String getDateOfRegisterString() {
        if(dateOfRegister == null)
            return " нет информации";
        return dateOfRegister.toString();
    }

    public void setDateOfRegister(Date dateOfRegister) {
        this.dateOfRegister = dateOfRegister;
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge(){
        return new GregorianCalendar().get(Calendar.YEAR) - year;
    }

    public void addBook(Book book){
        books.add(book);
        book.setOwner(this);
    }
    public void removeBook(Book book){
        books.remove(book);
        book.setOwner(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (year != person.year) return false;
        return name != null ? name.equals(person.name) : person.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + year;
        return result;
    }
}
