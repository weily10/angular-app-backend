package com.backend.controller;

import com.backend.model.Item;
import com.backend.repository.ItemRepo;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/items")
    public Item addItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    @DeleteMapping("items/{uuid}")
    public void deleteItem(@PathVariable String uuid) {
        itemRepository.deleteById(uuid);
    }
}
