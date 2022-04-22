package com.homework.firesotreretrieve;

public class Tutorial {
    private String title,description,food,drink,priority,token;
    private String id;

    public Tutorial(){
        //need empty constructor
    }

    public Tutorial(String title, String description, String priority,String food,String drink,String token) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.food = food;
        this.drink = drink;
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFood() {
        return food;
    }

    public String getDrink() {
        return drink;
    }

    public String getToken() {
        return token;
    }
}