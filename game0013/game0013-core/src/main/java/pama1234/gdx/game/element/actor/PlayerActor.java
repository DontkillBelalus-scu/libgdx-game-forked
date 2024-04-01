package pama1234.gdx.game.element.actor;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.game.element.OrientedEntity3D;
import pama1234.gdx.game.util.ui.ColorUtil;
import pama1234.math.physics.MassPoint3D;

import static pama1234.math.UtilMath.abs;

public class PlayerActor extends OrientedEntity3D{
  public MassPoint3D point;
  public String text="0.0";
  public PlayerActor(Screen0055 p) {
    super(p);
    point=new MassPoint3D(0,0,0);
  }
  @Override
  public void update() {
    super.update();

    pose.pos.set(point.x(),point.y(),point.z());
    rotateToCam();

    p.noFill();
  }

  @Override
  public void displayPose() {
    p.textColor(ColorUtil.interfase);
    //    p.text(">.<");
    //    p.text("0.<");
    float textWidth=p.textWidth(text);
    p.text("O.O",-textWidth/2f,-p.textSize()/2f-2);
//    p.circle(0,0,textWidth/2f+3);
    p.arc(0,0,textWidth/2f+3,p.frameCount*6f,abs(p.frameCount*2f-360)%360);
  }

}
