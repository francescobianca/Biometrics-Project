/*************************************************** 
  This is an example sketch for our optical Fingerprint sensor

  Designed specifically to work with the Adafruit BMP085 Breakout 
  ----> http://www.adafruit.com/products/751

  These displays use TTL Serial to communicate, 2 pins are required to 
  interface
  Adafruit invests time and resources providing this open source code, 
  please support Adafruit and open-source hardware by purchasing 
  products from Adafruit!

  Written by Limor Fried/Ladyada for Adafruit Industries.  
  BSD license, all text above must be included in any redistribution
 ****************************************************/

#include <LiquidCrystal.h>
#include <Adafruit_Fingerprint.h>

// On Leonardo/Micro or others with hardware serial, use those! #0 is green wire, #1 is white
// uncomment this line:
// #define mySerial Serial1

// For UNO and others without hardware serial, we must use software serial...
// pin #2 is IN from sensor (GREEN wire)
// pin #3 is OUT from arduino  (WHITE wire)
// comment these two lines if using hardware serial
#include <SoftwareSerial.h>


SoftwareSerial mySerial(2, 3);
LiquidCrystal lcd(9, 4, 5, 6, 7, 8);  

Adafruit_Fingerprint finger = Adafruit_Fingerprint(&mySerial);
const int buttonPin = 10;     // the number of the pushbutton pin
int buttonState = 0;   
char incomingByte = ' '; // for incoming serial data
String string_finger = "";

void setup()  
{
  Serial.begin(9600);
  lcd.begin(16, 2);  
  lcd.print("Start Lesson");
  delay(2000);
  lcd.clear();
  //Serial.begin(9600);

  // set the data rate for the sensor serial port
  finger.begin(57600);
  finger.getTemplateCount();
}

void loop()                     // run over and over again
{
  /*int finger_code = getFingerprintIDez();
  delay(50);            //don't ned to run this at full speed.
  
  if (finger_code != -1) {
    Serial.begin(9600);
    Serial.print(finger_code);
    Serial.end();
    delay(50);
  }*/

  if (Serial.available() > 0) {
    // read the incoming byte:
    //Serial.begin(9600);
    incomingByte = Serial.read();

    // say what you got:
    //Serial.print("I received: ");
    //Serial.println(incomingByte);
    //Serial.end();
      while (incomingByte == 'a') {
        
  // read the state of the pushbutton value:
  buttonState = digitalRead(buttonPin);
        
     // start lesson 
      String finger_code = getFingerprintIDez();
      delay(50);            //don't ned to run this at full speed.
      
      if (finger_code != "") {
        /*Serial.begin(9600);
        Serial.print(finger_code);
        Serial.end();
        delay(50);*/
        string_finger = string_finger + finger_code;
      }

      // send data only when you receive data:
      // read the incoming byte:
      //incomingByte = Serial.read();
      //Serial.print(incomingByte);
      if (buttonState == HIGH) {
        string_finger = string_finger+'*';
        Serial.print(string_finger);
        delay(50);
        lcd.print("End lesson");
        incomingByte = 'b';
        //Serial.end();
      }
      delay(50);    
    } /*else if (incomingByte == 'b') { // fine lezione
        //Serial.begin(9600);
        Serial.print(string_finger);
        //Serial.end();
        delay(50);
        string_finger="";
    }*/
    
  }
  
}



// returns -1 if failed, otherwise returns ID #
String getFingerprintIDez() {
  
  uint8_t p = finger.getImage();
  if (p != FINGERPRINT_OK)  return "";

  p = finger.image2Tz();
  if (p != FINGERPRINT_OK)  return "";

  p = finger.fingerFastSearch();
  if (p != FINGERPRINT_OK)  return "";
  
  // found a match!
  lcd.setCursor(0,0);
  lcd.print("Found ID "+String(finger.fingerID)+", "+String(finger.confidence));
  lcd.setCursor(0,1);
  lcd.print("Next one");
  delay(3000);
  lcd.clear();
  
  //lcd.print(" with confidence of ");
  String foundMatch = String(finger.fingerID)+":"+String(finger.confidence)+";"; 
  return foundMatch;
}
