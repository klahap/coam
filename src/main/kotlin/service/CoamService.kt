package io.github.klahap.coam.service

import io.github.klahap.coam.model.*
import kotlin.io.path.relativeTo

class CoamService {
    private val specService = OpenApiSpecService()

    fun check(config: CoamConfig) {
        val otherSpecPaths = config.mergedSpecs.toSet().flatMap { file ->
            val relFilePath = SpecPath.from(file.relativeTo(config.mainSpec.parent))
            specService.getAllPaths(file).map { apiPath ->
                PathRef(file = relFilePath, path = apiPath)
            }
        }.toSet()

        val mainPaths = specService.getAllPathRef(config.mainSpec)

        val missingPaths = otherSpecPaths - mainPaths.values.toSet()
        if (missingPaths.isNotEmpty()) throw MissingRefPathsException(config.mainSpec, missingPaths)

        mainPaths.filter { it.key != it.value.path }.forEach {
            throw DifferentRefPathsException(mainPath = it.key, refPath = it.value)
        }
    }
}