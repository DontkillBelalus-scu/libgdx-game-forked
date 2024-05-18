package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ListenerBuilder{
  public static EventListener touch(Runnable r) {
    return click(r);
  }
  public static EventListener click(Runnable r) {
    return new ClickListener() {
      public void clicked(InputEvent event,float x,float y) {
        r.run();
      }
    };
  }
}
