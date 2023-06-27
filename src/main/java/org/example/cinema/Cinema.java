package org.example.cinema;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cinema implements Serializable {

    private String name;
    private String description;
    private List<Hall> halls;
    private List<Session> sessions;
    private int incomes;

    public Cinema(String name, String description, List<Hall> halls)
    {
        this.name = name;
        this.description = description;
        this.halls = new ArrayList<>(halls);
        sessions = new ArrayList<>();
        incomes = 0;
    }

    @Override
    public String toString() {
        return name;
    }

    public void createSession(Hall h, Date d, Film f, int cost)
    {
        if(!halls.contains(h)) throw new IllegalArgumentException("Кинотеатр не имеет этот зал");
        Session s = (Session) new FilmSession(f,d,h.clone(),cost);
        sessions.add(s);
    }

    public void createClownSesshion(ClownSession cs){
        sessions.add(cs);
        incomes += cs.getCost();
    }

    public void buyTicket(Client c, FilmSession s, int x, int y) throws IllegalAccessException {
        if(!sessions.contains(s)) throw new IllegalArgumentException("Такого сеанса нет в кинотеатре");
        var cost = getPrice(s,c);
        c.Withdraw(cost);
        s.getHall().allocatePlace(x,y);
        incomes += cost;

    }

    public String getName() {
        return name;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public String getDescription() {
        return description;
    }

    public int getIncomes() {
        return incomes;
    }
    public int getPrice(FilmSession s, Client c)
    {
        int cost = s.getCost();
        if(c.getTicketsBought() > 3)
            cost = (int) (0.7 * cost);
        else if(c.getTicketsBought() >=2)
            cost = (int)(0.8*cost);
        return cost;
    }

    public int getAge(FilmSession s){
        return s.getFilm().getAgeRating();
    }

    public void createEatSession(EatSession eatSession) {
        sessions.add(eatSession);
        incomes += eatSession.getCost();
    }
}