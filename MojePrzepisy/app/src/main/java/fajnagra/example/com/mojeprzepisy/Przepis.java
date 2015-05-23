package fajnagra.example.com.mojeprzepisy;

/**
 * Created by kgb on 21.05.2015.
 */
public class Przepis {
    private final int id,czas;
    final String text;

    public Przepis(int id,int czas,String text){
        this.czas=czas;
        this.id=id;
        this.text=text;
    }
    public int getId(){return id;}
    public int getCzas(){return czas;}
}
