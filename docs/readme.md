# Yang Schema Parsing to Scala Case Class Models

+ Idea it to parse simple Yang schema and generate Scala case classes
  + Models have Circe Encoders/Decoders
  + Support a variety of inbuilt types as well as Yang TypeDefs
  + Take description items and add as Scala Docs.


## Other Work
There are some Java and Python Yang Schema Parsers.
List them:


## Basic Approach

- COnvert Scheme to ParserObjects
- Convert Parse Object to Scala and write to disk.
