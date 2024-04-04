package hhs.game.diffjourney.entities;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.Task.Status;
import com.badlogic.gdx.ai.btree.branch.Sequence;
import com.badlogic.gdx.math.Vector2;

public class TestCharacter extends Character<TestCharacter.Enemy1State,TestCharacter>{
  BehaviorTree<Character> tree;
  Sequence<Character> root;

  public TestCharacter() {
    tree=new BehaviorTree<>();
    tree.addChild(root=new Sequence<>());
  }

  public void update(float delta) {

  }

  public static enum Enemy1State{
    find,
    attack
  }

  public static class Blackboard{
  }

  public class find extends LeafTask<Character>{

    @Override
    protected Task<Character> copyTo(Task<Character> arg0) {
      return arg0;
    }
    Vector2 tmp=new Vector2();
    @Override
    public Status execute() {
      if(tmp.set(getObject().pos.sub(pro.pos)).len2()>100) {

      }
      return Status.SUCCEEDED;
    }
  }

}
