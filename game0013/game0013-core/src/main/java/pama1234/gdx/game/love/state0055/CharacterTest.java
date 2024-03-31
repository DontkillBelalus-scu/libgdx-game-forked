package pama1234.gdx.game.love.state0055;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.game.love.state0055.State0055Util.StateEntity0055;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.entity.EntityNeo;
import pama1234.math.MathPool;
import pama1234.math.MathTools;
import pama1234.math.vec.Vec3f;
import space.earlygrey.shapedrawer.CapType;

public class CharacterTest extends StateEntity0055{

  public CharacterTest(Screen0055 p) {
    super(p);
  }

  @Override
  public void from(StateEntity0055 in) {
    //    p.fill(0);
    p.capType=CapType.ROUND;

    p.centerCamAddAll(new Stickman01(p));
    //    p.centerNeoAddAll(new Stickman(p));

    p.centerCam.list.remove(p);

    p.depth(true);
  }

  @Override
  public void displayCam() {
    //        p.line(0,0,0,10);
    //      new Exception().printStackTrace();
  }

  public static class Stickman01 extends Entity<Screen0055>{

    Vec3f a=new Vec3f(100,300,500),b=new Vec3f(200,400,600);

    Vec3f c=new Vec3f();

    public Stickman01(Screen0055 p) {
      super(p);

      p.fill(0,127);

      c.set(a);
      c.add(b);
      c.scale(0.5f);
    }

    @Override
    public void display() {
      //      Thread.yield();
      //      System.out.println(p.centerNeo.list.size());
      //      System.out.println(p.centerCam.list.size());
      //            p.centerCam.list.forEach(e->System.out.println(e));

      //      new Exception().printStackTrace();
      //      p.rendererEnd();

      //      p.fill(0);
      //      p.capType=CapType.ROUND;
      //      p.doStroke();
      //      p.strokeWeight(1);
      //      p.pushMatrix();
      //      p.clearMatrix();

      float l=3;

      p.noStroke();
      p.circle(a.x,a.y,a.z,l,0);
      p.circle(b.x,b.y,b.z,l,0);
      p.circle(c.x,c.y,c.z,l,0);

      var cam=p.usedCamera.position;
      var foot=MathTools.perpendicularFoot(a.x,a.y,a.z,b.x,b.y,b.z,cam.x,cam.y,cam.z);
      float ox=foot.x;
      float oy=foot.y;
      float oz=foot.z;

      MathPool.vec3fPool.free(foot);
      p.circle(ox,oy,oz,l,0);

      p.doStroke();
      p.line(a.x,a.y,a.z,b.x,b.y,b.z);
      //      p.line(0,0,0,100);

      //      p.popMatrix();
    }
  }

  public static class Stickman extends EntityNeo<Screen0055>{

    public Stickman(Screen0055 p) {
      super(p);
    }

    @Override
    public void displayCam() {
      //      Thread.yield();
      //      System.out.println(p.centerNeo.list.size());
      //      System.out.println(p.centerCam.list.size());
      //            p.centerCam.list.forEach(e->System.out.println(e));

      //      new Exception().printStackTrace();
      //      p.rendererEnd();

      //      p.fill(0);
      //      p.capType=CapType.ROUND;
      //      p.doStroke();
      //      p.strokeWeight(1);
      //      p.pushMatrix();
      //      p.clearMatrix();

      p.line(0,0,0,100);

      //      p.popMatrix();
    }
  }
}
