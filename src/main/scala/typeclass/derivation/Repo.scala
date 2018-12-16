package typeclass.derivation

import shapeless.Generic

case class Repo(name: String, author: String, stars: Int, comments: List[Comment])

object Repo {
  implicit def prettyPrinterRepo: PrettyPrinter[Repo] = {
    val generic = Generic[Repo]
    val repr = PrettyPrinter[generic.Repr]

    PrettyPrinter.instance(repo => repr.print(generic.to(repo)))
  }
}