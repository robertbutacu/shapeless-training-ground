package typeclass.derivation

sealed trait MyADT

case class A(x: String) extends MyADT
case class B(anotherADT: MyADT, y: String) extends MyADT