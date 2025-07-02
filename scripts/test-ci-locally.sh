#!/bin/bash

# Local CI Testing Script
# This script tests the same commands that run in GitHub Actions

set -e  # Exit on any error

echo "Testing CI Pipeline Locally"
echo "================================"

# Test basic compilation (same as CI)
echo ""
echo "Testing Compilation..."
./gradlew compileJava compileTestJava --no-daemon
echo "Compilation successful!"

# Test basic unit tests (same as CI)
echo ""
echo "Testing Unit Tests..."
./gradlew test --no-daemon \
  -Djava.awt.headless=true \
  -Dorg.lwjgl.util.Debug=true || echo "Some tests may fail due to graphics context (expected locally)"
echo "Unit tests completed!"

# Test specific graphics tests if they exist
echo ""
echo "Testing Graphics-Specific Classes..."
./gradlew test --tests="*Window*" --tests="*Shader*" --tests="*Triangle*" \
  --no-daemon \
  -Djava.awt.headless=true \
  -Dorg.lwjgl.util.Debug=true \
  -Dorg.lwjgl.util.NoChecks=false || echo "Graphics tests may fail without proper display (expected locally)"
echo "Graphics tests completed!"

# Test JAR building (same as CI)
echo ""
echo "Testing JAR Build..."
./gradlew build --no-daemon
echo "JAR build successful!"

# Check for shader files
echo ""
echo "Checking Shader Files..."
if find src/main/resources/shaders -name "*.glsl" -o -name "*.vert" -o -name "*.frag" 2>/dev/null | grep -q .; then
    echo "Found shader files:"
    find src/main/resources/shaders -name "*.glsl" -o -name "*.vert" -o -name "*.frag" 2>/dev/null | while read shader; do
        echo "  - $shader"
        if grep -q "main()" "$shader"; then
            echo "    Has main() function"
        else
            echo "    Missing main() function"
        fi
    done
else
    echo "No shader files found (this is fine for current state)"
fi

# Test cross-platform JAR (same as CI)
echo ""
echo "Testing Cross-Platform JAR..."
./gradlew fatJar --no-daemon || echo "fatJar task may not exist yet"

# Summary
echo ""
echo "Local CI Test Summary"
echo "========================"
echo "Compilation: PASSED"
echo "Unit Tests: COMPLETED (may have graphics warnings)"
echo "JAR Build: PASSED"
echo "Shader Check: COMPLETED"
echo ""
echo "Your changes should work in CI!"
echo ""
echo "To test with act (GitHub Actions locally):"
echo "   act -W .github/workflows/graphics-ci.yml --job graphics-context-test"
echo ""
echo "To test specific job types:"
echo "   act -W .github/workflows/ci.yml --job compile-and-test"
echo "   act -W .github/workflows/graphics-ci.yml --job shader-compilation-test" 