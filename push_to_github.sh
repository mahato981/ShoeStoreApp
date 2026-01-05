#!/bin/bash

echo "🚀 Pushing Shoe Store App to GitHub"
echo "======================================"
echo ""

# Check if we have changes to commit
if [ -n "$(git status --porcelain)" ]; then
    echo "📝 Changes detected, committing..."
    git add .
    git commit -m "Fix lint error: Change TextView to ImageView for menuIcon in EditProductsActivity"
else
    echo "✅ No new changes to commit"
fi

echo ""
echo "📤 Pushing to GitHub..."
echo ""
echo "⚠️  You will need to authenticate with GitHub"
echo ""

# Push
git push origin main

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Successfully pushed to GitHub!"
    echo ""
    echo "🎯 Next steps:"
    echo "1. Go to: https://github.com/mahato981/ShoeStoreApp"
    echo "2. Click 'Actions' tab"
    echo "3. Watch the workflow run"
    echo "4. Download APK when complete (~5 minutes)"
    echo ""
else
    echo ""
    echo "❌ Push failed. Please run manually:"
    echo ""
    echo "   cd ShoeStoreApp"
    echo "   git push origin main"
    echo ""
fi
