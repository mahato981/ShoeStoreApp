package com.shoesstore.app.models;

import java.util.List;

public class Product {
    private int id;
    private String name;
    private String category;
    private String subCategory;
    private String price;
    private String discountPrice;
    private String minPrice;
    private String image;
    private List<String> images;
    private String color;
    private List<Integer> sizes;
    private int shelfLocation;
    private int rowLocation;
    private String boxNumber;
    private String code;
    private String description;

    public Product() {
    }

    public Product(String name, String category, String subCategory, String price, 
                   String discountPrice, String image, List<String> images, String color, 
                   List<Integer> sizes, int shelfLocation, int rowLocation) {
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.price = price;
        this.discountPrice = discountPrice;
        this.image = image;
        this.images = images;
        this.color = color;
        this.sizes = sizes;
        this.shelfLocation = shelfLocation;
        this.rowLocation = rowLocation;
        this.code = "9832";
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSubCategory() { return subCategory; }
    public void setSubCategory(String subCategory) { this.subCategory = subCategory; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getDiscountPrice() { return discountPrice; }
    public void setDiscountPrice(String discountPrice) { this.discountPrice = discountPrice; }

    public String getMinPrice() { return minPrice; }
    public void setMinPrice(String minPrice) { this.minPrice = minPrice; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public List<Integer> getSizes() { return sizes; }
    public void setSizes(List<Integer> sizes) { this.sizes = sizes; }

    public int getShelfLocation() { return shelfLocation; }
    public void setShelfLocation(int shelfLocation) { this.shelfLocation = shelfLocation; }

    public int getRowLocation() { return rowLocation; }
    public void setRowLocation(int rowLocation) { this.rowLocation = rowLocation; }

    public String getBoxNumber() { return boxNumber; }
    public void setBoxNumber(String boxNumber) { this.boxNumber = boxNumber; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
