#!/bin/bash
# Gradle wrapper script

# Determine the project base directory
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

# Check if gradle wrapper jar exists
GRADLE_WRAPPER_JAR="gradle/wrapper/gradle-wrapper.jar"

if [ ! -f "$GRADLE_WRAPPER_JAR" ]; then
    echo "Error: Gradle wrapper JAR not found at $GRADLE_WRAPPER_JAR"
    echo "Please ensure you have the gradle wrapper properly set up."
    exit 1
fi

# Get Java command
if [ -n "$JAVA_HOME" ]; then
    JAVA_CMD="$JAVA_HOME/bin/java"
else
    JAVA_CMD="java"
fi

# Check Java version
if ! command -v "$JAVA_CMD" &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

# Run Gradle wrapper
exec "$JAVA_CMD" \
    -classpath "$GRADLE_WRAPPER_JAR" \
    -Dorg.gradle.appname=gradlew \
    org.gradle.wrapper.GradleWrapperMain \
    "$@"
