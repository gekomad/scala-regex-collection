[![Build Status](https://travis-ci.com/gekomad/scala-regex-collection.svg?branch=master)](https://travis-ci.com/gekomad/scala-regex-collection) [![codecov.io](http://codecov.io/github/gekomad/scala-regex-collection/coverage.svg?branch=master)](http://codecov.io/github/gekomad/scala-regex-collection?branch=master)


Scala regex collection
=====================

Scala-regex-collection is a pure scala regex collection
## Add the library to your project
`libraryDependencies += "com.github.gekomad" %% "scala-regex-collection" % "0.0.1"`

 ## Using Library

### Patterns

You can use defined patterns or you can define yours

- [Email](https://github.com/gekomad/scala-regex-collection/wiki/EMAIL)
- [UUID](https://github.com/gekomad/scala-regex-collection/wiki/UUID)
- [IP](https://github.com/gekomad/scala-regex-collection/wiki/IP)
- [IP_6](https://github.com/gekomad/scala-regex-collection/wiki/IP_6)
- [SHA1](https://github.com/gekomad/scala-regex-collection/wiki/SHA1)
- [SHA256](https://github.com/gekomad/scala-regex-collection/wiki/SHA256)
- [URLs](https://github.com/gekomad/scala-regex-collection/wiki/URLs)
- [HEX](https://github.com/gekomad/scala-regex-collection/wiki/HEX)
- [Bitcon Address](https://github.com/gekomad/scala-regex-collection/wiki/Bitcon-Address)
- [US phone number](https://github.com/gekomad/scala-regex-collection/wiki/US-phone-number)
- [24 Hours time](https://github.com/gekomad/scala-regex-collection/wiki/24-Hours-time)
- [Youtube](https://github.com/gekomad/scala-regex-collection/wiki/Youtube)
- [Crontab expression](https://github.com/gekomad/scala-regex-collection/wiki/Crontab-expression)
- [Italian fiscal code](https://github.com/gekomad/scala-regex-collection/wiki/Italian-fiscal-code)
- [Italian partita iva](https://github.com/gekomad/scala-regex-collection/wiki/Italian-partita-iva)
- [LocalDateTime](https://github.com/gekomad/scala-regex-collection/wiki/LocalDateTime)
- [LocalDate](https://github.com/gekomad/scala-regex-collection/wiki/LocalDate)
- [LocalTime](https://github.com/gekomad/scala-regex-collection/wiki/LocalTime)
- [OffsetDateTime](https://github.com/gekomad/scala-regex-collection/wiki/OffsetDateTime)
- [OffsetTime](https://github.com/gekomad/scala-regex-collection/wiki/OffsetTime)
- [ZonedDateTime](https://github.com/gekomad/scala-regex-collection/wiki/ZonedDateTime)



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

### Find all matched strings
Example extracting all emails from a string
```
import com.github.gekomad.regexcollection.Email
import com.github.gekomad.regexcollection.Validate.findAll

assert(findAll[Email]("bar abc@def.com hi hello bar@foo.com") == List("abc@def.com", "bar@foo.com"))
assert(findAll[Email]("sdsdsd@sdf.com") == List("sdsdsd@sdf.com"))
assert(findAll[Email]("ddddd") == List())
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

### Find all matched strings

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