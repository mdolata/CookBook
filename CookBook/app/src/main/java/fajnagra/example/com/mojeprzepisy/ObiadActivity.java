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
import java.util.ArrayList;
import model.Potrawa;
import model.Przepis;
import model.Skladnik;
import sql.Zarzadca;

/**
 * Created by kgb on 19.05.2015.
 */
public class ObiadActivity extends FragmentActivity {
    Potrawa obiad;
    Zarzadca z;
    ArrayList<Skladnik> skladniki;
    ArrayList<Przepis> przepisy;
    int czas;

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        z = new Zarzadca(this);
        Bundle data = getIntent().getExtras();
        obiad = (Potrawa) data.getParcelable("potrawa");
        przepisy = z.getPrzpis(Integer.parseInt(obiad.getId()));
        skladniki = z.getSkladniki(Integer.parseInt(obiad.getId()));
        System.out.println(skladniki.size()+" "+obiad.getId());
        for(Przepis x:przepisy){
            czas+=x.getCzas();
        }
        System.out.println(przepisy.size()+"   "+obiad.getId());
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
    public static class Skladniki extends  Fragment{
        ListView list;
        WierszAdapter adapter;
        ArrayList<Skladnik> data;
        Button add;
        Zarzadca z;
        @Override
        public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            z = new Zarzadca(getActivity());
            ObiadActivity obiadActivity = (ObiadActivity)getActivity();
            data = obiadActivity.skladniki;
            View view = inflater.inflate(R.layout.skladniki,container,false);
            add = (Button)view.findViewById(R.id.lista_zak);
            add.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    for(Skladnik s:data)
                        z.dodaj_zak(s);
                    Toast.makeText(getActivity(), "Dodalem do Listy Zakupow", Toast.LENGTH_LONG).show();
                }
            });
            if(data.size()>0) {
                adapter = new WierszAdapter(view.getContext(), R.layout.wiersz, data);
                list = (ListView) view.findViewById(R.id.Lista);
                list.setAdapter(adapter);
            }
            return view;
        }

    }
    public static class ObiadSummary extends Fragment{
        Potrawa obiad;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            TextView nazwa,czas,osoby,poziom_txt;
            ImageView poziom_img;
            super.onCreate(savedInstanceState);
            ObiadActivity obiadActivity = (ObiadActivity)getActivity();
            obiad = obiadActivity.obiad;
            View rootView = inflater.inflate(R.layout.summary, container, false);
            nazwa =(TextView)rootView.findViewById(R.id.nazwa);
            czas =(TextView)rootView.findViewById(R.id.czas);
            osoby =(TextView)rootView.findViewById(R.id.osoby);
            poziom_txt =(TextView)rootView.findViewById(R.id.poziom_txt);
            poziom_img =(ImageView)rootView.findViewById(R.id.poziom_img);

            nazwa.setText(obiad.getNazwa());
            czas.setText(((ObiadActivity) getActivity()).czas+" min");
            osoby.setText(obiad.getIlosc()+" osoby");
            if(Integer.parseInt(obiad.getPoziom())==0){
                poziom_txt.setText("Latwy");
                poziom_img.setImageDrawable(getResources().getDrawable(R.drawable.low));

            }
            else if(Integer.parseInt(obiad.getPoziom())==1){
                poziom_txt.setText("Sredni");
                poziom_img.setImageDrawable(getResources().getDrawable(R.drawable.mid));
            }
            else{
                poziom_txt.setText("Trudny");
                poziom_img.setImageDrawable(getResources().getDrawable(R.drawable.hard));
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
            else{
                return new PrzepisFragment();
            }
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
