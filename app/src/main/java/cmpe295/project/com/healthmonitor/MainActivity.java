package cmpe295.project.com.healthmonitor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
                                                    ExpandableListView.OnItemClickListener {
    Button measureBtn;
    Button checkBtn;
    Button trendsBtn;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBar actionBar;
    ExpandableListAdapter mMenuAdapter;
    ExpandableListView expandableList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationdrawer);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MenuModel selectedMenu = ListNavigationMenus.getInstance().getMenu().get(position);
        selectItem(selectedMenu.id);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        MenuModel selectedMenu = ListNavigationMenus.getInstance().
                                    getChildMenu().get(groupPosition).get(childPosition);
        selectItem(selectedMenu.id);
        return false;
    }

    void selectItem(String selectedMenuItem){
        switch (selectedMenuItem) {
            case ("Profile"):
                FragmentManager fm = getSupportFragmentManager();
                Fragment fragment = new ProfileFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.content_frame, fragment);
                ft.commit();
                break;
            //case("")


        }

    }
}
