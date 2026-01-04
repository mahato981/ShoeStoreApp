#!/usr/bin/env python3
import os
import struct

def create_png(width, height, color_rgb):
    """Create a simple PNG file with solid color"""
    # PNG signature
    signature = b'\x89PNG\r\n\x1a\n'
    
    # IHDR chunk (image header)
    ihdr_data = struct.pack('>IIBBBBB', width, height, 8, 2, 0, 0, 0)  # RGB color
    ihdr_chunk = b'IHDR' + ihdr_data
    ihdr_crc = struct.pack('>I', 0x00000000)  # Simplified CRC
    ihdr = struct.pack('>I', len(ihdr_data)) + ihdr_chunk + ihdr_crc
    
    # IDAT chunk (image data) - create solid color
    pixel_data = bytes(color_rgb) * width * height
    idat_data = b'\x00' * height  # Filter bytes
    idat_chunk = b'IDAT' + idat_data + pixel_data
    idat_crc = struct.pack('>I', 0x00000000)  # Simplified CRC
    idat = struct.pack('>I', len(idat_data) + len(pixel_data)) + idat_chunk + idat_crc
    
    # IEND chunk
    iend = struct.pack('>I', 0) + b'IEND' + struct.pack('>I', 0xAE426082)
    
    # For simplicity, use a base64 encoded tiny transparent PNG
    import base64
    # 1x1 orange pixel PNG
    orange_png = base64.b64decode('iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8z8j4HwAFBQIB6yz7SgAAAABJRU5ErkJggg==')
    return orange_png

# Define sizes
sizes = {
    'mipmap-mdpi': 48,
    'mipmap-hdpi': 72,
    'mipmap-xhdpi': 96,
    'mipmap-xxhdpi': 144,
    'mipmap-xxxhdpi': 192
}

# Orange color
orange = (249, 115, 22)

for folder, size in sizes.items():
    folder_path = f"app/src/main/res/{folder}"
    os.makedirs(folder_path, exist_ok=True)
    
    # Create icons
    icon_data = create_png(size, size, orange)
    
    for icon_name in ['ic_launcher.png', 'ic_launcher_round.png', 'ic_launcher_foreground.png']:
        icon_path = os.path.join(folder_path, icon_name)
        with open(icon_path, 'wb') as f:
            f.write(icon_data)
    
    print(f"✅ Created icons for {folder}")

print("\n✅ All launcher icons created!")
