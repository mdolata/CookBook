package fajnagra.example.com.mojeprzepisy;

/**
 * Created by kgb_putin on 2015-05-21.
 */
public class Obiad {
    private Skladnik[] skladniki;
    private int czas,poziom,ilosc,kroki;
    private String nazwa;
    private Przepis[] przepis;

    public Obiad(Skladnik[] skladniki,int poziom,int ilosc,Przepis[] przepis,String nazwa){
        this.poziom=poziom;
        this.ilosc=ilosc;
        this.przepis=przepis;
        this.skladniki=skladniki;
        kroki = przepis.length;
        czas = getCzas();
        this.nazwa=nazwa;
    }
    public Przepis getPrzepis(int i){return przepis[i];}
    public String getNazwa(){return nazwa;}
    public int getPoziom(){return poziom;}
    public int getIlosc(){return ilosc;}
    public int getKroki(){return kroki;}

    public int getCzas(){
        int result=0;
        for(Przepis k:przepis){
            result+=k.getCzas();
        }
        return result;
    }

}
