[![Build Status](https://travis-ci.org/EDumdum/iso-7064-java.svg?branch=master)](https://travis-ci.org/EDumdum/iso-17442-java)
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/Edumdum/iso-17442-java/master/LICENSE)

# ISO-17442

Implementation of ISO 17422 used for the validation of [Legal Entity Identifier (LEI)](https://en.wikipedia.org/wiki/Legal_Entity_Identifier).

## Installation

Install using [maven](https://maven.apache.org/).

### Local repository

Retrieve sources and install it into your maven repository using following commands:
```bash
git clone git@github.com:EDumdum/iso-17442-java.git
cd iso-17422-java
mvn clean install
```

Reference it into your pom.xml:
```maven
<dependencies>
    <dependency>
        <groupId>org.edumdum.iso</groupId>
        <artifactId>iso17442</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

### [JitPack](https://jitpack.io/)

In your pom.xml, add a new repository:
```maven
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Then reference it, still in your pom.xml:
```maven
<dependencies>
    <dependency>
        <groupId>com.github.EDumdum</groupId>
        <artifactId>iso-17442-java</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

## Usage

Into your class, add the following import:
```java
import org.edumdum.iso.Iso17442;
```

Then, you can simple call the static methods. See API section for more informations about the methods.
```
Iso17442.isValid("969500T3MBS4SQAMHJ45"); // false
Iso17442.isValid("724500VKKSH9QOLTFR81"); // true

Iso17442.generate("969500KSV493XWY0PS"); // 969500KSV493XWY0PS33
```

## API

### `isValid(String rawValue)` -> `boolean`

Check requirements.

@param rawValue
- must be not `null`
- must respect format `^[0-9A-Z]{18}[0-9]{2}$`

@return If the check digit (last 2 characters) are valids 

@throws IllegalArgumentException

### `generate(String rawValue)`-> `String`

Check requirements.

@param rawValue
- must be not `null`
- must respect format `^[0-9A-Z]{18}$`

@return rawValue with check digits appended at the end

@throws IllegalArgumentException
