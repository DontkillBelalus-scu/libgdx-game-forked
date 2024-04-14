package com.test.galaxometer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import pama1234.gdx.util.entity.Entity;

public class Planet extends Entity<MainScreen>{
  //单位:10^24kg
  float mass=5.972f;
  //单位:10^2km
  float radiu=63.7f;
  Vector2 velocity=new Vector2(0,0);
  Vector2 position=new Vector2();
  Vector2 tmp=new Vector2(),tmp1=new Vector2();
  Color color=Galaxometer.colorList[MathUtils.random(0,Galaxometer.colorList.length-1)];
  public Planet(MainScreen p) {
    super(p);
  }
  @Override
  public void update() {
    for(var e:p.planetCenter.list) {
      float force=(p.G*e.mass*mass)/tmp.set(e.position).sub(position).len2();
      tmp.set(e.position).sub(position).setLength(force/mass*p.delta);
      velocity.add(tmp);
    }
    position.add(tmp.set(velocity).scl(p.delta));
  }
  public void baseVelocityIn(Planet e) {
    float force=(p.G*e.mass)/tmp.set(e.position).sub(position).len();
    tmp.set(e.position).sub(position);
    tmp1.set(1,-tmp.x/tmp.y).setLength((float)Math.sqrt(force));
    velocity.set(tmp1);
  }

  float sqrt(float x) {
    float val=x; // 最终
    float last; // 保存上一个计算的值
    do {
      last=val;
      val=(val+x/val)/2;
    }while(Math.abs(val-last)>0.0001);
    return val;
  }

  @Override
  public void display() {
    p.fill(color);
    p.circle(position.x,position.y,radiu);
  }

}
