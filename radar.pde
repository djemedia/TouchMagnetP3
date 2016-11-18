
class RadarRenderer extends AudioRenderer {

public String skchName = "Radar Renderer";
  float aura = 2;
  float orbit = .4;
  int delay = 3;

  int rotations;

  RadarRenderer(AudioSource source) {
    rotations =  (int) source.sampleRate() / source.bufferSize();
  }
 public void loadPresets() {
    println("Loading presets for" + skchName );
  }
  
  public void switchColorMode() {
    println("switching color mode for" + skchName );
    colorMode(HSB, TWO_PI * rotations, 1, 1);
  }
  
  public void setupSketch() {
    colorMode(HSB, TWO_PI * rotations, 1, 1);
    background(0);
  }

  synchronized void renderSketch()
  {
    colorMode(HSB, 40 * rotations, 1, 1);
    if (left != null) {
      float t = map(millis(), 0, delay * 800, 0, PI);   
      int n = left.length;

      // center 
      float w = width/2 + cos(t) * width * orbit;
      float h = height/2 + sin(t) * height * orbit; 

      // size of the aura
      float w2 = width * aura, h2 = height * aura;

      // smoke effect
      if (frameCount % delay == 0 ) image(g, 0, 0, width+1, height+1); 

      // draw polar curve 
      float r1=0, a1=0, x1=0, y1=0, r2=0, a2=0, x2=0, y2=0; 
      for (int i=0; i <= n; i++)
      {
        r1 = r2; 
        a1 = a2; 
        x1 = x2; 
        y1 = y2;
        r2 = left[i % n] ;
        a2 = map(i, 0, n, 0, TWO_PI * rotations);
        x2 = w + cos(a2) * r2 * w2;
        y2 = h + sin(a2) * r2 * h2;
        stroke(a1, 1, 1, 30);
        // strokeWeight(dist(x1,y1,x2,y2) / 4);
        if (i>0) line(x1, y1, x2, y2);
      }
    }
    colorMode(RGB,255);
  }
}