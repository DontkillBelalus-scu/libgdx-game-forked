package hhs.gdx.hsgame.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.PixmapBuilder;
import hhs.gdx.hsgame.tools.Resource;

public class WorldMapChooser extends Actor{
  Texture point=PixmapBuilder.getCircle(50,ColorTool.乳白);
  public BitmapFont font;
  float fontScale=1,pointSize=100,interval=Resource.u*2;
  public Array<Node> nodes=new Array<>();

  public WorldMapChooser() {
    addListener(
      new InputListener() {
        @Override
        public boolean touchDown(InputEvent arg0,float x,float y,int arg3,int arg4) {
          float cx=0;
          Rectangle.tmp2.set(x,y,Resource.u/15,Resource.u/15);
          for(Node n:nodes) {
            cx=(n.index==-1?cx+interval:cx*n.index);
            if(Rectangle.tmp.set(cx,n.y,pointSize,pointSize).overlaps(Rectangle.tmp2)) {
              n.callback.run();
              return false;
            }
          }
          return super.touchDown(arg0,x,y,arg3,arg4);
        }
      });
  }

  @Override
  public void draw(Batch batch,float arg1) {
    super.draw(batch,arg1);
    float cx=0,cs=50;
    font.getData().setScale(fontScale);
    for(Node n:nodes) {
      cx=(n.index==-1?cx+interval:cx*n.index);
      batch.draw(point,getX()+cx,getY()+n.y,pointSize,pointSize);
      font.draw(batch,n.str,getX()+cx,getY()+n.y);
    }
    font.getData().setScale(1);
  }

  public static class Node{
    public int index=-1;
    public float y;
    public String str="";
    public Runnable callback;

    public Node() {
      y=MathUtils.random(0,Resource.height/2);
    }

    public Node(Runnable callback) {
      this();
      this.callback=callback;
    }

    public Node(float y) {
      this.y=y;
    }

    public Node(String str,Runnable callback) {
      this.str=str;
      this.callback=callback;
    }
  }

  public float getFontScale() {
    return this.fontScale;
  }

  public void setFontScale(float fontScale) {
    this.fontScale=fontScale;
  }

  public float getPointSize() {
    return this.pointSize;
  }

  public void setPointSize(float pointSize) {
    this.pointSize=pointSize;
  }

  public float getInterval() {
    return this.interval;
  }

  public void setInterval(float interval) {
    this.interval=interval;
  }

  public BitmapFont getFont() {
    return this.font;
  }

  public void setFont(BitmapFont font) {
    this.font=font;
  }
}
