package game.gamestate

/**
  * Created by nol on 25/11/17.
  */
trait Score {
  private var questsAttempts: Int = 0
  private var questsFailures: Int = 0
  private var questsSuccesses: Int = 0

  def incrQuestAttempts(): Unit = questsAttempts += 1

  def incrQuestFailures(): Unit = questsFailures += 1

  def incrQuestSuccesses(): Unit = questsSuccesses += 1

  def getQuestAttempts: Int = questsAttempts

  def getQuestsFailures: Int = questsFailures

  def getQuestsSuccesses: Int = questsSuccesses

}
