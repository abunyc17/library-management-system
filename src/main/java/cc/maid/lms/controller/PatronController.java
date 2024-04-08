package cc.maid.lms.controller;

import cc.maid.lms.entity.Patron;
import cc.maid.lms.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Abubakar Adamu on 05/04/2024
 **/
@RestController
@RequestMapping("api/v1/patron")
public class PatronController {

    private final PatronService patronService;

    @Autowired
    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping
    public ResponseEntity<List<Patron>> getAllPatron(){
        List<Patron> patronList = patronService.findAllPatron();
        return new ResponseEntity<>(patronList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patron> getSinglePatronById(@PathVariable Long id){
        return patronService.getPatronById(id);
    }

    @PostMapping
    public ResponseEntity<Patron> addNewPatron(@RequestBody Patron patron){
        return patronService.createNewPatron(patron);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @RequestBody Patron patron){
        return patronService.updatePatron(id, patron);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatron(@PathVariable Long id){
        patronService.deletePatron(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
