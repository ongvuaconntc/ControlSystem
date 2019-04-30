package hongmp.chinhnt.controlsystem.list_utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLOutput;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hongmp.chinhnt.controlsystem.BlocklyActivity;
import hongmp.chinhnt.controlsystem.ViewElementActivity;
import hongmp.chinhnt.controlsystem.R;
import hongmp.chinhnt.controlsystem.net.Configuration;
import hongmp.chinhnt.controlsystem.object.SystemElement;
import hongmp.chinhnt.controlsystem.object.User;

public class CustomAdapterSystemElement extends RecyclerView.Adapter<CustomAdapterSystemElement.ViewHolder>{
    private ViewElementActivity activity;
    private String[] mDataset;
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView txtName;
        TextView txtFunction;
        Button btnDetail;
        Button btnEditId;
        boolean isError;
        public int position;
        AlertDialog.Builder builder;
        public ViewHolder(LinearLayout itemView) {
            super(itemView);
            this.txtName=itemView.findViewById(R.id.txtName);
            this.txtFunction=itemView.findViewById(R.id.txtFunction);
            this.btnDetail=itemView.findViewById(R.id.btnDetail);
            this.btnEditId=itemView.findViewById(R.id.btnEditId);

            this.btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SystemElement element=activity.listEl.get(position);

                        Intent intent = new Intent(activity, BlocklyActivity.class);
                        intent.putExtra("element", element);
                        intent.putExtra("user", activity.getUser());

                    activity.startActivity(intent);
                    //}
                }
            });

            this.btnEditId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isError=false;
/*
                         builder = new AlertDialog.Builder(activity);
                        String addition="";
                        if (isError) addition=" (ID length = 4 only)";
                        builder.setTitle("Edit " + txtName.getText().toString() + " ID"+addition);
                        final EditText input = new EditText(activity);
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(input);

                        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String text = input.getText().toString();
                                if (text.length() != 4) {
                                    System.out.println("error!!!!");
                                    isError=true;
                                }
                                else
                                activity.startEdit(txtFunction.getText().toString(), text);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        while (true) {
                            System.out.println("in loop");
                            builder.show();
                            if (!isError) {
                                System.out.println("Breakkkkkk");;
                                break;
                            }
                        }
                        */
                    final EditText input = new EditText(activity);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    
                    final AlertDialog d = new AlertDialog.Builder(activity)
                            .setView(input)
                            .setTitle(activity.getResources().getString(R.string.edit_txt)+" " + txtName.getText().toString() + "'s ID")
                            .setPositiveButton(activity.getResources().getString(R.string.edit_txt), null) //Set to null. We override the onclick
                            .setNegativeButton(activity.getResources().getString(R.string.cancel_txt), null)
                            .create();

                    d.setOnShowListener(new DialogInterface.OnShowListener() {

                        @Override
                        public void onShow(DialogInterface dialog) {

                            Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                            b.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    // TODO Do something
                                    String text = input.getText().toString();
                                    if (text.length() != 4) {
                                        System.out.println("error!!!!");
                                        isError=true;
                                        String addition=" ("+activity.getResources().getString(R.string.change_id_msg)+")";
                                        d.setTitle(activity.getResources().getString(R.string.edit_txt)+" " + txtName.getText().toString() + "'s ID"+addition);
                                    }
                                    else
                                    {
                                        activity.startEdit(txtFunction, text);
                                        d.dismiss();
                                    }
                                }
                            });
                        }
                    });
                    d.show();
                }
            });

        }
    }

    private List<SystemElement> elements;
    public CustomAdapterSystemElement(@NonNull List<SystemElement> objects) {
        this.elements=objects;
    }
    public CustomAdapterSystemElement.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_recycler_view_element, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (elements.size()>position) {
            holder.txtFunction.setText(elements.get(position).getId());
            holder.txtName.setText(elements.get(position).getName());
            holder.position = position;
        }

    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    public void setActivity(ViewElementActivity activity) {
        this.activity = activity;
    }

}


