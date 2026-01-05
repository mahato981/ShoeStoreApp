#!/bin/bash

echo "🔍 Extracting ALL Lint Errors from Build..."
echo "============================================"
echo ""

# Check if lint report exists
LINT_REPORT="app/build/intermediates/lint_intermediate_text_report/debug/lint-results-debug.txt"

if [ ! -f "$LINT_REPORT" ]; then
    echo "❌ Lint report not found. Running build first..."
    echo ""
    ./gradlew build > /dev/null 2>&1
    
    if [ ! -f "$LINT_REPORT" ]; then
        echo "❌ Build completed but report still not found."
        echo "Please run: ./gradlew build"
        exit 1
    fi
fi

echo "📄 Found lint report: $LINT_REPORT"
echo ""

# Count total errors
TOTAL_ERRORS=$(grep -c "^Error:" "$LINT_REPORT" 2>/dev/null || echo "0")
echo "📊 Total Errors Found: $TOTAL_ERRORS"
echo ""

if [ "$TOTAL_ERRORS" -eq 0 ]; then
    echo "✅ No errors found!"
    exit 0
fi

echo "=================================================="
echo "DETAILED ERROR LIST:"
echo "=================================================="
echo ""

# Extract each error with context
ERROR_NUM=1
while IFS= read -r line; do
    if [[ $line =~ ^Error: ]]; then
        echo "❌ ERROR #$ERROR_NUM"
        echo "-------------------"
        
        # Print the error line
        echo "$line"
        
        # Get the next few lines for context (file path, line number, etc.)
        CONTEXT=$(grep -A 8 "$line" "$LINT_REPORT" | head -9)
        echo "$CONTEXT" | tail -8
        
        echo ""
        ((ERROR_NUM++))
    fi
done < <(grep "^Error:" "$LINT_REPORT")

echo "=================================================="
echo "📝 Summary: $TOTAL_ERRORS errors need to be fixed"
echo "=================================================="
