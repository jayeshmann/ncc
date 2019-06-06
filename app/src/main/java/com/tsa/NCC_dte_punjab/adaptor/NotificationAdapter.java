package com.tsa.NCC_dte_punjab.adaptor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsa.NCC_dte_punjab.R;
import com.tsa.NCC_dte_punjab.models.NotificationModel;

import java.util.ArrayList;

/**
 * Created by Akhil Tripathi on 24-01-2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private ArrayList<NotificationModel> notificationModels;
    private Context context;

    public NotificationAdapter(ArrayList<NotificationModel> notificationModels, Context context) {
        this.notificationModels = notificationModels;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.e("NOTIFICATION", notificationModels.toString());

        final NotificationModel notificationModel = notificationModels.get(position);
        holder.title.setText(notificationModel.getmName());
        holder.date.setText(notificationModel.getmDate());
        holder.des.setText(notificationModel.getmDes());

        holder.mainRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(notificationModel.getmLink()));
                context.startActivity(viewIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView delete;

        TextView title;
        TextView date;
        TextView des;
        TextView link;

        LinearLayout mainRoot;

        public ViewHolder(View view) {
            super(view);
            delete = view.findViewById(R.id.delete);
            title = view.findViewById(R.id.title);
            date = view.findViewById(R.id.date);
            des = view.findViewById(R.id.des);
            mainRoot=view.findViewById(R.id.main_root);
            link=view.findViewById(R.id.link);
        }
    }
}
