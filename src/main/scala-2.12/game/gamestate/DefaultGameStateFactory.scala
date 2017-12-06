package game.gamestate

import game.config.ConfigLoader
import game.item.craft.{CraftFactory, Crafts, DefaultCraftFactory}
import game.item.{DefaultItem, ItemType}
import game.quest._
import game.unit.{DefaultHunterFactory, Hunter, HunterFactory}
import game.util.{DefaultLoopingRandomPool, LoopingRandomPool}

/**
  * Created by nol on 06/12/17.
  */
class DefaultGameStateFactory(craftFactory: CraftFactory,
                              hunterFactory: HunterFactory,
                              questFactory: QuestFactory,
                              questLogic: QuestLogic
                             ) extends GameStateFactory {
  def this(monsterNamePool: LoopingRandomPool[String]) = {
    this(new DefaultCraftFactory, new DefaultHunterFactory, new DefaultQuestFactory(monsterNamePool), new DefaultQuestLogic())
  }

  def this() = {
    this(new DefaultLoopingRandomPool(DefaultGameStateFactory.nameConfig.getMonsters))
  }

  override def createGameState: GameState = {
    val crafts = craftFactory.generateCraft
    val hunter = createDefaultHunter(crafts)
    val quests = createQuests(crafts)
    new DefaultGameState(hunter, quests, crafts, questLogic)
  }

  private def createDefaultHunter(crafts: Crafts): Hunter = {
    val hunter = hunterFactory.createHunter
    val itemTypesFirstLevel = crafts.getNonMaterials(DefaultGameStateFactory.config.getLevelMin).distinct
    val items = itemTypesFirstLevel.map(i => DefaultItem.createItem(i))
    hunter.getInventory.addItems(items: _*)
    for (item <- items) {
      hunter.getInventory.equipItem(item.getUniqueId)
    }
    hunter
  }

  private def createQuestsAtLevel(crafts: Crafts, level: Int): Seq[Quest] = {
    var quests = Seq.empty[Quest]
    val lootAtLevel = crafts.getMaterials(level).distinct
    val lootPool = new DefaultLoopingRandomPool[ItemType](lootAtLevel)
    // +1 => lootPerQuest * quests >= lootAtLevel
    val lootPerQuest = math.max(1, (lootAtLevel.size / DefaultGameStateFactory.config.getQuestsPerLevel) + 1)
    for (_ <- 0 until DefaultGameStateFactory.config.getQuestsPerLevel) {
      var loot = Seq.empty[ItemType]
      for (_ <- 0 until lootPerQuest) {
        loot = loot :+ lootPool.next
      }
      quests = quests :+ questFactory.createQuest(level, loot)
    }
    quests
  }

  private def createQuests(crafts: Crafts): Seq[Quest] = {
    var quests = Seq.empty[Quest]
    for (level <- DefaultGameStateFactory.config.getLevelMin until DefaultGameStateFactory.config.getLevelMax) {
      quests = quests ++ createQuestsAtLevel(crafts, level)
    }
    quests
  }


}

private object DefaultGameStateFactory {
  private val config = ConfigLoader.loadGameConfig
  private val nameConfig = ConfigLoader.loadNameConfig
}
