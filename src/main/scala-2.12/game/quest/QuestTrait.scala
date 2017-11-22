package game.quest

import game.id.Identifiable
import game.item.ItemTrait
import game.unit.Monster

/**
  * Created by nol on 22/11/17.
  */
trait QuestTrait extends Identifiable {

  def getMonster: Monster

  def createLoot: Seq[ItemTrait]
}
