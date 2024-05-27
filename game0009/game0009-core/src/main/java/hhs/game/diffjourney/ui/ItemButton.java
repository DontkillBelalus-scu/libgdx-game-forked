package hhs.game.diffjourney.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import hhs.gdx.hsgame.tools.ListenerBuilder;

public class ItemButton extends PixelFontButton{
  boolean selected=false;
  public ItemButton(String str) {
    super(str);
    setScale(6);
    clearListeners();
    addListener(ListenerBuilder.click(()->selected=true));
  }
  @Override
  public void draw(Batch batch,float arg1) {
    super.draw(batch,arg1);
    down=selected;
  }
  public void chengeOfFocus() {
    selected=false;
  }
}
