package com.backend.angular_app_backend.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.backend.Model.Item;
import com.backend.angular_app_backend.Repository.ItemRepo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
