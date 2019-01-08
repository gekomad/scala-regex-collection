import org.scalatest.FunSuite

class FindAll extends FunSuite {

  test("findAll") {
    import com.github.gekomad.regexcollection.Email
    import com.github.gekomad.regexcollection.Validate.findAll
    assert(findAll[Email]("bar abc@def.com hi hello bar@foo.com") == List("abc@def.com", "bar@foo.com"))
    assert(findAll[Email]("sdsdsd@sdf.com") == List("sdsdsd@sdf.com"))
    assert(findAll[Email]("ddddd") == List())
  }

  test("findAll italian fiscal code") {
    import com.github.gekomad.regexcollection.ItalianFiscalCode
    import com.github.gekomad.regexcollection.Validate.findAll
    assert(findAll[ItalianFiscalCode]("bar bdAPPP14A01A001R sdfdfgdfgw BDAPPP14A01A001R d ") == List("bdAPPP14A01A001R", "BDAPPP14A01A001R"))

  }

  test("Custom type") {
    trait Bar
    import com.github.gekomad.regexcollection.Validate._
    import com.github.gekomad.regexcollection.Collection.Validator
    //get all Bar email
    implicit val myValidator = Validator[Bar]("""Bar@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*""")
    assert(findAllIgnoreCase[Bar]("bar abc@google.com hi hello Bar@yahoo.com 123 bar@foo.com") == List("Bar@yahoo.com", "bar@foo.com"))
    assert(findAll[Bar]("bar abc@google.com hi hello Bar@yahoo.com 123 bar@foo.com") == List("Bar@yahoo.com"))

  }
}
