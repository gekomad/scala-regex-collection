package com.github.gekomad.regexcollection
import com.github.gekomad.regexcollection.Collection.Validator

import scala.annotation.tailrec
import scala.util.Try
import scala.util.matching.Regex

object Validate {
  implicit def validateIgnoreCase[A](a: String)(implicit f: Validator[A]): Option[String] = f.validateIgnoreCase(a)
  implicit def validate[A](a: String)(implicit f: Validator[A]): Option[String]           = f.validateCaseSensitive(a)
  implicit def regexp[A](implicit f: Validator[A]): String                                = f.regexp
  implicit def findAllIgnoreCase[A](a: String)(implicit f: Validator[A]): List[String]    = f.findAllIgnoreCase(a)
  implicit def findAll[A](a: String)(implicit f: Validator[A]): List[String]              = f.findAllCaseSensitive(a)

}

trait Email
trait EmailSimple
trait UUID
trait MD5
trait IP
trait IP1
trait IP_6
trait SHA1
trait SHA256
trait FTP
trait FTP1
trait FTP2
trait URL
trait URL1
trait URL2
trait URL3
trait HEX
trait HEX1
trait HEX2
trait HEX3
trait BitcoinAdd
trait USphoneNumber
trait Time24
trait Youtube
trait Cron
trait Domain
trait ItalianFiscalCode
trait ItalianPiva

object Collection {

  import java.time._
  import java.time.format.DateTimeFormatter._

  trait Validator[A] {
    def validateIgnoreCase(a: String): Option[String]
    def validateCaseSensitive(a: String): Option[String]
    def findAllIgnoreCase(a: String): List[String]
    def findAllCaseSensitive(a: String): List[String]
    val regexp: String
  }

  object Validator {
    def apply[A](reg: String): Validator[A] = new Validator[A] {
      override val regexp: String = reg
      val regex: Regex            = reg.r
      val regexIgn: Regex         = ("(?i)" + reg).r
      val regexExact: Regex       = ('^' + reg + '$').r
      val regexExactIgn: Regex    = ("^(?i)" + reg + '$').r

      override def validateIgnoreCase(a: String): Option[String]    = regexExactIgn.findFirstMatchIn(a).map(_ => a)
      override def validateCaseSensitive(a: String): Option[String] = regexExact.findFirstMatchIn(a).map(_ => a)
      def validateCaseInsensitive(a: String): Option[String]        = regexExactIgn.findFirstMatchIn(a).map(_ => a)

      override def findAllIgnoreCase(a: String): List[String] = findAll(a, regexIgn)

      override def findAllCaseSensitive(a: String): List[String] = findAll(a, regex)

      private def findAll(a: String, r: Regex): List[String] = {

        @tailrec
        def extractToList(mi: Regex.MatchIterator, acc: List[String]): List[String] =
          if (!mi.hasNext) acc
          else
            extractToList(mi, acc ::: List(mi.next))

        extractToList(r.findAllIn(a), Nil)
      }

    }

    def apply[A](f: String => Option[String]): Validator[A] = new Validator[A] {
      def validateCaseSensitive(a: String): Option[String]       = f(a)
      def validateIgnoreCase(a: String): Option[String]          = f(a)
      override def findAllIgnoreCase(a: String): List[String]    = Nil
      override def findAllCaseSensitive(a: String): List[String] = Nil
      override val regexp: String                                = ""
    }
  }

  implicit val validatorEmail: Validator[Email] =
    Validator[Email]("""[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*""")

  implicit val validatorEmailSimple: Validator[EmailSimple] = Validator[EmailSimple](""".+@.+\..+""")

  implicit val validatorUUID: Validator[UUID] = Validator[UUID]("[0-9A-Za-z]{8}-[0-9A-Za-z]{4}-4[0-9A-Za-z]{3}-[89ABab][0-9A-Za-z]{3}-[0-9A-Za-z]{12}")

  implicit val validatorItalianPiva: Validator[ItalianPiva] = Validator[ItalianPiva]("""^[0-9]{11}$""")

