package hhs.game.diffjourney.game;

import hhs.game.diffjourney.entities.Protagonist;
import hhs.game.diffjourney.interoperable.TestInteroperable;
import hhs.game.diffjourney.map.Map;
import hhs.game.diffjourney.screens.GameScreen;
import hhs.gdx.hsgame.light.LightCenter;
import hhs.gdx.hsgame.light.PointLight;
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

    var lcenter=new LightCenter();
    addEntity(lcenter);

    final var pLight=new PointLight();
    pLight.setRadiu(100);
    pLight.setIntensity(0);
    lcenter.add(pLight);
    addEntity(EntityTool.createUpdater((d)->pLight.setPosition(protagonist.pos.x-pLight.size.x/2,protagonist.pos.y-pLight.size.y/2)));

    m.computationalIllumination(0.125f);

  }
}
