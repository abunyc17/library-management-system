package cc.maid.lms.controller;

import cc.maid.lms.entity.BorrowingRecord;
import cc.maid.lms.service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Abubakar Adamu on 07/04/2024
 **/
@RestController
@RequestMapping("/api")
public class BorrowingRecordController {
    private final BorrowingRecordService borrowingRecordService;

    @Autowired
    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> createBorrowedBooks(@PathVariable Long bookId, @PathVariable Long patronId){
        BorrowingRecord successfullyBorrowed =  borrowingRecordService.borrowBook(bookId, patronId);
        return ResponseEntity.status(HttpStatus.OK).body(successfullyBorrowed);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<String> updateReturnedBook(@PathVariable Long bookId, @PathVariable Long patronId){
        return borrowingRecordService.returnBook(bookId, patronId);
    }
}
