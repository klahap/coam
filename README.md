# OpenAPI Spec Merger Checker (coam)

![GitHub License](https://img.shields.io/github/license/klahap/coam)
![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/klahap/coam/check.yml)
![Static Badge](https://img.shields.io/badge/coverage-100%25-success)

OpenAPI Spec Merger Checker (coam) is a Gradle plugin that ensures all paths from one or more OpenAPI specification files (merged specs) are fully included in a main OpenAPI specification file. This helps maintain consistent and complete API documentation across your project.

## Features

- **Validation of OpenAPI Spec Merges:** Ensures that the paths from the specified OpenAPI files are correctly referenced in the main OpenAPI spec file using `$ref`.
- **Simple Gradle Integration:** Easily integrates with your Gradle build process to automate the validation of OpenAPI specs.
- **Customizable Configuration:** Supports multiple OpenAPI spec files to be merged into a single main spec file.

## Installation

To use the OpenAPI Spec Merger Checker in your Gradle project, add the following to your `build.gradle.kts` file:

```kotlin
plugins {
    id("io.github.klahap.coam") version "$VERSION"
}
```

## Usage

To configure the plugin, add the following configuration block to your `build.gradle.kts`:

```kotlin
coam { 
    mainSpec = "$projectDir/main.yaml"  // Path to the main OpenAPI spec file
    mergedSpecs { 
        add("$projectDir/other1.yaml")  // Path to the first spec to be merged
        add("$projectDir/other2.yaml")  // Path to the second spec to be merged
    }
}
```

### Running the Check

You can run the OpenAPI spec merge check using the following Gradle tasks:

- **`gradle checkOpenApiMerge`**: Specifically runs the OpenAPI merge check.
- **`gradle check`**: Runs the OpenAPI merge check along with other checks configured in your project.

If all paths from the merged specs are correctly referenced in the main spec, the task will pass. Otherwise, it will fail, indicating which parts of the merged specs are missing from the main spec.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.
