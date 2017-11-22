package game.gamestate

import game.config.ConfigLoader
import game.item.craft.{CraftPrototype, CraftsTrait}
import game.item.{Item, ItemType}
import game.quest.{Quest, QuestTrait}
import game.unit.Hunter

/** Holds all game entities and states.
  * Created by nol on 11/11/17.
  */
class GameState(hunter: Hunter, quests: Seq[QuestTrait], crafts: CraftsTrait) extends GameStateTrait {

  private var questsCompletedIds: Set[Long] = Set.empty

  def getHunter: Hunter = hunter

  def getQuests: Seq[QuestTrait] = quests

  def getCrafts: CraftsTrait = crafts

  def setCompleted(questId: Long): Unit = {
    questsCompletedIds += questId
  }

  def isCompletedQuest(questId: Long): Boolean = questsCompletedIds.contains(questId)
}


object GameState {

  private val config = ConfigLoader.loadGameConfig

  /** Procedurally create a new GameState.
    * may fail if config values are inconsistent.
    *
    * @return new GameState
    */
  def createNewGameState: GameState = {
    val hunter = createHunter
    val crafts = CraftPrototype.generateCraft
    val quests = createQuests(crafts)
    val itemTypesFirstLevel = crafts.getNonMaterials(config.getLevelMin).distinct
    hunter.getInventory.addItems(itemTypesFirstLevel.map(i => Item.createItem(i)): _*)
    new GameState(hunter, quests, crafts)
  }

  private def createHunter: Hunter = {
    val hunter = new Hunter("unnamed")
    val weapon = Item.createItem(ItemType.createWeapon(config.getLevelMin, 500))
    hunter.getInventory.addItems(weapon)
    hunter.getInventory.equipItem(weapon.getUniqueId)
    hunter
  }

  private def createQuests(crafts: CraftsTrait): Seq[QuestTrait] = {
    var quests = Seq.empty[QuestTrait]
    for (level <- config.getLevelMin until config.getLevelMax) {
      val lootAtLevel = crafts.getMaterials(level).distinct
      val lootSizeAtLevel: Int = lootAtLevel.size
      val questsAtLevel: Int = Math.min(lootSizeAtLevel, config.getQuestsPerLevel)
      assert(questsAtLevel > 0, level)
      val lootPerQuest: Int = lootSizeAtLevel / questsAtLevel
      assert(lootPerQuest >= 1)
      for (q <- 0 until questsAtLevel) {
        val loot = lootAtLevel.slice(q * lootPerQuest, q * lootPerQuest + lootPerQuest)
        quests = quests :+ Quest.createQuest(level, loot)
      }
    }
    quests
  }
}
