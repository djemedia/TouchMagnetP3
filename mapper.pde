//build a map of ledPos[logical led positions] = map of led positions on canvas


void mapper() {

  int internalX = 0;
  int internalY = 0;
  int s=1;

  boolean up = false;
  boolean down = false;
  boolean left = false;
  boolean right = false;
//

//current mapper
   internalX = 300;
   internalY = 8;//starting point on canvas
   
   for (int x = 0; x < 300; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 0, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX--;//direction
   }
 

   internalX = 300;
   internalY = 16;//starting point on canvas
   
   for (int x = 0; x < 300; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 1, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX--;//direction
   }
   
  
   internalX = 300;
   internalY = 32;//starting point on canvas
   
   for (int x = 0; x < 300; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 2, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX--;
   }
   
   internalX = 300;
   internalY = 40;//starting point on canvas
   
   for (int x = 0; x < 300; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 3, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }

    internalX = 10;
   internalY = 48;//starting point on canvas
   
   for (int x = 0; x < 300; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 4, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }
   
   internalX = 10;
   internalY = 56;//starting point on canvas
   
   for (int x = 0; x < 300; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 5, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }

   internalX = 10;
   internalY = 8;//starting point on canvas
   
  //grid mapper
  for (int x = 0; x < 300; x++) {//start and number of pixels on strip

    //v strip #
    ledPos[xyPixels(x, 6, ledsW)] = xyPixels(internalX, internalY, canvasW);
    println(internalX + " " + internalY + " " + s);
    //make grid
    s++;//going up or down
    if (s > 8) {//height in pixels
      s = 1;
      internalX += 4;//distance for x
      up = !up;
      println("flipY" + up);
    } else {
      if (up == true) {
        internalY -= 4;//distance for y
      } else {
        internalY += 4;
      }
    }
  }
  

   internalX = 300;
   internalY = 64;//starting point on canvas
   
   for (int x = 0; x < 300; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x,7, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }

/*future mapper
//
// mapPixels(internalX, internalY, startPixel, endPixel, stripno, direction);

for (int x = startPixel; x < endPixel; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, stripno, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX++;//direction
   }
if(down == true){
    internalY++;  
  } 
  if(up == true){
    internalY--;
  }
  if(left == true){
    internalX--;
  }
  if(right == true){
    internalX++;
  }


*/


}


int xyPixels(int x, int y, int yScale) {
  return(x+(y*yScale));
}

int xPixels(int pxN, int yScale) {
  return(pxN % yScale);
}

int yPixels(int pxN, int yScale) {
  return(pxN / yScale);
}

void setupPixelPusher() {
  ledPos = new int[ledsW*ledsH]; //create array of positions of leds on canvas
  mapper();
  registry = new DeviceRegistry();
  testObserver = new TestObserver();
  registry.addObserver(testObserver);
  //registry.setAntiLog(true);
  registry.setLogging(false);
  //background(0);
}

void drawPixelPusher() {
  try{
    loadPixels();
  } catch(Exception e){
    
    println("error loading pixel pusher load pixels function: " + e);
  }

   //Pixel blackP = new Pixel((byte)0, (byte)0, (byte)0);

  if (testObserver.hasStrips) {
    registry.startPushing();
    List<Strip> strips = registry.getStrips( );
    //   List<Strip> strips1 = registry.getStrips(1);
    //   strips1.addAll(registry.getStrips(2));  
    //   strips1.addAll(registry.getStrips(3));  
    //List<Strip> strips2 = registry.getStrips(2);      

    colorMode(HSB, 255, 255, 255, 100);

    for (int y = 0; y < ledsH; y++) {     
      for (int x = 0; x < ledsW; x++) {


        thisLedPos = ledPos[xyPixels(x, y, ledsW)];

        color c = pixels[thisLedPos];
        
         c = color(hue(c), saturation(c), brightness(c) - dimmer1); 
         
       //  if (y >= 16 && y <= 71) //upstairs
        // c = color(hue(c), saturation(c), brightness(c) - dimmer2); 

         //if (y >= 2 && y <= 4 && x >= 235) // black out unused ends of strips
          //c = color(0,0,0);
          //float adjustedGreen = green(c) * .5;
         //float adjustedBlue = blue(c) * .125;
         
        // Pixel p = new Pixel((byte)red(c), (byte)adjustedGreen, (byte)adjustedBlue);
        Pixel p = new Pixel((byte)red(c), (byte)green(c), (byte)blue(c));
        if (y < strips.size()) {
        //if (y < strips.size() && y >= 16) {
        //if (y == 6 || y == 7) {
          //if (y < strips1.size() && y!=34) {
          //if (y < strips1.size() && y==65) {
          strips.get(y).setPixel(p, x);
        }

        //if (y < strips2.size()) {
        //  strips2.get(y).setPixel(blackP, x);
        //}
      }
    }
  }
}