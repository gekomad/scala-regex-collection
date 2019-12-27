import org.scalatest.funsuite.AnyFunSuite

class ReadFile extends AnyFunSuite {

  ignore("Extract from files") {
    import scala.io.Source
    import com.github.gekomad.regexcollection._
    import com.github.gekomad.regexcollection.Validate.findAll
    import java.io.File
    import scala.util.Try
    def getListOfFiles(dir: String)(f : File => Unit): Unit = {
      def go(dir: File): Unit= dir match {
        case d if d.exists && d.isDirectory && d.canRead=>
          val files = d.listFiles.filter(a => a.canRead && a.isFile).toList
          val dirs  = dir.listFiles.filter(_.isDirectory).toList
          files.foreach(f)
          dirs.foreach(go)
        case _ => ()
      }
      go(new File(dir))
    }

   getListOfFiles("/") { filename =>
      Try {
        for (line <- Source.fromFile(filename).getLines) {
          findAll[Email](line).foreach(a => println(s"$filename: [Email] $a"))
          findAll[Time24](line).foreach(a => println(s"$filename: [Time24] $a"))
          findAll[ApacheError](line).foreach(a => println(s"$filename: [ApacheError] $a"))
          findAll[BitcoinAdd](line).foreach(a => println(s"$filename: [BitcoinAdd] $a"))
          findAll[Celsius](line).foreach(a => println(s"$filename: [Celsius] $a"))
          findAll[Comments](line).foreach(a => println(s"$filename: [Comments] $a"))
          findAll[Coordinate](line).foreach(a => println(s"$filename: [Coordinate] $a"))
          findAll[Coordinate1](line).foreach(a => println(s"$filename: [Coordinate1] $a"))
          findAll[Coordinate2](line).foreach(a => println(s"$filename: [Coordinate2] $a"))
          findAll[Cron](line).foreach(a => println(s"$filename: [Cron] $a"))
          findAll[DMY](line).foreach(a => println(s"$filename: [DMY] $a"))
          findAll[DMY2](line).foreach(a => println(s"$filename: [DMY2] $a"))
          findAll[DMY3](line).foreach(a => println(s"$filename: [DMY3] $a"))
          findAll[UUID](line).foreach(a => println(s"$filename: [UUID] $a"))
          findAll[Youtube](line).foreach(a => println(s"$filename: [Youtube] $a"))

        }
      }
    }
  }

}
