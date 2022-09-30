# Scala 3 Parsers

## AAA-Accounting Log Parser
Parses aaa-accounting to get all the external API calls.
Has some re-usable parsing bits (Cats-Parse)

Main runner: com.adtran.utilapp.logparser.aaa.CommandLineMain

Might as well set it up for Scala CLI style usage (shell scripting)

## Yang Parser/Model Generator

Tried, and going to abandon and use some of the Java opensource stuff as base.

Idea is to spit out case classes, like an OpenAPI thing basically, but delegate to Json
when some items are too complicated etc.
