package com.test.galaxometer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;

public class Galaxometer extends Game{

  public static Color[] colorList= {Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.BLUE,Color.PURPLE};
  public static float G=6.754f;
  @Override
  public void create() {
    setScreen(new MainScreen());
  }
}
