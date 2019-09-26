package com.brnbyk.shoppingcart.test.model;

import com.brnbyk.shoppingcart.model.Category;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 01:13
 **/

public class CategoryTest {


    @Test
    public void createNoParentCategoryTest() {
        Category category = Category.createCategory("Electronic");
        assertNull(category.getParent());
    }

    @Test
    public void createCategoryWithParentTest() {
        Category parentCategory = Category.createCategory("Electronic");
        parentCategory.addSubCategory("Mobil Phones");

        assertNull(parentCategory.getParent());
        Assert.assertFalse(parentCategory.getSubCategories().isEmpty());

        assertNotNull(parentCategory.getSubCategories().get(0).getParent());
    }
}
