package io.github.klahap.coam.model

data class PathRef(
    val file: SpecPath,
    val path: ApiPath,
) {
    override fun toString() = "${file.value}#/paths/${path.value.replace("/", "~1")}"

    companion object {
        private val refRegex = Regex("^(?<file>[^#]+)#/paths/(?<path>.*)\$")
        fun parseRef(ref: String?) = ref
            ?.let { refRegex.matchEntire(it) }
            ?.let {
                PathRef(
                    file = SpecPath.from(it.groups["file"]!!.value),
                    path = ApiPath(it.groups["path"]!!.value.replace("~1", "/")),
                )
            }
    }
}