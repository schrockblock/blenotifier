package com.rndapp.blenotifier.activities;

import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.rndapp.blenotifier.R;
import com.rndapp.blenotifier.adapters.NotificationListAdapter;
import com.rndapp.blenotifier.database.RuleDataSource;
import com.rndapp.blenotifier.models.Rule;

import java.util.List;

public class NotificationListActivity extends ActionBarActivity {
    protected List<Rule> mRules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        RuleDataSource dataSource = new RuleDataSource(this);
        dataSource.open();

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        if (!preferences.getBoolean("populated", false)){

            PackageManager packageManager = getPackageManager();
            List<ApplicationInfo> apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
            for (ApplicationInfo app : apps){
                String appName = packageManager.getApplicationLabel(app).toString();
                if (appName.equals("Gmail")){
                    dataSource.createRule(appName, app.packageName, Rule.RED);
                }else if (appName.equals("Hangouts")){
                    dataSource.createRule(appName, app.packageName, Rule.GREEN);
                }else if (appName.equals("Facebook")){
                    dataSource.createRule(appName, app.packageName, Rule.BLUE);
                }else if (appName.equals("Candle")){
                    dataSource.createRule(appName, app.packageName, Rule.PURPLE);
                }else if (appName.equals("Snapchat")){
                    dataSource.createRule(appName, app.packageName, Rule.YELLOW);
                }else if (appName.equals("Instagram")){
                    dataSource.createRule(appName, app.packageName, Rule.BROWN);
                }
            }

            preferences.edit().putBoolean("populated",true).apply();
        }

        mRules = dataSource.getAllRules();
        dataSource.close();

        NotificationListAdapter adapter = new NotificationListAdapter(this, mRules);

        ((ListView)findViewById(R.id.lv_notification)).setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_add:
                //TODO: add item
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
