//import com.github.dlopuch.apa102_java_rpi.Apa102Output;
//import com.pi4j.io.spi.SpiChannel;
//import com.pi4j.io.spi.SpiDevice;

//for python bridge
//Client apaClient;

float apaRed, apaGreen, apaBlue;
int num_LEDS = 50;
byte[] apaColor = new byte[num_LEDS*3];



void setupApa() {
  
  
//python socket  
//  apaClient = new Client(this, "127.0.0.1", 5211);
//////////////////////////////////////////////////  

  //////////////////////////////////////////////eventual java port
  //public static final int NUM_LEDS = 32;
  
  //static public void main(String args[]) throws Exception {

Apa102Output.initSpi();
// Could also init with non-defaults using #initSpi(SpiChannel spiChannel, int spiSpeed, SpiMode spiMode)
// Default speed is 7.8 Mhz

Apa102Output strip = new Apa102Output(num_LEDS);

//byte[] ledRGBs = new byte[ NUM_LEDS * 3 ];

/*
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
     
//}
    
     example program
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
    

void drawApa(){
  for (int i = 0; i<= num_LEDS; i++){
  color pixColor = get(i,i);
  apaRed = red(pixColor);
  apaGreen = green(pixColor);
  apaBlue = blue(pixColor);
  apaColor[i-100/10*4-3+0] = (byte) apaRed;
  apaColor[i-100/10*4-3+1] = (byte) apaGreen;
  apaColor[i-100/10*4-3+2] = (byte) apaBlue;
  //apaColor[i-100/10*4-3+2] = (byte) 0;
  //} 
  strip.writeStrip(apaColor)
}
/*
  for (int i=0; i<leds.length; i += 3) {
        RainbowUtils.fillRgb(leds, i, pixelPhi);
        pixelPhi += pixelPhiIncrement;
      }
  
while (true) {
  // <fill in your ledRGBs buffer with your pattern... eg examples/RainbowStrip.java>

  strip.writeStrip(ledRGBs);

}
  
  for (int y = 1; y < dmxAddr+1; y+=3) {     
    for (int x = 1; x < dmxUniv+1; x++) {
      thisDmxPos = dmxPos[xyPixels(x, y, dmxUniv)];
      //thisDmxPos = ledPos[xyPixels(x, y, dmxUniv)];
      color c = pixels[thisDmxPos];          
      dmxOutput.set(y, (int)red(c));
      dmxOutput.set(y+1, (int)green(c));
      dmxOutput.set(y+2, (int)blue(c));
    }
  }
  
  Pixel p = new Pixel((byte)red(c), (byte)green(c), (byte)blue(c));
        if (y < strips.size()) {
        //if (y < strips.size() && y >= 16) {
        //if (y == 6 || y == 7) {
          //if (y < strips1.size() && y!=34) {
          //if (y < strips1.size() && y==65) {
          strips.get(y).setPixel(p, x);
        }
/*
      color c = pixels[1];          
      Pixel p = new Pixel((byte)red(c), (byte)green(c), (byte)blue(c));
      dmxOutput.set(0+1, (int)red(c));
      dmxOutput.set(0+2, (int)green(c));
      dmxOutput.set(0+3, (int)blue(c));
*/
  
  
  //python client
  //apaClient.write(apaColor);
  
  //example
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
      
      */
 // output.writeStrip(leds);
}