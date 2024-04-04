package hhs.app.hsgametest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.Array;

public class CameraControl extends GestureDetector{
  Camera camera;

  public CameraControl(Camera cam,Array<ModelInstance> el) {
    super(new Control(cam,el));
    this.camera=cam;
  }
  @Override
  public boolean touchDragged(int arg0,int arg1,int arg2) {
    float velocityX=Gdx.input.getDeltaX()*2;
    float velocityY=Gdx.input.getDeltaY()*2;
    camera.position.z-=velocityX*Gdx.graphics.getDeltaTime()*Math.abs(camera.position.x)/50;
    camera.position.y+=velocityY*Gdx.graphics.getDeltaTime()*Math.abs(camera.position.x)/50;
    return super.touchDragged(arg0,arg1,arg2);
  }

  public static class Control extends GestureDetector.GestureAdapter{
    Camera cam;
    Array<ModelInstance> el;
    public Control(Camera cam,Array<ModelInstance> el) {
      this.cam=cam;
      this.el=el;
    }
    int lsp=10;
    public boolean fling(float velocityX,float velocityY,int button) {

      return false;
    }
    float zoom;
    @Override
    public boolean zoom(float arg0,float arg1) {
      zoom=(arg0-arg1)*Math.abs(cam.position.x)/50;
      cam.position.x-=zoom*Gdx.graphics.getDeltaTime()/25f;
      return super.zoom(arg0,arg1);
    }
    //    @Override
    //    public boolean tap(float arg0, float arg1, int arg2, int arg3) {
    //      Ray ray = cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());
    //      BoundingBox box=new BoundingBox();
    //      for(var mi:el){
    //        if(Intersector.intersectRayBoundsFast(ray, mi.calculateBoundingBox(box))){
    //          
    //        }
    //      }
    //      return super.tap(arg0, arg1, arg2, arg3);
    //    }
    //    

  }
}
