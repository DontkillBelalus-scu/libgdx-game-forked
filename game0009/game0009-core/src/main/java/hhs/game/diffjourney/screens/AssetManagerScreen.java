package hhs.game.diffjourney.screens;

import com.badlogic.gdx.assets.AssetManager;

public interface AssetManagerScreen{
  public final AssetManager manager=new AssetManager();
  default void dispose() {
    manager.dispose();
  }
}
