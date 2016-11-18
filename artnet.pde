
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


//universes
int artnetPixels(int x, int y, int yScale) {
  return(x+(y*yScale));
}

int artnetxPixels(int pxN, int yScale) {
  return(pxN % yScale);
}

int artnetyPixels(int pxN, int yScale) {
  return(pxN / yScale);
}
*/
public void setupArtnet() {
  
  artnet = new ArtnetP5();
  artnetimg = new PImage(170, 1, PApplet.RGB);
 
     //mapSection(100, 20, 0,12);                                      here??
}

void mapSection(int sketchX, int sketchY, int startDMX, int endDMX)
{
   
   for(int i = startDMX; i < endDMX; i++){
     
     artnetimg.set(i % artnetimg.width, i / artnetimg.width, get(sketchX + (i % width), sketchY + (i / width)));
     sketchX-= 4;
  
  }  
}

void drawArtnet()  {
  
  int sketchX = 0;
  int sketchY = 0;
  int startDMX = 0;
  int endDMX = 0;
  
  //address the fixtures
  //mapSection(sketchX, sketchY, startDMX, endDMX);
  mapSection(480, 240, 0,12);
  mapSection(540, 240, 12,24);
  mapSection(600, 240, 24,36);
  mapSection(660, 240, 36,48);
  mapSection(720, 240, 48,60);
  mapSection(780, 240, 60,72);
  mapSection(840, 240, 72,84);
  mapSection(900, 240, 84,96);
  mapSection(960, 240, 96,108);
  mapSection(1020, 240, 108,120);
  mapSection(1080, 240, 120,132);
  mapSection(960, 240, 132,144);
  mapSection(1020, 240, 144, 156);
  mapSection(1260, 240, 156, 168);
 
 
  
  

  

  //add a dimmer
  artnetimg.loadPixels();
  colorMode(HSB, 255);
  
  for(int i = 0; i < 170; i++){    
    color c = color(artnetimg.pixels[i]);
         
   artnetimg.pixels[i] = color(hue(c), saturation(c), brightness(c) - dimmer4);
   artnetimg.pixels[i] = color(hue(c), saturation(c), brightness(c) - dimmer1);
  
   artnetimg.updatePixels();   
     
  }
 
  artnet.send(artnetimg.pixels, "192.168.1.111");
 
}