package game.item.craft.addOn

import game.config.{DefaultGameConfig, GameConfig}
import game.util.Procedural

/**
  * Created by nol on 21/12/17.
  */
abstract class DefaultAddOn(addOnName: String, val gameConfig: GameConfig = DefaultGameConfig.getInstance) extends AddOn {

  override val name: String = addOnName

  protected def getRandomValue(level: Int, base: Int): Int = {
    Procedural.getRandomValue(level, base, gameConfig.getHunterStatsGrowth, gameConfig.getPercentageVariation)
  }
}
