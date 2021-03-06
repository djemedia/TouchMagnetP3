/*
int fitzhughColor = 40;     // <--  New line for color memory
int fitzhughBrightness = 255;
int fitzhughContrast = 40;
*/

class FitzhughRenderer extends AudioRenderer {
  public String skchName = "FITZHUGH";
  int rotations;
  
  int w=canvasW;
  int h=canvasH;
  
  //MovieMaker mm;
  
  float gridU[][] = new float[w][h];
  float gridV[][] = new float[w][h];
  float gridNext[][] = new float[w][h];
  
  float reactionRate = 0.025;
  float reactionFader = 0.05;
  
  // 3 different ways to make diffusion
  // diffusion rate is constant
  float diffusionRateU = .04; // the inhabitor
  // diffusion rate is different from x to y direction
  float diffusionRateUX = .04;
  float diffusionRateUY = .03;
  // diffusion rate is changing in space
  float diffRateUYarr[][] = new float[w][h];
  float diffRateUXarr[][] = new float[w][h];
  
  float diffusionRateV = .01; //the activator
  
  
  float deltaT = 4; // time step
  float deltaXSq = 1.0; // x step
  
  float[][] Farr = new float[w][h];
  float[][] karr = new float[w][h];
  
  boolean movieOn = false;
  int count=0;
  
  int oX;
  int oY;
  
  //int setcolorMode = 0;
  //int vFader2 = 255;
  //int vFader3 = 200;
  
    
  FitzhughRenderer(AudioSource source) {
      //rotations =  (int) source.sampleRate() / source.bufferSize();
  }
  
   
  public void loadPresets() {
    println("Loading presets for" + skchName );
    getSketchPresets("fitzhugh", true);   
  }

  public void setupSketch() {
     //noStroke();
     colorMode(HSB, 255, 255, 255,255);
  //  if(movieOn) mm = new MovieMaker(this,width,height,"reaction"+day()+hour()+minute()+second()+".mov",30, MovieMaker.H263, MovieMaker.HIGH);
    //int w=canvasW;
    //int h=canvasH;
    
    //colorMode(HSB, 255);
    /*
    setcolorMode = fitzhughColor;     // <--  New line for color memory 
    vFader3 = fitzhughBrightness;
    vFader4 = fitzhughContrast;
    */
    //int fader = h;
    //getSketchPresets("fitzhugh", true);    
    /*
    // random to make it 2D otherwise it will just be 1d gradient like
    for(int i=0;i<w;++i) {
      for(int j=0;j<h;++j) {
        gridU[i][j] = .5+random(-.01,.01);
        //gridV[i][j] = .25+random(-.01,.01);
        gridV[i][j] = .25+random(-.01,.01);
      }
    }
  */
    setupF();
    setupK();
    setupDiffRates();
  }

  void renderSketch() {
   // colorMode(HSB, 255, 255, 255);
    diffusionU();
    diffusionV();
    reaction();
  
    loadPixels();
    for(int i=0;i<w;++i) {
      for(int j=0;j<h;++j) {
        //pixels[j*w+i] = color(gridU[i][j]*setcolorMode+oX, vFader2,gridU[i][j]*vFader3+oY);
        //pixels[j*w+i] = color(255*gridU[i][j]-setcolorMode, vFader2,gridU[i][j]*vFader3);
       //pixels[j*w+i] = color((gridU[i][j]*setcolorMode-20), vFader2,gridU[i][j]*vFader3);
    pixels[j*w+i] = color(((setcolorMode+.01)*gridU[i][j]+setcolorMode-vFader4-20), vFader2,gridU[i][j]*vFader3);  
  }
    }
    updatePixels();
  
    //if (count%2==0) {
    //  if(movieOn) mm.addFrame();
    //}
    count++;
  }
  
  // diffusion of chemical u
  void diffusionU() {
    for(int i=0;i<w;++i) {
      for(int j=0;j<h;++j) {
  
        //int fader = (int)map(vFader4, 0, 255, 1, 480);
        gridNext[i][j] = gridU[i][j]+deltaT/deltaXSq*
          (diffRateUXarr[i][j]*(gridU[(i-1+w)%w][j]
          +gridU[(i+1)%w][j]-2*gridU[i][j])+
          diffRateUYarr[i][j]*(gridU[i][(j-1+h)%h]
          +gridU[i][(j+1)%h]
          -2*gridU[i][j]));
      }
    }
    float temp[][] = gridU;
    gridU = gridNext;
    gridNext = temp;
  }
  
