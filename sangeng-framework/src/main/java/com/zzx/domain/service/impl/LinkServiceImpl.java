package com.zzx.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.constants.SystemConstants;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.LinkListDto;
import com.zzx.domain.dto.LinkStatusDto;
import com.zzx.domain.entity.Link;
import com.zzx.domain.mapper.LinkMapper;
import com.zzx.domain.service.LinkService;
import com.zzx.domain.vo.LinkVo;
import com.zzx.domain.vo.PageVo;
import com.zzx.enums.AppHttpCodeEnum;
import com.zzx.exception.SystemException;
import com.zzx.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(SgLink)表服务实现类
 *
 * @author makejava
 * @since 2022-02-24 14:28:26
 */
@Service("LinkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getLinkList() {
        //查询审核通过的友链
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        //封装vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //封装返回
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult getLinkDetail(Long linkId) {
        Link link = getById(linkId);
        LinkVo linkVo = BeanCopyUtils.copyBean(link, LinkVo.class);
        return ResponseResult.okResult(linkVo);
    }

    @Override
    public ResponseResult getAllLink(Integer pageNum, Integer pageSize, LinkListDto linkListDto) {
        //查询对应的友链
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(linkListDto.getName()), Link::getName, linkListDto.getName());
        queryWrapper.eq(StringUtils.hasText(linkListDto.getStatus()), Link::getStatus, linkListDto.getStatus());
        queryWrapper.eq(Link::getDelFlag, 0);
        //分页查询
        Page<Link> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        //封装 vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(page.getRecords(), LinkVo.class);

        return ResponseResult.okResult(new PageVo(linkVos, page.getTotal()));
    }

    @Override
    @Transactional
    public ResponseResult addLink(LinkListDto linkListDto) {
        Link link = BeanCopyUtils.copyBean(linkListDto, Link.class);
        if (linkNameAndLinkAddressExist(link.getName(), link.getAddress())) {
            throw new SystemException(AppHttpCodeEnum.LINK_EXIST);
        }
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult changeLinkStatus(LinkStatusDto linkStatusDto) {
        Link link = BeanCopyUtils.copyBean(linkStatusDto, Link.class);
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult updateLink(LinkListDto linkListDto) {
        Link link = BeanCopyUtils.copyBean(linkListDto, Link.class);
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult deleteLink(Long linkId) {
        removeById(linkId);
        return ResponseResult.okResult();
    }

    /**
     * 判断友链名称和地址是否存在
     * @param linkName 友链名称
     * @param linkAddress 友链地址
     * @return true or false
     */
    private boolean linkNameAndLinkAddressExist(String linkName, String linkAddress) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Link::getName, linkName)
                .or()
                .eq(Link::getAddress, linkAddress);
        return count(queryWrapper) > 0;
    }
}

