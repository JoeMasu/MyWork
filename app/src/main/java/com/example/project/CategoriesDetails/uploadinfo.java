package com.example.project.CategoriesDetails;

public class uploadinfo {
    private String imageName;
    private String imageURL;
    private String Description;

    public uploadinfo(){}

    public uploadinfo(String imageName, String imageURL, String description) {
        this.imageName = imageName;
        this.imageURL = imageURL;
        Description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}