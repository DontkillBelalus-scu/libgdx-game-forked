package hhs.app.hsgametest.physical;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import hhs.gdx.hsgame.entities.EntityCenter;

public class World extends EntityCenter<PEntity>{
  public static final ShapeRenderer shapeRenderer=new ShapeRenderer();
  public Vector2 g=new Vector2(0,-9.81f);
  Vector2 tmp=new Vector2();
  @Override
  public void update(float delta) {
    super.update(delta);
    for(PEntity pe:sons) {
      pe.velocity.add(tmp.set(g).scl(delta));
    }
  }
  @Override
  public void render(SpriteBatch batch) {
    batch.end();
    shapeRenderer.setProjectionMatrix(cam.combined);
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    super.render(batch);
    shapeRenderer.end();
    batch.begin();
  }

  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    for(PEntity pe:sons) {
      pe.velocity.add(tmp.set(g).scl(delta));
    }
    batch.end();
    shapeRenderer.setProjectionMatrix(cam.combined);
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    super.UpdateAndRender(batch,delta);
    shapeRenderer.end();
    batch.begin();
  }
}
