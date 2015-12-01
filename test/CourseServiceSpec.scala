import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import models.CodeTask
import models.Execution
import models._

class CourseServiceSpec extends Specification {
	val courseService = new CourseService(Config);
	val course1 = new Course("scala", List())

	"CourseService#findOneByName" should {
		"fail with unknown course name" in {
			val course = courseService.findOneByName("no name")
			course should be(None)
		}
	}
	"CourseService#create" should {
		"succeed when course doesn't exist" in {
			courseService.create(course1) should be equalTo(Some(course1))
		}
		"fail when course does exist" in {
			courseService.create(course1) should be(None)
		}
	}
	"CourseService#update" should {
		"work with existing course" in {
			course1.chapters = List(new Chapter("test"))
			courseService.update(course1)
			val course = courseService.findOneByName("scala").get
			course.chapters.size shouldEqual(1)
		}
	}
	"CourseService#delete" should {
		"succeed with existing course" in {
			var course = courseService.findOneByName("scala")
			courseService.delete(course.get)
			courseService.findOneByName("scala") should be(None)
		}
	}
}