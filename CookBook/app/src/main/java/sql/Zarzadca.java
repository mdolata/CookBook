package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import model.Potrawa;
import model.Przepis;
import model.Skladnik;

/**
 * Created by kgb on 26.05.2015.
 */
public class Zarzadca extends SQLiteOpenHelper {

    public Zarzadca(Context context){
        super(context,"cookbook2.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table obiady(" +
                        "id integer primary key autoincrement," +
                        "nazwa text," +
                        "kategoria integer," +
                        "img text," +
                        "ilosc integer," +
                        "poziom integer);" +
                        "");
        db.execSQL(
                "create table przepisy(" +
                        "id integer primary key autoincrement," +
                        "id_potrawa integer ," +
                        "czas integer ," +
                        "tekst text);" +
                        "");
        db.execSQL(
                "create table skladniki(" +
                        "id integer primary key autoincrement," +
                        "id_potrawa integer ," +
                        "nazwa text ," +
                        "ilosc integer ," +
                        "jednostka text);" +
                        "");
        db.execSQL(
                "create table zakupy(" +
                        "id integer primary key autoincrement," +
                        "nazwa text ," +
                        "ilosc integer ," +
                        "jednostka text);" +
                        "");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {}

    public long dodaj(Potrawa kontakt){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("kategoria",kontakt.getKategoria());
        wartosci.put("poziom",kontakt.getPoziom());
        wartosci.put("ilosc", kontakt.getIlosc());
        wartosci.put("img",kontakt.getImg());
        wartosci.put("nazwa", kontakt.getNazwa());
        return db.insertOrThrow("obiady",null, wartosci);
    }
    public Potrawa get(int id){
        Potrawa obiad = new Potrawa();
        SQLiteDatabase db = getReadableDatabase();
        String[] kolumny={"nr","nazwa","kategoria","img","ilosc","poziom"};
        String args[]={id+""};
        Cursor kursor = db.query("obiady",kolumny,"id=?",args,null,null,null,null);
        if(kursor!=null){
            kursor.moveToFirst();
            obiad.setId(kursor.getString(0));
            obiad.setNazwa(kursor.getString(1));
            obiad.setKategoria(kursor.getString(2));
            obiad.setImg(kursor.getString(3));
            obiad.setIlosc(kursor.getString(4));
            obiad.setPoziom(kursor.getString(5));
        }
        return obiad;
    }
    public ArrayList<Potrawa> getPotrawa(String kategoria){
        ArrayList<Potrawa> potrawy = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String args[]={kategoria};
        Cursor kursor =db.rawQuery("select id,nazwa,kategoria,img,ilosc,poziom from obiady where kategoria=?", args);
        while (kursor.moveToNext()) {
            Potrawa obiad = new Potrawa();
            obiad.setId(kursor.getString(0));
            obiad.setNazwa(kursor.getString(1));
            obiad.setKategoria(kursor.getString(2));
            obiad.setImg(kursor.getString(3));
            obiad.setIlosc(kursor.getString(4));
            obiad.setPoziom(kursor.getString(5));
            potrawy.add(obiad);
        }
        return potrawy;
    }

    public ArrayList<Przepis> getPrzpis(int id_potrawa){
        ArrayList<Przepis> przepisy = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String args[]={Integer.toString(id_potrawa)};
        Cursor kursor = db.rawQuery("select id,id_potrawa,tekst,czas from przepisy where id_potrawa=?",args);
        while (kursor.moveToNext()){
            Przepis przepis = new Przepis();
            przepis.setId(kursor.getInt(0));
            przepis.setId_potrawa(kursor.getInt(1));
            przepis.setText(kursor.getString(2));
            przepis.setCzas(kursor.getInt(3));
            przepisy.add(przepis);
        }
        return przepisy;
    }
    public void dodaj(Przepis przepis){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("id_potrawa",przepis.getId_potrawa());
        wartosci.put("czas",przepis.getCzas());
        wartosci.put("tekst", przepis.getText());
        db.insertOrThrow("przepisy",null, wartosci);
    }

    public ArrayList<Skladnik> getSkladniki(int id_potrawa){
        ArrayList<Skladnik> skladniki = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String args[]={Integer.toString(id_potrawa)};
        Cursor kursor = db.rawQuery("select id,id_potrawa,nazwa,ilosc,jednostka from skladniki where id_potrawa=?",args);
        while (kursor.moveToNext()){
            Skladnik skladnik = new Skladnik();
            skladnik.setId(kursor.getInt(0));
            skladnik.setId_potrawy(kursor.getInt(1));
            skladnik.setNazwa(kursor.getString(2));
            skladnik.setIlosc(kursor.getInt(3));
            skladnik.setJednoska(kursor.getString(4));
            skladniki.add(skladnik);
        }
        return skladniki;
    }

    public void dodaj(Skladnik skladnik){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("id_potrawa",skladnik.getId_potrawy());
        wartosci.put("nazwa",skladnik.getNazwa());
        wartosci.put("ilosc",skladnik.getIlosc());
        wartosci.put("jednostka",skladnik.getJednoska());
        db.insertOrThrow("skladniki",null, wartosci);
    }
    public void dodaj_zak(Skladnik skladnik){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("nazwa",skladnik.getNazwa());
        wartosci.put("ilosc",skladnik.getIlosc());
        wartosci.put("jednostka",skladnik.getJednoska());
        db.insertOrThrow("zakupy",null, wartosci);
    }
    public void usun_zak(Skladnik skladnik){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("zakupy","id ="+skladnik.getId(),null);
    }

    public ArrayList<Skladnik> getZakupy(){
        ArrayList<Skladnik> skladniki = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.rawQuery("select id,nazwa,ilosc,jednostka from zakupy",null);
        while (kursor.moveToNext()){
            Skladnik skladnik = new Skladnik();
            skladnik.setId(kursor.getInt(0));
            skladnik.setNazwa(kursor.getString(1));
            skladnik.setIlosc(kursor.getInt(2));
            skladnik.setJednoska(kursor.getString(3));
            skladniki.add(skladnik);
        }
        return skladniki;
    }
}
