class FindAll extends munit.FunSuite {

  test("Email") {
    import com.github.gekomad.regexcollection.Email
    import com.github.gekomad.regexcollection.Validate.findAll
    assertEquals(findAll[Email]("bar abc@def.com hi hello bar@foo.com"), List("abc@def.com", "bar@foo.com"))
    assertEquals(findAll[Email]("sdsdsd@sdf.com"), List("sdsdsd@sdf.com"))
    assertEquals(findAll[Email]("ddddd"), List())
  }


  test("italian fiscal code") {
    import com.github.gekomad.regexcollection.ItalianFiscalCode
    import com.github.gekomad.regexcollection.Validate.findAll
    assertEquals(findAll[ItalianFiscalCode]("bar bdAPPP14A01A001R sdfdfgdfgw BDAPPP14A01A001R d "), List("bdAPPP14A01A001R", "BDAPPP14A01A001R"))
  }

  test("NotASCII") {
    import com.github.gekomad.regexcollection.NotASCII
    import com.github.gekomad.regexcollection.Validate.findAll
    assertEquals(findAll[NotASCII]("""    テ ス  ト      a ああ   bw"""), List("テ ス  ト      ", "ああ   "))
  }

  test("Percentage") {
    import com.github.gekomad.regexcollection.Percentage
    import com.github.gekomad.regexcollection.Validate.findAll
    assertEquals(findAll[Percentage]("""dd 10%  dg 55% """), List("10%", "55%"))
  }

  test("MACAddress") {
    import com.github.gekomad.regexcollection.MACAddress
    import com.github.gekomad.regexcollection.Validate.findAll
    assertEquals(findAll[MACAddress]("""
        |eno1: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
        |        inet 10.21.40.196  netmask 255.255.252.0  broadcast 10.21.43.255
        |        inet6 fe80::98d1:d421:7c77:d414  prefixlen 64  scopeid 0x20<link>
        |        ether ec:f4:bb:1f:d1:fc  txqueuelen 1000  (Ethernet)
        |        RX packets 134062  bytes 72628035 (72.6 MB)
        |        RX errors 0  dropped 0  overruns 0  frame 0
        |        TX packets 47797  bytes 7828908 (7.8 MB)
        |        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0
        |        device interrupt 20  memory 0xf7d00000-f7d20000
        |
        |lo: flags=73<UP,LOOPBACK,RUNNING>  mtu 65536
        |        inet 127.0.0.1  netmask 255.0.0.0
        |        inet6 ::1  prefixlen 128  scopeid 0x10<host>
        |        loop  txqueuelen 1000  (Local Loopback)
        |        RX packets 488023  bytes 129378106 (129.3 MB)
        |        RX errors 0  dropped 0  overruns 0  frame 0
        |        TX packets 488023  bytes 129378106 (129.3 MB)
        |        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0
        |
        |wlp3s0: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
        |        inet 10.21.40.207  netmask 255.255.252.0  broadcast 10.21.43.255
        |        inet6 fe80::dd45:2c19:4d6e:3e1f  prefixlen 64  scopeid 0x20<link>
        |        ether b8:ee:11:01:e0:0e  txqueuelen 1000  (Ethernet)
        |        RX packets 852279  bytes 426024765 (426.0 MB)
        |        RX errors 0  dropped 0  overruns 0  frame 0
        |        TX packets 469856  bytes 449426765 (449.4 MB)
        |        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0
        |
      """.stripMargin), List("ec:f4:bb:1f:d1:fc", "b8:ee:11:01:e0:0e"))
  }

  test("Custom type") {
    trait Bar
    import com.github.gekomad.regexcollection.Validate._
    import com.github.gekomad.regexcollection.Collection.Validator
    // get all Bar email
    implicit val myValidator: Validator[Bar] = Validator[Bar]("""Bar@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*""")
    assertEquals(findAllIgnoreCase[Bar]("bar abc@google.com hi hello Bar@yahoo.com 123 bar@foo.com"), List("Bar@yahoo.com", "bar@foo.com"))
    assertEquals(findAll[Bar]("bar abc@google.com hi hello Bar@yahoo.com 123 bar@foo.com"), List("Bar@yahoo.com"))
  }

  test("Comments") {
    import com.github.gekomad.regexcollection.Comments
    import com.github.gekomad.regexcollection.Validate.findAll
    assertEquals(findAll[Comments]("""/*foo*/ bar /*baz*/ 10%  dg 55% """), List("/*foo*/", "/*baz*/"))
  }
}
