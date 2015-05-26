package model;

/**
 * Created by kgb on 20.05.2015.
 */
public class Skladnik  {
    private int ilosc,id,id_potrawy;
    private String nazwa,jednoska;

    public String getNazwa(){return  nazwa;}
    public void setNazwa(String nazwa) {this.nazwa = nazwa;}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getId_potrawy() {return id_potrawy;}
    public void setId_potrawy(int id_potrawy) {this.id_potrawy = id_potrawy;}

    public int getIlosc(){return  ilosc;}
    public void setIlosc(int ilosc) {this.ilosc = ilosc;}

    public String getJednoska(){return jednoska;}
    public void setJednoska(String jednoska) {this.jednoska = jednoska;}
}