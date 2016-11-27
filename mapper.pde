//build a map of ledPos[logical led positions] = map of led positions on canvas


void mapper() {

  int internalX = 0;
  int internalY = 0;
  //canvasW = 300;
  //canvasH = 64;
  //ledsW = 240;
  //ledsH = 2; 

  //  int s;
  //  boolean up;
 boolean up = false;
 boolean down = false;
 boolean left = false;
 boolean right = false;
/*
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

   internalX = 280;
   internalY = 40;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 0, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX--;//direction
   }
 

   internalX = 354;
   internalY = 40;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 1, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX--;//direction
   }
//chamged for testing   
   internalX = 640;
   internalY = 250;//starting point on canvas
   
   for (int x = 0; x < 119; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 2, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalY-= 2;
   }
   internalX = 640;
   internalY = 40;//starting point on canvas
   
   for (int x = 120; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 2, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX-= 4;
   }

   internalX = 500;
   internalY = 40;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 3, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }

   internalX = 240;
   internalY = 19;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 4, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }


   internalX = 240;
   internalY = 11;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 5, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }

  


   internalX = 240;
   internalY = 4;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 6, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
   
   internalX = 240;
   internalY = 4;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 7, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
//*/

//group2
//controller1
//soffit by door
   internalX = 1160;
   internalY = 220;//starting point on canvas
   
   for (int x = 0; x < 80; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 16, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX++;//direction
   }
   
   internalX = 1260;
   internalY = 220;//starting point on canvas
   
   for (int x = 81; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 16, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalY--;//direction
   }
//soffit 2 by bar
   internalX = 1100;
   internalY = 100;//starting point on canvas
   
   for (int x = 0; x < 60; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 17, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalY--;//direction
   }
   
    internalX = 1100;
   internalY = 40;//starting point on canvas
   
   for (int x = 61; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 17, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX--;//direction
   }
   
//soffit 2 short
  
    internalX = 1000;
   internalY = 220;//starting point on canvas
   
   for (int x = 0; x < 100; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 18, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }
    internalX = 1100;
   internalY = 220;//starting point on canvas
   
   for (int x = 101; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 18, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalY--;
   }
   
//soffit 2 far side
   internalX = 1020;
   internalY = 220;//starting point on canvas
   
   for (int x = 0; x < 80; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 19, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
   
    internalX = 940;
   internalY = 220;//starting point on canvas
   
   for (int x = 81; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 19, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalY--;
   }
//soffit 1 far side
   internalX = 1100;
   internalY = 210;//starting point on canvas
   
   for (int x = 0; x < 150; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 20, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalY--;
   }

 internalX = 1100;
   internalY = 100;//starting point on canvas
   
   for (int x = 150; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 20, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }
//none
   internalX = 240;
   internalY = 11;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 21, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
  
//blank

   internalX = 240;
   internalY = 4;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 22, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
   
   internalX = 240;
   internalY = 4;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 23, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }

//controller 2
//blank
   internalX = 240;
   internalY = 18;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 24, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX--;//direction
   }
   
//soffit 3 long strip by door
   internalX = 840;
   internalY = 220;//starting point on canvas
   
   for (int x = 0; x < 80; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 25, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX++;//direction
   }
   
   internalX = 920;
   internalY = 220;//starting point on canvas
   
   for (int x = 81; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 25, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalY--;//direction
   }
//soffit 3 short
   internalX = 760;
   internalY = 120;//starting point on canvas
   
   for (int x = 0; x < 100; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 26, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalY++;
   }
   internalX = 760;
   internalY = 220;//starting point on canvas
   
   for (int x = 101; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 26, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }
//soffit 4 long by bar
   internalX = 740;
   internalY = 100;//starting point on canvas
   
   for (int x = 0; x < 60; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 27, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalY--;
   }
   internalX = 740;
   internalY = 40;//starting point on canvas
   
   for (int x = 61; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 27, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
//soffit 4 short
   internalX = 740;
   internalY = 100;//starting point on canvas
   
   for (int x = 0; x < 80; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 28, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalY++;
   }
   internalX = 740;
   internalY = 220;//starting point on canvas
   
   for (int x = 81; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 28, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
//soffit 3 long by bar
   internalX = 760;
   internalY = 120;//starting point on canvas
   
   for (int x = 0; x < 80; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 29, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalY--;
   }
  internalX = 760;
   internalY = 40;//starting point on canvas
   
   for (int x = 81; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 29, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }
  

//soffit 4 long outside
   internalX = 660;
   internalY = 220;//starting point on canvas
   
   for (int x = 0; x < 80; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 30, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }
   internalX = 580;
   internalY = 220;//starting point on canvas
   
   for (int x = 81; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 30, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalY--;
   }
//blank
   internalX = 240;
   internalY = 4;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 31, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
   
//group2 controller3   
//soffit 5 short
   internalX = 560;
   internalY = 220;//starting point on canvas
   
   for (int x = 0; x < 60; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 32, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX--;//direction
   }
   internalX = 500;
   internalY = 220;//starting point on canvas
   
   for (int x = 61; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 32, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalY--;//direction
   }
//blank
   internalX = 240;
   internalY = 11;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 33, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX--;//direction
   }
//soffit 5 long towards bar
   internalX = 420;
   internalY = 120;//starting point on canvas
  
   for (int x = 0; x < 80; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 34, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalY--;
   }
   internalX = 420;
   internalY = 40;//starting point on canvas
  
   for (int x = 81; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 34, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }
//soffit 5 long inside
   internalX = 560;
   internalY = 220;//starting point on canvas
   
   for (int x = 0; x < 60; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 35, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }
   internalX = 580;
   internalY = 220;//starting point on canvas
   
   for (int x = 61; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 35, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalY--;
   }
//blank
   internalX = 240;
   internalY = 19;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 36, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
//blank

   internalX = 240;
   internalY = 11;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 37, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }

