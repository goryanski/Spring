package com.example.dao;

import com.example.models.Book;
import com.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAll() {
        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book get(int id) {
        return jdbcTemplate.query("SELECT * FROM books WHERE id=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public List<Book> getPersonBooks(int personId) {
        return jdbcTemplate.query("SELECT * FROM books WHERE person_id=?", new Object[]{personId},
                new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO books(name, author, year) VALUES(?, ?, ?)", book.getName(),
                book.getAuthor(), book.getYear());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE books SET name =?, author=?, year=? WHERE id=?", book.getName(),
                book.getAuthor(), book.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM books WHERE id=?", id);
    }

    // JOIN tables books and people, get a person who is owner of book with this id
    public Optional<Person> getBookOwner(int id) {
        // select all people columns from joined table. so we can get people list and take from it one person if he exists
        return jdbcTemplate.query("SELECT people.* FROM books JOIN people ON books.person_id = people.id " +
                        "WHERE books.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    // release book (remove link to person in table) - this method is called when a person returns a book to the library
    public void release(int id) {
        jdbcTemplate.update("UPDATE books SET person_id=NULL WHERE id=?", id);
    }

    // assign book to the selected person (create link to person in table) - this method is called when a person takes a book from the library
    public void assign(int bookId, int personId) {
        jdbcTemplate.update("UPDATE books SET person_id=? WHERE id=?", personId, bookId);
    }
}
