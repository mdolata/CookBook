package fajnagra.example.com.mojeprzepisy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import model.Skladnik;
import sql.Zarzadca;

/**
 * Created by kgb on 01.06.2015.
 */
public class ListaZakupow extends Activity{
    ListView list;
    WierszAdapter adapter;
    ArrayList<Skladnik> data;
    Zarzadca z;
    public void onCreate(Bundle savedInstanceState) {
        z = new Zarzadca(this);
        super.onCreate(savedInstanceState);
        data = z.getZakupy();
        setContentView(R.layout.zakupy);

        if(data.size()>0) {
            adapter = new WierszAdapter(this, R.layout.wiersz, data);
            list = (ListView) findViewById(R.id.Lista);
            list.setAdapter(adapter);
            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> arg0, View v, int index, long arg3) {
                    z.usun_zak(data.get(index));
                    adapter.remove(adapter.getItem(index));
                    return false;
                }
            });
        }

    }
}
