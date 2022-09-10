import scala.util.{Failure, Success}

class Validate extends munit.FunSuite {

  test("Email") {
    import com.github.gekomad.regexcollection.Validate.{regexp, validate}
    import com.github.gekomad.regexcollection.Email
    import com.github.gekomad.regexcollection.Collection.Validator

    {
      assertEquals(
        regexp[Email],
        """[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*"""
      )
      assertEquals(validate[Email]("sdsdsd@sdf.com"), Some("sdsdsd@sdf.com"))
      assertEquals(validate[Email]("$sdsdsd@sdf.com"), Some("$sdsdsd@sdf.com"))
      assertEquals(validate[Email]("bar@a.a"), Some("bar@a.a"))
      assertEquals(validate[Email](" sdsdsd@sdf.com"), None)
      assertEquals(validate[Email]("$@$.$"), None)
      assertEquals(validate[Email]("abc,a@%.d"), None)

    }

    { // custom email pattern
      import com.github.gekomad.regexcollection.Email
      implicit val validator: Validator[Email] = Validator[Email](""".+@.+\..+""")
      assertEquals(validate[Email]("abc,a@%.d"), Some("abc,a@%.d"))
    }

  }

  test("Email1") {
    import com.github.gekomad.regexcollection.Validate.validate
    import com.github.gekomad.regexcollection.Email1

    assertEquals(validate[Email1]("sdsdsd@sdf.com"), Some("sdsdsd@sdf.com"))
    assertEquals(validate[Email1]("$sdsdsd@sdf.com"), None)
    assertEquals(validate[Email1](" sdsdsd@sdf.com"), None)
    assertEquals(validate[Email1]("bar@a.a"), None)
    assertEquals(validate[Email1]("$@$.$"), None)
    assertEquals(validate[Email1]("bar@a.aa"), Some("bar@a.aa"))
    assertEquals(validate[Email1]("abc,a@%.d"), None)
  }

