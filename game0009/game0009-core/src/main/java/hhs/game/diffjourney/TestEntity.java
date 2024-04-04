package hhs.game.diffjourney;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;

public class TestEntity<T extends UtilScreen>extends Entity<T>{
  public TestEntity(T p) {
    super(p);
  }
  @Override
  public void update() {

  }
  @Override
  public void display() {

  }

}
