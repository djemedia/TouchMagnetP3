ArtNetClient artnet;
float artnetRed, artnetGreen, artnetBlue;

class Tube {
  public Tube(int _x, int _y, String _ip) {
    x = _x;
    y = _y;
    ip = _ip;
  }
  public String ip;
  public int x;
  public int y;
  public int w = 1;
  public int h = 170;
}

Tube[] tubes = {
  new Tube(1, 1, "192.168.1.25"),
  new Tube(1079, 1, "192.168.1.8")
};

public void setupArtnet() {
  artnet = new ArtNetClient(null);
  artnet.start();
}

byte[] mapSection(int sketchX, int sketchY, int width, int height)
{
   byte[] dmxData = new byte[512];
   for(int i = sketchX; i < sketchX + width; i++) {
    for(int j = sketchY; j < sketchY + height; j++) {
      setBytes(pixels[j*1280+i], (j-sketchY)*width+(i-sketchX), dmxData);
    }
  }
  return dmxData;
}

void setBytes(color pixel, int idx, byte[] dmxData ) {
    color artnetColor = color(pixel);
    artnetRed = red(artnetColor);
    artnetGreen = green(artnetColor);
    artnetBlue = blue(artnetColor);
    dmxData[idx*3+0] = (byte) artnetRed;
    dmxData[idx*3+1] = (byte) artnetGreen;
    dmxData[idx*3+2] = (byte) artnetBlue;
}

void drawArtnet()  {
  loadPixels();
  
  for(int i = 0; i < tubes.length; i++) {
    Tube tube = tubes[i];
    artnet.unicastDmx(tube.ip, 0, 0,
      mapSection(tube.x, tube.y, tube.w, tube.h)
    );
  }
}
