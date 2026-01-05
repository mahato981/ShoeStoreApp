#!/bin/bash

echo "🔍 Running Gradle Build and Extracting Lint Errors..."
echo "=================================================="
echo ""

# Run the build and capture output
BUILD_OUTPUT=$(./gradlew build 2>&1)

# Check if build succeeded
if echo "$BUILD_OUTPUT" | grep -q "BUILD SUCCESSFUL"; then
    echo "✅ BUILD SUCCESSFUL - No lint errors!"
    echo ""
    exit 0
fi

# Extract error count
ERROR_COUNT=$(echo "$BUILD_OUTPUT" | grep -oP "Lint found \K\d+(?= error)" | head -1)
WARNING_COUNT=$(echo "$BUILD_OUTPUT" | grep -oP "Lint found \d+ errors?, \K\d+(?= warning)" | head -1)

echo "📊 Lint Results:"
echo "   Errors: $ERROR_COUNT"
echo "   Warnings: $WARNING_COUNT"
echo ""

# Extract the lint report path
LINT_REPORT=$(echo "$BUILD_OUTPUT" | grep -oP "lint_intermediate_text_report.*?lint-results-debug\.txt" | head -1)

if [ -n "$LINT_REPORT" ]; then
    FULL_PATH="app/build/intermediates/$LINT_REPORT"
    
    if [ -f "$FULL_PATH" ]; then
        echo "📄 Reading full lint report from: $FULL_PATH"
        echo ""
        echo "=================================================="
        echo "ALL LINT ERRORS:"
        echo "=================================================="
        echo ""
        
        # Extract and display all errors
        grep -A 5 "Error:" "$FULL_PATH" | head -100
        
        echo ""
        echo "=================================================="
        echo ""
        echo "💡 Tip: Full report available at:"
        echo "   $FULL_PATH"
    else
        echo "⚠️  Lint report file not found at: $FULL_PATH"
        echo ""
        echo "📝 Showing errors from build output:"
        echo ""
        echo "$BUILD_OUTPUT" | grep -A 10 "First failure:" | head -30
    fi
else
    echo "📝 Showing errors from build output:"
    echo ""
    echo "$BUILD_OUTPUT" | grep -A 10 "First failure:" | head -30
fi

echo ""
echo "=================================================="
echo "🔧 Ready to fix errors!"
echo "=================================================="