//blank
   internalX = 240;
   internalY = 4;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 38, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
//
   internalX = 240;
   internalY = 4;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 39, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
   
//group 2 controller4
//
   internalX = 240;
   internalY = 18;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 40, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX--;//direction
   }
//
   internalX = 240;
   internalY = 11;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 41, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX--;//direction
   }
//
   internalX = 240;
   internalY = 11;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 42, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
//
   internalX = 240;
   internalY = 15;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 43, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
//far banquette short
   internalX = 240;
   internalY = 220;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 44, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }

//far banquette long
   internalX = 210;
   internalY = 220;//starting point on canvas
   
   for (int x = 0; x < 100; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 45, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
   internalX = 50;
   internalY = 220;//starting point on canvas
   
   for (int x = 101; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 45, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalY--;
   }
//
  
//banquette close
   internalX = 360;
   internalY = 220;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 46, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }
//  banquette mid
   internalX = 360;
   internalY = 200;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 47, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }

//group2 controller5
//blank
   internalX = 240;
   internalY = 18;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 48, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX--;//direction
   }
//bubble lounge bar
   internalX = 410;
   internalY = 30;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 49, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX--;//direction
   }
//front bar
   internalX = 560;
   internalY = 30;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 50, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }
//front bar short
   internalX = 460;
   internalY = 30;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 51, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }
//blank
   internalX = 240;
   internalY = 19;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 52, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
//
   internalX = 240;
   internalY = 11;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 53, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
//blank
   internalX = 240;
   internalY = 4;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 54, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }

//blank
   internalX = 240;
   internalY = 4;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 55, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
   
//controller 6  
//back long banquette
   internalX = 240;
   internalY = 10;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 56, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX--;//direction
   }
//bubble lounge bar back
   internalX = 300;
   internalY = 10;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 57, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX++;//direction
   }
//blank
   internalX = 240;
   internalY = 11;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 58, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
//blank
   internalX = 240;
   internalY = 15;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 59, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
//blank
   internalX = 240;
   internalY = 19;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 60, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
//blank

   internalX = 240;
   internalY = 11;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 61, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
//
  


   internalX = 240;
   internalY = 4;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 62, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
//   blank
   internalX = 240;
   internalY = 4;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 63, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
   
//bar back left
   internalX = 540;
   internalY = 12;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 64, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX--;//direction
   }
//bar back right
   internalX = 780;
   internalY = 12;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 65, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX++;//direction
   }
//bar back mid left
   internalX = 640;
   internalY = 12;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 66, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
//bar back mid right
   internalX = 640;
   internalY = 12;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 67, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }
//bar cove long right
   internalX = 560;
   internalY = 6;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 68, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }
//bar cove short left

   internalX = 560;
   internalY = 6;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 69, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
//blank
   internalX = 240;
   internalY = 4;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 70, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
//blank   
   internalX = 240;
   internalY = 4;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 71, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }
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

    colorMode(HSB, 255);

    for (int y = 0; y < ledsH; y++) {     
      for (int x = 0; x < ledsW; x++) {


        thisLedPos = ledPos[xyPixels(x, y, ledsW)];

        //  int lX = xPixels(thisLedPos, ledsW);
        //  int lY = yPixels(thisLedPos, ledsW);
        //   pixels[xyPixels(x,y,canvasW)] = color(r, g, b);
        color c = pixels[thisLedPos];


         
         c = color(hue(c), saturation(c), brightness(c) - dimmer1); 
         
         if (y >= 16 && y <= 71) //upstairs
         c = color(hue(c), saturation(c), brightness(c) - dimmer2); 
         
         //if (y >= 40 && y <= 47) //downstairs
         //c = color(hue(c), saturation(c), brightness(c) - dimmer3); 
         
         //if (y >= 16 && y <=39 ) //soffits
         //c = color(hue(c), saturation(c), brightness(c) - dimmer4);
/*
         if (y >= 8 && y <= 9) //kitchen seats
         c = color(hue(c), saturation(c), brightness(c) - dimmer5);

         if (y >= 2 && y <= 4 && x >= 235) // black out unused ends of strips
           c = color(0,0,0);
         */
         
         float adjustedGreen = green(c) * .5;
         float adjustedBlue = blue(c) * .125;
         
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