package io.github.klahap.coam.service

import io.github.klahap.coam.model.ApiPath
import io.github.klahap.coam.model.PathRef
import io.swagger.parser.OpenAPIParser
import java.nio.file.Path

class OpenApiSpecService {
    private val parser = OpenAPIParser()

    fun getAllPathRef(specFile: Path): Map<ApiPath, PathRef> {
        val spec = parser.readLocation(specFile.toString(), null, null)!!
        return spec.openAPI.paths.mapNotNull { (key, value) ->
            val ref = PathRef.parseRef(value.`$ref`)
            ApiPath(key!!) to (ref ?: return@mapNotNull null)
        }.toMap()
    }

    fun getAllPaths(specFile: Path): Set<ApiPath> {
        val spec = parser.readLocation(specFile.toString(), null, null)!!
        return spec.openAPI.paths.keys.map { ApiPath(it!!) }.toSet()
    }
}