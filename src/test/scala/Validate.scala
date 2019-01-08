import org.scalatest.FunSuite

import scala.util.Try

class Validate extends FunSuite {

  test("Email") {
    import com.github.gekomad.regexcollection.Validate.{regexp, validate}
    import com.github.gekomad.regexcollection.Email
    import com.github.gekomad.regexcollection.EmailSimple
    import com.github.gekomad.regexcollection.Collection.Validator

    {
      assert(regexp[Email] == """[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*""")
      assert(validate[Email]("sdsdsd@sdf.com") == Some("sdsdsd@sdf.com"))
      assert(validate[Email](" sdsdsd@sdf.com") == None)
      assert(validate[Email]("abc,a@%.d") == None)
      assert(validate[EmailSimple]("abc,a@%.d") == Some("abc,a@%.d"))
    }

    { //custom email pattern
      import com.github.gekomad.regexcollection.Email
      implicit val validator = Validator[Email](""".+@.+\..+""")
      assert(validate[Email]("abc,a@%.d") == Some("abc,a@%.d"))
    }

  }

  test("Email simple") {
    import com.github.gekomad.regexcollection.Validate.validate
    { //custom email pattern
      import com.github.gekomad.regexcollection.EmailSimple
      assert(validate[EmailSimple]("a@b.c") == Some("a@b.c"))
      assert(validate[EmailSimple]("@b.c") == None)
    }

  }

  test("MD5") {
    import com.github.gekomad.regexcollection.Validate.validate
    import com.github.gekomad.regexcollection.MD5
    assert(validate[MD5]("fc42757b4142b0474d35fcddb228b304") == Some("fc42757b4142b0474d35fcddb228b304"))
    assert(validate[MD5]("sdsddom") == None)
  }

  test("UUID") {
    import com.github.gekomad.regexcollection.UUID
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[UUID]("f450f8C0-dc59-4e06-8797-b94ce235d5e5") == Some("f450f8C0-dc59-4e06-8797-b94ce235d5e5"))
    assert(validate[UUID]("sdsddom") == None)

