class HeatmapRenderer extends AudioRenderer {
  /*
  OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/46554*@* 
  */

  // Array to store the heat values for each pixel
  float heatmap[][][] = new float[2][canvasW][canvasH];
  
  // The index of the current heatmap
  int index = 0;
  
  // A color gradient to see pretty colors
  Gradient g;

  public String skchName = "Heatmap";
  
  int rotations;
  //  boolean toggle = true;

  HeatmapRenderer(AudioSource source) {
    //rotations =  (int) source.sampleRate() / source.bufferSize();
  }
 
  public void loadPresets() {
    println("Loading presets for" + skchName );
    doPresets();
  }
  
  public void setupSketch() {
    //noStroke();
    colorMode(RGB, 255, 255, 255, 255);
    g = new Gradient();

    //getSketchPresets("heatmap", true);
    
    if (getSketchPresets("heatmap", false)) {
      XML[] xmlGradient = presets[preset].getChildren("gradient");
      println("Heatmap Preset #" + preset + " Gradient elements: " + xmlGradient.length + " faders: " + setcolorMode + " " + vFader2 + " " + vFader3 + " " + vFader4 + " " + vFader5 + " " + vFader6 + " " + vFader7 + " " + vFader8);

      for (int xg = 0; xg < xmlGradient.length; xg++) {
        //println("grad.addColorAt(" + xmlGradient[xg].getInt("at") + ",NamedColor." + xmlGradient[xg].getContent() + ");");
        //grad.addColorAt(xmlGradient[xg].getInt("at"), NamedColor.getForName(xmlGradient[xg].getContent()) );
        println("g.addColor " + xmlGradient[xg].getInt("r") + " " + xmlGradient[xg].getInt("g") + " " + xmlGradient[xg].getInt("b"));
        g.addColor(color(xmlGradient[xg].getInt("r"),xmlGradient[xg].getInt("g"),xmlGradient[xg].getInt("b")));
      }
    }
   
    /* // Initalize the heat map (make sure everything is 0.0)
     for (int i = 0; i < canvasW; ++i)
     for (int j = 0; j < canvasH; ++j)
     heatmap[index][i][j] = 0.0;
     */
  }
  
  void doPresets(){
    
    if (getSketchPresets("heatmap", false)) {
      XML[] xmlGradient = presets[preset].getChildren("gradient");
      println("Heatmap Preset #" + preset + " Gradient elements: " + xmlGradient.length + " faders: " + setcolorMode + " " + vFader2 + " " + vFader3 + " " + vFader4 + " " + vFader5 + " " + vFader6 + " " + vFader7 + " " + vFader8);

      for (int xg = 0; xg < xmlGradient.length; xg++) {
        //println("grad.addColorAt(" + xmlGradient[xg].getInt("at") + ",NamedColor." + xmlGradient[xg].getContent() + ");");
        //grad.addColorAt(xmlGradient[xg].getInt("at"), NamedColor.getForName(xmlGradient[xg].getContent()) );
        println("g.addColor " + xmlGradient[xg].getInt("r") + " " + xmlGradient[xg].getInt("g") + " " + xmlGradient[xg].getInt("b"));
        g.addColor(color(xmlGradient[xg].getInt("r"),xmlGradient[xg].getInt("g"),xmlGradient[xg].getInt("b")));
      }
    }

  }

  public void renderSketch(){
    colorMode(RGB, 255, 255, 255, 255);
    // See if heat (or cold) needs applied
    if (mousePressed && (mouseButton == LEFT))
      apply_heat(mouseX, mouseY, 30, .25);
    if (mousePressed && (mouseButton == RIGHT))
      apply_heat(mouseX, mouseY, 30, -.2);

    // Calculate the next step of the heatmap
    update_heatmap();

    // For each pixel, translate its heat to the appropriate color
    for (int i = 0; i < canvasW; ++i) {
      for (int j = 0; j < canvasH; ++j) {
        color thisColor = g.getGradient(heatmap[index][i][j]);
        //thisColor = color(red(thisColor) - 50, green(thisColor) - 50, blue(thisColor) - 50 ); //master fade

        set(i, j, thisColor);
      }
    }
    //colorMode(HSB, 255, 255, 255, 255);
  }

