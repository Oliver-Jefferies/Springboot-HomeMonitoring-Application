package ie.tus.nm4.sensor_project.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class AsyncSubscriber {

	public static void main(String[] args) throws MqttException {

		// define variables
		String topic = "net4Example";

		//Subscripe to port 1883; mosquitto
		String broker = "tcp://localhost:1883";

		int qos = 2;

		// Create client object
		String clientId = MqttClient.generateClientId();
		System.out.println("Subscriber: Client id: " + clientId);
		MqttClient client = new MqttClient(broker, clientId);

		// Set call-back object
		client.setCallback(new SimpleMqttCallBack3());

		// Set client connection options and connect
		MqttConnectOptions mqOptions = new MqttConnectOptions();
		mqOptions.setCleanSession(true);
		client.connect(mqOptions);

		// subscribe to a topic
		client.subscribe(topic, qos);
	}

}

//definition of the call-back class implements the MqttCallback interface
class SimpleMqttCallBack3 implements MqttCallback {

	public void connectionLost(Throwable throwable) {
		System.out.println("Connection to MQTT broker lost!");
	}

	// What to do when a message arrives.. print it out!
	public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
	//	System.out.println("Message received:\t" + new String(mqttMessage.getPayload()));
		String[] myArray = new String(mqttMessage.getPayload()).split(",");
		// Make a new Data Access Object to write to DB
		// Parse to correct data type before trying to call DAO
		int roomNum = Integer.parseInt(myArray[0]);      
		float tempF = Float.parseFloat(myArray[1]);
        float humF = Float.parseFloat(myArray[2]);
        float pressF = Float.parseFloat(myArray[3]);


        ReadingDAO dao = new ReadingDAO();
		
        
		System.out.println(roomNum);
		System.out.println(tempF);
		System.out.println(humF);
		System.out.println(pressF);



		dao.addReading(roomNum, tempF, humF, pressF);
		//}
		
	}

	public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
	}
}