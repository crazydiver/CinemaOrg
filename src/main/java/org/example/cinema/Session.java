package org.example.cinema;


import java.io.Serializable;
import java.util.Date;

public abstract class Session implements Serializable {



    abstract public Film getFilm();

    abstract public Hall getHall();

    abstract public Date getDate();

    abstract public int getCost();

}