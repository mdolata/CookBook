package fajnagra.example.com.mojeprzepisy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.Skladnik;

/**
 * Created by kgb on 20.05.2015.
 */
public class WierszAdapter extends ArrayAdapter<Skladnik> {
    Context context;
    int id;
    ArrayList<Skladnik> data;

    public WierszAdapter(Context context, int id,ArrayList data){
        super(context,id,data);
        this.id=id;
        this.context=context;
        this.data=data;
    }
    @Override
    public View getView(int position, View view,ViewGroup parent){
        RowBeanHolder holder = null;
        if(view == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view = inflater.inflate(id,parent, false);
            holder = new RowBeanHolder();
            holder.nazwa = (TextView)view.findViewById(R.id.nazwa);
            holder.ilosc = (TextView) view.findViewById(R.id.ilosc);
            holder.jednostka = (TextView) view.findViewById(R.id.jednostka);
            view.setTag(holder);
        }
        else{
            holder = (RowBeanHolder)view.getTag();
        }
        Skladnik object = data.get(position);
        holder.nazwa.setText(object.getNazwa());
        holder.ilosc.setText(Integer.toString(object.getIlosc()));
        holder.jednostka.setText(object.getJednoska());
        return  view;
    }

    private static class RowBeanHolder{
        private TextView nazwa;
        private TextView ilosc;
        private TextView jednostka;
    }

}