  implicit val validatorMD5: Validator[MD5] = Validator[MD5]("[a-fA-F0-9]{32}")
  implicit val validatorIP: Validator[IP]   = Validator[IP]("(?:(?:2(?:[0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9])\\.){3}(?:(?:2([0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9]))")
  implicit val validatorIP1: Validator[IP1] = Validator[IP1](
    """((?:2[0-5]{2}|1\d{2}|[1-9]\d|[1-9])\.(?:(?:2[0-5]{2}|1\d{2}|[1-9]\d|\d)\.){2}(?:2[0-5]{2}|1\d{2}|[1-9]\d|\d)):(\d|[1-9]\d|[1-9]\d{2,3}|[1-5]\d{4}|6[0-4]\d{3}|654\d{2}|655[0-2]\d|6553[0-5])"""
  )
  implicit val validatorIP_6: Validator[IP_6] = Validator[IP_6]("(([a-fA-F0-9]{1,4}|):){1,7}([a-fA-F0-9]{1,4}|:)")
  implicit val validatorSHA1: Validator[SHA1] = Validator[SHA1]("[a-fA-F0-9]{40}")
  implicit val validatorCron: Validator[Cron] = Validator[Cron](
    """((?:\*|[0-5]?[0-9](?:(?:-[0-5]?[0-9])|(?:,[0-5]?[0-9])+)?)(?:\/[0-9]+)?)\s+((?:\*|(?:1?[0-9]|2[0-3])(?:(?:-(?:1?[0-9]|2[0-3]))|(?:,(?:1?[0-9]|2[0-3]))+)?)(?:\/[0-9]+)?)\s+((?:\*|(?:[1-9]|[1-2][0-9]|3[0-1])(?:(?:-(?:[1-9]|[1-2][0-9]|3[0-1]))|(?:,(?:[1-9]|[1-2][0-9]|3[0-1]))+)?)(?:\/[0-9]+)?)\s+((?:\*|(?:[1-9]|1[0-2])(?:(?:-(?:[1-9]|1[0-2]))|(?:,(?:[1-9]|1[0-2]))+)?)(?:\/[0-9]+)?)\s+((?:\*|[0-7](?:-[0-7]|(?:,[0-7])+)?)(?:\/[0-9]+)?)"""
  )
  implicit val validatorSHA256: Validator[SHA256] = Validator[SHA256]("[A-Fa-f0-9]{64}")
  implicit val validatorItalianFiscalCode: Validator[ItalianFiscalCode] = Validator[ItalianFiscalCode](
    """(?i)(?:(?:[B-DF-HJ-NP-TV-Z]|[AEIOU])[AEIOU][AEIOUX]|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}[\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[1256LMRS][\dLMNP-V])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM])(?:[A-MZ][1-9MNP-V][\dLMNP-V]{2}|[A-M][0L](?:[1-9MNP-V][\dLMNP-V]|[0L][1-9MNP-V]))[A-Z]"""
  )
  implicit val validatorYoutube: Validator[Youtube] =
    Validator[Youtube](
      """(?i)(?:https:\/\/)?(?:(?:(?:www\.?)?youtube\.com(?:\/(?:(?:watch\?.*?(v=[^&\s]+).*)|(?:v(\/.*))|(channel\/.+)|(?:user\/(.+))|(?:results\?(search_query=.+))))?)|(?:youtu\.be(\/.*)?))"""
    )
  implicit val validatorBitcoinAdd: Validator[BitcoinAdd]       = Validator[BitcoinAdd]("([13][a-km-zA-HJ-NP-Z0-9]{26,33})")
  implicit val validatorHEX: Validator[HEX]                     = Validator[HEX]("#?([\\da-fA-F]{2})([\\da-fA-F]{2})([\\da-fA-F]{2})")
  implicit val validatorHEX1: Validator[HEX1]                   = Validator[HEX1]("#([\\da-fA-F]{2})([\\da-fA-F]{2})([\\da-fA-F]{2})")
  implicit val validatorHEX2: Validator[HEX2]                   = Validator[HEX2]("(#|0x)(?:[0-9A-Fa-f]{6}|0-9A-Fa-f]{8})\\b")
  implicit val validatorHEX3: Validator[HEX3]                   = Validator[HEX3]("0x(?:[0-9A-Fa-f]{6}|0-9A-Fa-f]{8})\\b")
  implicit val validatorUSphoneNumber: Validator[USphoneNumber] = Validator[USphoneNumber]("""(?:\d{1}\s)?\(?(\d{3})\)?-?\s?(\d{3})-?\s?(\d{4})""")
  implicit val validatorTime24: Validator[Time24]               = Validator[Time24]("([01]?[0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?")
  implicit val validatorURL: Validator[URL]                     = Validator[URL]("""((\w+:\/\/)[-a-zA-Z0-9:@;?&=\/%\+\.\*!'\(\),\$_\{\}\^~\[\]`#|]+)""")
  implicit val URL1: Validator[URL1]                            = Validator[URL1]("""(?i)(http:\/\/www\.|https:\/\/www\.)[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?""")
  implicit val FTP: Validator[FTP]                              = Validator[FTP]("""(?i)(ftp:\/\/|ftps:\/\/)[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?""")
  implicit val FTP1: Validator[FTP1]                            = Validator[FTP1]("""(?i)(ftp:\/\/)[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?""")
  implicit val FTP2: Validator[FTP2]                            = Validator[FTP2]("""(?i)(ftps:\/\/)[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?""")
  implicit val URL2: Validator[URL2]                            = Validator[URL2]("""(?i)(http:\/\/www\.)[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?""")
  implicit val URL3: Validator[URL3]                            = Validator[URL3]("""(?i)(https:\/\/www\.)[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?""")
  implicit val Domain: Validator[Domain]                        = Validator[Domain]("""(?!www\.)[A-z0-9][A-z0-9-]{1,61}[A-z0-9](\.[A-z]{2,}){1,}""")

  implicit val validatorLocalDateTime: Validator[LocalDateTime] = Validator[LocalDateTime]((a: String) => Try(LocalDateTime.parse(a, ISO_LOCAL_DATE_TIME)).toOption.map(_ => a))

  implicit val validatorLocalDate: Validator[LocalDate] = Validator[LocalDate]((a: String) => Try(LocalDate.parse(a, ISO_LOCAL_DATE)).toOption.map(_ => a))

  implicit val validatorLocalTime: Validator[LocalTime] = Validator[LocalTime]((a: String) => Try(LocalTime.parse(a, ISO_LOCAL_TIME)).toOption.map(_ => a))

  implicit val validatorOffsetDateTime: Validator[OffsetDateTime] = Validator[OffsetDateTime]((a: String) => Try(OffsetDateTime.parse(a, ISO_OFFSET_DATE_TIME)).toOption.map(_ => a))

  implicit val validatorOffsetTime: Validator[OffsetTime] = Validator[OffsetTime]((a: String) => Try(OffsetTime.parse(a, ISO_OFFSET_TIME)).toOption.map(_ => a))

  implicit val validatorZonedDateTime: Validator[ZonedDateTime] = Validator[ZonedDateTime]((a: String) => Try(ZonedDateTime.parse(a, ISO_ZONED_DATE_TIME)).toOption.map(_ => a))

}
