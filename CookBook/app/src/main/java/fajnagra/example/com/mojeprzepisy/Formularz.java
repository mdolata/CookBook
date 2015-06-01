package fajnagra.example.com.mojeprzepisy;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

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
        static ArrayList<Skladnik> data_s = new ArrayList<>();
        private boolean p_nazwa,p_ilosc,p_jed;

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
                data_s = savedInstanceState.getParcelableArrayList("LIST");
                adapter = new WierszAdapter(rootView.getContext(), R.layout.wiersz, data_s);
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
                    Toast.makeText(getActivity(), "Usuniete", Toast.LENGTH_LONG).show();
                    return false;
                }
            });
            nazwa = (EditText)rootView.findViewById(R.id.nazwa);
            nazwa.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if(!p_nazwa)
                        nazwa.setText("");
                    p_nazwa=true;
                    return false;
                }
            });
            ilosc = (EditText)rootView.findViewById(R.id.ilosc);
            ilosc.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if(!p_ilosc)
                        ilosc.setText("");
                    p_ilosc=true;
                    return false;
                }
            });
            jednostka = (EditText)rootView.findViewById(R.id.jednostka);
            jednostka.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if(!p_jed)
                        jednostka.setText("");
                    p_jed=true;
                    return false;
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
                    data_s.add(skladnik);
                    adapter = new WierszAdapter(rootView.getContext(), R.layout.wiersz, data_s);
                    list = (ListView) rootView.findViewById(R.id.Lista);
                    list.setAdapter(adapter);
                    nazwa.setText("");
                    jednostka.setText("");
                    ilosc.setText("");
                }
            });
            return rootView;
        }
        @Override
        public void onSaveInstanceState(Bundle savedInstanceState) {
            savedInstanceState.putParcelableArrayList("LIST",data_s);
            super.onSaveInstanceState(savedInstanceState);
        }
    }

    public static class NowyPrzepis extends Fragment{
        private PrzepisAdapter adapter;
        private boolean p_tekst,p_czas;
        private EditText tekst,czas;
        static ArrayList<Przepis> data_p = new ArrayList<>();
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
                    Toast.makeText(getActivity(), "Usuniete", Toast.LENGTH_LONG).show();
                    return false;
                }
            });
            tekst = (EditText)rootView.findViewById(R.id.tekst);
            tekst.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if(!p_tekst)
                        tekst.setText("");
                    p_tekst=true;
                    return false;
                }
            });
            czas = (EditText)rootView.findViewById(R.id.czas);
            czas.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if(!p_czas)
                        czas.setText("");
                    p_czas=true;
                    return false;
                }
            });

            add = (Button)rootView.findViewById(R.id.add);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Przepis przepis = new Przepis();
                    String temp = tekst.getText().toString();
                    if(temp.length()>=50)
                        temp=temp.substring(0,50)+"...";
                    przepis.setText(temp);
                    try {
                        przepis.setCzas(Integer.parseInt(czas.getText().toString()));
                    }catch (NumberFormatException e){
                        Toast.makeText(getActivity(), "Bledne dane", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    data_p.add(przepis);
                    adapter = new PrzepisAdapter(rootView.getContext(),R.layout.przepis_row,data_p);
                    list.setAdapter(adapter);
                    tekst.setText("");
                    czas.setText("");
                }
            });

            return rootView;
        }
    }
    public static class NowyOpis extends Fragment{
        private static final int SELECT_PICTURE = 1;
        private View rootView;
        private String selectedImagePath;
        private Spinner s1,s2;
        private ImageView img;
        private EditText editText,ilosc;
        private Button b,add;
        private boolean p_ile,p_nazwa;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final Zarzadca z = new Zarzadca(getActivity());
            rootView = inflater.inflate(R.layout.potrawa_nowy, container, false);
            s1 = (Spinner) rootView.findViewById(R.id.spinner);
            s2 = (Spinner) rootView.findViewById(R.id.spinner2);
            List<String> list = new ArrayList<String>();
            list.add("latwe");
            list.add("srednie");
            list.add("trudne");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s2.setAdapter(dataAdapter);
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),R.array.kategorie,android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editText = (EditText)rootView.findViewById(R.id.nazwa);
            s1.setAdapter(adapter1);
            ilosc = (EditText)rootView.findViewById(R.id.ilosc);
            img = (ImageView)rootView.findViewById(R.id.imageView4);
            editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if(!p_nazwa)
                        editText.setText("");
                    p_nazwa=true;
                    return false;
                }
            });
            add =(Button)rootView.findViewById(R.id.button4);
            ilosc.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if(!p_ile)
                        ilosc.setText("");
                    p_ile=true;
                    return false;
                }
            });
            /*
                NIE SKONCZONE!!!!!!!!!!
             */
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Potrawa potrawa = new Potrawa();
                    potrawa.setNazwa(editText.getText().toString());
                    potrawa.setIlosc(ilosc.getText().toString());
                    potrawa.setKategoria(String.valueOf(s1.getSelectedItem()));
                    if(String.valueOf(s2.getSelectedItem()).equals("latwe")){
                        potrawa.setPoziom("0");
                    }
                    else if(String.valueOf(s2.getSelectedItem()).equals("srednie")){
                        potrawa.setPoziom("1");
                    }
                    else if(String.valueOf(s2.getSelectedItem()).equals("trudne")){
                        potrawa.setPoziom("2");
                    }
                    long id = z.dodaj(potrawa);
                    for(Przepis p:NowyPrzepis.data_p){
                        p.setId_potrawa((int)id);
                        z.dodaj(p);
                    }
                    for(Skladnik p:NowySkladnik.data_s){
                        p.setId_potrawy((int)id);
                        z.dodaj(p);
                    }
                    NowySkladnik.data_s = new ArrayList<Skladnik>();
                    NowyPrzepis.data_p = new ArrayList<Przepis>();
                    Toast.makeText(getActivity(), "Dodalem", Toast.LENGTH_LONG).show();
                }
            });
            b =(Button)rootView.findViewById(R.id.button2);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
                }
            });
            return rootView;
        }
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode == RESULT_OK) {
                if (requestCode == SELECT_PICTURE) {
                        Uri selectedImageUri = data.getData();
                        selectedImagePath = getPath(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                        img.setImageBitmap(bitmap);
                }
            }
        }

        /**
         * helper to retrieve the path of an image URI
         */
        public String getPath(Uri uri) {
            if( uri == null ) {
                return null;
            }
            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
            if( cursor != null ){
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                System.out.println(column_index);
                return cursor.getString(column_index);
            }
            System.out.println(uri.getPath());
            return uri.getPath();
        }
    }
    public class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int i) {
            if(i==0)
                return new NowyOpis();
            else if(i==1)
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
