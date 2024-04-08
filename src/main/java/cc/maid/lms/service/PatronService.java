package cc.maid.lms.service;

import cc.maid.lms.entity.Patron;
import cc.maid.lms.exception.RequestException;
import cc.maid.lms.exception.RequestNotFoundException;
import cc.maid.lms.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * Created by Abubakar Adamu on 05/04/2024
 **/
@Service
public class PatronService {
    private final PatronRepository patronRepository;

    @Autowired
    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public List<Patron> findAllPatron() {
        return patronRepository.findAll();
    }

    public ResponseEntity<Patron> getPatronById(Long id) {
        Optional<Patron> optionalPatron = patronRepository.findById(id);
        if(optionalPatron.isEmpty()){
            throw new RequestNotFoundException(String.format("Patron with ID %d not found.", id));
        }
        return ResponseEntity.ok(optionalPatron.get());
    }


    public ResponseEntity<Patron> createNewPatron(Patron patron) {
        validatePatron(patron);
        Patron patronResponse = patronRepository.save(patron);
        return new ResponseEntity<>(patronResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<Patron> updatePatron(Long id, Patron patron) {
        Optional<Patron> patronDetailsById = patronRepository.findById(id);
        if(patronDetailsById.isEmpty()){
            throw new RequestNotFoundException(String.format("Patron with ID %d not found", id));
        }
        patron.setId(id);
        Patron updatedPatron = patronRepository.save(transformPatronDetails(patron,patronDetailsById));
        return ResponseEntity.status(HttpStatus.OK).body(updatedPatron);
    }

    public void deletePatron(Long id) {
        Optional<Patron> patronDetailsById = patronRepository.findById(id);
        if(patronDetailsById.isEmpty()){
            throw new RequestException(String.format("Patron with ID %d not found", id));
        }
        patronRepository.deleteById(id);
    }

    private Patron transformPatronDetails(Patron patron, Optional<Patron> patronOptional){
        Patron patronResponse = new Patron();
        String name = StringUtils.hasText(patron.getName()) ? patron.getName() :  patronOptional.get().getName();
        String contactInfo = StringUtils.hasText(patron.getContactInformation()) ? patron.getContactInformation() : patronOptional.get().getContactInformation();
        patronResponse.setId(patron.getId());
        patronResponse.setName(name);
        patronResponse.setContactInformation(contactInfo);
        return patronResponse;
    }

    private void validatePatron(Patron patron){
        if(!StringUtils.hasText(patron.getName())){
            throw new RequestException("name is required.");
        }
        if(!StringUtils.hasText(patron.getContactInformation())){
            throw new RequestException("contactInformation is required.");
        }
    }
}
