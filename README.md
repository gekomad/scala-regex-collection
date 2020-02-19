[![Build Status](https://travis-ci.com/gekomad/scala-regex-collection.svg?branch=master)](https://travis-ci.com/gekomad/scala-regex-collection)

Scala regex collection
=====================

Scala-regex-collection is a pure scala regex collection
## Add the library to your project
`libraryDependencies += "com.github.gekomad" %% "scala-regex-collection" % "1.0.1"`

 ## Using Library

### Patterns

You can use defined patterns or you can define yours

Email

- [Email](https://github.com/gekomad/scala-regex-collection/wiki/Email)  ($abc@</span>def</span>.c)
- [Email1](https://github.com/gekomad/scala-regex-collection/wiki/Email1)  (abc@</span>def</span>.com)
- [Email simple](https://github.com/gekomad/scala-regex-collection/wiki/EmailSimple)  ($@</span>%</span>.$)

Ciphers

- [UUID](https://github.com/gekomad/scala-regex-collection/wiki/UUID)  (1CC3CCBB-C749-3078-E050-1AACBE064651)
- [MD5](https://github.com/gekomad/scala-regex-collection/wiki/MD5)  (23f8e84c1f4e7c8814634267bd456194)
- [SHA1](https://github.com/gekomad/scala-regex-collection/wiki/SHA1)  (1c18da5dbf74e3fc1820469cf1f54355b7eec92d)
- [SHA256](https://github.com/gekomad/scala-regex-collection/wiki/SHA256) (000020f89134d831f48541b2d8ec39397bc99fccf4cc86a3861257dbe6d819d1)

URL, IP, MAC Address

- [IP](https://github.com/gekomad/scala-regex-collection/wiki/IP)  (10.192.168.1)
- [IP_6](https://github.com/gekomad/scala-regex-collection/wiki/IP_6)  (2001:db8:a0b:12f0::1)
- [URLs](https://github.com/gekomad/scala-regex-collection/wiki/URLs)  (http://</span>abc.def</span>.com)
- [Youtube](https://github.com/gekomad/scala-regex-collection/wiki/Youtube)  (https://</span>www</span>.youtube</span>.com/watch?v=9bZkp7q19f0)
- [Facebook](https://github.com/gekomad/scala-regex-collection/wiki/Facebook)  (https://</span>www</span>.facebook.</span>com/thesimpsons - https://</span>www</span>.facebook.</span>com/pages/)
- [Twitter](https://github.com/gekomad/scala-regex-collection/wiki/Twitter)  (https://</span>twitter</span>.com/rtpharry)
- [MAC Address](https://github.com/gekomad/scala-regex-collection/wiki/MACAddress)  (fE:dC:bA:98:76:54)

HEX

- [HEX](https://github.com/gekomad/scala-regex-collection/wiki/HEX)  (#F0F0F0 - 0xF0F0F0)

Bitcoin

- [Bitcon Address](https://github.com/gekomad/scala-regex-collection/wiki/Bitcon-Address)  (3Nxwenay9Z8Lc9JBiywExpnEFiLp6Afp8v)

Phone numbers

- [US phone number](https://github.com/gekomad/scala-regex-collection/wiki/US-phone-number)  (555-555-5555 - (416)555-3456)
- [Italian Mobile Phone](https://github.com/gekomad/scala-regex-collection/wiki/ItalianMobilePhone) (+393471234561 - 3381234561)
- [Italian Phone](https://github.com/gekomad/scala-regex-collection/wiki/ItalianPhone) (02 645566 - 02/583725 - 02-583725)

Date time

- [24 Hours time](https://github.com/gekomad/scala-regex-collection/wiki/24-Hours-time)  (23:50:00)
- [LocalDateTime](https://github.com/gekomad/scala-regex-collection/wiki/LocalDateTime)  (2000-12-31T11:21:19)
- [LocalDate](https://github.com/gekomad/scala-regex-collection/wiki/LocalDate)  (2000-12-31)
- [LocalTime](https://github.com/gekomad/scala-regex-collection/wiki/LocalTime)  (11:21:19)
- [OffsetDateTime](https://github.com/gekomad/scala-regex-collection/wiki/OffsetDateTime)  (2011-12-03T10:15:30+01:00)
- [OffsetTime](https://github.com/gekomad/scala-regex-collection/wiki/OffsetTime)  (10:15:30+01:00)
- [ZonedDateTime](https://github.com/gekomad/scala-regex-collection/wiki/ZonedDateTime)  (2016-12-02T11:15:30-05:00)
- [MDY](https://github.com/gekomad/scala-regex-collection/wiki/MDY) (1/12/1902 - 12/31/1902)
- [MDY2](https://github.com/gekomad/scala-regex-collection/wiki/MDY2)  (1-12-1902)
- [MDY3](https://github.com/gekomad/scala-regex-collection/wiki/MDY3)  (01/01/1900 - 12/31/9999)
- [MDY4](https://github.com/gekomad/scala-regex-collection/wiki/MDY4)  (01-12-1902 - 12-31-2018)
- [DMY](https://github.com/gekomad/scala-regex-collection/wiki/DMY)  (1/12/1902)
- [DMY2](https://github.com/gekomad/scala-regex-collection/wiki/DMY2)  (12-31-1902 - 1-12-1902)
- [DMY3](https://github.com/gekomad/scala-regex-collection/wiki/DMY3)  (01/12/1902 - 01/12/1902)
- [DMY4](https://github.com/gekomad/scala-regex-collection/wiki/DMY4)  (01-12-1902 - 01-12-1902)
- [Time](https://github.com/gekomad/scala-regex-collection/wiki/Time)  (8am - 8 pm - 11 PM - 8:00 am)

Crontab

- [Crontab expression](https://github.com/gekomad/scala-regex-collection/wiki/Crontab-expression) (5 4 * * *)

Codes

- [Italian fiscal code](https://github.com/gekomad/scala-regex-collection/wiki/Italian-fiscal-code) (BDAPPP14A01A001R)
- [Italian VAT code](https://github.com/gekomad/scala-regex-collection/wiki/ItalianVAT) (13297040362)
- [Italian Iban](https://github.com/gekomad/scala-regex-collection/wiki/ItalianIban) (IT28 W800 0000 2921 0064 5211 151 - IT28W8000000292100645211151)
- [US states](https://github.com/gekomad/scala-regex-collection/wiki/USstates) (FL - CA)
- [US states1](https://github.com/gekomad/scala-regex-collection/wiki/USstates1) (Connecticut - Colorado)
- [US zip code](https://github.com/gekomad/scala-regex-collection/wiki/USZipCode)  (43802)
- [US streets](https://github.com/gekomad/scala-regex-collection/wiki/USStreets)  (123 Park Ave Apt 123 New York City, NY 10002)
- [US street numbers](https://github.com/gekomad/scala-regex-collection/wiki/USStreetNumber)  (P.O. Box 432)
- [Italian zip code](https://github.com/gekomad/scala-regex-collection/wiki/ItalianZipCode) (23887)
- [German streets](https://github.com/gekomad/scala-regex-collection/wiki/GermanStreet) (Mühlenstr. 33)

Concurrency

- [USD Currency](https://github.com/gekomad/scala-regex-collection/wiki/USD-Currency)  ($1.00 - 1,500.00)
- [EUR Currency](https://github.com/gekomad/scala-regex-collection/wiki/EurCurrency)  (0,00 € - 133,89 EUR - 133,89 EURO)
- [YEN Currency](https://github.com/gekomad/scala-regex-collection/wiki/YenCurrency)  (¥1.00 - 15.00 - ¥-1213,120.00)

Strings

- [Not ASCII](https://github.com/gekomad/scala-regex-collection/wiki/NotASCII)  (テスト。)
- [Single char ASCII](https://github.com/gekomad/scala-regex-collection/wiki/SingleChar)  (A)
- [A-Z string](https://github.com/gekomad/scala-regex-collection/wiki/AZString)  (abc)
- [String and number](https://github.com/gekomad/scala-regex-collection/wiki/StringAndNumber)  (a1)
- [ASCII string](https://github.com/gekomad/scala-regex-collection/wiki/AsciiString)  (a1%)

Logs

- [Apache error](https://github.com/gekomad/scala-regex-collection/wiki/ApacheError)  ([Fri Dec 16 02:25:55 2005] [error] [client 1.2.3.4] Client sent malformed Host header)

Numbers

- [Number1](https://github.com/gekomad/scala-regex-collection/wiki/Number1)  (99.99 - 1.1 - .99)
- [Unsigned32](https://github.com/gekomad/scala-regex-collection/wiki/Unsigned32)  (0 - 122 - 4294967295)
- [Signed](https://github.com/gekomad/scala-regex-collection/wiki/Signed)  (-10 - +122 - 99999999999999999999999999)
- [Percentage](https://github.com/gekomad/scala-regex-collection/wiki/Percentage)  (10%)
- [Scientific](https://github.com/gekomad/scala-regex-collection/wiki/Scientific)  (-2.384E-03)
- [Single number](https://github.com/gekomad/scala-regex-collection/wiki/SingleNumber)  (1)
- [Celsius](https://github.com/gekomad/scala-regex-collection/wiki/Celsius)  (-2.2 °C)
- [Fahrenheit](https://github.com/gekomad/scala-regex-collection/wiki/Fahrenheit)  (-2.2 °F)

Coordinates

- [Coordinate](https://github.com/gekomad/scala-regex-collection/wiki/Coordinate)  (N90.00.00 E180.00.00)
- [Coordinate1](https://github.com/gekomad/scala-regex-collection/wiki/Coordinate1)  (45°23'36.0" N 10°33'48.0" E)
- [Coordinate2](https://github.com/gekomad/scala-regex-collection/wiki/Coordinate2)  (12:12:12.223546"N - 15:17:6"S - 12°30'23.256547"S)

Programming

- [Comments](https://github.com/gekomad/scala-regex-collection/wiki/Comments)  (/* foo */)

Credit Cards

- [Visa](https://github.com/gekomad/scala-regex-collection/wiki/CreditCards)  (/* 4111111111111 */)
- [Master Card](https://github.com/gekomad/scala-regex-collection/wiki/CreditCards)  (/* 5500000000000004 */)
- [American Express](https://github.com/gekomad/scala-regex-collection/wiki/CreditCards)  (/* 340000000000009 */)
- [Diners Club](https://github.com/gekomad/scala-regex-collection/wiki/CreditCards)  (/* 30000000000004 */)
- [Discover](https://github.com/gekomad/scala-regex-collection/wiki/CreditCards)  (/* 6011000000000004 */)
- [JCB](https://github.com/gekomad/scala-regex-collection/wiki/CreditCards)  (/* 3588000000000009 */)

 ## Use the library


### Validate String
Returns Option[String] with the matched string
```
import com.github.gekomad.regexcollection._
import com.github.gekomad.regexcollection.Validate.validate
import java.time.LocalDateTime

assert(validate[Email]("foo@bar.com") == Some("foo@bar.com"))
assert(validate[Email]("baz") == None)
assert(validate[MD5]("fc42757b4142b0474d35fcddb228b304") == Some("fc42757b4142b0474d35fcddb228b304"))
assert(validate[LocalDateTime]("2000-12-31T11:21:19") == Some("2000-12-31T11:21:19"))
```

### findAll
Example extracting all emails from a string
```
import com.github.gekomad.regexcollection.Email
import com.github.gekomad.regexcollection.Validate.findAll

assert(findAll[Email]("bar abc@def.com hi hello bar@foo.com") == List("abc@def.com", "bar@foo.com"))
assert(findAll[Email]("sdsdsd@sdf.com") == List("sdsdsd@sdf.com"))
assert(findAll[Email]("ddddd") == List())
```

### findFirst
Example extracting first email from a string
```
trait Bar
import com.github.gekomad.regexcollection.Validate.findFirst
import com.github.gekomad.regexcollection.Validate.findFirstIgnoreCase
import com.github.gekomad.regexcollection.Collection.Validator

implicit val myValidator = Validator[Bar]("""Bar@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*""")
assert(findFirstIgnoreCase[Bar]("bar abc@google.com hi hello bar@yahoo.com 123 Bar@foo.com") == Some("bar@yahoo.com"))
assert(findFirst[Bar]("bar abc@google.com hi hello Bar@yahoo.com 123 bar@foo.com") == Some("Bar@yahoo.com"))
```

### Get pattern
Returns the current pattern used for that type, for example for Email type:
```
import com.github.gekomad.regexcollection.Email
import com.github.gekomad.regexcollection.Validate.regexp

assert(regexp[Email] == """[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*""")
```

### Modify default pattern

It's possible modify the default pattern for all types, example for Email

```
import com.github.gekomad.regexcollection.Email
import com.github.gekomad.regexcollection.Validate.validate
import com.github.gekomad.regexcollection.Collection.Validator

val email = "abc,a@%.d"

//using default pattern doesn't match the string
assert(validate[Email](email) == None)

//using custom pattern the string is matched
implicit val validator = Validator[Email](""".+@.+\..+""")
assert(validate[Email](email) == Some("abc,a@%.d"))
```

### Matching your own type

Defining a pattern for Bar type
```
trait Bar

import com.github.gekomad.regexcollection.Validate.validate
import com.github.gekomad.regexcollection.Validate.validateIgnoreCase
import com.github.gekomad.regexcollection.Collection.Validator

// pattern for strings starting with "Bar."
implicit val myValidator = Validator[Bar]("Bar.*")

assert(validate[Bar]("a string") == None)
assert(validate[Bar]("Bar foo") == Some("Bar foo"))
assert(validate[Bar]("bar foo") == None)
assert(validateIgnoreCase[Bar]("bar foo") == Some("bar foo"))

```

### findAllIgnoreCase

Retrieve all emails using findAll and findAllCaseSensitive


```
trait Bar
import com.github.gekomad.regexcollection.Collection.Validator
import com.github.gekomad.regexcollection.Validate._

//get all Alice's emails
implicit val myValidator = Validator[Bar]("""Alice@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*""")

val s = "bar abc@google.com hi hello Alice@yahoo.com 123 alice@foo.com"
assert(findAll[Bar](s) == List("Alice@yahoo.com"))
assert(findAllIgnoreCase[Bar](s) == List("Alice@yahoo.com", "alice@foo.com"))
```

### Using a function pattern

Instead of using a regular expression to match a string it's possible defining a function pattern

Example matching even numbers

```
trait Foo

import com.github.gekomad.regexcollection.Validate.validate
import com.github.gekomad.regexcollection.Collection.Validator

def even: String => Option[String] = { s =>
  {
    for {
      i <- scala.util.Try(s.toInt)
      if (i % 2 == 0)
    } yield Some(s)
  }.getOrElse(None)
}

implicit val validator: Validator[Foo] = Validator[Foo](even)

assert(validate[Foo]("42") == Some("42"))
assert(validate[Foo]("41") == None)
assert(validate[Foo]("hello") == None)
```

## Bugs and Feedback
For bugs, questions and discussions please use [Github Issues](https://github.com/gekomad/scala-regex-collection/issues).

## License

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
with the License. You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and limitations under the License.

## Special Thanks ##

To [regexlib.com](http://regexlib.com)
