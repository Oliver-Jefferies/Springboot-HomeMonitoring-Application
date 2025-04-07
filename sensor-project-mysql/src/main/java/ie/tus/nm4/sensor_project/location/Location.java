package ie.tus.nm4.sensor_project.location;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class Location {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int locationId;
	private	int customerId;
	private String address;
	private String locationType;



	public Location() {
		super();
	}



	@Override
	public String toString() {
		return "Location [locationId=" + locationId + ", customerId=" + customerId + ", address=" + address
				+ ", locationType=" + locationType + "]";
	}



	public int getLocationId() {
		return locationId;
	}



	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}



	public int getCustomerId() {
		return customerId;
	}



	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getLocationType() {
		return locationType;
	}



	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}



	public Location(int locationId, int customerId, String address, String locationType) {
		super();
		this.locationId = locationId;
		this.customerId = customerId;
		this.address = address;
		this.locationType = locationType;
	}

}
