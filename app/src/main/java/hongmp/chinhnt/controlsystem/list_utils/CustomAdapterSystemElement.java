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
import hongmp.chinhnt.controlsystem.R;
import hongmp.chinhnt.controlsystem.object.SystemElement;

public class CustomAdapterSystemElement extends ArrayAdapter<SystemElement> {
    private ViewElementActivity activity;
    public static class ViewHolder {
        TextView txtName;
        TextView txtFunction;
        Button btnDetail;
        public int position;
    };

    private Context context;
    private int resource;
    private List<SystemElement> elements;
    public CustomAdapterSystemElement(@NonNull Context context, int resource, @NonNull List<SystemElement> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.elements=objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       ViewHolder viewHolder;
       View view=convertView;
       if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.custom_list_view_element,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.txtName=(TextView)view.findViewById(R.id.txtName);
            viewHolder.txtFunction=(TextView)view.findViewById(R.id.txtFunction);
            viewHolder.btnDetail=(Button)view.findViewById(R.id.btnUpdate);
            viewHolder.position=position;
            viewHolder.btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,ViewDetailElementActivity.class);
                    intent.putExtra("element",elements.get(position));
                    activity.startActivity(intent);
                }
            });
            view.setTag(viewHolder);
            view.setBackgroundResource(R.drawable.rounded_corner);
       }else{
           viewHolder=(ViewHolder)convertView.getTag();
           viewHolder.position=position;
       }

       SystemElement element=elements.get(position);
       viewHolder.txtName.setText(element.getName());
       viewHolder.txtFunction.setText(element.getAppendFunctions());

       return view;

    }

    public void setActivity(ViewElementActivity activity) {
        this.activity = activity;
    }
}
