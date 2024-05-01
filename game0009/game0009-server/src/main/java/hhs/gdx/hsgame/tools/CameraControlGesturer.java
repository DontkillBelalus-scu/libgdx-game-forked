package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;

public class CameraControlGesturer extends GestureDetector.GestureAdapter{
  public OrthographicCamera cam;
  float zoom=0;
  float maxScale=6,minScale=0.05f;
  public CameraControlGesturer(OrthographicCamera cam) {
    this.cam=cam;
  }
  @Override
  public boolean zoom(float initialDistance,float distance) {
    cam.zoom=zoom*initialDistance/distance;
    cam.zoom=cam.zoom<minScale?minScale:cam.zoom;
    cam.zoom=cam.zoom>maxScale?maxScale:cam.zoom;
    return false;
  }
  @Override
  public boolean touchDown(float arg0,float arg1,int arg2,int arg3) {
    zoom=cam.zoom;
    return true;
  }
  // @Override
  // public boolean zoom(float arg0, float arg1) {
  // zoom = arg0 - arg1;
  // cam.zoom += zoom * cam.zoom / 25000;
  // return super.zoom(arg0, arg1);
  // }
}
