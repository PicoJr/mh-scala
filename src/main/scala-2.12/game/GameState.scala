package game

import game.item.craft.Crafts
import game.item.{Item, ItemType}
import game.quest.Quest
import game.unit.{Hunter, Monster}

/** Holds all game entities and states.
  * Created by nol on 11/11/17.
  */
class GameState(hunter: Hunter, quests: Seq[Quest], crafts: Crafts) {

  private var questsCompletedIds: Set[Long] = Set.empty

  /** Get hunter
    *
    * @return hunter
    */
  def getHunter: Hunter = hunter

  /** Get quests
    *
    * @return quests regardless of completion
    */
  def getQuests: Seq[Quest] = quests

  /** Get craft recipes
    *
    * @return craft recipes
    */
  def getCrafts: Crafts = crafts

  /** Find quest with id questId
    *
    * @param questID to find
    * @return quest with id questId if any else None
    */
  def findQuest(questID: Long): Option[Quest] = {
    getQuests.find(q => q.getUniqueId == questID)
  }

  /** Find monster with id monsterId
    *
    * @param monsterId to find
    * @return monster with id monsterId if any else None
    */
  def findMonster(monsterId: Long): Option[Monster] = {
    getQuests.find(q => q.getMonster.getUniqueId == monsterId) match {
      case Some(q) => Some(q.getMonster)
      case None => None
    }
  }

  /** Find item with id itemId
    *
    * @param itemId to find
    * @return item with id itemId if any else None
    */
  def findItem(itemId: Long): Option[Item] = {
    getHunter.getInventory.findItem(itemId)
  }

  /** Set quest with id questId as completed
    *
    * @param questId of quest
    */
  def setCompleted(questId: Long): Unit = {
    questsCompletedIds += questId
  }

  /** Check quest with id questId is completed
    *
    * @param questId of quest
    * @return true if quest is completed, false if not or invalid Id
    */
  def isCompletedQuest(questId: Long): Boolean = questsCompletedIds.contains(questId)
}


object GameState {

  /** Procedurally create a new GameState.
    * may fail if config values are inconsistent.
    *
    * @return new GameState
    */
  def createNewGameState: GameState = {
    val hunter = createHunter
    val crafts = Crafts.generateCraftRecipes
    val quests = createQuests(crafts)
    val itemTypesFirstLevel = crafts.getNonMaterial(Config.LEVEL_MIN)
    hunter.getInventory.addItems(itemTypesFirstLevel.map(i => ItemType.createItem(i)): _*)
    new GameState(hunter, quests, crafts)
  }

  private def createHunter: Hunter = {
    val hunter = new Hunter("unnamed")
    val weapon = ItemType.createItem(ItemType.createWeapon("fists", Config.LEVEL_MIN, 500))
    hunter.getInventory.addItems(weapon)
    hunter.getInventory.equipItem(weapon.getUniqueId)
    hunter
  }

  private def createQuests(crafts: Crafts): Seq[Quest] = {
    var quests: Seq[Quest] = Seq.empty
    for (level <- Config.LEVEL_MIN to Config.LEVEL_MAX) {
      val lootSizeAtLevel: Int = crafts.getMaterials(level).size
      val questsAtLevel: Int = Math.min(lootSizeAtLevel, Config.QUESTS_PER_LEVEL)
      assert(questsAtLevel > 0)
      val lootPerQuest: Int = lootSizeAtLevel / questsAtLevel
      assert(lootPerQuest >= 1)
      for (q <- 0 until questsAtLevel) {
        val loot = crafts.getMaterials(level).slice(q * lootPerQuest, q * lootPerQuest + lootPerQuest)
        quests = quests :+ Quest.createQuest(level, loot)
      }
    }
    quests
  }
}
