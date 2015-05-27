package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kgb on 20.05.2015.
 */
public class Skladnik  implements Parcelable{
    private int ilosc,id,id_potrawy;
    private String nazwa,jednoska;

    public Skladnik(){}
    public Skladnik (Parcel in){
        String[] data = new String[5];
        in.readStringArray(data);
        id = Integer.parseInt(data[0]);
        id_potrawy = Integer.parseInt(data[1]);
        nazwa = data[2];
        ilosc = Integer.parseInt(data[3]);
        jednoska = data[4];
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {id+"",id_potrawy+"",nazwa,ilosc+"",jednoska});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Skladnik createFromParcel(Parcel in) {
            return new Skladnik(in);
        }
        public Skladnik[] newArray(int size) {return new Skladnik[size];}

    };
}