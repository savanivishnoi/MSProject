package cmpe295.project.com.healthmonitor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
    private static final String TAG = "Profile Fragment";
    private static final String REQUIRED = "Required";
    private String patient_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        setRetainInstance(true);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences
                (getActivity().getApplicationContext());
        patient_id = sp.getString(getString(R.string.patient_id), "");

        getProfile();
        edit_btn = (FloatingActionButton) v.findViewById(R.id.edit);
        lastName_txt = (TextInputEditText) v.findViewById(R.id.lastName);
        firstName_txt = (TextInputEditText) v.findViewById(R.id.firstName);
        sex_txt = (TextInputEditText) v.findViewById(R.id.sex);
        dob_txt = (TextInputEditText) v.findViewById(R.id.dob);
        phone_txt = (TextInputEditText) v.findViewById(R.id.phoneNumber);
        email_txt = (TextInputEditText) v.findViewById(R.id.email);
        address_txt = (TextInputEditText) v.findViewById(R.id.address);
        doctor_txt = (TextInputEditText) v.findViewById(R.id.doctor);


        lastName_txt.setEnabled(false);
        firstName_txt.setEnabled(false);
        sex_txt.setEnabled(false);
        dob_txt.setEnabled(false);
        phone_txt.setEnabled(false);
        email_txt.setEnabled(false);
        address_txt.setEnabled(false);
        doctor_txt.setEnabled(false);

        edit_btn.setOnClickListener(this);

        lastName_txt.setText("Doe");
        firstName_txt.setText("John");

//        postPicBtn =(ImageButton) v.findViewById(R.id.postPicButton);
//
//        postPicBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i(TAG,"You've clicked post button");
//                popUp = new Intent(view.getContext() , PopUp.class);
//                startActivityForResult(popUp, PopUp.UPLOAD_OR_TAKE_PHOTO);
//            }
//        });

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
                phone_txt.setEnabled(false);
                email_txt.setEnabled(false);
                address_txt.setEnabled(false);
                getProfile();
                edit_btn.setVisibility(View.VISIBLE);
                break;
            case R.id.submit:
                isEdit = false;
                setHasOptionsMenu(false);
                edit_btn.setVisibility(View.VISIBLE);
                saveProfile();
                //call service
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.profile));
    }

    void setEditable() {
        phone_txt.setEnabled(true);
        email_txt.setEnabled(true);
        address_txt.setEnabled(true);
    }

    void getProfile() {

        String url = getString(R.string.url) + "/patient?patient_id=" + patient_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    firstName_txt.setText(jsonArray.getJSONObject(0).getString("first_name"));
                    lastName_txt.setText(jsonArray.getJSONObject(0).getString("last_name"));
                    dob_txt.setText(jsonArray.getJSONObject(0).getString("dob"));
                    sex_txt.setText(jsonArray.getJSONObject(0).getString("sex"));
                    address_txt.setText(jsonArray.getJSONObject(0).getString("address"));
                    phone_txt.setText(jsonArray.getJSONObject(0).getString("phone"));
                    doctor_txt.setText(jsonArray.getJSONObject(0).getString("doctor_id"));
                    email_txt.setText(jsonArray.getJSONObject(0).getString("email"));

                    Log.d(TAG, response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }

        );
        VolleyQueueHelper.getInstance(getActivity().getApplicationContext()).getQueue().add(stringRequest);
    }

    void saveProfile() {

        if (validate(email_txt) && validate(phone_txt) && validate(address_txt)) {
            String phone = phone_txt.getText().toString();
            String email = email_txt.getText().toString();
            String address = address_txt.getText().toString();
            String url = getString(R.string.url) + "patient?patient_id=" + patient_id;
            final JSONObject jsonBody = new JSONObject();

            try {
                jsonBody.put("first_name", "Leela");
                jsonBody.put("last_name", "Roy");
                jsonBody.put("dob", "1971-10-22");
                jsonBody.put("sex" , "Female");
                jsonBody.put("doctor_id", 1);
                jsonBody.put("phone", phone);
                jsonBody.put("email", email);
                jsonBody.put("address", address);
            } catch (JSONException e) {
                Log.d(TAG, "Exception in JSON" + e);
            }
            final String mRequestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            Toast.makeText(getActivity(), "Successfully update profile.",
                                    Toast.LENGTH_SHORT);
                            phone_txt.setEnabled(false);
                            email_txt.setEnabled(false);
                            address_txt.setEnabled(false);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                    Toast.makeText(getActivity(), "Error. Please try later.",
                            Toast.LENGTH_SHORT);
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }
            };
            VolleyQueueHelper.getInstance(getActivity().getApplicationContext()).
                    getQueue().add(stringRequest);

        }
    }

    boolean validate(EditText etext) {
        if (etext.getText().toString().length() == 0) {
            etext.setError(REQUIRED);
            return false;
        }
        return true;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == PopUp.UPLOAD_OR_TAKE_PHOTO && data != null){
//            String userOption = data.getStringExtra(PopUp.PHOTO_OPTION);
//
//            Log.i(TAG,userOption + " this is the user option");
//            Log.i(TAG,PopUp.UPLOAD_PHOTO_OPTION + " this is the upload photo option");
//            Log.i(TAG,PopUp.TAKE_PHOTO_OPTION + " this is the take photo option");
//
//            if(userOption.equals(PopUp.UPLOAD_PHOTO_OPTION)){
//                Log.i(TAG,"You've clicked upload photo");
//                uploadPicFromGallery();
//            }else if(userOption.equals(PopUp.TAKE_PHOTO_OPTION)){
//                Log.i(TAG,"You've clicked take photo");
//                dispatchTakePictureIntent();
//            }
//        }else if( requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK){
//
//            Log.i(TAG,"Image Capture Successful");
//            int targetW = postPicBtn.getWidth();
//            int targetH = postPicBtn.getHeight();
//
//            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//            bmOptions.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(mCurrentPhotoPath);
//            int photoW = bmOptions.outWidth;
//            int photoH = bmOptions.outHeight;
//
//            int scaleFactor = 1;
//            if((targetH > 0) || (targetW > 0)){
//                scaleFactor = Math.min(photoW/targetW, photoH/targetH);
//            }
//
//            bmOptions.inJustDecodeBounds = false;
//            bmOptions.inSampleSize = scaleFactor;
//
//            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath,bmOptions);
//
//            postPicBtn.setImageBitmap(bitmap);
//            postPicBtn.setAdjustViewBounds(true);
//            postPicBtn.setVisibility(View.VISIBLE);
//            addToGallery();
//
//
//        }else if( requestCode == REQUEST_THUMBNAIL_CAPTURE && resultCode ==  getActivity().RESULT_OK && data != null){
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            postPicBtn.setImageBitmap(imageBitmap);
//
//        }else if( requestCode == PICK_IMAGE_REQUEST && resultCode ==  getActivity().RESULT_OK && data != null && data.getData() != null){
//            Uri uri =  data.getData();
//            try {
//                thumbnailImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
//                postPicBtn.setImageBitmap(thumbnailImage);
//                postPicBtn.setAdjustViewBounds(true);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
