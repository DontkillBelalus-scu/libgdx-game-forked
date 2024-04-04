package hhs.app.hsgametest.physical;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ball extends PEntity{
  public Ball() {

  }
  @Override
  public void update(float delta) {
    if(pos.y<=0) velocity.set(0,0);
    super.update(delta);
  }

  @Override
  public void render(SpriteBatch batch) {
    circle(center.x,center.y,size.x);
    renderVectorAt(center.x,center.y,velocity);
  }
}