  test("Href") {
    import com.github.gekomad.regexcollection.Validate.validate
    import com.github.gekomad.regexcollection.HtmlHref

    assertEquals(validate[HtmlHref]("""href="www.yahoo.com""""), Some("""href="www.yahoo.com""""))
    assertEquals(validate[HtmlHref]("""href="http://localhost/blah/""""), Some("""href="http://localhost/blah/""""))
    assertEquals(validate[HtmlHref]("""href="https://localhost/blah/""""), Some("""href="https://localhost/blah/""""))
    assertEquals(validate[HtmlHref]("""href="eek""""), Some("""href="eek""""))
    assertEquals(validate[HtmlHref]("href=eek"), None)
    assertEquals(validate[HtmlHref]("""href="""""), None)
    assertEquals(validate[HtmlHref]("""href="bad example""""), None)
  }

  test("Email simple") {
    import com.github.gekomad.regexcollection.Validate.validate
    { // custom email pattern
      import com.github.gekomad.regexcollection.EmailSimple
      assertEquals(validate[EmailSimple]("abc,a@%.d"), Some("abc,a@%.d"))
      assertEquals(validate[EmailSimple]("a@b.c"), Some("a@b.c"))
      assertEquals(validate[EmailSimple]("$@$.$"), Some("$@$.$"))
      assertEquals(validate[EmailSimple]("@b.c"), None)
    }

  }

  test("MD5") {
    import com.github.gekomad.regexcollection.Validate.validate
    import com.github.gekomad.regexcollection.MD5
    assertEquals(validate[MD5]("fc42757b4142b0474d35fcddb228b304"), Some("fc42757b4142b0474d35fcddb228b304"))
    assertEquals(validate[MD5]("sdsddom"), None)
  }

  test("UUID") {
    import com.github.gekomad.regexcollection.UUID
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[UUID]("f450f8C0-dc59-4e06-8797-b94ce235d5e5"), Some("f450f8C0-dc59-4e06-8797-b94ce235d5e5"))
    assertEquals(validate[UUID]("sdsddom"), None)

    assertEquals(validate[UUID]("f450f8C0-dc59-4e06-8797-b94ce235d5e5"), Some("f450f8C0-dc59-4e06-8797-b94ce235d5e5"))
    assertEquals(validate[UUID]("sdsddom"), None)
  }

  test("LocalDateTime") {
    import java.time.LocalDateTime
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[LocalDateTime]("2000-12-31T11:21:19"), Some("2000-12-31T11:21:19"))
    assertEquals(validate[LocalDateTime]("2000-12-31 11:21:19.0"), None)

    {
      import com.github.gekomad.regexcollection.Collection.Validator
      import scala.util.Try
      import java.time.format.DateTimeFormatter
      implicit val validator: Validator[LocalDateTime] =
        Validator[LocalDateTime]((a: String) => Try(LocalDateTime.parse(a, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0"))).toOption.map(_ => a))
      assertEquals(validate[LocalDateTime]("2000-12-31 11:21:19.0"), Some("2000-12-31 11:21:19.0"))
    }
  }

  test("LocalDate") {
    import java.time.LocalDate
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[LocalDate]("2000-12-31"), Some("2000-12-31"))
    assertEquals(validate[LocalDate]("2000-12-31 11:21:19.0"), None)
  }

  test("OffsetDateTime") {
    import java.time.OffsetDateTime
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[OffsetDateTime]("2011-12-03T10:15:30+01:00"), Some("2011-12-03T10:15:30+01:00"))
    assertEquals(validate[OffsetDateTime]("2011-12-03T10:15:30"), None)
  }

  test("OffsetTime") {
    import java.time.OffsetTime
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[OffsetTime]("10:15:30+01:00"), Some("10:15:30+01:00"))
    assertEquals(validate[OffsetTime]("10:15:30"), None)
  }

  test("ZonedDateTime") {
    import java.time.ZonedDateTime
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[ZonedDateTime]("2016-12-02T11:15:30-05:00[US/Central]"), Some("2016-12-02T11:15:30-05:00[US/Central]"))
    assertEquals(validate[ZonedDateTime]("2016-12-02T11:15:30-05:00"), Some("2016-12-02T11:15:30-05:00"))
    assertEquals(validate[ZonedDateTime]("10:15:30"), None)
  }

  test("LocalTime") {
    import java.time.LocalTime
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[LocalTime]("11:21:19"), Some("11:21:19"))
    assertEquals(validate[LocalTime]("2000-12-31 11:21:19.0"), None)
  }

  test("HEX") {
    import com.github.gekomad.regexcollection.HEX
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[HEX]("2000-12-31 11:21:19.0"), None)
    assertEquals(validate[HEX]("#FAFAFA"), Some("#FAFAFA"))
    assertEquals(validate[HEX]("FAFAFA"), Some("FAFAFA"))
    assertEquals(validate[HEX]("#FAFA"), None)
  }

  test("HEX1") {
    import com.github.gekomad.regexcollection.HEX1
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[HEX1]("2000-12-31 11:21:19.0"), None)
    assertEquals(validate[HEX1]("#FAFAFA"), Some("#FAFAFA"))
    assertEquals(validate[HEX1]("FAFAFA"), None)
    assertEquals(validate[HEX1]("#FAFA"), None)
  }

  test("HEX2") {
    import com.github.gekomad.regexcollection.HEX2
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[HEX2]("2000-12-31 11:21:19.0"), None)
    assertEquals(validate[HEX2]("0x121212"), Some("0x121212"))
    assertEquals(validate[HEX2]("0x1212"), None)
    assertEquals(validate[HEX2]("FAFAFA"), None)
    assertEquals(validate[HEX2]("#ababab"), Some("#ababab"))
    assertEquals(validate[HEX2]("#abab"), None)
  }

  test("HEX3") {
    import com.github.gekomad.regexcollection.HEX3
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[HEX3]("2000-12-31 11:21:19.0"), None)
    assertEquals(validate[HEX3]("0x121212"), Some("0x121212"))
    assertEquals(validate[HEX3]("FAFAFA"), None)
    assertEquals(validate[HEX3]("#ababab"), None)
    assertEquals(validate[HEX3]("#abab"), None)
  }

  test("SHA1") {
    import com.github.gekomad.regexcollection.SHA1
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[SHA1]("2sdf.0"), None)
    assertEquals(validate[SHA1]("1c18da5dbf74e3fc1820469cf1f54355b7eec92d"), Some("1c18da5dbf74e3fc1820469cf1f54355b7eec92d"))
  }

  test("SHA256") {
    import com.github.gekomad.regexcollection.SHA256
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[SHA256]("2sdf.0"), None)
    assertEquals(
      validate[SHA256]("000020f89134d831f48541b2d8ec39397bc99fccf4cc86a3861257dbe6d819d1"),
      Some("000020f89134d831f48541b2d8ec39397bc99fccf4cc86a3861257dbe6d819d1")
    )
  }

  test("IP") {
    import com.github.gekomad.regexcollection.IP
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[IP]("1011.164.108.1"), None)
    assertEquals(validate[IP]("10.164.108.1"), Some("10.164.108.1"))
  }

  test("IP1") {
    import com.github.gekomad.regexcollection.IP1
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[IP1]("111.164.108.1:80"), Some("111.164.108.1:80"))
    assertEquals(validate[IP1]("10.164.108.1"), None)
  }

  test("Italian Fiscal Code") {
    import com.github.gekomad.regexcollection.ItalianFiscalCode
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[ItalianFiscalCode]("BDAPPP14A01A001R"), Some("BDAPPP14A01A001R"))
    assertEquals(validate[ItalianFiscalCode]("bdAPPP14A01A001R"), Some("bdAPPP14A01A001R"))
    assertEquals(validate[ItalianFiscalCode]("aaaaaaaaaaaaaaaa"), None)
  }

  test("Italian Vat") {
    import com.github.gekomad.regexcollection.ItalianVAT
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[ItalianVAT]("13297040362"), Some("13297040362"))
    assertEquals(validate[ItalianVAT]("bdAPPP14A01A001R"), None)

  }

  test("IP6") {
    import com.github.gekomad.regexcollection.IP_6
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[IP_6]("10.164.108.1"), None)
    assertEquals(validate[IP_6]("2001:db8:0:0:0:0:2:1"), Some("2001:db8:0:0:0:0:2:1"))
    assertEquals(validate[IP_6]("2001:db8:a0b:12f0::1"), Some("2001:db8:a0b:12f0::1"))
  }

  test("Domain") {
    import com.github.gekomad.regexcollection.Domain
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[Domain]("2sdf.0"), None)
    assertEquals(validate[Domain]("https://plus.google.com/113849558824288265773"), None)
    assertEquals(validate[Domain]("https://www.aaa.com"), None)
    assertEquals(validate[Domain]("plus.google.com"), Some("plus.google.com"))
    assertEquals(validate[Domain]("google.com"), Some("google.com"))
  }

  test("URL") {
    import com.github.gekomad.regexcollection.URL
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[URL]("www.aaa.com"), None)
    assertEquals(validate[URL]("http://www.aaa.com"), Some("http://www.aaa.com"))
    assertEquals(validate[URL]("https://www.aaa.com"), Some("https://www.aaa.com"))
    assertEquals(validate[URL]("a://b"), Some("a://b"))
    assertEquals(validate[URL]("ftp://b"), Some("ftp://b"))
  }

  test("URL1") {
    import com.github.gekomad.regexcollection.URL1
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[URL1]("2sdf.0"), None)
    assertEquals(validate[URL1]("http://www.aaa.com"), Some("http://www.aaa.com"))
    assertEquals(validate[URL1]("Https://www.aaa.com"), Some("Https://www.aaa.com"))
    assertEquals(validate[URL1]("a://b"), None)
    assertEquals(validate[URL1]("https://www.google.com:8080/url?"), Some("https://www.google.com:8080/url?"))
    assertEquals(validate[URL1]("HTTP://www.example.com/wpstyle/?p=364"), Some("HTTP://www.example.com/wpstyle/?p=364"))
    assertEquals(validate[URL1]("http://foo.com/blah_(wikipedia)#cite-1"), None)
    assertEquals(validate[URL1]("http://1337.net"), None)
    assertEquals(validate[URL1]("http://例子.测试"), None)
  }

  test("URL2") {
    import com.github.gekomad.regexcollection.URL2
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[URL2]("2sdf.0"), None)
    assertEquals(validate[URL2]("http://www.aaa.com"), Some("http://www.aaa.com"))
    assertEquals(validate[URL2]("HTTPS://www.aaa.com"), None)
    assertEquals(validate[URL2]("a://b"), None)
    assertEquals(validate[URL2]("https://www.google.com:8080/url?"), None)
    assertEquals(validate[URL2]("HTTP://www.example.com/wpstyle/?p=364"), Some("HTTP://www.example.com/wpstyle/?p=364"))
    assertEquals(validate[URL2]("http://foo.com/blah_(wikipedia)#cite-1"), None)
    assertEquals(validate[URL2]("http://1337.net"), None)
    assertEquals(validate[URL2]("http://例子.测试"), None)
  }

  test("URL3") {
    import com.github.gekomad.regexcollection.URL3
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[URL3]("2sdf.0"), None)
    assertEquals(validate[URL3]("http://www.aaa.com"), None)
    assertEquals(validate[URL3]("HTTPS://www.aaa.com"), Some("HTTPS://www.aaa.com"))
    assertEquals(validate[URL3]("a://b"), None)
    assertEquals(validate[URL3]("https://www.google.com:8080/url?"), Some("https://www.google.com:8080/url?"))
    assertEquals(validate[URL3]("HTTP://www.example.com/wpstyle/?p=364"), None)
    assertEquals(validate[URL3]("http://foo.com/blah_(wikipedia)#cite-1"), None)
    assertEquals(validate[URL3]("http://1337.net"), None)
    assertEquals(validate[URL3]("http://例子.测试"), None)
  }

  test("FTP") {
    import com.github.gekomad.regexcollection.FTP
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[FTP]("2sdf.0"), None)
    assertEquals(validate[FTP]("FTP://aaa.com"), Some("FTP://aaa.com"))
    assertEquals(validate[FTP]("FTPS://www.aaa.com"), Some("FTPS://www.aaa.com"))
    assertEquals(validate[FTP]("a://b"), None)
    assertEquals(validate[FTP]("ftps://www.google.com:8080/url?"), Some("ftps://www.google.com:8080/url?"))
    assertEquals(validate[FTP]("ftp://www.example.com/wpstyle/?p=364"), Some("ftp://www.example.com/wpstyle/?p=364"))
    assertEquals(validate[FTP]("http://foo.com/blah_(wikipedia)#cite-1"), None)
    assertEquals(validate[FTP]("http://1337.net"), None)
    assertEquals(validate[FTP]("http://例子.测试"), None)
  }

  test("FTP1") {
    import com.github.gekomad.regexcollection.FTP1
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[FTP1]("2sdf.0"), None)
    assertEquals(validate[FTP1]("FTP://aaa.com"), Some("FTP://aaa.com"))
    assertEquals(validate[FTP1]("FTPS://www.aaa.com"), None)
    assertEquals(validate[FTP1]("a://b"), None)
    assertEquals(validate[FTP1]("FTPs://www.google.com:8080/url?"), None)
    assertEquals(validate[FTP1]("FTP://www.example.com/wpstyle/?p=364"), Some("FTP://www.example.com/wpstyle/?p=364"))
    assertEquals(validate[FTP1]("http://foo.com/blah_(wikipedia)#cite-1"), None)
    assertEquals(validate[FTP1]("http://1337.net"), None)
    assertEquals(validate[FTP1]("http://例子.测试"), None)
  }

  test("FTP2") {
    import com.github.gekomad.regexcollection.FTP2
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[FTP2]("2sdf.0"), None)
    assertEquals(validate[FTP2]("FTP://aaa.com"), None)
    assertEquals(validate[FTP2]("FTPS://www.aaa.com"), Some("FTPS://www.aaa.com"))
    assertEquals(validate[FTP2]("a://b"), None)
    assertEquals(validate[FTP2]("ftps://www.google.com:8080/url?"), Some("ftps://www.google.com:8080/url?"))
    assertEquals(validate[FTP2]("ftp://www.example.com/wpstyle/?p=364"), None)
    assertEquals(validate[FTP2]("http://foo.com/blah_(wikipedia)#cite-1"), None)
    assertEquals(validate[FTP2]("http://1337.net"), None)
    assertEquals(validate[FTP2]("http://例子.测试"), None)
  }

  test("Bitcoin Address") {
    import com.github.gekomad.regexcollection.BitcoinAdd
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[BitcoinAdd]("2sdf.0"), None)

    assertEquals(validate[BitcoinAdd]("3Nxwenay9Z8Lc9JBiywExpnEFiLp6Afp8v"), Some("3Nxwenay9Z8Lc9JBiywExpnEFiLp6Afp8v"))
  }

  test("USphone Number") {
    import com.github.gekomad.regexcollection.USphoneNumber
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[USphoneNumber]("2sdf.0"), None)

    assertEquals(validate[USphoneNumber]("555-555-5555"), Some("555-555-5555"))
    assertEquals(validate[USphoneNumber]("1 416 555 9292"), Some("1 416 555 9292"))
    assertEquals(validate[USphoneNumber]("4035555678"), Some("4035555678"))
    assertEquals(validate[USphoneNumber]("(416)555-3456"), Some("(416)555-3456"))
  }

  test("Youtube") {
    import com.github.gekomad.regexcollection.Youtube
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[Youtube]("2sdf.0"), None)
    assertEquals(validate[Youtube]("HTTPS://www.youtube.com/watch?v=9bZkp7q19f0"), Some("HTTPS://www.youtube.com/watch?v=9bZkp7q19f0"))
    assertEquals(
      validate[Youtube]("https://WWW.youtube.com/watch?feature=something&v=videoid1&embed=something"),
      Some("https://WWW.youtube.com/watch?feature=something&v=videoid1&embed=something")
    )
    assertEquals(
      validate[Youtube]("www.youtube.com/watch?feature=something&v=videoid2&embed=something"),
      Some("www.youtube.com/watch?feature=something&v=videoid2&embed=something")
    )
    assertEquals(validate[Youtube]("Https://Www.youtube.com/watch?v=videoid1"), Some("Https://Www.youtube.com/watch?v=videoid1"))
    assertEquals(validate[Youtube]("youtube.com/watch?v=videoid4"), Some("youtube.com/watch?v=videoid4"))
    assertEquals(validate[Youtube]("https://www.youtube.com/channel/channelid1"), Some("https://www.youtube.com/channel/channelid1"))
    assertEquals(validate[Youtube]("https://www.youtube.com/user/username1"), Some("https://www.youtube.com/user/username1"))
    assertEquals(validate[Youtube]("https://www.youtube.com/results?search_query=search+query1"), Some("https://www.youtube.com/results?search_query=search+query1"))
    assertEquals(validate[Youtube]("youtube.com/results?search_query=search+query4"), Some("youtube.com/results?search_query=search+query4"))
    assertEquals(validate[Youtube]("http://www.youtube.com/results?search_query=search+query1"), None)
  }

  test("Crontab expression") {
    import com.github.gekomad.regexcollection.Cron
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[Cron]("2sdf.0"), None)

    assertEquals(validate[Cron]("5 4 * * *"), Some("5 4 * * *"))
    assertEquals(validate[Cron]("5 4 d* * *"), None)
  }

  test("Time24") {
    import com.github.gekomad.regexcollection.Time24
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[Time24]("2sdf.0"), None)
    assertEquals(validate[Time24]("23:50:00"), Some("23:50:00"))
    assertEquals(validate[Time24]("14:00"), Some("14:00"))
    assertEquals(validate[Time24]("23:00"), Some("23:00"))
    assertEquals(validate[Time24]("9:30"), Some("9:30"))
    assertEquals(validate[Time24]("19:30"), Some("19:30"))
    assertEquals(validate[Time24]("25:00"), None)
  }

  test("YenCurrency") {
    import com.github.gekomad.regexcollection.YenCurrency
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[YenCurrency]("2sdf.0"), None)
    assertEquals(validate[YenCurrency]("¥150000000.00"), Some("¥150000000.00"))
    assertEquals(validate[YenCurrency]("¥1.00"), Some("¥1.00"))
    assertEquals(validate[YenCurrency]("15.00"), Some("15.00"))
    assertEquals(validate[YenCurrency]("-150.00"), Some("-150.00"))
    assertEquals(validate[YenCurrency]("1500.00"), Some("1500.00"))
    assertEquals(validate[YenCurrency]("1,500.00"), Some("1,500.00"))
    assertEquals(validate[YenCurrency]("¥0.20"), Some("¥0.20"))
    assertEquals(validate[YenCurrency]("¥-1213,120.00"), Some("¥-1213,120.00"))
    assertEquals(validate[YenCurrency]("25:00"), None)
  }

  test("UsdCurrency") {
    import com.github.gekomad.regexcollection.UsdCurrency
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[UsdCurrency]("2sdf.0"), None)
    assertEquals(validate[UsdCurrency]("$150000000.00"), Some("$150000000.00"))
    assertEquals(validate[UsdCurrency]("$1.00"), Some("$1.00"))
    assertEquals(validate[UsdCurrency]("15.00"), Some("15.00"))
    assertEquals(validate[UsdCurrency]("-150.00"), Some("-150.00"))
    assertEquals(validate[UsdCurrency]("1500.00"), Some("1500.00"))
    assertEquals(validate[UsdCurrency]("1,500.00"), Some("1,500.00"))
    assertEquals(validate[UsdCurrency]("$0.20"), Some("$0.20"))
    assertEquals(validate[UsdCurrency]("$-1213,120.00"), Some("$-1213,120.00"))
    assertEquals(validate[UsdCurrency]("25:00"), None)
  }

  test("EurCurrency") {
    import com.github.gekomad.regexcollection.EurCurrency
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[EurCurrency]("2sdf.0"), None)
    assertEquals(validate[EurCurrency]("00,00 €"), None)
    assertEquals(validate[EurCurrency]("0,00 €"), Some("0,00 €"))
    assertEquals(validate[EurCurrency]("1,-- EURO"), Some("1,-- EURO"))
    assertEquals(validate[EurCurrency]("1 234 567,89 EUR"), Some("1 234 567,89 EUR"))
    assertEquals(validate[EurCurrency]("1 234 567,89 €"), Some("1 234 567,89 €"))
    assertEquals(validate[EurCurrency]("133,89 €"), Some("133,89 €"))
    assertEquals(validate[EurCurrency]("133,89 EUR"), Some("133,89 EUR"))
    assertEquals(validate[EurCurrency]("133,89 EURO"), Some("133,89 EURO"))
  }

  test("Number1") {
    import com.github.gekomad.regexcollection.Number1
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[Number1]("2sdf.0"), None)
    assertEquals(validate[Number1]("99.99"), Some("99.99"))
    assertEquals(validate[Number1]("1.1"), Some("1.1"))
    assertEquals(validate[Number1]("99"), Some("99"))
    assertEquals(validate[Number1]("99.123"), None)
    assertEquals(validate[Number1]("991.11"), None)
    assertEquals(validate[Number1](".99"), Some(".99"))
    assertEquals(validate[Number1]("-1"), None)
    assertEquals(validate[Number1]("-1.33"), None)
  }

  test("Unsigned32") {
    import com.github.gekomad.regexcollection.Unsigned32
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[Unsigned32]("2sdf.0"), None)
    assertEquals(validate[Unsigned32]("99.99"), None)
    assertEquals(validate[Unsigned32]("0"), Some("0"))
    assertEquals(validate[Unsigned32]("99"), Some("99"))
    assertEquals(validate[Unsigned32]("4294967295"), Some("4294967295"))
    assertEquals(validate[Unsigned32]("4294967296"), None)
    assertEquals(validate[Unsigned32](".99"), None)
    assertEquals(validate[Unsigned32]("-1"), None)
  }

  test("Signed") {
    import com.github.gekomad.regexcollection.Signed
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[Signed]("2sdf.0"), None)
    assertEquals(validate[Signed]("99.99"), None)
    assertEquals(validate[Signed]("0"), Some("0"))
    assertEquals(validate[Signed]("11111111111111111111111110"), Some("11111111111111111111111110"))
    assertEquals(validate[Signed]("99"), Some("99"))
    assertEquals(validate[Signed]("+99"), Some("+99"))
    assertEquals(validate[Signed]("-99"), Some("-99"))
    assertEquals(validate[Signed]("4294967295"), Some("4294967295"))
    assertEquals(validate[Signed]("4294967296"), Some("4294967296"))
    assertEquals(validate[Signed](".99"), None)
    assertEquals(validate[Signed]("-1"), Some("-1"))
  }

  test("Scientific") {
    import com.github.gekomad.regexcollection.Scientific
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[Scientific]("10.1"), None)
    assertEquals(validate[Scientific]("100"), None)
    assertEquals(validate[Scientific]("3.7E-11"), Some("3.7E-11"))
    assertEquals(validate[Scientific]("-2.384E-03"), Some("-2.384E-03"))
    assertEquals(validate[Scientific]("9.4608e15"), Some("9.4608e15"))
  }

  test("USstreetNumber") {
    import com.github.gekomad.regexcollection.USstreetNumber
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[USstreetNumber]("po box 432"), None)
    assertEquals(validate[USstreetNumber]("GG4321"), None)
    assertEquals(validate[USstreetNumber]("48392021"), None)
    assertEquals(validate[USstreetNumber]("4444"), Some("4444"))
    assertEquals(validate[USstreetNumber]("G 4444"), Some("G 4444"))
    assertEquals(validate[USstreetNumber]("333/555"), Some("333/555"))
    assertEquals(validate[USstreetNumber]("P.O. Box 432"), Some("P.O. Box 432"))
    assertEquals(validate[USstreetNumber]("RR 4 Box 56"), Some("RR 4 Box 56"))
  }

  test("SingleChar") {
    import com.github.gekomad.regexcollection.SingleChar
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[SingleChar]("aa"), None)
    assertEquals(validate[SingleChar]("1"), None)
    assertEquals(validate[SingleChar]("a"), Some("a"))
    assertEquals(validate[SingleChar]("A"), Some("A"))
  }

  test("AZString") {
    import com.github.gekomad.regexcollection.AZString
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[AZString]("1"), None)
    assertEquals(validate[AZString]("テ"), None)
    assertEquals(validate[AZString]("abc"), Some("abc"))
    assertEquals(validate[AZString]("AbC"), Some("AbC"))
  }

  test("SingleNumber") {
    import com.github.gekomad.regexcollection.SingleNumber
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[SingleNumber]("11"), None)
    assertEquals(validate[SingleNumber]("a"), None)
    assertEquals(validate[SingleNumber]("1"), Some("1"))
  }

  test("StringAndNumber") {
    import com.github.gekomad.regexcollection.StringAndNumber
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[StringAndNumber]("テ"), None)
    assertEquals(validate[StringAndNumber]("1%"), None)
    assertEquals(validate[StringAndNumber]("1"), Some("1"))
    assertEquals(validate[StringAndNumber]("11"), Some("11"))
    assertEquals(validate[StringAndNumber]("a1"), Some("a1"))
    assertEquals(validate[StringAndNumber]("Aa1"), Some("Aa1"))
  }

  test("AsciiString") {
    import com.github.gekomad.regexcollection.AsciiString
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[AsciiString]("テ"), None)
    assertEquals(validate[AsciiString]("11"), Some("11"))
    assertEquals(validate[AsciiString]("a1%"), Some("a1%"))
    assertEquals(validate[AsciiString]("Aa1"), Some("Aa1"))
  }

  test("USstreets") {
    import com.github.gekomad.regexcollection.USstreets
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[USstreets]("123 Park Ave Apt 123 New York City, NY xxx"), None)
    assertEquals(validate[USstreets]("123 Park Ave Apt 123 New York City, NY 10002"), Some("123 Park Ave Apt 123 New York City, NY 10002"))
    assertEquals(validate[USstreets]("C/O John Paul, POBox 456, Motown, CA 96090"), Some("C/O John Paul, POBox 456, Motown, CA 96090"))
  }

  test("GermanStreet") {
    import com.github.gekomad.regexcollection.GermanStreet
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[GermanStreet]("Kaiserallee"), None)
    assertEquals(validate[GermanStreet]("Kaiser-Wilhelm-Allee 1aa"), None)
    assertEquals(validate[GermanStreet]("1 Kaiserstrasse"), None)
    assertEquals(validate[GermanStreet]("Kaiserallee 1"), Some("Kaiserallee 1"))
    assertEquals(validate[GermanStreet]("Kaiser-Wilhelm-Allee 1111a"), Some("Kaiser-Wilhelm-Allee 1111a"))
    assertEquals(validate[GermanStreet]("Mühlenstr. 33"), Some("Mühlenstr. 33"))
  }

  test("Coordinate") {
    import com.github.gekomad.regexcollection.Coordinate
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[Coordinate]("N91.00.00 E181.00.00"), None)
    assertEquals(validate[Coordinate]("N00.00.00 W181.00.00"), None)
    assertEquals(validate[Coordinate]("Z34.59.33 W179.59.59"), None)
    assertEquals(validate[Coordinate]("N90.00.00 E180.00.00"), Some("N90.00.00 E180.00.00"))
    assertEquals(validate[Coordinate]("S34.59.33 W179.59.59"), Some("S34.59.33 W179.59.59"))
    assertEquals(validate[Coordinate]("N00.00.00 W000.00.00"), Some("N00.00.00 W000.00.00"))
  }

  test("USstates") {
    import com.github.gekomad.regexcollection.USstates
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[USstates]("Italy"), None)
    assertEquals(validate[USstates]("OO"), None)
    assertEquals(validate[USstates]("FL"), Some("FL"))
    assertEquals(validate[USstates]("CA"), Some("CA"))
    assertEquals(validate[USstates]("OH"), Some("OH"))
  }

  test("USstates1") {
    import com.github.gekomad.regexcollection.USstates1
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[USstates1]("Italy"), None)
    assertEquals(validate[USstates1]("FL"), None)
    assertEquals(validate[USstates1]("Connecticut"), Some("Connecticut"))
    assertEquals(validate[USstates1]("North Carolina"), Some("North Carolina"))
  }

  test("Celsius") {
    import com.github.gekomad.regexcollection.Celsius
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[Celsius]("133"), None)
    assertEquals(validate[Celsius]("2 °C"), Some("2 °C"))
    assertEquals(validate[Celsius]("+2 °C"), Some("+2 °C"))
    assertEquals(validate[Celsius]("-2.2 °C"), Some("-2.2 °C"))
  }

  test("Fahrenheit") {
    import com.github.gekomad.regexcollection.Fahrenheit
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[Fahrenheit]("133"), None)
    assertEquals(validate[Fahrenheit]("2 °F"), Some("2 °F"))
    assertEquals(validate[Fahrenheit]("+2 °F"), Some("+2 °F"))
    assertEquals(validate[Fahrenheit]("-2.2 °F"), Some("-2.2 °F"))
  }

  test("Coordinate1") {
    import com.github.gekomad.regexcollection.Coordinate1
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[Coordinate1]("""45°23'36." N 10°33'48.0" E"""), None)
    assertEquals(validate[Coordinate1]("""45°23'36.1234567"N 010°33'48"E"""), None)
    assertEquals(validate[Coordinate1]("""45°23'36.0" N 10°33'48.0" E"""), Some("""45°23'36.0" N 10°33'48.0" E"""))
    assertEquals(validate[Coordinate1]("""45°23'36.0" N 10°33'48.0″ E"""), Some("""45°23'36.0" N 10°33'48.0″ E"""))
    assertEquals(validate[Coordinate1]("""45°23′36.0" N 10°33'48.0" E"""), Some("""45°23′36.0" N 10°33'48.0" E"""))
    assertEquals(validate[Coordinate1]("""45°23′36.0″ N 10°33′48.0″ E"""), Some("""45°23′36.0″ N 10°33′48.0″ E"""))
    assertEquals(validate[Coordinate1]("""45°23'36.123456"N 010°33'48"E"""), Some("""45°23'36.123456"N 010°33'48"E"""))
  }

