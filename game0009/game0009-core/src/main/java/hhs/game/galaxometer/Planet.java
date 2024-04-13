package hhs.game.galaxometer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.wrapper.EntityCenterConcurrent;

public class Planet extends Entity<MainScreen>{
  //单位:10^24kg
  float mass=5.972f;
  //单位:10^2km
  float radiu=63.7f;
  Vector2 velocity=new Vector2();
  Vector2 position=new Vector2();
  Vector2 tmp=new Vector2(),tmp1=new Vector2();
  Color color=Galaxometer.colorList[MathUtils.random(0,Galaxometer.colorList.length-1)];
  public Planet(MainScreen p){
    super(p);
  }
  @Override
  public void update() {
    position.add(tmp.set(velocity).scl(p.delta));
    
    for(var e:p.center.list){
      if(e instanceof Planet pt&&pt!=this){
        float force=(p.G*pt.mass)/tmp.set(pt.position).sub(position).len2();
        velocity.add(tmp.set(pt.position).sub(position).nor().setLength(force));
      }
    }
  }
  
  @Override
  public void display() {
    p.fill(color);
    p.circle(position.x,position.y,radiu);
  }
}
