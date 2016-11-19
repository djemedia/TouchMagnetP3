//// these values get accessed by both classes
//// should be moved to main class tho
int tId = 0;
int NUM_PARTICLES = 800; 

class NoiseFieldRenderer extends AudioRenderer {

  int rotations;
  int oX = mouseX;
  int oY = mouseY;

  ParticleSystem p;
  

  public String skchName = "Noise Field";

  NoiseFieldRenderer(AudioSource source) {
    //rotations =  (int) source.sampleRate() / source.bufferSize();
  }


  public void setInitVals(){
    
  }

  public void loadPresets() {
    println("Loading presets for" + skchName );
    getSketchPresets("noisefield", true);    
  }
  
  public void switchColorMode() {
    println("switching color mode for" + skchName );
    colorMode(HSB, 255);
  }
  
  public void setupSketch() {
    smooth();
    //size(canvasW, canvasH);
    colorMode(HSB, 255);
    background(0);

    int setTraceModeF = 10;
    int setSpeed = 20;
    int setContrastModeF = 2;
    getSketchPresets("noisefield", true);    
    p = new ParticleSystem();
  }

  public void renderSketch()
  {
    colorMode(HSB, 255);
    noStroke();
    int setTraceModeF = (int)map(vFader4, 0, 255, 0, 100);
    fill(0, setTraceModeF);
    rect(0, 0, width, height);

    try {
      p.update();
      p.render();
      throw new NullPointerException();
    }
    catch (NullPointerException e) {
    }
  }
  /// set the onClick function using the global X and Y values
  public void setClick(){
    onClick(theX, theY);
  }
  public void onClick(float mX, float mY) {
    float cX = mX * canvasW;
    float cY = mY * canvasH;
    oX = (int)cX;
    oY = (int)cY;
  }
}
public void onClick(int mX, int mY) {
    int brush = 4;
    int setContrastModeF = (int)map(vFader4, 0, 255, 0, 60);

    for (int y=mY - brush; y < brush + mY; y+=4) {
      for (int x=mX - brush; x < brush + mX; x+=4) {
        //    for(int y=-particleMargin; y<height+particleMargin; y+=particlesDensity) {
        //    for(int x=-particleMargin; x<width+particleMargin; x+=particlesDensity) {
        if (tId == 8) {
          println(tId);
          tId=0;
        }
        //       int c = color(50+50*sin(PI*x/width), 127, 255*sin(PI*y/width));
        //int c = color(200*sin(PI*x/width), 227, 255*sin(PI*y/width));
        //int c = color(setcolorMode*sin(PI*x/width), 225, 255*sin(PI*y/width));

        //int c = color(10+20*sin(PI*x/width), 200, 255*sin(PI*y/width));
        //static color
        //int c = color(setcolorMode+20*sin(PI*x/width), 255*sin(PI*(200)/width), 255);
        //int c = color(setcolorMode, vFader2, vFader3);
        int c = color((setcolorMode+20)-setContrastModeF*sin(PI*x/width), vFader2, vFader3);
        //add variability
        //position[i++] = new Particle(x, y, c);
        //particles[i++] = new Particle(x, y, cP);
      }
    }
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