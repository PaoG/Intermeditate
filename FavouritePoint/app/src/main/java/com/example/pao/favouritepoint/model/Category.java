package com.example.pao.favouritepoint.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by possiblelabs on 7/21/15.
 */
@Table(name = "Categories")
public class Category extends Model {

    @Column(name = "Name", index = true)
    public String name;

    public Category() {
        super();
    }

    public Category(String name) {
        super();
        this.name = name;
    }


    //No relaciona
    public List<Item> items() {
        return getMany(Item.class, "Category");
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Category{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
