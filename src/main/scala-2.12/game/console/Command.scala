package game.console

import game.GameState

/**
  * Created by nol on 11/11/17.
  */
trait Command {
  // TODO add filter options for list commands
  def listQuests(gameState: GameState): Unit

  def listInventory(gameState: GameState): Unit

  def showCraft(gameState: GameState, itemID: Long): Unit

  def showQuest(gameState: GameState, questID: Long): Unit

  def showMonster(gameState: GameState, monsterID: Long): Unit

  def showHunter(gameState: GameState): Unit

  def equipItem(gameState: GameState, itemID: Long): Unit

  def unEquipItem(gameState: GameState, itemID: Long): Unit

  def removeItem(gameState: GameState, itemID: Long): Unit

  def showItem(gameState: GameState, itemID: Long): Unit

  def renameHunter(gameState: GameState, newName: String): Unit

  def startQuest(gameState: GameState, questID: Long): Unit
}