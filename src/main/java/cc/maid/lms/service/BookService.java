package cc.maid.lms.service;

import cc.maid.lms.entity.Book;
import cc.maid.lms.exception.RequestException;
import cc.maid.lms.exception.RequestNotFoundException;
import cc.maid.lms.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Abubakar Adamu on 05/04/2024
 **/

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public ResponseEntity<Book> getBookById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if(bookOptional.isEmpty()){
            throw new RequestNotFoundException(String.format("Book with id %d not found",id));
        }
        return ResponseEntity.ok(bookOptional.get());
    }

    public ResponseEntity<Book> addNewBook(Book book) {
        validateBook(book);
        Book bookResponse =  bookRepository.save(book);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<Book> updateBook(Long id, Book book) {
        Optional<Book> bookDetailsById = bookRepository.findById(id);
        if(bookDetailsById.isEmpty()){
            throw new RequestException(String.format("Book with ID %d not found", id));
        }
        book.setId(id);
        Book updatedBook = bookRepository.save(transformBookDetails(book,bookDetailsById));
        //bookRepository.updateBookWithId(id,book.getTitle(),book.getAuthor(),book.getPublicationYear(),book.getIsbn(),book.getDescription());
        return ResponseEntity.status(HttpStatus.OK).body(updatedBook);
        //return ResponseEntity.status(HttpStatus.OK).body("Book with ID "+ id + "updated successfully");
    }

    public void deleteBook(Long id) {
        Optional<Book> bookDetailsById = bookRepository.findById(id);
        if(bookDetailsById.isEmpty()){
            throw new RequestException(String.format("Book with ID %d not found", id));
        }
        bookRepository.deleteById(id);
    }

    private void validateBook(Book book){
        if(!StringUtils.hasText(book.getAuthor())){
            throw new RequestException("author is required");
        }
        if(!StringUtils.hasText(book.getTitle())){
            throw new RequestException("title is required");
        }
        if(!StringUtils.hasText(book.getIsbn())){
            throw new RequestException("isbn is required");
        }
        if(!StringUtils.hasText(book.getDescription())){
            throw new RequestException("description is required");
        }
        if(Objects.isNull(book.getPublicationYear())){
            throw new RequestException("publicationYear is required");
        }
    }

    private Book transformBookDetails(Book book, Optional<Book> bookDetailsById){
        Book book1 = new Book();
        String author  = StringUtils.hasText(book.getAuthor()) ? book.getAuthor() : bookDetailsById.get().getAuthor();
        String isbn = StringUtils.hasText(book.getIsbn()) ? book.getIsbn(): bookDetailsById.get().getIsbn();
        String title = StringUtils.hasText(book.getTitle()) ? book.getTitle(): bookDetailsById.get().getTitle();
        String description = StringUtils.hasText(book.getDescription()) ? book.getDescription() : bookDetailsById.get().getDescription();
        int publicationYear = Objects.isNull(book.getPublicationYear()) || book.getPublicationYear() == 0 ?bookDetailsById.get().getPublicationYear() : book.getPublicationYear();
        book1.setId(book.getId());
        book1.setAuthor(author);
        book1.setIsbn(isbn);
        book1.setTitle(title);
        book1.setDescription(description);
        book1.setPublicationYear(publicationYear);
        return book1;

    }
}
