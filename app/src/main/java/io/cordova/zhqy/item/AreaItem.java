package io.cordova.zhqy.item;

import android.support.annotation.NonNull;


import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.item.TreeItem;

import io.cordova.zhqy.R;
import io.cordova.zhqy.bean.ProvinceBean;


/**
 */
public class AreaItem extends TreeItem<ProvinceBean.CityBean.AreasBean> {

    @Override
    public int getLayoutId() {
        return R.layout.item_three;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder) {
        holder.setText(R.id.tv_content, data.getAreaName());
    }

    @Override
    public int getSpanSize(int maxSpan) {
        return maxSpan / 3;
    }
}
