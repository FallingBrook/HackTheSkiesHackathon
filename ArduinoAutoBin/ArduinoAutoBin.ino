const int IN1 = 5;  // Motor control pin
const int IN2 = 6; // Motor control pin
const int IN3 = 9; // Motor control pin
const int IN4 = 10; // Motor control pin

void setup() {
  pinMode(IN1, OUTPUT);
  pinMode(IN2, OUTPUT);
  pinMode(IN3, OUTPUT);
  pinMode(IN4, OUTPUT);

  Serial.begin(9600);
  Serial.println("Arduino ready to receive commands.");
}

void loop() {
  if (Serial.available() > 0) {
    String command = Serial.readStringUntil('\n'); // Read the incoming command
    command.trim(); // Remove any extra whitespace or newlines
    Serial.println("Received command: " + command);
    // if(command.equalsIgnoreCase("off")){
    //   OFF();
    // }
    
    if (command.equalsIgnoreCase("garbage")) {
      Garbage();
    } else if (command.equalsIgnoreCase("recycling")) {
      Recycling();
    } else if (command.equalsIgnoreCase("compost")) {
      Compost();
    } else {
      Serial.println("Invalid command received.");
    }
  }
}


void Garbage(){
    digitalWrite(IN1, HIGH);
    digitalWrite(IN2, LOW);
    delay(100);
    digitalWrite(IN1, LOW);
    digitalWrite(IN2, LOW);
    delay(1500);
    digitalWrite(IN1, LOW);
    digitalWrite(IN2, HIGH);
    delay(100);
    digitalWrite(IN1, LOW);
    digitalWrite(IN2, LOW);
}
void Compost(){
    digitalWrite(IN3, HIGH);
    digitalWrite(IN4, LOW);
    delay(150);
    digitalWrite(IN3, LOW);
    digitalWrite(IN4, LOW);
    delay(1500);
    digitalWrite(IN3, LOW);
    digitalWrite(IN4, HIGH);
    delay(150);
    digitalWrite(IN3, LOW);
    digitalWrite(IN4, LOW);
}

void Recycling(){
    digitalWrite(IN1, LOW);
    digitalWrite(IN2, HIGH);
    delay(100);
    digitalWrite(IN1, LOW);
    digitalWrite(IN2, LOW);
    delay(1500);
    digitalWrite(IN1, HIGH);
    digitalWrite(IN2, LOW);
    delay(100);
    digitalWrite(IN1, LOW);
    digitalWrite(IN2, LOW);
}
