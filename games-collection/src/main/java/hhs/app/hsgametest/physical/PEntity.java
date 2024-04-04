package hhs.app.hsgametest.physical;

import com.badlogic.gdx.math.Vector2;
import hhs.gdx.hsgame.entities.BasicEntity;

public abstract class PEntity extends BasicEntity{

  public Vector2 velocity=new Vector2();
  public Vector2 center=new Vector2();

  @Override
  public void dispose() {}

  Vector2 tmp=new Vector2();
  @Override
  public void update(float delta) {
    super.update(delta);
    pos.add(tmp.set(velocity).scl(delta));
    center.set(pos).add(tmp.set(size).scl(0.5f));
  }
  public void setCenterPosition(float x,float y) {
    center.set(x,y);
    pos.set(size).scl(-0.5f).add(center);
  }

  public void circle(float x,float y,float radiu) {
    World.shapeRenderer.circle(x,y,radiu);
  }
  public void rect(float x,float y,float width,float height) {
    World.shapeRenderer.rect(x,y,width,height);
  }
  public void renderVectorAt(float x,float y,Vector2 v) {
    World.shapeRenderer.line(x,y,x+v.x,y+v.y);
  }

}
