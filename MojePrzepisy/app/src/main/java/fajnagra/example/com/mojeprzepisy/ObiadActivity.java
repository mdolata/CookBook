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
import android.widget.ListView;

/**
 * Created by kgb on 19.05.2015.
 */
public class ObiadActivity extends FragmentActivity {
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.obiad_main);
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        // Center the tabs in the layout
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(mViewPager);
        // Customize tab color
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
        Skladnik[] data = new Skladnik[2];
        @Override
        public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            View view = inflater.inflate(R.layout.skladniki,container,false);
            data[0] = new Skladnik(100,1,"Papier Toaletowy");
            data[1] = new Skladnik(100,1,"Proszek");
            adapter = new WierszAdapter(view.getContext(), R.layout.wiersz, data);
            list = (ListView)view.findViewById(R.id.Lista);
            list.setAdapter(adapter);
            return view;
        }

    }
    public static class ObiadSummary extends Fragment{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            View rootView = inflater.inflate(R.layout.summary, container, false);
            return rootView;
        }
    }

    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if(i==0)
                return new ObiadSummary();
            else
                return new Skladniki();
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
                    return "Steps";
                default:
                    return "Error";
            }
        }

    }
}