  // diffusion of chemical v
  void diffusionV() {
    for(int i=0;i<w;++i) {
      for(int j=0;j<h;++j) {
  
        //int fader = (int)map(vFader5, 0, 255, 1, 480);
        gridNext[i][j] = gridV[i][j]+diffusionRateV*deltaT/deltaXSq*
          (gridV[(i-1+w)%w][j]
          +gridV[(i+1)%w][j]
          +gridV[i][(j-1+h)%h]
          +gridV[i][(j+1)%h]
          -4*gridV[i][j]
          );
      }
    }
    float temp[][] = gridV;
    gridV = gridNext;
    gridNext = temp;
  }
  
  // Reaction between chemicals
  void reaction() {
    for(int i=0;i<w;++i) {
      for(int j=0;j<h;++j) {
        // new varible because we redefine gridU[i][j] but have to use it in the calculation of gridV[i][j]
        float  uVal = gridU[i][j]; 
        gridU[i][j] = gridU[i][j] + deltaT*(reactionU(gridU[i][j],gridV[i][j], Farr[i][j], karr[i][j]));
        gridV[i][j] = gridV[i][j] + deltaT*(reactionV(uVal,gridV[i][j], Farr[i][j], karr[i][j]));
      }
    }
  }
  
  //FitzHugh-Nagumo
  float reactionU(float aVal, float bVal, float FVal, float kVal) {
    float reactionFader = (float)map(vFader5, 0, 255, .01, .2);
    //return reactionRate*(aVal-aVal*aVal*aVal-bVal+kVal);
    return reactionFader*(aVal-aVal*aVal*aVal-bVal+kVal);
  }
  
  float reactionV(float aVal, float bVal, float FVal, float kVal) {
    float reactionFader = (float)map(vFader5, 0, 255, .01, .2);
    //return reactionRate*FVal*(aVal-bVal);
    return reactionFader*FVal*(aVal-bVal);
  }
  
  // parameter F for reaction is different for every cell
  void setupF() {
    //F .04 - .09
    for(int i=0;i<w;++i) {
      for(int j=0;j<h;++j) {
        Farr[i][j] = .01+i*.09/w;
        //Farr[i][j] = .03+i*.02/w;
        //Farr[i][j] = .04;
      }
    }
  }
  
  // parameter k for reaction is the same for every cell
  void setupK() {
    for(int i=0;i<w;++i) {
      for(int j=0;j<h;++j) {
        karr[i][j]=.06+j*0.4/h;
        //karr[i][j] = .06;
      }
    }
  }
  
  
  void setupDiffRates() {
    // Diffusion rate in vertical direction changes from 0.03 to 0.05
    for(int i=0;i<w;++i) {
      for(int j=0;j<h;++j) {
        diffRateUYarr[i][j] = .03+j*.04/h;
        diffRateUXarr[i][j] = .03+j*.02/h;
        //diffRateUYarr[i][j] = .02;
      }
    }
  }
  
  public void doMouseDrag(){
    onClick();
  }
    /// set the onClick funciton using the global X and Y values

//// if data is coming from mouse position, map from mouseX to 0 to 1 and mouseY to 1
   public void onClick() {
        float cX = theOSCX * canvasW;
        float cY = theOSCY * canvasH;
        oX = (int)cX;
        oY = (int)cY;
        
        
        /*
        println("OX : " + oX);
        println("OY: " + oY);
        */
        int brush = 16;
        
        for (int i=oX - brush; i < brush + oX; ++i) {
            for (int j=oY - brush; j < brush + oY; ++j) {
            //  gridU[i][j] = cY-.1;
            //gridV[i][j] = cX-.1;
            //pixels[j*w+i] = color((gridU[i][j]*setcolorMode-100), vFader2,gridU[i][j]*vFader3);
            //for(int i=0;i<w;++i) {
            //for(int j=0;j<h;++j) {
            //gridU[i][j] = cY/2-.1;
            //gridV[i][j] = cX/2-.1;
            // new varible because we redefine gridU[i][j] but have to use it in the calculation of gridV[i][j]
            
            try{
                float  uVal = gridU[i][j]; 
              //gridU[i][j] = gridU[i][j] + deltaT*(2*reactionU(gridU[i][j],gridV[i][j], Farr[i][j], karr[i][j]));
              //gridV[i][j] = gridV[i][j] + deltaT*(2*reactionV(uVal,gridV[i][j], Farr[i][j], karr[i][j]));
              gridU[i][j] = .5+random(-.01,.01);
              gridV[i][j] = .25+random(-.01,.01);  
            } catch(Exception e){
              println("exception on gridU array: " + e);
            }
          
         }
       }
           
        try{
           
          
        } catch (Exception e){
          
          println("error drawing brush on fitzhugh: " + e);
        }
        
   }
   
}