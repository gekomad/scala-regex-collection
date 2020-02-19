package com.github.gekomad.regexcollection
import com.github.gekomad.regexcollection.Collection.Validator

import scala.annotation.tailrec
import scala.util.Try
import scala.util.matching.Regex

object Validate {
  def validateIgnoreCase[A](a: String)(implicit f: Validator[A]): Option[String]  = f.validateIgnoreCase(a)
  def validate[A](a: String)(implicit f: Validator[A]): Option[String]            = f.validateCaseSensitive(a)
  def regexp[A](implicit f: Validator[A]): String                                 = f.regexp
  def findAllIgnoreCase[A](a: String)(implicit f: Validator[A]): List[String]     = f.findAllIgnoreCase(a)
  def findAll[A](a: String)(implicit f: Validator[A]): List[String]               = f.findAllCaseSensitive(a)
  def findFirstIgnoreCase[A](a: String)(implicit f: Validator[A]): Option[String] = f.findFirstIgnoreCase(a)
  def findFirst[A](a: String)(implicit f: Validator[A]): Option[String]           = f.findFirstCaseSensitive(a)
}

trait Email
trait Email1
trait EmailSimple
trait UUID
trait MD5
trait IP
trait IP1
trait IP_6
trait SHA1
trait SHA256
trait Scientific
trait ApacheError
trait Facebook
trait Twitter
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
trait GermanStreet
trait USstates
trait UStreets
trait USstates1
trait Time24
trait Youtube
trait Cron
trait Domain
trait ItalianFiscalCode
trait ItalianVAT
trait ItalianZipCode
trait ItalianMobilePhone
trait ItalianPhone
trait ItalianIban
trait UsdCurrency
trait EurCurrency
trait YenCurrency
trait NotASCII
trait Percentage
trait MACAddress

trait DMY
trait DMY2
trait DMY3
trait DMY4

trait MDY
trait MDY2
trait MDY3
trait MDY4

trait Number1
trait Signed
trait Unsigned32

trait Coordinate
trait Coordinate1
trait Coordinate2

trait USZipCode
trait Time
trait USstreets
trait USstreetNumber

trait SingleChar
trait AZString
trait SingleNumber
trait StringAndNumber
trait AsciiString
trait Celsius
trait Fahrenheit
trait HtmlHref

trait Comments
trait CreditCardVisa
trait CreditCardMasterCard
trait CreditCardAmericanExpress
trait CreditCardinersClub
trait CreditCardDiscover
trait CreditCardJCB

object Collection {

  import java.time._
  import java.time.format.DateTimeFormatter._

  trait Validator[A] {
    def validateIgnoreCase(a: String): Option[String]
    def validateCaseSensitive(a: String): Option[String]
    def findAllIgnoreCase(a: String): List[String]
    def findAllCaseSensitive(a: String): List[String]
    def findFirstIgnoreCase(a: String): Option[String]
    def findFirstCaseSensitive(a: String): Option[String]
    def regexp: String
  }

  object Validator {
    def apply[A](reg: String): Validator[A] = new Validator[A] {
      override val regexp: String = reg
      val regex: Regex            = reg.r
      val regexIgn: Regex         = s"(?i)$reg".r
      val regexExact: Regex       = s"^$reg$$".r
      val regexExactIgn: Regex    = s"^(?i)$reg$$".r

      override def validateIgnoreCase(a: String): Option[String]    = regexExactIgn.findFirstMatchIn(a).map(_ => a)
      override def validateCaseSensitive(a: String): Option[String] = regexExact.findFirstMatchIn(a).map(_ => a)
      def validateCaseInsensitive(a: String): Option[String]        = regexExactIgn.findFirstMatchIn(a).map(_ => a)

      override def findAllIgnoreCase(a: String): List[String] = findAll(a, regexIgn)

      override def findAllCaseSensitive(a: String): List[String] = findAll(a, regex)

      private def findFirst(a: String, r: Regex): Option[String] = r.findFirstIn(a)

      private def findAll(a: String, r: Regex): List[String] = {

        @tailrec
        def extractToList(mi: Regex.MatchIterator, acc: List[String]): List[String] =
          if (!mi.hasNext) acc
          else
            extractToList(mi, acc ::: List(mi.next))

        extractToList(r.findAllIn(a), Nil)
      }

      override def findFirstIgnoreCase(a: String): Option[String]    = findFirst(a, regexIgn)
      override def findFirstCaseSensitive(a: String): Option[String] = findFirst(a, regex)
    }

