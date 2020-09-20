package edu.kmust.bully.publish.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.kmust.bully.common.model.Model;
import edu.kmust.bully.common.vo.ModelListItemVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 公开动态的数据存储 Mapper 接口
 * </p>
 *
 * @author tedu.cn
 * @since 2020-08-06
 */
@Repository
public interface ModelMapper extends BaseMapper<Model> {
    /**
     * 查询点击量最多的动态列表
     * @return
     */
    List<Model> findMostLikes();

    /**
     * 查询心灵辅导专区的动态
     * @param
     * @return
     */
    List<Model> findSoulList();

    /**
     * 在主页显示非匿名的动态
     * @return 非匿名的动态列表
     */
    List<Model> findListByNotIsAnonymousness(Long userId);

    /**
     * 在树洞显示匿名的动态
     * @return 匿名的动态列表
     */
    List<Model> findListByIsAnonymousness(Long userId);

    /**
     * 通过动态的id查询动态的详情
     * @param id
     * @return  匹配动态的详情
     */
    Model findById(Integer id);

    /**
     * 通过动态id删除动态
     * @param id
     * @return
     */
    Integer deleteById(Integer id);

    /**
     * 点赞功能
     * @param id
     * @return
     */
    Integer updateLikes(Integer id);

    //查询所有的公开动态
    List<Model> getPublicAll();

    //查询所有匿名动态
    List<Model> getPrivateAll();

    //根据id查询用户所有的发布过的正常动态
    public List<Model> selectPublicById(Integer id);

    Integer updateDislikes(Integer modelId);

    Integer findUserIdById(Integer id);
}
