package printer

import quest.QuestResult

/**
  * Created by nol on 11/11/17.
  */
object Printer {

  def printQuestResult(questResult: QuestResult): Unit = {
    if (questResult.isMonsterSlain) {
      println("monster was slain")
    }
    if (questResult.isHunterDefeated) {
      println("hunter was defeated")
    }
    if (!questResult.isMonsterSlain & !questResult.isHunterDefeated) {
      println("quest max duration reached")
    }
  }

}
