package io.github.klahap.coam

import io.github.klahap.coam.model.CoamConfig
import io.github.klahap.coam.service.CoamService
import org.gradle.api.Project

class Plugin : org.gradle.api.Plugin<Project> {
    override fun apply(project: Project) {
        val coamBuilder = project.extensions.create("coam", CoamConfig.Builder::class.java)
        val service = CoamService()

        val coamTask = project.task("checkOpenApiMerge") { task ->
            task.doLast {
                val config = CoamConfig.buildOrNull(coamBuilder) ?: run {
                    println("coam is not configured")
                    return@doLast
                }
                service.check(config)
            }
        }

        project.tasks.named("check") {
            it.dependsOn(coamTask)
        }
    }
}