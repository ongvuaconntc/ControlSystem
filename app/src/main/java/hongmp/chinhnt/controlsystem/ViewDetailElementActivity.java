package hongmp.chinhnt.controlsystem;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import hongmp.chinhnt.controlsystem.list_utils.CustomAdapterSystemElement;
import hongmp.chinhnt.controlsystem.list_utils.CustomAdapterSystemFunction;
import hongmp.chinhnt.controlsystem.object.SystemElement;
import hongmp.chinhnt.controlsystem.object.SystemFunction;

public class ViewDetailElementActivity extends AppCompatActivity {

    ArrayList<SystemFunction> listFunc;
    private CustomAdapterSystemFunction adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        listView=(ListView) findViewById(R.id.list);

        if (intent.getSerializableExtra("element")!=null) {
            SystemElement element = (SystemElement) intent.getSerializableExtra("element");
            listFunc=element.getListFunctions();
            adapter=new CustomAdapterSystemFunction(this,R.layout.custom_list_view_function,listFunc);
            adapter.setActivity(this);
            listView.setAdapter(adapter);
        }
    }
//    public void updateFunction(View v){
//        Intent intent=new Intent(this,BlocklyActivity.class);
//        ConstraintLayout layout=(ConstraintLayout)v.getParent();
//        CustomAdapterSystemFunction.ViewHolder holder=(CustomAdapterSystemFunction.ViewHolder)layout.getTag();
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
