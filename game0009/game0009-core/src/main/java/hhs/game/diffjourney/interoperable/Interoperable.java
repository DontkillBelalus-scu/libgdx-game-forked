package hhs.game.diffjourney.interoperable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import hhs.game.diffjourney.ui.PixelFontButton;
import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.entities.EntityLayers;
import hhs.gdx.hsgame.tools.EntityTool;
import hhs.gdx.hsgame.tools.Resource;

public abstract class Interoperable extends BasicEntity implements EntityLayers.Stackable{

  EntityTool.VectorProvider receivePos;
  final static Vector2 tmp=new Vector2();
  public static final float triggerDistance=100;
  boolean in=false;
  public final Button interactive=new Button();

  public Interoperable(EntityTool.VectorProvider receivePos) {
    this.receivePos=receivePos;
  }

  public void addListener(EventListener el) {
    interactive.addListener(el);
  }

  @Override
  public void dispose() {}

  public static float distance2(Vector2 a,Vector2 b) {
    return tmp.set(a).sub(b).len2();
  }
  public float cdis2(BasicEntity a) {
    return distance2(EntityTool.getCenter(a),receivePos.provide());
  }

  @Override
  public void update(float delta) {
    if(distance2(EntityTool.getCenter(this),receivePos.provide())<triggerDistance*triggerDistance) {
      if(!in&&interactive.getParent()==null) {
        if(interactive.cur==null||cdis2(interactive.cur)>cdis2(this)) {
          if(interactive.cur!=null) interactive.cur.in=false;
          interactive.setCur(this);
          screen.stage.addActor(interactive);
          interactive.addAction(Actions.fadeIn(0.5f));
        }
      }
      in=true;
    }else {
      if(in) {
        interactive.cur=null;
        interactive.remove();
      }
      in=false;
    }
  }

  @Override
  public void render(SpriteBatch batch) {}

  public static class Button extends PixelFontButton{
    Interoperable cur;
    public Button() {
      super("交互");
      setScale(6);
      setPosition(Resource.width-Resource.u*4,Resource.height/4);
    }
    public Interoperable getCur() {
      return this.cur;
    }
    public void setCur(Interoperable cur) {
      this.cur=cur;
    }
  }
}
