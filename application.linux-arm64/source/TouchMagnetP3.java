import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import com.heroicrobot.dropbit.devices.*; 
import com.heroicrobot.dropbit.common.*; 
import com.heroicrobot.dropbit.discovery.*; 
import com.heroicrobot.dropbit.registry.*; 
import com.heroicrobot.dropbit.devices.pixelpusher.*; 
import toxi.sim.automata.*; 
import toxi.math.*; 
import toxi.color.*; 
import ddf.minim.*; 
import artnetP5.*; 
import dmxP512.*; 
import processing.net.*; 
import processing.serial.*; 
import javax.swing.JColorChooser; 
import java.awt.Color; 
import java.util.*; 
import oscP5.*; 
import netP5.*; 
import ddf.minim.analysis.*; 

import toxi.sim.automata.*; 
import toxi.sim.dla.*; 
import toxi.sim.erosion.*; 
import toxi.sim.fluids.*; 
import toxi.sim.grayscott.*; 
import toxi.geom.*; 
import toxi.geom.mesh.*; 
import toxi.geom.mesh.subdiv.*; 
import toxi.geom.mesh2d.*; 
import toxi.geom.nurbs.*; 
import toxi.math.*; 
import toxi.math.conversion.*; 
import toxi.math.noise.*; 
import toxi.math.waves.*; 
import toxi.util.*; 
import toxi.util.datatypes.*; 
import toxi.util.events.*; 
import toxi.color.*; 
import toxi.color.theory.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TouchMagnetP3 extends PApplet {

///////////////////////////
//////TouchMagnet///////////
//////////////////////////////
/* open source - GNU 3+ Public license
created by dustin edwards with dan cote, 2013. 
Artnet upgrade with Rich Trapani/Jamie Schwetmann 2015
P3 upgrade and additional programming with TenTon Raygun 2016
/////////////////////////////////////////////////////////////
////use mouse to interact/////space bar = next sketch///////
/////////////////////////////////////////////////////////////
//////run TouchMagnet GUI for advanced functionality//
////////////////////////////////////////////////////////////*/







 
DeviceRegistry registry;
TestObserver testObserver;

















 

// import hypermedia.net.*;
//import processing.core.*;



OscP5 oscP5;
OscP5 oscP5B;


NetAddress myRemoteLocation;
NetAddress stripApp;


//import spout.*;
//Spout spout;

PGraphics canvas;
PImage transition;

boolean artnetEnable = false;
boolean dmxEnable =false;
boolean pixEnable = true;
boolean apaEnable = false;
boolean hcsr04Enable = false;
//boolean spoutEnable = true;
//boolean syphonEnable = false;

boolean showFramerate = false;

boolean ready_to_go = true;
int lastPosition;


int canvasW = 300;
int canvasH = 80;

int ledsW = 300;
int ledsH = 72;
int dmxAddr = 100;
int dmxUniv = 1;
int[] ledPos;
int[] artnetPos;
int[] dmxPos;
int thisLedPos;
int thisartnetPos;
int thisDmxPos;

//these settings can be overridden by the data/presets.xml file
int setcolorMode = 220;
int vFader2 = 255;
int vFader3 = 200;
int vFader4 = 228;
int vFader5 = 1;
int vFader6 = 200;
int vFader7 = 0;
int vFader8 = 0;
int vFaderB1 = 200;
int vFaderB2 = 255;
int vFaderB3 = 155;
int vFaderB4 = 28;
int vFaderB5 = 25;
int vFaderB6 = 128;

int dimmer1 = 0;
int dimmer2 = 0;
int dimmer3 = 0;
int dimmer4 = 0;
int dimmer5 = 0;
int dimmer6 = 0;
int dimmer7 = 0;
int dimmer8 = 0;
int dimmer9 = 0;
int dimmer10 = 0;
boolean toggle = false;
boolean toggleB = false;


//// global X and Y positions for mouse
float theX = 0;
float theY = 0;

//// global X and Y positions for touchgui
float theOSCX = 0;
float theOSCY = 0;


float randomTouchState;
float audioResponseState;

int faderWait = 0;
int resetPixelsWait = 0;

int transitionOpacity = 0;

OscMessage faderOut;
OscMessage buttonOut;
float faderOutFloat;


Minim minim;
AudioInput in;

 
AudioRenderer radar;
HeatmapRenderer heatmap;
NoiseParticlesRenderer noiseParticles;
FluidRenderer fluidje;
PerlinColorRenderer perlincolor;
NoiseFieldRenderer noisefield;
FitzhughRenderer fitzhugh;
TuringRenderer turing;
stainedglassRenderer stainedglass;
LastCallRenderer lastcall;

AudioRenderer[] visuals;



int select =0;

int preset=0;
//int turingpreset = 0;

XML xml;
XML[] presets;



//////////////////////////////////////////////////
//////////////////////////////////////////////////
///////// SETUP ///////////////////
////////////////////////////////////////////////
//////////////////////////////////////////////////
public void setup() {
  //size(canvasW, canvasH);
  //fullScreen();
  
  //textureMode(NORMAL);
  //background(0);
  frameRate(60);
  colorMode(HSB, 255,255, 255,255);
  transition = get();
  //transition = createImage(0,0, ARGB);
 
  
  //////////// LOAD ALL PRESETS /////////////////////
  xml = loadXML("data/presets.xml");
  loadMasterPresets();
  
 
  //// setup player
  //minim = new Minim(this);

  //// get a line in from Minim, default bit depth is 16
  //in = minim.getLineIn(Minim.STEREO, 512);


  //in.addListener(visuals[select]);
  // visuals[select].setupSketch();
  
  //// setup renderers
  noiseParticles = new NoiseParticlesRenderer(in);
  perlincolor = new PerlinColorRenderer(in);
  //radar = new RadarRenderer(in);
  fluidje = new FluidRenderer(in);
  heatmap = new HeatmapRenderer(in);
  noisefield = new NoiseFieldRenderer(in);
  fitzhugh = new FitzhughRenderer(in);
  turing = new TuringRenderer(in);
  stainedglass = new stainedglassRenderer(in);
  lastcall = new LastCallRenderer(in);

//////////////////// set renderer array //////////////////////
  visuals = new AudioRenderer[] {
    fluidje, perlincolor, heatmap, noiseParticles, noisefield, fitzhugh, stainedglass, turing, lastcall 
  };  
  for(int i=0; i<visuals.length; i++){
    /// println("Loading sketch: " + i);
    visuals[i].setupSketch();
    
  }
  // activate first renderer in list
  select = 0;
  preset = 0;
  
  if (pixEnable == true){
    setupPixelPusher();
  }
  if (artnetEnable == true){
    setupArtnet();
  }
  if (dmxEnable == true){
    setupDMX();
  }
  if (apaEnable == true){
    setupApa();
  }
  /*
    if (hcsr04Enable == true){
    setupHCSR04();
  }

  if (spoutEnable == true)
    setupSpout();
  }    
  */
    
 ////setup oscp5/////
  oscP5 = new OscP5(this, 12000);
  oscP5B = new OscP5(this, 9001);
  myRemoteLocation = new NetAddress("255.255.255.255", 9000);
  stripApp = new NetAddress("127.0.0.1", 12001);
  
  oscP5.plug(this, "oscOnClick", "/luminous/xy");
  oscP5.plug(this, "oscOnClick2", "/luminous/xyB");

  oscP5.plug(this, "oscSketch1", "/luminous/sketch1");
  oscP5.plug(this, "oscSketch2", "/luminous/sketch2");
  oscP5.plug(this, "oscSketch3", "/luminous/sketch3");
  oscP5.plug(this, "oscSketch4", "/luminous/sketch4");
  oscP5.plug(this, "oscSketch5", "/luminous/sketch5");
  oscP5.plug(this, "oscSketch6", "/luminous/sketch6");
  oscP5.plug(this, "oscSketch7", "/luminous/sketch7");
  oscP5.plug(this, "oscSketch8", "/luminous/sketch8");
  oscP5.plug(this, "oscSketch9", "/luminous/sketch9");
  oscP5.plug(this, "oscSketch10", "/luminous/sketch10");
  oscP5.plug(this, "oscSketch11", "/luminous/sketch11");
  oscP5.plug(this, "oscSketch12", "/luminous/sketch12");
  oscP5.plug(this, "oscSketch13", "/luminous/sketch13");
  oscP5.plug(this, "oscSketch14", "/luminous/sketch14");
  oscP5.plug(this, "oscSketch15", "/luminous/sketch15");
  oscP5.plug(this, "oscSketch16", "/luminous/sketch16");
  oscP5.plug(this, "oscSketch17", "/luminous/sketch17");
  oscP5.plug(this, "oscSketch18", "/luminous/sketch18");
  oscP5.plug(this, "oscSketch19", "/luminous/sketch19");
  oscP5.plug(this, "oscSketch20", "/luminous/sketch20");

  oscP5.plug(this, "oscSketchB1", "/luminous/sketchB1");
  oscP5.plug(this, "oscSketchB2", "/luminous/sketchB2");
  oscP5.plug(this, "oscSketchB3", "/luminous/sketchB3");
  oscP5.plug(this, "oscSketchB4", "/luminous/sketchB4");
  oscP5.plug(this, "oscSketchB5", "/luminous/sketchB5");
  oscP5.plug(this, "oscSketchB6", "/luminous/sketchB6");
  oscP5.plug(this, "oscSketchB7", "/luminous/sketchB7");
  oscP5.plug(this, "oscSketchB8", "/luminous/sketchB8");
  oscP5.plug(this, "oscSketchB9", "/luminous/sketchB9");
  oscP5.plug(this, "oscSketchB10", "/luminous/sketchB10");
  oscP5.plug(this, "oscSketchB11", "/luminous/sketchB11");
  oscP5.plug(this, "oscSketchB12", "/luminous/sketchB12");
  oscP5.plug(this, "oscSketchB13", "/luminous/sketchB13");
  oscP5.plug(this, "oscSketchB14", "/luminous/sketchB14");
  oscP5.plug(this, "oscSketchB15", "/luminous/sketchB15");
  oscP5.plug(this, "oscSketchB16", "/luminous/sketchB16");
  oscP5.plug(this, "oscSketchB17", "/luminous/sketchB17");
  oscP5.plug(this, "oscSketchB18", "/luminous/sketchB18");
  oscP5.plug(this, "oscSketchB19", "/luminous/sketchB19");
  oscP5.plug(this, "oscSketchB20", "/luminous/sketchB20");  

  oscP5.plug(this, "oscEffect1", "/luminous/effect1");
  oscP5.plug(this, "oscEffect2", "/luminous/effect2");
  oscP5.plug(this, "oscEffect3", "/luminous/effect3");
  oscP5.plug(this, "oscEffect4", "/luminous/effect4");
  oscP5.plug(this, "oscEffect5", "/luminous/effect5");

  oscP5.plug(this, "oscEffectB1", "/luminous/effectB1");
  oscP5.plug(this, "oscEffectB2", "/luminous/effectB2");
  oscP5.plug(this, "oscEffectB3", "/luminous/effectB3");

  oscP5B.plug(this, "oscEffectB2B", "/luminous/effect2");

  //faders
  oscP5.plug(this, "oscFader1", "/luminous/fader1");
  oscP5.plug(this, "oscFader2", "/luminous/fader2");
  oscP5.plug(this, "oscFader3", "/luminous/fader3");
  oscP5.plug(this, "oscFader4", "/luminous/fader4");
  oscP5.plug(this, "oscFader5", "/luminous/fader5");
  oscP5.plug(this, "oscFader6", "/luminous/fader6");
  oscP5.plug(this, "oscFader7", "/luminous/fader7");
  oscP5.plug(this, "oscFader8", "/luminous/fader8");

  oscP5.plug(this, "oscFaderB1", "/luminous/faderB1");
  oscP5.plug(this, "oscFaderB2", "/luminous/faderB2");
  oscP5.plug(this, "oscFaderB3", "/luminous/faderB3");
  oscP5.plug(this, "oscFaderB4", "/luminous/faderB4");
  oscP5.plug(this, "oscFaderB5", "/luminous/faderB5");
  oscP5.plug(this, "oscFaderB6", "/luminous/faderB6");

  oscP5B.plug(this, "oscFaderB1", "/luminous/fader1");
  oscP5B.plug(this, "oscFaderB2", "/luminous/fader2");
  oscP5B.plug(this, "oscFaderB3", "/luminous/fader3");
  oscP5B.plug(this, "oscFaderB4", "/luminous/fader4");
  oscP5B.plug(this, "oscFaderB5", "/luminous/fader5");
  oscP5B.plug(this, "oscFaderB6", "/luminous/fader6");


  //dimmers
  oscP5.plug(this, "oscDimmer1", "/luminous/dimmer1");
  oscP5.plug(this, "oscDimmer2", "/luminous/dimmer2");
  oscP5.plug(this, "oscDimmer3", "/luminous/dimmer3");
  oscP5.plug(this, "oscDimmer4", "/luminous/dimmer4");
  oscP5.plug(this, "oscDimmer5", "/luminous/dimmer5");
  oscP5.plug(this, "oscDimmer6", "/luminous/dimmer6");
  oscP5.plug(this, "oscDimmer7", "/luminous/dimmer7");
  oscP5.plug(this, "oscDimmer8", "/luminous/dimmer8");
  oscP5.plug(this, "oscDimmer9", "/luminous/dimmer9");
  oscP5.plug(this, "oscDimmer10", "/luminous/dimmer10");

  oscP5.plug(this, "oscSave", "/luminous/save");
}

//////////////////////////////////////////////////
//////////////////////////////////////////////////
///////// ALL OSC FUNCTIONS ///////////////////
////////////////////////////////////////////////
//////////////////////////////////////////////////

public void oscSketch1(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 0;
    preset = 0;
    //  vFader5 = 30;
    //  vFader6 = 50;
    //getSketchPresets("fluidje", true);
    //in.addListener(visuals[select]);
    //// visuals[select].setupSketch();

    reLoadSketch();
  }
}
public void oscSketch2(float iA) {
  if (iA == 1) {
     transitionReset();
    //in.removeListener(visuals[select]);
    select = 1;
    preset = 0;
    //in.addListener(visuals[select]);
    /// visuals[select].setup();
    //add code to prevent double tap
    reLoadSketch();
    
  }
}
public void oscSketch3(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 2;
    preset = 0;
    //in.addListener(visuals[select]);
    //visuals[select].setup();
    reLoadSketch();
  }
}
public void oscSketch4(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 3;
    preset = 0;
    //in.addListener(visuals[select]);
    //visuals[select].setup();
    reLoadSketch();
  }
}
public void oscSketch5(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 4;
    preset = 0;
    //in.addListener(visuals[select]);
    //visuals[select].setup();
    reLoadSketch();
  }
}
public void oscSketch6(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 5;
    preset = 0;
    //int setcolorMode = 140;
    //  int vFader3 = 255;
    //  int vFader4 = 0;
    //in.addListener(visuals[select]);
    //visuals[select].setup();
    reLoadSketch();
  }
}
public void oscSketch7(float iA) { 
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 6;
    preset = 0;
    //in.addListener(visuals[select]);
    ///visuals[select].setup();
    reLoadSketch();
  }
}

