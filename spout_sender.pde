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