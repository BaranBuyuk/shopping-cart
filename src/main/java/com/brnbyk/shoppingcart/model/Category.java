package com.brnbyk.shoppingcart.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 01:11
 **/
public class Category {

    //Composite pattern for category architecture

    private String title;
    private Category parent;
    private List<Category> subCategories;

    private Category(Category parent, String title) {
        this.title = title;
        this.parent = parent;
        subCategories = new ArrayList<>();
    }

    public static Category createCategory(String parentCategory) {
        return new Category(null, parentCategory);
    }

    public Category addSubCategory(String name) {
        Category child = new Category(this, name);
        subCategories.add(child);
        return child;
    }

    public String getTitle() {
        return title;
    }

    public Category getParent() {
        return parent;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    @Override
    public String toString() {
        return "Category{" +
                "title='" + title + '\'' +
                ", parent=" + parent +
                ", subCategories=" + subCategories +
                '}';
    }
}
