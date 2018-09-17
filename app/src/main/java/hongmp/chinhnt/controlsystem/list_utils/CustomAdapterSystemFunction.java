package hongmp.chinhnt.controlsystem.list_utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import hongmp.chinhnt.controlsystem.ViewDetailElementActivity;
import hongmp.chinhnt.controlsystem.ViewElementActivity;
import hongmp.chinhnt.controlsystem.BlocklyActivity;
import hongmp.chinhnt.controlsystem.R;
import hongmp.chinhnt.controlsystem.object.SystemFunction;

public class CustomAdapterSystemFunction extends ArrayAdapter<SystemFunction> {
    private ViewDetailElementActivity activity;
    public static class ViewHolder {
        TextView txtName;
        TextView txtFunction;
        public Button btnUpdate;
        public Button btnRemove;
        public int position;
    };

    private Context context;
    private int resource;
    private List<SystemFunction> functions;
    public CustomAdapterSystemFunction(@NonNull Context context, int resource, @NonNull List<SystemFunction> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.functions=objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       ViewHolder viewHolder;
       View view=convertView;
       if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.custom_list_view_function,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.txtName=(TextView)view.findViewById(R.id.txtName);
            viewHolder.txtFunction=(TextView)view.findViewById(R.id.txtFunction);
            viewHolder.btnUpdate=(Button)view.findViewById(R.id.btnUpdate);
            viewHolder.btnRemove=(Button)view.findViewById(R.id.btnRemove);
            viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SystemFunction function=functions.get(position);
                    Intent intent=new Intent(context,BlocklyActivity.class);
                    intent.putExtra("function",function);
                    activity.startActivity(intent);
                }
            });

           viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   SystemFunction function=functions.get(position);
                    if (function!=null&&ViewElementActivity.client!=null)
                   ViewElementActivity.client.removeFunction(function.getNode(),function.getName());
               }
           });
            view.setTag(viewHolder);
            view.setBackgroundResource(R.drawable.rounded_corner);
       }else{
           viewHolder=(ViewHolder)convertView.getTag();
           viewHolder.position=position;
       }

       SystemFunction function=functions.get(position);
       viewHolder.txtName.setText("Port:"+new Integer(function.getPort()).toString());
       viewHolder.txtFunction.setText("Function:"+function.getName());

       return view;

    }

    public void setActivity(ViewDetailElementActivity activity) {
        this.activity = activity;
    }
}
