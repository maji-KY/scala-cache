package com.neco_labo.scala

import org.specs2.mutable.Specification

/**
 *
 */
class CacheSpec extends Specification {

  "値を設定" should {

    "普通に設定出来る" in {
      val c = new Cache[String]
      c.set("some", "hello")
      c.get("some").get mustEqual "hello"
    }

    "値の計算方法を渡して設定できる" in {
      val c = new Cache[String]
      c.set("some", new c.Lambda {
        override def apply(k: String): String = "hello"
      })
      c.get("some").get mustEqual "hello"
    }

    "値の計算方法を渡した場合は一回しか計算されない" in {
      val c = new Cache[String]
      var count = 0
      def set() = {
        c.set("some", new c.Lambda {
          override def apply(k: String): String = {
            count += 1
            "set count " + count
          }
        })
      }
      "同時にセットしてもOK1" in {
        set()
        c.get("some").get mustEqual "set count 1"
        count mustEqual 1
      }
      "同時にセットしてもOK2" in {
        set()
        c.get("some").get mustEqual "set count 1"
        count mustEqual 1
      }
      "同時にセットしてもOK3" in {
        set()
        c.get("some").get mustEqual "set count 1"
        count mustEqual 1
      }


    }

  }

  "値を取得" should {

    "キーが有る場合にはそれが取得できる" in {
      val c = new Cache[String]
      c.set("some", "hello")
      c.get("some").get mustEqual "hello"
    }

    "キーが存在しない時にはNoneが返る" in {

      val c = new Cache[String]
      c.get("none") mustEqual None

    }

  }

}
