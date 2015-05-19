package fajnagra.example.com.mojeprzepisy;

import android.app.ActionBar;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kgb on 19.05.2015.
 */
public class ObiadActivity extends FragmentActivity implements ActionBar.TabListener{
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
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
    public static class ObiadSumarry extends Fragment{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
            return new ObiadSumarry();
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
