package com.example.project.CategoriesDetails;

public class CategoryModel {

    String Type,Price,Image, addBtn, desc;
    private int numberInCart;

    public CategoryModel() {
    }

    public CategoryModel(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public CategoryModel(String addBtn) {
        this.addBtn = addBtn;
    }

    public CategoryModel(String type, String price, String image, String addBtn, String desc) {
        this.Type = type;
        this.Price = price;
        this.Image = image;
        this.addBtn = addBtn;
        this.desc = desc;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getAddBtn() {
        return addBtn;
    }

    public void setAddBtn(String addBtn) {
        this.addBtn = addBtn;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
