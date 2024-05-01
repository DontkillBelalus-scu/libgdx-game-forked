package hhs.game.diffjourney.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import hhs.game.diffjourney.entities.Enemy1;
import hhs.game.diffjourney.entities.Goblin;
import hhs.game.diffjourney.entities.Mushroom;
import hhs.game.diffjourney.entities.Skeleton;
import hhs.gdx.hsgame.tools.Resource;

public class MultipleEnemyGenerator{
  public static final Pool[] ep= {
    Pools.get(Chort.class),
    Pools.get(Mushroom.class),
    Pools.get(BigDemon.class),
    Pools.get(FlyingEye.class),
    Pools.get(Goblin.class),
    Pools.get(Skeleton.class)};
  public static void loadTexture(String str) {
    Resource.asset.load(str,Texture.class);
  }
  public static Enemy1 getEnemy1() {
    return (Enemy1)(ep[MathUtils.random(0,ep.length-1)].obtain());
  }
}
