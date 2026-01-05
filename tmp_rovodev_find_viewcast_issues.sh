#!/bin/bash

echo "🔍 Searching for Potential WrongViewCast Issues..."
echo "=================================================="
echo ""

# Find all findViewById calls and check against layout XMLs
echo "Analyzing findViewById calls..."
echo ""

# Get all Java files with findViewById
for java_file in app/src/main/java/com/shoesstore/app/*.java; do
    filename=$(basename "$java_file")
    
    # Extract findViewById calls with variable types
    grep -n "findViewById(R.id\." "$java_file" | while IFS=: read -r line_num content; do
        # Extract the view type and ID
        if [[ $content =~ ([A-Za-z]+View|Button|TextView|EditText|LinearLayout|FrameLayout|GridLayout|CheckBox|Spinner)[[:space:]]+[a-zA-Z_][a-zA-Z0-9_]*[[:space:]]*=[[:space:]]*findViewById\(R\.id\.([a-zA-Z_][a-zA-Z0-9_]*)\) ]]; then
            view_type="${BASH_REMATCH[1]}"
            view_id="${BASH_REMATCH[2]}"
            
            echo "File: $filename:$line_num"
            echo "  Type: $view_type, ID: $view_id"
            
            # Search for this ID in layout files
            layout_matches=$(grep -r "android:id=\"@+id/$view_id\"" app/src/main/res/layout/*.xml 2>/dev/null)
            
            if [ -n "$layout_matches" ]; then
                echo "  Layout definitions:"
                echo "$layout_matches" | while read -r match; do
                    # Extract the actual view type from XML
                    xml_file=$(echo "$match" | cut -d: -f1)
                    xml_line=$(grep -B 2 "android:id=\"@+id/$view_id\"" "$xml_file" | head -3)
                    echo "    $xml_line" | grep -oP "<\K[A-Za-z.]+(?=[[:space:]]|>)" | head -1
                done
            fi
            echo ""
        fi
    done
done
