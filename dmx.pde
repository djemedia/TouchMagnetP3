
//PImage dmximg;

DmxP512 dmxOutput;
int universeSize=128;

boolean LANBOX=false;
String LANBOX_IP="192.168.1.77";

boolean DMXPRO=true;
String DMXPRO_PORT = "/dev/tty.usbserial-6AWY0JLI";//case matters ! on windows port must be upper cased.
//String DMXPRO_PORT="/dev/ttyUSB0";//case matters ! on windows port must be upper cased.
int DMXPRO_BAUDRATE=115000;

void setupDMX() {
  
 /* dmximg = new PImage(170, 1, PApplet.RGB);
 void mapSection(int sketchX, int sketchY, int startDMX, int endDMX)
{
   
   for(int i = startDMX; i < endDMX; i++){
     
     dmximg.set(i % dmximg.width, i / dmximg.width, get(sketchX + (i % width), sketchY + (i / width)));
     sketchX-= 4;
  
  }  
}
*/


  dmxOutput=new DmxP512(this, universeSize, false);

  if (LANBOX) {
    dmxOutput.setupLanbox(LANBOX_IP);
  }

  if (DMXPRO) {
    dmxOutput.setupDmxPro(DMXPRO_PORT, DMXPRO_BAUDRATE);
  }

  /*wrgb
   for (int d=1; d<200; d+=4){
   
   dmxOutput.set(d,255);
   }
   */
dmxPos = new int[dmxAddr*dmxUniv];
  //starting dmx address, dmx universe, xpos, ypos
//  dmxPos[1] = xyPixels(10, 10, canvasW);

  dmxPos[xyPixels(1, 1, dmxUniv)] = xyPixels(10, 10, canvasW);
  dmxPos[xyPixels(4, 1, dmxUniv)] = xyPixels(10, 50, canvasW);
  dmxPos[xyPixels(7, 1, dmxUniv)] = xyPixels(10, 90, canvasW);
}

void drawDMX() {
  loadPixels();
  //dmximg.loadPixels();
  
  //    colorMode(HSB, 255);
  
  /*
    int sketchX = 0;
  int sketchY = 0;
  int startDMX = 0;
  int endDMX = 0;
  
  //address the fixtures
  //mapSection(sketchX, sketchY, startDMX, endDMX);
  mapSection(240, 240, 0,1);
  */

  for (int y = 1; y < dmxAddr+1; y+=3) {     
    for (int x = 1; x < dmxUniv+1; x++) {
      thisDmxPos = dmxPos[xyPixels(x, y, dmxUniv)];
      //thisDmxPos = ledPos[xyPixels(x, y, dmxUniv)];
      color c = pixels[thisDmxPos];          
      dmxOutput.set(y, (int)red(c));
      dmxOutput.set(y+1, (int)green(c));
      dmxOutput.set(y+2, (int)blue(c));
    }
  }
/*
      color c = pixels[1];          
      Pixel p = new Pixel((byte)red(c), (byte)green(c), (byte)blue(c));
      dmxOutput.set(0+1, (int)red(c));
      dmxOutput.set(0+2, (int)green(c));
      dmxOutput.set(0+3, (int)blue(c));
*/
}