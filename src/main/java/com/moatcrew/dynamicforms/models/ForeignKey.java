package com.moatcrew.dynamicforms.models;

/**
 * Created by maruku on 14/04/16.
 */
public class ForeignKey {
    private Table table;
    private Column column;

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }
}
