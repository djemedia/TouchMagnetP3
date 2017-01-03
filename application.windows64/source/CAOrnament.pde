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



class stainedglassRenderer extends AudioRenderer {


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
    ca.drawBoxAt(0, height/2, 5, 1);

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

  void doPresets() {
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

  void keyPressed() {
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