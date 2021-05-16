# Scala 3 Template

Template project with Scala 3 and my "standard" stack with Scala Steward to keep it up to date.

Stack:
- Cats
- Cats Effect
- FS2
- Circe
- Doobie

  
The standard libraries will be re-written and broken down by module.
        
odenzo-common: 
    - Utilities that just rely on logging, Cats Effect, Cats, and Circe, PPrint, os.lib
    - Generic helpers and some customer errors
    - OPrint as example of extending PPrint
