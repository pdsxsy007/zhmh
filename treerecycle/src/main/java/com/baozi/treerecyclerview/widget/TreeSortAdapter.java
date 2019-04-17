package com.baozi.treerecyclerview.widget;

import com.baozi.treerecyclerview.adpater.TreeRecyclerAdapter;
import com.baozi.treerecyclerview.adpater.TreeRecyclerType;
import com.baozi.treerecyclerview.base.BaseRecyclerAdapter;
import com.baozi.treerecyclerview.item.TreeItem;
import com.baozi.treerecyclerview.item.TreeSortItem;
import com.baozi.treerecyclerview.manager.ItemManager;

import java.util.HashMap;
import java.util.List;


/**
 * Created by Administrator on 2017/8/8 0008.
 * 索引adapter
 */

public class TreeSortAdapter extends TreeRecyclerAdapter {
    private final HashMap<Object, TreeItem> sortMap = new HashMap<>();
    private TreeSortManageWrapper manageWrapper;

    public TreeSortAdapter() {

    }

    public TreeSortAdapter(TreeRecyclerType treeRecyclerType) {
        super(treeRecyclerType);
    }

    @Override
    public void setDatas(List<TreeItem> datas) {
        super.setDatas(datas);
        getItemManager().updateSorts(getDatas());
    }


    public TreeItem getSortItem(Object o) {
        if (o == null) {
            return null;
        }
        return sortMap.get(o);
    }

    public int getSortIndex(Object o) {
        TreeItem sortItem = getSortItem(o);
        if (sortItem == null) {
            return -1;
        }
        return getItemManager().getItemPosition(sortItem);
    }

    @Override
    public TreeSortManageWrapper getItemManager() {
        if (manageWrapper == null) {
            manageWrapper = new TreeSortManageWrapper(this, super.getItemManager());
        }
        return manageWrapper;
    }

    public class TreeSortManageWrapper extends ItemManager<TreeItem> {
        ItemManager<TreeItem> manager;

        public TreeSortManageWrapper(BaseRecyclerAdapter adapter, ItemManager<TreeItem> manager) {
            super(adapter);
            this.manager = manager;
        }

        @Override
        public void setOpenAnim(boolean openAnim) {
            manager.setOpenAnim(openAnim);
        }

        @Override
        public boolean isOpenAnim() {
            return manager.isOpenAnim();
        }

        @Override
        public void addItem(TreeItem treeItem) {
            manager.addItem(treeItem);
            updateSort(manager.getItemPosition(treeItem), treeItem);
        }


        @Override
        public void addItem(int i, TreeItem treeItem) {
            manager.addItem(i, treeItem);
            updateSort(i, treeItem);
        }

        @Override
        public void addItems(List<TreeItem> list) {
            manager.addItems(list);
            updateSorts(list);
        }

        @Override
        public void addItems(int i, List<TreeItem> list) {
            manager.addItems(i, list);
            updateSorts(list);
        }

        @Override
        public void setAdapter(BaseRecyclerAdapter<TreeItem> adapter) {
            manager.setAdapter(adapter);
        }

        @Override
        public BaseRecyclerAdapter getAdapter() {
            return manager.getAdapter();
        }

        @Override
        public void removeItem(TreeItem treeItem) {
            manager.removeItem(treeItem);
            updateSort(getItemPosition(treeItem), treeItem);
        }

        @Override
        public void removeItem(int i) {
            TreeItem item = manager.getItem(i);
            if (item instanceof TreeSortItem) {
                sortMap.remove(((TreeSortItem) item).getSortKey());
            }
            manager.removeItem(i);
        }

        @Override
        public void removeItems(List<TreeItem> list) {
            manager.removeItems(list);
            int size = list.size();
            for (int i = 0; i < size; i++) {
                TreeItem item = list.get(i);
                if (item instanceof TreeSortItem) {
                    sortMap.remove(((TreeSortItem) item).getSortKey());
                }
            }
        }


        @Override
        public void replaceItem(int i, TreeItem treeItem) {
            manager.replaceItem(i, treeItem);
            updateSort(i, treeItem);
        }

        @Override
        public void replaceAllItem(List<TreeItem> list) {
            manager.replaceAllItem(list);
            updateSorts(list);
        }

        @Override
        public TreeItem getItem(int i) {
            return manager.getItem(i);
        }

        @Override
        public int getItemPosition(TreeItem treeItem) {
            return manager.getItemPosition(treeItem);
        }

        @Override
        public void clean() {
            manager.clean();
        }

        public void updateSorts(List<TreeItem> treeItems) {
            int size = treeItems.size();
            for (int i = 0; i < size; i++) {
                TreeItem treeItem = treeItems.get(i);
                if (treeItem instanceof TreeSortItem) {
                    sortMap.put(((TreeSortItem) treeItem).getSortKey(), treeItem);
                }
            }
        }

        public void updateSort(TreeItem treeItem) {
            if (treeItem instanceof TreeSortItem) {
                sortMap.put(((TreeSortItem) treeItem).getSortKey(), treeItem);
            }
        }

        public void updateSort(int position, TreeItem treeItem) {
            if (treeItem instanceof TreeSortItem) {
                sortMap.put(((TreeSortItem) treeItem).getSortKey(), treeItem);
            }
        }

        @Override
        public int dataToItemPosition(int index) {
            return manager.dataToItemPosition(index);
        }

        @Override
        public int itemToDataPosition(int position) {
            return manager.itemToDataPosition(position);
        }

        @Override
        public void notifyDataChanged() {
            manager.notifyDataChanged();
        }

        @Override
        public void addCheckItemInterfaces(CheckItemInterface itemInterface) {
            manager.addCheckItemInterfaces(itemInterface);
        }

        @Override
        public void removeCheckItemInterfaces(CheckItemInterface itemInterface) {
            manager.removeCheckItemInterfaces(itemInterface);
        }
    }
}
