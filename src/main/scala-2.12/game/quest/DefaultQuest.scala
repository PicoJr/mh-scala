package game.quest

import game.item.ItemType
import game.unit.Monster

/** Default quest.
  * Created by nol on 05/11/17.
  */
class DefaultQuest[TItemType <: ItemType](questMonster: Monster, questLoot: Seq[TItemType], questLevel: Int, questUniqueId: Long) extends Quest[TItemType] {


  override def getUniqueId: Long = questUniqueId

  override val loot: Seq[TItemType] = questLoot
  override val monster: Monster = questMonster
  override val level: Int = questLevel
}
