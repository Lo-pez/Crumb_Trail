package com.example.crumbtrail.data.model;

import java.time.LocalDate;

public class Food {
    private long fdcID;
    private String description;
    private String lowercaseDescription;
    private String brandOwner;
    private String brandName;
    private String ingredients;
    private String foodCategory;
    private String packageWeight;
    private String servingSizeUnit;
    private long servingSize;
    private String householdServingFullText;
    private String shortDescription;
    private String preparationStateCode;
    private String allHighlightFields;
    private double score;

    public long getFDCID() { return fdcID; }
    public void setFDCID(long value) { this.fdcID = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public String getLowercaseDescription() { return lowercaseDescription; }
    public void setLowercaseDescription(String value) { this.lowercaseDescription = value; }

    public String getBrandOwner() { return brandOwner; }
    public void setBrandOwner(String value) { this.brandOwner = value; }

    public String getBrandName() { return brandName; }
    public void setBrandName(String value) { this.brandName = value; }

    public String getIngredients() { return ingredients; }
    public void setIngredients(String value) { this.ingredients = value; }

    public String getFoodCategory() { return foodCategory; }
    public void setFoodCategory(String value) { this.foodCategory = value; }

    public String getPackageWeight() { return packageWeight; }
    public void setPackageWeight(String value) { this.packageWeight = value; }

    public String getServingSizeUnit() { return servingSizeUnit; }
    public void setServingSizeUnit(String value) { this.servingSizeUnit = value; }

    public long getServingSize() { return servingSize; }
    public void setServingSize(long value) { this.servingSize = value; }

    public String getHouseholdServingFullText() { return householdServingFullText; }
    public void setHouseholdServingFullText(String value) { this.householdServingFullText = value; }

    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String value) { this.shortDescription = value; }

    public String getPreparationStateCode() { return preparationStateCode; }
    public void setPreparationStateCode(String value) { this.preparationStateCode = value; }

    public String getAllHighlightFields() { return allHighlightFields; }
    public void setAllHighlightFields(String value) { this.allHighlightFields = value; }

    public double getScore() { return score; }
    public void setScore(double value) { this.score = value; }
}