package hhs.game.diffjourney.interoperable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Null;
import hhs.game.diffjourney.ui.PixelFontButton;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.FontTool;
import hhs.gdx.hsgame.tools.ListenerBuilder;
import hhs.gdx.hsgame.tools.PixmapBuilder;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.tools.TextureTool;

public class ChooseWindow extends Table{
  String str;
  BitmapFont font;
  public ChooseWindow(String title,@Null Actor main,Runnable callback) {
    str=title;
    font=Resource.font.newFont(64,Color.WHITE);
    setBackground(TextureTool.ttd(PixmapBuilder.getRectangle(200,100,ColorTool.萱草黄)));
    setSize(Resource.u*8,Resource.u*4);
    setPosition(Resource.width/2-getWidth()/2,Resource.height/2-getHeight()/2);
    if(main!=null) {
      add(main);
      row();
    }
    var exit=new PixelFontButton("取消");
    exit.setScale(4);
    exit.addListener(ListenerBuilder.touch(()->remove()));
    add(exit).padRight(getWidth()/8);
    var button=new PixelFontButton("确定");
    button.setScale(4);
    button.addListener(ListenerBuilder.touch(callback));
    button.addListener(ListenerBuilder.touch(()->remove()));
    add(button);
  }
  @Override
  public void draw(Batch batch,float arg1) {
    super.draw(batch,arg1);
    FontTool.drawText(font,batch,str,getX(),
      getY()+getHeight()-font.getLineHeight()/2,
      getWidth(),
      font.getLineHeight());
  }

}