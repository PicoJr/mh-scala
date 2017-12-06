package game.item

/**
  * Created by nol on 06/12/17.
  */
trait OpenEnum[T] {
  def getValues: Seq[T]
}
