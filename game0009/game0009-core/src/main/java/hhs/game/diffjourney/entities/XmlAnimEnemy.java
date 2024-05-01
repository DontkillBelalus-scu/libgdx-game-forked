package hhs.game.diffjourney.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import hhs.game.diffjourney.util.XmlAnimationLoader;
import hhs.gdx.hsgame.tools.AnimationSet;

public class XmlAnimEnemy extends Enemy1{
  public static Pool<XmlAnimEnemy> pool=new Pool<>() {
    @Override
    public XmlAnimEnemy newObject() {
      return new XmlAnimEnemy("");
    }
  };
  public static AnimationSet<Character.State,TextureRegion> animData=XmlAnimationLoader.getAnimationSet("Mushroom.xml");
  static {
    Pools.set(XmlAnimEnemy.class,pool);
  }
  public static AnimationSet<Character.State,TextureRegion> init() {
    var anim=new AnimationSet<Character.State,TextureRegion>();
    anim.setData(animData);
    return anim;
  }
  public XmlAnimEnemy(String file) {
    super(init());
  }
  @Override
  public void remove() {
    pool.free(this);
  }
}
