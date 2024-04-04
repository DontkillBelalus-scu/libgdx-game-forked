package hhs.game.diffjourney.interoperable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import hhs.game.diffjourney.game.WorldMapChooserSense;
import hhs.gdx.hsgame.tools.EntityTool;
import hhs.gdx.hsgame.tools.ListenerBuilder;
import hhs.gdx.hsgame.tools.Resource;

public class TestInteroperable extends Interoperable{
  final static Texture text=Resource.asset.get("dian.png");
  public TestInteroperable(EntityTool.VectorProvider receivePos) {
    super(receivePos);
    setPosition(0,0);
    setSize(50,50);
    addListener(ListenerBuilder.touch(()-> {
      screen.stage.addActor(new ChooseWindow("进入征途",null,()->Resource.setScreen(new WorldMapChooserSense())));
    }));
  }
  @Override
  public void render(SpriteBatch batch) {
    batch.draw(text,pos.x,pos.y,size.x,size.y);
  }

}
