package hhs.game.diffjourney;

import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.gdx.util.entity.Entity;

public class MyScreen extends UtilScreen2D{
  @Override
  public void setup() {
    //假设这是游戏里的一个实体
    Entity<MyScreen> e=new TestEntity<>(this);
    //增加一个Entity
    center.add(e);
    //删除这个Entity
    center.remove(e);
  }

  @Override
  public void update() {}

  @Override
  public void display() {}

  @Override
  public void displayWithCam() {}

  @Override
  public void frameResized() {}
}
