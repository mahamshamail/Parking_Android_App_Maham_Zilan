//Group 10
//101328732 Saiyeda Maham Shamail
//101226318 Zilan Ouyang
package com.maham.parking_android_app_maham_zilan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.maham.parking_android_app_maham_zilan.R;
import com.maham.parking_android_app_maham_zilan.model.UserProfile;

import java.util.ArrayList;

public class UserProfileItemAdapter extends ArrayAdapter<UserProfile> {
    private Context context;
    private int resource;
    public UserProfileItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<UserProfile> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String title = getItem(position).getTitle();
        String content = getItem(position).getContent();

        UserProfile profileItem = new UserProfile(title, content);
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);
        TextView itemTitle = convertView.findViewById(R.id.tvOptions);
        TextView itemContent = convertView.findViewById(R.id.tvContent);
        itemTitle.setText(title);
        itemContent.setText(content);

        return convertView;
    }
}
