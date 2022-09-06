package com.zzx.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.TagListDto;
import com.zzx.domain.entity.Tag;
import com.zzx.domain.vo.PageVo;

/**
 * 标签(Tag)表服务接口
 *
 * @author 那个小楠瓜
 * @since 2022-08-22 16:29:02
 */
public interface TagService extends IService<Tag> {

    ResponseResult selectTagPage(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagListDto tagListDto);

    ResponseResult updateTag(TagListDto tagListDto);

    ResponseResult deleteTag(Long tagId);

    ResponseResult selectTagById(Long tagId);

    ResponseResult getTagList();

}

