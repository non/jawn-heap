package test

import org.scalatest._

import jawnHeap._

class HeapSpec extends WordSpec with Matchers {

  "The async parser" must {

    "parse an infinite stream without blowing up the heap" in {
      val jsonObject = """
        { 
          "a": "value a1",
          "b": 
            {
              "c": "value c1",
              "d": "value d1"
            }
          ,
          "e": [
            {
              "f": "value f11",
              "g": "value g11"
            },
            {
              "f": "value f12",
              "g": "value g12"
            },
            {
              "f": "value f13",
              "g": "value g13"
            }
          ],
          "i" : [
            {
              "j" : [ 
                {
                  "k": "value k1",
                  "l": "value l1"
                }
              ]
            }
          ],
          "m" : ["value m11", "value m12"]
        }
      """
      Parser.streamConversion("[" #:: Stream.continually[String](jsonObject + ","))
    }
  }
}