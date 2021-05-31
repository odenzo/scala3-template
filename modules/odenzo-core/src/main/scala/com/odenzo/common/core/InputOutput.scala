package com.odenzo.common.core

import cats.effect.*
import cats.effect.syntax.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import os.*

/** Generally I use os-lib for this now */
object InputOutput {

  def writeToFile(txt: String) = {
    os.write(os.pwd / "foo.txt", txt)
  }

  /** Loads text file returnuing list of line content with EOL delimeters
    */
  def loadTextFile(filename: String): IO[List[String]] = {
    import scala.io.*
    val acquire          = IO(Source.fromFile(filename)(Codec.UTF8))
    def close(s: Source) = IO(s.close())

    Resource
      .make(acquire)(close(_))
      .use { src =>
        IO(src.getLines().toList)
      }

  }

  def inputStream(f: File): Resource[IO, FileInputStream] =
    Resource.make {
      IO(new FileInputStream(f)) // build
    } { inStream =>
      IO(inStream.close()).handleErrorWith(_ => IO.unit) // release
    }

  /** Resource Manaaged (File) OutputStream
    */
  def outputStream(f: File): Resource[IO, FileOutputStream] = {
    Resource.make {
      IO(new FileOutputStream(f))
    } { outStream =>
      IO(outStream.close()).handleErrorWith(_ => IO.unit) // release
    }
  }
}
