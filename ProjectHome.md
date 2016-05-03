# Office-o-tron #

A web and command-line application that accepts [ODF](http://en.wikipedia.org/wiki/Odf) or [OOXML](http://en.wikipedia.org/wiki/Ooxml) packages and validates the XML within, returning a summary report in XHTML.

See the [About page](http://code.google.com/p/officeotron/wiki/About) for more information ...

The source is licensed under the [Mozilla Public License v1.1.](http://www.mozilla.org/MPL/MPL-1.1.html)

See it working for [this online validator](http://www.probatron.org:8080/officeotron/officeotron.html).

Office-o-tron is also used by the [Officeshots](http://officeshots.org/) project.

## Version 0.7.0 ##

<i>Credit to Cedric Bosdonnat of Suse for many of the enhancements in this release</i>

Is the latest release:

  * Fuller schema validation for OOXML documents
  * Support for MCE
  * Updates ODF schemas to latest versions
  * Fixes bug whereby ODF 1.0 documents were validated against ODF 1.2 spec

## Version 0.6.1 ##

Changes:

  * Better ODF manifest validation and package spidering
  * Tuning of online service to balance loads better

## Version 0.6.0 ##

Changes:

  * OOXML - Patches from the Novell team applied (better command-line options) - thanks guys!
  * ODF - schemas updated to latest public review drafts
  * ODF - spurious error message about ZIP structure removed
  * ODF - manifest validation added for draft 1.2 documents
  * General - resource leak fixed + substantial performance improvements!

## Version 0.5.4 ##

Changes:

  * OOXML - fixed bug which prevented complete spidering of OPC packages
  * OOXML - fixed bugs in MIME type/schema mappings
  * OOXML - implemented relative URI addressing for OPC Relationship targets
  * OOXML - implemented content type defaulting from Part Name extensions
  * OOXML - alerts on encountering deprecated technology


## Version 0.5.2 ##

Changes:

  * OOXML - Reports leading slash problem in OPC package when encountered
  * Posting of an [About page](http://code.google.com/p/officeotron/wiki/About)

## Version 0.5.0 ##

Changes:

  * Major refactoring to allow for physical validation phase
  * Bit-level ZIP inspection (and first error reporting)

## Version 0.4.0 ##

Changes:

  * ZIP functionality re-worked to use [Info-ZIP](http://www.info-zip.org/)
  * Updated ODF Pt 1 schema to current (CD04) draft for draft ODF 1.2 submissions

## Version 0.3.2 ##

Changes:

  * Bug fix for leading slash ambiguity in OPC part references
  * Re-licenced to use the MPL 1.1

## Version 0.3.0 ##

Changes:

  * Support for OOXML validation (ISO/IEC 29500:2008 "Transitional" only)

## Version 0.2.0 ##

Changes:

  * Support for POSTed ODF documents (see [here](http://www.griffinbrown.co.uk/blog/2009/06/17/CloudyODFValidation.aspx) for more details)
  * Better validation reporting

## Version 0.1.1 ##

Changes:

  * More gracefully failure in the face of unexpected resources being submitted for validation
  * A report of the meta:generator value for submitted ODF documents
  * Support for ID/IDREF integrity checking
  * Support for enforced validation of ODF adocuments gainst the ODF International Standard (ISO/IEC 26300)
  * More nicely formatted validation reports