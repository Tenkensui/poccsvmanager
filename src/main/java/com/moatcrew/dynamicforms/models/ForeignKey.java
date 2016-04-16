package com.moatcrew.dynamicforms.models;

import java.util.List;

/**
 * Created by maruku on 14/04/16.
 */
public class ForeignKey {
    private Table originTable;
    private Table referenceTable;
    private List<Column> columns;

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

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
