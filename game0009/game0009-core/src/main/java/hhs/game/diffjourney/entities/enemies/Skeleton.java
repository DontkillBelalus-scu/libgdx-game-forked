package hhs.game.diffjourney.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import hhs.game.diffjourney.util.XmlAnimationLoader;
import hhs.gdx.hsgame.tools.AnimationSet;

public class Skeleton extends Enemy1{
  public static final Pool<Skeleton> pool=new Pool<>() {
    @Override
    public Skeleton newObject() {
      return new Skeleton();
    }
  };
  public static final AnimationSet<Character.State,TextureRegion> animData=XmlAnimationLoader.getAnimationSet("Skeleton.xml");
  static {
    Pools.set(Skeleton.class,pool);
  }
  public static AnimationSet<Character.State,TextureRegion> init() {
    var anim=new AnimationSet<Character.State,TextureRegion>();
    anim.setData(animData);
    return anim;
  }
  public Skeleton() {
    super(init());
    data.hp=data.maxHp=80;
    data.damage=3;
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
