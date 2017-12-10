package game.quest

import game.id.Identifiable
import game.item.Item
import game.unit.Monster

/** Rewards hunter with loot when monster is slain
  * Created by nol on 22/11/17.
  */
trait Quest extends Identifiable {

  /** Return monster to hunt during quest
    *
    * @return monster to hunt during quest
    */
  def getMonster: Monster

  /** Returns items obtained from quest if success
    * Calling this method several times
    * should give the same itemTypes but items with different id
    *
    * @return <b>items</b> obtained from quest if success
    */
  def createLoot: Seq[Item]

  /** Returns quest level (how tough the quest is to complete)
    *
    * @return quest level
    */
  def getLevel: Int
}
