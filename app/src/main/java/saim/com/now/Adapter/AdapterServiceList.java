package saim.com.now.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import saim.com.now.Model.ModelServiceList;
import saim.com.now.R;

/**
 * Created by Android on 8/6/2017.
 */

public class AdapterServiceList extends RecyclerView.Adapter<AdapterServiceList.ServiceListViewHolder>{

    ArrayList<ModelServiceList> adapterList = new ArrayList<>();
    Context mContext;

    public static String post_id = "";

    public AdapterServiceList(ArrayList<ModelServiceList> adapterList) {
        this.adapterList = adapterList;
    }

    public AdapterServiceList(ArrayList<ModelServiceList> adapterList, Context mContext) {
        this.adapterList = adapterList;
        this.mContext = mContext;
    }

    @Override
    public ServiceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_service_list, parent, false);
        ServiceListViewHolder serviceListViewHolder = new ServiceListViewHolder(view);
        return serviceListViewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceListViewHolder holder, int position) {

        Picasso.with(holder.listImageView.getContext())
                .load(adapterList.get(position).getService_icon())
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(holder.listImageView);
        holder.listName.setText(adapterList.get(position).getService_name());

    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public class ServiceListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView listImageView;
        TextView listName;

        public ServiceListViewHolder(View itemView) {
            super(itemView);

            listImageView = (ImageView) itemView.findViewById(R.id.listImageView);
            listName = (TextView) itemView.findViewById(R.id.listName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
