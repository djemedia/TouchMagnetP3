
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
  colorMode(HSB, 255, 255, 255, 255);
 
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
    color c = color(artnetimg.pixels[i]);
         
   artnetimg.pixels[i] = color(hue(c), saturation(c), brightness(c) - dimmer4 - dimmer1);
   //artnetimg.pixels[i] = color(hue(c), saturation(c), brightness(c) - dimmer1);
  
   artnetimg.updatePixels();   
     
  }
 
  artnet.send(artnetimg.pixels, "192.168.1.111");
 
}