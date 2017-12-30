package game.item.craft

/**
  * Created by nol on 20/11/17.
  */
class DescriptionBuilder(nature: String) {
  private var adjectives: Seq[String] = Seq.empty

  def getDescription: String = {
    val description = new StringBuilder()
    for (adjective <- adjectives) {
      description.append(adjective).append(" ")
    }
    description.append(nature)
    description.toString()
  }

  def getNature: String = nature

  def getAdjectives: Seq[String] = adjectives

  def addAdjective(adjective: String): DescriptionBuilder = {
    this.adjectives = adjectives :+ adjective
    this
  }
}
