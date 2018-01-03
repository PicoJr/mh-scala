package game.item.element

import game.config.{DefaultGameConfig, GameConfig}
import game.item.element.Effectiveness.Effectiveness

import scala.collection.mutable

/**
  * Created by nol on 22/11/17.
  */
class DefaultEEResolverBuilder(gameConfig: GameConfig = DefaultGameConfig.getInstance) {

  private var ee = mutable.Map.empty[(ElementType, ElementType), Effectiveness.Effectiveness]

  def withEE(elementType: ElementType, other: ElementType, effectiveness: Effectiveness): DefaultEEResolverBuilder = {
    ee += (elementType, other) -> effectiveness
    this
  }

  def build: EEResolver = {
    new EEResolver {

      override def effectiveness(elementType: ElementType, other: ElementType): Effectiveness = {
        ee.getOrElse((elementType, other), Effectiveness.NORMAL)
      }

      override def multiplier(elementType: ElementType, other: ElementType): Double = {
        Effectiveness.multiplier(effectiveness(elementType, other), gameConfig)
      }
    }
  }

}
