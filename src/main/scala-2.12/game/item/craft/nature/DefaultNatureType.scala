package game.item.craft.nature

import game.config.{DefaultGameConfig, GameConfig}
import game.item.{AbstractItemTypeFactory, DefaultItemTypeFactory}
import game.util.Procedural

/**
  * Created by nol on 21/12/17.
  */
abstract class DefaultNatureType extends NatureType {

  protected val gameConfig: GameConfig = DefaultGameConfig.getGameConfig

  protected val itemTypeFactory: AbstractItemTypeFactory = DefaultItemTypeFactory.getDefaultItemFactory

  protected def getRandomSlot: Int = Procedural.pickRandom(1, 2, 3).get

  protected def getRandomValue(level: Int, base: Int): Int = {
    Procedural.getRandomValue(level, base, gameConfig.getHunterStatsGrowth, gameConfig.getPercentageVariation)
  }

}
