package cmpe295.project.com.healthmonitor;

import android.view.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by savani on 9/27/16.
 */

public class ListNavigationMenus {
    private  List<MenuModel> listDataHeader = new ArrayList<>();
    private  HashMap<String, List<MenuModel>> listChild = new HashMap<>();
    public static ListNavigationMenus mInstance;
    private ListNavigationMenus(){
        MenuModel mmProfile = new MenuModel();
        mmProfile.id = "Account";
        mmProfile.menuItem = "Account";
        mmProfile.imageIcon = R.mipmap.ic_account;
       // mmProfile.className = "ProfileFragment.class";
        listDataHeader.add(mmProfile);

        MenuModel mmMeasure = new MenuModel();
        mmMeasure.id = "Measure";
        mmMeasure.menuItem = "Measure";
        listDataHeader.add(mmMeasure);

        MenuModel mmNotifications = new MenuModel();
        mmNotifications.menuItem = "Notifications";
        mmNotifications.id = "Notifications";
        listDataHeader.add(mmNotifications);

        MenuModel mmTrends = new MenuModel();
        mmTrends.menuItem = "Trends";
        mmTrends.id = "Trends";
        listDataHeader.add(mmTrends);

        List<MenuModel> lh =new ArrayList<>();

        MenuModel mmEditProfile = new MenuModel();
        mmEditProfile.menuItem = "Profile";
        mmEditProfile.id = "Profile";
        mmEditProfile.imageIcon = R.mipmap.ic_profile;
        lh.add(mmEditProfile);

        MenuModel mmRelatives = new MenuModel();
        mmRelatives.menuItem = "Relatives";
        mmRelatives.id = "Relatives";
        mmRelatives.imageIcon = R.mipmap.ic_relatives;
        lh.add(mmRelatives);


        List<MenuModel> lh1 =  new ArrayList<>();
        MenuModel mmDiabetes = new MenuModel();
        mmDiabetes.menuItem = "Diabetes";
        mmDiabetes.id = "Diabetes";
        lh1.add(mmDiabetes);

        listChild.put(listDataHeader.get(0).id,lh);
        listChild.put(listDataHeader.get(1).id,lh1);
    }
    public static ListNavigationMenus getInstance(){
        if ( mInstance == null)
        {
            mInstance = new ListNavigationMenus();
        }
        return mInstance;
    }

    public List<MenuModel> getMenu(){
        return listDataHeader;
    }
    public HashMap<String, List<MenuModel>> getChildMenu(){
        return listChild;
    }



}
class MenuModel{
    String id;//id of menu
    String menuItem;//description of menu
    int imageIcon;//image icon
    String className;
}
