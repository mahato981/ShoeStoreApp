package com.shoesstore.app.models;

public class Category {
    private String value;
    private String label;
    private String icon;
    private String parentCategory;

    public Category(String value, String label, String icon) {
        this.value = value;
        this.label = label;
        this.icon = icon;
    }

    public Category(String value, String label, String icon, String parentCategory) {
        this.value = value;
        this.label = label;
        this.icon = icon;
        this.parentCategory = parentCategory;
    }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public String getParentCategory() { return parentCategory; }
    public void setParentCategory(String parentCategory) { this.parentCategory = parentCategory; }
}
