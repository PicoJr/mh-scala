package game.util

/** Generic Looping Random Pool
  * Created by nol on 29/11/17.
  */
trait LoopingRandomPool[T] {

  /** Returns next random object from pool if any and remove it from pool
    * else refill the pool with objects given at initialization
    * and returns next random object from pool.
    *
    * @return next object from pool
    */
  def next: T
}

