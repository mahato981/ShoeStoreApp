#!/bin/bash

echo "🔍 Quick Build Check - Showing Only Errors"
echo "=========================================="
echo ""

# Build and capture only error lines
./gradlew build 2>&1 | grep -E "Error:|WrongViewCast|line [0-9]+:" | head -50

echo ""
echo "=========================================="
echo "Run './gradlew build' for full output"
