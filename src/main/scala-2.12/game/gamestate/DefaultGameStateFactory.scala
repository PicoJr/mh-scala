package game.gamestate

import game.config.{DefaultGameConfig, GameConfig}
import game.id.DefaultIdSupplier
import game.item.craft.{Crafts, DefaultCraftFactory}
import game.item.{AbstractItemFactory, DefaultItemFactory, ItemType}
import game.quest._
import game.unit.{DefaultHunter, DefaultMonsterFactory, Hunter}
import game.util.DefaultLoopingRandomPool

/**
  * Created by nol on 06/12/17.
  */
class DefaultGameStateFactory(crafts: Crafts,
                              hunter: Hunter,
                              gameConfig: GameConfig,
                              itemFactory: AbstractItemFactory
                             ) {
  def this() = {
    this(
      new DefaultCraftFactory().generateCraft,
      new DefaultHunter(),
      DefaultGameConfig.getGameConfig,
      DefaultItemFactory.getDefaultItemFactory
    )
  }

  def createGameState: GameState = {
    val hunter = createDefaultHunter(crafts)
    val quests = createQuests(crafts)
    new DefaultGameState(hunter, quests, crafts)
  }

  private def createDefaultHunter(crafts: Crafts): Hunter = {
    val hunter = new DefaultHunter()
    val itemTypesFirstLevel = crafts.getNonMaterials(gameConfig.getLevelMin).distinct
    val items = itemTypesFirstLevel.map(i => itemFactory.createItem(i))
    hunter.getInventory.addItems(items: _*)
    for (item <- items) {
      hunter.getInventory.tryEquipItem(item.getUniqueId, force = false)
    }
    hunter
  }

  private def createQuests(crafts: Crafts): Seq[Quest] = {
    val monsterNamePool = new DefaultLoopingRandomPool(gameConfig.getMonsters)
    val idSupplier = new DefaultIdSupplier()
    val monsterFactory = new DefaultMonsterFactory()
    var quests = Seq.empty[Quest]
    for (level <- gameConfig.getLevelMin until gameConfig.getLevelMax) {
      val lootAtLevel = crafts.getMaterials(level).distinct
      val lootPool = new DefaultLoopingRandomPool[ItemType](lootAtLevel)
      // +1 => lootPerQuest * quests >= lootAtLevel
      val lootPerQuest = math.max(1, (lootAtLevel.size / gameConfig.getQuestsPerLevel) + 1)
      for (_ <- 0 until gameConfig.getQuestsPerLevel) {
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
