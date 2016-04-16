package com.moatcrew.dynamicforms.models;

import java.util.List;

/**
 * Created by maruku on 16/04/16.
 */
public class PrimaryKey {

    private Table table;
    private List<Column> columns;

    public PrimaryKey(Table table, List<Column> columns) {
        this.table = table;
        this.columns = columns;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
