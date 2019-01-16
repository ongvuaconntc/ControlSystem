package hongmp.chinhnt.controlsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import hongmp.chinhnt.controlsystem.list_utils.CustomAdapterSystemElement;
import hongmp.chinhnt.controlsystem.list_utils.CustomAdapterSystemFunction;
import hongmp.chinhnt.controlsystem.object.SystemElement;
import hongmp.chinhnt.controlsystem.object.SystemFunction;
import hongmp.chinhnt.controlsystem.object.User;

public class ViewDetailElementActivity extends AppCompatActivity {

    ArrayList<SystemFunction> listFunc;
    private CustomAdapterSystemFunction adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private User user;
    private TextView txtAccount;
    private Button btnAddFunction;
    private ViewDetailElementActivity pointer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        recyclerView=(RecyclerView) findViewById(R.id.recylcerView_);
        pointer=this;


        if (intent.getSerializableExtra("element")!=null) {
            SystemElement element = (SystemElement) intent.getSerializableExtra("element");
            listFunc=element.getListFunctions();
            adapter=new CustomAdapterSystemFunction(listFunc);
            adapter.setActivity(this);
            mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapter);
        }
        if (intent.getSerializableExtra("User")!=null) {
            user = (User) intent.getSerializableExtra("User");
        }

        txtAccount=(TextView)findViewById(R.id.txtAccount);
        txtAccount.setText(user.getName());
        txtAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAccount();
            }
        });

        btnAddFunction=(Button)findViewById(R.id.btnAddFunction);

        btnAddFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(pointer);
                builder.setTitle("Add Function Name");
                final EditText input = new EditText(pointer);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = input.getText().toString();
                        listFunc.add(new SystemFunction("",text,"",""));
                        adapter.notifyItemInserted(listFunc.size()-1);

                        //send update name to server;
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });


    }

    private void onClickAccount(){
        //request for user Detail
        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra("User",user);
        startActivityForResult(intent, 100);
    }
//    public void updateFunction(View v){
//        Intent intent=new Intent(this,BlocklyActivity.class);
//        ConstraintLayout accountbarlayout=(ConstraintLayout)v.getParent();
//        CustomAdapterSystemFunction.ViewHolder holder=(CustomAdapterSystemFunction.ViewHolder)accountbarlayout.getTag();
//
//        if (holder==null) return;
//        System.out.println("holder postion: "+holder.position);
//        intent.putExtra("function",listFunc.get(holder.position));
//        startActivityForResult(intent,101);
//    }
//
//    public void removeFunction(View v){
//
//    }
}
