package org.example.cinema;

import java.util.Date;

public class ClownSession extends Session {

    private String clownName;

    private String name;
    private Date date;
    private Hall hall;
    private int cost;

    ClownSession(String name, String clownName, Date date, Hall hall){
        this.name = name;
        this.clownName = clownName;
        this.date = date;
        this.hall = hall;
        this.cost = countCost();
    }

    public int countCost(){
        return clownName.length()*30;
    }



    public String getClownName() {
        return clownName;
    }

    @Override
    public Film getFilm()  {
        throw new IllegalArgumentException("Клоунада не имеет фильма!");
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
