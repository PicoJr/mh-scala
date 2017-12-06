package game.quest

import game.item.ItemType

/**
  * Created by nol on 06/12/17.
  */
trait QuestFactory {
  def createQuest(level: Int, loot: Seq[ItemType]): Quest

}
