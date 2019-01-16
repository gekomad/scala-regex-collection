import org.scalatest.FunSuite

class Validate extends FunSuite {

  test("Email") {
    import com.github.gekomad.regexcollection.Validate.{regexp, validate}
    import com.github.gekomad.regexcollection.Email
    import com.github.gekomad.regexcollection.Collection.Validator

    {
      assert(regexp[Email] == """[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*""")
      assert(validate[Email]("sdsdsd@sdf.com") == Some("sdsdsd@sdf.com"))
      assert(validate[Email]("$sdsdsd@sdf.com") == Some("$sdsdsd@sdf.com"))
      assert(validate[Email]("bar@a.a") == Some("bar@a.a"))
      assert(validate[Email](" sdsdsd@sdf.com") == None)
      assert(validate[Email]("$@$.$") == None)
      assert(validate[Email]("abc,a@%.d") == None)

    }

    { //custom email pattern
      import com.github.gekomad.regexcollection.Email
      implicit val validator = Validator[Email](""".+@.+\..+""")
      assert(validate[Email]("abc,a@%.d") == Some("abc,a@%.d"))
    }

  }

  test("Email1") {
    import com.github.gekomad.regexcollection.Validate.validate
    import com.github.gekomad.regexcollection.Email1

    assert(validate[Email1]("sdsdsd@sdf.com") == Some("sdsdsd@sdf.com"))
    assert(validate[Email1]("$sdsdsd@sdf.com") == None)
    assert(validate[Email1](" sdsdsd@sdf.com") == None)
    assert(validate[Email1]("bar@a.a") == None)
    assert(validate[Email1]("$@$.$") == None)
    assert(validate[Email1]("bar@a.aa") == Some("bar@a.aa"))
    assert(validate[Email1]("abc,a@%.d") == None)
  }

