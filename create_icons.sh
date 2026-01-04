#!/bin/bash
# Create simple launcher icons using base64 encoded 1x1 transparent PNG

BASE64_ICON="iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8z8DwHwAFBQIAX8jx0gAAAABJRU5ErkJggg=="

for size_dir in mipmap-mdpi mipmap-hdpi mipmap-xhdpi mipmap-xxhdpi mipmap-xxxhdpi; do
    dir="app/src/main/res/$size_dir"
    
    # Create launcher icons (will be orange from background)
    echo "$BASE64_ICON" | base64 -d > "$dir/ic_launcher.png" 2>/dev/null || \
    python3 -c "import base64; open('$dir/ic_launcher.png', 'wb').write(base64.b64decode('$BASE64_ICON'))"
    
    cp "$dir/ic_launcher.png" "$dir/ic_launcher_round.png"
    cp "$dir/ic_launcher.png" "$dir/ic_launcher_foreground.png"
done

echo "✅ Launcher icons created!"
