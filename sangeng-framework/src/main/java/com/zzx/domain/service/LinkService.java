package com.zzx.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.dto.LinkListDto;
import com.zzx.domain.dto.LinkStatusDto;
import com.zzx.domain.entity.Link;


/**
 * 友链(SgLink)表服务接口
 *
 * @author 那个小楠瓜
 * @since 2022-02-24 14:28:26
 */
public interface LinkService extends IService<Link> {

    ResponseResult getLinkList();

    ResponseResult getLinkDetail(Long linkId);

    ResponseResult getAllLink(Integer pageNum, Integer pageSize, LinkListDto linkListDto);

    ResponseResult addLink(LinkListDto linkListDto);

    ResponseResult changeLinkStatus(LinkStatusDto linkStatusDto);

    ResponseResult updateLink(LinkListDto linkListDto);

    ResponseResult deleteLink(Long linkId);
}

