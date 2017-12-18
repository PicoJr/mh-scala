package game.gameEventsHandler

/**
  * Created by nol on 18/12/17.
  */
class DefaultGameEventsHandlerFactory extends AbstractGameEventsHandlerFactory {
  override def createGameEventsHandler: GameEventsHandler = new DefaultGameEventsHandler
}

object DefaultGameEventsHandlerFactory {
  private final val gameEventsHandlerFactory = new DefaultGameEventsHandlerFactory

  def getGameEventsHandlerFactory: AbstractGameEventsHandlerFactory = gameEventsHandlerFactory
}

