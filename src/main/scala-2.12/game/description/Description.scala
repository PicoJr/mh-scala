package game.description

import game.gamestate.GameState
import game.quest.QuestResult

/**
  * Created by nol on 23/11/17.
  */
trait Description {

  def descriptionRecipesWith(gameState: GameState, itemId: Long): String

  def descriptionItem(gameState: GameState, itemId: Long): String

  def descriptionInventory(gameState: GameState): String

  def descriptionHunter(gameState: GameState): String

  def descriptionMonster(gameState: GameState, monsterId: Long): String

  def descriptionQuest(gameState: GameState, questId: Long): String

  def descriptionQuestResult(gameState: GameState, questResult: QuestResult): String

  def descriptionScore(gameState: GameState): String
}
