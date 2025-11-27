package com.backend.angular_app_backend.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Model.Item;
import com.backend.angular_app_backend.Repository.ItemRepo;

import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
public class ItemsController {

    private final ItemRepo itemRepository;

    public ItemsController(ItemRepo itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/items")
    public List<Item> getItems() {
        return itemRepository.findAll();
    }
}
