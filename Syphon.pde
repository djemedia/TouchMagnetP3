
void setupSyphon() {
  ;;size(canvasW, canvasH, P3D);
  //frameRate(30);
  
  //background(0);
  
  client = new SyphonClient(this, "Arena", "Composition");
  
}




void drawSyphon() {
  /*if (client.available()) {
    background(0, 255);
    canvas = client.getGraphics(canvas);
    image(canvas, 0, 0, width, height);
  }  
  */
}