ArtNetClient artnet;
byte[] dmxData = new byte[512];
//ArtnetP5 artnet;
PImage artnetimg;
float artnetRed, artnetGreen, artnetBlue;

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

 String[] ips = {
    "192.168.1.8",
    "192.168.1.25"
  };
  
public void setupArtnet() {
  
  artnet = new ArtNetClient();
  artnet.start();
  artnetimg = new PImage(170, ips.length, PApplet.RGB);
  //colorMode(RGB, 255, 255, 255, 255);
 
     //mapSection(100, 20, 0,12);                                      here??
}

void mapSection(int index, int sketchX, int sketchY, int startDMX, int endDMX)
{
   
   for(int i = startDMX; i < endDMX; i++){
     
     artnetimg.set(i % artnetimg.width, index/artnetimg.height, get(sketchX + (i % width), sketchY + (i / width)));
     sketchX+= 2;
  
  }  
}

void drawArtnet()  {
  
  //int sketchX = 0;
  //int sketchY = 0;
  //int startDMX = 0;
  //int endDMX = 0;
  
  //address the fixtures
  //mapSection(sketchX, sketchY, startDMX, endDMX);
  mapSection(0,10,10, 0,169);
  mapSection(1,100, 200,0,169);
  /*mapSection(241, 240, 2,3);
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
 
 */
  
  

  

  //add a dimmer
  artnetimg.loadPixels();
  //colorMode(RGB, 255, 255, 255);
  for(int j = 0; j < ips.length; j++) {
      for(int i = 0; i < 170; i++){    
        color artnetColor = color(artnetimg.pixels[i]);
       artnetimg.pixels[i] = color(hue(artnetColor), saturation(artnetColor), brightness(artnetColor) - dimmer1);
             //color pixColor = get(i,i);
        artnetRed = red(artnetColor);
        artnetGreen = green(artnetColor);
        artnetBlue = blue(artnetColor);
        dmxData[i*3+0] = (byte) artnetRed;
        dmxData[i*3+1] = (byte) artnetGreen;
        dmxData[i*3+2] = (byte) artnetBlue;
       
          
           //print("R: " + (int)red(artnetColor) + " Green: " + (int)green(artnetColor) + " Blue: " + (int)blue(artnetColor), width / 2, height / 2);
          //}
        //  artnetimg.updatePixels();   
          
        
      }
   //artnet.broadcastDmx(0, 0, artnetimg.pixels[i]);
      //artnet.broadcastDmx(0,2, dmxData);
      
      
        artnet.unicastDmx(ips[j], 0, 0, dmxData);
    }
     //artnet.unicastDmx("192.168.1.247", 0, 0, dmxData);
     // artnet.unicastDmx("192.168.1.65", 0, 0, dmxData);
      
     // byte[] motionData = artnet.readDmxData(0, 1);
      
  //int c = color(data[0] & 0xFF, data[1] & 0xFF, data[2] & 0xFF);
  //artnet.universe(0)
   // artnet.broadcastDmx(0, 0, artnetimg.pixels[i]);
  //artnet.send(artnetimg.pixels, "192.168.1.159");
}  

 