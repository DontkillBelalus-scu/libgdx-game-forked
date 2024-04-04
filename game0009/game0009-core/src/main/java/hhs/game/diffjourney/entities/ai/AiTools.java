package hhs.game.diffjourney.entities.ai;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import org.apache.logging.log4j.core.util.internal.Status;
import pama1234.util.function.GetBoolean;

public class AiTools{
  public static <E> LeafTask getGuard(GetBoolean gbool) {
    return new LeafTask<E>() {
      @Override
      protected Task<E> copyTo(Task<E> task) {
        return task;
      }
      @Override
      public Status execute() {
        return gbool.get()?Status.SUCCEEDED:Status.FAILED;
      }
    };
  }
}
