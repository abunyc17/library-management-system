package cc.maid.lms.service;

import cc.maid.lms.entity.Book;
import cc.maid.lms.entity.BorrowingRecord;
import cc.maid.lms.entity.Patron;
import cc.maid.lms.exception.RequestException;
import cc.maid.lms.exception.RequestNotFoundException;
import cc.maid.lms.repository.BookRepository;
import cc.maid.lms.repository.BorrowingRecordRepository;
import cc.maid.lms.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by Abubakar Adamu on 05/04/2024
 **/

@Service
public class BorrowingRecordService {
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    public BorrowingRecordService(BookRepository bookRepository, PatronRepository patronRepository, BorrowingRecordRepository borrowingRecordRepository) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.borrowingRecordRepository = borrowingRecordRepository;
    }

    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Optional<Patron> optionalPatron = patronRepository.findById(patronId);
        if(optionalBook.isEmpty() || optionalPatron.isEmpty()){
            throw new RequestNotFoundException("Book or Patron id not found");
        }

        Book book = optionalBook.get();
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setBorrowDate(LocalDate.now());
        borrowingRecord.setReturned(false);
        borrowingRecord.setPatron(optionalPatron.get());
        BorrowingRecord borrowingRecord1 = borrowingRecordRepository.save(borrowingRecord);
        return borrowingRecord1;


    }

    public ResponseEntity<String> returnBook(Long bookId, Long patronId) {
        Optional<BorrowingRecord> getBorrowingDetails = borrowingRecordRepository.getRecordByBookIdAndPatronId(bookId,patronId);
        validatePatronAndBook(getBorrowingDetails);
        boolean boolReturned  = true;
        Long idValue = getBorrowingDetails.get().getId();
        borrowingRecordRepository.updateBookWithId(LocalDate.now(),boolReturned, idValue);
        return ResponseEntity.ok("Record successfully updated");

    }

    private void validatePatronAndBook(Optional<BorrowingRecord> getBorrowingDetails){
        if(getBorrowingDetails.isEmpty()){
            throw new RequestNotFoundException("Book or patron not found");
        }
        if(getBorrowingDetails.isPresent()){
            boolean alreadyReturned = getBorrowingDetails.get().isReturned();
            if(alreadyReturned == true){
                throw new RequestException("Book already returned by patron.");
            }
        }
    }

}