    def apply[A](f: String => Option[String]): Validator[A] = new Validator[A] {
      def validateCaseSensitive(a: String): Option[String]           = f(a)
      def validateIgnoreCase(a: String): Option[String]              = f(a)
      override def findAllIgnoreCase(a: String): List[String]        = Nil
      override def findAllCaseSensitive(a: String): List[String]     = Nil
      override val regexp: String                                    = ""
      override def findFirstIgnoreCase(a: String): Option[String]    = None
      override def findFirstCaseSensitive(a: String): Option[String] = None
    }
  }

  implicit val validatorEmail: Validator[Email] =
    Validator[Email]("""[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*""")

  implicit val validatorEmailSimple: Validator[EmailSimple] = Validator[EmailSimple](""".+@.+\..+""")
  implicit val validatorHref: Validator[HtmlHref]           = Validator[HtmlHref]("""href=[\"\'](http[s]?:\/\/|\.\/|\/)?\w+(\.\w+)*(\/\w+(\.\w+)?)*(\/|\?\w*=\w*(&\w*=\w*)*)?[\"\']""")
  implicit val validatorEmail1: Validator[Email1]           = Validator[Email1]("""[_&A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*(\.[A-Za-z]{2,})""")
  implicit val validatorFacebook: Validator[Facebook]       = Validator[Facebook]("""https:\/\/(www|[a-zA-Z]{2}-[a-zA-Z]{2})\.facebook\.com\/(pages\/[a-zA-Z0-9\.-]+\/[0-9]+|[a-zA-Z0-9\.-]+)[\/]?""")
  implicit val validatorTwitter: Validator[Twitter]         = Validator[Twitter]("""https:\/\/twitter\.com\/(#!\/)?[a-zA-Z0-9]{1,15}[\/]?""")
  implicit val validatorUsdCurrency: Validator[UsdCurrency] = Validator[UsdCurrency]("""[\$]?[+-]?[0-9]{1,3}(?:,?[0-9]{3})*\.[0-9]{2}""")
  implicit val validatorNumber1: Validator[Number1]         = Validator[Number1]("""\d{0,2}(\.\d{1,2})?""")
  implicit val validatorCoordinate: Validator[Coordinate]   = Validator[Coordinate]("""[NS]([0-8][0-9](\.[0-5]\d){2}|90(\.00){2})\040[EW]((0\d\d|1[0-7]\d)(\.[0-5]\d){2}|180(\.00){2})""")

  implicit val validatorCoordinate1: Validator[Coordinate1] = Validator[Coordinate1](
    """([0-8]?\d(°|\s)[0-5]?\d(['|′]|\s)[0-5]?\d(\.\d{1,6})?["|″]?|90(°|\s)0?0(['|′]|\s)0?0["|″]?)\s{0,}[NnSs]\s{1,}([0-1]?[0-7]?\d(°|\s)[0-5]?\d(['|′]|\s)[0-5]?\d(\.\d{1,6})?["|″]?|180(°|\s)0?0(['|′]|\s)0?0["|″]?)\s{0,}[EeOoWw]"""
  )
  implicit val validatorCoordinate2: Validator[Coordinate2] = Validator[Coordinate2]("""[0-9]{1,2}[:|°][0-9]{1,2}[:|'](?:\b[0-9]+(?:\.[0-9]*)?|\.[0-9]+\b)"?[N|S|E|W]""")
  implicit val validatorSigned: Validator[Signed]           = Validator[Signed]("""(\+|-)?\d+""")
  implicit val validatorUnsigned32: Validator[Unsigned32] = Validator[Unsigned32](
    """(0|(\+)?[1-9]{1}[0-9]{0,8}|(\+)?[1-3]{1}[0-9]{1,9}|(\+)?[4]{1}([0-1]{1}[0-9]{8}|[2]{1}([0-8]{1}[0-9]{7}|[9]{1}([0-3]{1}[0-9]{6}|[4]{1}([0-8]{1}[0-9]{5}|[9]{1}([0-5]{1}[0-9]{4}|[6]{1}([0-6]{1}[0-9]{3}|[7]{1}([0-1]{1}[0-9]{2}|[2]{1}([0-8]{1}[0-9]{1}|[9]{1}[0-5]{1})))))))))"""
  )
  implicit val validatorYenCurrency: Validator[YenCurrency] = Validator[YenCurrency]("""[\¥]?[+-]?[0-9]{1,3}(?:,?[0-9]{3})*\.[0-9]{2}""")
  implicit val validatorEurCurrency: Validator[EurCurrency] =
    Validator[EurCurrency]("""(0|(([1-9]{1}|[1-9]{1}[0-9]{1}|[1-9]{1}[0-9]{2}){1}(\ [0-9]{3}){0,})),(([0-9]{2})|\-\-)([\ ]{1})(€|EUR|EURO){1}""")
  implicit val validatorNotASCII: Validator[NotASCII] = Validator[NotASCII]("""[^\x00-\x7F]+\ *(?:[^\x00-\x7F]| )*""")