  test("Href") {
    import com.github.gekomad.regexcollection.Validate.validate
    import com.github.gekomad.regexcollection.HtmlHref

    assert(validate[HtmlHref]("""href="www.yahoo.com"""") == Some("""href="www.yahoo.com""""))
    assert(validate[HtmlHref]("""href="http://localhost/blah/"""") == Some("""href="http://localhost/blah/""""))
    assert(validate[HtmlHref]("""href="https://localhost/blah/"""") == Some("""href="https://localhost/blah/""""))
    assert(validate[HtmlHref]("""href="eek"""") == Some("""href="eek""""))
    assert(validate[HtmlHref]("href=eek") == None)
    assert(validate[HtmlHref]("""href=""""") == None)
    assert(validate[HtmlHref]("""href="bad example"""") == None)
  }

  test("HtmlImg") {
    import com.github.gekomad.regexcollection.Validate.validate
    import com.github.gekomad.regexcollection.HtmlImg

    assert(
      validate[HtmlImg]("""<img alt="bar" height="178" src="https://www.aa.it/cce.jpg" width="316"/>"""") == Some("""<img alt="bar" height="178" src="https://www.aa.it/cce.jpg" width="316"/>"""")
    )
    assert(validate[HtmlImg]("""xxx"""") == Some("""xxx""""))
    assert(validate[HtmlImg]("""xxx"""") == Some("""xxx""""))
    assert(validate[HtmlImg]("""<a href="http://www.aaa.it/pxx/""""") == None)
    assert(
      validate[HtmlImg]("""<img alt="bar" height="178" src="https://www.aa.it/cce.jpg" width="316"/>"""") == Some("""<img alt="bar" height="178" src="https://www.aa.it/cce.jpg" width="316"/>"""")
    )
    assert(validate[HtmlImg]("""<h2 class="entry-title">"""") == Some("""<h2 class="entry-title">""""))
    assert(validate[HtmlImg]("aaaa") == None)

  }

  test("Email simple") {
    import com.github.gekomad.regexcollection.Validate.validate
    { //custom email pattern
      import com.github.gekomad.regexcollection.EmailSimple
      assert(validate[EmailSimple]("abc,a@%.d") == Some("abc,a@%.d"))
      assert(validate[EmailSimple]("a@b.c") == Some("a@b.c"))
      assert(validate[EmailSimple]("$@$.$") == Some("$@$.$"))
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
      import scala.util.Try
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

  test("Italian Vat") {
    import com.github.gekomad.regexcollection.ItalianVAT
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[ItalianVAT]("13297040362") == Some("13297040362"))
    assert(validate[ItalianVAT]("bdAPPP14A01A001R") == None)

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
    assert(validate[Cron]("5 4 d* * *") == None)
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

  test("YenCurrency") {
    import com.github.gekomad.regexcollection.YenCurrency
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[YenCurrency]("2sdf.0") == None)
    assert(validate[YenCurrency]("¥150000000.00") == Some("¥150000000.00"))
    assert(validate[YenCurrency]("¥1.00") == Some("¥1.00"))
    assert(validate[YenCurrency]("15.00") == Some("15.00"))
    assert(validate[YenCurrency]("-150.00") == Some("-150.00"))
    assert(validate[YenCurrency]("1500.00") == Some("1500.00"))
    assert(validate[YenCurrency]("1,500.00") == Some("1,500.00"))
    assert(validate[YenCurrency]("¥0.20") == Some("¥0.20"))
    assert(validate[YenCurrency]("¥-1213,120.00") == Some("¥-1213,120.00"))
    assert(validate[YenCurrency]("25:00") == None)
  }

  test("UsdCurrency") {
    import com.github.gekomad.regexcollection.UsdCurrency
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[UsdCurrency]("2sdf.0") == None)
    assert(validate[UsdCurrency]("$150000000.00") == Some("$150000000.00"))
    assert(validate[UsdCurrency]("$1.00") == Some("$1.00"))
    assert(validate[UsdCurrency]("15.00") == Some("15.00"))
    assert(validate[UsdCurrency]("-150.00") == Some("-150.00"))
    assert(validate[UsdCurrency]("1500.00") == Some("1500.00"))
    assert(validate[UsdCurrency]("1,500.00") == Some("1,500.00"))
    assert(validate[UsdCurrency]("$0.20") == Some("$0.20"))
    assert(validate[UsdCurrency]("$-1213,120.00") == Some("$-1213,120.00"))
    assert(validate[UsdCurrency]("25:00") == None)
  }

  test("EurCurrency") {
    import com.github.gekomad.regexcollection.EurCurrency
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[EurCurrency]("2sdf.0") == None)
    assert(validate[EurCurrency]("00,00 €") == None)
    assert(validate[EurCurrency]("0,00 €") == Some("0,00 €"))
    assert(validate[EurCurrency]("1,-- EURO") == Some("1,-- EURO"))
    assert(validate[EurCurrency]("1 234 567,89 EUR") == Some("1 234 567,89 EUR"))
    assert(validate[EurCurrency]("1 234 567,89 €") == Some("1 234 567,89 €"))
    assert(validate[EurCurrency]("133,89 €") == Some("133,89 €"))
    assert(validate[EurCurrency]("133,89 EUR") == Some("133,89 EUR"))
    assert(validate[EurCurrency]("133,89 EURO") == Some("133,89 EURO"))
  }

  test("Number1") {
    import com.github.gekomad.regexcollection.Number1
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[Number1]("2sdf.0") == None)
    assert(validate[Number1]("99.99") == Some("99.99"))
    assert(validate[Number1]("1.1") == Some("1.1"))
    assert(validate[Number1]("99") == Some("99"))
    assert(validate[Number1]("99.123") == None)
    assert(validate[Number1]("991.11") == None)
    assert(validate[Number1](".99") == Some(".99"))
    assert(validate[Number1]("-1") == None)
    assert(validate[Number1]("-1.33") == None)
  }

  test("Unsigned32") {
    import com.github.gekomad.regexcollection.Unsigned32
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[Unsigned32]("2sdf.0") == None)
    assert(validate[Unsigned32]("99.99") == None)
    assert(validate[Unsigned32]("0") == Some("0"))
    assert(validate[Unsigned32]("99") == Some("99"))
    assert(validate[Unsigned32]("4294967295") == Some("4294967295"))
    assert(validate[Unsigned32]("4294967296") == None)
    assert(validate[Unsigned32](".99") == None)
    assert(validate[Unsigned32]("-1") == None)
  }

  test("Signed") {
    import com.github.gekomad.regexcollection.Signed
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[Signed]("2sdf.0") == None)
    assert(validate[Signed]("99.99") == None)
    assert(validate[Signed]("0") == Some("0"))
    assert(validate[Signed]("11111111111111111111111110") == Some("11111111111111111111111110"))
    assert(validate[Signed]("99") == Some("99"))
    assert(validate[Signed]("+99") == Some("+99"))
    assert(validate[Signed]("-99") == Some("-99"))
    assert(validate[Signed]("4294967295") == Some("4294967295"))
    assert(validate[Signed]("4294967296") == Some("4294967296"))
    assert(validate[Signed](".99") == None)
    assert(validate[Signed]("-1") == Some("-1"))
  }

  test("Scientific") {
    import com.github.gekomad.regexcollection.Scientific
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[Scientific]("10.1") == None)
    assert(validate[Scientific]("100") == None)
    assert(validate[Scientific]("3.7E-11") == Some("3.7E-11"))
    assert(validate[Scientific]("-2.384E-03") == Some("-2.384E-03"))
    assert(validate[Scientific]("9.4608e15") == Some("9.4608e15"))
  }

  test("USstreetNumber") {
    import com.github.gekomad.regexcollection.USstreetNumber
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[USstreetNumber]("po box 432") == None)
    assert(validate[USstreetNumber]("GG4321") == None)
    assert(validate[USstreetNumber]("48392021") == None)
    assert(validate[USstreetNumber]("4444") == Some("4444"))
    assert(validate[USstreetNumber]("G 4444") == Some("G 4444"))
    assert(validate[USstreetNumber]("333/555") == Some("333/555"))
    assert(validate[USstreetNumber]("P.O. Box 432") == Some("P.O. Box 432"))
    assert(validate[USstreetNumber]("RR 4 Box 56") == Some("RR 4 Box 56"))
  }

  test("SingleChar") {
    import com.github.gekomad.regexcollection.SingleChar
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[SingleChar]("aa") == None)
    assert(validate[SingleChar]("1") == None)
    assert(validate[SingleChar]("a") == Some("a"))
    assert(validate[SingleChar]("A") == Some("A"))
  }

  test("AZString") {
    import com.github.gekomad.regexcollection.AZString
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[AZString]("1") == None)
    assert(validate[AZString]("テ") == None)
    assert(validate[AZString]("abc") == Some("abc"))
    assert(validate[AZString]("AbC") == Some("AbC"))
  }

  test("SingleNumber") {
    import com.github.gekomad.regexcollection.SingleNumber
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[SingleNumber]("11") == None)
    assert(validate[SingleNumber]("a") == None)
    assert(validate[SingleNumber]("1") == Some("1"))
  }

  test("StringAndNumber") {
    import com.github.gekomad.regexcollection.StringAndNumber
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[StringAndNumber]("テ") == None)
    assert(validate[StringAndNumber]("1%") == None)
    assert(validate[StringAndNumber]("1") == Some("1"))
    assert(validate[StringAndNumber]("11") == Some("11"))
    assert(validate[StringAndNumber]("a1") == Some("a1"))
    assert(validate[StringAndNumber]("Aa1") == Some("Aa1"))
  }

  test("AsciiString") {
    import com.github.gekomad.regexcollection.AsciiString
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[AsciiString]("テ") == None)
    assert(validate[AsciiString]("11") == Some("11"))
    assert(validate[AsciiString]("a1%") == Some("a1%"))
    assert(validate[AsciiString]("Aa1") == Some("Aa1"))
  }

  test("USstreets") {
    import com.github.gekomad.regexcollection.USstreets
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[USstreets]("123 Park Ave Apt 123 New York City, NY xxx") == None)
    assert(validate[USstreets]("123 Park Ave Apt 123 New York City, NY 10002") == Some("123 Park Ave Apt 123 New York City, NY 10002"))
    assert(validate[USstreets]("C/O John Paul, POBox 456, Motown, CA 96090") == Some("C/O John Paul, POBox 456, Motown, CA 96090"))
  }

  test("GermanStreet") {
    import com.github.gekomad.regexcollection.GermanStreet
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[GermanStreet]("Kaiserallee") == None)
    assert(validate[GermanStreet]("Kaiser-Wilhelm-Allee 1aa") == None)
    assert(validate[GermanStreet]("1 Kaiserstrasse") == None)
    assert(validate[GermanStreet]("Kaiserallee 1") == Some("Kaiserallee 1"))
    assert(validate[GermanStreet]("Kaiser-Wilhelm-Allee 1111a") == Some("Kaiser-Wilhelm-Allee 1111a"))
    assert(validate[GermanStreet]("Mühlenstr. 33") == Some("Mühlenstr. 33"))
  }

  test("Coordinate") {
    import com.github.gekomad.regexcollection.Coordinate
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[Coordinate]("N91.00.00 E181.00.00") == None)
    assert(validate[Coordinate]("N00.00.00 W181.00.00") == None)
    assert(validate[Coordinate]("Z34.59.33 W179.59.59") == None)
    assert(validate[Coordinate]("N90.00.00 E180.00.00") == Some("N90.00.00 E180.00.00"))
    assert(validate[Coordinate]("S34.59.33 W179.59.59") == Some("S34.59.33 W179.59.59"))
    assert(validate[Coordinate]("N00.00.00 W000.00.00") == Some("N00.00.00 W000.00.00"))
  }

  test("USstates") {
    import com.github.gekomad.regexcollection.USstates
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[USstates]("Italy") == None)
    assert(validate[USstates]("OO") == None)
    assert(validate[USstates]("FL") == Some("FL"))
    assert(validate[USstates]("CA") == Some("CA"))
    assert(validate[USstates]("OH") == Some("OH"))
  }

  test("USstates1") {
    import com.github.gekomad.regexcollection.USstates1
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[USstates1]("Italy") == None)
    assert(validate[USstates1]("FL") == None)
    assert(validate[USstates1]("Connecticut") == Some("Connecticut"))
    assert(validate[USstates1]("North Carolina") == Some("North Carolina"))
  }

  test("Celsius") {
    import com.github.gekomad.regexcollection.Celsius
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[Celsius]("133") == None)
    assert(validate[Celsius]("2 °C") == Some("2 °C"))
    assert(validate[Celsius]("+2 °C") == Some("+2 °C"))
    assert(validate[Celsius]("-2.2 °C") == Some("-2.2 °C"))
  }

  test("Fahrenheit") {
    import com.github.gekomad.regexcollection.Fahrenheit
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[Fahrenheit]("133") == None)
    assert(validate[Fahrenheit]("2 °F") == Some("2 °F"))
    assert(validate[Fahrenheit]("+2 °F") == Some("+2 °F"))
    assert(validate[Fahrenheit]("-2.2 °F") == Some("-2.2 °F"))
  }

  test("Coordinate1") {
    import com.github.gekomad.regexcollection.Coordinate1
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[Coordinate1]("""45°23'36." N 10°33'48.0" E""") == None)
    assert(validate[Coordinate1]("""45°23'36.1234567"N 010°33'48"E""") == None)
    assert(validate[Coordinate1]("""45°23'36.0" N 10°33'48.0" E""") == Some("""45°23'36.0" N 10°33'48.0" E"""))
    assert(validate[Coordinate1]("""45°23'36.0" N 10°33'48.0″ E""") == Some("""45°23'36.0" N 10°33'48.0″ E"""))
    assert(validate[Coordinate1]("""45°23′36.0" N 10°33'48.0" E""") == Some("""45°23′36.0" N 10°33'48.0" E"""))
    assert(validate[Coordinate1]("""45°23′36.0″ N 10°33′48.0″ E""") == Some("""45°23′36.0″ N 10°33′48.0″ E"""))
    assert(validate[Coordinate1]("""45°23'36.123456"N 010°33'48"E""") == Some("""45°23'36.123456"N 010°33'48"E"""))
  }

