package hhs.game.diffjourney.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.input.GestureDetector;
import hhs.gdx.hsgame.tools.CameraControlGesturer;
import hhs.game.diffjourney.entities.Protagonist;
import hhs.game.diffjourney.ui.UiList;
import hhs.gdx.hsgame.screens.LayersScreen;
import hhs.gdx.hsgame.ui.DesktopController;
import hhs.gdx.hsgame.ui.Controller;
import hhs.gdx.hsgame.tools.TextureTool;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.PixmapBuilder;

public class GameScreen extends LayersScreen{
  @Override
  public void show() {
    super.show();
    //setDebug(true);
    timeAcceleration=1;
    UiList.getBasicUi(this);
  }
  public void addMoveControler(Protagonist pro) {
    if(Gdx.app.getType()==ApplicationType.Desktop) {
      var ctrl=new DesktopController(pro);
      input.addProcessor(ctrl);
      addEntity(ctrl);
    }else {
      stage.addActor(
        new Controller(
          pro,
          TextureTool.ttr(PixmapBuilder.getRectangle(200,200,ColorTool.宝石蓝)),
          TextureTool.ttr(PixmapBuilder.getCircle(50,ColorTool.奶酪色))));
    }
  }
  public void addCameraControler() {
    input.addProcessor(new GestureDetector(new CameraControlGesturer(camera)));
  }
}
