class LastCallRenderer extends AudioRenderer {


  public String skchName = "Last Call";

  int numBoxes = 3;
  
  color theRed = 255;
  color theBlue = 255;
  color theGreen = 255;
  color theAlpha = 255;
  
  /// width, height, x and y for all boxes
  float boxW0 = 1280;
  float boxH0 = 255;
  float boxX0 = 0;
  float boxY0 = 0;

  //behind bar
  float boxW1 = 600;
  float boxH1 = 20;
  float boxX1 = 260;
  float boxY1 = 0;
  
  //soffits
  float boxW2 = 860;
  float boxH2 = 180;
  float boxX2 = 420;
  float boxY2 = 40;
  
  //long wall
  float boxW3 = 860;
  float boxH3 = 20;
  float boxX3 = 420;
  float boxY3 = 230;
  
  ColorBox theBox;
  
  ArrayList<ColorBox> BoxArray = new ArrayList<ColorBox>();
  
  color theColor = color(theRed, theBlue, theGreen, theAlpha);

  LastCallRenderer(AudioSource source) {
  }

 
  public void loadPresets() {
    getSketchPresets("lastCall", true);   
  }

  public void switchColorMode() {
    println("switching color mode for" + skchName );
    colorMode(HSB);
  }

  public void setupSketch() {
    noStroke();
    colorMode(HSB);
    println("SETTING UPr" + skchName );
    
    
    /// initialize our box array
    for(int i=0; i<=numBoxes; i++){
      
      switch (i){
        
        case 0:
         theBox = new ColorBox(boxW0, boxH0, boxX0, boxY0);
         break;
         
         case 1:
         theBox = new ColorBox(boxW1, boxH1, boxX1, boxY1);
         break;
         
         case 2:
         theBox = new ColorBox(boxW2, boxH2, boxX2, boxY2);
         break;
         
         case 3:
         theBox = new ColorBox(boxW3, boxH3, boxX3, boxY3);
         break;
         
        
        
      }
      println("ADDING BOX: " + i);
      BoxArray.add(theBox);
      
    }

    getSketchPresets("lastCall", true);
     
  }


  /// set the onClick function using the global X and Y values
  public void doMouseDrag(){
    
  }
  public void onClick() {
    //// do soemthing with (theX, theY);
  }

  public void renderSketch() {
     fill(0);
    rect(0,0,canvasW,canvasH);

    /// update fader arrays
    
     /// draw all boxes
    for (int i=0; i<BoxArray.size(); i++){
      
      theBox = BoxArray.get(i);
      switch(i){
        
        case 0:
          theBox.theHue = setcolorMode;
        break;

        case 1:
          theBox.theHue = vFader2;
        break;

        case 2:
          theBox.theHue = vFader3;
        break;
        
        case 3:
          theBox.theHue = vFader4;
        break;

        case 4:
          theBox.theHue = vFader5;
        break;
      }
      theBox.drawBox();
    }
    
    
    
   
    
  }


  //// end class
}


//////////// BOX CLASS /////////////////
/////////////////////////////////////////
class ColorBox {
  
  float tWidth;
  float tHeight;
  float boxX;
  float boxY;
  
  color boxColor = color(255,255,255);
  
  int theHue = 255;
  
  ColorBox(float tw, float th, float tx, float ty){
    tWidth = tw;
    tHeight = th;
    boxX = tx;
    boxY = ty;
  }
  
  /// dont really need this
  void initBox(){
    
  }
  
  void drawBox(){
    noStroke();
    fill(theHue, 255,255);
    rect(boxX, boxY, tWidth, tHeight);
  }
  
  
  
  
  
}