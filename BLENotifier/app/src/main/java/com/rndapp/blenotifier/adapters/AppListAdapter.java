package com.rndapp.blenotifier.adapters;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rndapp.blenotifier.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ell on 4/4/15.
 */
public class AppListAdapter extends BaseAdapter {
    private List<ApplicationInfo> mApps = new ArrayList<>();
    private PackageManager mPackageManager;
    private LayoutInflater mLayoutInflator;

    public AppListAdapter(Context context) {
        mPackageManager = context.getPackageManager();
        mLayoutInflator = LayoutInflater.from(context);
        this.mApps = mPackageManager.getInstalledApplications(PackageManager.GET_META_DATA);
    }

    @Override
    public int getCount() {
        return mApps.size();
    }

    @Override
    public ApplicationInfo getItem(int position) {
        return mApps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mLayoutInflator.inflate(R.layout.app_item, parent, false);
        }

        ApplicationInfo app = getItem(position);
        ((TextView)convertView.findViewById(R.id.tv_app_name)).setText(mPackageManager.getApplicationLabel(app).toString());
        ((TextView)convertView.findViewById(R.id.tv_app_package)).setText(app.packageName);
        ((ImageView)convertView.findViewById(R.id.iv_app_icon)).setImageDrawable(mPackageManager.getApplicationIcon(app));

        return convertView;
    }
}
