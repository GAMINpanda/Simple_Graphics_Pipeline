#!/bin/bash

# Quick Workflow Validation Script
# Validates GitHub Actions YAML syntax and common issues

echo "🔍 Validating GitHub Actions Workflows"
echo "======================================"

# Check YAML syntax
echo ""
echo "📝 Checking YAML Syntax..."

for workflow in .github/workflows/*.yml; do
    echo "Checking: $workflow"
    if command -v yamllint &> /dev/null; then
        yamllint "$workflow" && echo "✅ YAML syntax valid"
    elif python3 -c "import yaml; yaml.safe_load(open('$workflow'))" 2>/dev/null; then
        echo "✅ YAML syntax valid"
    else
        echo "⚠️ Install yamllint for better validation: brew install yamllint"
        # Basic check
        if grep -q "name:" "$workflow" && grep -q "on:" "$workflow" && grep -q "jobs:" "$workflow"; then
            echo "✅ Basic structure looks good"
        else
            echo "❌ Missing required fields (name, on, jobs)"
        fi
    fi
    echo ""
done

# Check for common issues
echo "🔍 Checking for Common Issues..."

echo "Checking for gradlew permissions..."
if grep -q "chmod +x gradlew" .github/workflows/*.yml; then
    echo "✅ gradlew permissions handled"
else
    echo "⚠️ Consider adding 'chmod +x gradlew' step"
fi

echo "Checking for proper error handling..."
if grep -q "|| echo" .github/workflows/*.yml; then
    echo "✅ Error handling found"
else
    echo "⚠️ Consider adding error handling with '|| echo'"
fi

echo "Checking for caching..."
if grep -q "actions/cache" .github/workflows/*.yml; then
    echo "✅ Gradle caching enabled"
else
    echo "⚠️ Consider adding Gradle caching for faster builds"
fi

echo ""
echo "📊 Validation Complete!"
echo "Run ./scripts/test-ci-locally.sh to test actual commands" 