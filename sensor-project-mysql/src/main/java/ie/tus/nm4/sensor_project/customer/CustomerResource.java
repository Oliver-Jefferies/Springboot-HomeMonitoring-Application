package ie.tus.nm4.sensor_project.customer;


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
public class CustomerResource {
	private CustomerRepository repository;
	
	@Autowired
	public CustomerResource(CustomerRepository repository ) {
		this.repository = repository;
	}
	
	@GetMapping("/Customers")
	public List<Customer> retrieveAllCustomers() {
		return repository.findAll();
	}
	
	@GetMapping("/Customers/{id}")
	public ResponseEntity<Customer> retrieveCustomer(@PathVariable int id) {
		Optional<Customer> Customer = repository.findById(id);
		
		if(Customer.isEmpty()) {
			System.out.println("Customer not found in the database");
			return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok(Customer.get());
		}
	}
	
	// DELETEONE
    @DeleteMapping("/Customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int id) {
        Optional<Customer> existingCustomer = repository.findById(id);
    	  if (existingCustomer.isEmpty()) {
    	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
    	                .body("Customer with ID " + id + " not found.");
    	    }
    	repository.deleteById(id);
//        if () {
//            return 204; // 204 No Content on success
//        } else {
//        	return 404; // 404 Not Found if Customer doesn't exist
//        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content    
        }
    
    @DeleteMapping("/Customers")
    public ResponseEntity<String> deleteAllCustomer() {
        repository.deleteAll();
        return ResponseEntity.ok("All Customers have been deleted.");
    }

    @PostMapping("/Customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = repository.save(customer);
        return ResponseEntity.ok(savedCustomer);
    }
    // POST
    @PutMapping("/Customers/{id}")
    public ResponseEntity<Customer> editCustomer(@PathVariable int id, @RequestBody Customer updatedCustomer) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        updatedCustomer.setCustomerId(id); // Ensure the ID is correct
        Customer savedCustomer = repository.save(updatedCustomer);
        return ResponseEntity.ok(savedCustomer);
    }
	
}
