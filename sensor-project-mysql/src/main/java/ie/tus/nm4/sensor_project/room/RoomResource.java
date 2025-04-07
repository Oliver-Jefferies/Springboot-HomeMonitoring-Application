package ie.tus.nm4.sensor_project.room;


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
public class RoomResource {
	private RoomRepository repository;
	
	//Define resource and define as a repository
	@Autowired
	public RoomResource(RoomRepository repository ) {
		this.repository = repository;
	}
	
	@GetMapping("/Rooms")
	public List<Room> retrieveAllRooms() {
		return repository.findAll();
	}
	
	@GetMapping("/Rooms/{id}")
	public ResponseEntity<Room> retrieveReading(@PathVariable int id) {
		Optional<Room> Room = repository.findById(id);
		
		if(Room.isEmpty()) {
			System.out.println("Reading not found in the database");
			return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok(Room.get());
		}
	}
	
	// DELETEONE if id is found, if not then delete and return success
    @DeleteMapping("/Rooms/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable int id) {
    	//Check if room 
        Optional<Room> existingRoom = repository.findById(id);
    	  if (existingRoom.isEmpty()) {
    	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
    	                .body("Room with ID " + id + " not found.");
    	    }
    	repository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content    
        }
    
    //Delete all rooms
    @DeleteMapping("/Rooms")
    public ResponseEntity<String> deleteAllRoom() {
        repository.deleteAll();
        return ResponseEntity.ok("All Rooms have been deleted.");
    }

    //Post mapping using full object in request body
    @PostMapping("/Rooms")
    public ResponseEntity<Room> createReading(@RequestBody Room room) {
        Room savedRoom = repository.save(room);
        return ResponseEntity.ok(savedRoom);
    }
    // POST
    @PutMapping("/Rooms/{id}")
    public ResponseEntity<Room> editRoom(@PathVariable int id, @RequestBody Room updatedRoom) {
    	//Check if the specified ID exists; if not return if it does update it 
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        updatedRoom.setRoomId(id); // Ensure the ID is correct
        Room savedReading = repository.save(updatedRoom);
        return ResponseEntity.ok(savedReading);
    }
	
}
