package org.example.cinema;

import java.io.Serializable;
import java.util.Date;

public class FilmSession extends Session {
    private Film film;
    private Date date;
    private Hall hall;
    private int cost;

    public FilmSession(Film f, Date d, Hall h, int cost)
    {
        if(!h.canShow(f)) throw new IllegalCallerException("Фильм не поддерживается");
        film = f;
        date = d;
        hall = h;
        this.cost = cost;
    }

    public Film getFilm() {
        return film;
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
        return "Фильм: "+ film.getName()+ " Дата сеанса: "+ date;
    }

}