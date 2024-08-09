package io.github.klahap.coam.model

import java.nio.file.Path
import kotlin.io.path.Path

data class CoamConfig(
    val mainSpec: Path,
    val mergedSpecs: Set<Path>,
) {
    open class Builder(
        var mainSpec: Path? = null,
        private var _mergedSpecs: Set<Path> = emptySet(),
    ) {
        val mergedSpecs get() = _mergedSpecs
        fun mergedSpecs(block: PathSetBuilder.() -> Unit) {
            _mergedSpecs = PathSetBuilder().apply(block).toSet()
        }

        companion object {
            class PathSetBuilder(private val data: MutableSet<Path> = mutableSetOf()) : MutableSet<Path> by data {
                fun add(path: String) = add(Path(path))
            }
        }
    }

    companion object {
        fun buildOrNull(builder: Builder): CoamConfig? {
            if (builder.mainSpec == null && builder.mergedSpecs.isEmpty()) return null

            return CoamConfig(
                mainSpec = builder.mainSpec ?: throw Exception("no main OpenApi spec defined"),
                mergedSpecs = builder.mergedSpecs.takeIf { it.isNotEmpty() }
                    ?: throw Exception("no other OpenApi spec defined"),
            )
        }
    }
}