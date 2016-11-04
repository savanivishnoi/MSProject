package cmpe295.project.com.healthmonitor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by savani on 10/22/16.
 */

public class RelativesFragment extends Fragment  implements View.OnClickListener {
    private FloatingActionButton edit_btn;
    private static boolean isEdit = false;
    private TextInputEditText firstNameTxt;
    private TextInputEditText relationTxt;
    private TextInputEditText phoneTxt;
    private TextInputEditText emailTxt;
    private static final String TAG = "Relatives Fragment";
    private static final String REQUIRED = "Required";
    private String patient_id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences
                (getActivity().getApplicationContext());
        patient_id = sp.getString(getString(R.string.patient_id), "");
        View v = inflater.inflate(R.layout.fragment_relativedetails, container, false);
        getRelatives();
        edit_btn = (FloatingActionButton) v.findViewById(R.id.edit);
        firstNameTxt = (TextInputEditText) v.findViewById(R.id.firstName);
        relationTxt = (TextInputEditText) v.findViewById(R.id.relation);
        phoneTxt = (TextInputEditText) v.findViewById(R.id.phoneNumber);
        emailTxt = (TextInputEditText) v.findViewById(R.id.email);
        edit_btn.setOnClickListener(this);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_profile, menu);  //   getActivity().getMenuInflater()
        super.onCreateOptionsMenu(menu, inflater);
        //  return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit:  //when editing is pressed
            {
                isEdit = true;
                edit_btn.setVisibility(View.INVISIBLE);
                setHasOptionsMenu(true);
                setEditable();
                break;
            }
        }
    }

    void setEditable() {
        phoneTxt.setEnabled(true);
        emailTxt.setEnabled(true);
        relationTxt.setEnabled(true);
        firstNameTxt.setEnabled(true);
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.relativeDetails));
    }

    public void getRelatives(){

    }

}
