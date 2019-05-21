package com.programele.nibble;

public class Recipe {

    private String title;
    private String ingredients_list;
    private String description;
    private String photo;
    private String user_id;

    public Recipe()
    {
        title = "";
        ingredients_list = "";
        description = "";
        photo = "";
        user_id = "0";
    }

    public Recipe(String title, String ingredients_list, String description, String user_id, String photo) {
        this.title = title;
        this.ingredients_list = ingredients_list;
        this.description = description;
        this.user_id = user_id;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", ingredients_list='" + ingredients_list + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients_list() {
        return ingredients_list;
    }

    public void setIngredients_list(String ingredients_list) {
        this.ingredients_list = ingredients_list;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
