package com.eduardo.moneysaver.core.application.service.tag;

import com.eduardo.moneysaver.common.CurrentUserProvider;
import com.eduardo.moneysaver.core.application.controller.tags.dto.NewTagDto;
import com.eduardo.moneysaver.core.application.controller.tags.dto.TagResp;
import com.eduardo.moneysaver.core.domain.model.Tag;
import com.eduardo.moneysaver.core.domain.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final CurrentUserProvider currentUserProvider;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, CurrentUserProvider currentUserProvider) {
        this.tagRepository = tagRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public List<TagResp> listTags() {
        return this.tagRepository.findAllByUser(this.currentUserProvider.getCurrentUser()).stream()
                .map(TagResp::new)
                .collect(Collectors.toList());
    }

    @Override
    public TagResp createTag(NewTagDto newTagDto) {
        var tag = this.tagRepository.save(Tag.newTag()
                .user(this.currentUserProvider.getCurrentUser())
                .descricao(newTagDto.descricao())
                .build()
        );
        return new TagResp(tag);
    }
}
