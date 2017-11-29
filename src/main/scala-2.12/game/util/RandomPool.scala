package game.util

/** Generic Random Pool
  * Created by nol on 29/11/17.
  */
trait RandomPool[T] {

  /** Returns next random object from pool if any and remove it from pool else None
    *
    * @return next object from pool if any else None
    */
  def next: Option[T]
}
