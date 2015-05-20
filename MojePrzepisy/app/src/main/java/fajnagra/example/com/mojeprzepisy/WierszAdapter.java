package fajnagra.example.com.mojeprzepisy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by kgb on 20.05.2015.
 */
public class WierszAdapter extends ArrayAdapter<Skladnik> {
    Context context;
    int id;
    Skladnik[] data;

    public WierszAdapter(Context context, int id,Skladnik[] data){
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
            view.setTag(holder);
        }
        else{
            holder = (RowBeanHolder)view.getTag();
        }
        Skladnik object = data[position];
        holder.nazwa.setText(object.getNazwa());
        holder.ilosc.setText(Integer.toHexString(object.getIlosc()));
        return  view;
    }

    private static class RowBeanHolder{
        private TextView nazwa;
        private TextView ilosc;
    }

}
