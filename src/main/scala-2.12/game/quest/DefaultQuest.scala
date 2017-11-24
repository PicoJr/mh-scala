package game.quest

import game.id.DefaultIdSupplier
import game.item.{DefaultItem, Item, ItemType}
import game.unit.{Monster, RandomMonsterFactory}

/** Rewards hunter with loot when monster is slain
  * Created by nol on 05/11/17.
  */
class DefaultQuest(monster: Monster, loot: Seq[ItemType], level: Int) extends Quest {
  private final val uniqueID: Long = DefaultQuest.questIdSupplier.getNextUniqueId

  override def getUniqueId: Long = uniqueID

  override def getMonster: Monster = monster

  override def createLoot: Seq[Item] = loot.map(i => DefaultItem.createItem(i))

  override def getLevel: Int = level
}

object DefaultQuest {

  private val questIdSupplier = new DefaultIdSupplier

  def createQuest(level: Int, loot: Seq[ItemType]): Quest = {
    val monster = RandomMonsterFactory.generateMonster(level)
    new DefaultQuest(monster, loot, level)
  }
}
