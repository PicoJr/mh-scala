package game.quest

/**
  * Created by nol on 22/11/17.
  */
class QuestResultDefault(monsterSlain: Boolean, hunterDefeated: Boolean) extends QuestResult {

  override def isMonsterSlain: Boolean = monsterSlain

  override def isHunterDefeated: Boolean = hunterDefeated

}
