package org.example.cinema;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Client implements Serializable {
    private String name;
    private String phone;
    private String mail;
    private int money;

    private int age;
    private int ticketsBought;

    public Client(String name, String phone, String mail, int budget, int age)
    {
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        money = budget;
        this.age = age;
        ticketsBought = 0;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public int getMoney() {
        return money;
    }

    public String getPhone() {
        return phone;
    }

    public int getAge() {return age;}

    public int getTicketsBought() {
        return ticketsBought;
    }

    public void Withdraw(int cost) throws IllegalAccessException {
        if(money < cost) throw new IllegalAccessException("Недостаточно средств, не хватает: "+ (cost - money));
        money-=cost;
        ticketsBought++;
    }

    public void createClownSesh(int clientMoney, Cinema c, Date date){
        Scanner sc = new Scanner(System.in);
        System.out.println("Session name: ");
        String name = sc.next();
        System.out.println("Enter clown name:");
        String clownName = sc.next();
        System.out.println("Выберите зал:");
        for(int i = 0; i < c.getHalls().size(); i++)
        {
            System.out.println(i+" "+ c.getHalls().get(i));
        }
        var hall = c.getHalls().get(sc.nextInt());

        ClownSession sesh = new ClownSession(name, clownName, date, hall);
        if (clientMoney < sesh.getCost()){
            System.out.println("Client has not enough money");
        } else {
            c.createClownSesshion(sesh);
            System.out.println("Event created successfully");
        }
    }

    public void createEatsSesh(int clientMoney, Cinema c, Date date){
        Scanner sc = new Scanner(System.in);
        System.out.println("Session name: ");
        String name = sc.next();
        System.out.println("Eats count");
        int eatsCount = sc.nextInt();

        EatSession sesh = new EatSession(name, eatsCount, date);
        if (clientMoney < sesh.getCost()){
            System.out.println("Client has not enough money");
        } else {
            c.createEatSession(sesh);
            System.out.println("Event created successfully");
        }
    }

    @Override
    public String toString() {
        return name;
    }

}