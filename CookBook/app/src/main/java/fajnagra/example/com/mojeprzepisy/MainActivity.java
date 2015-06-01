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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import model.Potrawa;
import sql.Zarzadca;

/**
 * Created by kgb on 19.05.2015.
 */
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
        if(getResources().getStringArray(R.array.obiad_array)[position].equals("Nowy")){
            Intent intent = new Intent(getApplicationContext(), Formularz.class);
            startActivity(intent);
        }
        else if(getResources().getStringArray(R.array.obiad_array)[position].equals("Lista Zakupow")){
            Intent intent = new Intent(getApplicationContext(), ListaZakupow.class);
            startActivity(intent);
        }
        else {
            Fragment fragment = new Obiady();
            Bundle args = new Bundle();
            args.putInt("ID", position);
            fragment.setArguments(args);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            list.setItemChecked(position, true);
            setTitle(list_titles[position]);
            mDrawerLayout.closeDrawer(list);
        }
    }

    public static class Obiady extends Fragment{
        private GridAdapter adapter;
        private GridViewWithHeaderAndFooter grid;
        private GridView gw;
        private ArrayList<Potrawa> data;
        private Zarzadca z;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            int x = getArguments().getInt("ID");
            z = new Zarzadca(getActivity());
            View rootView = inflater.inflate(R.layout.fragment_obiady, container, false);
            /*
                Reczne wrzucanie wartosci do bazy

            Skladnik skladnik = new Skladnik();
            skladnik.setId_potrawy(1);
            skladnik.setJednoska("kg");
            skladnik.setIlosc(100);
            skladnik.setNazwa("Gruz");
            z.dodaj(skladnik);
            ArrayList s = z.getSkladniki(1);
            System.out.println(s.size());
            Przepis przepis = new Przepis();
            przepis.setText("Test");
            przepis.setCzas(10);
            przepis.setId_potrawa(1);
            z.dodaj(przepis);
            Potrawa obiad = new Potrawa();
            obiad.setNazwa("Test");
            obiad.setKategoria("Zupy");
            obiad.setImg("123");
            obiad.setIlosc("1");
            obiad.setPoziom("0");
            z.dodaj(obiad);
            obiad.setKategoria("Dania Glowne");
            z.dodaj(obiad);
            */
            String kategoria = getResources().getStringArray(R.array.obiad_array)[x];
            data = z.getPotrawa(kategoria);
            System.out.println("ONCREATE "+data.size());
            int len = data.size();

            if(len>0){
                final Potrawa h = data.get(0);
                if(len<3){
                    rootView = inflater.inflate(R.layout.grid_zero, container, false);
                    gw = (GridView) rootView.findViewById(R.id.gridView1);
                    gw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Potrawa potrawa = data.get(position);
                            Intent intent = new Intent(getActivity().getApplicationContext(), ObiadActivity.class);
                            intent.putExtra("potrawa", potrawa);
                            startActivity(intent);
                        }
                    });
                }
                else{
                    rootView = inflater.inflate(R.layout.fragment_obiady, container, false);
                    data.remove(0);
                    grid = (GridViewWithHeaderAndFooter) rootView.findViewById(R.id.gridView1);
                    LayoutInflater layoutInflater = LayoutInflater.from(rootView.getContext());
                    View headerView = layoutInflater.inflate(R.layout.header_grid, null);
                    TextView text = (TextView) headerView.findViewById(R.id.item_text);
                    text.setText(h.getNazwa());
                    grid.addHeaderView(headerView);
                    headerView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity().getApplicationContext(), ObiadActivity.class);
                            intent.putExtra("potrawa", h);
                            startActivity(intent);
                        }

                    });
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Potrawa potrawa = data.get(position);
                            Intent intent = new Intent(getActivity().getApplicationContext(), ObiadActivity.class);
                            intent.putExtra("potrawa", potrawa);
                            startActivity(intent);
                        }
                    });

                }
                adapter = new GridAdapter(rootView.getContext(), R.layout.row_grid, data);
                if(len<3)
                    gw.setAdapter(adapter);
                else
                    grid.setAdapter(adapter);
            }
            getActivity().setTitle(kategoria);
            return rootView;
        }
    }
}
