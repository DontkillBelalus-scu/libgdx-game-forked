package hhs.app.hsgametest;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import hhs.app.hsgametest.physical.Ball;
import hhs.app.hsgametest.physical.PEntity;
import hhs.app.hsgametest.physical.World;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.EntityTool;
import hhs.gdx.hsgame.tools.ListenerBuilder;
import hhs.gdx.hsgame.tools.Resource;

public class PhysicalSimulation extends BasicScreen{
  Table ui=new Table();

  World world=new World();
  Ball ball=new Ball();
  float ppm=100,time=0,lt=0;
  Vector2 ov=new Vector2(10,5);
  Pixmap pixmap;
  Texture text;
  public void start() {
    ball.pos.set(0,(Resource.height-ppm*5)/ppm);
    ball.size.set(1,1);
    ball.velocity.set(ov);
    time=0;
    pixmap.setColor(ColorTool.rgba(0,0,0,0));
    pixmap.fill();
  }
  public PhysicalSimulation() {
    pixmap=new Pixmap(Resource.width,Resource.height,Pixmap.Format.RGBA8888);
    text=new Texture(pixmap);

    clearColor=ColorTool.烟墨色;
    d.style.font=Resource.font.newFont(64,Color.WHITE);
    setDebug(true);
    camera.viewportWidth=Resource.width/ppm;
    camera.viewportHeight=Resource.height/ppm;
    camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
    start();
    world.addEntity(ball);
    // world.update=false;
    addEntity(world);

    world.addEntity(
      new PEntity() {
        @Override
        public void render(SpriteBatch batch) {
          World.shapeRenderer.line(
            camera.viewportWidth/2,
            camera.viewportHeight/2,
            camera.viewportWidth/2+1,
            camera.viewportHeight/2);
        }
      });
    addEntity(EntityTool.createRenderer((batch)-> {
      final var font=Resource.font.newFont(32,Color.WHITE);
      font.getData().setScale(1/ppm);
      font.draw(batch,"一米示意↓",camera.viewportWidth/2,camera.viewportHeight/2+32/ppm);
    }));
    addEntity(EntityTool.createUpdater((d)->time+=d));
    addEntity(
      EntityTool.createRenderer(
        (batch)-> {
          pixmap.setColor(Color.RED);
          pixmap.drawCircle((int)(ball.center.x*ppm),(int)(camera.viewportHeight*ppm-ball.center.y*ppm),10);
          text.draw(pixmap,0,0);
          batch.draw(text,0,0,camera.viewportWidth,camera.viewportHeight);
        }));

    world.shapeRenderer.setColor(ColorTool.东方既白);

    setTimeAcceleration(0.5f);
    final StringBuilder sb=new StringBuilder();
    d.addTrace(()-> {
      sb.setLength(0);
      sp(sb,String.format("屏幕比例尺宽:%.1fm,高:%.1fm",camera.viewportWidth,camera.viewportHeight));
      sp(sb,String.format("时间流逝倍率:%.2f",timeAcceleration));
      sp(sb,String.format("时间流逝:%.1fs",time));
      sp(sb,String.format("x方向速度:%.2fm/s",ball.velocity.x));
      sp(sb,String.format("y方向速度:%.2fm/s",ball.velocity.y));
      sp(sb,String.format("合速度:%.2fm/s",ball.velocity.len()));
      sp(sb,String.format("速度偏转角:%.2f Deg",MathUtils.atan2(-ball.velocity.y,ball.velocity.x)*MathUtils.radiansToDegrees));
      sp(sb,String.format("x坐标:%.2fm,y坐标:%.2fm",ball.center.x,ball.center.y));
      return sb.toString();
    });

    final var tbs=new TextButton.TextButtonStyle(null,null,null,Resource.font.newFont(64,Color.GREEN));
    var bu=new TextButton("重新开始",tbs);
    var bu2=new TextButton("设置时间流逝倍率",tbs);
    var bu3=new TextButton("暂停",tbs);
    var bu4=new TextButton("设置速度",tbs);
    var bu5=new TextButton("设置位置",tbs);
    var bu6=new TextButton("隐藏信息",tbs);

    bu.addListener(ListenerBuilder.touch(()->start()));
    bu2.addListener(
      ListenerBuilder.touch(
        ()-> {
          bu3.setText("暂停");
          Intend.androidIntend.dialog(
            "设置时间流逝倍率","倍率",(s1,s2)->setTimeAcceleration(Float.valueOf(s1)),()-> {});
        }));

    bu3.addListener(
      ListenerBuilder.touch(
        ()-> {
          if(timeAcceleration!=0) {
            lt=timeAcceleration;
            timeAcceleration=0;
            bu3.setText("恢复");
          }else {
            timeAcceleration=lt;
            bu3.setText("暂停");
          }
        }));
    bu4.addListener(
      ListenerBuilder.touch(
        ()->Intend.androidIntend.dialog(
          "设置速度",
          "x分量速度",
          "y分量速度",
          (s1,s2)-> {
            ov.set(Float.valueOf(s1),Float.valueOf(s2));
            ball.velocity.set(ov);
          },
          ()-> {})));
    bu5.addListener(
      ListenerBuilder.touch(
        ()->Intend.androidIntend.dialog(
          "设置位置 单位：米",
          "x坐标",
          "y坐标",
          (s1,s2)->ball.pos.set(Float.valueOf(s1),Float.valueOf(s2)),
          ()-> {})));
    bu6.addListener(
      ListenerBuilder.touch(
        ()-> {
          if(stage.getActors().contains(d,true)) {
            d.remove();
            bu6.setText("显示信息");
          }else {
            stage.addActor(d);
            bu6.setText("隐藏信息");
          }
        }));

    ui.add(bu).padRight(50);
    ui.add(bu2).padRight(50);
    ui.add(bu3).padRight(50);
    ui.add(bu4).padRight(50);
    ui.add(bu5).padRight(50);
    ui.add(bu6);

    ui.setPosition(
      Resource.width/2-ui.getWidth()/2,Resource.height-ui.getPrefHeight());
    //d.addTrace(()->ui.getPrefWidth()+" "+ui.getPrefHeight());
    stage.addActor(ui);
    //    d.addTrace(()->String.format("x方向速度:%.2f",ball.velocity.x));
    //    d.addTrace(()->String.format("y方向速度:%.2f",ball.velocity.y));
    //    d.addTrace(()->String.format("合速度:%.2f",ball.velocity.len()));
    //
    // d.addTrace(()->String.format("速度偏转角:%.2f",MathUtils.atan2(-ball.velocity.y,ball.velocity.x)*MathUtils.radiansToDegrees));
  }

  public static void sp(StringBuilder sb,String s) {
    sb.append(s+"\n");
  }
}
