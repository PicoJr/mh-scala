import item.Item
import unit.Monster

/**
  * Created by nol on 05/11/17.
  */
trait Quest {
  def getMonster: Monster
  def getLoot: Seq[Item]
  def getMaxDuration: Int
}
