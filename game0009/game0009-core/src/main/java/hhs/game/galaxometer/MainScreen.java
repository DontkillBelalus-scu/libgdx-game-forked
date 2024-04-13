package hhs.game.galaxometer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.app.UtilScreen2D;

public class MainScreen extends ScreenCore2D {

  int lev = 24;
  public float delta = 0;
  public static float G = 6.754f;
  
  @Override
  public void setup() {
    var p1=new Planet(this);
    center.add(p1);
    
    var p2=new Planet(this);
    p2.position.set(100,100);
    center.add(p2);
    
  }

  @Override
  public void update() {
    delta = Gdx.graphics.getDeltaTime();
  }

  @Override
  public void display() {
    background(Color.BLACK);
  }

  @Override
  public void displayWithCam() {}

  @Override
  public void frameResized() {}
}
