package game.gamestate

import game.config.ConfigLoader
import game.item.craft.{CraftPrototype, Crafts}
import game.item.element.DefaultEEResolver
import game.item.{DefaultItem, ItemType}
import game.quest._
import game.unit.{DefaultHunter, Hunter}
import game.util.DefaultLoopingRandomPool

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
  private val hunterConfig = ConfigLoader.loadHunterConfig

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
    val hunter = new DefaultHunter(hunterConfig.getHunterName)
    val itemTypesFirstLevel = crafts.getNonMaterials(config.getLevelMin).distinct
    val items = itemTypesFirstLevel.map(i => DefaultItem.createItem(i))
    hunter.getInventory.addItems(items: _*)
    for (item <- items) {
      hunter.getInventory.equipItem(item.getUniqueId)
    }
    hunter
  }

  private def createQuests(crafts: Crafts, level: Int): Seq[Quest] = {
    val monsterNamePool = new DefaultLoopingRandomPool[String](nameConfig.getMonsters)
    var quests = Seq.empty[Quest]
    val lootAtLevel = crafts.getMaterials(level).distinct
    val lootPool = new DefaultLoopingRandomPool[ItemType](lootAtLevel)
    // +1 => lootPerQuest * quests >= lootAtLevel
    val lootPerQuest = math.max(1, (lootAtLevel.size / config.getQuestsPerLevel) + 1)
    for (_ <- 0 until config.getQuestsPerLevel) {
      var loot = Seq.empty[ItemType]
      for (_ <- 0 until lootPerQuest) {
        loot = loot :+ lootPool.next
      }
      quests = quests :+ DefaultQuest.createQuest(level, loot, monsterNamePool.next)
    }
    quests
  }

  private def createQuests(crafts: Crafts): Seq[Quest] = {
    var quests = Seq.empty[Quest]
    for (level <- config.getLevelMin until config.getLevelMax) {
      quests = quests ++ createQuests(crafts, level)
    }
    quests
  }
}
