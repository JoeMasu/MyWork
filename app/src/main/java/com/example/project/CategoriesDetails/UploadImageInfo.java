package com.example.project.CategoriesDetails;

public class UploadImageInfo{
        private String imageName;
        private String imageURL;
        private String description;

    public UploadImageInfo() {
    }

    public UploadImageInfo(String imageName, String imageURL, String description) {
        this.imageName = imageName;
        this.imageURL = imageURL;
        this.description = description;
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
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}