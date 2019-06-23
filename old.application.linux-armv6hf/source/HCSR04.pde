/**
 * HC-SR04 Demo
 * Demonstration of the HC-SR04 Ultrasonic Sensor
 * Date: August 3, 2016
 * 
 * Description:
 *  Connect the ultrasonic sensor to the Arduino as per the
 *  hardware connections below. Run the sketch and open a serial
 *  monitor. The distance read from the sensor will be displayed
 *  in centimeters and inches.
 * 
 * Hardware Connections:
 *  Arduino | HC-SR04 
 *  -------------------
 *    5V    |   VCC     
 *    7     |   Trig     
 *    8     |   Echo     
 *    GND   |   GND
 *  
 * License:
 *  Public Domain
 
import processing.io.*;
// Pins
int TRIG_PIN = 4;
int ECHO_PIN = 5;

// Anything over 400 cm (23200 us pulse) is "out of range"
int MAX_DIST = 23200;

public void setupHCSR04() {

  // The Trigger pin will tell the sensor to range find
  //pinMode(TRIG_PIN, OUTPUT);
  GPIO.pinMode(TRIG_PIN, GPIO.OUTPUT);
  //GPIO.pinMode(4, GPIO.INPUT);

  // On the Raspberry Pi, GPIO 4 is pin 7 on the pin header,
  // located on the fourth row, above one of the ground pins

  //digitalWrite(TRIG_PIN, LOW);
  GPIO.digitalWrite(TRIG_PIN, GPIO.LOW);


 // frameRate(0.5);

  // We'll use the serial monitor to view the sensor output
  //Serial.begin(9600);
}

public void drawHCSR04() {
/*
   long t1;
   long t2;
   long pulse_width;
  float cm;
  float inches;

  // Hold the trigger pin high for at least 10 us
  //digitalWrite(TRIG_PIN, HIGH);
 GPIO.digitalWrite(4, GPIO.HIGH);
    
  //delay,icroseconds(10);
  //digitalWrite(TRIG_PIN, LOW); 
   GPIO.digitalWrite(4, GPIO.LOW);  

  // Wait for pulse on echo pin
  while ( digitalRead(ECHO_PIN) == 0 );
  //      GPIO.digitalRead(4) == GPIO.HIGH
  
  // Measure how long the echo pin was held high (pulse width)
  // Note: the micros() counter will overflow after ~70 min
  t1 = micros();
  while ( digitalRead(ECHO_PIN) == 1);
  //      GPIO.digitalRead(4) == GPIO.HIGH
  t2 = micros();
  pulse_width = t2 - t1;

  // Calculate distance in centimeters and inches. The constants
  // are found in the datasheet, and calculated from the assumed speed 
  //of sound in air at sea level (~340 m/s).
  cm = pulse_width / 58.0;
  inches = pulse_width / 148.0;

  // Print out results
  if ( pulse_width > MAX_DIST ) {
    print("Out of range");
  } else {
    //theOSCX = map(mouseX, 0, canvasW, 0, 1);
    Serial.print(cm);
    Serial.print(" cm \t");
    Serial.print(inches);
    Serial.println(" in");
  }
  
  // Wait at least 60ms before next measurement
  delay(60);


GPIO.releasePin(TRIG_PIN);
  exit();
}*/
/*/////python code
import RPi.GPIO as GPIO
import time
GPIO.setmode(GPIO.BCM)

TRIG = 23 
ECHO = 24

print "Distance Measurement In Progress"

GPIO.setup(TRIG,GPIO.OUT)
GPIO.setup(ECHO,GPIO.IN)

GPIO.output(TRIG, False)
print "Waiting For Sensor To Settle"
time.sleep(2)

GPIO.output(TRIG, True)
time.sleep(0.00001)
GPIO.output(TRIG, False)

while GPIO.input(ECHO)==0:
  pulse_start = time.time()

while GPIO.input(ECHO)==1:
  pulse_end = time.time()

pulse_duration = pulse_end - pulse_start

distance = pulse_duration * 17150

distance = round(distance, 2)

print "Distance:",distance,"cm"

GPIO.cleanup()
*/
