package hhs.game.diffjourney.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import hhs.gdx.hsgame.tools.ListenerBuilder;
import hhs.gdx.hsgame.tools.Resource;

// var window=new CompositeWindow();
// for(int i=0;i<30;i++) {
// var b=new PixelFontButton("测试"+i);
// b.setScale(6);
// window.addItem("测试"+i,b);
// }
// stage.addActor(window);
// window.debugAll();
public class CompositeWindow extends Table{
  public VerticalGroup items;
  public ScrollPane sleft,sright;
  ItemButton focusItem;

  public CompositeWindow() {
    left().top();
    setSize(Resource.width/1.25f,Resource.height/1.25f);
    setPosition(Resource.width/2-getWidth()/2,Resource.height/2-getHeight()/2);

    items=new VerticalGroup();
    sleft=new ScrollPane(items);
    sright=new ScrollPane(new Actor());

    sleft.setWidth(getWidth()/3);
    sright.setWidth(getWidth()-sleft.getWidth());
    sleft.setHeight(getHeight());
    sright.setHeight(getHeight());

    add(sleft);
    add(sright).fillY();
  }

  public void addItem(String title,final Widget content) {
    final var itemButton=new ItemButton(title);
    itemButton.addListener(
      ListenerBuilder.click(
        ()-> {
          if(focusItem!=null&&focusItem!=itemButton) {
            focusItem.chengeOfFocus();
          }
          focusItem=itemButton;
          sright.setActor(content);
        }));
    items.addActor(itemButton);

  }

  @Override
  public void act(float arg0) {
    sleft.setWidth(sleft.getPrefWidth());
    sright.setWidth(getWidth()-sleft.getWidth());
    super.act(arg0);
    // TODO: Implement this method
  }

}
