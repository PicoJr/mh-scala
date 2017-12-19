package game.console

/** Commands available to the player
  * Created by nol on 11/11/17.
  */
trait Command {
  /** List quests
    *
    */
  def listQuests(): Unit

  /** List items contained in hunter inventory
    *
    */
  def listInventory(): Unit

  /** Show craft recipes requiring item with id itemId if any
    *
    * @param itemId    of item
    */
  def showCraft(itemId: Long): Unit

  /** Craft item result of itemId1 and itemId2, add it to hunter inventory
    *
    * Note: id order should not be taken into account
    * craftItem(i1, i2) equivalent to craftItem(i2, i1)
    *
    * @param itemId1   one item id
    * @param itemId2   other item id
    */
  def craftItem(itemId1: Long, itemId2: Long): Unit

  /** Show quest info with id questId if any
    *
    * @param questId   of quest
    */
  def showQuest(questId: Long): Unit

  /** Show monster info with id monsterId if any
    *
    * @param monsterId of monster
    */
  def showMonster(monsterId: Long): Unit

  /** Show hunter info
    *
    */
  def showHunter(): Unit

  /** Show player (hunter) score
    *
    */
  def showScore(): Unit

  /** Equip item from hunter inventory with id itemId if any and possible
    *
    * @param itemId    of item
    */
  def equipItem(itemId: Long): Unit

  /** Un-equip item from hunter inventory with id itemId if any
    *
    * @param itemId    of item
    */
  def unEquipItem(itemId: Long): Unit

  /** Show item info with id itemId if any
    *
    * @param itemId    of item
    */
  def showItem(itemId: Long): Unit

  /** Set new name for hunter
    *
    * @param newName   for hunter
    */
  def renameHunter(newName: String): Unit

  /** Start quest with id questId if any
    *
    * @param questId   of quest to start
    */
  def startQuest(questId: Long): Unit
}