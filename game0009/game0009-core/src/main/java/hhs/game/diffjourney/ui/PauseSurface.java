package hhs.game.diffjourney.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.ListenerBuilder;
import hhs.gdx.hsgame.tools.PixmapBuilder;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.tools.TextureTool;

public class PauseSurface extends Table{
  BasicScreen screen;
  float fontScale=8,time=0,animTime=.4f;
  public boolean show=true;
  public static Texture shadow=PixmapBuilder.getRectangle(200,100,ColorTool.rgba(0,0,0,100));
  Vector2 correctPos=new Vector2();
  PixelFontButton c;
  public PauseSurface(BasicScreen b) {
    setBackground(TextureTool.ttd(PixmapBuilder.getRectangle(1,1,ColorTool.明月珰)));
    screen=b;
    center();
    c=newButton("继续游戏");
    c.addListener(
      ListenerBuilder.touch(
        ()-> {
          screen.setTimeAcceleration(1);
          PauseSurface.this.remove();
        }));
    add(UiList.getBack()).padTop(50);
    validate();
    setSize(getPrefWidth(),getPrefHeight());
    setPosition(Resource.width/2-getPrefWidth()/2,-getPrefHeight());
    correctPos.set(getX(),Resource.height/2-getPrefHeight()/2);
    setY(correctPos.y);
  }
  @Override
  public void act(float arg0) {
    super.act(arg0);
    if(show) setY(getY()+(correctPos.y-getY())/8);
    else setY(getY()+(-getPrefHeight()*1.5f-getY())/8);
    if(getY()<-getPrefHeight()) {
      show=true;
      super.remove();
    }
  }
  @Override
  public void draw(Batch arg0,float arg1) {
    arg0.draw(shadow,0,0,Resource.width,Resource.height);
    super.draw(arg0,arg1);
  }
  public PixelFontButton newButton(String str) {
    PixelFontButton b=new PixelFontButton(str);
    b.setScale(fontScale);
    add(b).width(b.getPrefWidth()).padBottom(50).row();
    return b;
  }
  public BasicScreen getScreen() {
    return this.screen;
  }
  public void setScreen(BasicScreen screen) {
    this.screen=screen;
  }
  @Override
  public boolean remove() {
    show=false;
    return true;
  }

}