  void update_heatmap()
  {
    // Calculate the new heat value for each pixel
    for (int i = 0; i < canvasW; ++i)
      for (int j = 0; j < canvasH; ++j)
        heatmap[index ^ 1][i][j] = calc_pixel(i, j);

    // flip the index to the next heatmap
    index ^= 1;
  }

  float calc_pixel(int i, int j)
  {
    float total = 0.0;
    int count = 0;

    // This is were the magic happens...
    // Average the heat around the current pixel to determin the new value
    for (int ii = -1; ii < 2; ++ii)
    {
      for (int jj = -1; jj < 2; ++jj)
      {
        if (i + ii < 0 || i + ii >= width || j + jj < 0 || j + jj >= height)
          continue;

        ++count;
        total += heatmap[index][i + ii][j + jj];
      }
    }

    // return the average
    return total / count;
  }
  
    /// set the onClick funciton using the global X and Y values
 public void doMouseDrag(){
   
 }
  public void onClick() {
    float cX = theX * canvasW;
    float cY = theY * canvasH;
    int oX = (int)cX;
    int oY = (int)cY;
    if (toggle == true)
      apply_heat(oX, oY, 60, .10);
    if (toggle == false)
      apply_heat(oX, oY, 60, -.10);
  }
  /*
  public void heattoggle(float oscToggle) {
   //int mI = (int)placeholder;
   if (oscToggle == 1)
   toggle = false;
   if (oscToggle == 0)
   toggle = true;
   }
   */
  void apply_heat(int i, int j, int r, float delta)
  {
    // apply delta heat (or remove it) at location 
    // (i, j) with rad8ius r

    for (int ii = - (r / 2 ); ii < (r / 2); ++ii)
    {
      for (int jj = - (r / 2); jj < (r / 2); ++jj)
      {
        if (i + ii < 0 || i + ii >= width || j + jj < 0 || j + jj >= height)
          continue;

        // apply the heat
        heatmap[index][i + ii][j + jj] += delta;
        heatmap[index][i + ii][j + jj] = constrain(heatmap[index][i + ii][j + jj], 0.0, 20.0);
        if (heatmap[index][i + ii][j + jj] < .3 && !toggle) {
          toggle = !toggle;
        }
        if (heatmap[index][i + ii][j + jj] > 19.7 && toggle) {
          toggle = !toggle;
        }
      }
    }
  }
}
//example gradient colors
    /*
    g.addColor(color(0, 0, 0));
     g.addColor(color(102, 11, 0));
     g.addColor(color(140, 29, 72));
     g.addColor(color(204, 50, 0));
     g.addColor(color(200, 40, 102));
     g.addColor(color(111, 20, 75));
     g.addColor(color(191, 0, 50));
     g.addColor(color(255, 102, 0));
     g.addColor(color(204, 0, 20));
     g.addColor(color(153, 0, 0));
     g.addColor(color(255, 153, 102));
     g.addColor(color(255, 255, 255));
     g.addColor(color(0, 0, 0));
     */
/*
    g.addColor(color(102, 11, 0));
    g.addColor(color(204, 50, 0));
    g.addColor(color(111, 0, 75));
    g.addColor(color(10, 0, 50));
    g.addColor(color(102, 11, 0));
    g.addColor(color(0, 0, 0));
    g.addColor(color(151, 10, 0));
    g.addColor(color(204, 0, 20));
    g.addColor(color(153, 0, 0));
    g.addColor(color(250, 153, 0));

    g.addColor(color(255, 200, 200));
    g.addColor(color(0, 0, 0));

    g.addColor(color(2, 11, 75));
    g.addColor(color(111, 20, 0));
    g.addColor(color(111, 0, 10));
    g.addColor(color(10, 0, 50));
    //g.addColor(color(102, 11, 0));
    g.addColor(color(0, 0, 0));
    g.addColor(color(151, 10, 0));
    g.addColor(color(204, 0, 20));
    g.addColor(color(153, 0, 0));
    g.addColor(color(250, 153, 0));
    //add gradient color
    //hsb picker
*/