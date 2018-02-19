package game.quest

import game.item.ItemType
import game.unit.Monster

/** Default quest.
  * Created by nol on 05/11/17.
  */
class DefaultQuest(questMonster: Monster, questLoot: Seq[ItemType], questLevel: Int, questUniqueId: Long) extends Quest {


  override def getUniqueId: Long = questUniqueId

  override val loot: Seq[ItemType] = questLoot
  override val monster: Monster = questMonster
  override val level: Int = questLevel
}
