package pama1234.gdx.game.app.app0002;

import pama1234.gdx.game.love.state0055.State0055Util;
import pama1234.gdx.game.love.state0055.State0055Util.StateCenter0055;
import pama1234.gdx.game.love.state0055.State0055Util.StateEntity0055;
import pama1234.gdx.game.util.ui.ColorUtil;
import pama1234.gdx.util.app.ScreenCoreState3D;
import pama1234.math.MathTools;
import pama1234.math.UtilMath;

public class Screen0055 extends ScreenCoreState3D<StateCenter0055,StateEntity0055>{

  @Override
  public void setup() {
    stateCenter=new StateCenter0055(this);
    State0055Util.loadState0055(this,stateCenter);

    state(stateCenter.firstRun);

    backgroundColor=ColorUtil.background;

    cam3d.point.des.set(0,0,-320);
  }

//  @Override
//  public void line(float x1,float y1,float z1,float x2,float y2,float z2) {
//    super.line(x1,y1,z1,x2,y2,z2);
//    float dist1=UtilMath.dist(x1,y1,z1,x2,y2,z2);
//
//    float midX=(x1+x2)/2f;
//    float midY=(y1+y2)/2f;
//    float midZ=(z1+z2)/2f;
//
//    float dx=x2-x1;
//    float dy=y2-y1;
//    float dz=z2-z1;
//
//    var cam=usedCamera.position;
//
//    var foot=MathTools.perpendicularFoot(x1,y1,z1,x2,y2,z2,cam.x,cam.y,cam.z);
//    float ox=foot.x;
//    float oy=foot.y;
//    float oz=foot.z;
//
//    boolean leftSide=MathTools.isLeft(x1,y1,z1,x2,y2,z2,ox,oy,oz);
//
//    fill(255,0,0,127);
//    circle(x1,y1,z1,3,0);
//    fill(0,0,255,127);
//    circle(x2,y2,z2,3,0);
//    if(leftSide) fill(0,255,0,127);
//    else fill(255,255,0,127);
//    circle(ox,oy,oz,3,0);
//  }

  public void debugAxis() {
    line(0,0,0,100,0,0);
    line(0,0,0,0,100,0);
    line(0,0,0,0,0,100);
  }

  @Override
  public void update() {}

  @Override
  public void display() {}

  @Override
  public void displayWithCam() {}

  @Override
  public void frameResized() {}

  public int randomInt(int a) {
    return (int)random(a);
  }
}