# General #

The Office-o-tron validates ODF or OOXML documents using two validation layers:

  * schema validation
  * programmatic application of normative constraints specified in the standards.

_A live version of office-o-tron is generally available [here](http://www.probatron.org:8080/officeotron/officeotron.html), and this usually uses the most recent code from the source repository, which is often more advanced than the most recent numbered release_.

# Restish invocation #

As of version 0.2.0 Office-o-tron supports validation of office documents POSTED to it.

Using a useful tool like [cURL](http://en.wikipedia.org/wiki/CURL), this can be done on the command line as follows:

```
curl --data-binary @mydocument.docx http://www.probatron.org:8080/officeotron/validator
```

The response is a validation report in XHTML.

Feel free to help yourself to this functionality at the above URL!

# Schema Validation #

## ODF Schemas ##

The appropriate ODF schema is selected using the version attribute on root elements of XML documents.

The ODF schemas (certainly prior to version 1.2) exhibit a common construction error`[1]` that occurs when RELAX NG schemas use ID/IDREF types. This is disregarded for the purposes of validation.

## OOXML Schemas ##

OOXML documents are validated against the Transitional variant XSD schemas of ISO/IEC 29500:2008. The validator used is Apache Xerces-J as supplied with the Java 1.6 SDK. Since XSD schemas are inherently non-interoperable, the suggested changes of the schema comments are implemented to make them work correctly with this validator.

# Programmatic Validation #

Programmatic validation is carried out in Java code. Care is take to maintain a streaming approach at all times to ensure performance.

## ZIP validation ##

The Office-o-tron inspects the construction of the ZIP archive behind an office document and carried out tests for provisions of the spec at the bit and byte level.

## Contradictory Constraints ##

Where the standard contains two or more contradictory provisions **all** of those provisions are tested. The standard is in this case impossible to implement since no document can exist that satisfies its provisions. The validator thus accurately exposes this phenomenon.


# References #

`[1]` see [James Clark's article here](http://blog.jclark.com/2009/01/relax-ng-and-xmlid.html) for details.