package io.github.klahap.coam.model

import java.nio.file.Path


class MissingRefPathsException(mainSpec: Path, missingPaths: Set<PathRef>) :
    Exception("missing \$ref-paths in $mainSpec: $missingPaths")

class DifferentRefPathsException(mainPath: ApiPath, refPath: PathRef) :
    Exception("main path ($mainPath) and ref path (${refPath.path}, file=${refPath.file}) doesn't match")
