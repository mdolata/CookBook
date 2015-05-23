package fajnagra.example.com.mojeprzepisy;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] list_titles;
    private ListView list;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();
        list_titles = getResources().getStringArray(R.array.obiad_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        list = (ListView) findViewById(R.id.left_drawer);
        list.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, list_titles));
        list.setOnItemClickListener(new DrawerItemClickListener());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(list);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.action_websearch:
                // create intent to perform web search for this planet
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
                // catch event that there's no activity to handle intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new Obiady();
        Bundle args = new Bundle();
        args.putInt("ID", position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        list.setItemChecked(position, true);
        setTitle(list_titles[position]);
        mDrawerLayout.closeDrawer(list);
    }


    public static class Obiady extends Fragment{
        private GridAdapter adapter;
        private GridViewWithHeaderAndFooter grid;
        private Obiad[] data = new Obiad[50];
        private Skladnik[] sk = new Skladnik[5];
        private Przepis[] kroki = new Przepis[5];
        public Obiady(){}


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            kroki[0]=new Przepis(1,1,"Poledwiczke z sarny zamarynuj w przyprawach oraz w wodce. Przygotuj farsz francuski: zmielone mieso z udka perliczki wymieszaj ze smietanka i jajkiem, dopraw sola, pieprzem i tymiankiem.");
            for(int i=1;i<kroki.length;i++){
                kroki[i]=new Przepis(i,i+2,"Gotuj pan jak uwazasz "+(i+1));
            }
            sk[0] = new Skladnik(300,1,"Poledwiczka z sarny","g");
            sk[1] = new Skladnik(250,2,"Mieso z perliczki","g");
            sk[2] = new Skladnik(100,3,"Wodka zubrowka","ml");
            sk[3] = new Skladnik(7,4,"Kapusta Wloska","lisci");
            sk[4] = new Skladnik(80,5,"Wedzona szynka","g");
            int x = getArguments().getInt("ID");
            for(int i=0;i<data.length;i++){
                data[i]=new Obiad(R.drawable.sarny,sk,1,4,kroki,"Balotyna z perliczki "+x);
            }
            View rootView = inflater.inflate(R.layout.fragment_obiady, container, false);

            String planet = getResources().getStringArray(R.array.obiad_array)[x];
            adapter = new GridAdapter(rootView.getContext(),R.layout.row_grid, data);
            grid = (GridViewWithHeaderAndFooter)rootView.findViewById(R.id.gridView1);
            LayoutInflater layoutInflater = LayoutInflater.from(rootView.getContext());
            View headerView = layoutInflater.inflate(R.layout.header_grid, null);
            TextView text = (TextView) headerView.findViewById(R.id.item_text);
            text.setText("Trololo");
            headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), ObiadActivity.class);
                    startActivity(intent);
                }
            });
            grid.addHeaderView(headerView);

            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), ObiadActivity.class);
                    startActivity(intent);
                }
            });

            grid.setAdapter(adapter);

            getActivity().setTitle(planet);
            return rootView;
        }
    }
}
