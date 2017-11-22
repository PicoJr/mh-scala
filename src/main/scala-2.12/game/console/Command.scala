package game.console

import game.gamestate.GameStateTrait

/**
  * Created by nol on 11/11/17.
  */
trait Command {
  /** List quests
    *
    * @param gameState containing quests
    */
  def listQuests(gameState: GameStateTrait): Unit

  /** List items contained in hunter inventory
    *
    * @param gameState containing hunter inventory
    */
  def listInventory(gameState: GameStateTrait): Unit

  /** Show craft recipes requiring item with id itemId if any
    *
    * @param gameState containing craft recipes
    * @param itemId    of item
    */
  def showCraft(gameState: GameStateTrait, itemId: Long): Unit

  /** Craft item result of itemId1 and itemId2, add it to hunter inventory
    *
    * @param gameState containing items
    * @param itemId1   1st item id
    * @param itemId2   2nd item id
    */
  def craftItem(gameState: GameStateTrait, itemId1: Long, itemId2: Long): Unit

  /** Show quest info with id questId if any
    *
    * @param gameState containing quests
    * @param questId   of quest
    */
  def showQuest(gameState: GameStateTrait, questId: Long): Unit

  /** Show monster info with id monsterId if any
    *
    * @param gameState containing monsters
    * @param monsterId of monster
    */
  def showMonster(gameState: GameStateTrait, monsterId: Long): Unit

  /** Show hunter info
    *
    * @param gameState containing hunter
    */
  def showHunter(gameState: GameStateTrait): Unit

  /** Equip item from hunter inventory with id itemId if any and possible
    *
    * @param gameState containing inventory
    * @param itemId    of item
    */
  def equipItem(gameState: GameStateTrait, itemId: Long): Unit

  /** Un-equip item from hunter inventory with id itemId if any
    *
    * @param gameState containing inventory
    * @param itemId    of item
    */
  def unEquipItem(gameState: GameStateTrait, itemId: Long): Unit

  /** Show item info with id itemId if any
    *
    * @param gameState containing inventory
    * @param itemId    of item
    */
  def showItem(gameState: GameStateTrait, itemId: Long): Unit

  /** Set new name for hunter
    *
    * @param gameState containing hunter
    * @param newName   for hunter
    */
  def renameHunter(gameState: GameStateTrait, newName: String): Unit

  /** Start quest with id questId if any
    *
    * @param gameState containing quest
    * @param questId   of quest to start
    */
  def startQuest(gameState: GameStateTrait, questId: Long): Unit
}