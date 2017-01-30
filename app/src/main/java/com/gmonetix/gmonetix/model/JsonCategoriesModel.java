package com.gmonetix.gmonetix.model;

public class JsonCategoriesModel {

    private int categoriesId;
    private String categoriesName, categoriesDescription;

    public int getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(int categoriesId) {
        this.categoriesId = categoriesId;
    }

    public String getCategoriesName() {
        return categoriesName;
    }

    public void setCategoriesName(String categoriedName) {
        this.categoriesName = categoriedName;
    }

    public String getCategoriesDescription() {
        return categoriesDescription;
    }

    public void setCategoriesDescription(String categoriesDescription) {
        this.categoriesDescription = categoriesDescription;
    }
}
