package com.hch.gotoplay;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hch.myutils.utils.SimpleSharedPreferences;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ActivityListObj activityListObj;
    private ArrayList<ActivityItemObj> activityItemObjs;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityListObj = new ActivityListObj();
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);
        gson = builder.create();
        String activitysJson = SimpleSharedPreferences.getString("activitys",this);
        if(activitysJson.equals("")){
            activityItemObjs = new ArrayList<>();
            activityItemObjs.add(new ActivityItemObj("笔架山公园",1));
            activityItemObjs.add(new ActivityItemObj("仙湖植物园",1));
            activityItemObjs.add(new ActivityItemObj("野生动物园",1));
            activityItemObjs.add(new ActivityItemObj("凤凰山",2));
            activityItemObjs.add(new ActivityItemObj("西乡天虹",3));
            activityItemObjs.add(new ActivityItemObj("在家呆着",4));
            activityListObj.setActivityItemObjs(activityItemObjs);
            SimpleSharedPreferences.putString("activitys",gson.toJson(activityListObj),MainActivity.this);
        }else{
            activityListObj = gson.fromJson(activitysJson,ActivityListObj.class) ;
            activityItemObjs = activityListObj.getActivityItemObjs();
        }
        findViewById(R.id.setting_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                intent.putParcelableArrayListExtra("activityList",activityItemObjs);
                startActivityForResult(intent,200);
            }
        });
        //文字
        creatWheel(activityItemObjs);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 200){
            activityItemObjs = data.getParcelableArrayListExtra("activitys");
            activityListObj.setActivityItemObjs(activityItemObjs);
            SimpleSharedPreferences.putString("activitys",gson.toJson(activityListObj),MainActivity.this);
            creatWheel(activityItemObjs);
        }
    }

    private void  creatWheel(ArrayList<ActivityItemObj> items){
        Integer[] colors = new Integer[items.size()];
        String[] des = new String[items.size()];
        //图标
        List<Bitmap> mListBitmap = new ArrayList<>();
        for(int i= 0; i<items.size(); i++ ){
            des[i] = items.get(i).getActivityName();
            switch (items.get(i).getActivityType()){
                case 0:
                    mListBitmap.add(BitmapFactory.decodeResource(getResources(), R.mipmap.transparent));
                    break;
                case 1:
                    mListBitmap.add(BitmapFactory.decodeResource(getResources(), R.mipmap.park));
                    break;
                case 2:
                    mListBitmap.add(BitmapFactory.decodeResource(getResources(), R.mipmap.mountain));
                    break;
                case 3:
                    mListBitmap.add(BitmapFactory.decodeResource(getResources(), R.mipmap.shop));
                    break;
                case 4:
                    mListBitmap.add(BitmapFactory.decodeResource(getResources(), R.mipmap.home));
                    break;
            }
            if(i == 0){
                colors[0] = Color.parseColor("#fef9f7");
            }else{
                if(i % 2 == 1){
                    colors[i] =Color.parseColor("#fbc6a9");
                }else{
                    colors[i] =Color.parseColor("#ffdecc");
                }
            }
        }
        MLog.d("des.length:"+items.size());
        MLog.d("colors.length:"+colors.length);
        //主动旋转一下图片
        mListBitmap = WheelSurfView.rotateBitmaps(mListBitmap);

        //获取第三个视图
        final WheelSurfView wheelSurfView2 = findViewById(R.id.wheelSurfView2);
        WheelSurfView.Builder build = new WheelSurfView.Builder()
                .setmColors(colors)
                .setmDeses(des)
                .setmIcons(mListBitmap)
                .setmType(1)
                .setmTypeNum(items.size())
                .setmMinTimes(10)
                .setmVarTime(150)
                .build();
        wheelSurfView2.setConfig(build);

        //添加滚动监听
        wheelSurfView2.setRotateListener(new RotateListener() {
            @Override
            public void rotateEnd(int position, String des) {
                Toast.makeText(MainActivity.this, "恭喜你！抽中了：" + des, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rotating(ValueAnimator valueAnimator) {

            }

            @Override
            public void rotateBefore(ImageView goImg) {
                int position = new Random().nextInt(8) + 1;
                wheelSurfView2.startRotate(position);
            }
        });
    }
}
