package org.example;

import org.example.cinema.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    private static List<Client> clients;
    private static List<Film> films;
    private static List<Cinema> cinemas;

    static Client client;

    private static Administrator administrator = new Administrator("Admin", "pass");

    private static void Load()
    {
        try{
            cinemas = (ArrayList<Cinema>) loadObject("cinemas.bin");
            films = (ArrayList<Film>) loadObject("films.bin");
            clients = (ArrayList<Client>) loadObject("clients.bin");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            clients = new ArrayList<>();
            films = new ArrayList<>();
            cinemas = new ArrayList<>();

            cinemas.add(new Cinema("Default cinema", "Decr",
                    List.of(
                            new Hall("Обычный зал",81, List.of("2D")),
                            new Hall("Зал с диванами", 16, List.of("2D", "3D", "4D")),
                            new Hall("Детский зал", 36, List.of("2D", "3D")))));
            films.add(new Film("test", "20.01.1990", "2D","Comedy", 0));
        }

    }

    private static void Save() throws IOException {
        saveObj(clients,"clients.bin");
        saveObj(films,"films.bin");
        saveObj(cinemas, "cinemas.bin");
    }

    private static void saveObj(Object data, String filename) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(filename));
        stream.writeObject(data);
        stream.close();
    }
    private static Object loadObject(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(filename));
        Object obj = stream.readObject();
        stream.close();
        return obj;
    }

    private static Client register()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ваши имя, номер и почту:");
        var name = scanner.next();
        var number = scanner.next();
        var mail = scanner.next();
        System.out.println("Введите желаемый депозит:");
        var money = scanner.nextInt();
        System.out.println("Введите Ваш возраст:");
        var age = Integer.parseInt(scanner.next());
        System.out.println("Транкзакция успешна");
        var client = new Client(name,number,mail,money,age);
        clients.add(client);
        return client;
    }
    private static Client login()
    {
        System.out.println("Введите ваше имя: ");
        Scanner scanner = new Scanner(System.in);
        var name = scanner.next();
        var client = clients.stream().filter(c -> c.getName().equals(name)).findFirst();
        if(client.isEmpty())
        {
            System.out.println("Вы не зарегистрированны\nПеренаправление на регистрацию...");
            return register();
        }
        return client.get();
    }
    private static void administrating(Administrator adm)
    {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.println("""
                    1.Вывести доходы кинотератра
                    2.Вывести список пользователей
                    3.Создать фильм
                    4.Создать сеанс
                    0.Выйти""");
            var res = scanner.nextInt();
            switch (res) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    for(int i = 0;i<cinemas.size();i++)
                        System.out.println(i+" "+ cinemas.get(i));
                    var cinema = cinemas.get(scanner.nextInt());
                    adm.printCinemaIncomes(cinema);
                }
                case 2 -> adm.printClientTypes(clients);
                case 3 -> films.add(adm.createFilm());
                case 4 -> {
                    for(int i = 0;i<cinemas.size();i++)
                        System.out.println(i+" "+ cinemas.get(i));
                    var cinema = cinemas.get(scanner.nextInt());
                    try {
                        adm.createSession(cinema,films);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

    }
    private static void clienting(Client c) throws ParseException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.println("1.Купить билет\n2.Организовать мероприятие\n3.Выбрать сеанс по бюджету\n0.Выйти");
            var res = scanner.next();
            if(res.equals("0")) return;
            if(res.equals("2")){
                System.out.println("Выберите зал:");
                for(int i = 0;i<cinemas.size();i++)
                    System.out.println(i+" "+ cinemas.get(i));
                var cinema = cinemas.get(scanner.nextInt());

                System.out.println("Следующие сеансы заняты: ");
                for(var sesh:cinema.getSessions()){
                    System.out.println(sesh.getDate());
                }
                System.out.println("Выберите другое время в формате dd.mm hh:mm ");
                boolean timeIsBooked = false;
                Date date = new SimpleDateFormat("dd.MM HH:mm").parse(scanner.next() +" "+ scanner.next());
                for (var sesh:cinema.getSessions()){
                    timeIsBooked = sesh.getDate() == date;
                    break;
                }
                if (timeIsBooked){
                    System.out.println("Данное время занято");
                    continue;
                }
                System.out.println("Выберите тип мроприятия:\n1.клоунада\n2.фуршет\n3.Другое\n0.Выход");
                var seshType = scanner.next();
                if (seshType.equals("0")) return;
                if (seshType.equals("1")){
                    client.createClownSesh(client.getMoney(), cinema, date);
                    client.Withdraw(cinema.getSessions().get(cinema.getSessions().size() - 1).getCost());
                }
                if (seshType.equals("2")){
                    client.createEatsSesh(client.getMoney(), cinema, date);
                    client.Withdraw(cinema.getSessions().get(cinema.getSessions().size() - 1).getCost());
                }




            }
            if(res.equals("1"))
            {
                for(int i = 0;i<cinemas.size();i++)
                    System.out.println(i+" "+ cinemas.get(i));
                var cinema = cinemas.get(scanner.nextInt());
                if(cinema.getSessions().size() == 0)
                {
                    System.out.println("Нет доступных сеансов");
                    continue;
                }
                int filmSeshCounter = 0;
                for(int i = 0;i<cinema.getSessions().size();i++)
                    if (cinema.getSessions().get(i) instanceof FilmSession) {
                        filmSeshCounter += 1;

                        System.out.println(i + " " + cinema.getSessions().get(i));
//                        if (cinema.getSessions().get(i).getCost() > client.getMoney())
//                            System.out.println(" Не хватит денег ");
//                        else
//                            System.out.println();
                    }
                if (filmSeshCounter == 0){
                    System.out.println("Нет доступных сеансов");
                    continue;
                }
                var session = cinema.getSessions().get(scanner.nextInt());
                    if (cinema.getAge((FilmSession) session) > c.getAge()){
                        System.out.println("Вы слишком молоды для просмотра данного фильма");
                        continue;
                    }
                session.getHall().outputScheme();
                System.out.println("Цена: "+cinema.getPrice((FilmSession) session,c));
                System.out.println("Выберите ряд и место:");
                int y = scanner.nextInt();
                int x = scanner.nextInt();
                try {
                    cinema.buyTicket(c, (FilmSession) session,x,y);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                System.out.println("Билет успешно приобретен");
            }

            if(res.equals("3"))
            {
                for(int i = 0; i < cinemas.size(); i++)
                    System.out.println(i+" "+ cinemas.get(i));
                var cinema = cinemas.get(scanner.nextInt());
                if(cinema.getSessions().size() == 0)
                {
                    System.out.println("Нет доступных сеансов");
                    continue;
                }
                int filmSeshCounter = 0;
                int filmAvailableSeshCounter = 0;
                for(int i = 0;i<cinema.getSessions().size();i++)
                    if (cinema.getSessions().get(i) instanceof FilmSession) {
                        filmSeshCounter += 1;

                        if (cinema.getSessions().get(i).getCost() <= client.getMoney()) {
                            filmAvailableSeshCounter += 1;
                            System.out.println(i + " " + cinema.getSessions().get(i));
                        }
                    }
                if (filmSeshCounter == 0 || filmAvailableSeshCounter == 0){
                    System.out.println("Нет доступных сеансов");
                    continue;
                }
                var session = cinema.getSessions().get(scanner.nextInt());
                if (cinema.getAge((FilmSession) session) > c.getAge()){
                    System.out.println("Вы слишком молоды для просмотра данного фильма");
                    continue;
                }
                session.getHall().outputScheme();
                System.out.println("Цена: "+cinema.getPrice((FilmSession) session,c));
                System.out.println("Выберите ряд и место:");
                int y = scanner.nextInt();
                int x = scanner.nextInt();
                try {
                    cinema.buyTicket(c, (FilmSession) session,x,y);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                System.out.println("Билет успешно приобретен");
            }

        }

    }
    public static void main(String[] args) throws InterruptedException, ParseException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        Load();
        while (true)
        {
            System.out.println("1.Ввойти\n2.Зарегистрироваться\n0.Выйти");
            var res = scanner.next();
            if(res.equals("0")) break;
            if(res.equals("Admin")) {
                System.out.println("ВВЕДИТЕ ПАРОЛЬ:");
                var pass = scanner.next();
                if(!administrator.valid(pass))
                {
                    System.out.println("НЕВЕРНЫЙ ПАРОЛЬ");
                    continue;
                }
                System.out.println("ПАРОЛЬ ПРИНЯТ");
                System.out.println();
                System.out.println("Добро пожаловать Admin");
                administrating(administrator);
                continue;
            }

            if(res.equals("2"))
            {
                client = register();
            }
            else if(res.equals("1"))
            {
                client = login();
            }
            else break;
            clienting(client);
        }
        try {
            Save();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}