  implicit val validatorDMY: Validator[DMY]   = Validator[DMY]("""(0?[1-9]|[12]\d|3[01])[\/](0?[1-9]|1[0-2])[\/](\d{4}|\d{3}|\d{2}|\d{1})""")
  implicit val validatorDMY2: Validator[DMY2] = Validator[DMY2]("""(0?[1-9]|[12]\d|3[01])[-](0?[1-9]|1[0-2])[-](\d{4}|\d{3}|\d{2}|\d{1})""")
  implicit val validatorDMY3: Validator[DMY3] = Validator[DMY3]("""(0[1-9]|[12]\d|3[01])[\/](0[1-9]|1[0-2])[\/](\d{4}|\d{3}|\d{2}|\d{1})""")
  implicit val validatorDMY4: Validator[DMY4] = Validator[DMY4]("""(0[1-9]|[12]\d|3[01])[-](0[1-9]|1[0-2])[-](\d{4}|\d{3}|\d{2}|\d{1})""")

  implicit val validatorMDY: Validator[MDY]   = Validator[MDY]("""(0?[1-9]|1[0-2])[\/](0?[1-9]|[12]\d|3[01])[\/](\d{4}|\d{3}|\d{2}|\d{1})""")
  implicit val validatorMDY2: Validator[MDY2] = Validator[MDY2]("""(0?[1-9]|1[0-2])[-](0?[1-9]|[12]\d|3[01])[-](\d{4}|\d{3}|\d{2}|\d{1})""")
  implicit val validatorMDY3: Validator[MDY3] = Validator[MDY3]("""(0[1-9]|1[0-2])[\/](0[1-9]|[12]\d|3[01])[\/](\d{4}|\d{3}|\d{2}|\d{1})""")
  implicit val validatorMDY4: Validator[MDY4] = Validator[MDY4]("""(0[1-9]|1[0-2])[-](0[1-9]|[12]\d|3[01])[-](\d{4}|\d{3}|\d{2}|\d{1})""")

  implicit val validatorUUID: Validator[UUID] = Validator[UUID]("[0-9A-Za-z]{8}-[0-9A-Za-z]{4}-4[0-9A-Za-z]{3}-[89ABab][0-9A-Za-z]{3}-[0-9A-Za-z]{12}")

