package com.tscore.dto;

import java.util.List;

public class OrderRequest {
    public String userId;
    public List<Item> items;

    public static class Item {
        public Long productId;
        public int quantity;
    }
}
