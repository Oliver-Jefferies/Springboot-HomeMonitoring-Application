package ie.tus.nm4.sensor_project.reading;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;


@Entity
public class Reading {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int readingId;
	private	int roomId;
	private LocalDate time;
	private float temp;
	private float humidity;
	private float pressure;

    @PrePersist
    protected void onCreate() {
            this.time = LocalDate.now();
    }

	public Reading() {
		super();
	}


	public Reading(int id, int roomId, LocalDate time, float temp, float humidity, float pressure) {
		super();
		this.readingId = id;
		this.roomId = roomId;
		this.time = time;
		this.temp = temp;
		this.humidity = humidity;
		this.pressure = pressure;
	}


	@Override
	public String toString() {
		return "Reading [id=" + readingId + ", roomId=" + roomId + ", time=" + time + ", temp=" + temp + ", humidity="
				+ humidity + ", pressure=" + pressure + "]";
	}


	public int getId() {
		return readingId;
	}


	public void setId(int id) {
		this.readingId = id;
	}


	public int getroomId() {
		return roomId;
	}


	public void setroomId(int roomId) {
		this.roomId = roomId;
	}


	public LocalDate getTime() {
		return time;
	}


	public void setTime(LocalDate time) {
		this.time = time;
	}


	public float getTemp() {
		return temp;
	}


	public void setTemp(float temp) {
		this.temp = temp;
	}


	public float getHumidity() {
		return humidity;
	}


	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}


	public float getPressure() {
		return pressure;
	}


	public void setPressure(float pressure) {
		this.pressure = pressure;
	}
}
