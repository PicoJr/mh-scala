package game.quest

import game.id.Identifiable
import game.item.ItemType
import game.unit.Monster

/** Rewards hunter with loot when monster is slain
  * Created by nol on 22/11/17.
  */
trait Quest extends Identifiable {

  /** monster to hunt during quest */
  val monster: Monster

  /** quest level (how tough the quest is to complete) */
  val level: Int

  /** <b>item types</b> obtained from quest if success */
  val loot: Seq[ItemType]

}
