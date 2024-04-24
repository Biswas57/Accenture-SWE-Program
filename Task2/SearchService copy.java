package com.mockcompany.webapp.service;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SearchService {

    private final ProductItemRepository productItemRepository;

    @Autowired
    public SearchService(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    public Collection<ProductItem> search(String query) {
        Iterable<ProductItem> allItems = this.productItemRepository.findAll();
        List<ProductItem> itemList = new ArrayList<>();
        boolean exactMatch = query.startsWith("\"") && query.endsWith("\"");

        if (exactMatch) {
            query = query.substring(1, query.length() - 1);
        }
        String finalQuery = query.toLowerCase();

        for (ProductItem item : allItems) {
            String name = item.getName().toLowerCase();
            String description = item.getDescription().toLowerCase();

            if (exactMatch) {
                if (name.equals(finalQuery) || description.equals(finalQuery)) {
                    itemList.add(item);
                }
            } else {
                if (name.contains(finalQuery) || description.contains(finalQuery)) {
                    itemList.add(item);
                }
            }
        }
        return itemList;
    }
}
