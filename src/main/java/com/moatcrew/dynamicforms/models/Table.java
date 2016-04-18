package com.moatcrew.dynamicforms.models;

import java.util.*;

/**
 * Created by maruku on 14/04/16.
 */
public class Table {

    private String name;
    private PrimaryKey primaryKey;
    private Map<String, ForeignKey> foreignKeys;
    private LinkedHashMap<String, Column> columns;

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

    public Column getColumn(String columnName) {
        return columns.get(columnName);
    }

    public LinkedHashMap<String, Column> getColumns() {
        return columns;
    }

    public void setColumns(LinkedHashMap<String, Column> columns) {
        this.columns = columns;
    }

    public PrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(PrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Map<String, ForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(Map<String, ForeignKey> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    public void addForeignKey(ForeignKey foreignKey) {
        if (foreignKeys == null) {
            foreignKeys = new HashMap<>();
        }
        foreignKeys.put(foreignKey.getOriginTable().getName() + foreignKey.getReferenceTable().getName(), foreignKey);
    }

    public void addColumn(Column column) {
        if (columns == null) {
            columns = new LinkedHashMap<>();
        }
        columns.put(column.getName(), column);
    }
}
