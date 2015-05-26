package fajnagra.example.com.mojeprzepisy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import model.Potrawa;

/**
 * Created by kgb on 22.05.2015.
 */
public class GridAdapter extends ArrayAdapter<Potrawa> {
    private Context context;
    private int id;
    private ArrayList<Potrawa> data;

    public GridAdapter(Context context,int id,ArrayList data){
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
            holder.nazwa = (TextView)view.findViewById(R.id.item_text);
            holder.imageView =(ImageView)view.findViewById(R.id.item_image);
            view.setTag(holder);
        }
        else{
            holder = (RecordHolder)view.getTag();
        }
        Potrawa obiad = data.get(position);
        holder.nazwa.setText(obiad.getNazwa());
        holder.imageView.getResources().getDrawable(R.drawable.sarny);
        return view;
    }

    private static class RecordHolder{
        private ImageView imageView;
        private TextView nazwa;
    }
}
