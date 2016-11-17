void loadMasterPresets() {
  presets = xml.getChildren("master");
  preset = 0;
  println("Loading dimmers.");
  dimmer1 = presets[preset].getChild("dimmer1").getIntContent();
  dimmer2 = presets[preset].getChild("dimmer2").getIntContent();
  dimmer3 = presets[preset].getChild("dimmer3").getIntContent();
  dimmer4 = presets[preset].getChild("dimmer4").getIntContent();
  dimmer5 = presets[preset].getChild("dimmer5").getIntContent();
  dimmer6 = presets[preset].getChild("dimmer6").getIntContent();
  dimmer7 = presets[preset].getChild("dimmer7").getIntContent();
  dimmer8 = presets[preset].getChild("dimmer8").getIntContent();
  dimmer9 = presets[preset].getChild("dimmer9").getIntContent();
  dimmer10 = presets[preset].getChild("dimmer10").getIntContent();
  println("Loading faders.");
  setcolorMode = presets[preset].getChild("vFader1").getIntContent();
  vFader2 = presets[preset].getChild("vFader2").getIntContent();
  vFader3 = presets[preset].getChild("vFader3").getIntContent();
  vFader4 = presets[preset].getChild("vFader4").getIntContent();
  vFader5 = presets[preset].getChild("vFader5").getIntContent();
  vFader6 = presets[preset].getChild("vFader6").getIntContent();
  vFader7 = presets[preset].getChild("vFader7").getIntContent();
  vFader8 = presets[preset].getChild("vFader8").getIntContent();
}

boolean getSketchPresets(String tagName, boolean customLoadMsg) {
  presets = xml.getChildren(tagName);
  println();
  println("There are " + presets.length + " presets for " + tagName);
  if (presets.length > preset) {
    //    positionElement = presets[0].getChild("vFader1");
    //    vFader1 = positionElement.getIntContent();
    setcolorMode = presets[preset].getChild("vFader1").getIntContent();
    vFader2 = presets[preset].getChild("vFader2").getIntContent();
    vFader3 = presets[preset].getChild("vFader3").getIntContent();
    vFader4 = presets[preset].getChild("vFader4").getIntContent();
    vFader5 = presets[preset].getChild("vFader5").getIntContent();
    vFader6 = presets[preset].getChild("vFader6").getIntContent();
    vFader7 = presets[preset].getChild("vFader7").getIntContent();
    vFader8 = presets[preset].getChild("vFader8").getIntContent();
    if (customLoadMsg) {
      println("Faders Loaded. " + tagName + " Preset #" + preset + " Faders: " + setcolorMode + " " + vFader2 + " " + vFader3 + " " + vFader4 + " " + vFader5 + " " + vFader6 + " " + vFader7 + " " + vFader8);
    }
    return true;
  } else {
    println(tagName + " Preset #" + preset + " is not in presets.xml");
    return false;
  }
}

void savePresets() {
  setSketchPresets();
  saveXML(xml, "data/presets.xml");
  println("Saved");
  xml = loadXML("data/presets.xml");
}

void setSketchPresets() {

  if (presets.length > preset) {
    //    positionElement = presets[0].getChild("vFader1");
    //    vFader1 = positionElement.getIntContent();
    presets[preset].getChild("vFader1").setContent(Integer.toString(setcolorMode));
    presets[preset].getChild("vFader2").setContent(Integer.toString(vFader2));
    presets[preset].getChild("vFader3").setContent(Integer.toString(vFader3));
    presets[preset].getChild("vFader4").setContent(Integer.toString(vFader4));
    presets[preset].getChild("vFader5").setContent(Integer.toString(vFader5));
    presets[preset].getChild("vFader6").setContent(Integer.toString(vFader6));
    presets[preset].getChild("vFader7").setContent(Integer.toString(vFader7));
    presets[preset].getChild("vFader8").setContent(Integer.toString(vFader8));
    //additional sketch properties need to be written somehow.
    println("Preset Saved. Sketch# " + select + " Preset #" + preset + " Faders: " + setcolorMode + " " + vFader2 + " " + vFader3 + " " + vFader4 + " " + vFader5 + " " + vFader6 + " " + vFader7 + " " + vFader8);
  } else {
    println("Sketch#" + select + " Preset #" + preset + " is not in presets.xml or other inconsistency");
  }
}

