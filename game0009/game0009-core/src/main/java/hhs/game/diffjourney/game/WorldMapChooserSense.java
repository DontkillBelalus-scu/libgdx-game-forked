package hhs.game.diffjourney.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import hhs.game.diffjourney.interoperable.ChooseWindow;
import hhs.game.diffjourney.screens.GameScreen;
import hhs.game.diffjourney.ui.UiList;
import hhs.game.diffjourney.util.CameraViewController;
import hhs.gdx.hsgame.entities.Entity;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.ui.WorldMapChooser;
import hhs.gdx.hsgame.ui.WorldMapChooser.Node;

public class WorldMapChooserSense extends GameScreen{
  Stage nstage=new Stage();
  public WorldMapChooserSense() {
    setClearColor(ColorTool.碧落);
    addEntity(
      new Entity() {
        @Override
        public void update(float delta) {
          nstage.act(delta);
        }

        @Override
        public void render(SpriteBatch batch) {
          nstage.draw();
        }

        @Override
        public void dispose() {}
      });
    input.addProcessor(nstage);
    input.addProcessor(
      new GestureDetector(new CameraViewController((OrthographicCamera)nstage.getCamera())));

    var chooser=new WorldMapChooser();
    chooser.setBounds(0,0,Integer.MAX_VALUE,Integer.MAX_VALUE);
    chooser.setFont(UiList.font);
    chooser.setFontScale(2);
    chooser.nodes.add(new Node("蘑菇森林",tip("内部充满奇怪的蘑菇，似乎要喷涌而出",TestSence.class)));
    nstage.addActor(chooser);
  }

  public Runnable tip(String description,Class<? extends BasicScreen> cscreen) {
    return ()-> {
      var window=new ChooseWindow(description,null,()->Resource.setScreen(cscreen));
      window.setPosition(window.getX(),-window.getHeight());
      window.addAction(
        Actions.moveTo(
          Resource.width/2-window.getWidth()/2,
          Resource.height/2-window.getHeight()/2,
          0.25f));
      stage.addActor(window);
    };
  }
}
