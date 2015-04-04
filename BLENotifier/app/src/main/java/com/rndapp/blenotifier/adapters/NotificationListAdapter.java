package com.rndapp.blenotifier.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rndapp.blenotifier.R;
import com.rndapp.blenotifier.models.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ell on 12/28/14.
 */
public class NotificationListAdapter extends BaseAdapter {
    private List<Rule> mRules = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflator;

    public NotificationListAdapter(Context context, List<Rule> mRules) {
        mContext = context;
        mLayoutInflator = LayoutInflater.from(context);
        this.mRules = mRules;
    }

    @Override
    public int getCount() {
        return mRules.size();
    }

    @Override
    public Rule getItem(int position) {
        return mRules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mLayoutInflator.inflate(R.layout.notification_item, null);
        }

        Rule rule = getItem(position);
        ((TextView)convertView.findViewById(R.id.tv_app_name)).setText(rule.getAppName());
        ((TextView)convertView.findViewById(R.id.tv_app_package)).setText(rule.getPackageName());
        convertView.findViewById(R.id.v_color).setBackgroundColor(rule.getColor(mContext));

        return convertView;
    }
}
