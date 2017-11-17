package game.quest

/** Holds the result of a quest.
  * Created by nol on 06/11/17.
  */
trait QuestResult {

  def isMonsterSlain: Boolean

  def isHunterDefeated: Boolean
}