public void oscSketch8(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 6;
    preset = 1;
    //in.addListener(visuals[select]);
    ///visuals[select].setup();
    reLoadSketch();
  }
}
public void oscSketch9(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 6;
    preset = 3;
    //in.addListener(visuals[select]);
    reLoadSketch();
  }
}

public void oscSketch10(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 8;
    preset = 0;
    //in.addListener(visuals[select]);
    ///visuals[select].setup();
    
    reLoadSketch();
  }
}
public void oscSketch11(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 7;
    preset = 2;
    //in.addListener(visuals[select]);
    ///visuals[select].setup();
    reLoadSketch();
  }
}
public void oscSketch12(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 7;
    preset = 0;
    //in.addListener(visuals[select]);
    ////visuals[select].setup();
    reLoadSketch();
  }
}
public void oscSketch13(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 0;
    preset = 1;
    //in.addListener(visuals[select]);
    ////visuals[select].setup();
    reLoadSketch();
  }
}
public void oscSketch14(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 0;
    preset = 2;
    //in.addListener(visuals[select]);
    /// visuals[select].setup();
    reLoadSketch();
  }
}
public void oscSketch15(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 4;
    preset = 1;
    //in.addListener(visuals[select]);
    ///visuals[select].setup();
    reLoadSketch();
  }
}
public void oscSketch16(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 5;
    preset = 1;
    //int setcolorMode = 140;
    //  int vFader3 = 255;
    //  int vFader4 = 0;
    //in.addListener(visuals[select]);
    ///visuals[select].setup();
    reLoadSketch();
  }
}
public void oscSketch17(float iA) { 
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 4;
    preset = 2;
    //in.addListener(visuals[select]);
    ///visuals[select].setup();
    reLoadSketch();
  }
}

public void oscSketch18(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 1;
    preset = 2;
    //in.addListener(visuals[select]);
    ///visuals[select].setup();

    //colorMode(RGB);
    reLoadSketch();
  }
}
public void oscSketch19(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 1;
    preset = 1;
    //in.addListener(visuals[select]);
    //visuals[select].setup();
    reLoadSketch();
  }
}

public void oscSketch20(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 2;
    preset = 1;
    //in.addListener(visuals[select]);
    ///visuals[select].setup();
    //colorMode(HSB, 255);
    reLoadSketch();
  }
}

