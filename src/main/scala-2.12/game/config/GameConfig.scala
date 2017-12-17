package game.config

/**
  * Created by nol on 17/12/17.
  */
trait GameConfig {

  def getLevelMin: Int

  def getLevelMax: Int

  def getDamageMin: Int

  def getQuestDurationMax: Int

  def getQuestsPerLevel: Int

  def getPercentageVariation: Int

  def getPercentageBonus: Int

  def getDamageBase: Int

  def getArmorBase: Int

  def getArmorBonusBase: Int

  def getDamageBonusBase: Int

  def getHunterStatsGrowth: Double

  def getHunterName: String

  def getHunterLifeMax: Int

  def getMonsterStatsGrowth: Double

  def getMonsterLifeBase: Int

  def getMonsterArmorBase: Int

  def getMonsterDamageBase: Int

  def getNormalMultiplier: Double

  def getIneffectiveMultiplier: Double

  def getEffectiveMultiplier: Double

  def getMonsters: Seq[String]

}
