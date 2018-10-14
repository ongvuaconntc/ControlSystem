package hongmp.chinhnt.controlsystem;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import hongmp.chinhnt.controlsystem.list_utils.CustomAdapterSystemElement;
import hongmp.chinhnt.controlsystem.net.Configuration;
import hongmp.chinhnt.controlsystem.net.MyTCPClient;
import hongmp.chinhnt.controlsystem.object.SystemElement;
import hongmp.chinhnt.controlsystem.object.SystemFunction;

public class ViewElementActivity extends AppCompatActivity {
    static String ipServer = "10.3.141.1";
    static int port = 11000;
    static boolean TEST_MODE = true;

    public ArrayList<SystemElement> listEl;
    private CustomAdapterSystemElement adapter;
    static public MyTCPClient client;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
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

        if (!TEST_MODE) {
            try {
                client = new MyTCPClient(InetAddress.getByName(ipServer), port);
                String x = "hello\n";
                byte[] data = new byte[6];
                for (int i = 0; i < 6; i++) data[i] = (byte) x.charAt(i);
                client.writeData(data);
            } catch (Exception e) {
                e.printStackTrace();

                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Can't connect to hub wifi, Please check connection and try again!");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                finish();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }

            if (client != null)
                listEl = client.requestStatus();
            else
                listEl = new ArrayList<>();
        } else {
            listEl = new ArrayList<>();
            SystemFunction function=new SystemFunction("D1","Tắt cửa","cửa cửa","Node1");
            SystemFunction function1=new SystemFunction("D2","Tắt đèn","cửa cửa","Node1");

            ArrayList<SystemFunction> functions=new ArrayList<>();
            functions.add(function);
            functions.add(function1);
            SystemElement element=new SystemElement("Node1",functions);

            ArrayList<SystemFunction> m_function = new ArrayList<>();
            m_function.add(new SystemFunction(0 + "", "Masterrr", "system control", "Master"));
            SystemElement element_ = new SystemElement("Master", m_function);
            listEl.add(element_);
            listEl.add(element);
        }
        System.out.println("In View Element Activity");
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new CustomAdapterSystemElement(listEl);
        adapter.setActivity(this);
        recyclerView.setAdapter(adapter);
    }

    public void viewDetail(View v) {
        Intent intent = new Intent(this, ViewDetailElementActivity.class);
        ConstraintLayout layout = (ConstraintLayout) v.getParent();
        CustomAdapterSystemElement.ViewHolder holder = (CustomAdapterSystemElement.ViewHolder) layout.getTag();
        System.out.println("Holder: " + holder);
        if (holder == null) return;
        System.out.println("holder postion: " + holder.position);
        intent.putExtra("element", listEl.get(holder.position));
        startActivityForResult(intent, 100);
    }

    public void scanBtn(View v) {
        System.out.println("scanning");
        ScanTask mScanTask = new ScanTask(this);
        try {
            JSONObject jsonObj = mScanTask.execute((Void) null).get();

            listEl = new ArrayList<>();

            ArrayList<SystemFunction> m_function = new ArrayList<>();
            m_function.add(new SystemFunction("0", "Master", "system control", "Master"));
            SystemElement element_ = new SystemElement("Master", m_function);
            listEl.add(element_);

            for (int i = 0; i < jsonObj.length(); i++) {
                ArrayList<SystemFunction> functions_ = new ArrayList<>();
                SystemFunction function7 = new SystemFunction("1", "Hẹn giờ bật nóng lạnh", "cửa cửa", "Node" + (i + 1));
                SystemFunction function8 = new SystemFunction("2", "Bật điều hòa", "cửa cửa", "Node2" + (i + 1));
                SystemFunction function9 = new SystemFunction("3", "Bật camera", "cửa cửa", "Node2" + (i + 1));
                functions_.add(function8);
                functions_.add(function9);
                functions_.add(function7);
                SystemElement element = new SystemElement("Node" + (i + 1), functions_);
                listEl.add(element);
            }
            adapter = new CustomAdapterSystemElement(listEl);
            adapter.setActivity(this);
            recyclerView.swapAdapter(adapter, false);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ex: " + e);
        }
    }

    public ArrayList<SystemElement> getListEl() {
        return listEl;
    }

    public void setListEl(ArrayList<SystemElement> listEl) {
        this.listEl = listEl;
    }

    public CustomAdapterSystemElement getAdapter() {
        return adapter;
    }

    public void setAdapter(CustomAdapterSystemElement adapter) {
        this.adapter = adapter;
    }
}

class ScanTask extends AsyncTask<Void, String, JSONObject> {

    private Activity contextParent;

    ScanTask() {
    }

    public ScanTask(Activity contextParent) {
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

}
