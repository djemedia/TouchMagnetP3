//// these values get accessed by both classes
//// should be moved to main class tho
int tId = 0;
int NUM_PARTICLES = 800; 

class NoiseFieldRenderer extends AudioRenderer {

  int rotations;
  //int oX;
  //int oY;
  
   int oX = mouseX;
   int oY = mouseY;
  

  ParticleSystem p;
  

  public String skchName = "Noise Field";

  NoiseFieldRenderer(AudioSource source) {
    //rotations =  (int) source.sampleRate() / source.bufferSize();
  }

  public void loadPresets() {
    println("Loading presets for " + skchName );
    getSketchPresets("noisefield", true);    
  }
  

  public void setupSketch() {
    //smooth(); //used in processing 2
    //size(canvasW, canvasH);
    colorMode(HSB, 255, 255, 255, 255);
    //noStroke();
    //background(0);

    int setTraceModeF = 10;
    int setSpeed = 20;
    int setContrastModeF = 2;
    //getSketchPresets("noisefield", true);    
    p = new ParticleSystem();
  }

  public void renderSketch()
  {
    colorMode(HSB, 255);
    /*
    fill(0);
    rect(0,0,canvasW,canvasH);
    */
    //colorMode(HSB, 255);
    noStroke();
    //background(0);
    int setTraceModeF = (int)map(vFader4, 0, 255, 0, 100);
    fill(0, setTraceModeF);
    rect(0, 0, width, height);

    try {
      p.update();
      p.render();
      
    } catch (Exception e) {
      println("error drawing noisefield: " + e);
      
    }
  }

 public void doMouseDrag(){
   
 }
 
  public void onClick() {
    float cX = theX * canvasW;
    float cY = theY * canvasH;
    oX = (int)cX;
    oY = (int)cY;
  }
  
  /*
   public void onClick() {
    //// do soemthing with (theX, theY);
  }
  */
}

class Particle
{
  PVector position, velocity;



  Particle()
  {
    position = new PVector(random(width), random(height));
    velocity = new PVector();
  }

  void update()
  {
    float setSpeed = (float)map(vFader5, 0, 255, 0.1, 10);
    int setDetailF = (int)map(vFader6, 0, 255, 1, 20);
    
    
    //float noisefields = setNoiseDetailF;
    //velocity.x = setSpeed*(noise(noisefield.oX/10+position.y/100)-.5);
    //velocity.y = setSpeed*(noise(noisefield.oY/10+position.x/100)-.5);
    velocity.x = setSpeed*(noise(noisefield.oX/setDetailF+position.y/10)-0.5);
    velocity.y = setSpeed*(noise(noisefield.oY/setDetailF+position.x/10)-0.5);
    position.add(velocity);

    if (position.x<0)position.x+=width;
    if (position.x>width)position.x-=width;
    if (position.y<0)position.y+=height;
    if (position.y>height)position.y-=height;
  }

  void render()
  {
    //int setContrastModeF = (int)map(vFader6, 0, 255, 1, 10);
    int setContrastModeF = (int)map(vFader6, 0, 255, 1, 10);
    //stroke((setcolorMode+10)+20*sin(setContrastModeF*HALF_PI*noisefield.rotations), vFader2, vFader3);
    stroke((setcolorMode+10)- setContrastModeF*sin(HALF_PI), vFader2, vFader3);
    line(position.x, position.y, position.x-velocity.x, position.y-velocity.y);
  }
}

class ParticleSystem
{
  Particle[] particles;
  
  ParticleSystem()
  {
    particles = new Particle[NUM_PARTICLES];
    for(int i = 0; i < NUM_PARTICLES; i++)
    {
      particles[i]= new Particle();
    }
  }
  
  void update()
  {
    for(int i = 0; i < NUM_PARTICLES; i++)
    {
      particles[i].update();
    }
  }
  
  void render()
  {
    for(int i = 0; i < NUM_PARTICLES; i++)
    {
      particles[i].render();
    }
  }
}