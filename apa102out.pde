//import com.github.dlopuch.apa102_java_rpi.Apa102Output;
//import com.pi4j.io.spi.SpiChannel;
//import com.pi4j.io.spi.SpiDevice;
//Client apaClient;
float apaRed, apaGreen, apaBlue;
int num_LEDS = 70;
byte[] apaColor;
Apa102Output strip;
//PImage APAimg;

void setupApa() {
  
 // APAimg = new PImage(num_LEDS, 1, PApplet.RGB);
  try {
          Apa102Output.initSpi();
        } catch (IOException e) {
          throw new RuntimeException("failure to init", e);
        }
    
    
    strip = new Apa102Output(num_LEDS);
    apaColor = new byte[num_LEDS*3];
 /*
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
      */
//}
    
    /* example program
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


public void drawApa() {
  int sketchX = 0;
  int sketchY = 0;
  
  for (int i = 0; i<= num_LEDS-1; i++){
   //APAimg.set(i % APAimg.width, i / APAimg.width, get(sketchX + (i % width), sketchY + (i / width)));
    //sketchX++;
  color pixColor = get(i,i);
  apaRed = red(pixColor);
  apaGreen = green(pixColor);
  apaBlue = blue(pixColor);
  apaColor[i*3+0] = (byte) apaRed;
  apaColor[i*3+1] = (byte) apaGreen;
  apaColor[i*3+2] = (byte) apaBlue;
  //apaColor[i-100/10*4-3+2] = (byte) 0;
  //} 
   try {
          strip.writeStrip(apaColor);
          //strip.writeStrip(APAimg.pixels); //needs to be converted to (int[]) to work
       
      } catch (IOException e) {
          throw new RuntimeException("ERROR writing strip", e);
        }
  }
}
