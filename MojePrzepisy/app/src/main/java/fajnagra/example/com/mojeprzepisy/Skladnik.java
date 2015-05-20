package fajnagra.example.com.mojeprzepisy;

import java.io.Serializable;

/**
 * Created by kgb on 20.05.2015.
 */
public class Skladnik implements Serializable {
    private int ilosc,index;
    private String nazwa;

    public Skladnik(int ilosc,int index,String nazwa){
        this.ilosc=ilosc;
        this.index=index;
        this.nazwa=nazwa;
    }
    public String getNazwa(){return  nazwa;}
    public int getIndex(){return index;}
    public int getIlosc(){return  ilosc;}
}