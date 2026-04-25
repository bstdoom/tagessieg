package io.github.bstdoom.tagessieg.infrastructure

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test


class EchoFormatTest {

  data class TestOutput(val message: String)

  @Serializable
  data class TestOutputSerializable(val message: String)

  @Test
  fun name() {
    val string = EchoFormat.JSON.encode(TestOutputSerializable("test"))
    println(string)
  }
  @Test
  fun name1() {
    assertThatThrownBy { EchoFormat.JSON.encode(TestOutput("test")) }
      .isInstanceOf(SerializationException::class.java)
  }
}
