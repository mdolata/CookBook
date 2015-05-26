package model;

/**
 * Created by kgb on 21.05.2015.
 */
public class Przepis {
    private int id,id_potrawa,czas;
    private String text;

    public int getId(){return id;}
    public void setId(int id) { this.id = id;}

    public int getId_potrawa() {return id_potrawa;}
    public void setId_potrawa(int id_potrawa) {this.id_potrawa = id_potrawa;}

    public int getCzas(){return czas;}
    public void setCzas(int czas) {this.czas = czas;}

    public String getText(){return text;}
    public void setText(String text) {this.text = text;}
}
