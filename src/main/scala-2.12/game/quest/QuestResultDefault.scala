package game.quest

/**
  * Created by nol on 22/11/17.
  */
class QuestResultDefault(monsterSlain: Boolean, hunterDefeated: Boolean) extends QuestResult {

  def isMonsterSlain: Boolean = monsterSlain

  def isHunterDefeated: Boolean = hunterDefeated

}
