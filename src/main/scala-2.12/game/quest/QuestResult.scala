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

  /** Returns damage dealt by Monster to Hunter during quest
    *
    * @return damage dealt by Monster during quest
    */
  def getDamageDealtByMonster: Double

  /** Returns damage dealt by Hunter to Monster during quest
    *
    * @return damage dealt by Hunter during quest
    */
  def getDamageDealtByHunter: Double

  /** Test if hunter is defeated
    *
    * @return hunter was defeated during quest
    */
  def isHunterDefeated: Boolean

  /** Get time elapsed during quest
    *
    * @return time elapsed (>0)
    */
  def getTimeElapsed: Double

  /** Same as isMonsterSlain && !isHunterDefeated
    *
    * @return quest is successful for hunter
    */
  def isSuccessful: Boolean = isMonsterSlain && !isHunterDefeated
}
