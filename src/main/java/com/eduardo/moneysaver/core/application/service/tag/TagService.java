package com.eduardo.moneysaver.core.application.service.tag;

import com.eduardo.moneysaver.core.application.controller.tags.dto.NewTagDto;
import com.eduardo.moneysaver.core.application.controller.tags.dto.TagResp;

import java.util.List;

public interface TagService {
    List<TagResp> listTags();

    TagResp createTag(NewTagDto newTagDto);
}
