package com.moatcrew.dynamicforms.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maruku on 14/04/16.
 */
public class Table {

    private String name;
    private PrimaryKey primaryKey;
    private List<ForeignKey> foreignKeys;
    private Map<String, Column> columns;

    public Table() {
    }

    public Table(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Column> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, Column> columns) {
        this.columns = columns;
    }

    public PrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(PrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public List<ForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(List<ForeignKey> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    public void addForeignKey(ForeignKey foreignKey) {
        if (foreignKeys == null) {
            foreignKeys = new ArrayList<ForeignKey>();
        }
        foreignKeys.add(foreignKey);
    }

    public void addColumn(Column column) {
        if (columns == null) {
            columns = new HashMap<String, Column>();
        }
        columns.put(column.getName(), column);
    }
}
