package config

/**
  * Created by nol on 05/11/17.
  */
object Config {
  def getQuestMaxDuration: Int = 100

  def getHunterLife: Int = 100

  def getLevelMax: Int = 5

  def getQuestsAtLevel: Int => Int = (level: Int) => (getLevelMax + 1) - level
}
