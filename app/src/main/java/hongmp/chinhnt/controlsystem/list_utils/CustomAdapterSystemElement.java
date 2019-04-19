package hongmp.chinhnt.controlsystem.list_utils;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import hongmp.chinhnt.controlsystem.BlocklyActivity;
import hongmp.chinhnt.controlsystem.ViewDetailElementActivity;
import hongmp.chinhnt.controlsystem.ViewElementActivity;
import hongmp.chinhnt.controlsystem.R;
import hongmp.chinhnt.controlsystem.object.SystemElement;

public class CustomAdapterSystemElement extends RecyclerView.Adapter<CustomAdapterSystemElement.ViewHolder>{
    private ViewElementActivity activity;
    private String[] mDataset;
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView txtName;
        TextView txtFunction;
        Button btnDetail;
        public int position;
        public ViewHolder(LinearLayout itemView) {
            super(itemView);
            this.txtName=itemView.findViewById(R.id.txtName);
            this.txtFunction=itemView.findViewById(R.id.txtFunction);
            this.btnDetail=itemView.findViewById(R.id.btnDetail);

            this.btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SystemElement element=activity.listEl.get(position);
                    /*
                    if (!element.getName().equalsIgnoreCase("Master")) {
                        Intent intent = new Intent(activity, ViewDetailElementActivity.class);
                        System.out.println("holder postion: " + position);
                        intent.putExtra("element", element);
                        intent.putExtra("User",activity.getUser());
                        activity.startActivityForResult(intent, 100);
                    }
                    else{
                    */
                        Intent intent = new Intent(activity, BlocklyActivity.class);
                        intent.putExtra("element", element);
                        activity.startActivity(intent);
                    //}
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
        holder.txtFunction.setText(elements.get(position).getId());
        holder.txtName.setText(elements.get(position).getName());
        holder.position=position;

    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    public void setActivity(ViewElementActivity activity) {
        this.activity = activity;
    }

}
