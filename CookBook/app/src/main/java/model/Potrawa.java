package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kgb_putin on 2015-05-26.
 */
public class Potrawa implements Parcelable{
    private String img,nazwa,kategoria,id,ilosc,poziom;

    public String getId(){return id;}

    public void setId(String id) {
        this.id = id;
    }
    public Potrawa(){}

    public Potrawa (Parcel in){
        String[] data = new String[6];
        in.readStringArray(data);
        id = data[0];
        nazwa = data[1];
        kategoria = data[2];
        img = data[3];
        ilosc = data[4];
        poziom = data[5];
    }

    public String getKategoria(){return kategoria;}
    public void setKategoria(String kategoria){this.kategoria=kategoria;}

    public String getImg(){return img;}
    public void setImg(String img) {this.img = img;}

    public String getNazwa(){return nazwa;}
    public void setNazwa(String nazwa) {this.nazwa = nazwa;}

    public String getIlosc(){return ilosc;}
    public void setIlosc(String ilosc) {this.ilosc = ilosc;}

    public String getPoziom(){return poziom;}
    public void setPoziom(String poziom) {this.poziom = poziom;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {id,nazwa,kategoria,img,ilosc,poziom});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Potrawa createFromParcel(Parcel in) {
            return new Potrawa(in);
        }
        public Potrawa[] newArray(int size) {
            return new Potrawa[size];
        }
    };
}
