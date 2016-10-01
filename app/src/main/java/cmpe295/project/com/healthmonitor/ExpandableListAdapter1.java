package cmpe295.project.com.healthmonitor;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by savani on 9/27/16.
 */

public class ExpandableListAdapter1 extends BaseExpandableListAdapter {
    private Context mContext;
    private List<MenuModel> mListDataHeader; // header titles

    // child data in format of header title, child title
    private HashMap<String, List<MenuModel>> mListDataChild;
    ExpandableListView expandList;
    public ExpandableListAdapter1(Context context, List<MenuModel> listDataHeader,
                                  HashMap<String, List<MenuModel>> listChildData,
                                  ExpandableListView mView)
    {
        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
        this.expandList=mView;
    }

    @Override
    public int getGroupCount() {
        int i= mListDataHeader.size();
        Log.d("GROUPCOUNT",String.valueOf(i));
        return this.mListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount=0;
        if(groupPosition!=2)
        {
            childCount=this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
                    .size();
        }
        return childCount;
    }

    @Override
    public Object getGroup(int groupPosition) {

        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.d("CHILD",mListDataChild.get(this.mListDataHeader.get(groupPosition))
                .get(childPosition).toString());
        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }




//    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View   convertView, ViewGroup parent) {
        //ExpandedMenuModel headerTitle = (ExpandedMenuModel) getGroup(groupPosition);
        MenuModel headerTitle = (MenuModel) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.navigation_row_menu, null);
        }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.menu);
        ImageView headerIcon=    (ImageView)convertView.findViewById(R.id.menu_img);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle.menuItem);
       // headerIcon.setImageDrawable();
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,  boolean isLastChild, View convertView, ViewGroup parent) {
        final MenuModel child = (MenuModel) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.navigation_row_submenu, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.menuChild);

        txtListChild.setText(child.menuItem);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}