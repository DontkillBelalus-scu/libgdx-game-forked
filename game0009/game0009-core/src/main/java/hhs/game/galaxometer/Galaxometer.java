package hhs.game.galaxometer;

import com.badlogic.gdx.Game;

public class Galaxometer extends Game {

  @Override
  public void create() {
    setScreen(new MainScreen());
  }
}