    assert(validate[UUID]("f450f8C0-dc59-4e06-8797-b94ce235d5e5") == Some("f450f8C0-dc59-4e06-8797-b94ce235d5e5"))
    assert(validate[UUID]("sdsddom") == None)
  }

  test("LocalDateTime") {
    import java.time.LocalDateTime
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[LocalDateTime]("2000-12-31T11:21:19") == Some("2000-12-31T11:21:19"))
    assert(validate[LocalDateTime]("2000-12-31 11:21:19.0") == None)

    {
      import com.github.gekomad.regexcollection.Collection.Validator

      import java.time.format.DateTimeFormatter
      implicit val validator: Validator[LocalDateTime] =
        Validator[LocalDateTime]((a: String) => Try(LocalDateTime.parse(a, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0"))).toOption.map(_ => a))
      assert(validate[LocalDateTime]("2000-12-31 11:21:19.0") == Some("2000-12-31 11:21:19.0"))
    }
  }

  test("LocalDate") {
    import java.time.LocalDate
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[LocalDate]("2000-12-31") == Some("2000-12-31"))
    assert(validate[LocalDate]("2000-12-31 11:21:19.0") == None)
  }

  test("OffsetDateTime") {
    import java.time.OffsetDateTime
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[OffsetDateTime]("2011-12-03T10:15:30+01:00") == Some("2011-12-03T10:15:30+01:00"))
    assert(validate[OffsetDateTime]("2011-12-03T10:15:30") == None)
  }

  test("OffsetTime") {
    import java.time.OffsetTime
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[OffsetTime]("10:15:30+01:00") == Some("10:15:30+01:00"))
    assert(validate[OffsetTime]("10:15:30") == None)
  }

  test("ZonedDateTime") {
    import java.time.ZonedDateTime
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[ZonedDateTime]("2016-12-02T11:15:30-05:00[US/Central]") == Some("2016-12-02T11:15:30-05:00[US/Central]"))
    assert(validate[ZonedDateTime]("2016-12-02T11:15:30-05:00") == Some("2016-12-02T11:15:30-05:00"))
    assert(validate[ZonedDateTime]("10:15:30") == None)
  }

  test("LocalTime") {
    import java.time.LocalTime
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[LocalTime]("11:21:19") == Some("11:21:19"))
    assert(validate[LocalTime]("2000-12-31 11:21:19.0") == None)
  }

  test("HEX") {
    import com.github.gekomad.regexcollection.HEX
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[HEX]("2000-12-31 11:21:19.0") == None)
    assert(validate[HEX]("#FAFAFA") == Some("#FAFAFA"))
    assert(validate[HEX]("FAFAFA") == Some("FAFAFA"))
    assert(validate[HEX]("#FAFA") == None)
  }

  test("HEX1") {
    import com.github.gekomad.regexcollection.HEX1
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[HEX1]("2000-12-31 11:21:19.0") == None)
    assert(validate[HEX1]("#FAFAFA") == Some("#FAFAFA"))
    assert(validate[HEX1]("FAFAFA") == None)
    assert(validate[HEX1]("#FAFA") == None)
  }

  test("HEX2") {
    import com.github.gekomad.regexcollection.HEX2
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[HEX2]("2000-12-31 11:21:19.0") == None)
    assert(validate[HEX2]("0x121212") == Some("0x121212"))
    assert(validate[HEX2]("0x1212") == None)
    assert(validate[HEX2]("FAFAFA") == None)
    assert(validate[HEX2]("#ababab") == Some("#ababab"))
    assert(validate[HEX2]("#abab") == None)
  }

  test("HEX3") {
    import com.github.gekomad.regexcollection.HEX3
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[HEX3]("2000-12-31 11:21:19.0") == None)
    assert(validate[HEX3]("0x121212") == Some("0x121212"))
    assert(validate[HEX3]("FAFAFA") == None)
    assert(validate[HEX3]("#ababab") == None)
    assert(validate[HEX3]("#abab") == None)
  }

  test("SHA1") {
    import com.github.gekomad.regexcollection.SHA1
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[SHA1]("2sdf.0") == None)
    assert(validate[SHA1]("1c18da5dbf74e3fc1820469cf1f54355b7eec92d") == Some("1c18da5dbf74e3fc1820469cf1f54355b7eec92d"))
  }

  test("SHA256") {
    import com.github.gekomad.regexcollection.SHA256
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[SHA256]("2sdf.0") == None)
    assert(validate[SHA256]("000020f89134d831f48541b2d8ec39397bc99fccf4cc86a3861257dbe6d819d1") == Some("000020f89134d831f48541b2d8ec39397bc99fccf4cc86a3861257dbe6d819d1"))
  }

  test("IP") {
    import com.github.gekomad.regexcollection.IP
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[IP]("1011.164.108.1") == None)
    assert(validate[IP]("10.164.108.1") == Some("10.164.108.1"))
  }

  test("IP1") {
    import com.github.gekomad.regexcollection.IP1
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[IP1]("111.164.108.1:80") == Some("111.164.108.1:80"))
    assert(validate[IP1]("10.164.108.1") == None)
  }

  test("Italian Fiscal Code") {
    import com.github.gekomad.regexcollection.ItalianFiscalCode
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[ItalianFiscalCode]("BDAPPP14A01A001R") == Some("BDAPPP14A01A001R"))
    assert(validate[ItalianFiscalCode]("bdAPPP14A01A001R") == Some("bdAPPP14A01A001R"))
    assert(validate[ItalianFiscalCode]("aaaaaaaaaaaaaaaa") == None)
  }

  test("Italian Partita Iva") {
    import com.github.gekomad.regexcollection.ItalianPiva
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[ItalianPiva]("13297040362") == Some("13297040362"))
    assert(validate[ItalianPiva]("bdAPPP14A01A001R") == None)

  }

  test("IP6") {
    import com.github.gekomad.regexcollection.IP_6
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[IP_6]("10.164.108.1") == None)
    assert(validate[IP_6]("2001:db8:0:0:0:0:2:1") == Some("2001:db8:0:0:0:0:2:1"))
    assert(validate[IP_6]("2001:db8:a0b:12f0::1") == Some("2001:db8:a0b:12f0::1"))
  }

  test("Domain") {
    import com.github.gekomad.regexcollection.Domain
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[Domain]("2sdf.0") == None)
    assert(validate[Domain]("https://plus.google.com/113849558824288265773") == None)
    assert(validate[Domain]("https://www.aaa.com") == None)
    assert(validate[Domain]("plus.google.com") == Some("plus.google.com"))
    assert(validate[Domain]("google.com") == Some("google.com"))
  }

  test("URL") {
    import com.github.gekomad.regexcollection.URL
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[URL]("www.aaa.com") == None)
    assert(validate[URL]("http://www.aaa.com") == Some("http://www.aaa.com"))
    assert(validate[URL]("https://www.aaa.com") == Some("https://www.aaa.com"))
    assert(validate[URL]("a://b") == Some("a://b"))
    assert(validate[URL]("ftp://b") == Some("ftp://b"))
  }

  test("URL1") {
    import com.github.gekomad.regexcollection.URL1
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[URL1]("2sdf.0") == None)
    assert(validate[URL1]("http://www.aaa.com") == Some("http://www.aaa.com"))
    assert(validate[URL1]("Https://www.aaa.com") == Some("Https://www.aaa.com"))
    assert(validate[URL1]("a://b") == None)
    assert(validate[URL1]("https://www.google.com:8080/url?") == Some("https://www.google.com:8080/url?"))
    assert(validate[URL1]("HTTP://www.example.com/wpstyle/?p=364") == Some("HTTP://www.example.com/wpstyle/?p=364"))
    assert(validate[URL1]("http://foo.com/blah_(wikipedia)#cite-1") == None)
    assert(validate[URL1]("http://1337.net") == None)
    assert(validate[URL1]("http://例子.测试") == None)
  }

  test("URL2") {
    import com.github.gekomad.regexcollection.URL2
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[URL2]("2sdf.0") == None)
    assert(validate[URL2]("http://www.aaa.com") == Some("http://www.aaa.com"))
    assert(validate[URL2]("HTTPS://www.aaa.com") == None)
    assert(validate[URL2]("a://b") == None)
    assert(validate[URL2]("https://www.google.com:8080/url?") == None)
    assert(validate[URL2]("HTTP://www.example.com/wpstyle/?p=364") == Some("HTTP://www.example.com/wpstyle/?p=364"))
    assert(validate[URL2]("http://foo.com/blah_(wikipedia)#cite-1") == None)
    assert(validate[URL2]("http://1337.net") == None)
    assert(validate[URL2]("http://例子.测试") == None)
  }

  test("URL3") {
    import com.github.gekomad.regexcollection.URL3
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[URL3]("2sdf.0") == None)
    assert(validate[URL3]("http://www.aaa.com") == None)
    assert(validate[URL3]("HTTPS://www.aaa.com") == Some("HTTPS://www.aaa.com"))
    assert(validate[URL3]("a://b") == None)
    assert(validate[URL3]("https://www.google.com:8080/url?") == Some("https://www.google.com:8080/url?"))
    assert(validate[URL3]("HTTP://www.example.com/wpstyle/?p=364") == None)
    assert(validate[URL3]("http://foo.com/blah_(wikipedia)#cite-1") == None)
    assert(validate[URL3]("http://1337.net") == None)
    assert(validate[URL3]("http://例子.测试") == None)
  }

  test("FTP") {
    import com.github.gekomad.regexcollection.FTP
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[FTP]("2sdf.0") == None)
    assert(validate[FTP]("FTP://aaa.com") == Some("FTP://aaa.com"))
    assert(validate[FTP]("FTPS://www.aaa.com") == Some("FTPS://www.aaa.com"))
    assert(validate[FTP]("a://b") == None)
    assert(validate[FTP]("ftps://www.google.com:8080/url?") == Some("ftps://www.google.com:8080/url?"))
    assert(validate[FTP]("ftp://www.example.com/wpstyle/?p=364") == Some("ftp://www.example.com/wpstyle/?p=364"))
    assert(validate[FTP]("http://foo.com/blah_(wikipedia)#cite-1") == None)
    assert(validate[FTP]("http://1337.net") == None)
    assert(validate[FTP]("http://例子.测试") == None)
  }

  test("FTP1") {
    import com.github.gekomad.regexcollection.FTP1
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[FTP1]("2sdf.0") == None)
    assert(validate[FTP1]("FTP://aaa.com") == Some("FTP://aaa.com"))
    assert(validate[FTP1]("FTPS://www.aaa.com") == None)
    assert(validate[FTP1]("a://b") == None)
    assert(validate[FTP1]("FTPs://www.google.com:8080/url?") == None)
    assert(validate[FTP1]("FTP://www.example.com/wpstyle/?p=364") == Some("FTP://www.example.com/wpstyle/?p=364"))
    assert(validate[FTP1]("http://foo.com/blah_(wikipedia)#cite-1") == None)
    assert(validate[FTP1]("http://1337.net") == None)
    assert(validate[FTP1]("http://例子.测试") == None)
  }

  test("FTP2") {
    import com.github.gekomad.regexcollection.FTP2
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[FTP2]("2sdf.0") == None)
    assert(validate[FTP2]("FTP://aaa.com") == None)
    assert(validate[FTP2]("FTPS://www.aaa.com") == Some("FTPS://www.aaa.com"))
    assert(validate[FTP2]("a://b") == None)
    assert(validate[FTP2]("ftps://www.google.com:8080/url?") == Some("ftps://www.google.com:8080/url?"))
    assert(validate[FTP2]("ftp://www.example.com/wpstyle/?p=364") == None)
    assert(validate[FTP2]("http://foo.com/blah_(wikipedia)#cite-1") == None)
    assert(validate[FTP2]("http://1337.net") == None)
    assert(validate[FTP2]("http://例子.测试") == None)
  }

  test("Bitcoin Address") {
    import com.github.gekomad.regexcollection.BitcoinAdd
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[BitcoinAdd]("2sdf.0") == None)

    assert(validate[BitcoinAdd]("3Nxwenay9Z8Lc9JBiywExpnEFiLp6Afp8v") == Some("3Nxwenay9Z8Lc9JBiywExpnEFiLp6Afp8v"))
  }

  test("USphone Number") {
    import com.github.gekomad.regexcollection.USphoneNumber
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[USphoneNumber]("2sdf.0") == None)

    assert(validate[USphoneNumber]("555-555-5555") == Some("555-555-5555"))
    assert(validate[USphoneNumber]("1 416 555 9292") == Some("1 416 555 9292"))
    assert(validate[USphoneNumber]("4035555678") == Some("4035555678"))
    assert(validate[USphoneNumber]("(416)555-3456") == Some("(416)555-3456"))
  }

  test("Youtube") {
    import com.github.gekomad.regexcollection.Youtube
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[Youtube]("2sdf.0") == None)
    assert(validate[Youtube]("HTTPS://www.youtube.com/watch?v=9bZkp7q19f0") == Some("HTTPS://www.youtube.com/watch?v=9bZkp7q19f0"))
    assert(validate[Youtube]("https://WWW.youtube.com/watch?feature=something&v=videoid1&embed=something") == Some("https://WWW.youtube.com/watch?feature=something&v=videoid1&embed=something"))
    assert(validate[Youtube]("www.youtube.com/watch?feature=something&v=videoid2&embed=something") == Some("www.youtube.com/watch?feature=something&v=videoid2&embed=something"))
    assert(validate[Youtube]("Https://Www.youtube.com/watch?v=videoid1") == Some("Https://Www.youtube.com/watch?v=videoid1"))
    assert(validate[Youtube]("youtube.com/watch?v=videoid4") == Some("youtube.com/watch?v=videoid4"))
    assert(validate[Youtube]("https://www.youtube.com/channel/channelid1") == Some("https://www.youtube.com/channel/channelid1"))
    assert(validate[Youtube]("https://www.youtube.com/user/username1") == Some("https://www.youtube.com/user/username1"))
    assert(validate[Youtube]("https://www.youtube.com/results?search_query=search+query1") == Some("https://www.youtube.com/results?search_query=search+query1"))
    assert(validate[Youtube]("youtube.com/results?search_query=search+query4") == Some("youtube.com/results?search_query=search+query4"))
    assert(validate[Youtube]("http://www.youtube.com/results?search_query=search+query1") == None)
  }

  test("Crontab expression") {
    import com.github.gekomad.regexcollection.Cron
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[Cron]("2sdf.0") == None)

    assert(validate[Cron]("5 4 * * *") == Some("5 4 * * *"))
  }

  test("Time24") {
    import com.github.gekomad.regexcollection.Time24
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[Time24]("2sdf.0") == None)
    assert(validate[Time24]("23:50:00") == Some("23:50:00"))
    assert(validate[Time24]("14:00") == Some("14:00"))
    assert(validate[Time24]("23:00") == Some("23:00"))
    assert(validate[Time24]("9:30") == Some("9:30"))
    assert(validate[Time24]("19:30") == Some("19:30"))
    assert(validate[Time24]("25:00") == None)
  }

  test("sdf") {

    import com.github.gekomad.regexcollection.Validate.validate
    import java.time.ZonedDateTime
    assert(validate[ZonedDateTime]("2016-12-02T11:15:30-05:00[US/Central]") == Some("2016-12-02T11:15:30-05:00[US/Central]"))
  }

  test("Custom type") {

    trait Bar
    import com.github.gekomad.regexcollection.Collection.Validator
    import com.github.gekomad.regexcollection.Validate.validate
    import com.github.gekomad.regexcollection.Validate.validateIgnoreCase
    implicit val myValidator = Validator[Bar]("Bar.*")
    assert(validate[Bar]("a string") == None)
    assert(validate[Bar]("Bar foo") == Some("Bar foo"))
    assert(validate[Bar]("bar foo") == None)
    assert(validateIgnoreCase[Bar]("bar foo") == Some("bar foo"))

  }

  test("Function pattern") {
    import com.github.gekomad.regexcollection.Validate.validate
    trait Foo
    import com.github.gekomad.regexcollection.Collection.Validator
    import scala.util.Try
    implicit val validator = Validator[Foo]((a: String) => {
      val even = for {
        i <- Try(a.toInt)
        if (i % 2 == 0)
      } yield Some(a)
      even.getOrElse(None)
    })

    assert(validate[Foo]("42") == Some("42"))
    assert(validate[Foo]("hello") == None)

  }

}