  test("Coordinate2") {
    import com.github.gekomad.regexcollection.Coordinate2
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[Coordinate2]("""12.2225"""), None)
    assertEquals(validate[Coordinate2]("""15.25.257S"""), None)
    assertEquals(validate[Coordinate2]("""AA:BB:CC.DDS"""), None)

    assertEquals(validate[Coordinate2]("""12:12:12.223546"N"""), Some("""12:12:12.223546"N"""))
    assertEquals(validate[Coordinate2]("""12:12:12.2246N"""), Some("""12:12:12.2246N"""))
    assertEquals(validate[Coordinate2]("""15:17:6"S"""), Some("""15:17:6"S"""))
    assertEquals(validate[Coordinate2]("""12°30'23.256547"S"""), Some("""12°30'23.256547"S"""))
  }

  test("MACAddress") {
    import com.github.gekomad.regexcollection.MACAddress
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[MACAddress]("""ab"""), None)
    assertEquals(validate[MACAddress]("""01:23:45:67:89:a"""), None)

    assertEquals(validate[MACAddress]("""fE:dC:bA:98:76:54"""), Some("""fE:dC:bA:98:76:54"""))
    assertEquals(validate[MACAddress]("""01:23:45:67:89:AB"""), Some("""01:23:45:67:89:AB"""))
    assertEquals(validate[MACAddress]("""01:23:45:67:89:ab"""), Some("""01:23:45:67:89:ab"""))
  }

  test("Time") {
    import com.github.gekomad.regexcollection.Time
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[Time]("8am"), Some("8am"))
    assertEquals(validate[Time]("8 pm"), Some("8 pm"))
    assertEquals(validate[Time]("11 PM"), Some("11 PM"))
    assertEquals(validate[Time]("8:00 am"), Some("8:00 am"))

    assertEquals(validate[Time]("8a"), None)
    assertEquals(validate[Time]("8 a"), None)
    assertEquals(validate[Time]("8:00 a"), None)
  }

  test("Twitter") {
    import com.github.gekomad.regexcollection.Twitter
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[Twitter]("http://twitter.com/rtpharry/"), None)
    assertEquals(validate[Twitter]("https://twitter.com/rtpharry/"), Some("https://twitter.com/rtpharry/"))
    assertEquals(validate[Twitter]("https://twitter.com/rtpharry"), Some("https://twitter.com/rtpharry"))
    assertEquals(validate[Twitter]("https://twitter.com/#!/rtpharry/"), Some("https://twitter.com/#!/rtpharry/"))
  }

  test("Facebook") {
    import com.github.gekomad.regexcollection.Facebook
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[Facebook]("http://www.facebook.com/thesimpsons"), None)
    assertEquals(validate[Facebook]("www.facebook.com/thesimpsons"), None)
    assertEquals(validate[Facebook]("https://facebook.com/pages/Andy-Worthington/196377860390800"), None)
    assertEquals(validate[Facebook]("www.facebook.com/pages/Andy-Worthington/196377860390800"), None)
    assertEquals(
      validate[Facebook]("https://www.facebook.com/pages/Andy-Worthington/196377860390800"),
      Some("https://www.facebook.com/pages/Andy-Worthington/196377860390800")
    )
    assertEquals(validate[Facebook]("https://www.facebook.com/pages/"), Some("https://www.facebook.com/pages/"))
    assertEquals(validate[Facebook]("https://www.facebook.com/thesimpsons"), Some("https://www.facebook.com/thesimpsons"))

  }

  test("ApacheError") {
    import com.github.gekomad.regexcollection.ApacheError
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[ApacheError]("[Fri Dec 16 02:25:55 2005] [error]  [client 1.2.3.4] Client sent malformed Host header"), None)
    assertEquals(
      validate[ApacheError]("[Fri Dec 16 02:25:55 2005] [error] [client 1.2.3.4] Client sent malformed Host header"),
      Some(
        "[Fri Dec 16 02:25:55 2005] [error] [client 1.2.3.4] Client sent malformed Host header"
      )
    )

  }

  test("Percentage") {
    import com.github.gekomad.regexcollection.Percentage
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[Percentage]("""10"""), None)
    assertEquals(validate[Percentage]("""101%"""), None)
    assertEquals(validate[Percentage]("""-10%"""), None)
    assertEquals(validate[Percentage]("""1.1%"""), None)

    assertEquals(validate[Percentage]("""0%"""), Some("""0%"""))
    assertEquals(validate[Percentage]("""10%"""), Some("""10%"""))
    assertEquals(validate[Percentage]("""100%"""), Some("""100%"""))
    assertEquals(validate[Percentage]("""99%"""), Some("""99%"""))

  }

  test("USZipCode") {
    import com.github.gekomad.regexcollection.USZipCode
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[USZipCode]("""443073"""), None)
    assertEquals(validate[USZipCode]("""2"""), None)
    assertEquals(validate[USZipCode]("""-44307"""), None)

    assertEquals(validate[USZipCode]("""43802"""), Some("""43802"""))
    assertEquals(validate[USZipCode]("""44307"""), Some("""44307"""))

  }

  test("ItalianPhone") {
    import com.github.gekomad.regexcollection.ItalianPhone
    import com.github.gekomad.regexcollection.Validate.validate

    assertEquals(validate[ItalianPhone]("""+3902/583725"""), None)
    assertEquals(validate[ItalianPhone]("""023232323"""), None)
    assertEquals(validate[ItalianPhone]("""02 645566"""), Some("""02 645566"""))
    assertEquals(validate[ItalianPhone]("""02/583725"""), Some("""02/583725"""))
    assertEquals(validate[ItalianPhone]("""02-583725"""), Some("""02-583725"""))

  }

  test("ItalianMobilePhone") {
    import com.github.gekomad.regexcollection.ItalianMobilePhone
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[ItalianMobilePhone]("""+39 3401234567"""), None)
    assertEquals(validate[ItalianMobilePhone]("""347 1234567"""), None)
    assertEquals(validate[ItalianMobilePhone]("""338-1234567"""), None)
    assertEquals(validate[ItalianMobilePhone]("""02343434"""), None)

    assertEquals(validate[ItalianMobilePhone]("""+393471234561"""), Some("""+393471234561"""))
    assertEquals(validate[ItalianMobilePhone]("""3381234561"""), Some("""3381234561"""))

  }

  test("ItalianZipCode") {
    import com.github.gekomad.regexcollection.ItalianZipCode
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[ItalianZipCode]("""1234"""), None)
    assertEquals(validate[ItalianZipCode]("""1996"""), None)
    assertEquals(validate[ItalianZipCode]("""-23887"""), None)

    assertEquals(validate[ItalianZipCode]("""23887"""), Some("""23887"""))
    assertEquals(validate[ItalianZipCode]("""23001"""), Some("""23001"""))
    assertEquals(validate[ItalianZipCode]("""20066"""), Some("""20066"""))

  }

  test("ItalianIban") {
    import com.github.gekomad.regexcollection.ItalianIban
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[ItalianIban]("""IT28-W800-0000-2921-0064-5211-151"""), None)

    assertEquals(validate[ItalianIban]("""IT28 W800 0000 2921 0064 5211 151"""), Some("""IT28 W800 0000 2921 0064 5211 151"""))
    assertEquals(validate[ItalianIban]("""IT28W8000000292100645211151"""), Some("""IT28W8000000292100645211151"""))

  }

  test("NotASCII") {
    import com.github.gekomad.regexcollection.NotASCII
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[NotASCII]("2sdf.0"), None)
    assertEquals(validate[NotASCII]("　前に来た時は北側からで、当時の光景はいまでも思い出せる。"), Some("　前に来た時は北側からで、当時の光景はいまでも思い出せる。"))
    assertEquals(validate[NotASCII]("の中央には純白のホワイトパレス"), Some("の中央には純白のホワイトパレス"))
    assertEquals(validate[NotASCII]("　……あ。"), Some("　……あ。"))
    assertEquals(validate[NotASCII]("テスト。"), Some("テスト。"))
  }

  test("MDY") {
    import com.github.gekomad.regexcollection.MDY
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[MDY]("2sdf.0"), None)
    assertEquals(validate[MDY]("1/12/1902"), Some("1/12/1902"))
    assertEquals(validate[MDY]("12/31/1902"), Some("12/31/1902"))
    assertEquals(validate[MDY]("12/31/9999"), Some("12/31/9999"))
    assertEquals(validate[MDY]("12/31/19020"), None)
    assertEquals(validate[MDY]("-1/1/1900"), None)
    assertEquals(validate[MDY]("1/1/1900"), Some("1/1/1900"))
    assertEquals(validate[MDY]("1/-1/1900"), None)
    assertEquals(validate[MDY]("1/1/-1900"), None)
  }

  test("MDY2") {
    import com.github.gekomad.regexcollection.MDY2
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[MDY2]("2sdf.0"), None)
    assertEquals(validate[MDY2]("1-12-1902"), Some("1-12-1902"))
    assertEquals(validate[MDY2]("12-31-1902"), Some("12-31-1902"))
    assertEquals(validate[MDY2]("12-31-9999"), Some("12-31-9999"))
    assertEquals(validate[MDY2]("12-31-10000"), None)
    assertEquals(validate[MDY2]("12-31-19020"), None)
    assertEquals(validate[MDY2]("-1-1-1900"), None)
    assertEquals(validate[MDY2]("1-1-1900"), Some("1-1-1900"))
    assertEquals(validate[MDY2]("1--1-1900"), None)
    assertEquals(validate[MDY2]("1-1--1900"), None)
  }

  test("MDY3") {
    import com.github.gekomad.regexcollection.MDY3
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[MDY3]("2sdf.0"), None)
    assertEquals(validate[MDY3]("1/12/1902"), None)
    assertEquals(validate[MDY3]("12/31/2018"), Some("12/31/2018"))
    assertEquals(validate[MDY3]("12/31/9999"), Some("12/31/9999"))
    assertEquals(validate[MDY3]("12/31/10000"), None)
    assertEquals(validate[MDY3]("/1/1/1900"), None)
    assertEquals(validate[MDY3]("1/1/1900"), None)
    assertEquals(validate[MDY3]("2sdf.0"), None)
    assertEquals(validate[MDY3]("01/12/1902"), Some("01/12/1902"))
    assertEquals(validate[MDY3]("12/31/1902"), Some("12/31/1902"))
    assertEquals(validate[MDY3]("12/31/9999"), Some("12/31/9999"))
    assertEquals(validate[MDY3]("01/01/1900"), Some("01/01/1900"))
    assertEquals(validate[MDY3]("01//01/1900"), None)
    assertEquals(validate[MDY3]("01/01//1900"), None)
  }

  test("MDY4") {
    import com.github.gekomad.regexcollection.MDY4
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[MDY4]("2sdf.0"), None)
    assertEquals(validate[MDY4]("1-12-1902"), None)
    assertEquals(validate[MDY4]("12-31-2018"), Some("12-31-2018"))
    assertEquals(validate[MDY4]("12-31-9999"), Some("12-31-9999"))
    assertEquals(validate[MDY4]("12-31-10000"), None)
    assertEquals(validate[MDY4]("-1-1-1900"), None)
    assertEquals(validate[MDY4]("1-1-1900"), None)
    assertEquals(validate[MDY4]("2sdf.0"), None)
    assertEquals(validate[MDY4]("01-12-1902"), Some("01-12-1902"))
    assertEquals(validate[MDY4]("12-31-1902"), Some("12-31-1902"))
    assertEquals(validate[MDY4]("12-31-9999"), Some("12-31-9999"))
    assertEquals(validate[MDY4]("01-01-1900"), Some("01-01-1900"))
    assertEquals(validate[MDY4]("01--01-1900"), None)
    assertEquals(validate[MDY4]("01-01--1900"), None)
  }

  test("DMY") {
    import com.github.gekomad.regexcollection.DMY
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[DMY]("2sdf.0"), None)
    assertEquals(validate[DMY]("1/12/2"), Some("1/12/2"))
    assertEquals(validate[DMY]("12/31/1902"), None)
    assertEquals(validate[DMY]("31/12/1902"), Some("31/12/1902"))
    assertEquals(validate[DMY]("31/12/9999"), Some("31/12/9999"))
    assertEquals(validate[DMY]("12/31/9999"), None)
    assertEquals(validate[DMY]("12/31/19020"), None)
    assertEquals(validate[DMY]("-1/1/1900"), None)
    assertEquals(validate[DMY]("1/1/1900"), Some("1/1/1900"))
    assertEquals(validate[DMY]("1/-1/1900"), None)
    assertEquals(validate[DMY]("1/1/-1900"), None)
  }

  test("DMY2") {
    import com.github.gekomad.regexcollection.DMY2
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[DMY2]("2sdf.0"), None)
    assertEquals(validate[DMY2]("1-12-1902"), Some("1-12-1902"))
    assertEquals(validate[DMY2]("12-31-1902"), None)
    assertEquals(validate[DMY2]("31-12-1902"), Some("31-12-1902"))
    assertEquals(validate[DMY2]("31-12-9999"), Some("31-12-9999"))
    assertEquals(validate[DMY2]("31-12-10000"), None)
    assertEquals(validate[DMY2]("12-31-9999"), None)
    assertEquals(validate[DMY2]("12-31-19020"), None)
    assertEquals(validate[DMY2]("-1-1-1900"), None)
    assertEquals(validate[DMY2]("1-1-1900"), Some("1-1-1900"))
    assertEquals(validate[DMY2]("1--1-1900"), None)
    assertEquals(validate[DMY2]("1-1--1900"), None)
  }

  test("DMY3") {
    import com.github.gekomad.regexcollection.DMY3
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[DMY3]("2sdf.0"), None)
    assertEquals(validate[DMY3]("1/12/1902"), None)
    assertEquals(validate[DMY3]("01/12/1902"), Some("01/12/1902"))
    assertEquals(validate[DMY3]("12/31/1902"), None)
    assertEquals(validate[DMY3]("31/12/1902"), Some("31/12/1902"))
    assertEquals(validate[DMY3]("31/12/9999"), Some("31/12/9999"))
    assertEquals(validate[DMY3]("31/12/10000"), None)
    assertEquals(validate[DMY3]("12/31/9999"), None)
    assertEquals(validate[DMY3]("12/31/19020"), None)
    assertEquals(validate[DMY3]("/1/1/1900"), None)
    assertEquals(validate[DMY3]("01/01/1900"), Some("01/01/1900"))
    assertEquals(validate[DMY3]("1/1/1900"), None)
    assertEquals(validate[DMY3]("01/1/1900"), None)
    assertEquals(validate[DMY3]("1//1/1900"), None)
    assertEquals(validate[DMY3]("1/1//1900"), None)
  }

  test("DMY4") {
    import com.github.gekomad.regexcollection.DMY4
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[DMY4]("2sdf.0"), None)
    assertEquals(validate[DMY4]("1-12-1902"), None)
    assertEquals(validate[DMY4]("01-12-1902"), Some("01-12-1902"))
    assertEquals(validate[DMY4]("12-31-1902"), None)
    assertEquals(validate[DMY4]("31-12-1902"), Some("31-12-1902"))
    assertEquals(validate[DMY4]("31-12-9999"), Some("31-12-9999"))
    assertEquals(validate[DMY4]("31-12-10000"), None)
    assertEquals(validate[DMY4]("12-31-9999"), None)
    assertEquals(validate[DMY4]("12-31-19020"), None)
    assertEquals(validate[DMY4]("-1-1-1900"), None)
    assertEquals(validate[DMY4]("01-01-1900"), Some("01-01-1900"))
    assertEquals(validate[DMY4]("1-1-1900"), None)
    assertEquals(validate[DMY4]("01-1-1900"), None)
    assertEquals(validate[DMY4]("1--1-1900"), None)
    assertEquals(validate[DMY4]("1-1--1900"), None)
  }

  test("Custom type") {

    trait Bar
    import com.github.gekomad.regexcollection.Collection.Validator
    import com.github.gekomad.regexcollection.Validate.validate
    import com.github.gekomad.regexcollection.Validate.validateIgnoreCase
    implicit val myValidator: Validator[Bar] = Validator[Bar]("Bar.*")
    assertEquals(validate[Bar]("a string"), None)
    assertEquals(validate[Bar]("Bar foo"), Some("Bar foo"))
    assertEquals(validate[Bar]("bar foo"), None)
    assertEquals(validateIgnoreCase[Bar]("bar foo"), Some("bar foo"))

  }

  test("Function pattern") {
    import com.github.gekomad.regexcollection.Validate.validate
    trait Foo
    import com.github.gekomad.regexcollection.Collection.Validator
    import scala.util.Try

    implicit val validator: Validator[Foo] =
      Validator[Foo]((a: String) =>
        Try(a.toInt) match {
          case Failure(_) => None
          case Success(i) => if (i % 2 == 0) Option(a) else None
        }
      )

    assertEquals(validate[Foo]("42"), Some("42"))
    assertEquals(validate[Foo]("21"), None)
    assertEquals(validate[Foo]("hello"), None)

  }

  test("Comments") {
    import com.github.gekomad.regexcollection.Comments
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[Comments]("hi"), None)
    assertEquals(validate[Comments]("/*hi"), None)
    assertEquals(validate[Comments]("/*hi*/"), Some("/*hi*/"))
    assertEquals(
      validate[Comments]("""/*hi
        |foo * */""".stripMargin),
      Some("""/*hi
        |foo * */""".stripMargin)
    )
  }

  test("CreditCardVisa") {
    import com.github.gekomad.regexcollection.CreditCardVisa
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[CreditCardVisa]("4111x"), None)
    assertEquals(validate[CreditCardVisa]("411111a111111"), None)
    assertEquals(validate[CreditCardVisa]("4111111111111111"), Some("4111111111111111"))
    assertEquals(validate[CreditCardVisa]("4111111111111"), Some("4111111111111"))
  }

  test("CreditCarMasterCard") {
    import com.github.gekomad.regexcollection.CreditCardMasterCard
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[CreditCardMasterCard]("4111x"), None)
    assertEquals(validate[CreditCardMasterCard]("550000000a000004"), None)
    assertEquals(validate[CreditCardMasterCard]("5500000000000004"), Some("5500000000000004"))
  }

  test("CreditCarAmericanExpress") {
    import com.github.gekomad.regexcollection.CreditCardAmericanExpress
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[CreditCardAmericanExpress]("4111x"), None)
    assertEquals(validate[CreditCardAmericanExpress]("140000000000009"), None)
    assertEquals(validate[CreditCardAmericanExpress]("340000000000009"), Some("340000000000009"))
  }

  test("CreditCarDinersClub") {
    import com.github.gekomad.regexcollection.CreditCardinersClub
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[CreditCardinersClub]("4111x"), None)
    assertEquals(validate[CreditCardinersClub]("30000000000004"), Some("30000000000004"))
  }

  test("CreditCardDiscover") {
    import com.github.gekomad.regexcollection.CreditCardDiscover
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[CreditCardDiscover]("4111x"), None)
    assertEquals(validate[CreditCardDiscover]("6011000000000004"), Some("6011000000000004"))
  }

  test("CreditCardJCB") {
    import com.github.gekomad.regexcollection.CreditCardJCB
    import com.github.gekomad.regexcollection.Validate.validate
    assertEquals(validate[CreditCardJCB]("4111x"), None)
    assertEquals(validate[CreditCardJCB]("3588000000000009"), Some("3588000000000009"))
  }

}
