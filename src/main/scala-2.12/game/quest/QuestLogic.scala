package game.quest

import game.gamestate.GameState

/**
  * Created by nol on 22/11/17.
  */
trait QuestLogic {

  /** Returns quest result of hunter (from gameState) attempt at quest.
    *
    * @param gameState containing the given quest
    * @param quest     whose result is processed
    * @return
    */
  def processQuestResult(gameState: GameState, quest: Quest): QuestResult
}
