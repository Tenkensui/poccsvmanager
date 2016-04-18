package com.moatcrew.dynamicforms.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maruku on 14/04/16.
 */
public class ForeignKey {
    private Table originTable;
    private Table referenceTable;
    private Map<String, Column> columns;

    public ForeignKey(Table originTable, Table referenceTable) {
        this.originTable = originTable;
        this.referenceTable = referenceTable;
    }

    public Table getOriginTable() {
        return originTable;
    }

    public void setOriginTable(Table originTable) {
        this.originTable = originTable;
    }

    public Table getReferenceTable() {
        return referenceTable;
    }

    public void setReferenceTable(Table referenceTable) {
        this.referenceTable = referenceTable;
    }

    public Map<String, Column> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, Column> columns) {
        this.columns = columns;
    }

    public void addColumn(Column column) {
        if (this.columns == null) {
            this.columns = new HashMap<>();
        }
        this.columns.put(column.getName(), column);
    }
}
