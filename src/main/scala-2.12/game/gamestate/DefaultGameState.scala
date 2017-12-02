package game.gamestate

import game.config.ConfigLoader
import game.item.DefaultItem
import game.item.craft.{CraftPrototype, Crafts}
import game.item.element.DefaultEEResolver
import game.quest._
import game.unit.{DefaultHunter, Hunter}
import game.util.DefaultRandomPool

/** Holds all game entities and states.
  * Created by nol on 11/11/17.
  */
class DefaultGameState(hunter: Hunter, quests: Seq[Quest], crafts: Crafts, questLogic: QuestLogic) extends GameState {

  private var questsCompletedIds: Set[Long] = Set.empty
  private val defaultScore: DefaultScore = new DefaultScore

  override def getHunter: Hunter = hunter

  override def getQuests: Seq[Quest] = quests

  override def getCrafts: Crafts = crafts

  override def setCompleted(questId: Long): Unit = {
    questsCompletedIds += questId
  }

  override def isCompletedQuest(questId: Long): Boolean = questsCompletedIds.contains(questId)

  override def getQuestLogic: QuestLogic = questLogic

  override def getScore: Score = defaultScore
}


object DefaultGameState {

  private val config = ConfigLoader.loadGameConfig
  private val nameConfig = ConfigLoader.loadNameConfig

  /** Procedurally create a new GameState.
    * may fail if config values are inconsistent.
    *
    * @return new GameState
    */
  def createNewGameState: DefaultGameState = {
    val crafts = CraftPrototype.generateCraft
    val quests = createQuests(crafts)
    val hunter = createHunter(crafts)
    val questLogic = new DefaultQuestLogic(new DefaultEEResolver)
    new DefaultGameState(hunter, quests, crafts, questLogic)
  }

  private def createHunter(crafts: Crafts): Hunter = {
    val hunter = new DefaultHunter(config.getHunterName)
    val itemTypesFirstLevel = crafts.getNonMaterials(config.getLevelMin).distinct
    val items = itemTypesFirstLevel.map(i => DefaultItem.createItem(i))
    hunter.getInventory.addItems(items: _*)
    for (item <- items) {
      hunter.getInventory.equipItem(item.getUniqueId)
    }
    hunter
  }

  private def createQuests(crafts: Crafts): Seq[Quest] = {
    val monsterNamePool = new DefaultRandomPool[String](nameConfig.getMonsters)
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
        quests = quests :+ DefaultQuest.createQuest(level, loot, monsterNamePool.next.getOrElse("name pool empty"))
      }
    }
    quests
  }
}
