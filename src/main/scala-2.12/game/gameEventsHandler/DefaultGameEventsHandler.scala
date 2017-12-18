package game.gameEventsHandler

/**
  * Created by nol on 18/12/17.
  */
class DefaultGameEventsHandler extends GameEventsHandler {
}

object DefaultGameEventsHandler {
  private final val gameEventsHandler = new DefaultGameEventsHandler

  def getGameEventsHandler: GameEventsHandler = gameEventsHandler
}
