package com.zzx.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.domain.entity.ArticleTag;
import com.zzx.domain.mapper.ArticleTagMapper;
import com.zzx.domain.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * (ArticleTag)表服务实现类
 *
 * @author 那个小楠瓜
 * @since 2022-09-01 14:56:14
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

