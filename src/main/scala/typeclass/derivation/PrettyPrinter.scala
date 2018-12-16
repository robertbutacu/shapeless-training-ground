package typeclass.derivation

import java.util.Date

import shapeless.{:+:, ::, CNil, Coproduct, Generic, HList, HNil, Inl, Inr, Lazy}

trait PrettyPrinter[T] {
  def print(input: T): String
}

object PrettyPrinter {
  def apply[T](implicit enc: PrettyPrinter[T]): PrettyPrinter[T] = enc

  def instance[T](f: T => String): PrettyPrinter[T] = { input: T => f(input) }

  //defining instances for basic types that are used in the case classes
  implicit def stringPrinter: PrettyPrinter[String] =
    instance(s => s + "\t\t")

  implicit def intPrinter: PrettyPrinter[Int] =
    instance(i => i + "\t\t")

  implicit def datePrinter: PrettyPrinter[Date] =
    instance(d => d.toString + "\t\t")

  implicit def listPrinter[A](implicit impl: PrettyPrinter[A] ): PrettyPrinter[List[A]] =
    instance{
      l =>
        l.map(v => impl.print(v)).foldLeft("[")((acc, curr) => acc ++ "\t" ++ curr ++ ",\t") ++ "]"
    }

  //defining instances for HList elements
  implicit val hNilPrinter: PrettyPrinter[HNil] =
    instance(_ => "")

  implicit val cNilPrinter: PrettyPrinter[CNil] = instance(_ => throw new Exception("Inconceivable!"))

  implicit def hListPrinter[H, T <: HList](implicit
                                  headPrinter: Lazy[PrettyPrinter[H]],
                                  tailPrinter: PrettyPrinter[T]): PrettyPrinter[H :: T] = {
    instance{case h::t => headPrinter.value.print(h) ++ tailPrinter.print(t)}
  }

  implicit def coproductPrettyPrinter[H, T <: Coproduct](implicit h: PrettyPrinter[H], t: PrettyPrinter[T]): PrettyPrinter[H :+: T] = instance {
    case Inl(a) => h.print(a)
    case Inr(b) => t.print(b)
  }

  //defining generic instance for all coproducts - case classes

  implicit def productPrettyPrinter[A, R](implicit gen: Generic.Aux[A, R], repr: Lazy[PrettyPrinter[R]]): PrettyPrinter[A] = {
    PrettyPrinter.instance(a => repr.value.print(gen.to(a)))
  }
}
