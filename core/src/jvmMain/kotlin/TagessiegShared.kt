package io.github.bstdoom.tagessieg.infrastructure

import io.github.bstdoom.tagessieg.model.Match
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.csv.Csv
import java.io.File

data object TagessiegShared {
  const val DATA_PATH = "shared/src/jvmMain/resources/data.csv"
  const val TEST_DATA_PATH = "shared/src/jvmTest/resources/test-data.csv"

  fun readMatchDays(file: File): List<Match> {
    if (!file.exists()) return emptyList()
    TODO()//return yaml.decodeFromString<List<MatchDay>>(file.readText())
  }

  fun writeMatchDays(file: File, matches: List<Match>) {
    val sortedMatchDays = matches.sortedBy { it.date }
    TODO()//file.writeText(yaml.encodeToString(sortedMatchDays))
  }


  @OptIn(ExperimentalSerializationApi::class)
  fun main() {
    val csv = Csv { hasHeaderRecord = true }

//    val records = listOf(
////      Person("Neo", "Thomas A. Anderson", Appearance(Gender.MALE, 37, 1.86)),
////      Person("Trinity", null, Appearance(Gender.FEMALE, null, 1.74))
//    )
//    val serialized = csv.encodeToString(ListSerializer(Person.serializer()), records)
//    println(serialized)
//    // nickname,name,appearance.gender,appearance.age,appearance.height
//    // Neo,Thomas A. Anderson,MALE,37,1.86
//    // Trinity,,FEMALE,,1.74
//
//    val input = """
//        nickname,appearance.gender,appearance.height,appearance.age,name
//        Neo,MALE,1.86,37,Thomas A. Anderson
//        Trinity,FEMALE,1.74,,
//    """.trimIndent()
//    val parsed = csv.decodeFromString(ListSerializer(Person.serializer()), input)
//    println(parsed)
//    // [
//    //   Person(nickname=Neo, name=Thomas A. Anderson, appearance=Appearance(gender=MALE, age=37, height=1.86)),
//    //   Person(nickname=Trinity, name=null, appearance=Appearance(gender=FEMALE, age=null, height=1.74))
//    // ]
  }
}