  test("Coordinate2") {
    import com.github.gekomad.regexcollection.Coordinate2
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[Coordinate2]("""12.2225""") == None)
    assert(validate[Coordinate2]("""15.25.257S""") == None)
    assert(validate[Coordinate2]("""AA:BB:CC.DDS""") == None)

    assert(validate[Coordinate2]("""12:12:12.223546"N""") == Some("""12:12:12.223546"N"""))
    assert(validate[Coordinate2]("""12:12:12.2246N""") == Some("""12:12:12.2246N"""))
    assert(validate[Coordinate2]("""15:17:6"S""") == Some("""15:17:6"S"""))
    assert(validate[Coordinate2]("""12°30'23.256547"S""") == Some("""12°30'23.256547"S"""))
  }

  test("MACAddress") {
    import com.github.gekomad.regexcollection.MACAddress
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[MACAddress]("""ab""") == None)
    assert(validate[MACAddress]("""01:23:45:67:89:a""") == None)

    assert(validate[MACAddress]("""fE:dC:bA:98:76:54""") == Some("""fE:dC:bA:98:76:54"""))
    assert(validate[MACAddress]("""01:23:45:67:89:AB""") == Some("""01:23:45:67:89:AB"""))
    assert(validate[MACAddress]("""01:23:45:67:89:ab""") == Some("""01:23:45:67:89:ab"""))
  }

  test("Time") {
    import com.github.gekomad.regexcollection.Time
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[Time]("8am") == Some("8am"))
    assert(validate[Time]("8 pm") == Some("8 pm"))
    assert(validate[Time]("11 PM") == Some("11 PM"))
    assert(validate[Time]("8:00 am") == Some("8:00 am"))

    assert(validate[Time]("8a") == None)
    assert(validate[Time]("8 a") == None)
    assert(validate[Time]("8:00 a") == None)
  }

  test("Twitter") {
    import com.github.gekomad.regexcollection.Twitter
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[Twitter]("http://twitter.com/rtpharry/") == None)
    assert(validate[Twitter]("https://twitter.com/rtpharry/") == Some("https://twitter.com/rtpharry/"))
    assert(validate[Twitter]("https://twitter.com/rtpharry") == Some("https://twitter.com/rtpharry"))
    assert(validate[Twitter]("https://twitter.com/#!/rtpharry/") == Some("https://twitter.com/#!/rtpharry/"))
  }

  test("Facebook") {
    import com.github.gekomad.regexcollection.Facebook
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[Facebook]("http://www.facebook.com/thesimpsons") == None)
    assert(validate[Facebook]("www.facebook.com/thesimpsons") == None)
    assert(validate[Facebook]("https://facebook.com/pages/Andy-Worthington/196377860390800") == None)
    assert(validate[Facebook]("www.facebook.com/pages/Andy-Worthington/196377860390800") == None)
    assert(validate[Facebook]("https://www.facebook.com/pages/Andy-Worthington/196377860390800") == Some("https://www.facebook.com/pages/Andy-Worthington/196377860390800"))
    assert(validate[Facebook]("https://www.facebook.com/pages/") == Some("https://www.facebook.com/pages/"))
    assert(validate[Facebook]("https://www.facebook.com/thesimpsons") == Some("https://www.facebook.com/thesimpsons"))

  }

  test("ApacheError") {
    import com.github.gekomad.regexcollection.ApacheError
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[ApacheError]("[Fri Dec 16 02:25:55 2005] [error]  [client 1.2.3.4] Client sent malformed Host header") == None)
    assert(
      validate[ApacheError]("[Fri Dec 16 02:25:55 2005] [error] [client 1.2.3.4] Client sent malformed Host header") == Some(
        "[Fri Dec 16 02:25:55 2005] [error] [client 1.2.3.4] Client sent malformed Host header"
      )
    )

  }

  test("Percentage") {
    import com.github.gekomad.regexcollection.Percentage
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[Percentage]("""10""") == None)
    assert(validate[Percentage]("""101%""") == None)
    assert(validate[Percentage]("""-10%""") == None)
    assert(validate[Percentage]("""1.1%""") == None)

    assert(validate[Percentage]("""0%""") == Some("""0%"""))
    assert(validate[Percentage]("""10%""") == Some("""10%"""))
    assert(validate[Percentage]("""100%""") == Some("""100%"""))
    assert(validate[Percentage]("""99%""") == Some("""99%"""))

  }

  test("USZipCode") {
    import com.github.gekomad.regexcollection.USZipCode
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[USZipCode]("""443073""") == None)
    assert(validate[USZipCode]("""2""") == None)
    assert(validate[USZipCode]("""-44307""") == None)

    assert(validate[USZipCode]("""43802""") == Some("""43802"""))
    assert(validate[USZipCode]("""44307""") == Some("""44307"""))

  }

  test("ItalianPhone") {
    import com.github.gekomad.regexcollection.ItalianPhone
    import com.github.gekomad.regexcollection.Validate.validate

    assert(validate[ItalianPhone]("""+3902/583725""") == None)
    assert(validate[ItalianPhone]("""023232323""") == None)
    assert(validate[ItalianPhone]("""02 645566""") == Some("""02 645566"""))
    assert(validate[ItalianPhone]("""02/583725""") == Some("""02/583725"""))
    assert(validate[ItalianPhone]("""02-583725""") == Some("""02-583725"""))

  }

  test("ItalianMobilePhone") {
    import com.github.gekomad.regexcollection.ItalianMobilePhone
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[ItalianMobilePhone]("""+39 3401234567""") == None)
    assert(validate[ItalianMobilePhone]("""347 1234567""") == None)
    assert(validate[ItalianMobilePhone]("""338-1234567""") == None)
    assert(validate[ItalianMobilePhone]("""02343434""") == None)

    assert(validate[ItalianMobilePhone]("""+393471234561""") == Some("""+393471234561"""))
    assert(validate[ItalianMobilePhone]("""3381234561""") == Some("""3381234561"""))

  }

  test("ItalianZipCode") {
    import com.github.gekomad.regexcollection.ItalianZipCode
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[ItalianZipCode]("""1234""") == None)
    assert(validate[ItalianZipCode]("""1996""") == None)
    assert(validate[ItalianZipCode]("""-23887""") == None)

    assert(validate[ItalianZipCode]("""23887""") == Some("""23887"""))
    assert(validate[ItalianZipCode]("""23001""") == Some("""23001"""))
    assert(validate[ItalianZipCode]("""20066""") == Some("""20066"""))

  }

  test("ItalianIban") {
    import com.github.gekomad.regexcollection.ItalianIban
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[ItalianIban]("""IT28-W800-0000-2921-0064-5211-151""") == None)

    assert(validate[ItalianIban]("""IT28 W800 0000 2921 0064 5211 151""") == Some("""IT28 W800 0000 2921 0064 5211 151"""))
    assert(validate[ItalianIban]("""IT28W8000000292100645211151""") == Some("""IT28W8000000292100645211151"""))

  }

  test("NotASCII") {
    import com.github.gekomad.regexcollection.NotASCII
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[NotASCII]("2sdf.0") == None)
    assert(validate[NotASCII]("　前に来た時は北側からで、当時の光景はいまでも思い出せる。") == Some("　前に来た時は北側からで、当時の光景はいまでも思い出せる。"))
    assert(validate[NotASCII]("の中央には純白のホワイトパレス") == Some("の中央には純白のホワイトパレス"))
    assert(validate[NotASCII]("　……あ。") == Some("　……あ。"))
    assert(validate[NotASCII]("テスト。") == Some("テスト。"))
  }

  test("MDY") {
    import com.github.gekomad.regexcollection.MDY
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[MDY]("2sdf.0") == None)
    assert(validate[MDY]("1/12/1902") == Some("1/12/1902"))
    assert(validate[MDY]("12/31/1902") == Some("12/31/1902"))
    assert(validate[MDY]("12/31/9999") == Some("12/31/9999"))
    assert(validate[MDY]("12/31/19020") == None)
    assert(validate[MDY]("-1/1/1900") == None)
    assert(validate[MDY]("1/1/1900") == Some("1/1/1900"))
    assert(validate[MDY]("1/-1/1900") == None)
    assert(validate[MDY]("1/1/-1900") == None)
  }

  test("MDY2") {
    import com.github.gekomad.regexcollection.MDY2
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[MDY2]("2sdf.0") == None)
    assert(validate[MDY2]("1-12-1902") == Some("1-12-1902"))
    assert(validate[MDY2]("12-31-1902") == Some("12-31-1902"))
    assert(validate[MDY2]("12-31-9999") == Some("12-31-9999"))
    assert(validate[MDY2]("12-31-10000") == None)
    assert(validate[MDY2]("12-31-19020") == None)
    assert(validate[MDY2]("-1-1-1900") == None)
    assert(validate[MDY2]("1-1-1900") == Some("1-1-1900"))
    assert(validate[MDY2]("1--1-1900") == None)
    assert(validate[MDY2]("1-1--1900") == None)
  }

  test("MDY3") {
    import com.github.gekomad.regexcollection.MDY3
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[MDY3]("2sdf.0") == None)
    assert(validate[MDY3]("1/12/1902") == None)
    assert(validate[MDY3]("12/31/2018") == Some("12/31/2018"))
    assert(validate[MDY3]("12/31/9999") == Some("12/31/9999"))
    assert(validate[MDY3]("12/31/10000") == None)
    assert(validate[MDY3]("/1/1/1900") == None)
    assert(validate[MDY3]("1/1/1900") == None)
    assert(validate[MDY3]("2sdf.0") == None)
    assert(validate[MDY3]("01/12/1902") == Some("01/12/1902"))
    assert(validate[MDY3]("12/31/1902") == Some("12/31/1902"))
    assert(validate[MDY3]("12/31/9999") == Some("12/31/9999"))
    assert(validate[MDY3]("01/01/1900") == Some("01/01/1900"))
    assert(validate[MDY3]("01//01/1900") == None)
    assert(validate[MDY3]("01/01//1900") == None)
  }

  test("MDY4") {
    import com.github.gekomad.regexcollection.MDY4
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[MDY4]("2sdf.0") == None)
    assert(validate[MDY4]("1-12-1902") == None)
    assert(validate[MDY4]("12-31-2018") == Some("12-31-2018"))
    assert(validate[MDY4]("12-31-9999") == Some("12-31-9999"))
    assert(validate[MDY4]("12-31-10000") == None)
    assert(validate[MDY4]("-1-1-1900") == None)
    assert(validate[MDY4]("1-1-1900") == None)
    assert(validate[MDY4]("2sdf.0") == None)
    assert(validate[MDY4]("01-12-1902") == Some("01-12-1902"))
    assert(validate[MDY4]("12-31-1902") == Some("12-31-1902"))
    assert(validate[MDY4]("12-31-9999") == Some("12-31-9999"))
    assert(validate[MDY4]("01-01-1900") == Some("01-01-1900"))
    assert(validate[MDY4]("01--01-1900") == None)
    assert(validate[MDY4]("01-01--1900") == None)
  }

  test("DMY") {
    import com.github.gekomad.regexcollection.DMY
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[DMY]("2sdf.0") == None)
    assert(validate[DMY]("1/12/2") == Some("1/12/2"))
    assert(validate[DMY]("12/31/1902") == None)
    assert(validate[DMY]("31/12/1902") == Some("31/12/1902"))
    assert(validate[DMY]("31/12/9999") == Some("31/12/9999"))
    assert(validate[DMY]("12/31/9999") == None)
    assert(validate[DMY]("12/31/19020") == None)
    assert(validate[DMY]("-1/1/1900") == None)
    assert(validate[DMY]("1/1/1900") == Some("1/1/1900"))
    assert(validate[DMY]("1/-1/1900") == None)
    assert(validate[DMY]("1/1/-1900") == None)
  }

  test("DMY2") {
    import com.github.gekomad.regexcollection.DMY2
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[DMY2]("2sdf.0") == None)
    assert(validate[DMY2]("1-12-1902") == Some("1-12-1902"))
    assert(validate[DMY2]("12-31-1902") == None)
    assert(validate[DMY2]("31-12-1902") == Some("31-12-1902"))
    assert(validate[DMY2]("31-12-9999") == Some("31-12-9999"))
    assert(validate[DMY2]("31-12-10000") == None)
    assert(validate[DMY2]("12-31-9999") == None)
    assert(validate[DMY2]("12-31-19020") == None)
    assert(validate[DMY2]("-1-1-1900") == None)
    assert(validate[DMY2]("1-1-1900") == Some("1-1-1900"))
    assert(validate[DMY2]("1--1-1900") == None)
    assert(validate[DMY2]("1-1--1900") == None)
  }

  test("DMY3") {
    import com.github.gekomad.regexcollection.DMY3
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[DMY3]("2sdf.0") == None)
    assert(validate[DMY3]("1/12/1902") == None)
    assert(validate[DMY3]("01/12/1902") == Some("01/12/1902"))
    assert(validate[DMY3]("12/31/1902") == None)
    assert(validate[DMY3]("31/12/1902") == Some("31/12/1902"))
    assert(validate[DMY3]("31/12/9999") == Some("31/12/9999"))
    assert(validate[DMY3]("31/12/10000") == None)
    assert(validate[DMY3]("12/31/9999") == None)
    assert(validate[DMY3]("12/31/19020") == None)
    assert(validate[DMY3]("/1/1/1900") == None)
    assert(validate[DMY3]("01/01/1900") == Some("01/01/1900"))
    assert(validate[DMY3]("1/1/1900") == None)
    assert(validate[DMY3]("01/1/1900") == None)
    assert(validate[DMY3]("1//1/1900") == None)
    assert(validate[DMY3]("1/1//1900") == None)
  }

  test("DMY4") {
    import com.github.gekomad.regexcollection.DMY4
    import com.github.gekomad.regexcollection.Validate.validate
    assert(validate[DMY4]("2sdf.0") == None)
    assert(validate[DMY4]("1-12-1902") == None)
    assert(validate[DMY4]("01-12-1902") == Some("01-12-1902"))
    assert(validate[DMY4]("12-31-1902") == None)
    assert(validate[DMY4]("31-12-1902") == Some("31-12-1902"))
    assert(validate[DMY4]("31-12-9999") == Some("31-12-9999"))
    assert(validate[DMY4]("31-12-10000") == None)
    assert(validate[DMY4]("12-31-9999") == None)
    assert(validate[DMY4]("12-31-19020") == None)
    assert(validate[DMY4]("-1-1-1900") == None)
    assert(validate[DMY4]("01-01-1900") == Some("01-01-1900"))
    assert(validate[DMY4]("1-1-1900") == None)
    assert(validate[DMY4]("01-1-1900") == None)
    assert(validate[DMY4]("1--1-1900") == None)
    assert(validate[DMY4]("1-1--1900") == None)
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
