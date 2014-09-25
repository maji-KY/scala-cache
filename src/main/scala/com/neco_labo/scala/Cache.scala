package com.neco_labo.scala

import java.util.concurrent.ConcurrentHashMap

/**
 *
 */
class Cache[A] {

  private[this] val store = new ConcurrentHashMap[String, A]()

  trait Lambda extends java.util.function.Function[String, A] {
    override def compose[V](before: java.util.function.Function[_ >: V, _ <: String]): java.util.function.Function[V, A] = ???
    override def andThen[V](after: java.util.function.Function[_ >: A, _ <: V]): java.util.function.Function[String, V] = ???
  }

  def set(key: String, value: A): Unit = store.put(key, value)

  def set(key: String, f: Lambda): Unit = store.computeIfAbsent(key, f)

  def get(key: String): Option[A] = store.get(key) match {case null => None; case a => Some(a);}

}


