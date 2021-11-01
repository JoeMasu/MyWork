package com.example.project.Frontcategories;

public class FrontCatModel {

    String img;
    String Tittle;

    public FrontCatModel() {
    }

    public FrontCatModel(String img, String tittle) {
        this.img = img;
        Tittle = tittle;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTittle() {
        return Tittle;
    }

    public void setTittle(String tittle) {
        Tittle = tittle;
    }
}
