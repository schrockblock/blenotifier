package com.rndapp.blenotifier.models;

import android.content.Context;

import com.rndapp.blenotifier.database.RuleDataSource;

import java.util.List;

/**
 * Created by ell on 12/26/14.
 */
public class Matcher {

    public static Rule ruleForPackage(Context context, String packageName){
        Rule matchingRule = null;

        RuleDataSource dataSource = new RuleDataSource(context);
        dataSource.open();
        List<Rule> rules = dataSource.getAllRules();
        dataSource.close();

        for (Rule rule : rules){
            if (rule.isMatchForPackage(packageName)){
                matchingRule = rule;
            }
        }

        return matchingRule;
    }
}
