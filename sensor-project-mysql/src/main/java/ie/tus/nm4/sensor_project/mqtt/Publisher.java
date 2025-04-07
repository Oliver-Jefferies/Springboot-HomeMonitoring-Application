package ie.tus.nm4.sensor_project.mqtt;

import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Publisher {

	public static void main(String[] args) throws MqttException {
		// TODO Auto-generated method stub
		String topic = "net4Example";
		//String content = "Hello from net4";
		int qos = 2;
		String broker = "tcp://localhost:1883";
		
		String clientId = MqttClient.generateClientId();
		System.out.println("Publisher: CLient id: " + clientId);
		MqttClient client = new MqttClient(broker, clientId);
		
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		client.connect(connOpts);
		
				
		MqttMessage	message = new MqttMessage();
		message.setQos(qos);
		message.setRetained(true);
		
		
		//Define minimum and maximum variables for random generation
		boolean finished = false;
		int tempmax = 30;
		int tempmin = 1;
		
		int hummax = 100;
		int hummin = 0;
		
		int roomMin = 1;
		int roomMax = 4;
		
		int pressuremax = 1050;
		int pressuremin = 950;
		
		//Set up scanner
		Scanner myScan= new Scanner(System.in);
		for (int i = 0; i < 10; i++) {
			
			//Generate random data
	        double temp = tempmin + (Math.random() * tempmax);
	        double humidity = hummin + (Math.random() * hummax);
	        double pressure = pressuremin + (Math.random() * pressuremax);
	        double roomNum = roomMin + (Math.random() * roomMax);

	        //Pase to correct datatypes 
	        int roomI = (int) roomNum;
	        float tempF = (float) temp;
	        float humF = (float) humidity;
	        float pressF = (float) pressure;

	        String content = (roomI + "," + tempF + "," + humF + "," + pressF); 
	        
	        
	        //Send message to mqtt broker
			message.setPayload((content).getBytes());
			client.publish(topic, message);
			System.out.println("Published string: " + content);
			}
		

		client.disconnect();
		client.close();
		
		
	}

}
