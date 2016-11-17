//build a map of ledPos[logical led positions] = map of led positions on canvas


void mapper() {

  int internalX = 0;
  int internalY = 0;


  //canvasW = 300;
  //canvasH = 64;
  //ledsW = 240;
  //ledsH = 2;

  int s;
  boolean up;

  internalX = 80;
  internalY = 24;//starting point on canvas
  s=1;
  up = true;


  for (int x = 0; x < 240; x++) {//start and number of pixels on strip

    //v strip #
    ledPos[xyPixels(x, 0, ledsW)] = xyPixels(internalX, internalY, canvasW);
    println(internalX + " " + internalY + " " + s);
    //make grid
    s++;//going up or down
    if (s > 12) {//height in pixels
      s = 1;
      internalX -= 2;//distance for x
      up = !up;
      println("flipY" + up);
    } else {
      if (up == true) {
        internalY -= 2;//distance for y
      } else {
        internalY += 2;
      }
    }
  }
  
  println("split");


  for (int x = 0; x < 240; x++) {//start and number of pixels on strip

    //v strip #
    ledPos[xyPixels(x, 1, ledsW)] = xyPixels(internalX, internalY, canvasW);
    println(internalX + " " + internalY + " " + s);
    //make grid
    s++;//going up or down
    if (s > 12) {//height in pixels
      s = 1;
      internalX -= 2;//distance for x
      up = !up;
      println("flipY" + up);
    } else {
      if (up == true) {
        internalY -= 2;//distance for y
      } else {
        internalY += 2;
      }
    }
  }

/*
   internalX = 234;
   internalY = 51;//starting point on canvas
   
   for (int x = 0; x < 234; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 2, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }

   internalX = 234;
   internalY = 55;//starting point on canvas
   
   for (int x = 0; x < 234; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 3, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }

   internalX = 234;
   internalY = 59;//starting point on canvas
   
   for (int x = 0; x < 234; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 4, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }


   internalX = 235;
   internalY = 51;//starting point on canvas
   
   for (int x = 0; x < 60; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 5, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }

   internalX = 294;
   internalY = 55;//starting point on canvas
   
   for (int x = 61; x < 120; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 5, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }

   internalX = 235;
   internalY = 59;//starting point on canvas
   
   for (int x = 121; x < 180; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 5, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }


   internalX = 234;
   internalY = 4;//starting point on canvas
   
   for (int x = 0; x < 234; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 6, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
   
   internalX = 235;
   internalY = 4;//starting point on canvas
   
   for (int x = 0; x < 60; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 7, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
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
  background(0);
}

void drawPixelPusher() {
  loadPixels();

   //Pixel blackP = new Pixel((byte)0, (byte)0, (byte)0);

  if (testObserver.hasStrips) {
    registry.startPushing();
    List<Strip> strips = registry.getStrips( );
    //   List<Strip> strips1 = registry.getStrips(1);
    //   strips1.addAll(registry.getStrips(2));  
    //   strips1.addAll(registry.getStrips(3));  
    //List<Strip> strips2 = registry.getStrips(2);      

    colorMode(HSB, 255);

    for (int y = 0; y < ledsH; y++) {     
      for (int x = 0; x < ledsW; x++) {


        thisLedPos = ledPos[xyPixels(x, y, ledsW)];

        //  int lX = xPixels(thisLedPos, ledsW);
        //  int lY = yPixels(thisLedPos, ledsW);
        //   pixels[xyPixels(x,y,canvasW)] = color(r, g, b);
        color c = pixels[thisLedPos];


         
         c = color(hue(c), saturation(c), brightness(c) - dimmer1); 
         
         if (y >= 0 && y <= 1) //diffused wall
         c = color(hue(c), saturation(c), brightness(c) - dimmer2); 
         /*
         if (y >= 2 && y <= 5) //bar bottles
         c = color(hue(c), saturation(c), brightness(c) - dimmer3); 
         
         if (y >= 6 && y <= 7) //bar seats
         c = color(hue(c), saturation(c), brightness(c) - dimmer4);

         if (y >= 8 && y <= 9) //kitchen seats
         c = color(hue(c), saturation(c), brightness(c) - dimmer5);

         if (y >= 2 && y <= 4 && x >= 235) // black out unused ends of strips
           c = color(0,0,0);
         */
        Pixel p = new Pixel((byte)red(c), (byte)green(c), (byte)blue(c));
        if (y < strips.size() && y < 2) {
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

