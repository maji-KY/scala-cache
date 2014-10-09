package com.neco_labo.scala.cache

import java.time.{Instant, Duration}
import java.util.concurrent.ConcurrentHashMap

/**
 *
 */
class Cache[A] {

  private[this] val store = new ConcurrentHashMap[String, ValueWithTTL[A]]()

  trait Lambda extends java.util.function.Function[String, ValueWithTTL[A]] {
    override def compose[V](before: java.util.function.Function[_ >: V, _ <: String]): java.util.function.Function[V, ValueWithTTL[A]] = ???
    override def andThen[V](after: java.util.function.Function[_ >: ValueWithTTL[A], _ <: V]): java.util.function.Function[String, V] = ???
    protected def withTTL(value: A, ttl: Duration) = new ValueWithTTL[A](value, ttl)
    protected def withoutTTL(value: A) = withTTL(value, Duration.ZERO)
  }

  def set(key: String, value: A): Unit = store.put(key, new ValueWithTTL[A](value, Duration.ZERO))

  def set(key: String, value: A, ttl: Duration): Unit = store.put(key, new ValueWithTTL[A](value, ttl))

  def set(key: String, f: Lambda): Unit = store.computeIfAbsent(key, f)

  def get(key: String)(implicit now: Instant = Instant.now()): Option[A] = store.get(key) match {case null => None; case value => handleValue(key, value)(now);}

  def delete(key: String): Unit = store.remove(key)

  private def handleValue(key: String, value: ValueWithTTL[A])(now: Instant): Option[A] = value.get(now) match {
    case None => delete(key);None
    case a => a
  }

}

