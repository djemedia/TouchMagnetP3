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


import com.heroicrobot.dropbit.devices.*;
import com.heroicrobot.dropbit.common.*;
import com.heroicrobot.dropbit.discovery.*;
import com.heroicrobot.dropbit.registry.*;
import com.heroicrobot.dropbit.devices.pixelpusher.*;
 
DeviceRegistry registry;
TestObserver testObserver;


import toxi.sim.automata.*;
import toxi.math.*;
import toxi.color.*;


import ddf.minim.*;

import artnetP5.*;
import ch.bildspur.artnet.*;
import ch.bildspur.artnet.packets.*;
import ch.bildspur.artnet.events.*;


import dmxP512.*;

import processing.net.*;
import processing.serial.*;

import javax.swing.JColorChooser;
import java.awt.Color; 

// import hypermedia.net.*;
//import processing.core.*;
import java.util.*;

import oscP5.*;
OscP5 oscP5;
OscP5 oscP5B;

import netP5.*;
NetAddress myRemoteLocation;
NetAddress stripApp;


//import spout.*;
//Spout spout;

PGraphics canvas;
PImage transition;


boolean showFramerate = false;

boolean ready_to_go = true;
int lastPosition;


int canvasW = 300;
int canvasH = 60;

int ledsW = 300;
int ledsH = 60;
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


boolean artnetEnable = true;
boolean dmxEnable =false;
boolean pixEnable = true;
boolean apaEnable = true;
boolean hcsr04Enable = false;
//boolean spoutEnable = true;
//boolean syphonEnable = false; 

