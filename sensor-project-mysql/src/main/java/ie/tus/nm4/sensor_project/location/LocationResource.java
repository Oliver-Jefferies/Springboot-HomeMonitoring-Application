package ie.tus.nm4.sensor_project.location;


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
public class LocationResource {
	private LocationRepository repository;
	
	@Autowired
	public LocationResource(LocationRepository repository ) {
		this.repository = repository;
	}
	
	@GetMapping("/Locations")
	public List<Location> retrieveAllLocations() {
		return repository.findAll();
	}
	
	@GetMapping("/Locations/{id}")
	public ResponseEntity<Location> retrieveLocation(@PathVariable int id) {
		Optional<Location> Location = repository.findById(id);
		
		if(Location.isEmpty()) {
			System.out.println("Location not found in the database");
			return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok(Location.get());
		}
	}
	
	// DELETEONE
    @DeleteMapping("/Locations/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable int id) {
        Optional<Location> existingLocation = repository.findById(id);
    	  if (existingLocation.isEmpty()) {
    	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
    	                .body("Location with ID " + id + " not found.");
    	    }
    	repository.deleteById(id);
//        if () {
//            return 204; // 204 No Content on success
//        } else {
//        	return 404; // 404 Not Found if Location doesn't exist
//        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content    
        }
    
    @DeleteMapping("/Locations")
    public ResponseEntity<String> deleteAllLocation() {
        repository.deleteAll();
        return ResponseEntity.ok("All Locations have been deleted.");
    }

    @PostMapping("/Locations")
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        Location savedLocation = repository.save(location);
        return ResponseEntity.ok(savedLocation);
    }
    // POST
    @PutMapping("/Locations/{id}")
    public ResponseEntity<Location> editLocation(@PathVariable int id, @RequestBody Location updatedLocation) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        updatedLocation.setLocationId(id); // Ensure the ID is correct
        Location savedLocation = repository.save(updatedLocation);
        return ResponseEntity.ok(savedLocation);
    }
	
}
