package hhs.app.hsgametest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import hhs.gdx.hsgame.tools.FontManager;
import hhs.gdx.hsgame.tools.Resource;

public class PhysicalEnter extends Game{

  Resource res;

  /* This method is being called when application is created */
  @Override
  public void create() {
    res=new Resource(this);
    res.font=new FontManager(new FreeTypeFontGenerator(Gdx.files.internal("auto.ttf")));
    res.init();
    setScreen(new PhysicalSimulation());
  }

  /* This method is being called whenever the application is destroyed */
  @Override
  public void dispose() {
    res.dispose();
  }
}