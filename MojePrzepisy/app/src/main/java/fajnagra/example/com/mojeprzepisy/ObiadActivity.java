package fajnagra.example.com.mojeprzepisy;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by kgb on 19.05.2015.
 */
public class ObiadActivity extends FragmentActivity {
    Obiad obiad;
    Skladnik[] data = new Skladnik[5];
    Przepis[] kroki = new Przepis[5];

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;
    public void onCreate(Bundle savedInstanceState) {
        kroki[0]=new Przepis(1,1,"Poledwiczke z sarny zamarynuj w przyprawach oraz w wodce. Przygotuj farsz francuski: zmielone mieso z udka perliczki wymieszaj ze smietanka i jajkiem, dopraw sola, pieprzem i tymiankiem.");
        for(int i=1;i<kroki.length;i++){
            kroki[i]=new Przepis(i,i+2,"Gotuj pan jak uwazasz "+(i+1));
        }
        data[0] = new Skladnik(300,1,"Poledwiczka z sarny","g");
        data[1] = new Skladnik(250,2,"Mieso z perliczki","g");
        data[2] = new Skladnik(100,3,"Wodka zubrowka","ml");
        data[3] = new Skladnik(7,4,"Kapusta Wloska","lisci");
        data[4] = new Skladnik(80,5,"Wedzona szynka","g");
        obiad = new Obiad(data,1,4,kroki,"Balotyna z perliczki i sarny na musie z burakow z sosem z szalotek");
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
    public class Skladniki extends  Fragment{
        ListView list;
        WierszAdapter adapter;
        Button add;
        @Override
        public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            View view = inflater.inflate(R.layout.skladniki,container,false);
            adapter = new WierszAdapter(view.getContext(), R.layout.wiersz, data);
            add = (Button)view.findViewById(R.id.lista_zak);
            add.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Not supportet yet", Toast.LENGTH_SHORT).show();
                }
            });
            list = (ListView)view.findViewById(R.id.Lista);
            list.setAdapter(adapter);
            return view;
        }

    }
    public class ObiadSummary extends Fragment{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            TextView nazwa,czas,osoby,poziom_txt;
            ImageView poziom_img;
            super.onCreate(savedInstanceState);
            View rootView = inflater.inflate(R.layout.summary, container, false);
            nazwa =(TextView)rootView.findViewById(R.id.nazwa);
            czas =(TextView)rootView.findViewById(R.id.czas);
            osoby =(TextView)rootView.findViewById(R.id.osoby);
            poziom_txt =(TextView)rootView.findViewById(R.id.poziom_txt);
            poziom_img =(ImageView)rootView.findViewById(R.id.poziom_img);

            nazwa.setText(obiad.getNazwa());
            czas.setText(obiad.getCzas()+" min");
            osoby.setText(obiad.getIlosc()+" osoby");
            if(obiad.getPoziom()==0){
                poziom_txt.setText("Latwy");
                poziom_img.setImageDrawable(getResources().getDrawable(R.drawable.low,null));
            }
            else if(obiad.getPoziom()==1){
                poziom_txt.setText("Sredni");
                poziom_img.setImageDrawable(getResources().getDrawable(R.drawable.mid, null));
            }
            else{
                poziom_txt.setText("Trudny");
                poziom_img.setImageDrawable(getResources().getDrawable(R.drawable.hard, null));
            }
            return rootView;
        }
    }

    public class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if(i==0)
                return new ObiadSummary();
            else if(i==1)
                return new Skladniki();
            else
                return new PrzepisFragment(obiad);
        }

        @Override
        public int getCount() {
          return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:
                    return "Opis";
                case 1:
                    return "Skladniki";
                case 2:
                    return "Kroki";
                default:
                    return "Error";
            }
        }
    }
}
