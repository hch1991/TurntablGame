package com.hch.gotoplay.adpater;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.hch.gotoplay.ActivityItemObj;
import com.hch.gotoplay.MLog;
import com.hch.gotoplay.R;
import com.hch.gotoplay.base.BaseAda;
import com.hch.gotoplay.interfaces.AddressEditInterface;
import com.hch.gotoplay.interfaces.DeleteItemInterface;
import com.hch.gotoplay.interfaces.EditItemInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingAdapter extends BaseAda<ActivityItemObj> {

    private DeleteItemInterface deleteItemInterface;
    private EditItemInterface editItemInterface;
    private AddressEditInterface addressEditInterface;
    private List<Map<String,Object>> data = new ArrayList<>();//一个集合
    private Context mContext;
    public SettingAdapter(Context context, DeleteItemInterface deleteItemInterface,EditItemInterface editItemInterface,AddressEditInterface addressEditInterface) {
        super(context);
        this.mContext = context;
        this.deleteItemInterface = deleteItemInterface;
        this.editItemInterface = editItemInterface;
        this.addressEditInterface = addressEditInterface;
        getDat();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_setting, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.itemAddressEt = convertView.findViewById(R.id.item_address_et);
            viewHolder.itemSpinner = convertView.findViewById(R.id.item_spinner);
            viewHolder.itemDeleteIv = convertView.findViewById(R.id.item_delete_iv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ActivityItemObj activityItemObj = getItem(position);
        viewHolder.itemAddressEt.setText(activityItemObj.getActivityName());
        viewHolder.itemAddressEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                addressEditInterface.addressEdit(s.toString(),position);
            }
        });
        //第一个参数：上下文，第二个参数：数据源，第三个参数：item子布局，第四、五个参数：键值对，获取item布局中的控件id
        SimpleAdapter s_adapter = new SimpleAdapter(mContext, data, R.layout.item_spinner, new String[]{"image"}, new int[]{R.id.item_img});
        //控件与适配器绑定
        viewHolder.itemSpinner.setAdapter(s_adapter);
        viewHolder.itemSpinner.setSelection(activityItemObj.getActivityType());
        //点击事件
        viewHolder.itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //为TextView控件赋值！在适配器中获取一个值赋给tv
                editItemInterface.edit(i,position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        viewHolder.itemDeleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItemInterface.delete(position);
            }
        });
        return convertView;
    }

    //数据源
    private List<Map<String,Object>> getDat() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map4 = new HashMap<>();
        map4.put("image", R.mipmap.no_have);
        data.add(map4);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("image", R.mipmap.park);
        data.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("image", R.mipmap.mountain);
        data.add(map3);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("image", R.mipmap.shop);
        data.add(map1);
        map.put("image", R.mipmap.home);
        data.add(map);
        return data;
    }

    static class ViewHolder {
        EditText itemAddressEt;
        Spinner itemSpinner;
        ImageView itemDeleteIv;
    }
}
