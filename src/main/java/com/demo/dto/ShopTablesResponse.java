package com.demo.dto;

import com.demo.wrapper.TableLoginWrapper;

import java.util.List;

public class ShopTablesResponse {
    private String shopName;
    private List<TableLoginWrapper> tables;


    public ShopTablesResponse(String shopName, List<TableLoginWrapper> tables) {
        this.shopName = shopName;
        this.tables = tables;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<TableLoginWrapper> getTables() {
        return tables;
    }

    public void setTables(List<TableLoginWrapper> tables) {
        this.tables = tables;
    }
}
