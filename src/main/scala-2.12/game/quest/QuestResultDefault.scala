package game.quest

/**
  * Created by nol on 22/11/17.
  */
class QuestResultDefault() extends QuestResult {
  private var timeElapsed: Double = 0
  private var monsterSlain = false
  private var hunterDefeated = false
  private var damageDealtByMonster: Double = 0
  private var damageDealtByHunter: Double = 0

  def withTimeElapsed(timeElapsed: Double): QuestResultDefault = {
    this.timeElapsed = timeElapsed
    this
  }

  def withMonsterSlain(monsterSlain: Boolean): QuestResultDefault = {
    this.monsterSlain = monsterSlain
    this
  }

  def withHunterDefeated(hunterDefeated: Boolean): QuestResultDefault = {
    this.hunterDefeated = hunterDefeated
    this
  }

  def withDamageDealtByMonster(damage: Double): QuestResultDefault = {
    this.damageDealtByMonster = damage
    this
  }

  def withDamageDealtByHunter(damage: Double): QuestResultDefault = {
    this.damageDealtByHunter = damage
    this
  }

  override def isMonsterSlain: Boolean = monsterSlain

  override def isHunterDefeated: Boolean = hunterDefeated

  override def getTimeElapsed: Double = timeElapsed

  override def getDamageDealtByMonster: Double = damageDealtByMonster

  override def getDamageDealtByHunter: Double = damageDealtByHunter
}
