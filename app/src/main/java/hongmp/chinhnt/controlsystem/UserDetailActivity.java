package hongmp.chinhnt.controlsystem;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import hongmp.chinhnt.controlsystem.net.Configuration;
import hongmp.chinhnt.controlsystem.object.User;

public class UserDetailActivity extends Activity {

    private TextView txtUserAccount;
    private TextView txtLog;
    private Button btnChange;
    private Button btnLogout;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        txtUserAccount=(TextView)findViewById(R.id.txtDetailName);
        txtLog=(TextView)findViewById(R.id.txtDetailLog);
        btnChange=(Button)findViewById(R.id.btnChangepass);
        btnLogout=(Button)findViewById(R.id.btnLogout);

        Intent intent=getIntent();
        if (intent.getSerializableExtra("User")!=null) {
            user = (User) intent.getSerializableExtra("User");
        }

        txtUserAccount.setText(user.getName());
        txtLog.setText(user.getLog_list());

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoChangePassword();
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //send to server logout information!!!!!
                gotoLogin();
            }
        });

    }
    private void gotoChangePassword(){
        Intent intent = new Intent(this,ChangePasswordActivity.class);
        intent.putExtra("User",user);
        startActivity(intent);
    }

    private void gotoLogin(){
        // prepare data
        HttpURLConnection conn = null;
        byte[] postDataBytes = null;
        String intentData = "";
        try {
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("request", "logout");
            params.put("session_id", this.user.getSession_id());
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
        }
        catch (Exception e){
            System.out.println(e);
        }
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
