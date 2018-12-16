package typeclass.derivation

sealed trait MyADT

case class A(x: String) extends MyADT
//for recursiveness, see how the automatic type class derivation handles this
case class B(anotherADT: MyADT, y: String) extends MyADT