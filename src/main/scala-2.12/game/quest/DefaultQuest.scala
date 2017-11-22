package game.quest

import game.id.DefaultIdSupplier
import game.item.{DefaultItem, Item, ItemType}
import game.unit.{Monster, RandomMonsterFactory}

/** Rewards hunter with loot when monster is slain
  * Created by nol on 05/11/17.
  */
class DefaultQuest(monster: Monster, loot: Seq[ItemType]) extends Quest {
  private final val uniqueID: Long = DefaultQuest.questIdSupplier.getNextUniqueId

  def getUniqueId: Long = uniqueID

  def getMonster: Monster = monster

  def createLoot: Seq[Item] = loot.map(i => DefaultItem.createItem(i))
}

object DefaultQuest {

  private val questIdSupplier = new DefaultIdSupplier

  def createQuest(level: Int, loot: Seq[ItemType]): Quest = {
    val monster = RandomMonsterFactory.generateMonster(level)
    new DefaultQuest(monster, loot)
  }
}
