package game.quest

import game.item.{AbstractItemFactory, DefaultItemFactory, Item, ItemType}
import game.unit.Monster

/** Default quest.
  * Created by nol on 05/11/17.
  */
class DefaultQuest(monster: Monster, loot: Seq[ItemType], level: Int, questUniqueId: Long, itemFactory: AbstractItemFactory) extends Quest {

  def this(monster: Monster, loot: Seq[ItemType], level: Int, questUniqueId: Long) {
    this(monster, loot, level, questUniqueId, DefaultItemFactory.getDefaultItemFactory)
  }

  override def getUniqueId: Long = questUniqueId

  override def getMonster: Monster = monster

  override def createLoot: Seq[Item] = loot.map(i => itemFactory.createItem(i))

  override def getLevel: Int = level
}
