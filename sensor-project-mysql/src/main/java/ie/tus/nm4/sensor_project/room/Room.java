package ie.tus.nm4.sensor_project.room;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Room {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int roomId;
	private	int locationId;
	private String sensorType;
	private String roomType;
	private int floorNum;




	public Room() {
		super();
	}




	public int getRoomId() {
		return roomId;
	}




	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}




	public int getLocationId() {
		return locationId;
	}




	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}




	public String getSensorType() {
		return sensorType;
	}




	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}




	public String getRoomType() {
		return roomType;
	}




	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}




	public int getFloorNum() {
		return floorNum;
	}




	public void setFloorNum(int floorNum) {
		this.floorNum = floorNum;
	}




	public Room(int roomId, int locationId, String sensorType, String roomType, int floorNum) {
		super();
		this.roomId = roomId;
		this.locationId = locationId;
		this.sensorType = sensorType;
		this.roomType = roomType;
		this.floorNum = floorNum;
	}




	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", locationId=" + locationId + ", sensorType=" + sensorType + ", roomType=" + roomType
				+ ", floorNum=" + floorNum + "]";
	}



}
