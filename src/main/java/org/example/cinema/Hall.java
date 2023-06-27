package org.example.cinema;


import java.io.Serializable;
import java.util.*;

public class Hall implements Cloneable, Serializable {

    private Set<String> supportedTypes;
    private String name;
    private boolean[][] places;
    public Hall(String name, int capacity, Collection<String> supportedTypes)
    {
        int x = (int) Math.sqrt(capacity);
        places = new boolean[x][x];
        this.supportedTypes = new HashSet<>(supportedTypes);
        this.name = name;
    }
    public boolean canShow(Film f)
    {
        return supportedTypes.contains(f.getType());
    }

    public void allocatePlace(int x, int y)
    {
        if(x <0 || y < 0 || y > places.length || x > places[0].length )
            throw new IndexOutOfBoundsException("Некорректное место в зале");
        if(places[y][x]) throw new IllegalCallerException("Место занято");

        places[y][x] = true;

    }

    public void outputScheme(){
        for(int i = 0; i < places.length; i++)
        {
            System.out.print(i+"\t");
            for(int j = 0; j < places[i].length; j++)
            {
                System.out.print(j);
                if(!places[i][j])
                    System.out.print("\uD83D\uDFE7");
                else System.out.print("\uD83D\uDD32");
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    @Override
    public Hall clone() {
        try {
            return (Hall) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return name + "\nПоддерживаемые типы: "+ supportedTypes;
    }
}