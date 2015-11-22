import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import models.CodeTask
import models.Execution

class CodeTaskSpec extends Specification {
	val codeTask = new CodeTask(test = "assert(reverse(List(1,2,3,4)) == List(4,3,2,1), \"fail\")")

	"CodeTaskSpec#run" should {
		"succeed when the code is valid" in {
			codeTask.code = "// Template\ndef reverse(l: List[Any]): List[Any] = {\n  l.reverse \n}"
			codeTask.run().success must beTrue
		}
		"be incomplete in case of infinite loop" in {
			codeTask.code = "// Template\ndef reverse(l: List[Any]): List[Any] = {\n  while(true){}; l.reverse \n}"
			codeTask.run().incomplete must beTrue
		}
		"be error in case of invalid code" in {
			codeTask.code = "// Template\ndef reverse(l: List[Any]): List[Any] = {\n  not code \n}"
			codeTask.run().error must beTrue
		}
		"contain errors in output for invalid code" in {
			codeTask.code = "// Template\ndef reverse(l: List[Any]): List[Any] = {\n  l \n}"
			val ex = codeTask.run()
			ex.consoleOutput contains "assertion failed: fail" must beTrue
			ex.error must beTrue
		}
	}
}


/*
TODO:
- CONSTANTE für await zeit 
*/