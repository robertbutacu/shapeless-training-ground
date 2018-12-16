import java.util.Date

import shapeless.{::, HList, HNil}
import typeclass.derivation.{PrettyPrinter, Repo, Comment}

object Main extends App {
  val prettyPrinter = PrettyPrinter[String :: String :: Date :: Int :: HNil]

  println(prettyPrinter.print(HList("a", "b", new Date(), 5)))

  println(PrettyPrinter[Repo].print(Repo("a", "b", 5, List(Comment(new Date(), "bc", "fasdf")))))
}
