package game.gamestate

import game.config.ConfigLoader
import game.item.craft.{CraftPrototype, Crafts}
import game.item.element.DefaultEEResolver
import game.item.{DefaultItem, DefaultItemType}
import game.quest._
import game.unit.{DefaultHunter, Hunter}

/** Holds all game entities and states.
  * Created by nol on 11/11/17.
  */
class DefaultGameState(hunter: Hunter, quests: Seq[Quest], crafts: Crafts, questLogic: QuestLogic) extends GameState {

  private var questsCompletedIds: Set[Long] = Set.empty

  def getHunter: Hunter = hunter

  def getQuests: Seq[Quest] = quests

  def getCrafts: Crafts = crafts

  def setCompleted(questId: Long): Unit = {
    questsCompletedIds += questId
  }

  def isCompletedQuest(questId: Long): Boolean = questsCompletedIds.contains(questId)

  override def getQuestLogic: QuestLogic = questLogic
}


object DefaultGameState {

  private val config = ConfigLoader.loadGameConfig

  /** Procedurally create a new GameState.
    * may fail if config values are inconsistent.
    *
    * @return new GameState
    */
  def createNewGameState: DefaultGameState = {
    val hunter = createHunter
    val crafts = CraftPrototype.generateCraft
    val quests = createQuests(crafts)
    val itemTypesFirstLevel = crafts.getNonMaterials(config.getLevelMin).distinct
    hunter.getInventory.addItems(itemTypesFirstLevel.map(i => DefaultItem.createItem(i)): _*)
    val questLogic = new DefaultQuestLogic(new DefaultEEResolver)
    new DefaultGameState(hunter, quests, crafts, questLogic)
  }

  private def createHunter: Hunter = {
    val hunter = new DefaultHunter("unnamed")
    val weapon = DefaultItem.createItem(DefaultItemType.createWeapon(config.getLevelMin, 500))
    hunter.getInventory.addItems(weapon)
    hunter.getInventory.equipItem(weapon.getUniqueId)
    hunter
  }

  private def createQuests(crafts: Crafts): Seq[Quest] = {
    var quests = Seq.empty[Quest]
    for (level <- config.getLevelMin until config.getLevelMax) {
      val lootAtLevel = crafts.getMaterials(level).distinct
      val lootSizeAtLevel: Int = lootAtLevel.size
      val questsAtLevel: Int = Math.min(lootSizeAtLevel, config.getQuestsPerLevel)
      assert(questsAtLevel > 0, level)
      val lootPerQuest: Int = lootSizeAtLevel / questsAtLevel
      assert(lootPerQuest >= 1)
      for (q <- 0 until questsAtLevel) {
        val loot = lootAtLevel.slice(q * lootPerQuest, q * lootPerQuest + lootPerQuest)
        quests = quests :+ DefaultQuest.createQuest(level, loot)
      }
    }
    quests
  }
}