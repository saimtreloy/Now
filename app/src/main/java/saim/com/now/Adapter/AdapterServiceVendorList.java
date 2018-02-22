package saim.com.now.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import saim.com.now.Model.ModelShopMenu;
import saim.com.now.Model.ModelShopVendor;
import saim.com.now.R;
import saim.com.now.Shop.ShopCategory;
import saim.com.now.Shop.ShopHome;
import saim.com.now.Shop.ShopItemList;

/**
 * Created by Android on 8/6/2017.
 */

public class AdapterServiceVendorList extends RecyclerView.Adapter<AdapterServiceVendorList.ServiceListViewHolder>{

    ArrayList<ModelShopVendor> adapterList = new ArrayList<>();
    Context mContext;

    public static String post_id = "";

    public AdapterServiceVendorList(ArrayList<ModelShopVendor> adapterList) {
        this.adapterList = adapterList;
    }

    public AdapterServiceVendorList(ArrayList<ModelShopVendor> adapterList, Context mContext) {
        this.adapterList = adapterList;
        this.mContext = mContext;
    }

    @Override
    public ServiceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_vendor_list, parent, false);
        ServiceListViewHolder serviceListViewHolder = new ServiceListViewHolder(view);
        return serviceListViewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceListViewHolder holder, int position) {

        Picasso.with(holder.listImageView.getContext())
                .load(adapterList.get(position).getService_shop_vendor_icon())
                .placeholder(R.drawable.ic_placeholder_icon)
                .error(R.drawable.ic_placeholder_icon)
                .into(holder.listImageView);
        holder.listName.setText(adapterList.get(position).getService_shop_vendor_name());

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
            Intent intent = new Intent(v.getContext(), ShopHome.class);
            intent.putExtra("VENDOR_ID", adapterList.get(getAdapterPosition()).getService_shop_vendor_id());
            v.getContext().startActivity(intent);
        }
    }
}
