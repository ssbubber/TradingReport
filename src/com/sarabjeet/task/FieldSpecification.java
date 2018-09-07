/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sarabjeet.task;

/**
 *
 * @author Sarabjeet_Singh
 */
public class FieldSpecification {

    private String fieldName;
    private String decimals;
    private String isDate;
    private String startPosition;
    private String endPosition;

    /**
     * Get the value of endPosition
     *
     * @return the value of endPosition
     */
    public String getEndPosition() {
        return endPosition;
    }

    /**
     * Set the value of endPosition
     *
     * @param endPosition new value of endPosition
     */
    public void setEndPosition(String endPosition) {
        this.endPosition = endPosition;
    }


    /**
     * Get the value of startPosition
     *
     * @return the value of startPosition
     */
    public String getStartPosition() {
        return startPosition;
    }

    /**
     * Set the value of startPosition
     *
     * @param startPosition new value of startPosition
     */
    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * Get the value of isDate
     *
     * @return the value of isDate
     */
    public String getIsDate() {
        return isDate;
    }

    /**
     * Set the value of isDate
     *
     * @param isDate new value of isDate
     */
    public void setIsDate(String isDate) {
        this.isDate = isDate;
    }

    /**
     * Get the value of decimals
     *
     * @return the value of decimals
     */
    public String getDecimals() {
        return decimals;
    }

    /**
     * Set the value of decimals
     *
     * @param decimals new value of decimals
     */
    public void setDecimals(String decimals) {
        this.decimals = decimals;
    }

    /**
     * Get the value of string
     *
     * @return the value of string
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Set the value of string
     *
     * @param string new value of string
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

}
