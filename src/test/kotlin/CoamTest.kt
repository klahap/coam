import io.github.klahap.coam.model.CoamConfig
import io.github.klahap.coam.model.DifferentRefPathsException
import io.github.klahap.coam.model.MissingRefPathsException
import io.github.klahap.coam.service.CoamService
import io.kotest.assertions.throwables.shouldThrowExactly
import kotlin.io.path.Path
import kotlin.test.Test

class CoamTest {
    @Test
    fun `test valid merge`() {
        CoamService().check(CoamConfig(main1, mergedSpecs = setOf(other)))
        CoamService().check(CoamConfig(main4, mergedSpecs = setOf(other)))
    }

    @Test
    fun `test missing path`() {
        shouldThrowExactly<MissingRefPathsException> {
            CoamService().check(CoamConfig(main2, mergedSpecs = setOf(other)))
        }
    }

    @Test
    fun `test different path`() {
        shouldThrowExactly<DifferentRefPathsException> {
            CoamService().check(CoamConfig(main3, mergedSpecs = setOf(other)))
        }
    }

    companion object {
        private fun getResourcePath(name: String) = Path(CoamTest::class.java.getResource(name)!!.path!!)
        private val main1 = getResourcePath("main1.yaml")
        private val main2 = getResourcePath("main2.yaml")
        private val main3 = getResourcePath("main3.yaml")
        private val main4 = getResourcePath("main4.yaml")
        private val other = getResourcePath("other.yaml")
    }
}
