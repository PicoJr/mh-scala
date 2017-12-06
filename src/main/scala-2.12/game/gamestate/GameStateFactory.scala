package game.gamestate

/**
  * Created by nol on 06/12/17.
  */
trait GameStateFactory {
  def createGameState: GameState
}
