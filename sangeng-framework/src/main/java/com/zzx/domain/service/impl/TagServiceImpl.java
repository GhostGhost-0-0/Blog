package com.zzx.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.TagListDto;
import com.zzx.domain.entity.Tag;
import com.zzx.domain.mapper.TagMapper;
import com.zzx.domain.service.TagService;
import com.zzx.domain.vo.PageVo;
import com.zzx.domain.vo.admin.TagVo;
import com.zzx.enums.AppHttpCodeEnum;
import com.zzx.exception.SystemException;
import com.zzx.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author 那个小楠瓜
 * @since 2022-08-22 16:29:03
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult selectTagPage(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        // 查找对应的标签
        wrapper.eq(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName());
        wrapper.eq(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());
        wrapper.eq(Tag::getDelFlag, 0);
        // 分页
        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, wrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(page.getRecords(), TagVo.class);
        return ResponseResult.okResult(new PageVo(tagVos, page.getTotal()));
    }

    @Override
    @Transactional
    public ResponseResult addTag(TagListDto tagListDto) {
        // 对标签名进行非空判断和重复判断
        if (!StringUtils.hasText(tagListDto.getName())) {
            throw new SystemException(AppHttpCodeEnum.TAGNAME_NOT_NULL);
        }
        if (tagNameExist(tagListDto.getName())) {
            throw new SystemException(AppHttpCodeEnum.TAGNAME_EXIST);
        }
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult updateTag(TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult deleteTag(Long tagId) {
        removeById(tagId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectTagById(Long tagId) {
        Tag tag = getById(tagId);
        return ResponseResult.okResult(tag);
    }

    @Override
    public ResponseResult getTagList() {
        List<Tag> tags = list();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tags, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }

    /**
     * 判断标签名是否重复
     * @param tagName 标签名
     * @return true or false
     */
    private boolean tagNameExist(String tagName) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getName, tagName);
        return count(queryWrapper) > 0;
    }

}

