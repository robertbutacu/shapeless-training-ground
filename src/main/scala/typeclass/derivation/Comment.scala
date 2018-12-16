package typeclass.derivation

import java.util.Date

import shapeless.Generic


case class Comment(date: Date, text: String, author: String)

object Comment {
  implicit def prettyPrinterInstance: PrettyPrinter[Comment] = {
    val generic = Generic[Comment]
    val repr = PrettyPrinter[generic.Repr]

    PrettyPrinter.instance(comment => repr.print(generic.to(comment)))
  }
}
