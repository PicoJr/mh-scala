package game.quest

import game.item.{AbstractItemFactory, DefaultItemFactory, Item, ItemType}
import game.unit.Monster

/** Default quest.
  * Created by nol on 05/11/17.
  */
class DefaultQuest(questMonster: Monster, loot: Seq[ItemType], questLevel: Int, questUniqueId: Long, itemFactory: AbstractItemFactory = DefaultItemFactory.getDefaultItemFactory) extends Quest {


  override def getUniqueId: Long = questUniqueId

  override def createLoot: Seq[Item] = loot.map(i => itemFactory.createItem(i))

  override val monster: Monster = questMonster
  override val level: Int = questLevel
}
