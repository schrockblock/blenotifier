package com.rndapp.blenotifier.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rndapp.blenotifier.R;
import com.rndapp.blenotifier.adapters.AppListAdapter;
import com.rndapp.blenotifier.database.RuleDataSource;
import com.rndapp.blenotifier.models.Rule;

import java.util.List;

/**
 * Created by ell on 4/2/15.
 */
public class EditNotificationActivity extends ActionBarActivity implements View.OnClickListener{
    public static final int NoIdValue = -1;
    public static final String RuleIdKey = "rule_id";
    public Rule mRule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notification);

        int ruleId = getIntent().getExtras().getInt(RuleIdKey);

        if (ruleId != NoIdValue){
            RuleDataSource dataSource = new RuleDataSource(this);
            dataSource.open();
            mRule = dataSource.getRule(ruleId);
            dataSource.close();

            View colorView = findViewById(R.id.v_color);
            colorView.setBackgroundColor(mRule.getColor(this));

            PackageManager pm = this.getPackageManager();
            List<ApplicationInfo> applications = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            for (ApplicationInfo info : applications){
                if (info.packageName.equals(mRule.getPackageName())){
                    setApp(info);
                }
            }
        }else {
            mRule = new Rule();
        }

        setButtonOnClickListeners();
    }

    protected void setButtonOnClickListeners(){
        findViewById(R.id.iv_app_icon).setOnClickListener(this);

        findViewById(R.id.btn_blue).setOnClickListener(this);
        findViewById(R.id.btn_brown).setOnClickListener(this);
        findViewById(R.id.btn_green).setOnClickListener(this);
        findViewById(R.id.btn_orange).setOnClickListener(this);
        findViewById(R.id.btn_pink).setOnClickListener(this);
        findViewById(R.id.btn_purple).setOnClickListener(this);
        findViewById(R.id.btn_red).setOnClickListener(this);
        findViewById(R.id.btn_yellow).setOnClickListener(this);

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_app_icon:
                chooseApp();
                break;

            case R.id.btn_save:
                save();
            case R.id.btn_cancel:
                finish();
                break;

            case R.id.btn_blue:
                setColor(Rule.BLUE);
                break;
            case R.id.btn_green:
                setColor(Rule.GREEN);
                break;
            case R.id.btn_orange:
                setColor(Rule.ORANGE);
                break;
            case R.id.btn_purple:
                setColor(Rule.PURPLE);
                break;
            case R.id.btn_red:
                setColor(Rule.RED);
                break;
            case R.id.btn_yellow:
                setColor(Rule.YELLOW);
                break;
            case R.id.btn_brown:
                setColor(Rule.BROWN);
                break;
            case R.id.btn_pink:
                setColor(Rule.PINK);
                break;
        }
    }

    protected void chooseApp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("Choose an App");
        builder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        final AppListAdapter adapter = new AppListAdapter(this);
        builder.setAdapter(adapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ApplicationInfo app = adapter.getItem(which);
                        setApp(app);
                    }
                });
        builder.show();
    }

    protected void save(){
        RuleDataSource dataSource = new RuleDataSource(this);
        dataSource.open();
        if (mRule.getId() != 0){
            dataSource.updateRule(mRule);
        }else {
            dataSource.createRule(mRule.getAppName(), mRule.getPackageName(), mRule.getColorEnum());
        }
        dataSource.close();
    }

    protected void setColor(int ruleColor){
        mRule.setColor(ruleColor);
        findViewById(R.id.v_color).setBackgroundColor(mRule.getColor(this));
    }

    protected void setApp(ApplicationInfo info){
        if (info != null){
            PackageManager pm = this.getPackageManager();

            ImageView appIcon = (ImageView)findViewById(R.id.iv_app_icon);
            appIcon.setImageDrawable(pm.getApplicationIcon(info));

            String appLabel = pm.getApplicationLabel(info).toString();

            TextView name = (TextView)findViewById(R.id.tv_app_name);
            name.setText(appLabel);

            mRule.setAppName(appLabel);
            mRule.setPackageName(info.packageName);
        }
    }
}
