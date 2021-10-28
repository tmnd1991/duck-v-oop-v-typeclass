package test

import org.openjdk.jmh.annotations.Benchmark

import scala.io.Source
import scala.util.Random

/** Based on
  * [[https://github.com/ktoso/sbt-jmh/blob/master/sbt-jmh-tester/src/main/scala/test/TestBenchmark.scala]]
  */

object Experiment {
  trait WithId[A] {
    def id(a: A): String
  }
  trait Identifiable {
    val id: String
  }
  object WithId {
    implicit val fooId: WithId[Foo] = new WithId[Foo] {
      override def id(a: Foo) = a.id
    }
    implicit val barId: WithId[Bar] = new WithId[Bar] {
      override def id(a: Bar) = a.id
    }
  }
  sealed trait FooBar extends Product with Serializable
  case class Foo(id: String) extends FooBar with Identifiable
  case class Bar(id: String) extends FooBar with Identifiable
  def buildOne(): FooBar = {
    if (Random.nextBoolean) {
      Foo(Random.nextString(5))
    } else {
      Bar(Random.nextString(5))
    }
  }

  def buildOneId(): Identifiable = {
    if (Random.nextBoolean) {
      Foo(Random.nextString(5))
    } else {
      Bar(Random.nextString(5))
    }
  }

  def oop(x: Identifiable): String = {
    x.id
  }

  def duck(x: { val id: String }): String = x.id

  def typeclass[A: WithId](x: A) = implicitly[WithId[A]].id(x)

  def buildIterator(): Iterator[FooBar] = {
    Iterator
      .from(1)
      .takeWhile(_ < 100000)
      .map(_ => Experiment.buildOne)
  }

  def buildIteratorId(): Iterator[Identifiable] = {
    Iterator
      .from(1)
      .takeWhile(_ < 100000)
      .map(_ => Experiment.buildOneId)
  }

}
class TestBenchmark {
  @Benchmark
  def typeclass(): Int =
    Experiment.buildIterator
      .map {
        case f: Experiment.Foo => Experiment.typeclass(f)
        case b: Experiment.Bar => Experiment.typeclass(b)
      }
      .count(_.length == 5)

  @Benchmark
  def oop(): Int =
    Experiment.buildIterator
      .map {
        case f: Experiment.Foo => Experiment.oop(f)
        case b: Experiment.Bar => Experiment.oop(b)
      }
      .count(_.length == 5)

  @Benchmark
  def duck(): Int =
    Experiment.buildIterator
      .map {
        case f: Experiment.Foo => Experiment.duck(f)
        case b: Experiment.Bar => Experiment.duck(b)
      }
      .count(_.length == 5)
}
