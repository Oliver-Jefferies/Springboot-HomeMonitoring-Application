function createCustomer() {
    // Open dialog boxes to get input from the user
const firstName = prompt("Enter Customer First Name:");
    const lastName = prompt("Enter Customer Last Name");
    const phoneNum = prompt("Enter Customer Phone Number:");


    // If user cancels, stop execution
    if (!firstName || !lastName || !phoneNum) {
        alert("All fields are required!");
        return;
    }

    // Create the customer object
    const customerData = { firstName, lastName, phoneNum};

    // Send the data to the backend using Fetch API
    fetch('/Customers', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(customerData)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Customer created:', data);
        alert(`Customer Created:\nFirst Name: ${data.firstName}\nLast Name: ${data.lastName}\nPhone: ${data.phoneNum}`);
    })
    .catch(error => console.error('Error:', error));
}

function createLocation() {
    // Open dialog boxes to get input from the user
	const customerId = prompt("Enter Location Owner ID:");
    const address = prompt("Enter Location Address:");
    const locationType = prompt("Enter Location Type:");


    // If user cancels, stop execution
    if (!customerId || !address || !locationType) {
        alert("All fields are required!");
        return;
    }

    // Create the location object
    const locationData = { customerId, address, locationType };

    // Send the data to the backend using Fetch API
    fetch('/Locations', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(locationData)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Location created:', data);
        alert(`Location Created:\nCustomer Id: ${data.customerId}\nAddress: ${data.address}\nLocation Type: ${data.locationType}`);
    })
    .catch(error => console.error('Error:', error));
}

function createRoom() {
    // Open dialog boxes to get input from the user
	const location_id = prompt("Enter Location ID:");
    const sensorType = prompt("Enter Room Sensor Types:");
    const roomType = prompt("Enter Room Type:");
	const floorNum = prompt("Enter Room Floor Number:");


    // If user cancels, stop execution
    if (!location_id || !sensorType || !roomType || !floorNum) {
        alert("All fields are required!");
        return;
    }

    // Create the Room object
    const roomData = { location_id, sensorType, roomType, floorNum };

    // Send the data to the backend using Fetch API
    fetch('/Rooms', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(roomData)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Room created:', data);
        alert(`Room Created:\nLocation ID: ${data.location_id}\nSensorType: ${data.sensorType}\nName: ${data.roomType}\nFloor Number: ${data.floorNum}`);
    })
    .catch(error => console.error('Error:', error));
}

function fetchReadings() {
fetch('/Readings')  //Get Request
    .then(response => response.json())
    .then(readings => {
		//Debug readings recieved through browser console
        console.log("Readings received:", readings); 
	
        fetch('/Rooms') // Second Get Request
            .then(response => response.json())
            .then(rooms => {
                console.log("Rooms received:", rooms);

                // Build Room Map of Name of Room to Room ID to correspond with readings
                const roomMap = {};
                rooms.forEach(room => {
                    if (room.roomId && room.roomType) {
                        roomMap[room.roomId] = room.roomType;
                    }
                });
				
				// Print to console for debugging
                console.log("Room Map:", roomMap); 

                // Aggregate Data to a room type
                const roomAverages = {};

				//For each reading get corresponding room Id for room Type
                readings.forEach(reading => {
                    const roomType = roomMap[reading.roomId];

					// If a room type exists then Set total values for that room type
                    if (roomType) {
                        if (!roomAverages[roomType]) {
                            roomAverages[roomType] = {
                                tempSum: 0,
                                humSum: 0,
                                pressSum: 0,
                                count: 0
                            };
                        }
						// Add per room Type and divide by nunber of type of room for average
                        roomAverages[roomType].tempSum += reading.temp;
                        roomAverages[roomType].humSum += reading.humidity;
                        roomAverages[roomType].pressSum += reading.pressure;
                        roomAverages[roomType].count++;
						
                    }
                });

                console.log("Room Averages (before computing):", roomAverages);

                // Compute Averages
                const roomLabels = [];
                const avgTemp = [];
                const avgHum = [];
                const avgPress = [];

				//Push data to chart
                Object.keys(roomAverages).forEach(roomType => {
                    const data = roomAverages[roomType];
                    roomLabels.push(roomType);
                    avgTemp.push(data.tempSum / data.count);
                    avgHum.push(data.humSum / data.count);
                    avgPress.push(data.pressSum / data.count);
                });

				//Log to console for debugging reasons
                console.log("Final Averaged Data:");
                console.log("Room Labels:", roomLabels);
                console.log("Average Temperature:", avgTemp);
                console.log("Average Humidity:", avgHum);
                console.log("Average Pressure:", avgPress);

                // Call Render Chart with Averages
                renderChart(roomLabels, avgTemp, avgHum, avgPress);
            })
            .catch(error => console.error('Error fetching Rooms:', error));
    })
    .catch(error => console.error('Error fetching Readings:', error));
}

//Pass labels and variables into chart function
function renderChart(labels, tempData, humData, pressData) {

	//Get chart canvas
    const ctx = document.getElementById('readingsChart').getContext('2d');

    // Destroy the old chart instance if it exists
    if (window.myChart) {
        window.myChart.destroy();
    }

    window.myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,  // Room Names
            datasets: [
                {
                    label: 'Average Temperature (°C)',
                    data: tempData,
                    backgroundColor: 'rgba(255, 99, 132, 0.5)',
                    borderColor: 'rgba(255, 99, 132, 1)',
                    borderWidth: 1,
					yAxisID: 'y-left' // 1st y axis 
                },
                {
                    label: 'Average Humidity (%)',
                    data: humData,
                    backgroundColor: 'rgba(54, 162, 235, 0.5)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1,
					yAxisID: 'y-left'
                },
                {
                    label: 'Average Pressure (hPa)',
                    data: pressData,
                    backgroundColor: 'rgba(75, 192, 192, 0.5)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1,
					yAxisID: 'y-right' // Second y axis
                }
            ]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                },
				//Hard define left and right to prevent third axis
				'y-left': { 
				    type: 'linear',
				    position: 'left',
				    title: {
				        display: true,
				        text: 'Temperature (°C) & Humidity (%)'
				    },
				    beginAtZero: true
				},
				'y-right': {
				    type: 'linear',
				    position: 'right',
				    title: {
				        display: true,
				        text: 'Pressure (hPa)'
				    },
				    beginAtZero: false
				}
            }
        }
    });
}


