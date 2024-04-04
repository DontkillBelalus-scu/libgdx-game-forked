package hhs.game.diffjourney.entities.ai;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task.Status;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.badlogic.gdx.ai.btree.branch.RandomSequence;
import com.badlogic.gdx.ai.btree.branch.Sequence;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import hhs.game.diffjourney.entities.Character;
import hhs.gdx.hsgame.entities.Entity;
import hhs.gdx.hsgame.util.Rect;

/*
*
*
*
*/
public class EnemyAi extends Entity{

  public Sequence<Character> root;
  public BehaviorTree<Character> tree;
  Item<Rect> info;
  float speed=150;
  float delta=0;

  public EnemyAi(Character character) {
    tree.setObject(character);
    root=new Sequence<>();
    tree.addChild(root);

    var rdq=new RandomSequence<Character>();
    root.addChild(rdq);
    var idle=new Idle();
    rdq.addChild(idle);
  }

  @Override
  public void update(float delta) {
    this.delta=delta;
    tree.step();
  }

  @Override
  public void render(SpriteBatch batch) {}

  Vector2 tmp=new Vector2(),tmp2=new Vector2();

  public class isNearby extends LeafTask<Character>{
    @TaskAttribute
    public float distance=700;
    public isNearby() {}
    public isNearby(float distance) {
      this.distance=distance;
    }
    @Override
    protected Task<Character> copyTo(Task<Character> task) {
      ((isNearby)task).distance=distance;
      return task;
    }
    @Override
    public Status execute() {
      if(tmp.set(getObject().pos).sub(getObject().pro.pos).len2()<=700) return Status.SUCCEEDED;
      else return Status.FAILED;
    }
  }

  public class Idle extends LeafTask<Character>{
    @Override
    protected Task<Character> copyTo(Task<Character> arg0) {
      return arg0;
    }

    @Override
    public Status execute() {
      getObject().state=Character.State.idle;
      return Status.SUCCEEDED;
    }
  }

  public class Walk extends LeafTask<Character>{
    Vector2 direction=new Vector2();

    @Override
    protected Task<Character> copyTo(Task<Character> arg0) {
      return arg0;
    }

    @Override
    public Status execute() {
      Vector2 pos=getObject().pos;
      if(info!=null) {
        World<Rect> world=getObject().c.getCollisions();
        var victroy=tmp.set(direction);
        tmp.set(pos).add(tmp2.set(direction).scl(speed*delta));
        Response.Result result=world.move(info,tmp.x,tmp.y,Character.normal);
        pos.set(result.goalX,result.goalY);
      }else {
        pos.add(tmp.set(direction).scl(speed*delta));
      }
      return Status.SUCCEEDED;
    }
  }

  public class Attack extends LeafTask<Character>{
    Vector2 direction=new Vector2();

    @Override
    protected Task<Character> copyTo(Task<Character> arg0) {
      return arg0;
    }

    @Override
    public Status execute() {
      if(getObject() instanceof Character.Attachable a) {
        a.hurt(getObject().pro);
        return Status.SUCCEEDED;
      }
      return Status.FAILED;
    }
  }

  @Override
  public void dispose() {}

  public Item<Rect> getInfo() {
    return this.info;
  }

  public void setInfo(Item<Rect> info) {
    this.info=info;
  }
}
