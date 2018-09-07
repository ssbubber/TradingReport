/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sarabjeet.task;

import java.math.BigDecimal;

/**
 *
 * @author Sarabjeet_Singh
 */
public class ReportLine {
    
    private String clientInformation;

    private String productInformation;

    private BigDecimal totalTransactionAmount;

    /**
     * Get the value of totalTransactionAmount
     *
     * @return the value of totalTransactionAmount
     */
    public BigDecimal getTotalTransactionAmount() {
        return totalTransactionAmount;
    }

    /**
     * Set the value of totalTransactionAmount
     *
     * @param totalTransactionAmount new value of totalTransactionAmount
     */
    public void setTotalTransactionAmount(BigDecimal totalTransactionAmount) {
        this.totalTransactionAmount = totalTransactionAmount;
    }

    /**
     * Get the value of productInformation
     *
     * @return the value of productInformation
     */
    public String getProductInformation() {
        return productInformation;
    }

    /**
     * Set the value of productInformation
     *
     * @param productInformation new value of productInformation
     */
    public void setProductInformation(String productInformation) {
        this.productInformation = productInformation;
    }

    /**
     * Get the value of clientInformation
     *
     * @return the value of clientInformation
     */
    public String getClientInformation() {
        return clientInformation;
    }

    /**
     * Set the value of clientInformation
     *
     * @param clientInformation new value of clientInformation
     */
    public void setClientInformation(String clientInformation) {
        this.clientInformation = clientInformation;
    }

}
