package cc.maid.lms.repository;

import cc.maid.lms.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by Abubakar Adamu on 05/04/2024
 **/

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    @Transactional
    @Query(value = "SELECT * FROM library_db.borrowing_record_tbl where book_id = :bookId and patron_id = :patronId", nativeQuery = true)
    Optional<BorrowingRecord> getRecordByBookIdAndPatronId(@Param("bookId") Long bookId, @Param("patronId") Long patronId);

    @Modifying
    @Query(value = "UPDATE library_db.borrowing_record_tbl b SET b.return_date = :returnDate, b.is_returned = :isReturned where id = :id", nativeQuery = true)
    @Transactional
    void updateBookWithId(@Param("returnDate")LocalDate returnDate, @Param("isReturned") boolean isReturned, @Param("id") Long id);
}
