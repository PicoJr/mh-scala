package game.quest

/** Holds the result of a quest.
  * Created by nol on 06/11/17.
  */
trait QuestResult {

  /** Test if monster is slain
    *
    * @return monster was slain during quest
    */
  def isMonsterSlain: Boolean

  /** Test if hunter is defeated
    *
    * @return hunter was defeated during quest
    */
  def isHunterDefeated: Boolean

  /** Same as isMonsterSlain && !isHunterDefeated
    *
    * @return quest is successful for hunter
    */
  def isSuccessful: Boolean = isMonsterSlain && !isHunterDefeated
}
