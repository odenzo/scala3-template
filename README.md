# Scala 3 Template

Template project with Scala 3 and my "standard" stack with Scala Steward to keep it up to date.
The primary dependency drivers are Cats Effect 3.x and Scala 3

Stack:
- Cats
- Cats Effect 3
- FS2 - 3.03 Cats Effect 3, Scala 3
- Circe (Milestones)
- Doobie **Trouble**
- http4s **Trouble**
- scodec-bits
             
Testing:
- mockito
- munit
- scalacheck
  
The standard libraries will be re-written and broken down by module.
        
odenzo-common: 
    - Utilities that just rely on logging, Cats Effect, Cats, and Circe, PPrint, os.lib
    - Generic helpers and some customer errors
    - OPrint as example of extending PPrint
