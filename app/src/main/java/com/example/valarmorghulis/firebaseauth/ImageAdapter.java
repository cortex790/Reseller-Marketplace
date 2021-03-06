package com.example.valarmorghulis.firebaseauth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;


    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
        NetworkConnection networkConnection = new NetworkConnection();
        if (networkConnection.isConnectedToInternet(mContext)
                || networkConnection.isConnectedToMobileNetwork(mContext)
                || networkConnection.isConnectedToWifi(mContext)) {

        } else {
            networkConnection.showNoInternetAvailableErrorDialog(mContext);
            return;
        }

    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.new_product_layout, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        holder.textViewPrice.setText("??? " + uploadCurrent.getPrice());
        holder.desc.setText(uploadCurrent.getDesc());
        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
//                .placeholder(R.drawable.loading)
//                .fit()
//                .centerInside()
//                .into(holder.imageView);
                .into(holder.imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        //do something when picture is loaded successfully
                        holder.progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError() {
                        //do something when there is picture loading error
                    }
                });

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuyFragment buyFragment = new BuyFragment();
                Bundle bundle = new Bundle();

               final Upload current = mUploads.get(position);
//                String name = current.getName();
//                bundle.putInt("position", position);
//                bundle.putString("name", name);
//                bundle.putString("price", current.getPrice());

//                if (holder.imageView != null)
//                    bundle.putString("imageUrl", current.getImageUrl());
//                else
//                    bundle.putString("imageUrl", null);
//                bundle.putString("userName", current.getUserName());
//                bundle.putString("date", current.getDate());
//                bundle.putString("desc", current.getDesc());
//                bundle.putString("email", current.getEmail());
//                bundle.putString("key", current.getKey());
                bundle.putSerializable("upload", (Serializable) current);

                    /*if (((BitmapDrawable) imageView.getDrawable()).getBitmap() != null)
                        bundle.putParcelable("bitmapImage", ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                    else
                        bundle.putParcelable("bitmapImage", null);*/
                buyFragment.setArguments(bundle);


                ((FragmentActivity) mContext)
                        .getSupportFragmentManager()
                        .beginTransaction().replace(R.id.frag_container, buyFragment)
                        .addToBackStack(null)
                       .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName,desc;
        public TextView textViewPrice;
        public ImageView imageView;
        public ConstraintLayout ll;
        public ProgressBar progressBar;

        public ImageViewHolder(View itemView) {
            super(itemView);
            desc=itemView.findViewById(R.id.desc);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewPrice = itemView.findViewById(R.id.text_view_price);
            imageView = itemView.findViewById(R.id.image_view_upload);
            ll=itemView.findViewById(R.id.ll);
            progressBar = itemView.findViewById(R.id.p_c);

        }


    }
}
