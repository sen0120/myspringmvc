package com.hus.biz.elasticsearch;


import java.util.List;
import java.util.Map;

/**
 * Created by fanyun on 17/1/17.
 */
public class EasySearch<DataObject> extends SearchHelper<DataObject> {
    @Override
    public boolean insertOrUpdate(DataObject idAndColumn, boolean nullSet) {
        return super.insertOrUpdate(idAndColumn, nullSet);
    }

    @Override
    public boolean insertOrUpdate(List<DataObject> idAndColumnValueList, boolean nullSet) {
        return super.insertOrUpdate(idAndColumnValueList, nullSet);
    }

    @Override
    public SearchSelectResult select(String keywords, Integer pageNo, Integer pageSize, String... columnNames) {
        return super.select(keywords, pageNo, pageSize, columnNames);
    }

    public Map<String, Object> get(Long id) {
        return super.get(id);
    }

    public CountAndMaxIdResult selectCountAndMaxId() {
        return super.selectCountAndMaxId();
    }

    public boolean exists(Long id) {
        return super.exists(id);
    }

    @Override
    public boolean delete(Long id) {
        return super.delete(id);
    }
}
