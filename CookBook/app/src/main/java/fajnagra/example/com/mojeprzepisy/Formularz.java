package fajnagra.example.com.mojeprzepisy;

import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import model.Potrawa;
import model.Przepis;
import model.Skladnik;
import sql.Zarzadca;

/**
 * Created by kgb on 19.05.2015.
 */
public class Formularz extends FragmentActivity {

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.obiad_main);
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(mViewPager);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });
    }
    public static class NowySkladnik extends Fragment{
        private Button add;
        private View rootView;
        private ListView list;
        private EditText nazwa,ilosc,jednostka;
        private WierszAdapter adapter;
        private ArrayList<Skladnik> data = new ArrayList<>();

        @Override
        public void onResume(){
            super.onResume();
            list.setAdapter(adapter);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            System.out.println("OnCreateView");
            if (savedInstanceState != null) {
                System.out.println("123");
                data = savedInstanceState.getParcelableArrayList("LIST");
                adapter = new WierszAdapter(rootView.getContext(), R.layout.wiersz, data);
                list = (ListView) rootView.findViewById(R.id.Lista);
                list.setAdapter(adapter);
            }
            rootView = inflater.inflate(R.layout.skladnik_nowy, container, false);
            list =(ListView)rootView.findViewById(R.id.Lista);
            list.setLongClickable(true);
            list.setClickable(true);
            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> arg0, View v, int index, long arg3) {
                    adapter.remove(adapter.getItem(index));
                    data.remove(index);
                    Toast.makeText(getActivity(), "Usuniete", Toast.LENGTH_LONG).show();
                    return false;
                }
            });
            nazwa = (EditText)rootView.findViewById(R.id.nazwa);
            nazwa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nazwa.setText("");
                }
            });
            ilosc = (EditText)rootView.findViewById(R.id.ilosc);
            ilosc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ilosc.setText("");
                }
            });
            jednostka = (EditText)rootView.findViewById(R.id.jednostka);
            jednostka.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jednostka.setText("");
                }
            });
            add = (Button)rootView.findViewById(R.id.button);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Skladnik skladnik = new Skladnik();
                    skladnik.setNazwa(nazwa.getText().toString());
                    try {
                        skladnik.setIlosc(Integer.parseInt(ilosc.getText().toString()));
                    }catch (NumberFormatException e){
                        Toast.makeText(getActivity(), "Bledne dane", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    skladnik.setJednoska(jednostka.getText().toString());
                    data.add(skladnik);
                    adapter = new WierszAdapter(rootView.getContext(), R.layout.wiersz, data);
                    list = (ListView) rootView.findViewById(R.id.Lista);
                    list.setAdapter(adapter);
                }
            });
            return rootView;
        }
        @Override
        public void onSaveInstanceState(Bundle savedInstanceState) {
            savedInstanceState.putParcelableArrayList("LIST",data);
            super.onSaveInstanceState(savedInstanceState);
        }
    }

    public static class NowyPrzepis extends Fragment{
        private PrzepisAdapter adapter;
        private EditText tekst,czas;
        private ArrayList<Przepis> data = new ArrayList<>();
        private ListView list;
        private View rootView;
        Button add;
        @Override
        public void onResume(){
            super.onResume();
            list.setAdapter(adapter);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            rootView = inflater.inflate(R.layout.przepis_nowy, container, false);
            list =(ListView)rootView.findViewById(R.id.Lista);
            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> arg0, View v, int index, long arg3) {
                    adapter.remove(adapter.getItem(index));
                    data.remove(index);
                    Toast.makeText(getActivity(), "Usuniete", Toast.LENGTH_LONG).show();
                    return false;
                }
            });
            tekst = (EditText)rootView.findViewById(R.id.tekst);
            tekst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tekst.setText("");
                }
            });
            czas = (EditText)rootView.findViewById(R.id.czas);
            czas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    czas.setText("");
                }
            });
            add = (Button)rootView.findViewById(R.id.add);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Przepis przepis = new Przepis();
                    przepis.setText(tekst.getText().toString());
                    try {
                        przepis.setCzas(Integer.parseInt(czas.getText().toString()));
                    }catch (NumberFormatException e){
                        Toast.makeText(getActivity(), "Bledne dane", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    data.add(przepis);
                    adapter = new PrzepisAdapter(rootView.getContext(),R.layout.przepis_row,data);
                    list.setAdapter(adapter);
                    tekst.setText("");
                    czas.setText("");
                }
            });

            return rootView;
        }
    }
    public class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int i) {
            if(i!=2)
                return new NowySkladnik();
            else
                return new NowyPrzepis();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:
                    return "1";
                case 1:
                    return "2";
                case 2:
                    return "3";
                default:
                    return "Error";
            }
        }
    }
}
