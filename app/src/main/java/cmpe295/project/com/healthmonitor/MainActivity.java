package cmpe295.project.com.healthmonitor;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

/**
 * Created by savani on 8/3/16.
 */
public class MainActivity extends AppCompatActivity implements
                                                    ExpandableListView.OnChildClickListener,
                                                    ExpandableListView.OnItemClickListener,
                                                    ExpandableListView.OnGroupClickListener
{
   private Button measureBtn;
    private  Button checkBtn;
    private  Button trendsBtn;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBar actionBar;
    private  ExpandableListAdapter mMenuAdapter;
    private  ExpandableListView expandableList;
    private ActionBarDrawerToggle mDrawerToggle;
     static final String TAG = "Main Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationdrawer);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        String [] listTitles= {"Hello", "How"};
        expandableList = (ExpandableListView)findViewById(R.id.navigation_menu);
       // mDrawerList = (ListView) findViewById(R.id.left_drawer);
        NavigationView navigationView = (NavigationView)    findViewById(R.id.nav_view);
        if(navigationView!=null){
            setupDrawerContent(navigationView);
        }
        mMenuAdapter = new ExpandableListAdapter1(this, ListNavigationMenus.getInstance().getMenu(),
                         ListNavigationMenus.getInstance().getChildMenu(), expandableList);

        // setting list adapter
        expandableList.setAdapter(mMenuAdapter);
        expandableList.setOnItemClickListener(this);
        expandableList.setOnChildClickListener(this);
        expandableList.setOnGroupClickListener(this);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close ){

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                Log.d(TAG,"drawer closed");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                Log.d(TAG,"drawer state changed");
            }

            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                super.onConfigurationChanged(newConfig);
                Log.d(TAG,"drawer config changed");
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                Log.d(TAG,"item selected...."+item);
                // true, then it has handled the app icon touch event
//                if (mDrawerToggle.onOptionsItemSelected(item)) {
//                    return false;
//                }
                return super.onOptionsItemSelected(item);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                Log.d(TAG,"drawer opened");
                super.onDrawerOpened(drawerView);
                actionBar.setTitle(getString(R.string.app_name));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);




// Set the adapter for the list view
//        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.navigation_row_menu, listTitles));
        // Set the list's click listener
       // mDrawerList.setOnItemClickListener(new DrawerItemClickListener());



  /*old code
       // setContentView(R.layout.main_activity);
//        measureBtn = (Button)findViewById(R.id.measure);
//        checkBtn = (Button) findViewById(R.id.sugarLevel);
//        trendsBtn = (Button)findViewById(R.id.trends);
//
//        trendsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), TrendsActivity.class);
//                startActivity(i);
//
//            }
//        });
*/ //old code end

    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Log.d(TAG,"navigation item selected");
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "item click"+position);
            MenuModel selectedMenu = ListNavigationMenus.getInstance().getMenu().get(position);
        selectItem(selectedMenu.id);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Log.d(TAG, "child click"+groupPosition + "child"+childPosition);
        MenuModel selectedMenu = ListNavigationMenus.getInstance().
                                    getChildMenu().get(ListNavigationMenus.getInstance().
                                        getMenu().get(groupPosition).id).get(childPosition);
        selectItem(selectedMenu.id);
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        Log.d(TAG, "item click"+groupPosition);
        MenuModel selectedMenu = ListNavigationMenus.getInstance().getMenu().get(groupPosition);
        selectItem(selectedMenu.id);
        return false;
    }

    void selectItem(String selectedMenuItem){  //for handling menu item sleected in navigation drawer
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment;
        Log.d(TAG, selectedMenuItem);
        switch (selectedMenuItem) {
            case ("Profile"):
                fragment = new ProfileFragment();
                ft.replace(R.id.content_frame, fragment);
                mDrawerLayout.closeDrawers();
                break;
            case("Heart Rate"):
                fragment = new HeartRateGraphFragment();
                ft.replace(R.id.content_frame, fragment);
                mDrawerLayout.closeDrawers();
                break;
        }
        ft.commit();
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
        mDrawerToggle.onConfigurationChanged(newConfig);
        Log.d(TAG,"config changed");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "main options selected.. "+item.getItemId()+item.getMenuInfo());
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
