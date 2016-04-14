package com.moatcrew.dynamicforms.models;

/**
 * Created by maruku on 14/04/16.
 */
public class Column {

    private String name;
    private String type;
    private Integer length;
    private Integer scale;
    private Boolean primaryKey;
    private ForeignKey foreignKey;
    private Boolean allowsNull;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Boolean getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public ForeignKey getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(ForeignKey foreignKey) {
        this.foreignKey = foreignKey;
    }

    public Boolean getAllowsNull() {
        return allowsNull;
    }

    public void setAllowsNull(Boolean allowsNull) {
        this.allowsNull = allowsNull;
    }
}
