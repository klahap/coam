package io.github.klahap.coam.model

import java.nio.file.Path

@JvmInline
value class SpecPath private constructor(val value: String) {
    override fun toString() = value

    companion object {
        fun from(value: String) = SpecPath(value.removePrefix("./"))
        fun from(value: Path) = from(value.toString())
    }
}