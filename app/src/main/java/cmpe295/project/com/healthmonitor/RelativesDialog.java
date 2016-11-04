package cmpe295.project.com.healthmonitor;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by savani on 10/22/16.
 */

public class RelativesDialog extends DialogFragment {
    private TextInputEditText firstNameTxt;
    private TextInputEditText relationTxt;
    private TextInputEditText phoneTxt;
    private TextInputEditText emailTxt;
    private FloatingActionButton edit_btn;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_relativedetails, null);
        firstNameTxt = (TextInputEditText) v.findViewById(R.id.firstName);
        relationTxt = (TextInputEditText) v.findViewById(R.id.relation);
        phoneTxt = (TextInputEditText) v.findViewById(R.id.phoneNumber);
        emailTxt = (TextInputEditText) v.findViewById(R.id.email);
        edit_btn = (FloatingActionButton) v.findViewById(R.id.edit);
        edit_btn.setVisibility(View.INVISIBLE);


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)
                // Add action buttons
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                       saveRelativesDetails();


                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RelativesDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void saveRelativesDetails() {
        String firstName = null;
        String relation = null;
        String phone = null;
        String email = null;
        if(firstNameTxt.getText() != null){
            firstName = firstNameTxt.getText().toString();
        }
        if(relationTxt.getText() != null){
            relation = relationTxt.getText().toString();
        }
        if(phoneTxt.getText() != null){
            phone = phoneTxt.getText().toString();
        }
        if(emailTxt.getText() != null){
            email = emailTxt.getText().toString();
        }
        final JSONObject jsonBody = new JSONObject();
        try{
            jsonBody.put("name",firstName);
        }catch (JSONException e){

        }
        Intent i = new Intent(getContext(), MainActivity.class);
                        startActivity(i);
//Access key : TYuivv689
    }

    @Override
    public void onStart() {
        getDialog().setTitle(R.string.relativeDetails);
        super.onStart();
    }

    //    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
}
