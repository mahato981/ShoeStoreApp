#!/bin/bash

echo "🚀 Building and Analyzing Lint Errors..."
echo "========================================"
echo ""

# Run build and capture output
echo "⏳ Running gradle build (this may take a minute)..."
./gradlew build > /tmp/build_output.txt 2>&1

BUILD_EXIT_CODE=$?

# Check build result
if [ $BUILD_EXIT_CODE -eq 0 ]; then
    echo "✅ BUILD SUCCESSFUL - No lint errors!"
    exit 0
fi

echo "⚠️  Build failed with lint errors"
echo ""

# Try to read the detailed lint report
LINT_REPORT="app/build/intermediates/lint_intermediate_text_report/debug/lint-results-debug.txt"

if [ -f "$LINT_REPORT" ]; then
    echo "📄 Reading detailed lint report..."
    echo ""
    
    # Extract error summary
    ERROR_COUNT=$(grep -c "^Error:" "$LINT_REPORT")
    
    echo "=================================================="
    echo "📊 FOUND $ERROR_COUNT LINT ERRORS"
    echo "=================================================="
    echo ""
    
    # Show each error with file and line number
    awk '
    /^Error:/ {
        error_count++
        error_line = $0
        in_error = 1
        file = ""
        line_num = ""
        next
    }
    
    in_error && /^    / {
        detail_line = $0
        # Try to extract file path and line number
        if (match(detail_line, /\/.*\.java:[0-9]+/)) {
            location = substr(detail_line, RSTART, RLENGTH)
            split(location, parts, ":")
            file = parts[1]
            line_num = parts[2]
            
            # Extract just the filename
            split(file, path_parts, "/")
            filename = path_parts[length(path_parts)]
            
            print "❌ ERROR #" error_count
            print "   File: " filename
            print "   Line: " line_num
            print "   " error_line
            print "   Location: " location
            print ""
            
            in_error = 0
        }
    }
    ' "$LINT_REPORT"
    
    echo "=================================================="
    echo ""
    echo "💡 Full report at: $LINT_REPORT"
    
else
    echo "⚠️  Detailed report not found, showing build output errors:"
    echo ""
    grep -A 5 "Error:" /tmp/build_output.txt | head -50
fi

echo ""
echo "🔧 Run this script anytime to check lint status"
