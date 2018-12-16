import java.util.Date

import shapeless.{::, HList, HNil}
import typeclass.derivation.PrettyPrinter

object Main extends App {
  val prettyPrinter = PrettyPrinter[String :: String :: Date :: Int :: HNil]

  println(prettyPrinter.print(HList("a", "b", new Date(), 5)))
}