  implicit val validatorItalianMobilePhone: Validator[ItalianMobilePhone] = Validator[ItalianMobilePhone]("""([+]39)?((38[{8,9}|0])|(34[{7-9}|0])|(36[6|8|0])|(33[{3-9}|0])|(32[{8,9}]))([\d]{7})""")
  implicit val validatorItalianPhone: Validator[ItalianPhone]             = Validator[ItalianPhone]("""([0-9]{2,4})([-\s\/]{1})([0-9]{4,8})""")
  implicit val validatorItalianZipCode: Validator[ItalianZipCode]         = Validator[ItalianZipCode]("""(V-|I-)?[0-9]{5}""")
  implicit val validatorItalianVAT: Validator[ItalianVAT]                 = Validator[ItalianVAT]("""[0-9]{11}""")
  implicit val validatorItalianIban: Validator[ItalianIban]               = Validator[ItalianIban]("""IT\d{2}[ ][a-zA-Z]\d{3}[ ]\d{4}[ ]\d{4}[ ]\d{4}[ ]\d{4}[ ]\d{3}|IT\d{2}[a-zA-Z]\d{22}""")
  implicit val validatorItalianFiscalCode: Validator[ItalianFiscalCode] = Validator[ItalianFiscalCode](
    """(?i)(?:(?:[B-DF-HJ-NP-TV-Z]|[AEIOU])[AEIOU][AEIOUX]|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}[\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[1256LMRS][\dLMNP-V])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM])(?:[A-MZ][1-9MNP-V][\dLMNP-V]{2}|[A-M][0L](?:[1-9MNP-V][\dLMNP-V]|[0L][1-9MNP-V]))[A-Z]"""
  )
  implicit val validatorMD5: Validator[MD5]             = Validator[MD5]("[a-fA-F0-9]{32}")
  implicit val validatorUSZipCode: Validator[USZipCode] = Validator[USZipCode]("""[0-9]{5}(?:-[0-9]{4})?""")
  implicit val validatorIP: Validator[IP]               = Validator[IP]("(?:(?:2(?:[0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9])\\.){3}(?:(?:2([0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9]))")
  implicit val validatorIP1: Validator[IP1] = Validator[IP1](
    """((?:2[0-5]{2}|1\d{2}|[1-9]\d|[1-9])\.(?:(?:2[0-5]{2}|1\d{2}|[1-9]\d|\d)\.){2}(?:2[0-5]{2}|1\d{2}|[1-9]\d|\d)):(\d|[1-9]\d|[1-9]\d{2,3}|[1-5]\d{4}|6[0-4]\d{3}|654\d{2}|655[0-2]\d|6553[0-5])"""
  )
  implicit val validatorIP_6: Validator[IP_6]                 = Validator[IP_6]("(([a-fA-F0-9]{1,4}|):){1,7}([a-fA-F0-9]{1,4}|:)")
  implicit val validatorSHA1: Validator[SHA1]                 = Validator[SHA1]("[a-fA-F0-9]{40}")
  implicit val validatorGermanStreet: Validator[GermanStreet] = Validator[GermanStreet]("""([A-ZÄÖÜ][a-zäöüß]+(([.] )|( )|([-])))+[1-9][0-9]{0,3}[a-z]?""")

  implicit val validatorSingleChar: Validator[SingleChar]           = Validator[SingleChar]("""[a-zA-Z]""")
  implicit val validatorCelsius: Validator[Celsius]                 = Validator[Celsius]("""[+-]?(\d+|\d+\.\d+)\s*°C""")
  implicit val validatorFahrenheit: Validator[Fahrenheit]           = Validator[Fahrenheit]("""[+-]?(\d+|\d+\.\d+)\s*°F""")
  implicit val validatorAZString: Validator[AZString]               = Validator[AZString]("""[a-zA-Z]+""")
  implicit val validatorSingleNumber: Validator[SingleNumber]       = Validator[SingleNumber]("""[0-9]""")
  implicit val validatorStringAndNumber: Validator[StringAndNumber] = Validator[StringAndNumber]("""[a-zA-Z0-9]+""")
  implicit val validatorAsciiString: Validator[AsciiString]         = Validator[AsciiString]("""[\x00-\x7F]+""")

