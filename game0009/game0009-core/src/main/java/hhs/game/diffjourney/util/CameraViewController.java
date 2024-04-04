package hhs.game.diffjourney.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import hhs.gdx.hsgame.tools.CameraControlGesturer;

public class CameraViewController extends CameraControlGesturer{
  public CameraViewController(OrthographicCamera cam) {
    super(cam);
  }
  @Override
  public boolean pan(float x,float y,float dx,float dy) {
    cam.position.sub(cam.zoom*dx,-cam.zoom*dy,0);
    return false;
  }

}
