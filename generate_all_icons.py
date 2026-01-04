#!/usr/bin/env python3
"""
Generate proper launcher icons for Android without external dependencies.
Creates valid PNG files with proper structure and CRC checksums.
"""
import os
import zlib
import struct

def create_png(width, height, color_rgba=(249, 115, 22, 255)):
    """Create a valid PNG file with solid color"""
    # PNG signature
    signature = b'\x89PNG\r\n\x1a\n'
    
    def create_chunk(chunk_type, data):
        """Create a PNG chunk with proper CRC"""
        chunk = chunk_type + data
        crc = zlib.crc32(chunk) & 0xffffffff
        return struct.pack('>I', len(data)) + chunk + struct.pack('>I', crc)
    
    # IHDR chunk (image header) - RGBA color type
    ihdr_data = struct.pack('>IIBBBBB', width, height, 8, 6, 0, 0, 0)  # 6 = RGBA
    ihdr = create_chunk(b'IHDR', ihdr_data)
    
    # Create image data (solid color)
    rows = []
    for y in range(height):
        # Filter type 0 (None) at start of each row
        row = b'\x00' + (bytes(color_rgba) * width)
        rows.append(row)
    
    raw_data = b''.join(rows)
    compressed_data = zlib.compress(raw_data, 9)
    
    # IDAT chunk (image data)
    idat = create_chunk(b'IDAT', compressed_data)
    
    # IEND chunk
    iend = create_chunk(b'IEND', b'')
    
    # Combine all chunks
    return signature + ihdr + idat + iend

def create_round_icon_png(width, height, color_rgba=(249, 115, 22, 255)):
    """Create a round icon (circle with transparency)"""
    signature = b'\x89PNG\r\n\x1a\n'
    
    def create_chunk(chunk_type, data):
        chunk = chunk_type + data
        crc = zlib.crc32(chunk) & 0xffffffff
        return struct.pack('>I', len(data)) + chunk + struct.pack('>I', crc)
    
    ihdr_data = struct.pack('>IIBBBBB', width, height, 8, 6, 0, 0, 0)
    ihdr = create_chunk(b'IHDR', ihdr_data)
    
    # Create circular icon
    rows = []
    center_x = width / 2
    center_y = height / 2
    radius = min(width, height) / 2
    
    for y in range(height):
        row_data = b'\x00'  # Filter type
        for x in range(width):
            # Calculate distance from center
            dx = x - center_x + 0.5
            dy = y - center_y + 0.5
            distance = (dx * dx + dy * dy) ** 0.5
            
            if distance <= radius:
                # Inside circle - use color
                row_data += bytes(color_rgba)
            else:
                # Outside circle - transparent
                row_data += bytes([0, 0, 0, 0])
        rows.append(row_data)
    
    raw_data = b''.join(rows)
    compressed_data = zlib.compress(raw_data, 9)
    idat = create_chunk(b'IDAT', compressed_data)
    iend = create_chunk(b'IEND', b'')
    
    return signature + ihdr + idat + iend

# Define sizes for different density folders
sizes = {
    'mipmap-mdpi': 48,
    'mipmap-hdpi': 72,
    'mipmap-xhdpi': 96,
    'mipmap-xxhdpi': 144,
    'mipmap-xxxhdpi': 192
}

# Orange color (RGB + Alpha)
orange_rgba = (249, 115, 22, 255)

print("Generating Android launcher icons...")
print("=" * 50)

for folder, size in sizes.items():
    folder_path = f"app/src/main/res/{folder}"
    os.makedirs(folder_path, exist_ok=True)
    
    # Create square launcher icon
    icon_data = create_png(size, size, orange_rgba)
    icon_path = os.path.join(folder_path, 'ic_launcher.png')
    with open(icon_path, 'wb') as f:
        f.write(icon_data)
    print(f"✅ Created {icon_path} ({size}x{size}px, {len(icon_data)} bytes)")
    
    # Create round launcher icon
    round_icon_data = create_round_icon_png(size, size, orange_rgba)
    round_icon_path = os.path.join(folder_path, 'ic_launcher_round.png')
    with open(round_icon_path, 'wb') as f:
        f.write(round_icon_data)
    print(f"✅ Created {round_icon_path} ({size}x{size}px, {len(round_icon_data)} bytes)")
    
    # Create foreground icon (same as regular for now)
    fg_icon_path = os.path.join(folder_path, 'ic_launcher_foreground.png')
    with open(fg_icon_path, 'wb') as f:
        f.write(icon_data)
    print(f"✅ Created {fg_icon_path} ({size}x{size}px, {len(icon_data)} bytes)")
    print()

print("=" * 50)
print("✅ All launcher icons generated successfully!")
