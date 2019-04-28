package hongmp.chinhnt.controlsystem;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import hongmp.chinhnt.controlsystem.list_utils.CustomAdapterSystemElement;
import hongmp.chinhnt.controlsystem.net.Configuration;
import hongmp.chinhnt.controlsystem.object.SystemElement;
import hongmp.chinhnt.controlsystem.object.User;

public class ViewElementActivity extends AppCompatActivity {
    public ArrayList<SystemElement> listEl;
    private CustomAdapterSystemElement adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private View processView;
    private View allView;
    static int pressCount=0;
    private ViewElementActivity pointer;
    private User user;

    private TextView txtAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        pressCount=0;
        pointer=this;


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET}, 101);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        processView=(View)findViewById(R.id.progress_bar);
        allView=(View)findViewById(R.id.constraint_layout);

        System.out.println("In View Element Activity");
        Intent intent=getIntent();
        if (intent.getSerializableExtra("User")!=null) {
            user = (User) intent.getSerializableExtra("User");
        }

        if (intent.getSerializableExtra("elements")!=null) {
            listEl = (ArrayList<SystemElement>) intent.getSerializableExtra("elements");
        }

        txtAccount=(TextView)findViewById(R.id.txtAccount);
        txtAccount.setText(user.getName());
        txtAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAccount();
            }
        });

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new CustomAdapterSystemElement(listEl);
        adapter.setActivity(this);
        recyclerView.setAdapter(adapter);
    }

    private void onClickAccount(){
        //request for user Detail
        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra("User",user);
        startActivityForResult(intent, 100);
    }

    public User getUser(){
        return user;
    }


    @Override
    public void onBackPressed() {

        pressCount++;
        if (pressCount==1) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            pointer.onBackPressed();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            pressCount=0;
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to quit and log out the application?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }
        else
        {
            pressCount=0;
            super.onBackPressed();
        }
     }

    public void scanBtn(View v) {
        System.out.println("scanning");
        ScanTask mScanTask = new ScanTask(this,user);
        try {
            while (listEl.size()>0){
              //  recyclerView.removeViewAt(0);
                listEl.remove(0);
                recyclerView.getRecycledViewPool().clear();
                adapter.notifyDataSetChanged();
            //    adapter.notifyItemRemoved(0);
            //    adapter.notifyItemRangeChanged(0, listEl.size());
            }
            showProgress(true);
            JSONObject jsonObj = mScanTask.execute((Void) null).get();

            if (jsonObj!=null) {
                int elements_length = jsonObj.getInt("elements_length");
                JSONObject elements = jsonObj.getJSONObject("elements");

                for (int i = 0; i < elements_length; i++) {
                    String ele_name = "" + i;
                    JSONObject element = elements.getJSONObject(ele_name);
                    String element_name = element.getString("name");
                    String element_id = element.getString("id");
                    String element_xml = element.getString("xml");
                    SystemElement element_ = new SystemElement(element_name, element_id, element_xml);
                    listEl.add(element_);
                    adapter.notifyItemInserted(i);
                }

                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ex: " + e);
        }
    }
    public void showProgress(final boolean show){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            allView.setVisibility(show ? View.GONE : View.VISIBLE);
            allView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    allView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            processView.setVisibility(show ? View.VISIBLE : View.GONE);
            processView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    processView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            processView.setVisibility(show ? View.VISIBLE : View.GONE);
            allView.setVisibility(show ? View.GONE : View.VISIBLE);
           // mImageView.setVisibility(View.VISIBLE);
        }
    }
    public ArrayList<SystemElement> getListEl() {
        return listEl;
    }

    public CustomAdapterSystemElement getAdapter() {
        return adapter;
    }

    public void setAdapter(CustomAdapterSystemElement adapter) {
        this.adapter = adapter;
    }

    public void startEdit(String oldid, String newid){

        EditIDTask task=new EditIDTask(this);
        showProgress(true);
        task.execute(oldid,newid);
    }

    class ScanTask extends AsyncTask<Void, String, JSONObject> {

        private ViewElementActivity contextParent;
        User user;

        public ScanTask(ViewElementActivity contextParent,User user) {
            this.user=user;
            this.contextParent = contextParent;
        }

        @Override
        protected JSONObject doInBackground(Void... data) {
            // TODO: attempt authentication against a network service.
            HttpURLConnection conn = null;
            byte[] postDataBytes = null;
            try {
                // prepare data
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("request", "scan");
                params.put("session_id", user.getSession_id());

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                postDataBytes = postData.toString().getBytes("UTF-8");

                // Simulate network access.
                URL url = new URL(Configuration.SERVER_IP + ":" + Configuration.PORT + "/");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postDataBytes);
                Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder returnData = new StringBuilder();
                for (int c; (c = in.read()) >= 0; ) {
                    returnData.append((char) c);
                }
                return new JSONObject(returnData.toString());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ex: " + e);
                return null;
            }


        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            showProgress(false);
        }
    }
    class EditIDTask extends AsyncTask<String, Void, Boolean> {

        private ViewElementActivity contextParent;
        User user;

        public EditIDTask(ViewElementActivity contextParent) {

            this.contextParent = contextParent;
            this.user=contextParent.getUser();
        }

        @Override
        protected Boolean doInBackground(String... data) {
            // TODO: attempt authentication against a network service.
            HttpURLConnection conn = null;
            byte[] postDataBytes = null;
            try {
                // prepare data
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("request", "edit_id");
                params.put("old_id", data[0]);
                params.put("new_id",data[1]);
                params.put("session_id", user.getSession_id());

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                postDataBytes = postData.toString().getBytes("UTF-8");

                // Simulate network access.
                URL url = new URL(Configuration.SERVER_IP + ":" + Configuration.PORT + "/");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postDataBytes);
                Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder returnData = new StringBuilder();
                for (int c; (c = in.read()) >= 0; ) {
                    returnData.append((char) c);
                }



                return returnData.equals("ahihi");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ex: " + e);

                return null;
            }

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            showProgress(false);
        }
    }
}