//////////////////////////////////////////////////
//////////////////////////////////////////////////
///////// SETUP ///////////////////
////////////////////////////////////////////////
//////////////////////////////////////////////////
void setup() {
  //size(canvasW, canvasH);
  //fullScreen();
  size(300,100, P2D);
  //textureMode(NORMAL);
  //background(0);
  //frameRate(30);
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
    fluidje, stainedglass, heatmap, noiseParticles, turing, fitzhugh, perlincolor, noisefield, lastcall 
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
  myRemoteLocation = new NetAddress("192.168.3.1", 9000);
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

void oscSketch1(float iA) {
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
void oscSketch2(float iA) {
  if (iA == 1) {
     transitionReset();
    //in.removeListener(visuals[select]);
    select = 6;
    preset = 0;
    //in.addListener(visuals[select]);
    /// visuals[select].setup();
    //add code to prevent double tap
    reLoadSketch();
    
  }
}
void oscSketch3(float iA) {
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
void oscSketch4(float iA) {
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
void oscSketch5(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 7;
    preset = 0;
    //in.addListener(visuals[select]);
    //visuals[select].setup();
    reLoadSketch();
  }
}
void oscSketch6(float iA) {
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
void oscSketch7(float iA) { 
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 1;
    preset = 0;
    //in.addListener(visuals[select]);
    ///visuals[select].setup();
    reLoadSketch();
  }
}

void oscSketch8(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 1;
    preset = 1;
    //in.addListener(visuals[select]);
    ///visuals[select].setup();
    reLoadSketch();
  }
}
void oscSketch9(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 1;
    preset = 3;
    //in.addListener(visuals[select]);
    reLoadSketch();
  }
}

void oscSketch10(float iA) {
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
void oscSketch11(float iA) {
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
void oscSketch12(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 4;
    preset = 0;
    //in.addListener(visuals[select]);
    ////visuals[select].setup();
    reLoadSketch();
  }
}
void oscSketch13(float iA) {
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
void oscSketch14(float iA) {
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
void oscSketch15(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 7;
    preset = 1;
    //in.addListener(visuals[select]);
    ///visuals[select].setup();
    reLoadSketch();
  }
}
void oscSketch16(float iA) {
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
void oscSketch17(float iA) { 
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

void oscSketch18(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 6;
    preset = 2;
    //in.addListener(visuals[select]);
    ///visuals[select].setup();

    //colorMode(RGB);
    reLoadSketch();
  }
}
void oscSketch19(float iA) {
  if (iA == 1) {
    transitionReset();
    //in.removeListener(visuals[select]);
    select = 6;
    preset = 1;
    //in.addListener(visuals[select]);
    //visuals[select].setup();
    reLoadSketch();
  }
}

void oscSketch20(float iA) {
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

void oscSketchB1(float iA) {
  buttonOut = new OscMessage("/luminous/sketch1");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB2(float iA) {
  buttonOut = new OscMessage("/luminous/sketch2");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB3(float iA) {
  buttonOut = new OscMessage("/luminous/sketch3");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB4(float iA) {
  buttonOut = new OscMessage("/luminous/sketch4");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB5(float iA) {
  buttonOut = new OscMessage("/luminous/sketch5");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB6(float iA) {
  buttonOut = new OscMessage("/luminous/sketch6");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB7(float iA) {
  buttonOut = new OscMessage("/luminous/sketch7");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB8(float iA) {
  buttonOut = new OscMessage("/luminous/sketch8");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB9(float iA) {
  buttonOut = new OscMessage("/luminous/sketch9");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB10(float iA) {
  buttonOut = new OscMessage("/luminous/sketch10");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB11(float iA) {
  buttonOut = new OscMessage("/luminous/sketch11");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB12(float iA) {
  buttonOut = new OscMessage("/luminous/sketch12");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB13(float iA) {
  buttonOut = new OscMessage("/luminous/sketch3");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB14(float iA) {
  buttonOut = new OscMessage("/luminous/sketch4");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB15(float iA) {
  buttonOut = new OscMessage("/luminous/sketch5");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB16(float iA) {
  buttonOut = new OscMessage("/luminous/sketch6");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB17(float iA) {
  buttonOut = new OscMessage("/luminous/sketch7");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB18(float iA) {
  buttonOut = new OscMessage("/luminous/sketch8");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB19(float iA) {
  buttonOut = new OscMessage("/luminous/sketch9");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscSketchB20(float iA) {
  buttonOut = new OscMessage("/luminous/sketch10");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}

void oscEffectB1(float iA) {
  buttonOut = new OscMessage("/luminous/effect1");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscEffectB2(float iA) {
  buttonOut = new OscMessage("/luminous/effect2");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscEffectB3(float iA) {
  buttonOut = new OscMessage("/luminous/effect3");
  buttonOut.add(iA);
  oscP5.send(buttonOut, stripApp);
}
void oscEffectB2B(float iA) {
  if (iA == 1.0) {
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

void oscOnClick(float iA, float iB) {
  /// set the global X and Y to whichever interface is passing it
  theX = theOSCX = iA;
  theY = theOSCY = iB;
  visuals[select].onClick();

}

void oscOnClick2(float iA, float iB) {
  faderOut = new OscMessage("/luminous/xy");
  faderOut.add(iA);
  faderOut.add(iB);
  oscP5.send(faderOut, stripApp);
}

void oscEffect1(float iA) {
  if ((millis() - resetPixelsWait) > 100) {
    resetPixelsWait = millis();
    noiseParticles.clearParticles(iA);
  }
}

void oscEffect2(float iA) {
  if (iA == 1)
    toggle = true;
  if (iA == 0)
    toggle = false;
  //  heatmap.heattoggle(iA);
  turing.directiontoggle(iA);
  // simplegradient.directiontoggle(iA);
}


void oscEffect3(float iA) {
  //turing.directiontoggle(iA);
}
void oscEffect4(float iA) {
  randomTouchState = iA;
}
void oscEffect5(float iA) {
  audioResponseState = iA;
}

void oscSave(float iA) {
  if (iA == 1) {
    savePresets();
    buttonOut = new OscMessage("/luminous/save");
    buttonOut.add(iA);
    oscP5.send(buttonOut, stripApp);
  }
}

void oscFader1(float faderIn) {
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

void oscFaderSet() {
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
      faderOutFloat = 1.0;
    } else {
      faderOutFloat = 0.0;
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
      faderOutFloat = 1.0;
    } else {
      faderOutFloat = 0.0;
    }
    faderOut.add(faderOutFloat);
    oscP5.send(faderOut, myRemoteLocation);
  }
}
/* incoming osc message are forwarded to the oscEvent method. */
void oscEvent(OscMessage theOscMessage) {
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


void transitionReset  () {
   // colorMode(HSB, 255, 255, 255, 255);
  transition = get();
  transitionOpacity = 255;
  
}

void reLoadSketch(){
  
   //visuals[select].setInitVals();
  visuals[select].loadPresets();
   /// visuals[select].switchColorMode();
   visuals[select].setupSketch();
   
}

void draw() {    
  
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

void transitionDraw() {
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





void stop()
{
  // always close Minim audio classes when you are done with them
  //in.close();
  minim.stop();
  artnet.stop();
  super.stop();
}

//////////////////////////////////////////////////
//////////////////////////////////////////////////
///////// MOUSE AND KEYBOARD ///////////////////
////////////////////////////////////////////////
//////////////////////////////////////////////////


void keyPressed() {
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
    if (select == 4)
    {
      turing.keyPressed();
    }
  }
}


void mouseClicked() {
  if(select == 5 || select == 3){
    theOSCX = map(mouseX, 0, canvasW, 0, 1);
    theOSCY = map(mouseY, 0, canvasH, 0, 1);

  } else {
      theX = mouseX;
      theY = mouseY;
  }
  visuals[select].onClick(); 
}

void mouseDragged() {

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
