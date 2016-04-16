package com.moatcrew.dynamicforms.models;

/**
 * Created by maruku on 14/04/16.
 */
public class ForeignKey {
    private Table table;
    private ForeignKey foreignKey;

    public ForeignKey(Table table, ForeignKey foreignKey) {
        this.table = table;
        this.foreignKey = foreignKey;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public ForeignKey getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(ForeignKey foreignKey) {
        this.foreignKey = foreignKey;
    }
}
