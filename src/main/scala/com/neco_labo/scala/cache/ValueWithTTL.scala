package com.neco_labo.scala.cache

import java.time.{Instant, Duration}

/**
 *
 */
class ValueWithTTL[A](value: A, ttl: Duration, cachedTime: Instant = Instant.now()) {
  val expire = cachedTime.plus(ttl)
  def expired(now: Instant = Instant.now()): Boolean = ttl != Duration.ZERO && expire.isBefore(now)
  def get(now: Instant = Instant.now()): Option[A] = if (expired(now)) None else Some(value)
}

