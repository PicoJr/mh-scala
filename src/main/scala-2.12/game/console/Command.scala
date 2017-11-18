package game.console

import game.GameState

/**
  * Created by nol on 11/11/17.
  */
trait Command {
  /** List quests
    *
    * @param gameState containing quests
    */
  def listQuests(gameState: GameState): Unit

  /** List items contained in hunter inventory
    *
    * @param gameState containing hunter inventory
    */
  def listInventory(gameState: GameState): Unit

  /** Show craft recipes requiring item with id itemId if any
    *
    * @param gameState containing craft recipes
    * @param itemId    of item
    */
  def showCraft(gameState: GameState, itemId: Long): Unit

  /** Show quest info with id questId if any
    *
    * @param gameState containing quests
    * @param questId   of quest
    */
  def showQuest(gameState: GameState, questId: Long): Unit

  /** Show monster info with id monsterId if any
    *
    * @param gameState containing monsters
    * @param monsterId of monster
    */
  def showMonster(gameState: GameState, monsterId: Long): Unit

  /** Show hunter info
    *
    * @param gameState containing hunter
    */
  def showHunter(gameState: GameState): Unit

  /** Equip item from hunter inventory with id itemId if any and possible
    *
    * @param gameState containing inventory
    * @param itemId    of item
    */
  def equipItem(gameState: GameState, itemId: Long): Unit

  /** Un-equip item from hunter inventory with id itemId if any
    *
    * @param gameState containing inventory
    * @param itemId    of item
    */
  def unEquipItem(gameState: GameState, itemId: Long): Unit

  /** Show item info with id itemId if any
    *
    * @param gameState containing inventory
    * @param itemId    of item
    */
  def showItem(gameState: GameState, itemId: Long): Unit

  /** Set new name for hunter
    *
    * @param gameState containing hunter
    * @param newName   for hunter
    */
  def renameHunter(gameState: GameState, newName: String): Unit

  /** Start quest with id questId if any
    *
    * @param gameState containing quest
    * @param questId   of quest to start
    */
  def startQuest(gameState: GameState, questId: Long): Unit
}