public void oscSketchB1(float iA) {
  buttonOut = new OscMessage("/luminous/sketch1");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB2(float iA) {
  buttonOut = new OscMessage("/luminous/sketch2");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB3(float iA) {
  buttonOut = new OscMessage("/luminous/sketch3");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB4(float iA) {
  buttonOut = new OscMessage("/luminous/sketch4");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB5(float iA) {
  buttonOut = new OscMessage("/luminous/sketch5");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB6(float iA) {
  buttonOut = new OscMessage("/luminous/sketch6");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB7(float iA) {
  buttonOut = new OscMessage("/luminous/sketch7");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB8(float iA) {
  buttonOut = new OscMessage("/luminous/sketch8");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB9(float iA) {
  buttonOut = new OscMessage("/luminous/sketch9");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB10(float iA) {
  buttonOut = new OscMessage("/luminous/sketch10");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB11(float iA) {
  buttonOut = new OscMessage("/luminous/sketch11");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB12(float iA) {
  buttonOut = new OscMessage("/luminous/sketch12");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB13(float iA) {
  buttonOut = new OscMessage("/luminous/sketch3");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB14(float iA) {
  buttonOut = new OscMessage("/luminous/sketch4");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB15(float iA) {
  buttonOut = new OscMessage("/luminous/sketch5");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB16(float iA) {
  buttonOut = new OscMessage("/luminous/sketch6");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB17(float iA) {
  buttonOut = new OscMessage("/luminous/sketch7");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB18(float iA) {
  buttonOut = new OscMessage("/luminous/sketch8");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB19(float iA) {
  buttonOut = new OscMessage("/luminous/sketch9");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscSketchB20(float iA) {
  buttonOut = new OscMessage("/luminous/sketch10");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}

public void oscEffectB1(float iA) {
  buttonOut = new OscMessage("/luminous/effect1");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscEffectB2(float iA) {
  buttonOut = new OscMessage("/luminous/effect2");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscEffectB3(float iA) {
  buttonOut = new OscMessage("/luminous/effect3");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
public void oscEffectB2B(float iA) {
  if (iA == 1.0f) {
    toggleB = true;
  } else {
    toggleB = false;
  }
}

//////////////////////////////////////////////////
//////////////////////////////////////////////////
///////// OSC CLICK ///////////////////
////////////////////////////////////////////////
//////////////////////////////////////////////////

public void oscOnClick(float iA, float iB) {
  /// set the global X and Y to whichever interface is passing it
  theX = theOSCX = iA;
  theY = theOSCY = iB;
  visuals[select].onClick();

}

public void oscOnClick2(float iA, float iB) {
  faderOut = new OscMessage("/luminous/xy");
  faderOut.add(iA);
  faderOut.add(iB);
  oscP5.send(faderOut, stripApp);
}

public void oscEffect1(float iA) {
  if ((millis() - resetPixelsWait) > 100) {
    resetPixelsWait = millis();
    noiseParticles.clearParticles(iA);
  }
}

public void oscEffect2(float iA) {
  if (iA == 1)
    toggle = true;
  if (iA == 0)
    toggle = false;
  //  heatmap.heattoggle(iA);
  turing.directiontoggle(iA);
  // simplegradient.directiontoggle(iA);
}


public void oscEffect3(float iA) {
  //turing.directiontoggle(iA);
}
public void oscEffect4(float iA) {
  randomTouchState = iA;
}
public void oscEffect5(float iA) {
  audioResponseState = iA;
}

public void oscSave(float iA) {
  if (iA == 1) {
    savePresets();
    buttonOut = new OscMessage("/luminous/save");
    buttonOut.add(iA);
    oscP5.send(buttonOut, stripApp);
  }
}

public void oscFader1(float faderIn) {
  setcolorMode = (int)map(faderIn, 0, 1, 0, 255);
}
public void oscFader2(float faderIn) {
  vFader2 = (int)map(faderIn, 0, 1, 0, 255);
}
public void oscFader3(float faderIn) {
  vFader3 = (int)map(faderIn, 0, 1, 0, 255);
}
public void oscFader4(float faderIn) {
  vFader4 = (int)map(faderIn, 0, 1, 0, 255);
}  
public void oscFader5(float faderIn) {
  vFader5 = (int)map(faderIn, 0, 1, 0, 255);
}  
public void oscFader6(float faderIn) {
  vFader6 = (int)map(faderIn, 0, 1, 0, 255);
}
public void oscFader7(float faderIn) {
  vFader7 = (int)map(faderIn, 0, 1, 0, 255);
}  
public void oscFader8(float faderIn) {
  vFader8 = (int)map(faderIn, 0, 1, 0, 255);
}    
public void oscFaderB1(float faderIn) {
  vFaderB1 = (int)map(faderIn, 0, 1, 0, 255);
}
public void oscFaderB2(float faderIn) {
  vFaderB2 = (int)map(faderIn, 0, 1, 0, 255);
}
public void oscFaderB3(float faderIn) {
  vFaderB3 = (int)map(faderIn, 0, 1, 0, 255);
}
public void oscFaderB4(float faderIn) {
  vFaderB4 = (int)map(faderIn, 0, 1, 0, 255);
}
public void oscFaderB5(float faderIn) {
  vFaderB5 = (int)map(faderIn, 0, 1, 0, 255);
}
public void oscFaderB6(float faderIn) {
  vFaderB6 = (int)map(faderIn, 0, 1, 0, 255);
}

public void oscDimmer1(float faderIn) {
  dimmer1 = (int)map(faderIn, 0, 1, 0, 255);
}  
public void oscDimmer2(float faderIn) {
  dimmer2 = (int)map(faderIn, 0, 1, 0, 255);
}
public void oscDimmer3(float faderIn) {
  dimmer3 = (int)map(faderIn, 0, 1, 0, 255);
}
public void oscDimmer4(float faderIn) {
  dimmer4 = (int)map(faderIn, 0, 1, 0, 255);
}
public void oscDimmer5(float faderIn) {
  dimmer5 = (int)map(faderIn, 0, 1, 0, 255);
}
public void oscDimmer6(float faderIn) {
  dimmer6 = (int)map(faderIn, 0, 1, 0, 255);
}
public void oscDimmer7(float faderIn) {
  dimmer7 = (int)map(faderIn, 0, 1, 0, 255);
}
public void oscDimmer8(float faderIn) {
  dimmer8 = (int)map(faderIn, 0, 1, 0, 255);
}
public void oscDimmer9(float faderIn) {
  dimmer9 = (int)map(faderIn, 0, 1, 0, 255);
}
public void oscDimmer10(float faderIn) {
  dimmer10 = (int)map(faderIn, 0, 1, 0, 255);
}

public void oscFaderSet() {
  faderOut = new OscMessage("/luminous/fader1");
  faderOutFloat = (float)map(vFaderB1, 0, 255, 0, 1);
  faderOut.add(faderOutFloat);
  oscP5.send(faderOut, stripApp);

  faderOut = new OscMessage("/luminous/fader2");
  faderOutFloat = (float)map(vFaderB2, 0, 255, 0, 1);
  faderOut.add(faderOutFloat);
  oscP5.send(faderOut, stripApp);

  faderOut = new OscMessage("/luminous/fader3");
  faderOutFloat = (float)map(vFaderB3, 0, 255, 0, 1);
  faderOut.add(faderOutFloat);
  oscP5.send(faderOut, stripApp);

  faderOut = new OscMessage("/luminous/fader4");
  faderOutFloat = (float)map(vFaderB4, 0, 255, 0, 1);
  faderOut.add(faderOutFloat);
  oscP5.send(faderOut, stripApp);

  faderOut = new OscMessage("/luminous/fader5");
  faderOutFloat = (float)map(vFaderB5, 0, 255, 0, 1);
  faderOut.add(faderOutFloat);
  oscP5.send(faderOut, stripApp);

  faderOut = new OscMessage("/luminous/fader6");
  faderOutFloat = (float)map(vFaderB6, 0, 255, 0, 1);
  faderOut.add(faderOutFloat);
  oscP5.send(faderOut, stripApp);

  faderOut = new OscMessage("/luminous/dimmer1");
  faderOutFloat = (float)map(dimmer1, 0, 255, 0, 1);
  faderOut.add(faderOutFloat);
  oscP5.send(faderOut, stripApp);

  faderOut = new OscMessage("/luminous/dimmer2");
  faderOutFloat = (float)map(dimmer2, 0, 255, 0, 1);
  faderOut.add(faderOutFloat);
  oscP5.send(faderOut, stripApp);

  faderOut = new OscMessage("/luminous/dimmer3");
  faderOutFloat = (float)map(dimmer3, 0, 255, 0, 1);
  faderOut.add(faderOutFloat);
  oscP5.send(faderOut, stripApp);

  faderOut = new OscMessage("/luminous/dimmer4");
  faderOutFloat = (float)map(dimmer4, 0, 255, 0, 1);
  faderOut.add(faderOutFloat);
  oscP5.send(faderOut, stripApp);

  faderOut = new OscMessage("/luminous/dimmer5");
  faderOutFloat = (float)map(dimmer5, 0, 255, 0, 1);
  faderOut.add(faderOutFloat);
  oscP5.send(faderOut, stripApp);

  faderOut = new OscMessage("/luminous/dimmer6");
  faderOutFloat = (float)map(dimmer6, 0, 255, 0, 1);
  faderOut.add(faderOutFloat);
  oscP5.send(faderOut, stripApp);


  if ((millis() - faderWait) > 350) {
    faderWait = millis();

    faderOut = new OscMessage("/luminous/fader1");
    faderOutFloat = (float)map(setcolorMode, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/fader2");
    faderOutFloat = (float)map(vFader2, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/fader3");
    faderOutFloat = (float)map(vFader3, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/fader4");
    faderOutFloat = (float)map(vFader4, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/fader5");
    faderOutFloat = (float)map(vFader5, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/fader6");
    faderOutFloat = (float)map(vFader6, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/fader7");
    faderOutFloat = (float)map(vFader7, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/fader8");
    faderOutFloat = (float)map(vFader8, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/faderB1");
    faderOutFloat = (float)map(vFaderB1, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/faderB2");
    faderOutFloat = (float)map(vFaderB2, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/faderB3");
    faderOutFloat = (float)map(vFaderB3, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/faderB4");
    faderOutFloat = (float)map(vFaderB4, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/faderB5");
    faderOutFloat = (float)map(vFaderB5, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/faderB6");
    faderOutFloat = (float)map(vFaderB6, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);


    faderOut = new OscMessage("/luminous/dimmer1");
    faderOutFloat = (float)map(dimmer1, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/dimmer2");
    faderOutFloat = (float)map(dimmer2, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/dimmer3");
    faderOutFloat = (float)map(dimmer3, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/dimmer4");
    faderOutFloat = (float)map(dimmer4, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/dimmer5");
    faderOutFloat = (float)map(dimmer5, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/dimmer6");
    faderOutFloat = (float)map(dimmer6, 0, 255, 0, 1);
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/effect2");
    if (toggle) {
      faderOutFloat = 1.0f;
    } else {
      faderOutFloat = 0.0f;
    }
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/effect4");
    faderOutFloat = randomTouchState;
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/effect5");
    faderOutFloat = audioResponseState;
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);

    faderOut = new OscMessage("/luminous/effectB2");
    if (toggleB) {
      faderOutFloat = 1.0f;
    } else {
      faderOutFloat = 0.0f;
    }
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);
  }
}
/* incoming osc message are forwarded to the oscEvent method. */
public void oscEvent(OscMessage theOscMessage) {
  /* with theOscMessage.isPlugged() you check if the osc message has already been
   * forwarded to a plugged method. if theOscMessage.isPlugged()==true, it has already 
   * been forwared to another method in your sketch. theOscMessage.isPlugged() can 
   * be used for double posting but is not required.
   */
  if (theOscMessage.isPlugged()==false) {
    /* print the address pattern and the typetag of the received OscMessage */
    println("### received an osc message.");
    println("### addrpattern\t"+theOscMessage.addrPattern());
    println("### typetag\t"+theOscMessage.typetag());
  }

}


//////////////////////////////////////////////////
//////////////////////////////////////////////////
///////// DRAW ///////////////////
////////////////////////////////////////////////
//////////////////////////////////////////////////


public void transitionReset  () {
   // colorMode(HSB, 255, 255, 255, 255);
  transition = get();
  transitionOpacity = 255;
  
}

public void reLoadSketch(){
  
   //visuals[select].setInitVals();
  visuals[select].loadPresets();
   /// visuals[select].switchColorMode();
   visuals[select].setupSketch();
   
}

public void draw() {    
  
  //background(0);
  oscFaderSet();
  
  /// println("cur sketch : " + select);
  
  visuals[select].renderSketch();
  transitionDraw();
  
  
  if (pixEnable == true){
    drawPixelPusher();
  }
  if (artnetEnable == true){
    drawArtnet();
  }
  if (dmxEnable == true){
    drawDMX();
  }
  if (apaEnable == true){
    drawApa();
  }
  /*
  if (hrsc04Enable == true){
    drawHCSR04;
  }
  
  if (spoutEnable == true){
    drawSpout();
  }
  */
  if(showFramerate){
    println(frameRate);
  }
}

public void transitionDraw() {
  if (transitionOpacity > 0) {
    //colorMode(HSB, 255, 255, 255, 255);
    //transition = get();
    transitionOpacity -= 1;
    tint(255, 255,255,transitionOpacity);
    //float value = alpha(transition);
    image(transition, 0, 0);
    //tint(255, 255);
    noTint();
  }
      //colorMode(HSB, 255, 255, 255,255);
}





public void stop()
{
  // always close Minim audio classes when you are done with them
  //in.close();
  minim.stop();
  super.stop();
}

//////////////////////////////////////////////////
//////////////////////////////////////////////////
///////// MOUSE AND KEYBOARD ///////////////////
////////////////////////////////////////////////
//////////////////////////////////////////////////


public void keyPressed() {
  if(key == 'f'){
    if(showFramerate){
      showFramerate = false;
    } else {
      showFramerate = true;
    }
    
  }
  
  if(key == 'p'){
    /// loadPresets();
    
  }
  if (key == ' ') {
    transitionReset();
    //in.removeListener(visuals[select]);
    if(select >= visuals.length-1){
      select = 0;
    } else {
      select++;
    }
    
    /// select %= visuals.length;
    //in.addListener(visuals[select]);
    /// visuals[select].setup();
  } else {
    if (select == 7)
    {
      turing.keyPressed();
    }
  }
}


public void mouseClicked() {
  theX = mouseX;
  theY = mouseY;
  visuals[select].onClick(); 
}

public void mouseDragged() {

   /// Drawing for these sketches requires 0-1 pos values, not canvasW-H
  if(select == 5 || select == 3){
    theOSCX = map(mouseX, 0, canvasW, 0, 1);
    theOSCY = map(mouseY, 0, canvasH, 0, 1);

  } else {
      theX = mouseX;
      theY = mouseY;
  }
  visuals[select].doMouseDrag();
  
  
}


class stainedglassRenderer extends AudioRenderer {
/* OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/18093*@* */
/* !do not delete the line above, required for linking your tweak if you re-upload */
/**
 * <p>Much like the GameOfLife example, this demo shows the basic usage
 * pattern for the 2D cellular automata implementation, this time however
 * utilizing cell aging and using a tone map to render its current state.
 * The CA simulation can be configured with birth and survival rules to
 * create all the complete set of rules with a 3x3 cell evaluation kernel.</p>
 *
 * <p><strong>Usage:</strong><ul>
 * <li>click + drag mouse to disturb the CA matrix</li>
 * <li>press 'r' to restart simulation</li>
 * </ul></p>
 */

/* 
 * Copyright (c) 2011 Karsten Schmidt
 * 
 * This demo & library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * http://creativecommons.org/licenses/LGPL/2.1/
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */



  public String skchName = "Stained Glass CA Ornament";
  int rotations;
  int w=canvasW;
  int h=canvasH;
  int oX = w/2;
  int oY = h/2;
  int vFader5 = 128;

  CAMatrix ca;
  ToneMap toneMap;
  ColorGradient grad;

  stainedglassRenderer(AudioSource source) {
    //rotations =  (int) source.sampleRate() / source.bufferSize();
  }

  
  public void loadPresets() {
    println("Loading presets for" + skchName );
    doPresets();
  }


  public void setupSketch() {
    //size(canvasW, canvasH);
    //colorMode(RGB,255);
    //noStroke();
    colorMode(RGB, 255, 255, 255, 255);
    // the birth rules specify options for when a cell becomes active
    // the numbers refer to the amount of ACTIVE neighbour cells allowed,
    // their order is irrelevant
    byte[] birthRules=new byte[] { 
      1, 5, 7
      //1,5
    };
    // survival rules specify the possible numbers of allowed or required
    // ACTIVE neighbour cells in order for a cell to stay alive
    byte[] survivalRules=new byte[] { 
      0, 3, 5, 6, 7, 8
      //0,3,7,8
    };
    // setup cellular automata matrix
    ca=new CAMatrix(width, height);

    // unlike traditional CA's only supporting binary cell states
    // this implementation supports a flexible number of states (cell age)
    // in this demo cell states reach from 0 - 255

    CARule rule=new CARule2D(birthRules, survivalRules, 256, false);
    // we also want cells to automatically die when they've reached their
    // maximum age
    rule.setAutoExpire(true);
    // finally assign the rules to the CAMatrix
    ca.setRule(rule);

    // create initial seed pattern
    ca.drawBoxAt(width/2, height/2, 5, 1);

    // create a gradient for rendering/shading the CA
    grad=new ColorGradient();
    
    // NamedColors are preset colors, but any TColor can be added
    // see javadocs for list of names:
    // http://toxiclibs.org/docs/colorutils/toxi/color/NamedColor.html

    if (getSketchPresets("stainedGlass", false)) {
      XML[] xmlGradient = presets[preset].getChildren("gradient");
      println("StainedGlass Preset #" + preset + " Gradient elements: " + xmlGradient.length + " faders: " + setcolorMode + " " + vFader2 + " " + vFader3 + " " + vFader4 + " " + vFader5 + " " + vFader6 + " " + vFader7 + " " + vFader8);

      for (int xg = 0; xg < xmlGradient.length; xg++) {
        println("grad.addColorAt(" + xmlGradient[xg].getInt("at") + ",NamedColor." + xmlGradient[xg].getContent() + ");");
        grad.addColorAt(xmlGradient[xg].getInt("at"), NamedColor.getForName(xmlGradient[xg].getContent()) );
      }
    }
    
    // the tone map will map cell states/ages to a gradient color
    toneMap=new ToneMap(0, rule.getStateCount()-1, grad);
  }

  public void doPresets() {
    if (getSketchPresets("stainedGlass", false)) {
      XML[] xmlGradient = presets[preset].getChildren("gradient");
      println("StainedGlass Preset #" + preset + " Gradient elements: " + xmlGradient.length + " faders: " + setcolorMode + " " + vFader2 + " " + vFader3 + " " + vFader4 + " " + vFader5 + " " + vFader6 + " " + vFader7 + " " + vFader8);

      for (int xg = 0; xg < xmlGradient.length; xg++) {
        println("grad.addColorAt(" + xmlGradient[xg].getInt("at") + ",NamedColor." + xmlGradient[xg].getContent() + ");");
        grad.addColorAt(xmlGradient[xg].getInt("at"), NamedColor.getForName(xmlGradient[xg].getContent()) );
      }
    }
  }

  public void renderSketch() {
    //colorMode(RGB, 255, 255, 255, 255);
    loadPixels();
    if (mousePressed) {
      ca.drawBoxAt(mouseX, mouseY, 18, 4);
    }

    //if (onClick) {
    //ca.drawBoxAt(oX,oY,18,4);
    //}
    if (ca != null) {
      ca.update();
      try {
        toneMap.getToneMappedArray(ca.getMatrix(), pixels);
      }
      catch(NullPointerException e) {
      }
      //colorMode(RGB, 255-vFader3);
    }  


    updatePixels();
  }

  public void keyPressed() {
    if (key=='r') {
      ca.reset();
    }
  }
  
  public void doMouseDrag(){
    
  }

  public void onClick() {
    float cX = theX * canvasW;
    float cY = theY * canvasH;
    oX = (int)cX;
    oY = (int)cY;
    ca.drawBoxAt(oX, oY, 16, 4);
  }
}
class FluidRenderer extends AudioRenderer {

  /* OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/29833*@* */
  /* !do not delete the line above, required for linking your tweak if you re-upload */
  /* 
   Circus Fluid
   Made by Jared "BlueThen" C. on June 5th, 2011.
   Updated June 7th, 2011 (Commenting, refactoring, coloring changes)
   
   www.bluethen.com
   www.twitter.com/BlueThen
   www.openprocessing.org/portal/?userID=3044
   www.hawkee.com/profile/37047/
   
   Feel free to email me feedback, criticism, advice, job offers at:
   bluethen (@) gmail.com
   */


  // Variables for the timeStep
  public String skchName = "Fluidje";
  long previousTime;
  long currentTime;
  float timeScale = .5f; // Play with this to slow down or speed up the fluid (the higher, the faster)
  final int fixedDeltaTime = (int)(10 / timeScale);
  float fixedDeltaTimeSeconds = (float)fixedDeltaTime / 1000;
  float leftOverDeltaTime = 0;

  // The grid for fluid solving
  GridSolver grid;

  int rotations;

  FluidRenderer(AudioSource source) {
    //rotations =  (int) source.sampleRate() / source.bufferSize();
  }


  public void loadPresets() {
    println("Loading presets for" + skchName );
    getSketchPresets("fluidje", true);
    
  }

  
  public void setupSketch() {
    //size(canvasW, canvasH, P3D);
    colorMode(HSB, 255, 255, 255, 255);
    noStroke();

    // grid = new GridSolver(integer cellWidth)
    grid = new GridSolver(4);
    //colorMode(RGB, 255);
    //setcolorMode = 0;
    //vFader3 = 100;
    //getSketchPresets("fluidje", true);
  }

  public void renderSketch () {
   // colorMode(HSB, 255, 255, 255, 255);
    /******** Physics ********/
    // time related stuff
    // Calculate amount of time since last frame (Delta means "change in")
    currentTime = millis();
    long deltaTimeMS = (long)((currentTime - previousTime));
    previousTime = currentTime; // reset previousTime

      // timeStepAmt will be how many of our fixedDeltaTimes we need to make up for the passed time since last frame. 
    int timeStepAmt = (int)(((float)deltaTimeMS + leftOverDeltaTime) / (float)(fixedDeltaTime));

    // If we have any left over time left, add it to the leftOverDeltaTime.
    leftOverDeltaTime += deltaTimeMS - (timeStepAmt * (float)fixedDeltaTime); 

    if (timeStepAmt > 10) {
      timeStepAmt = 15; // too much accumulation can freeze the program!
      println("Time step amount too high");
    }

    // Update physics
    for (int iteration = 1; iteration <= timeStepAmt; iteration++) {

      try {
        grid.solve(fixedDeltaTimeSeconds * timeScale);
        throw new NullPointerException();
      }
      catch (NullPointerException e) {
        //println("null pointer in fluidje at grid.solve");
      }
    }

    grid.drawGrid();
    //println(frameRate);
  }

  /* Interation stuff below this line */

  public void doMouseDrag () {
    // The ripple size will be determined by mouse speed
    float force = dist(mouseX, mouseY, pmouseX, pmouseY) * 255;

    /* This is bresenham's line algorithm
     http://en.wikipedia.org/wiki/Bresenham's_line_algorithm
     Instead of plotting points for a line, we create a ripple for each pixel between the
     last cursor pos and the current cursor pos 
     */
    float dx = abs(theX - pmouseX);
    float dy = abs(theY - pmouseY);
    float sx;
    float sy;
    if (pmouseX < theX)
      sx = 1;
    else
      sx = -1;
    if (pmouseY < theY)
      sy = 1;
    else
      sy = -1;
    float err = dx - dy;
    float x0 = pmouseX;
    float x1 = theX;
    float y0 = pmouseY;
    float y1 = theY;
    while ( (x0 != x1) || (y0 != y1)) {
      // Make sure the coordinate is within the window
      if (((int)(x0 / grid.cellSize) < grid.density.length) && ((int)(y0 / grid.cellSize) < grid.density[0].length) &&
        ((int)(x0 / grid.cellSize) > 0) && ((int)(y0 / grid.cellSize) > 0))
        grid.velocity[(int)(x0 / grid.cellSize)][(int)(y0 / grid.cellSize)] += force;
      float e2 = 2 * err;
      if (e2 > -dy) {
        err -= dy;
        x0 = x0 + sx;
      }
      if (e2 < dx) {
        err = err + dx;
        y0 = y0 + sy;
      }
    }
  }
  /// set the onClick funciton using the global X and Y values

  public void onClick() {
    
    float cX = theX * canvasW;
    float cY = theY * canvasH;
    int oX = (int)cX;
    int oY = (int)cY;
    float force = 50000;
    if (((int)(cX / grid.cellSize) < grid.density.length) && ((int)(cY / grid.cellSize) < grid.density[0].length) &&
      ((int)(cX / grid.cellSize) > 0) && ((int)(cY / grid.cellSize) > 0)) {
      grid.velocity[(int)(cX / grid.cellSize)][(int)(cY / grid.cellSize)] += force;
    }
    /// doPointRipple();
  }

  
}



// Velocity: How fast each pixel is moving up or down
// Density: How much "fluid" is in each pixel.

// *note* 
// Density isn't conserved as far as I know. 
// Changing the velocity ends up changing the density too.

class GridSolver {
  int cellSize;
  
  // Use 2 dimensional arrays to store velocity and density for each pixel.
  // To access, use this: grid[x/cellSize][y/cellSize]
  float [][] velocity;
  float [][] density;
  float [][] oldVelocity;
  float [][] oldDensity;
  
  float friction = .58f  ;
  float speed = 21;
  float setContrastModeF = .00004f ;

 // float setcolorMode = 167;
  
  /* Constructor */
  GridSolver (int sizeOfCells) {
    cellSize = sizeOfCells;
    velocity = new float[PApplet.parseInt(width/cellSize)][PApplet.parseInt(height/cellSize)];
    density = new float[PApplet.parseInt(width/cellSize)][PApplet.parseInt(height/cellSize)];
  }
  
  /* Drawing */
  public void drawGrid () {
    for (int x = 0; x < velocity.length; x++) {
      for (int y = 0; y < velocity[x].length; y++) {
        /* Sine probably isn't needed, but oh well. It's pretty and looks more organic. */
       
       //color 
        float setContrastModeF = (float)map(vFader4, 0, 255, 0, .00008f); 
        
        //fill(167 + 127 * sin(density[x][y]*0.00004), 127, 0 + 127 * sin(velocity[x][y]*0.0004));
        
       
        //variable color
        fill(setcolorMode + 20 * sin(density[x][y]*setContrastModeF), vFader2, vFader3 + 127 * sin(velocity[x][y]*0.0004f));
        //(setcolorMode-50)+40*sin(PI*x/width
        rect(x*cellSize, y*cellSize, cellSize, cellSize);
      }
    }
  }
  /*
   public void setcolorMode(float colorspectrum) {
    setcolorMode = (int)map(colorspectrum, 0, 1, 0, 255);
  }
  */

  /* "Fluid" Solving
   Based on http://www.cs.ubc.ca/~rbridson/fluidsimulation/GameFluids2007.pdf
   To help understand this better, imagine each pixel as a spring.
     Every spring pulls on springs adjacent to it as it moves up or down (The speed of the pull is the Velocity)
     This pull flows throughout the window, and eventually deteriates due to friction
  */
  
 
    
  public void solve (float timeStep) {
    // Reset oldDensity and oldVelocity
    oldDensity = (float[][])density.clone();  
    oldVelocity = (float[][])velocity.clone();
    float setSpeedModeF = (float)map(vFader5, 0, 255, .57f, .59f);
    float friction = setSpeedModeF;
    
    for (int x = 0; x < velocity.length; x++) {
      for (int y = 0; y < velocity[x].length; y++) {
        /* Equation for each cell:
           Velocity = oldVelocity + (sum_Of_Adjacent_Old_Densities - oldDensity_Of_Cell * 4) * timeStep * speed)
           Density = oldDensity + Velocity
           Scientists and engineers: Please don't use this to model tsunamis, I'm pretty sure it's not *that* accurate
        */
        velocity[x][y] = friction * oldVelocity[x][y] + ((getAdjacentDensitySum(x,y) - density[x][y] * 4) * timeStep * speed);
        density[x][y] = oldDensity[x][y] + velocity[x][y];
      }
    }
  }

  public float getAdjacentDensitySum (int x, int y) {
    // If the x or y is at the boundary, use the closest available cell
    float sum = 0;
    if (x-1 > 0)
      sum += oldDensity[x-1][y];
    else
      sum += oldDensity[0][y];
      
    if (x+1 <= oldDensity.length-1)
      sum += (oldDensity[x+1][y]);
    else
      sum += (oldDensity[oldDensity.length-1][y]);
      
    if (y-1 > 0)
      sum += (oldDensity[x][y-1]);
    else
      sum += (oldDensity[x][0]);
      
    if (y+1 <= oldDensity[x].length-1)
      sum += (oldDensity[x][y+1]);
    else
      sum += (oldDensity[x][oldDensity[x].length-1]);
      
    return sum;
  }
}
/**
 * HC-SR04 Demo
 * Demonstration of the HC-SR04 Ultrasonic Sensor
 * Date: August 3, 2016
 * 
 * Description:
 *  Connect the ultrasonic sensor to the Arduino as per the
 *  hardware connections below. Run the sketch and open a serial
 *  monitor. The distance read from the sensor will be displayed
 *  in centimeters and inches.
 * 
 * Hardware Connections:
 *  Arduino | HC-SR04 
 *  -------------------
 *    5V    |   VCC     
 *    7     |   Trig     
 *    8     |   Echo     
 *    GND   |   GND
 *  
 * License:
 *  Public Domain
 
import processing.io.*;
// Pins
int TRIG_PIN = 4;
int ECHO_PIN = 5;

// Anything over 400 cm (23200 us pulse) is "out of range"
int MAX_DIST = 23200;

public void setupHCSR04() {

  // The Trigger pin will tell the sensor to range find
  //pinMode(TRIG_PIN, OUTPUT);
  GPIO.pinMode(TRIG_PIN, GPIO.OUTPUT);
  //GPIO.pinMode(4, GPIO.INPUT);

  // On the Raspberry Pi, GPIO 4 is pin 7 on the pin header,
  // located on the fourth row, above one of the ground pins

  //digitalWrite(TRIG_PIN, LOW);
  GPIO.digitalWrite(TRIG_PIN, GPIO.LOW);


 // frameRate(0.5);

  // We'll use the serial monitor to view the sensor output
  //Serial.begin(9600);
}

public void drawHCSR04() {
/*
   long t1;
   long t2;
   long pulse_width;
  float cm;
  float inches;

  // Hold the trigger pin high for at least 10 us
  //digitalWrite(TRIG_PIN, HIGH);
 GPIO.digitalWrite(4, GPIO.HIGH);
    
  //delay,icroseconds(10);
  //digitalWrite(TRIG_PIN, LOW); 
   GPIO.digitalWrite(4, GPIO.LOW);  

  // Wait for pulse on echo pin
  while ( digitalRead(ECHO_PIN) == 0 );
  //      GPIO.digitalRead(4) == GPIO.HIGH
  
  // Measure how long the echo pin was held high (pulse width)
  // Note: the micros() counter will overflow after ~70 min
  t1 = micros();
  while ( digitalRead(ECHO_PIN) == 1);
  //      GPIO.digitalRead(4) == GPIO.HIGH
  t2 = micros();
  pulse_width = t2 - t1;

  // Calculate distance in centimeters and inches. The constants
  // are found in the datasheet, and calculated from the assumed speed 
  //of sound in air at sea level (~340 m/s).
  cm = pulse_width / 58.0;
  inches = pulse_width / 148.0;

  // Print out results
  if ( pulse_width > MAX_DIST ) {
    print("Out of range");
  } else {
    //theOSCX = map(mouseX, 0, canvasW, 0, 1);
    Serial.print(cm);
    Serial.print(" cm \t");
    Serial.print(inches);
    Serial.println(" in");
  }
  
  // Wait at least 60ms before next measurement
  delay(60);


GPIO.releasePin(TRIG_PIN);
  exit();
}*/
/*/////python code
import RPi.GPIO as GPIO
import time
GPIO.setmode(GPIO.BCM)

TRIG = 23 
ECHO = 24

print "Distance Measurement In Progress"

GPIO.setup(TRIG,GPIO.OUT)
GPIO.setup(ECHO,GPIO.IN)

GPIO.output(TRIG, False)
print "Waiting For Sensor To Settle"
time.sleep(2)

GPIO.output(TRIG, True)
time.sleep(0.00001)
GPIO.output(TRIG, False)

while GPIO.input(ECHO)==0:
  pulse_start = time.time()

while GPIO.input(ECHO)==1:
  pulse_end = time.time()

pulse_duration = pulse_end - pulse_start

distance = pulse_duration * 17150

distance = round(distance, 2)

print "Distance:",distance,"cm"

GPIO.cleanup()
*/
class LastCallRenderer extends AudioRenderer {


  public String skchName = "lastCall";

  int numBoxes = 3;
  
  int theRed = 255;
  int theBlue = 255;
  int theGreen = 255;
  int theAlpha = 255;
  
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
  float boxW2 = 800;
  float boxH2 = 180;
  float boxX2 = 420;
  float boxY2 = 40;
  
  //long wall
  float boxW3 = 800;
  float boxH3 = 20;
  float boxX3 = 420;
  float boxY3 = 230;
  
  ColorBox theBox;
  
  ArrayList<ColorBox> BoxArray = new ArrayList<ColorBox>();
  
  int theColor = color(theRed, theBlue, theGreen, theAlpha);

  LastCallRenderer(AudioSource source) {
  }

 
  public void loadPresets() {
    getSketchPresets("lastCall", true); 
    println("Loading presets for" + skchName );
  }

  public void switchColorMode() {
    println("switching color mode for" + skchName );
    colorMode(HSB);
  }

  public void setupSketch() {
    //noStroke();
    colorMode(HSB, 255, 255, 255, 255);
    
    if (BoxArray.size() > 0) {
      BoxArray.clear();
    }
    println("SETTING UPr" + skchName );
    //getSketchPresets("lastCall", true);
    
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

    
     
  }


  /// set the onClick function using the global X and Y values
  public void doMouseDrag(){
    
  }
  public void onClick() {
    //// do soemthing with (theX, theY);
  }

  public void renderSketch() {
    colorMode(HSB, 255, 255, 255);
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
    
    
    //colorMode(HSB, 255);
   
    
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
  
  int boxColor = color(255,255,255);
  
  int theHue = 255;
  
  ColorBox(float tw, float th, float tx, float ty){
    tWidth = tw;
    tHeight = th;
    boxX = tx;
    boxY = ty;
  }
  
  /// dont really need this
  public void initBox(){
    
  }
  
  public void drawBox(){
    noStroke();
    fill(theHue, 255,255);
    rect(boxX, boxY, tWidth, tHeight);
  }
  
  
  
  
  
}
//import com.github.dlopuch.apa102_java_rpi.Apa102Output;
//import com.pi4j.io.spi.SpiChannel;
//import com.pi4j.io.spi.SpiDevice;
Client apaClient;
float apaRed, apaGreen, apaBlue;
int num_LED = 50;
byte[] apaColor = new byte[num_LED*4];



public void setupApa() {
  /*
  
//python socket  
  apaClient = new Client(this, "127.0.0.1", 5211)
//////////////////////////////////////////////////  

  //////////////////////////////////////////////eventual java port
  //public static final int NUM_LEDS = 32;
  
  //static public void main(String args[]) throws Exception {
//////////////////////////////////////////////////////////////////////////
Apa102Output.initSpi();
// Could also init with non-defaults using #initSpi(SpiChannel spiChannel, int spiSpeed, SpiMode spiMode)
// Default speed is 7.8 Mhz

Apa102Output strip = new Apa102Output(NUM_LEDS);

byte[] ledRGBs = new byte[ NUM_LEDS * 3 ];

while (true) {
  // <fill in your ledRGBs buffer with your pattern... eg examples/RainbowStrip.java>

  strip.writeStrip(ledRGBs);
}

    Apa102Output.initSpi();
    Apa102Output output = new Apa102Output(NUM_LEDS);

    byte[] leds = new byte[ NUM_LEDS * 3 ];
    final boolean[] loop = new boolean[] { true };


    // Make sure we turn everything off when shutting down
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("Shutting down: turning all LEDs off...");

        // Stop output thread loop
        loop[0] = false;

        // And turn all off
        byte[] allOff = new byte[ NUM_LEDS * 3 ];
        Arrays.fill(allOff, (byte) 0x00);
        try {
          output.writeStrip(allOff);
        } catch (IOException e) {
          throw new RuntimeException("ERROR turning all off", e);
        }
      }
      */
//}
    
    /* example program
    // Do rainbow loop
    
    double phi = 0;
    double phiIncrement = 16d / ROTATION_DURATION_MS;
    double pixelPhiIncrement = 1d / RAINBOW_SPREAD_PX;
    while (loop[0]) {

      double pixelPhi = phi;
      for (int i=0; i<leds.length; i += 3) {
        RainbowUtils.fillRgb(leds, i, pixelPhi);
        pixelPhi += pixelPhiIncrement;
      }

      output.writeStrip(leds);

      phi += phiIncrement;
      if (phi >= 1) {
        System.out.println("Looping back to red");
        phi = 0;
      }

      Thread.sleep(16);
    }
  }
  */
}


public void drawApa() {
  /*
  byte[] data = { (byte) 0, (byte) 0, (byte) 127, (byte) 0);
  //printIn(data);
  
  for (int i = 10; i<=290; i +=10){
  color pixColor = get(i,i)
  apaRed = red(pixColor);
  apaGreen = green(pixColor);
  apaBlue = blue(pixColor);
  apaColor[i-100/10*4-3+0] = (byte) apaRed;
  apaColor[i-100/10*4-3+1] = (byte) apaGreen;
  apaColor[i-100/10*4-3+2] = (byte) apaBlue;
  apaColor[i-100/10*4-3+2] = (byte) 0;
  } 
  apaClient.write(apaColor);
  /*for (int i=0; i<leds.length; i += 3) {
        RainbowUtils.fillRgb(leds, i, pixelPhi);
        pixelPhi += pixelPhiIncrement;
      }
      int colorsI = 0;
    for (int i=0; i<outputRgbBuffer.length; ) {
      outputRgbBuffer[ i++ ] = LXColor.red(   colors[ colorsI ]);
      outputRgbBuffer[ i++ ] = LXColor.green( colors[ colorsI ]);
      outputRgbBuffer[ i++ ] = LXColor.blue(  colors[ colorsI ]);
      colorsI++;
    }
      
      
 // output.writeStrip(leds);
 */
}
ArtnetP5 artnet;
PImage artnetimg;
/*
#controller ip address
#hint, use unicast address or 239.255.0.0 for multicast 
#e131.ip=239.255.0.0

#define how many rgb pixels are used on a universe, maximal 170 (=510 Channels)
#Example: if you use two 8x8 RGB Led matrix, you connected the first matrix on universe 1
#         and the second matrix on universe 2, you would set artnet.pixels.per.universe=64 
#e131.pixels.per.universe=170

#define the first universe id
#e131.first.universe.id=1

*/
public void setupArtnet() {
  
  artnet = new ArtnetP5();
  artnetimg = new PImage(170, 1, PApplet.RGB);
  colorMode(HSB, 255, 255, 255, 255);
 
     //mapSection(100, 20, 0,12);                                      here??
}

public void mapSection(int sketchX, int sketchY, int startDMX, int endDMX)
{
   
   for(int i = startDMX; i < endDMX; i++){
     
     artnetimg.set(i % artnetimg.width, i / artnetimg.width, get(sketchX + (i % width), sketchY + (i / width)));
     sketchX-= 4;
  
  }  
}

public void drawArtnet()  {
  
  int sketchX = 0;
  int sketchY = 0;
  int startDMX = 0;
  int endDMX = 0;
  
  //address the fixtures
  //mapSection(sketchX, sketchY, startDMX, endDMX);
  mapSection(240, 240, 0,1);
  mapSection(241, 240, 2,3);
  mapSection(242, 240, 4,5);
  mapSection(243, 240, 6,7);
  mapSection(244, 240, 8,9);
  mapSection(245, 240, 10,11);
  mapSection(246, 240, 12,13);
  mapSection(247, 240, 14, 15);
  mapSection(248, 240, 16,17);
  mapSection(249, 240, 18,19);
  mapSection(250, 240, 20,21);
  mapSection(960, 240, 132,144);
  mapSection(1020, 240, 144, 156);
  mapSection(1260, 240, 156, 168);
  
  //add a dimmer
  artnetimg.loadPixels();
  //colorMode(HSB, 255, 255, 255);
  
  for(int i = 0; i < 170; i++){    
    int c = color(artnetimg.pixels[i]);
         
   artnetimg.pixels[i] = color(hue(c), saturation(c), brightness(c) - dimmer4 - dimmer1);
   //artnetimg.pixels[i] = color(hue(c), saturation(c), brightness(c) - dimmer1);
  
   artnetimg.updatePixels();   
     
  }
 
  artnet.send(artnetimg.pixels, "192.168.1.111");
 
}

//PImage dmximg;

DmxP512 dmxOutput;
int universeSize=128;

boolean LANBOX=false;
String LANBOX_IP="192.168.1.77";

boolean DMXPRO=true;
String DMXPRO_PORT = "/dev/tty.usbserial-6AWY0JLI";//case matters ! on windows port must be upper cased.
//String DMXPRO_PORT="/dev/ttyUSB0";//case matters ! on windows port must be upper cased.
int DMXPRO_BAUDRATE=115000;

public void setupDMX() {
  
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

public void drawDMX() {
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
      int c = pixels[thisDmxPos];          
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
  
  float reactionRate = 0.025f;
  float reactionFader = 0.05f;
  
  // 3 different ways to make diffusion
  // diffusion rate is constant
  float diffusionRateU = .04f; // the inhabitor
  // diffusion rate is different from x to y direction
  float diffusionRateUX = .04f;
  float diffusionRateUY = .03f;
  // diffusion rate is changing in space
  float diffRateUYarr[][] = new float[w][h];
  float diffRateUXarr[][] = new float[w][h];
  
  float diffusionRateV = .01f; //the activator
  
  
  float deltaT = 4; // time step
  float deltaXSq = 1.0f; // x step
  
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
     colorMode(HSB, 255, 255, 255,100);
    //int w=canvasW;
    //int h=canvasH;
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

  public void renderSketch() {
    colorMode(HSB, 255, 255, 255, 255);
    diffusionU();
    diffusionV();
    reaction();
  
    loadPixels();
    for(int i=0;i<w;++i) {
      for(int j=0;j<h;++j) {
        pixels[j*w+i] = color(((setcolorMode+.01f)*gridU[i][j]+setcolorMode-vFader4-20), vFader2,gridU[i][j]*vFader3);
        //pixels[j*w+i] = color(gridU[i][j]*setcolorMode+oX, vFader2,gridU[i][j]*vFader3+oY);
        //pixels[j*w+i] = color(255*gridU[i][j]-setcolorMode, vFader2,gridU[i][j]*vFader3);
        //pixels[j*w+i] = color((gridU[i][j]*setcolorMode-20), vFader2,gridU[i][j]*vFader3);
   
  }
    }
    updatePixels();
 
    count++;
  }
  
  // diffusion of chemical u
  public void diffusionU() {
    for(int i=0;i<w;++i) {
      for(int j=0;j<h;++j) {

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
  public void diffusionV() {
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
  public void reaction() {
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
  public float reactionU(float aVal, float bVal, float FVal, float kVal) {
    float reactionFader = (float)map(vFader5, 0, 255, .01f, .2f);
    //return reactionRate*(aVal-aVal*aVal*aVal-bVal+kVal);
    return reactionFader*(aVal-aVal*aVal*aVal-bVal+kVal);
  }
  
  public float reactionV(float aVal, float bVal, float FVal, float kVal) {
    float reactionFader = (float)map(vFader5, 0, 255, .01f, .2f);
    //return reactionRate*FVal*(aVal-bVal);
    return reactionFader*FVal*(aVal-bVal);
  }
  
  // parameter F for reaction is different for every cell
  public void setupF() {
    //F .04 - .09
    for(int i=0;i<w;++i) {
      for(int j=0;j<h;++j) {
        Farr[i][j] = .01f+i*.09f/w;
        //Farr[i][j] = .03+i*.02/w;
        //Farr[i][j] = .04;
      }
    }
  }
  
  // parameter k for reaction is the same for every cell
  public void setupK() {
    for(int i=0;i<w;++i) {
      for(int j=0;j<h;++j) {
        karr[i][j]=.06f+j*0.4f/h;
        //karr[i][j] = .06;
      }
    }
  }
  
  
  public void setupDiffRates() {
    // Diffusion rate in vertical direction changes from 0.03 to 0.05
    for(int i=0;i<w;++i) {
      for(int j=0;j<h;++j) {
        diffRateUYarr[i][j] = .03f+j*.04f/h;
        diffRateUXarr[i][j] = .03f+j*.02f/h;
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

            try{
                float  uVal = gridU[i][j]; 
      
              gridU[i][j] = .5f+random(-.01f,.01f);
              gridV[i][j] = .25f+random(-.01f,.01f);  
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
class Gradient
{
  ArrayList colors;
  
  // Constructor
  Gradient()
  {
    colors = new ArrayList();
  }
  
  public void addColor(int c)
  {
    colors.add(c);
  }
  
  public int getGradient(float value)
  {
    // make sure there are colors to use
    if(colors.size() == 0)
      return 0xff000000;
    
    // if its too low, use the lowest value
    if(value <= 0.0f)
      try{
      return (int)(Integer) colors.get(0);
      }
      catch(NullPointerException e){
      }
    // if its too high, use the highest value
    if(value >= colors.size() - 1)
      return (int)(Integer) colors.get(colors.size() -  1);
    
    // lerp between the two needed colors
    int color_index = (int)value;
    int c1 = (int)(Integer) colors.get(color_index);
    int c2 = (int)(Integer) colors.get(color_index + 1);
    
    return lerpColor(c1, c2, value - color_index);
  }
}
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
  
  public void doPresets(){
    
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
      apply_heat(mouseX, mouseY, 30, .25f);
    if (mousePressed && (mouseButton == RIGHT))
      apply_heat(mouseX, mouseY, 30, -.2f);

    // Calculate the next step of the heatmap
    update_heatmap();

    // For each pixel, translate its heat to the appropriate color
    for (int i = 0; i < canvasW; ++i) {
      for (int j = 0; j < canvasH; ++j) {
        int thisColor = g.getGradient(heatmap[index][i][j]);
        //thisColor = color(red(thisColor) - 50, green(thisColor) - 50, blue(thisColor) - 50 ); //master fade

        set(i, j, thisColor);
      }
    }
    //colorMode(HSB, 255, 255, 255, 255);
  }

  public void update_heatmap()
  {
    // Calculate the new heat value for each pixel
    for (int i = 0; i < canvasW; ++i)
      for (int j = 0; j < canvasH; ++j)
        heatmap[index ^ 1][i][j] = calc_pixel(i, j);

    // flip the index to the next heatmap
    index ^= 1;
  }

  public float calc_pixel(int i, int j)
  {
    float total = 0.0f;
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
      apply_heat(oX, oY, 60, .10f);
    if (toggle == false)
      apply_heat(oX, oY, 60, -.10f);
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
  public void apply_heat(int i, int j, int r, float delta)
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
        heatmap[index][i + ii][j + jj] = constrain(heatmap[index][i + ii][j + jj], 0.0f, 20.0f);
        if (heatmap[index][i + ii][j + jj] < .3f && !toggle) {
          toggle = !toggle;
        }
        if (heatmap[index][i + ii][j + jj] > 19.7f && toggle) {
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

/// abstract class for audio visualization

abstract class AudioRenderer implements AudioListener {
  float[] left;
  float[] right;
  public synchronized void samples(float[] samp) { 
    left = samp;
  }
  public synchronized void samples(float[] sampL, float[] sampR) { 
    left = sampL; 
    right = sampR;
  }
  /// abstract void setup();
  /// functions to initialize and draw sketch
  public abstract void setupSketch();
  public abstract void renderSketch();
  
  /// functions to reset sketch
  //// abstract void setInitVals();
  // abstract void switchColorMode();
  public abstract void loadPresets();
  public abstract void onClick();
  public abstract void doMouseDrag();
  String skchName;
}


// abstract class for FFT visualization



abstract class FourierRenderer extends AudioRenderer {
  FFT fft; 
  float maxFFT;
  float[] leftFFT;
  float[] rightFFT;
  FourierRenderer(AudioSource source) {
    float gain = .125f;
    fft = new FFT(source.bufferSize(), source.sampleRate());
    maxFFT =  source.sampleRate() / source.bufferSize() * gain;
    fft.window(FFT.HAMMING);
  }

  public void calc(int bands) {
    if (left != null) {
      leftFFT = new float[bands];
      fft.linAverages(bands);
      fft.forward(left);
      for (int i = 0; i < bands; i++) leftFFT[i] = fft.getAvg(i);
    }
  }
}
/* OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/17043*@* */
//// these values get accessed by both classes
//// should be moved to main class tho
int type = 0;
int scl = 8; 
int res = 8;
//int scl = 2; 
//int res = 7;
//int scl = 1; 

/////////////////////////////////////////////////
//                                             //
class TuringRenderer extends AudioRenderer {
  //    The Secret Life of Turing Patterns       //
  //                                             //
  /////////////////////////////////////////////////
  // Inspired by the work of Jonathan McCabe
  // (c) Martin Schneider 2010

  //int vFader3 = 0;
  //int res = 6;
  //int scl = 3;
  //int res = 5;
  int dirs = 7, rdrop = 8, lim = 128;
  int palette = 0, pattern = 2, soft =1;
  int dx, dy, w, h, s;
  boolean border;
  boolean invert  = true;
  PImage img;

  // random angular offset
  float R = random(TWO_PI);
  // copy chemicals
  float[] pnew = new float[s];
  //for(int i=0; i<s; i++) pnew[i] = pat[i];

  float[] pat;
  int rotations;
  public String skchName = "Turing Living Pixels";
  TuringRenderer(AudioSource source) {
    //rotations =  (int) source.sampleRate() / source.bufferSize();
  }

  /*
  int scl = 2, dirs = 9, rdrop = 10, lim = 128;
   int res = 6, palette = 0, pattern = 2, soft = 2;
   int dx, dy, w, h, s;
   boolean border, invert;
   float[] pat;
   PImage img;
   */
  //  l i v  i n g              

 
  public void loadPresets() {
    println("Loading presets for" + skchName );
    //doPresets();
  }

  public void setupSketch() {

    colorMode(HSB, 255,255,255,255);
    noStroke();
    /*
    int setcolorMode = 0;
     int setContrastModeF = 60;
     int vFader2 = 255;
     int vFader3 = 0;
     */
    //int vFader5 = 50;
    //int vFader6 = 126;

    int setScaleDetailF = 2;
    int setResF = 4;
    //int setScaleDetailF = (int)map(vFader5, 0, 255, 1, 6);
    //int setResF = (int)map(vFader6, 0, 255, 2,6);
    /*    
     if (turingpreset == 0){
     pattern = 0;
     res = setResF;
     scl  = setScaleDetailF;
     }
     if (turingpreset == 1){
     pattern = 1;
     res = setResF;
     scl  = setScaleDetailF;
     }
     if (turingpreset == 2){
     pattern = 2;
     res = setResF;
     scl  = setScaleDetailF;
     }
     */
    res = setResF;
    scl  = setScaleDetailF;  
   //getSketchPresets("livingPixelism", true);

    if (getSketchPresets("livingPixelism", false)) {
      pattern = presets[preset].getChild("pattern").getIntContent();
      println("LivingPixelism Preset #" + preset + " Pattern: " + pattern + " Faders: " + setcolorMode + " " + vFader2 + " " + vFader3 + " " + vFader4 + " " + vFader5 + " " + vFader6 + " " + vFader7 + " " + vFader8);
    }

    reset();
  }
  /*
  void doPresets(){
    if (getSketchPresets("livingPixelism", false)) {
      pattern = presets[preset].getChild("pattern").getIntContent();
      println("LivingPixelism Preset #" + preset + " Pattern: " + pattern + " Faders: " + setcolorMode + " " + vFader2 + " " + vFader3 + " " + vFader4 + " " + vFader5 + " " + vFader6 + " " + vFader7 + " " + vFader8);
    }

  }
*/
  public void reset() {
    w = width/res; 
    h = height/res; 
    s = w*h;
    img = createImage(w, h, RGB);
    pat = new float[s];
    // random init
    for (int i=0; i<s; i++)  
      pat[i] = floor(random(480));
  }

  public void renderSketch() {

     //colorMode(HSB, 255);
    // constrain the mouse position
    if (border) {
      mouseX = constrain(mouseX, 0, width-1);
      mouseY = constrain(mouseY, 0, height-1);
    } 

    // add a circular drop of chemical
    if (mousePressed) {
      if (mouseButton != CENTER) {
        int x0 =  mod((mouseX-dx)/res, w);
        int y0 = mod((mouseY-dy)/res, h);
        int r = rdrop * scl / res ;
        for (int y=y0-r; y<y0+r; y++)
          for (int x=x0-r; x<x0+r; x++) {
            int xwrap = mod(x, w), ywrap = mod(y, h);
            //crashes when mouseclicks by border
            if (border && (x!=xwrap || y!=ywrap)) continue;          
            if (dist(x, y, x0, y0) <= r)
              pat[xwrap+w*ywrap] = mouseButton == LEFT ? 255 : 1;
          }
      }
    }

    // calculate a single pattern step
    pattern();

    // draw chemicals to the canvas
    try {
      img.loadPixels();
    }
    catch(NullPointerException e) {
      println("null pointer in livingpixelism at img.loadPixels");
    }
    for (int x=0; x<w; x++)
      for (int y=0; y<h; y++) {


        int c = (x+dx/res)%w + ((y+dy/res)%h)*w;
        int i = x+y*w;
        float val = invert ? 255-pat[i]: pat[i];

        float setBrightnessF = (float)map(vFader3, 0, 255, .0001f, .1f);
        float setContrastF = (float)map(vFader4, 0, 255, 0.001f, .035f); 

        switch(palette) {
          //case 0: img.pixels[c] = color((val-setcolorMode)/setContrastModeF, vFader2, val-vFader3); break;
          //case 0: img.pixels[c] = color((setcolorMode-10)+30*sin(TWO_PI*x/width), vFader2, vFader3-val); break;
          //case 0: img.pixels[c] = color(((setcolorMode-10)+20*sin(PI*x/width)-val)/setContrastModeF, vFader2, val-vFader3); break;
          //          
          //case 0: img.pixels[c] = color(setcolorMode + 20 * sin(val*setContrastModeF), vFader2,val-vFader3 + 127 * sin(val*0.0004));  break;
        case 0: 
        img.pixels[c] = color(setcolorMode + 20 * sin(val*setContrastF), vFader2,vFader3 - val + 127 * sin(val*0.0004f));
        //img.pixels[c] = color(setcolorMode + 20 * sin(val*setContrastF), vFader2, vFader3 - val);
           break; 
        case 1: 
         img.pixels[c] = color(setcolorMode + 20 * sin(val*setContrastF), vFader2, val + 127 * sin(val*.0004f));           
          break;
        case 2: 
          img.pixels[c] = color(setcolorMode + 20 * sin(val*setContrastF), vFader2, val + vFader3 * sin(val*.0004f));  
          break;
          //case 1: img.pixels[c] = color(128+val/4, 255, val); break;
          //case 2: img.pixels[c] = color(val,val,255-val); break;
        case 3: 
          img.pixels[c] = color(val, 178, vFader3/val); 
          break;
        case 4: 
          img.pixels[c] = color(val/10, 255, vFader3); 
          break;
        case 5: 
          img.pixels[c] = color(val*5, 255, val); 
          break;
        }
      }
    img.updatePixels();

    // display the canvas

    if (soft>0) smooth(); 
    else noSmooth();

    image(img, 0, 0, res*w, res*h);

    if (soft==2) filter(BLUR);
  }

  public void keyPressed() {
    switch(key) {
    case 'r': 
      reset(); 
      break;
    case 'p': 
      pattern = (pattern + 1) % 3; 
      break;
    case 'c': 
      palette = (palette + 1) % 6; 
      break;
    case 'b': 
      border = !border; 
      dx=0; 
      dy=0; 
      break;
    case 'i': 
      invert = !invert; 
      break;
    case 's': 
      soft = (soft + 1) % 3; 
      break;
    case '+': 
      lim = min(lim+8, 255); 
      break;
    case '-': 
      lim = max(lim-8, 0); 
      break;
    case CODED:
      switch(keyCode) {
      case LEFT: 
        scl = max(scl-1, 2); 
        break;
      case RIGHT:
        scl = min(scl+1, 6); 
        break; 
      case UP:   
        res = min(res+1, 5); 
        reset(); 
        break;
      case DOWN: 
        res = max(res-1, 1); 
        reset(); 
        break;
      }
      break;
    }
  }

  // moving the canvas
  
  public void doMouseDrag(){
    if (mouseButton == CENTER && !border) {
      dx = mod(dx + mouseX - pmouseX, width);
      dy = mod(dy + mouseY - pmouseY, height);
    }
    
  }
   /*
  public void mouseDragged() {
   
    if (mouseButton == CENTER && !border) {
      dx = mod(dx + mouseX - pmouseX, width);
      dy = mod(dy + mouseY - pmouseY, height);
    }
   
  }
  */

  // floor modulo
  public final int mod(int a, int n) {
    return a>=0 ? a%n : (n-1)-(-a-1)%n;
  }



  /// set the onClick funciton using the global X and Y values
  public void onClick() {
    // add a circular drop of chemical
    
    try{
      
       float cX = theX * canvasW;
        float cY = theY * canvasH;
        int oX = (int)cX;
        int oY = (int)cY;
        int x0 = mod((oX-dx)/res, w);
        int y0 = mod((oY-dy)/res, h);
        int r = rdrop * scl / res ;
        for (int y=y0-r; y<y0+r; y++)
          for (int x=x0-r; x<x0+r; x++) {
            int xwrap = mod(x, w), ywrap = mod(y, w);
            if (border && (x!=xwrap || y!=ywrap)) continue;          
            if (dist(x, y, x0, y0) < r)
              pat[xwrap+w*ywrap] = 255;
          }
      
      
    } catch (Exception e){
      println("error drawing pixelism on canvas: " + e);
    }
   
  }

  public void directiontoggle(float oscToggle) {
    //int mI = (int)placeholder;
    if (oscToggle == 1) {
      invert = false;
      type++;
      if (type > 1) {
        type=0;
      }
    }
    if (oscToggle == 0) {
      invert = true;
      type++;
      if (type > 1) {
        type=0;
      }
    }
  } 


  // this is where the magic happens ...

  public void pattern() {

    // random angular offset
    float R = random(TWO_PI);

    // copy chemicals
    float[] pnew = new float[s];
    for (int i=0; i<s; i++) pnew[i] = pat[i];

    // create matrices
    float[][] pmedian = new float[s][scl];
    float[][] prange = new float[s][scl];
    float[][] pvar = new float[s][scl];

    // iterate over increasing distances
    for (int i=0; i<scl; i++) {
      float d = (2<<i) ; 

      // update median matrix
      for (int j=0; j<dirs; j++) {
        float dir = j*TWO_PI/dirs + R;
        int dx = PApplet.parseInt (d * cos(dir));
        int dy = PApplet.parseInt (d * sin(dir));
        for (int l=0; l<s; l++) {  
          // coordinates of the connected cell
          int x1 = l%w + dx, y1 = l/w + dy;
          // skip if the cell is beyond the border or wrap around
          if (x1<0) if (border) continue; 
          else x1 = w-1-(-x1-1)% w; 
          else if (x1>=w) if (border) continue; 
          else x1 = x1%w;
          if (y1<0) if (border) continue; 
          else y1 = h-1-(-y1-1)% h; 
          else if (y1>=h) if (border) continue; 
          else y1 = y1%h;
          // update median
          pmedian[l][i] += pat[x1+y1*w] / dirs;
        }
      }

      // update range and variance matrix
      for (int j=0; j<dirs; j++) {
        float dir = j*TWO_PI/dirs + R;
        int dx = PApplet.parseInt (d * cos(dir));
        int dy = PApplet.parseInt (d * sin(dir));
        for (int l=0; l<s; l++) {  
          // coordinates of the connected cell
          int x1 = l%w + dx, y1 = l/w + dy;
          // skip if the cell is beyond the border or wrap around
          if (x1<0) if (border) continue; 
          else x1 = w-1-(-x1-1)% w; 
          else if (x1>=w) if (border) continue; 
          else x1 = x1%w;
          if (y1<0) if (border) continue; 
          else y1 = h-1-(-y1-1)% h; 
          else if (y1>=h) if (border) continue; 
          else y1 = y1%h;
          // update variance
          pvar[l][i] += abs( pat[x1+y1*w]  - pmedian[l][i] ) / dirs;
          // update range

          prange[l][i] += pat[x1+y1*w] > (lim + i*10) ? +1 : -1;
        }
      }
    }

    for (int l=0; l<s; l++) {  

      // find min and max variation
      int imin=0, imax=scl;
      float vmin = MAX_FLOAT;
      float vmax = -MAX_FLOAT;
      for (int i=0; i<scl; i+=1) {
        if (pvar[l][i] <= vmin) { 
          vmin = pvar[l][i]; 
          imin = i;
        }
        if (pvar[l][i] >= vmax) { 
          vmax = pvar[l][i]; 
          imax = i;
        }
      } 

      // turing pattern variants
      switch(pattern) {
      case 0: 
        for (int i=0; i<=imin; i++)    pnew[l] += prange[l][i]; 
        break;
      case 1: 
        for (int i=imin; i<=imax; i++) pnew[l] += prange[l][i]; 
        break;
      case 2: 
        for (int i=imin; i<=imax; i++) pnew[l] += prange[l][i] + pvar[l][i]/2; 
        break;
      }
    }

    // rescale values
    float vmin = MAX_FLOAT;
    float vmax = -MAX_FLOAT;
    for (int i=0; i<s; i++) {
      vmin = min(vmin, pnew[i]);
      vmax = max(vmax, pnew[i]);
    }       
    float dv = vmax - vmin;
    for (int i=0; i<s; i++) 
      pat[i] = (pnew[i] - vmin) * 255 / dv;
  }
}
//build a map of ledPos[logical led positions] = map of led positions on canvas


public void mapper() {

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
 

   internalX = 280;
   internalY = 40;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 1, ledsW)] = xyPixels(internalX, internalY, canvasW);
   internalX--;//direction
   }
//chamged for testing  
   internalX = 440;
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

    internalX = 10;
   internalY = 40;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 2, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }
   
   internalX = 500;
   internalY = 40;//starting point on canvas
   
   for (int x = 0; x < 240; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 3, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX++;
   }

   internalX = 290;
   internalY = 19;//starting point on canvas
   
   for (int x = 0; x < 288; x++) {//start and number of pixels on strip
   //v strip #
   ledPos[xyPixels(x, 4, ledsW)] = xyPixels(internalX, internalY, canvasW);
   //internalX++;//direction
   internalX--;
   }


   internalX = 290;
   internalY = 11;//starting point on canvas
   
   for (int x = 0; x < 288; x++) {//start and number of pixels on strip
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

/*

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
   */
}


public int xyPixels(int x, int y, int yScale) {
  return(x+(y*yScale));
}

public int xPixels(int pxN, int yScale) {
  return(pxN % yScale);
}

public int yPixels(int pxN, int yScale) {
  return(pxN / yScale);
}

public void setupPixelPusher() {
  ledPos = new int[ledsW*ledsH]; //create array of positions of leds on canvas
  mapper();
  registry = new DeviceRegistry();
  testObserver = new TestObserver();
  registry.addObserver(testObserver);
  //registry.setAntiLog(true);
  registry.setLogging(false);
  //background(0);
}

public void drawPixelPusher() {
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

        //  int lX = xPixels(thisLedPos, ledsW);
        //  int lY = yPixels(thisLedPos, ledsW);
        //   pixels[xyPixels(x,y,canvasW)] = color(r, g, b);
        int c = pixels[thisLedPos];


         
         c = color(hue(c), saturation(c), brightness(c) - dimmer1); 
         
       //  if (y >= 16 && y <= 71) //upstairs
        // c = color(hue(c), saturation(c), brightness(c) - dimmer2); 
         
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
class NoiseParticlesRenderer extends AudioRenderer {


  int cP = color(255, 255, 255);
  Color javaColor; 


  public String skchName = "Noise Particles";
  float noiseScale = 0.005f;
  float noiseZ = .8f;
  int particlesDensity = 4;
  int particleMargin = 8;  
  Particle[] particles;
  int[] currFrame;
  int[] prevFrame;
  int[] tempFrame;
  int i = 0;
  boolean okToDraw = true;
  boolean drawNext = false;
  ImgProc imgProc = new ImgProc();
  int speed;
  //  float setcolorMode = 200;

  NoiseParticlesRenderer(AudioSource source) {
    //speed =  (int) source.sampleRate() / source.bufferSize();
    
  }

  public void loadPresets() {
    println("Loading presets for" + skchName );
    getSketchPresets("noiseParticles", true);
  }

  public void setupSketch() {
    
    colorMode(HSB, 255, 255, 255, 255);
    //noStroke();
    currFrame = new int[width*height];
    prevFrame = new int[width*height];
    tempFrame = new int[width*height];
    /*
    setcolorMode = 200;
    vFader3 = 255;
    vFader4 = 28;
    vFader5 = 50;
    vFader6 = 60;
    */
    //getSketchPresets("noiseParticles", true);
    for (int i=0; i<width*height; i++) {
      currFrame[i] = color(0, 0, 0);
      prevFrame[i] = color(0, 0, 0);
      tempFrame[i] = color(0, 0, 0);
    }



    particles = new Particle[(width+particleMargin*2)/particlesDensity*(height+particleMargin*2)/particlesDensity];
    int i = 0;
    for (int y=-particleMargin; y<height+particleMargin; y+=particlesDensity) {
      for (int x=-particleMargin; x<width+particleMargin; x+=particlesDensity) {
        if (i == particles.length) {
          println("# of particles " + i);
          break;
        }
        //color control - insert audio option
        //purple
        //int c = color(180+60*sin(PI*x/width), 200, 255*sin(PI*y/width));
        //candle
        int c = color(5+20*sin(PI*x/width), 255, 225*sin(PI*y/height));
        //increased saturation
        //int c = color(200+60*sin(PI*x/width), 225, 255*sin(PI*y/width));
        //int c = color(360*sin(PI*x/width), 127, 255*sin(PI*y/width));
        particles[i++] = new Particle(x, y, c);
      }
    }
  }


  public void renderSketch() {
    //colorMode(HSB, 255, 255, 255);
    if (okToDraw) {
      okToDraw = false;
      noiseZ += 2*noiseScale;

      imgProc.blur(prevFrame, tempFrame, width, height);
      imgProc.scaleBrightness(tempFrame, tempFrame, width, height, 0.2f);  
      arraycopy(tempFrame, currFrame);
      for (int i=0; i<particles.length; i++) {
        try{
          if (particles.length > i - 10) {
            particles[i].update();
            particles[i].drawParticles();
          }
        } catch(Exception e){
          println("error drawing noise field particles: " + e);
        }
        /*
        try {
         particles[i].update();
         if (particles.length > i - 3)
         particles[i].draw();
         
         throw new NullPointerException();
         }
         catch (NullPointerException e) {
         }
         */
      }  
      imgProc.drawPixelArray(currFrame, 0, 0, width, height);
      arraycopy(currFrame, prevFrame);

      if (drawNext)
        resetParticles();

      okToDraw = true;
      
    }
/*
    if (mousePressed && (mouseButton == LEFT))
    theX = mouseX;
    theY = mouseY;
    onClick();
    if (mousePressed && (mouseButton == RIGHT)) {
    }
   */
  }

  /////////////////////////////////////////////////////////
  ////// WHY ARE THERE TWO onCLICK FUNCTIONS ////////////////
  //////////////////////////////////////////////////////////
  /*
  public void onClick(float mX, float mY) {
    float cX = mX * canvasW;
    float cY = mY * canvasH;
    int oX = (int)cX;
    int oY = (int)cY;
    onClick(oX, oY);
  }
  */
  
  /// set the onClick funciton using the global X and Y values
  
  public void doMouseDrag(){
    onClick();
  }

  public void onClick() {
    
     //println("NOISE PARTICLES: mouse" + theX + " " + theY + " osx: " + theOSCX + " " + theOSCY);
    int brush = 4;
    int setContrastModeF = (int)map(vFader4, 0, 255, 0, 60);

    float cX = theOSCX * canvasW;
    float cY = theOSCY * canvasH;
    int oX = (int)cX;
    int oY = (int)cY;


    for (int y= oY - brush; y < brush + oY; y+=particlesDensity) {
      for (int x= oX - brush; x < brush + oX; x+=particlesDensity) {
        //    for(int y=-particleMargin; y<height+particleMargin; y+=particlesDensity) {
        //    for(int x=-particleMargin; x<width+particleMargin; x+=particlesDensity) {
        if (i == particles.length) {
          println(i);
          i=0;
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
        particles[i++] = new Particle(x, y, c);
        //particles[i++] = new Particle(x, y, cP);
      }
    }
  }

  class Particle {
    float x;
    float y;
    int c;
    float speed = 2;
    Particle(int x, int y, int c) {
      this.x = x;
      this.y = y;
      this.c = c;
    }
    public void update() {
      float setNoiseValueF = (float)map(vFader6, 0, 255, .0001f, .5f);
      //float noiseScale = setNoiseValueF;
      //float noiseVal = noise(x*noiseScale, y*noiseScale, noiseZ);
      float noiseVal = noise(x*setNoiseValueF, y*setNoiseValueF, noiseZ);
      //set direction/randomness
      //float angle = noiseVal*-1.4*PI;
      float angle = noiseVal*-2*PI;
      float speed = (float)map(vFader5, 0, 255, 0, 2.1f);
      x -= speed * cos(angle);
      y += speed * sin(angle);

      if (x < -particleMargin) {
        x += width + 2*particleMargin;
      } else if (x > width + particleMargin) {
        x -= width + 2*particleMargin;
      }

      if (y < -particleMargin) {
        y += height + 2*particleMargin;
      } else if (y > height + particleMargin) {
        y -= height + 2*particleMargin;
      }
    }
    public void drawParticles() {
      if ((x >= 0) && (x < width-1) && (y >= 0) && (y < height-1)) {
        int currC = currFrame[(int)x + ((int)y)*width];
        currFrame[(int)x + ((int)y)*width] = blendColor(c, currC, OVERLAY);
      }
    }
  }


  public void clearParticles(float placeholder) {
    int mI = (int)placeholder;
    clearParticles(mI);
  }


  public void clearParticles(int placeholder) {
    if (okToDraw)
      resetParticles();
    else
      drawNext = true;
  }

  public void resetParticles() {
    okToDraw = false;
    particles = new Particle[(width+particleMargin*2)/particlesDensity*(height+particleMargin*2)/particlesDensity];
    int i = 0;
    for (int y=-particleMargin; y<height+particleMargin; y+=particlesDensity) {
      for (int x=-particleMargin; x<width+particleMargin; x+=particlesDensity) {
        if (i == particles.length) {
          println("# of particles " + i);
          break;
        }
        //color control - insert audio option
        //purple
        //int c = color(180+60*sin(PI*x/width), 200, 255*sin(PI*y/width));
        //earthy
        //int c = color(18+60*sin(PI*x/width), 200, 255*sin(PI*y/width));
        //increased saturation
        //int c = color(200*sin(PI*x/width), 225, 255*sin(PI*y/width));
        //int c = color(360*sin(PI*x/width), 127, 255*sin(PI*y/width));
        //variable color
        int c = color((setcolorMode-50)+40*sin(PI*x/width)*2, 225, 255*sin(PI*y/canvasH));
        particles[i++] = new Particle(x, y, c);
      }
    }
    drawNext = false;
    okToDraw = true;
  }


  /*
  public void setcolorMode(float colorspectrum) {
   setcolorMode = (int)map(colorspectrum, 0, 1, 0, 255);
   }    
   */

  public class ImgProc {

    public void ImgProc() {
    }

    public void drawPixelArray(int[] src, int dx, int dy, int w, int h) {  
      //backBuf.loadPixels();
      //arraycopy(src, backBuf.pixels);
      //backBuf.updatePixels();
      //image(backBuf, dx, dy);
      loadPixels();
      int x;
      int y;
      for (int i=0; i<w*h; i++) {
        x = dx + i % w;
        y = dy + i / w;
        pixels[x  + y * w] = src[i];
      }
      updatePixels();
    }

    public void blur(int[] src, int[] dst, int w, int h) {
      int c;
      int r;
      int g;
      int b;
      for (int y=1; y<h-1; y++) {
        for (int x=1; x<w-1; x++) {      
          r = 0;
          g = 0;
          b = 0;
          for (int yb=-1; yb<=1; yb++) {
            for (int xb=-1; xb<=1; xb++) {
              c = src[(x+xb)+(y-yb)*w];      
              r += (c >> 16) & 0xFF;
              g += (c >> 8) & 0xFF;
              b += (c) & 0xFF;
            }
          }      
          r /= 9;
          g /= 9;
          b /= 9;
          dst[x + y*w] = 0xFF000000 | (r << 16) | (g << 8) | b;
        }
      }
    }

    //you must be in RGB colorModel
    public void scaleBrightness(int[] src, int[] dst, int w, int h, float s) {
      int r;
      int g;
      int b;
      int c;
      int a;
      float as = s;
      s = 1.0f;
      for (int y=0; y<h; y++) {
        for (int x=0; x<w; x++) {
          c = src[x + y*w];
          a = (int)(as *((c >> 24) & 0xFF));
          r = (int)(s * ((c >> 16) & 0xFF));
          g = (int)(s *((c >> 8) & 0xFF));
          b = (int)(s *((c) & 0xFF));      
          dst[x + y*w] = (a << 24) | (r << 16) | (g << 8) | b;
          //ch = hue(c);
          //cs = saturation(c);
          //cb = brightness(c) * s;
          //dst[x + y*w] = color(ch, cs, cb);
          //dst[x + y*w] = src[x + y*w];
        }
      }
    }
  }
}
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

  public void update()
  {
    float setSpeed = (float)map(vFader5, 0, 255, 0.1f, 10);
    int setDetailF = (int)map(vFader6, 0, 255, 1, 20);
    
    
    //float noisefields = setNoiseDetailF;
    //velocity.x = setSpeed*(noise(noisefield.oX/10+position.y/100)-.5);
    //velocity.y = setSpeed*(noise(noisefield.oY/10+position.x/100)-.5);
    velocity.x = setSpeed*(noise(noisefield.oX/setDetailF+position.y/10)-0.5f);
    velocity.y = setSpeed*(noise(noisefield.oY/setDetailF+position.x/10)-0.5f);
    position.add(velocity);

    if (position.x<0)position.x+=width;
    if (position.x>width)position.x-=width;
    if (position.y<0)position.y+=height;
    if (position.y>height)position.y-=height;
  }

  public void render()
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
  
  public void update()
  {
    for(int i = 0; i < NUM_PARTICLES; i++)
    {
      particles[i].update();
    }
  }
  
  public void render()
  {
    for(int i = 0; i < NUM_PARTICLES; i++)
    {
      particles[i].render();
    }
  }
}
class TestObserver implements Observer {
  public boolean hasStrips = false;
  public void update(Observable registry, Object updatedDevice) {
    ///println("Registry changed!");
    if (updatedDevice != null) {
      // println("Device change: " + updatedDevice);
    }
    this.hasStrips = true;
  }
};
////first try of moving osc into a seperate tab didn't work
class PerlinColorRenderer extends AudioRenderer {


  // Perlin Noise Demo - Jim Bumgardner
  //dje mod

public String skchName = "Perlin color";
  float perlinColor;
  float kNoiseDetail = 0.001f;
  float r;
  float speed = .01f;
  float ox = 200;
  float oy = 200;
  float cX = 0;
  float cY = 0;

  int rotations;

  PerlinColorRenderer(AudioSource source) {
    //rotations =  (int) source.sampleRate() / source.bufferSize();
  } 
 
  public void loadPresets() {
    println("Loading presets for" + skchName );
    getSketchPresets("perlincolor", true);
  }

  public void setupSketch() {
    //background(0);
   colorMode(HSB, 255, 255, 255, 255);
    
    r = width/PI;
    //noStroke();
    //smooth();
    
    noiseDetail(3, .6f);
    //colorMode(HSB, 1); //setupPixelPusher();
    //colorMode(HSB, 1,1,1, 255);

    //getSketchPresets("perlincolor", true);
    /*
  setcolorMode = 205;
     vFader2 = 255;
     vFader3 = 125;
     vFader4 = 128;
     vFader5 = 10;
     vFader6 = 200;
     */
  }


  public void renderSketch()
  {
    //background(0);
      
   colorMode(HSB, 1,1,1,255);
    //ox += max(-speed,min(speed,(mouseX-width/2)*speed/r));
    //oy += max(-speed,min(speed,(mouseY-height/2)*speed/r));
    float setSpeedModeF = (float)map(vFader5, 0, 255, .0001f, .08f);
    speed = setSpeedModeF;
    ox += max(-speed, min(speed, (width/2-cX)*speed/r));
    oy += max(-speed, min(speed, (height/2-cY)*speed/r));


    for (int y = 0; y < height; ++y)
    {
      for (int x = 0; x < width; ++x)
      {
        //change colors
        //setcolorMode
        //set(x,y,color(.1-y*.1/height,4-v,.7+v*v));
        float setcolorModeF = (float)map(setcolorMode, 0, 255, 0.01f, .98f);
        float setSatModeF = (float)map(vFader2, 0, 255, 0, 1);
        float setBrightModeF = (float)map(vFader3, 0, 255, 0, 1);
        float setContrastModeF = (float)map(vFader4, 0, 255, 0.1f, .6f);
        float setNoiseDetailF = (float)map(vFader6, 0, 255, .000001f, .06f);


        //float v = noise(ox+x*kNoiseDetail,oy+y*kNoiseDetail,millis()*setSpeedModeF);     
        //float v = noise(ox+x*kNoiseDetail,oy+y*kNoiseDetail,millis()*.0001);     
        //set(x,y,color(setcolorModeF-y*.05/height,(4-v)*setSatModeF,(setContrastModeF+v*v)*setBrightModeF));    
        float v = noise(ox+x*(kNoiseDetail+setNoiseDetailF), oy+y*(kNoiseDetail+setNoiseDetailF), setNoiseDetailF);
        set(x, y, color(setcolorModeF-setContrastModeF*v, setSatModeF, (v+v)*setBrightModeF));
      }
    }
    //colorMode(HSB, 255, 255, 255, 255);

  }
  
  
/// set the onClick function using the global X and Y values
public void doMouseDrag(){
  
}
  public void onClick() {
    cX = theX * canvasW;
    cY = theY * canvasH;
    //ellipse(cX, cY, 10, 10);
    //rect(0, 0, 10, 10,7);
    int oX = (int)cX;
    int oY = (int)cY;
    //ox += max(-speed,min(speed,(cX-width/2)*speed/r));
    //oy += max(-speed,min(speed,(cY-height/2)*speed/r));

    brush_clouds(oX, oY, 25, .25f);
  }

    
   public void brush_clouds(int i, int j, int r, float delta)
   {/*
   ox += max(-speed,min(speed,(ox-width/2)*speed/r));
   oy += max(-speed,min(speed,(oy-height/2)*speed/r));
   
   for (int y = 0; y < height; ++y)
   {
   for (int x = 0; x < width; ++x)
   {
   float v = noise(ox+x*kNoiseDetail,oy+y*kNoiseDetail,millis()*.0002);
   //change colors
   //setcolorMode
   //set(x,y,color(.1-y*.1/height,4-v,.7+v*v)); 
   set(x,y,color(.1-y*.1/height,4-v,.3+v*v));    
   }
   }*/
   }
}
public void loadMasterPresets() {
  presets = xml.getChildren("master");
  preset = 0;
  println("Loading dimmers.");
  dimmer1 = presets[preset].getChild("dimmer1").getIntContent();
  dimmer2 = presets[preset].getChild("dimmer2").getIntContent();
  dimmer3 = presets[preset].getChild("dimmer3").getIntContent();
  dimmer4 = presets[preset].getChild("dimmer4").getIntContent();
  dimmer5 = presets[preset].getChild("dimmer5").getIntContent();
  dimmer6 = presets[preset].getChild("dimmer6").getIntContent();
  dimmer7 = presets[preset].getChild("dimmer7").getIntContent();
  dimmer8 = presets[preset].getChild("dimmer8").getIntContent();
  dimmer9 = presets[preset].getChild("dimmer9").getIntContent();
  dimmer10 = presets[preset].getChild("dimmer10").getIntContent();
  println("Loading faders.");
  setcolorMode = presets[preset].getChild("vFader1").getIntContent();
  vFader2 = presets[preset].getChild("vFader2").getIntContent();
  vFader3 = presets[preset].getChild("vFader3").getIntContent();
  vFader4 = presets[preset].getChild("vFader4").getIntContent();
  vFader5 = presets[preset].getChild("vFader5").getIntContent();
  vFader6 = presets[preset].getChild("vFader6").getIntContent();
  vFader7 = presets[preset].getChild("vFader7").getIntContent();
  vFader8 = presets[preset].getChild("vFader8").getIntContent();
}

public boolean getSketchPresets(String tagName, boolean customLoadMsg) {
  presets = xml.getChildren(tagName);
  println();
  println("There are " + presets.length + " presets for " + tagName);
  if (presets.length > preset) {
    //    positionElement = presets[0].getChild("vFader1");
    //    vFader1 = positionElement.getIntContent();
    setcolorMode = presets[preset].getChild("vFader1").getIntContent();
    vFader2 = presets[preset].getChild("vFader2").getIntContent();
    vFader3 = presets[preset].getChild("vFader3").getIntContent();
    vFader4 = presets[preset].getChild("vFader4").getIntContent();
    vFader5 = presets[preset].getChild("vFader5").getIntContent();
    vFader6 = presets[preset].getChild("vFader6").getIntContent();
    vFader7 = presets[preset].getChild("vFader7").getIntContent();
    vFader8 = presets[preset].getChild("vFader8").getIntContent();
    if (customLoadMsg) {
      println("Faders Loaded. " + tagName + " Preset #" + preset + " Faders: " + setcolorMode + " " + vFader2 + " " + vFader3 + " " + vFader4 + " " + vFader5 + " " + vFader6 + " " + vFader7 + " " + vFader8);
    }
    return true;
  } else {
    println(tagName + " Preset #" + preset + " is not in presets.xml");
    return false;
  }
}

public void savePresets() {
  setSketchPresets();
  saveXML(xml, "data/presets.xml");
  println("Saved");
  xml = loadXML("data/presets.xml");
}

public void setSketchPresets() {

  if (presets.length > preset) {
    //    positionElement = presets[0].getChild("vFader1");
    //    vFader1 = positionElement.getIntContent();
    presets[preset].getChild("vFader1").setContent(Integer.toString(setcolorMode));
    presets[preset].getChild("vFader2").setContent(Integer.toString(vFader2));
    presets[preset].getChild("vFader3").setContent(Integer.toString(vFader3));
    presets[preset].getChild("vFader4").setContent(Integer.toString(vFader4));
    presets[preset].getChild("vFader5").setContent(Integer.toString(vFader5));
    presets[preset].getChild("vFader6").setContent(Integer.toString(vFader6));
    presets[preset].getChild("vFader7").setContent(Integer.toString(vFader7));
    presets[preset].getChild("vFader8").setContent(Integer.toString(vFader8));
    //additional sketch properties need to be written somehow.
    println("Preset Saved. Sketch# " + select + " Preset #" + preset + " Faders: " + setcolorMode + " " + vFader2 + " " + vFader3 + " " + vFader4 + " " + vFader5 + " " + vFader6 + " " + vFader7 + " " + vFader8);
  } else {
    println("Sketch#" + select + " Preset #" + preset + " is not in presets.xml or other inconsistency");
  }
}

class RadarRenderer extends AudioRenderer {

public String skchName = "Radar Renderer";
  float aura = 2;
  float orbit = .4f;
  int delay = 3;

  int rotations;

  RadarRenderer(AudioSource source) {
    rotations =  (int) source.sampleRate() / source.bufferSize();
  }
  
 public void setInitVals(){
    
 }
 public void loadPresets() {
    println("Loading presets for" + skchName );
  }
  

  public void setupSketch() {
    colorMode(HSB, TWO_PI * rotations, 1, 1);
    // background(0);
  }

public void doMouseDrag(){
  
}
  /// set the onClick function using the global X and Y values
  public void onClick(){
    //// do soemthing with (theX, theY);
  }
  
public void renderSketch(){
     
    colorMode(HSB, TWO_PI * rotations, 1, 1);

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

/*
//
//            SpoutSender
//
//      Send to a Spout receiver
//
//           spout.zeal.co
//
//       http://spout.zeal.co/download-spout/
//

PImage spoutImage;

PGraphics spoutGraphics; // Graphics for demo


// DECLARE A SPOUT OBJECT
//Spout spout;

public void setupSpout() {

  // Initial window size
  //size(640, 360, P3D);
  textureMode(NORMAL);
  
  // Create a graphics object
  //spoutGraphics = createGraphics(1280, 255, P3D);
  
  // Load an image
  //img = loadImage("SpoutLogoMarble3.bmp");
  //spoutImage = get();
  
  // The dimensions of graphics or image objects
  // do not have to be the same as the sketch window
    
  // CREATE A NEW SPOUT OBJECT
  spout = new Spout(this);
  
  // CREATE A NAMED SENDER
  // A sender can be created now with any name.
  // Otherwise a sender is created the first time
  // "sendTexture" is called and the sketch
  // folder name is used.  
  spout.createSender("TouchMagnetP3");
  
} 

void drawSpout()  { 
    //
    //background(0);
    //noStroke();
    /*
    // Draw the graphics   
    pushMatrix();
    translate(width/2.0, height/2.0, -100);
    rotateX(frameCount * 0.01);
    rotateY(frameCount * 0.01);      
    scale(110);
    TexturedCube(img);
    popMatrix();
    
    // OPTION 1: SEND THE TEXTURE OF THE DRAWING SURFACE
    // Sends at the size of the window    
    spout.sendTexture();
    //image(spoutGraphics, 0, 0, width, height);
    
    /*
    // OPTION 2: SEND THE TEXTURE OF GRAPHICS
    // Sends at the size of the graphics
    spoutGraphics.beginDraw();
    spoutGraphics.fill(255)
    spoutGraphics.endDraw();
    spout.sendTexture(spoutGraphics);
    image(spoutGraphics, 0, 0, width, height);
    
    pgr.beginDraw();
    pgr.lights();
    pgr.background(0, 90, 100);
    pgr.fill(255);
     pushMatrix();
    pgr.translate(pgr.width/2, pgr.height/2);
    pgr.rotateX(frameCount/100.0);
    pgr.rotateY(frameCount/100.0);
    pgr.fill(192);
    pgr.box(pgr.width/4); // box is not textured
    popMatrix();
    pgr.endDraw();
    spout.sendTexture(pgr);
    image(pgr, 0, 0, width, height);
    
    
    
    
    // OPTION 3: SEND THE TEXTURE OF AN IMAGE
    // Sends at the size of the image
    spout.sendTexture(spoutImage);
    //image(img, 0, 0, width, height); //render spoutImage
    
   
}
*/
  public void settings() {  size(300,80, P2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TouchMagnetP3" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
