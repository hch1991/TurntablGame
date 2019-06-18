package com.hch.gotoplay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.hch.gotoplay.adpater.SettingAdapter;
import com.hch.gotoplay.base.BaseActivity;
import com.hch.gotoplay.interfaces.AddressEditInterface;
import com.hch.gotoplay.interfaces.DeleteItemInterface;
import com.hch.gotoplay.interfaces.EditItemInterface;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity  {

    ListView itemLv;
    Button addOneBt;
    Button settingBackBt;
    private ArrayList<ActivityItemObj> activityItemObjs;
    private SettingAdapter settingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initData();
        initListener();
    }

    private void initView(){
        itemLv = (ListView)findViewById(R.id.item_lv);
        addOneBt = (Button) findViewById(R.id.add_one_bt);
        settingBackBt = (Button) findViewById(R.id.setting_back_bt);
    }

    private void initData(){
        activityItemObjs = getIntent().getParcelableArrayListExtra("activityList");
        settingAdapter = new SettingAdapter(this, new DeleteItemInterface() {
            @Override
            public void delete(int position) {
                activityItemObjs.remove(position);
                settingAdapter.setGroup(activityItemObjs);
            }
        }, new EditItemInterface() {
            @Override
            public void edit(int type, int position) {
                activityItemObjs.get(position).setActivityType(type);
            }
        }, new AddressEditInterface() {
            @Override
            public void addressEdit(String content, int position) {
                activityItemObjs.get(position).setActivityName(content);
            }
        });
        settingAdapter.setGroup(activityItemObjs);
        itemLv.setAdapter(settingAdapter);
    }
    private void initListener(){
        addOneBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityItemObj activityItemObj = new ActivityItemObj("", 0);
                activityItemObjs.add(activityItemObj);
                settingAdapter.setGroup(activityItemObjs);
            }
        });
        settingBackBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activityItemObjs.size()<=0){
                    showToastMsg("不能没有去的地方");
                    SettingActivity.this.finish();
                }else{
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra("activitys",activityItemObjs);
                    setResult(200,intent);
                    SettingActivity.this.finish();
                }
            }
        });
    }
}
