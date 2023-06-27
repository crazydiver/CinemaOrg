package org.example.cinema;

import java.util.Date;
import java.util.List;

public class EatSession extends Session {

    private Integer eatsCount;

    private String name;
    private Date date;
    private Hall hall = null;
    private int cost;

    EatSession(String name, int eatsCount, Date date){
        this.name = name;
        this.eatsCount = eatsCount;
        this.date = date;
//        this.hall = hall;
        this.cost = eatsCount * 50;
    }


    @Override
    public Film getFilm() {
        throw new IllegalArgumentException("фуршет не имеет фильма!");
    }

    public Hall getHall() {
        return hall;
    }

    public Date getDate() {
        return date;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Фильм: "+ name + " Дата сеанса: " + date;
    }

}
