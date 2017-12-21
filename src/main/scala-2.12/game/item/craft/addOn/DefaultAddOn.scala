package game.item.craft.addOn

import game.config.{DefaultGameConfig, GameConfig}
import game.util.Procedural

/**
  * Created by nol on 21/12/17.
  */
abstract class DefaultAddOn extends AddOn {

  protected val gameConfig: GameConfig = DefaultGameConfig.getGameConfig

  protected def getRandomSlot: Int = Procedural.pickRandom(1, 2, 3).get

  protected def getRandomValue(level: Int, base: Int): Int = {
    Procedural.getRandomValue(level, base, gameConfig.getHunterStatsGrowth, gameConfig.getPercentageVariation)
  }
}
