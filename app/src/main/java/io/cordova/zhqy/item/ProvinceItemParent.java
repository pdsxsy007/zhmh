package io.cordova.zhqy.item;

import android.support.annotation.NonNull;


import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.baozi.treerecyclerview.item.TreeItem;
import com.baozi.treerecyclerview.item.TreeItemGroup;
import com.baozi.treerecyclerview.manager.ItemManager;

import java.util.List;

import io.cordova.zhqy.R;
import io.cordova.zhqy.bean.ProvinceBean;

/**
 * Created by baozi on 2016/12/8.
 */
public class ProvinceItemParent extends TreeItemGroup<ProvinceBean> {


    @Override
    public List<TreeItem> initChildList(ProvinceBean data) {
        return ItemHelperFactory.createTreeItemList(data.getCitys(),CountyItemParent.class, this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.itme_one;
    }

    @Override
    protected void onExpand() {
        ItemManager itemManager = getItemManager();
        if (itemManager != null) {
            int itemPosition = itemManager.getItemPosition(this);
            List datas = itemManager.getAdapter().getDatas();
            datas.addAll(itemPosition + 1, getExpandChild());
            itemManager.getAdapter().notifyItemRangeInserted(itemPosition + 1, getExpandChild().size());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder) {
        holder.setText(R.id.tv_content, data.getProvinceName());
    }

}
