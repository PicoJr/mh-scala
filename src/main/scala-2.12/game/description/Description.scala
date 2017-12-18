package game.description

import game.gamestate.GameState

/** Provides descriptions of game instances to the player.
  * Created by nol on 23/11/17.
  */
trait Description {

  /** Returns description of craft recipes with item identified with itemId.
    *
    * @param gameState current
    * @param itemId    ingredient of craft recipe
    * @return description of craft recipes with item identified with itemId.
    */
  def descriptionRecipesWith(gameState: GameState, itemId: Long): String

  def descriptionItem(gameState: GameState, itemId: Long): String

  def descriptionInventory(gameState: GameState): String

  def descriptionHunter(gameState: GameState): String

  def descriptionMonster(gameState: GameState, monsterId: Long): String

  def descriptionQuest(gameState: GameState, questId: Long): String

  def descriptionQuestResult(gameState: GameState): String

  def descriptionScore(gameState: GameState): String
}
