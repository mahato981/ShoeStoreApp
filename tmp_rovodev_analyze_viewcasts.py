#!/usr/bin/env python3
"""
Analyze Java files for potential WrongViewCast errors by comparing
findViewById declarations with actual XML layout definitions.
"""

import os
import re
import xml.etree.ElementTree as ET
from collections import defaultdict

# Parse layout XMLs to get view ID to type mapping
def parse_layouts(layout_dir):
    """Parse all layout XML files and return mapping of ID -> view type"""
    id_to_type = {}
    
    for xml_file in os.listdir(layout_dir):
        if not xml_file.endswith('.xml'):
            continue
            
        xml_path = os.path.join(layout_dir, xml_file)
        
        try:
            tree = ET.parse(xml_path)
            root = tree.getroot()
            
            # Find all elements with android:id attribute
            for elem in root.iter():
                id_attr = elem.get('{http://schemas.android.com/apk/res/android}id')
                if id_attr and id_attr.startswith('@+id/'):
                    view_id = id_attr.replace('@+id/', '')
                    view_type = elem.tag
                    
                    # Store or check for conflicts
                    if view_id in id_to_type and id_to_type[view_id] != view_type:
                        print(f"⚠️  ID conflict: {view_id} is both {id_to_type[view_id]} and {view_type}")
                    
                    id_to_type[view_id] = view_type
                    
        except Exception as e:
            print(f"Error parsing {xml_file}: {e}")
    
    return id_to_type

# Parse Java files for findViewById calls
def parse_java_files(java_dir, id_to_type):
    """Parse Java files and check findViewById type casts"""
    errors = []
    
    for java_file in os.listdir(java_dir):
        if not java_file.endswith('.java'):
            continue
            
        java_path = os.path.join(java_dir, java_file)
        
        with open(java_path, 'r') as f:
            content = f.read()
            lines = content.split('\n')
            
            # Pattern: Type varName = findViewById(R.id.viewId);
            pattern = r'(\w+(?:View|Layout|Button|Text\w+|CheckBox|Spinner|EditText|RecyclerView))\s+\w+\s*=\s*(?:\w+\.)?findViewById\(R\.id\.(\w+)\)'
            
            for i, line in enumerate(lines, 1):
                matches = re.finditer(pattern, line)
                
                for match in matches:
                    java_type = match.group(1)
                    view_id = match.group(2)
                    
                    if view_id in id_to_type:
                        xml_type = id_to_type[view_id]
                        
                        # Normalize types for comparison
                        xml_simple = xml_type.split('.')[-1]  # Handle full package names
                        
                        # Check if types match
                        if not types_compatible(java_type, xml_simple):
                            errors.append({
                                'file': java_file,
                                'line': i,
                                'java_type': java_type,
                                'xml_type': xml_simple,
                                'view_id': view_id,
                                'code': line.strip()
                            })
    
    return errors

def types_compatible(java_type, xml_type):
    """Check if Java type and XML type are compatible"""
    # Direct match
    if java_type == xml_type:
        return True
    
    # View is compatible with any view type in XML
    if java_type == 'View':
        return True
    
    # Check common parent-child relationships
    compatible_map = {
        'TextView': ['TextView', 'Button', 'EditText'],
        'ViewGroup': ['LinearLayout', 'FrameLayout', 'RelativeLayout', 'GridLayout'],
        'ImageView': ['ImageView'],
        'Button': ['Button'],
        'EditText': ['EditText'],
        'CheckBox': ['CheckBox'],
        'Spinner': ['Spinner'],
        'RecyclerView': ['RecyclerView'],
        'LinearLayout': ['LinearLayout'],
        'FrameLayout': ['FrameLayout'],
        'GridLayout': ['GridLayout'],
    }
    
    if java_type in compatible_map:
        return xml_type in compatible_map[java_type]
    
    return False

# Main execution
if __name__ == '__main__':
    print("🔍 Analyzing View Cast Compatibility...")
    print("=" * 60)
    
    layout_dir = 'app/src/main/res/layout'
    java_dir = 'app/src/main/java/com/shoesstore/app'
    
    # Parse layouts
    print(f"\n📄 Parsing layout XMLs from {layout_dir}...")
    id_to_type = parse_layouts(layout_dir)
    print(f"   Found {len(id_to_type)} view IDs")
    
    # Parse Java files
    print(f"\n☕ Parsing Java files from {java_dir}...")
    errors = parse_java_files(java_dir, id_to_type)
    
    # Report errors
    print("\n" + "=" * 60)
    if errors:
        print(f"❌ Found {len(errors)} WrongViewCast errors:\n")
        
        for i, error in enumerate(errors, 1):
            print(f"ERROR #{i}")
            print(f"  File: {error['file']}:{error['line']}")
            print(f"  View ID: {error['view_id']}")
            print(f"  Java Type: {error['java_type']}")
            print(f"  XML Type: {error['xml_type']}")
            print(f"  Code: {error['code']}")
            print()
    else:
        print("✅ No WrongViewCast errors found!")
    
    print("=" * 60)
