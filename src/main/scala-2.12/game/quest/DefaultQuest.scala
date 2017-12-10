package game.quest

import game.item.{DefaultItem, Item, ItemType}
import game.unit.Monster

/** Default quest.
  * Created by nol on 05/11/17.
  */
class DefaultQuest(monster: Monster, loot: Seq[ItemType], level: Int, questUniqueId: Long) extends Quest {
  override def getUniqueId: Long = questUniqueId

  override def getMonster: Monster = monster

  override def createLoot: Seq[Item] = loot.map(i => DefaultItem.createItem(i))

  override def getLevel: Int = level
}
