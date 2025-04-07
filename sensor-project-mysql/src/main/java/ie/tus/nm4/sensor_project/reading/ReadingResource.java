package ie.tus.nm4.sensor_project.reading;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ReadingResource {
	private ReadingRepository repository;
	
	@Autowired
	public ReadingResource(ReadingRepository repository ) {
		this.repository = repository;
	}
	
	@GetMapping("/Readings")
	public List<Reading> retrieveAllReadings() {
		return repository.findAll();
	}
	
	@GetMapping("/Readings/{id}")
	public ResponseEntity<Reading> retrieveReading(@PathVariable int id) {
		Optional<Reading> Reading = repository.findById(id);
		
		if(Reading.isEmpty()) {
			System.out.println("Reading not found in the database");
			return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok(Reading.get());
		}
	}
	
	// DELETEONE
    @DeleteMapping("/Readings/{id}")
    public ResponseEntity<?> deleteReading(@PathVariable int id) {
        Optional<Reading> existingReading = repository.findById(id);
    	  if (existingReading.isEmpty()) {
    	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
    	                .body("Reading with ID " + id + " not found.");
    	    }
    	repository.deleteById(id);
//        if () {
//            return 204; // 204 No Content on success
//        } else {
//        	return 404; // 404 Not Found if Reading doesn't exist
//        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content    
        }
    
    @DeleteMapping("/Readings")
    public ResponseEntity<String> deleteAllReading() {
        repository.deleteAll();
        return ResponseEntity.ok("All readings have been deleted.");
    }

    @PostMapping("/Readings")
    public ResponseEntity<Reading> createReading(@RequestBody Reading reading) {
        Reading savedReading = repository.save(reading);
        return ResponseEntity.ok(savedReading);
    }
    // POST
    @PutMapping("/Readings/{id}")
    public ResponseEntity<Reading> editReading(@PathVariable int id, @RequestBody Reading updatedReading) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        updatedReading.setId(id); // Ensure the ID is correct
        Reading savedReading = repository.save(updatedReading);
        return ResponseEntity.ok(savedReading);
    }
	
}
