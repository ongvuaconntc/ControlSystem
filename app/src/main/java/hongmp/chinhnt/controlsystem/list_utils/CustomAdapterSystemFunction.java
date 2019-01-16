package hongmp.chinhnt.controlsystem.list_utils;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import hongmp.chinhnt.controlsystem.ViewDetailElementActivity;
import hongmp.chinhnt.controlsystem.BlocklyActivity;
import hongmp.chinhnt.controlsystem.R;
import hongmp.chinhnt.controlsystem.object.SystemFunction;

public class CustomAdapterSystemFunction extends  RecyclerView.Adapter<CustomAdapterSystemFunction.ViewHolder>{
    private ViewDetailElementActivity activity;

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView txtName;
        public Button btnUpdateName;
        public Button btnUpdate;
    //    public Button btnRemove;
        public int position;
        public ViewHolder(LinearLayout itemView) {
            super(itemView);
            this.txtName=itemView.findViewById(R.id.txtName);
            this.btnUpdateName=itemView.findViewById(R.id.btnUpdateName);
            this.btnUpdate=itemView.findViewById(R.id.btnUpdate);
         //   this.btnRemove=itemView.findViewById(R.id.btnRemove);



            this.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SystemFunction function=functions.get(position);
                    Intent intent=new Intent(activity,BlocklyActivity.class);
                    intent.putExtra("function",function);
                    activity.startActivity(intent);
                }
            });

            this.btnUpdateName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Change Function Name");
                    final EditText input = new EditText(activity);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String text = input.getText().toString();
                            txtName.setText(text);
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
    };


    private List<SystemFunction> functions;
    public CustomAdapterSystemFunction(List<SystemFunction> objects) {
        this.functions=objects;
    }

    public CustomAdapterSystemFunction.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_recycler_view_function, parent, false);

        CustomAdapterSystemFunction.ViewHolder vh = new CustomAdapterSystemFunction.ViewHolder(v);
        return vh;
    }


    public void onBindViewHolder(@NonNull CustomAdapterSystemFunction.ViewHolder holder, int position) {
        holder.txtName.setText(functions.get(position).getName());
        holder.position=position;
    }

    @Override
    public int getItemCount() {
        return functions.size();
    }

/*
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       ViewHolder viewHolder;
       View view=convertView;
       if (view==null){
            view= LayoutInflater.from(context).inflate(R.accountbarlayout.custom_recycler_view_function,parent,false);
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
    */

    public void setActivity(ViewDetailElementActivity activity) {
        this.activity = activity;
    }
}
