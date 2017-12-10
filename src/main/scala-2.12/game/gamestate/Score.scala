package game.gamestate

/** Holds scoring data. Updated during game.
  * Created by nol on 25/11/17.
  */
trait Score {
  private var questsAttempts: Int = 0
  private var questsFailures: Int = 0
  private var questsSuccesses: Int = 0

  /** Increment number of quests attempts by 1. */
  def incrQuestAttempts(): Unit = questsAttempts += 1

  /** Increment number of quests failures by 1. */
  def incrQuestFailures(): Unit = questsFailures += 1

  /** Increment number of quests successes by 1. */
  def incrQuestSuccesses(): Unit = questsSuccesses += 1

  /** Get quests attempts.
    *
    * @return quest attempts (>= 0)
    */
  def getQuestAttempts: Int = questsAttempts

  /** Get quests failures.
    *
    * @return quests failures (>=0)
    */
  def getQuestsFailures: Int = questsFailures

  /** Get quests successes.
    *
    * @return quests successes (>= 0)
    */
  def getQuestsSuccesses: Int = questsSuccesses

}
