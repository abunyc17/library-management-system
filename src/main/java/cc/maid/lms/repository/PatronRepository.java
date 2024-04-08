package cc.maid.lms.repository;

import cc.maid.lms.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Abubakar Adamu on 05/04/2024
 **/

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {

}
