import io.github.klahap.coam.model.CoamConfig
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlin.io.path.Path
import kotlin.test.Test

class CoamConfigBuilderTest {
    @Test
    fun `test normal`() {
        CoamConfig.Builder().apply {
            mainSpec = Path("foo.yaml")
            mergedSpecs {
                add("other1.yaml")
                add("other2.yaml")
            }
        }.let { CoamConfig.buildOrNull(it) } shouldBe CoamConfig(
            mainSpec = Path("foo.yaml"),
            mergedSpecs = setOf(
                Path("other1.yaml"),
                Path("other2.yaml"),
            )
        )
    }

    @Test
    fun `test empty`() {
        CoamConfig.Builder().apply {}.let { CoamConfig.buildOrNull(it) } shouldBe null
    }

    @Test
    fun `test no mainSpec`() {
        shouldThrow<Exception> {
            CoamConfig.Builder().apply {
                mergedSpecs {
                    add("other1.yaml")
                }
            }.let { CoamConfig.buildOrNull(it) }
        }
    }

    @Test
    fun `test no mergedSpecs`() {
        shouldThrow<Exception> {
            CoamConfig.Builder().apply {
                mainSpec = Path("foo.yaml")
            }.let { CoamConfig.buildOrNull(it) }
        }
    }
}