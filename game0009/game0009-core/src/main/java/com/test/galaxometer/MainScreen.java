package com.test.galaxometer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.cam.CameraController2D;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.UtilMath;

public class MainScreen extends ScreenCore2D{

  int lev=24;
  public float delta=0;
  public static float G=6754f;
  float timeVelocityMultiplier=40;
  public EntityCenter<MainScreen,Planet> planetCenter;
  Planet ptest;
  @Override
  public void setup() {
    if(!isAndroid) cam2d.pixelPerfect=CameraController2D.SMOOTH;
    if(isAndroid) {
      camStrokeWeight=()->cam2d.pixelPerfect==CameraController2D.SMOOTH?cam2d.scale.pos/4f:u/128*cam2d.scale.pos;
    }else {
      camStrokeWeight=()->cam2d.pixelPerfect==CameraController2D.SMOOTH?cam2d.scale.pos/8f:u/512f*cam2d.scale.pos;
    }
    cam2d.maxScale=64;
    cam2d.minScale=1/64f;
    cam2d.scale.des=1/2f;
    cam2d.testScale();
    cam2d.active(true);
    backgroundColor(Color.BLACK);

    planetCenter=new EntityCenter<>(this);
    centerCam.add(planetCenter);

    var p1=new Planet(this);
    planetCenter.add(p1);

    var p2=new Planet(this);
    p2.position.set(150,150);
    p2.mass/=81;
    p2.radiu/=2;
    p2.baseVelocityIn(p1);
    planetCenter.add(p2);

    var p3=new Planet(this);
    p3.mass/=128;
    p3.radiu/=2.5f;
    p3.position.set(500,200);
    p3.baseVelocityIn(p1);
    planetCenter.add(p3);

    ptest=p2;
  }

  @Override
  public void update() {
    delta=Gdx.graphics.getDeltaTime()*timeVelocityMultiplier;
  }

  @Override
  public void display() {
    textSize(UtilMath.max(UtilMath.floor(pus/2f),1)*16);
    fullText(cam2d.scale.f+
      " "+
      cam2d.scale.des+
      "\n"+
      ptest.velocity.toString(),(int)u,(int)u);
  }

  @Override
  public void displayWithCam() {}

  @Override
  public void frameResized() {}
}
