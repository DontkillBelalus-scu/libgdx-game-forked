package hhs.gdx.hsgame.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Pools;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StringBuilder;

public class DialogBox extends Actor{
  TextureRegion background;
  LinkedList<String> sequence; // 文字序列
  StringBuilder appear=new StringBuilder();
  String paragraph;
  boolean end=false;
  public boolean isRemoved;
  int apc=0;
  float appearTime=.02f,time=0,scale=8;
  protected BitmapFont font;
  int fontSize=0;
  boolean ok=false;
  boolean autoRemove=true;

  public DialogBox(Texture background) {
    this(new TextureRegion(background));
  }

  public DialogBox(TextureRegion background) {
    this();
    this.background=background;
  }

  public DialogBox() {
    sequence=new LinkedList<>();
    addListener(
      new ClickListener() {
        public void clicked(InputEvent event,float x,float y) {
          ok=ok?false:true;
          if(!ok) {
            if(!sequence.isEmpty()) paragraph=sequence.pollFirst();
            return;
          }
          appear.clear();
          //			if(paragraph == null&& !sequence.isEmpty()){
          //				appear.appendLine(sequence.pollFirst());
          //			}
          appear.appendLine(paragraph);
          apc=0;
        }
      });
  }

  @Override
  public void act(float arg0) {
    if(sequence.isEmpty()&&ok) {
      end=true;
      return;
    }else {
      if(end==true) {
        if(autoRemove) {
          remove();
          isRemoved=true;
        }
      }
      end=false;
    }
    if(font==null) throw new GdxRuntimeException("No font set");
    if(ok) {
      return;
    }
    if(paragraph==null&&!sequence.isEmpty()) {
      paragraph=sequence.pollFirst();
    }
    if(paragraph!=null&&paragraph.length()==apc) {
      paragraph=null;
      apc=0;
      ok=true;
      return;
    }
    if((time+=arg0)>appearTime&&paragraph!=null) {
      time=0;
      if(apc==0) appear.clear();
      appear.append(paragraph.charAt(apc++));
    }
  }

  @Override
  public void draw(Batch batch,float arg1) {
    batch.draw(background,getX(),getY(),getWidth(),getHeight());
    font.getData().setScale(scale);
    drawText(
      font,
      batch,
      appear.toString(),
      getX(),
      getHeight()-font.getLineHeight()/2,
      (int)getWidth(),
      (int)font.getLineHeight());
    font.getData().setScale(1);
    //    font.draw(
    //      batch,
    //      appear.toString(),
    //      (int)getX(),
    //      (int)getHeight()-font.getDescent()/2,
    //      (int)getWidth(),
    //      (int)0,true);
  }

  public BitmapFont getFont() {
    return this.font;
  }

  public void setFont(BitmapFont font) {
    this.font=font;
    fontSize=getWidth("田");
  }

  public LinkedList<String> getSequence() {
    return this.sequence;
  }

  public void setSequence(LinkedList<String> sequence) {
    this.sequence=sequence;
  }

  public boolean getAutoRemove() {
    return this.autoRemove;
  }

  public void setAutoRemove(boolean autoRemove) {
    this.autoRemove=autoRemove;
  }

  public int getWidth(String str) {
    GlyphLayout layout=Pools.obtain(GlyphLayout.class);
    layout.setText(font,str);
    return (int)layout.width;
  }

  public void drawText(
    BitmapFont font,Batch batch,String text,float x,float y,int w,int lineSpacing) {
    int lastSubStrIndex=0;
    for(int i=0;i<text.length();i++) {
      // 计算当前字符串、最后一个字符、宽度
      String currentSubStr=text.substring(lastSubStrIndex,i+1); // 当前正在组建的一行字符串
      char currentLastChar=currentSubStr.charAt(currentSubStr.length()-1);
      int cW=getWidth(currentSubStr);
      if(cW>w||currentLastChar=='\n') {
        font.draw(batch,text.substring(lastSubStrIndex,i),x,y);
        y-=fontSize/2f+lineSpacing; // fontSize/2等于行高
        if(currentLastChar=='\n') {
          lastSubStrIndex=i+1;
        }else {
          lastSubStrIndex=i;
        }
      }
      if(i==text.length()-1) {
        font.draw(batch,text.substring(lastSubStrIndex,i+1),x,y);
      }
    }
  }

  public float getScale() {
    return this.scale;
  }

  public void setScale(float scale) {
    this.scale=scale;
  }
}