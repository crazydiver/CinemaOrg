package org.example.cinema;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Film implements Serializable {
    private String name;
    private String creatingDate;
    private String type;
    private String genre;
    private Integer ageRating;

    public Film(String name, String creatingDate, String type, String genre, Integer ageRating)
    {
        this.name = name;
        this.creatingDate = creatingDate;
        this.type = type;
        this.genre = genre;
        this.ageRating = ageRating;
    }

    public String getName() {
        return name;
    }

    public String getCreatingDate() {
        return creatingDate;
    }

    public String getGenre() {
        return genre;
    }

    public String getType() {
        return type;
    }

    public Integer getAgeRating() { return ageRating;}

    @Override
    public String toString() {
        return name +" Жанр: "+ genre+ " Дата создания: "+ creatingDate;
    }

}
