package hhs.game.diffjourney.game;

import hhs.game.diffjourney.entities.Protagonist;
import hhs.game.diffjourney.interoperable.TestInteroperable;
import hhs.game.diffjourney.map.Map;
import hhs.game.diffjourney.screens.GameScreen;
import hhs.game.diffjourney.vfx.GlobalFog;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.EntityTool;
import squidpony.squidmath.RNG;

public class HomeSense extends GameScreen{
  Protagonist protagonist;
  public HomeSense() {
    setClearColor(ColorTool.碧落);

    addCameraControler();
    protagonist=new Protagonist();
    addEntity(protagonist);
    addMoveControler(protagonist);

    var dg=new squidpony.squidgrid.mapping.DenseRoomMapGenerator(20,10,new RNG("home"));
    Map m;
    addEntity(m=new Map(dg.generate(),1,camera));
    protagonist.setMap(m.map);
    protagonist.setCurr(m);

    addEntity(new TestInteroperable(EntityTool.providePosition(protagonist)));

    var fog=new GlobalFog();
    addEntity(fog);

  }
}
