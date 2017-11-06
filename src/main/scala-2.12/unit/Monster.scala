package unit

import item.Item

/**
  * Created by nol on 05/11/17.
  */
class Monster(name: String, life: Int, loot: Seq[Item]) extends GameUnitDefault(name: String, life: Int){
  val items : Seq[Item] = loot

  def this(name: String, life: Int) {
    this(name, life, Seq.empty)
  }

  def getLoot: Seq[Item] = items
}
