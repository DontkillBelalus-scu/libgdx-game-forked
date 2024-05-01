package hhs.game.diffjourney.entities.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import hhs.game.diffjourney.entities.Character;
import hhs.game.diffjourney.entities.Enemy1;
import hhs.game.diffjourney.util.XmlAnimationLoader;
import hhs.gdx.hsgame.tools.AnimationSet;

public class FlyingEye extends Enemy1{
  public static final Pool<FlyingEye> pool=new Pool<>() {
    @Override
    public FlyingEye newObject() {
      return new FlyingEye();
    }
  };
  public static final AnimationSet<Character.State,TextureRegion> animData=XmlAnimationLoader.getAnimationSet("FlyingEye.xml");
  static {
    Pools.set(FlyingEye.class,pool);
  }
  public static AnimationSet<Character.State,TextureRegion> init() {
    var anim=new AnimationSet<Character.State,TextureRegion>();
    anim.setData(animData);
    return anim;
  }
  public FlyingEye() {
    super(init());
    size.set(20*1.5f,30*1.5f).scl(2);
    setTransformer((p,s)-> {
      p.set(pos.x-2*size.x,pos.y-size.y);
      s.set(size.x*5,size.x*5);
    });
  }

  @Override
  public void remove() {
    pool.free(this);
  }
}
