package game.gamestate

import game.config.ConfigLoader
import game.id.DefaultIdSupplier
import game.item.craft.{Crafts, DefaultCraftFactory}
import game.item.{DefaultItem, ItemType}
import game.quest._
import game.unit.{DefaultHunter, DefaultMonsterFactory, Hunter}
import game.util.DefaultLoopingRandomPool

/**
  * Created by nol on 06/12/17.
  */
class DefaultGameStateFactory(crafts: Crafts,
                              hunter: Hunter,
                              questLogic: QuestLogic
                             ) {
  def this() = {
    this(
      new DefaultCraftFactory().generateCraft,
      new DefaultHunter(),
      new DefaultQuestLogic())
  }

  def createGameState: GameState = {
    val hunter = createDefaultHunter(crafts)
    val quests = createQuests(crafts)
    new DefaultGameState(hunter, quests, crafts, questLogic)
  }

  private def createDefaultHunter(crafts: Crafts): Hunter = {
    val hunter = new DefaultHunter()
    val itemTypesFirstLevel = crafts.getNonMaterials(DefaultGameStateFactory.config.getLevelMin).distinct
    val items = itemTypesFirstLevel.map(i => DefaultItem.createItem(i))
    hunter.getInventory.addItems(items: _*)
    for (item <- items) {
      hunter.getInventory.tryEquipItem(item.getUniqueId, force = false)
    }
    hunter
  }

  private def createQuests(crafts: Crafts): Seq[Quest] = {
    val monsterNamePool = new DefaultLoopingRandomPool(DefaultGameStateFactory.nameConfig.getMonsters)
    val idSupplier = new DefaultIdSupplier()
    val monsterFactory = new DefaultMonsterFactory()
    var quests = Seq.empty[Quest]
    for (level <- DefaultGameStateFactory.config.getLevelMin until DefaultGameStateFactory.config.getLevelMax) {
      val lootAtLevel = crafts.getMaterials(level).distinct
      val lootPool = new DefaultLoopingRandomPool[ItemType](lootAtLevel)
      // +1 => lootPerQuest * quests >= lootAtLevel
      val lootPerQuest = math.max(1, (lootAtLevel.size / DefaultGameStateFactory.config.getQuestsPerLevel) + 1)
      for (_ <- 0 until DefaultGameStateFactory.config.getQuestsPerLevel) {
        var loot = Seq.empty[ItemType]
        for (_ <- 0 until lootPerQuest) {
          loot = loot :+ lootPool.next
        }
        val monster = monsterFactory.createMonster(level, monsterNamePool.next)
        quests = quests :+ new DefaultQuest(monster, loot, level, idSupplier.getNextUniqueId)
      }
    }
    quests
  }


}

private object DefaultGameStateFactory {
  private val config = ConfigLoader.loadGameConfig
  private val nameConfig = ConfigLoader.loadNameConfig
}
