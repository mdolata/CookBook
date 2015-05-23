package fajnagra.example.com.mojeprzepisy;

/**
 * Created by kgb on 21.05.2015.
 */
public class KrokPrzepis {
    private final int id,czas,img;
    final String text;

    KrokPrzepis(int id,int czas,int img,String text){
        this.czas=czas;
        this.id=id;
        this.img=img;
        this.text=text;
    }
    public int getId(){return id;}
    public int getCzas(){return czas;}
    public int getImg(){return img;}
}