  implicit val validatorUSstreetNumber: Validator[USstreetNumber] = Validator[USstreetNumber](
    """((\d{1,6}\-\d{1,6})|(\d{1,6}\\\d{1,6})|(\d{1,6})(\/)(\d{1,6})|(\w{1}\-?\d{1,6})|(\w{1}\s\d{1,6})|((P\.?O\.?\s)((BOX)|(Box))(\s\d{1,6}))|((([R]{2})|([H][C]))(\s\d{1,6}\s)((BOX)|(Box))(\s\d{1,6}))?)"""
  )
  implicit val validatorUSstreets: Validator[USstreets] = Validator[USstreets](
    """\s*((?:(?:\d+(?:\x20+\w+\.?)+(?:(?:\x20+STREET|ST|DRIVE|DR|AVENUE|AVE|ROAD|RD|LOOP|COURT|CT|CIRCLE|LANE|LN|BOULEVARD|BLVD)\.?)?)|(?:(?:P\.\x20?O\.|P\x20?O)\x20*Box\x20+\d+)|(?:General\x20+Delivery)|(?:C[\\\/]O\x20+(?:\w+\x20*)+))\,?\x20*(?:(?:(?:APT|BLDG|DEPT|FL|HNGR|LOT|PIER|RM|S(?:LIP|PC|T(?:E|OP))|TRLR|UNIT|\x23)\.?\x20*(?:[a-zA-Z0-9\-]+))|(?:BSMT|FRNT|LBBY|LOWR|OFC|PH|REAR|SIDE|UPPR))?)\,?\s+((?:(?:\d+(?:\x20+\w+\.?)+(?:(?:\x20+STREET|ST|DRIVE|DR|AVENUE|AVE|ROAD|RD|LOOP|COURT|CT|CIRCLE|LANE|LN|BOULEVARD|BLVD)\.?)?)|(?:(?:P\.\x20?O\.|P\x20?O)\x20*Box\x20+\d+)|(?:General\x20+Delivery)|(?:C[\\\/]O\x20+(?:\w+\x20*)+))\,?\x20*(?:(?:(?:APT|BLDG|DEPT|FL|HNGR|LOT|PIER|RM|S(?:LIP|PC|T(?:E|OP))|TRLR|UNIT|\x23)\.?\x20*(?:[a-zA-Z0-9\-]+))|(?:BSMT|FRNT|LBBY|LOWR|OFC|PH|REAR|SIDE|UPPR))?)?\,?\s+((?:[A-Za-z]+\x20*)+)\,\s+(A[LKSZRAP]|C[AOT]|D[EC]|F[LM]|G[AU]|HI|I[ADLN]|K[SY]|LA|M[ADEHINOPST]|N[CDEHJMVY]|O[HKR]|P[ARW]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY])\s+(\d+(?:-\d+)?)\s*"""
  )
  implicit val validatorCron: Validator[Cron] = Validator[Cron](
    """((?:\*|[0-5]?[0-9](?:(?:-[0-5]?[0-9])|(?:,[0-5]?[0-9])+)?)(?:\/[0-9]+)?)\s+((?:\*|(?:1?[0-9]|2[0-3])(?:(?:-(?:1?[0-9]|2[0-3]))|(?:,(?:1?[0-9]|2[0-3]))+)?)(?:\/[0-9]+)?)\s+((?:\*|(?:[1-9]|[1-2][0-9]|3[0-1])(?:(?:-(?:[1-9]|[1-2][0-9]|3[0-1]))|(?:,(?:[1-9]|[1-2][0-9]|3[0-1]))+)?)(?:\/[0-9]+)?)\s+((?:\*|(?:[1-9]|1[0-2])(?:(?:-(?:[1-9]|1[0-2]))|(?:,(?:[1-9]|1[0-2]))+)?)(?:\/[0-9]+)?)\s+((?:\*|[0-7](?:-[0-7]|(?:,[0-7])+)?)(?:\/[0-9]+)?)"""
  )
  implicit val validatorSHA256: Validator[SHA256]     = Validator[SHA256]("[A-Fa-f0-9]{64}")
  implicit val validatorUSstates: Validator[USstates] = Validator[USstates]("(?:A[KLRZ]|C[AOT]|D[CE]|FL|GA|HI|I[ADLN]|K[SY]|LA|M[ADEINOST]|N[CDEHJMVY]|O[HKR]|P[AR]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY])")
  implicit val validatorUSstates1: Validator[USstates1] =
    Validator[USstates1](
      """(Alabama|Alaska|Arizona|Arkansas|California|Colorado|Connecticut|Delaware|Florida|Georgia|Hawaii|Idaho|Illinois|Indiana|Iowa|Kansas|Kentucky|Louisiana|Maine|Maryland|Massachusetts|Michigan|Minnesota|Mississippi|Missouri|Montana|Nebraska|Nevada|New\sHampshire|New\sJersey|New\sMexico|New\sYork|North\sCarolina|North\sDakota|Ohio|Oklahoma|Oregon|Pennsylvania|Rhode\sIsland|South\sCarolina|South\sDakota|Tennessee|Texas|Utah|Vermont|Virginia|Washington|West\sVirginia|Wisconsin|Wyoming)"""
    )
  implicit val validatorScientific: Validator[Scientific] = Validator[Scientific]("-?[1-9](?:\\.\\d+)?[Ee][-+]?\\d+")
  implicit val validatorApacheError: Validator[ApacheError] =
    Validator[ApacheError]("""(\[[^\]]+\]) (\[[^\]]+\]) (\[[^\]]+\]) (.*)""")

