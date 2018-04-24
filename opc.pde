Img2Opc i2o;
PImage opcImage;


void setupOPC() {
  opcImage = get();
  //int displayWidth = 25;
  //int displayHeight = 25;
  i2o = new Img2Opc(this, "192.168.7.2", 7890, displayWidth, displayHeight);
  i2o.setSourceSize(canvasW, canvasH);
}

void drawOPC() { 
  i2o.sendImg(opcImage);
}