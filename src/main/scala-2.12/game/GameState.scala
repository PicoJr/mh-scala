package game

import config.Config
import craft.Crafts
import item.ItemType
import quest.Quest
import unit.Hunter

/**
  * Created by nol on 11/11/17.
  */
class GameState(hunter: Hunter, quests: Seq[Quest], crafts: Crafts) {

  private var questsCompletedIds: Set[Long] = Set.empty

  def getHunter: Hunter = hunter

  def getQuests: Seq[Quest] = quests

  def getCrafts: Crafts = crafts

  def completeQuest(questId: Long): Unit = {
    questsCompletedIds += questId
  }

  def isCompletedQuest(questId: Long): Boolean = questsCompletedIds.contains(questId)
}


object GameState {
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
