package cc.maid.lms.repository;

import cc.maid.lms.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Abubakar Adamu on 05/04/2024
 **/

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Modifying
    @Query(value = "UPDATE Book b SET b.title = :title, b.author = :author, b.publicationYear = :publicationYear, b.isbn = :isbn, b.description = :description WHERE b.id = :id ")
    @Transactional
    void updateBookWithId(@Param("id") Long id, @Param("title") String title, @Param("author") String author, @Param("publicationYear") int publicationYear, @Param("isbn") String isbn, @Param("description") String description);
}
