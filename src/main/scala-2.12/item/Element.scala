package item

/**
  * Created by nol on 06/11/17.
  */
class Element(i: Item, e: Element) extends ItemDecorator(i: Item) {
  def getElement: Element = e
}
