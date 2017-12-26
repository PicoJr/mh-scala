package game.id

/** Supplies id suitable for an Identifiable
  * Created by nol on 22/11/17.
  */
trait IdSupplier {

  /** Returns new unique id
    *
    * @return new unique id (increasing)
    */
  def getNextUniqueId: IdSupplier.Id

}

object IdSupplier {

  type Id = Long

}