  implicit val validatorPercentage: Validator[Percentage] = Validator[Percentage]("""[0]?(?<Percentage>[1-9]?[0-9]?|100)%""")
  implicit val validatorMACAddress: Validator[MACAddress] = Validator[MACAddress]("""([0-9a-fA-F][0-9a-fA-F]:){5}([0-9a-fA-F][0-9a-fA-F])""")

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
  implicit val validatorURL1: Validator[URL1]                   = Validator[URL1]("""(?i)(http:\/\/www\.|https:\/\/www\.)[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?""")
  implicit val validatorURL2: Validator[URL2]                   = Validator[URL2]("""(?i)(http:\/\/www\.)[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?""")
  implicit val validatorURL3: Validator[URL3]                   = Validator[URL3]("""(?i)(https:\/\/www\.)[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?""")
  implicit val validatorFTP: Validator[FTP]                     = Validator[FTP]("""(?i)(ftp:\/\/|ftps:\/\/)[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?""")
  implicit val validatorFTP1: Validator[FTP1]                   = Validator[FTP1]("""(?i)(ftp:\/\/)[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?""")
  implicit val validatorFTP2: Validator[FTP2]                   = Validator[FTP2]("""(?i)(ftps:\/\/)[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?""")
  implicit val validatorDomain: Validator[Domain]               = Validator[Domain]("""(?!www\.)[A-z0-9][A-z0-9-]{1,61}[A-z0-9](\.[A-z]{2,}){1,}""")
  implicit val validatorTime: Validator[Time] =
    Validator[Time]("""(([0-9]|[0-1][0-9]|[2][0-3]):([0-5][0-9])(\s{0,1})(AM|PM|am|pm|aM|Am|pM|Pm{2,2})$)|(^([0-9]|[1][0-9]|[2][0-3])(\s{0,1})(AM|PM|am|pm|aM|Am|pM|Pm{2,2}))""")

  implicit val validatorComment: Validator[Comments] = Validator[Comments]("""(\/\*([^*]|[\r\n]|(\*+([^*/]|[\r\n])))*\*+\/)""")

  implicit val validatorCreditCardVisa: Validator[CreditCardVisa]                       = Validator[CreditCardVisa]("""4[0-9]{12}(?:[0-9]{3})?""")
  implicit val validatorCreditCardMasterCard: Validator[CreditCardMasterCard]           = Validator[CreditCardMasterCard]("""(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}""")
  implicit val validatorCreditCardAmericanExpress: Validator[CreditCardAmericanExpress] = Validator[CreditCardAmericanExpress]("""3[47][0-9]{13}""")
  implicit val validatorCreditCardDinersClub: Validator[CreditCardinersClub]            = Validator[CreditCardinersClub]("""3(?:0[0-5]|[68][0-9])[0-9]{11}""")
  implicit val validatorCreditCardDiscover: Validator[CreditCardDiscover]               = Validator[CreditCardDiscover]("""6(?:011|5[0-9]{2})[0-9]{12}""")
  implicit val validatorCreditCardJCB: Validator[CreditCardJCB]                         = Validator[CreditCardJCB]("""(?:2131|1800|35\d{3})\d{11}""")

  implicit val validatorLocalDateTime: Validator[LocalDateTime] = Validator[LocalDateTime]((a: String) => Try(LocalDateTime.parse(a, ISO_LOCAL_DATE_TIME)).toOption.map(_ => a))

  implicit val validatorLocalDate: Validator[LocalDate] = Validator[LocalDate]((a: String) => Try(LocalDate.parse(a, ISO_LOCAL_DATE)).toOption.map(_ => a))

  implicit val validatorLocalTime: Validator[LocalTime] = Validator[LocalTime]((a: String) => Try(LocalTime.parse(a, ISO_LOCAL_TIME)).toOption.map(_ => a))

  implicit val validatorOffsetDateTime: Validator[OffsetDateTime] = Validator[OffsetDateTime]((a: String) => Try(OffsetDateTime.parse(a, ISO_OFFSET_DATE_TIME)).toOption.map(_ => a))

  implicit val validatorOffsetTime: Validator[OffsetTime] = Validator[OffsetTime]((a: String) => Try(OffsetTime.parse(a, ISO_OFFSET_TIME)).toOption.map(_ => a))

  implicit val validatorZonedDateTime: Validator[ZonedDateTime] = Validator[ZonedDateTime]((a: String) => Try(ZonedDateTime.parse(a, ISO_ZONED_DATE_TIME)).toOption.map(_ => a))

}
