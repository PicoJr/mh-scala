package game

import config.Config
import item.ItemType
import procedural.Crafts
import quest.Quest
import unit.Hunter

/**
  * Created by nol on 11/11/17.
  */
trait GameState {

  def getCrafts: Crafts

  def getHunter: Hunter

  def getQuests: Seq[Quest]

  def completeQuest(questId: Long): Unit

  def isCompletedQuest(questId: Long): Boolean
}

private class DefaultGameState(hunter: Hunter, quests: Seq[Quest], crafts: Crafts) extends GameState {
  override def getHunter: Hunter = hunter

  override def getQuests: Seq[Quest] = quests

  override def getCrafts: Crafts = crafts

  override def completeQuest(questId: Long): Unit = {} // TODO

  override def isCompletedQuest(questId: Long): Boolean = false
}


object GameState {
  def createNewGameState: GameState = {
    val hunter = createHunter
    val crafts = Crafts.generateCraftRecipes
    val quests = createQuests(crafts)
    val itemTypesFirstLevel = crafts.getNonMaterial(Config.LEVEL_MIN)
    hunter.getInventory.addItems(itemTypesFirstLevel.map(i => ItemType.createItem(i)): _*)
    new DefaultGameState(hunter, quests, crafts)
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
