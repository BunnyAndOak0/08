package edu.kmust.bully.publish.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import edu.kmust.bully.common.model.Model;
import edu.kmust.bully.common.dto.ModelDTO;
import edu.kmust.bully.common.vo.ModelListItemVO;

import java.util.List;

/**
 * <p>
 * 公开动态的数据存储 服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-08-06
 */

public interface IModelService extends IService<Model> {

    void publicModel(ModelDTO modelDTO, Long userId, String username);


    void privateModel(ModelDTO modelDTO, Long userId);

    void soulModel(ModelDTO modelDTO, Long userId, String username,Integer role);
    /**
     * 获取点击量最多的动态
     * @return 点击量最多的问题列表
     */
    List<Model> getMostLikes();

    /**
     * 获取点击量最多的动态的缓存列表
     * @return 动态缓存列表
     */
    List<Model> getCachedMostLikes();

    /**
     * 查询心灵专区动态列表
     * @return
     */
    PageInfo<Model> getSoulList(Integer page);

    /**
     *查询心灵专区缓存动态列表
     * @return
     */
    List<Model> getCachedSoulList();

    /**
     * 查询不匿名发布的动态
     * @return 非匿名动态列表
     */
    PageInfo<Model> getListByNotIsAnonymousness(Long userId, Integer page);

    /**
     * 获取非匿名发布的动态列表的缓存列表
     * @return
     */
    List<Model> getCachedListByNotIsAnonymousness();

    /**
     * 查询匿名发布的动态
     * @return 匿名动态列表
     */
    PageInfo<Model> getListByIsAnonymousness(Long userId, Integer page);

    /**
     * 获取匿名发布的动态列表的缓存列表
     * @return
     */
    List<Model> getCachedListByIsAnonymousness();

    /**
     * 获取动态详情
     * @param id
     * @return 动态列表详情
     */

    Model getModelById(Integer id);

    /**
     * 删除动态
     * @param modelId
     * @return
     */
    Model delete(Integer modelId, Long userId, Integer role);

    /**
     * 修改动态
     * @param modelId
     * @param userId
     * @param title
     * @param content
     * @return
     */
    Model update(Integer modelId, Long userId, String title, String content);

    /**
     * 更新点赞数量
     * @param modelId
     * @return
     */
    Integer updateLikes(Integer modelId);

    //查询发布过的所有的公开动态
    PageInfo<Model> getPublicAll(Integer page);

    //查询发布过的所有的匿名动态
    PageInfo<Model> getPrivateAll(Integer page);

    //根据id获取用户所有的发布过的公开动态
    List<Model> getPublicById(Integer id);

    Integer updateDislikes(Integer modelId);
}
