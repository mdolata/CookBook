package fajnagra.example.com.mojeprzepisy;

import java.io.Serializable;

/**
 * Created by kgb on 20.05.2015.
 */
public class Skladnik implements Serializable {
    private final int ilosc,index;
    private final String nazwa,jednoska;

    public Skladnik(int ilosc,int index,String nazwa,String jednoska){
        this.ilosc=ilosc;
        this.index=index;
        this.nazwa=nazwa;
        this.jednoska=jednoska;
    }
    public String getNazwa(){return  nazwa;}
    public int getIndex(){return index;}
    public int getIlosc(){return  ilosc;}
    public String getJednoska(){return jednoska;}

}