package com.shoesstore.app.utils;

import android.content.Context;
import com.shoesstore.app.database.DatabaseHelper;
import com.shoesstore.app.models.Product;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DataSeeder {
    
    public static void seedDatabase(Context context) {
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        
        // Check if database is already seeded
        if (db.getProductCount() > 0) {
            return;
        }

        List<Product> products = Arrays.asList(
            // Male - Sports Shoes
            createProduct("Nike Air Max | Running Shoes | Nike", "Male", "Sports Shoes", "₹8,995", "₹7,499", 
                "👟", "blue_gradient", Arrays.asList(8, 9, 10, 11), 1, 4),
            createProduct("Under Armour Sports | Training Shoes | UA", "Male", "Sports Shoes", "₹7,299", "₹5,999",
                "⚽", "orange_gradient", Arrays.asList(8, 9, 10, 11, 12), 1, 5),
            createProduct("Converse All Star | Basketball Shoes | Converse", "Male", "Sports Shoes", "₹4,999", "₹3,999",
                "👟", "red_gradient", Arrays.asList(8, 9, 10, 11), 2, 2),
            createProduct("Puma Speed 500 | Running Shoes | Puma", "Male", "Sports Shoes", "₹6,799", "₹5,299",
                "👟", "green_gradient", Arrays.asList(8, 9, 10, 11, 12), 1, 9),
            
            // Male - Running Shoes
            createProduct("Reebok Floatride | Running Shoes | Reebok", "Male", "Running Shoes", "₹6,499", "₹5,199",
                "👟", "gray_gradient", Arrays.asList(9, 10, 11, 12), 2, 1),
            createProduct("Adidas Ultraboost | Performance Running | Adidas", "Male", "Running Shoes", "₹13,999", "₹11,499",
                "🏃", "indigo_gradient", Arrays.asList(8, 9, 10, 11, 12), 2, 8),
            
            // Male - Casual Slippers
            createProduct("Crocs Classic | Casual Clogs | Crocs", "Male", "Casual Slippers", "₹2,999", "₹2,499",
                "🩴", "green_gradient", Arrays.asList(8, 9, 10, 11, 12), 4, 2),
            createProduct("Birkenstock Arizona | Comfort Sandals | Birkenstock", "Male", "Casual Slippers", "₹4,599", "₹3,799",
                "🩴", "amber_gradient", Arrays.asList(8, 9, 10, 11, 12), 4, 3),
            createProduct("Adidas Adilette | Slide Sandals | Adidas", "Male", "Casual Slippers", "₹1,999", "₹1,599",
                "🩴", "blue_gradient", Arrays.asList(8, 9, 10, 11, 12), 4, 7),
            
            // Male - Formal Shoes
            createProduct("Oxford Formal | Office Shoes | Clarks", "Male", "Formal Shoes", "₹5,499", "₹4,399",
                "👞", "dark_gradient", Arrays.asList(8, 9, 10, 11), 2, 5),
            createProduct("Clarks Derby | Business Formal | Clarks", "Male", "Formal Shoes", "₹6,299", "₹4,999",
                "👞", "stone_gradient", Arrays.asList(8, 9, 10, 11), 2, 6),
            createProduct("Bata Executive | Formal Shoes | Bata", "Male", "Formal Shoes", "₹4,299", "₹3,499",
                "👞", "dark_gradient", Arrays.asList(7, 8, 9, 10, 11), 2, 9),
            
            // Male - Boots
            createProduct("Timberland Pro | Work Boots | Timberland", "Male", "Boots", "₹11,999", "₹9,999",
                "🥾", "dark_brown_gradient", Arrays.asList(9, 10, 11, 12, 13), 4, 4),
            createProduct("CAT Footwear | Leather Boots | Caterpillar", "Male", "Boots", "₹7,999", "₹6,499",
                "🥾", "brown_gradient", Arrays.asList(8, 9, 10, 11, 12), 4, 1),
            createProduct("Red Tape Boots | Casual Boots | Red Tape", "Male", "Boots", "₹5,999", "₹4,799",
                "🥾", "amber_gradient", Arrays.asList(8, 9, 10, 11, 12), 4, 8),
            
            // Female - Running Shoes
            createProduct("Adidas Ultraboost | Women's Running | Adidas", "Female", "Running Shoes", "₹12,499", "₹9,999",
                "👟", "pink_gradient", Arrays.asList(6, 7, 8, 9), 2, 3),
            createProduct("Asics Gel-Kayano | Running Shoes | Asics", "Female", "Running Shoes", "₹8,799", "₹6,999",
                "🏃", "cyan_gradient", Arrays.asList(5, 6, 7, 8, 9), 2, 4),
            createProduct("Nike Pegasus | Women's Running | Nike", "Female", "Running Shoes", "₹9,999", "₹7,999",
                "👟", "purple_gradient", Arrays.asList(6, 7, 8, 9), 2, 10),
            
            // Female - Casual Shoes
            createProduct("New Balance 574 | Casual Sneakers | New Balance", "Female", "Casual Shoes", "₹9,999", "₹7,999",
                "👟", "indigo_gradient", Arrays.asList(6, 7, 8, 9, 10), 1, 3),
            createProduct("Bata Comfit | Ladies Casual | Bata", "Female", "Casual Shoes", "₹2,999", "₹2,399",
                "👞", "rose_gradient", Arrays.asList(5, 6, 7, 8, 9), 1, 7),
            createProduct("Skechers Go Walk | Comfort Shoes | Skechers", "Female", "Casual Shoes", "₹5,499", "₹4,299",
                "👟", "pink_gradient", Arrays.asList(5, 6, 7, 8, 9), 1, 11),
            
            // Female - Sandals
            createProduct("Havaianas Flip Flops | Beach Sandals | Havaianas", "Female", "Sandals", "₹1,799", "₹1,399",
                "🩴", "purple_gradient", Arrays.asList(5, 6, 7, 8), 1, 2),
            createProduct("Wedge Sandals | Comfort Wedges | Metro", "Female", "Sandals", "₹2,199", "₹1,799",
                "🩴", "teal_gradient", Arrays.asList(5, 6, 7, 8), 1, 8),
            createProduct("Crocs Bistro | Kitchen Clogs | Crocs", "Female", "Sandals", "₹2,499", "₹1,999",
                "🩴", "green_gradient", Arrays.asList(5, 6, 7, 8, 9), 1, 12),
            
            // Female - Heels
            createProduct("Stiletto Heels | Party Wear | Metro", "Female", "Heels", "₹4,299", "₹3,499",
                "👠", "red_pink_gradient", Arrays.asList(5, 6, 7, 8), 1, 1),
            createProduct("Block Heels | Office Heels | Inc.5", "Female", "Heels", "₹3,899", "₹2,999",
                "👠", "purple_pink_gradient", Arrays.asList(5, 6, 7, 8), 1, 6),
            createProduct("Kitten Heels | Formal Heels | Mochi", "Female", "Heels", "₹3,299", "₹2,599",
                "👠", "rose_gradient", Arrays.asList(5, 6, 7, 8), 1, 13),
            
            // Kids - Sneakers
            createProduct("Puma Kids | Junior Sneakers | Puma", "Kids", "Sneakers", "₹3,499", "₹2,799",
                "👟", "yellow_gradient", Arrays.asList(1, 2, 3, 4, 5), 3, 1),
            createProduct("Vans Classic | Kids Skate Shoes | Vans", "Kids", "Sneakers", "₹2,799", "₹2,299",
                "👟", "slate_gradient", Arrays.asList(1, 2, 3, 4, 5, 6), 3, 3),
            createProduct("Nike Kids Revolution | Running Shoes | Nike", "Kids", "Sneakers", "₹3,999", "₹3,199",
                "👟", "blue_gradient", Arrays.asList(1, 2, 3, 4, 5, 6), 3, 7),
            
            // Kids - Sandals
            createProduct("Disney Characters | Kids Sandals | Disney", "Kids", "Sandals", "₹999", "₹799",
                "🩴", "red_gradient", Arrays.asList(1, 2, 3, 4), 3, 5),
            createProduct("Crocs Kids | Comfort Clogs | Crocs", "Kids", "Sandals", "₹1,299", "₹999",
                "🩴", "lime_gradient", Arrays.asList(1, 2, 3, 4), 3, 6),
            createProduct("Relaxo Kids | Daily Wear Sandals | Relaxo", "Kids", "Sandals", "₹699", "₹549",
                "🩴", "yellow_gradient", Arrays.asList(1, 2, 3, 4, 5), 3, 8),
            
            // Kids - School Shoes
            createProduct("Bata School | Black School Shoes | Bata", "Kids", "School Shoes", "₹1,899", "₹1,499",
                "👞", "blue_cyan_gradient", Arrays.asList(1, 2, 3, 4, 5), 3, 2),
            createProduct("Skechers Light-Up | LED School Shoes | Skechers", "Kids", "School Shoes", "₹2,299", "₹1,899",
                "👟", "violet_gradient", Arrays.asList(1, 2, 3, 4, 5), 3, 4),
            createProduct("Action Kids | School Shoes | Action", "Kids", "School Shoes", "₹1,499", "₹1,199",
                "👞", "blue_gradient", Arrays.asList(1, 2, 3, 4, 5, 6), 3, 9)
        );

        for (Product product : products) {
            db.insertProduct(product);
        }
    }

    private static Product createProduct(String name, String category, String subCategory,
                                        String price, String discountPrice, String emoji,
                                        String colorGradient, List<Integer> sizes,
                                        int shelf, int row) {
        Product product = new Product(name, category, subCategory, price, discountPrice,
                emoji, Arrays.asList(emoji, emoji, emoji), colorGradient, sizes, shelf, row);
        
        // Generate random box number
        Random random = new Random();
        product.setBoxNumber(String.valueOf(100000 + random.nextInt(900000)));
        product.setCode("9832");
        
        // Extract brand name from product name (after last |)
        String brand = "Premium";
        if (name.contains("|")) {
            String[] parts = name.split("\\|");
            brand = parts[parts.length - 1].trim();
        }
        
        // Create detailed description
        product.setDescription("Premium quality " + subCategory.toLowerCase() + 
                " by " + brand + ". Designed for " + category.toLowerCase() + 
                " customers with focus on comfort, durability, and style. " +
                "Features high-quality materials, cushioned insoles, and modern design. " +
                "Perfect for daily wear, sports activities, or special occasions. " +
                "Available in multiple sizes with proper arch support and breathable construction.");
        
        return product;
    }
}
