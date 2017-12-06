package game.unit

/**
  * Created by nol on 06/12/17.
  */
class DefaultHunterFactory extends HunterFactory {
  override def createHunter: Hunter = new DefaultHunter()
}
