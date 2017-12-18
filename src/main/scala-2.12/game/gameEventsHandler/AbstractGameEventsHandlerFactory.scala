package game.gameEventsHandler

/**
  * Created by nol on 18/12/17.
  */
trait AbstractGameEventsHandlerFactory {
  def createGameEventsHandler: GameEventsHandler
}
