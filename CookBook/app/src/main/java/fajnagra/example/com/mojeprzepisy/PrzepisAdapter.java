package fajnagra.example.com.mojeprzepisy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.Przepis;

/**
 * Created by kgb on 27.05.2015.
 */
public class PrzepisAdapter extends ArrayAdapter<Przepis> {
    private Context context;
    private int id;
    private ArrayList<Przepis> data;

    public PrzepisAdapter(Context context,int id,ArrayList data){
        super(context,id,data);
        this.context=context;
        this.id=id;
        this.data=data;
    }
    @Override
    public View getView(int position,View view,ViewGroup parent){
        RecordHolder holder = null;
        if(view==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view = inflater.inflate(id,parent, false);
            holder = new RecordHolder();
            holder.przepis = (TextView)view.findViewById(R.id.przepis);
            holder.czas =(TextView)view.findViewById(R.id.czas);
            view.setTag(holder);
        }
        else{
            holder = (RecordHolder)view.getTag();
        }
        Przepis p = data.get(position);
        holder.przepis.setText(p.getText());
        holder.czas.setText(p.getCzas()+" min");
        return view;
    }

    private static class RecordHolder{
        private TextView przepis;
        private TextView czas;
    }
}

