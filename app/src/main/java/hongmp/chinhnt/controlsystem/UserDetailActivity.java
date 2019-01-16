package hongmp.chinhnt.controlsystem;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
