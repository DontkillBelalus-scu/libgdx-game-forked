package hhs.gdx.hsgame.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.input.GestureDetector;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.tools.CameraControlGesturer;
import hhs.gdx.hsgame.tools.Resource;

public class LightTest extends BasicScreen{
  LightCenter lights=new LightCenter();
  public LightTest() {
    setClearColor(Color.BLACK);
    final PointLight pl=new PointLight();
    input.addProcessor(
      new GestureDetector(
        new CameraControlGesturer(camera) {
          @Override
          public boolean pan(float x,float y,float dx,float dy) {
            cam.position.sub(cam.zoom*dx,-cam.zoom*dy,0);
            return false;
          }
        }));
    camera.combined.setToOrtho2D(0,0,Resource.width,Resource.height);
    lights=new LightCenter();
    addEntity(lights);
    lights.add(pl);
    pl.size.set(1000,1000);
  }
}
