package game.quest

import game.gamestate.GameState

/**
  * Created by nol on 22/11/17.
  */
trait QuestLogic {

  /** Process quest result of hunter (from gameState) attempt at quest.
    *
    * @param gameState containing the given quest
    * @param quest     whose result is processed
    */
  def processQuest(gameState: GameState, quest: Quest): Unit
}
