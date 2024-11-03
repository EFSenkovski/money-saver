package com.eduardo.moneysaver.core.application.controller.tags;

import com.eduardo.moneysaver.core.application.controller.tags.dto.NewTagDto;
import com.eduardo.moneysaver.core.application.controller.tags.dto.TagResp;
import com.eduardo.moneysaver.core.application.service.tag.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagsController {

    private final TagService tagService;

    @Autowired
    public TagsController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagResp>> listTags() {
        return ResponseEntity.ok(this.tagService.listTags());
    }

    @PostMapping
    public ResponseEntity<TagResp> createTag(@RequestBody @Valid NewTagDto newTagDto, UriComponentsBuilder builder) {
        var tag = this.tagService.createTag(newTagDto);
        var uri = builder.path("/tags/{id}").buildAndExpand(tag.id()).toUri();
        return ResponseEntity.created(uri).body(tag);
    }
}
