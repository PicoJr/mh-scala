package game.id

/** Something that is identified by a unique id.
  * Created by nol on 14/11/17.
  */
trait Identifiable {
  /** Get unique id
    *
    * @return unique id
    */
  def getUniqueId: Long
}
