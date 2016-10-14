package cmpe295.project.com.healthmonitor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.w3c.dom.Text;

/**
 * Created by savani on 9/30/16.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private TextInputEditText lastName_txt;
    private TextInputEditText firstName_txt;
    private TextInputEditText sex_txt;
    private TextInputEditText dob_txt;
    private TextInputEditText phone_txt;
    private TextInputEditText email_txt;
    private TextInputEditText address_txt;
    private TextInputEditText doctor_txt;
    private FloatingActionButton edit_btn;
    private static boolean isEdit = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        setRetainInstance(true);


        edit_btn = (FloatingActionButton) v.findViewById(R.id.edit);
        lastName_txt = (TextInputEditText) v.findViewById(R.id.lastName);
        firstName_txt = (TextInputEditText) v.findViewById(R.id.firstName);
        sex_txt = (TextInputEditText) v.findViewById(R.id.sex);
        dob_txt = (TextInputEditText) v.findViewById(R.id.dob);
        phone_txt = (TextInputEditText) v.findViewById(R.id.phoneNumber);
        email_txt = (TextInputEditText) v.findViewById(R.id.email);
        address_txt = (TextInputEditText) v.findViewById(R.id.address);
        doctor_txt = (TextInputEditText) v.findViewById(R.id.doctor);


        lastName_txt.setFocusable(false);
        firstName_txt.setFocusable(false);
        sex_txt.setFocusable(false);
        dob_txt.setFocusable(false);
        phone_txt.setFocusable(false);
        email_txt.setFocusable(false);
        address_txt.setFocusable(false);
        doctor_txt.setFocusable(false);

        edit_btn.setOnClickListener(this);

      /*
        sex_txt

        dob_txt
        phone_txt
        email_txt
        address_txt
        doctor_txt
                */


        lastName_txt.setText("HElllo");
        firstName_txt.setText("Savani");


        return v;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_profile, menu);  //   getActivity().getMenuInflater()
        super.onCreateOptionsMenu(menu, inflater);
        //  return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cancelform:
                break;
            case R.id.submit:
                isEdit = false;
                setHasOptionsMenu(false);
                edit_btn.setVisibility(View.VISIBLE);
                //call service
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit  :  //when editing is pressed
            {
                isEdit = true;
                edit_btn.setVisibility(View.INVISIBLE);
                setHasOptionsMenu(true);
                setEditable();


                break;

            }
        }

    }

    void setEditable(){
        phone_txt.setFocusable(true);
        email_txt.setFocusable(true);
        address_txt.setFocusable(true);
    }


}
