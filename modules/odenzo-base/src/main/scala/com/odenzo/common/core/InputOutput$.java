package com.odenzo.common.core;object InputOutput {

        /**
         * Loads text file returnuing list of line content with EOL delimeters
         */
        def loadTextFile(filename: String = "/Users/stevef/Desktop/ODENZO-NZ-Ltd-28SEP2019-to-28SEP2020.csv"): IO[List[String]] = {
                import scala.io._
                val acquire          = IO(Source.fromFile(filename)(Codec.UTF8))
                //os.read.lines(filename)
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

        /**
         * Resource Manaaged (File) OutputStream
         */
        def outputStream(f: File): Resource[IO, FileOutputStream] = {
                Resource.make {
                        IO(new FileOutputStream(f))
                        } { outStream =>
                        IO(outStream.close()).handleErrorWith(_ => IO.unit) // release
                        }
                }
        }
