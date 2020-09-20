package edu.kmust.bully.publish.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.kmust.bully.common.exception.InsertException;
import edu.kmust.bully.common.exception.ModelNotFoundException;
import edu.kmust.bully.common.exception.PermissionDeniedException;
import edu.kmust.bully.common.model.Model;
import edu.kmust.bully.common.dto.ModelDTO;
import edu.kmust.bully.publish.mapper.ModelMapper;
import edu.kmust.bully.publish.schedule.CacheSchedule;
import edu.kmust.bully.publish.service.IModelService;
import edu.kmust.bully.common.vo.ModelListItemVO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 * 公开动态的数据存储 服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-08-06
 */
@Service
public class ModelServiceImpl extends ServiceImpl<ModelMapper, Model> implements IModelService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AmqpTemplate rabbitTemplate;

    /**
     * 公开发布动态
     * @param modelDTO
     * @param userId
     * @param username
     */
    @Override
    public void publicModel(ModelDTO modelDTO, Long userId, String username) {
        // 创建当前时间对象：now
        LocalDateTime now = LocalDateTime.now();
        Model model = new Model()
                .setUserId(userId)
                .setUsername(username)
                .setTitle(modelDTO.getTitle())
                .setContent(modelDTO.getContent())
                .setImage(modelDTO.getImage())
                .setCreateTime(now)
                .setIsPublic(1)
                .setIsAnonymousness(0)
                .setIsSoul(0);

        int rows = modelMapper.insert(model);
        // 判断返回值是否不为1
        if (rows != 1) {
            // 是：抛出InsertException
            throw new InsertException("发布问题失败！当前服务器忙，请稍后再尝试！");
        }
        //获取插入后的自增id
        Integer id = model.getId();
        System.out.println("public中发布时获取的自增id为：" + id);
        rabbitTemplate.convertAndSend("BULLY.SAVE.PUBLIC.EXCHANGE",
                "public.save", id);
        System.out.println("已经加入公开的队列中");
    }

    /**
     * 匿名发布动态
     * @param modelDTO
     * @param userId
     */
    @Override
    public void privateModel(ModelDTO modelDTO, Long userId) {
        // 创建当前时间对象：now
        LocalDateTime now = LocalDateTime.now();
        Random random = new Random();
        String userNo="";
        for(int i=0;i<5;i++) {
            userNo+=String.valueOf(random.nextInt(10));
        }
        Model model = new Model()
                .setUserId(userId)
                .setUsername(userNo+"用户")
                .setTitle(modelDTO.getTitle())
                .setContent(modelDTO.getContent())
                .setImage(modelDTO.getImage())
                .setCreateTime(now)
                .setIsPublic(0)
                .setIsAnonymousness(1)
                .setIsSoul(0);
        int rows = modelMapper.insert(model);

        // 判断返回值是否不为1
        if (rows != 1) {
            // 是：抛出InsertException
            throw new InsertException("发布问题失败！当前服务器忙，请稍后再尝试！");
        }
        //匿名动态获取自增id在rabbitmq中加入
        Integer id = model.getId();
        System.out.println("private中获取的自增id为：" + id);
        rabbitTemplate.convertAndSend("BULLY.SAVE.PRIVATE.EXCHANGE",
                "private.save", id);
    }

    @Override
    public void soulModel(ModelDTO modelDTO, Long userId, String username, Integer role) {
        if(role == 0){
            throw new PermissionDeniedException("权限不足！仅心灵辅导认证人员可发布！");
        }
        // 创建当前时间对象：now
        LocalDateTime now = LocalDateTime.now();
        Model model = new Model()
                .setUserId(userId)
                .setUsername(username)
                .setTitle(modelDTO.getTitle())
                .setContent(modelDTO.getContent())
                .setImage(modelDTO.getImage())
                .setCreateTime(now)
                .setIsPublic(0)
                .setIsAnonymousness(0)
                .setIsSoul(1);
        int rows = modelMapper.insert(model);
        // 判断返回值是否不为1
        if (rows != 1) {
            // 是：抛出InsertException
            throw new InsertException("发布问题失败！当前服务器忙，请稍后再尝试！");
        }
        //辅导区的发布监听
        Integer id = model.getId();
        System.out.println("发布后获取的id为：" + id);
        rabbitTemplate.convertAndSend("BULLY.SAVE.SOUL.EXCHANGE",
                "soul.save", id);
    }


    private List<Model> mostLikesModels = new CopyOnWriteArrayList<>();
    /**
     * 查找点击量最高的动态列表
     * @return 点击量最高的动态列表
     */
    @Override
    public List<Model> getMostLikes() {
        if (mostLikesModels.isEmpty()){
            synchronized (CacheSchedule.LOCK_CACHE_MODEL){
                if(mostLikesModels.isEmpty()){
                    mostLikesModels.addAll(modelMapper.findMostLikes());
                }
            }
        }
        return mostLikesModels;
    }

    /**
     * 点击量最高动态列表的缓存列表
     * @return 点击量最高的动态列表的缓存列表
     */
    @Override
    public List<Model> getCachedMostLikes() {
        return mostLikesModels;
    }

    private List<Model> soulListModel = new CopyOnWriteArrayList<>();

    /**
     *心灵辅导专区的列表
     * @return
     */
    @Override
    public PageInfo<Model> getSoulList(Integer page) {
        //设置分页
        PageHelper.startPage(page,2);
        List<Model> soulModel = modelMapper.findSoulList();
        if (soulModel.isEmpty()){
            synchronized (CacheSchedule.LOCK_CACHE){
                soulListModel.addAll(soulModel);
            }

        }
        return new PageInfo<>(soulModel);
    }

    @Override
    public List<Model> getCachedSoulList() {
        return soulListModel;
    }

    private List<Model> listByUserIdModel = new CopyOnWriteArrayList<>();
    /**
     * 显示非匿名的动态列表
     */
    private List<Model> listByNotIsAnonymousnessModel = new CopyOnWriteArrayList<>();
    @Override
    public PageInfo<Model> getListByNotIsAnonymousness(Long userId,Integer page) {
        //设置分页
        PageHelper.startPage(page,2);
        List<Model> notIsAnonymousnessModel = modelMapper.findListByNotIsAnonymousness(userId);
        if (listByNotIsAnonymousnessModel.isEmpty()){
            synchronized (CacheSchedule.LOCK_CACHE){
                listByNotIsAnonymousnessModel.addAll(notIsAnonymousnessModel);

            }
        }
        return new PageInfo<>(notIsAnonymousnessModel);
    }

    /**
     * 非匿名动态列表的缓存
     * @return
     */
    @Override
    public List<Model> getCachedListByNotIsAnonymousness() {
        return listByNotIsAnonymousnessModel;
    }


    private List<Model> listByIsAnonymousnessModel = new CopyOnWriteArrayList<>();
    @Override
    public PageInfo<Model> getListByIsAnonymousness(Long userId,Integer page) {
        //设置分页
        PageHelper.startPage(page,2);
        List<Model> isAnonymousnessModel = modelMapper.findListByIsAnonymousness(userId);
        if (listByIsAnonymousnessModel.isEmpty()){
            synchronized (CacheSchedule.LOCK_CACHE){
                listByIsAnonymousnessModel.addAll(isAnonymousnessModel);
            }
        }
        return new PageInfo<>(isAnonymousnessModel);
    }

    @Override
    public List<Model> getCachedListByIsAnonymousness() {
        return listByIsAnonymousnessModel;
    }


    /**
     * 通过动态的id来查询动态详情用于显示该动态的详情
     * @param id
     * @return
     */
    @Override
    public Model getModelById(Integer id) {
        Model model = modelMapper.findById(id);
        if (model == null){
            throw new ModelNotFoundException("未查询到结果");
        }
        return model;
    }

    @Override
    @Transactional
    public Model delete(Integer modelId, Long userId, Integer role) {
        Model model = modelMapper.selectById(modelId);
        if (model == null){
            throw new ModelNotFoundException("该动态不存在!");
        }
        if (!model.getUserId().equals(userId) && role!=2){
            throw new PermissionDeniedException("权限不足,仅发布者和管理员可删除!");
        }
        int rows = modelMapper.deleteById(modelId);
        //public删除成功加入Rabbitmq队列中 然后监听进行es索引的删除
        Integer isPublic = model.getIsPublic();
        Integer isAnonymousness = model.getIsAnonymousness();
        if (isPublic == 1){
            rabbitTemplate.convertAndSend("BULLY.DELETE.PUBLIC.EXCHANGE",
                    "public.delete", modelId);
            System.out.println("加入删除的公开队列");
        }else if (isAnonymousness == 1){
            rabbitTemplate.convertAndSend("BULLY.DELETE.PRIVATE.EXCHANGE",
                    "private.delete", modelId);
            System.out.println("加入删除的私有队列");
        }else {
            rabbitTemplate.convertAndSend("BULLY.DELETE.SOUL.EXCHANGE",
                    "soul.delete", modelId);
            System.out.println("加入删除的辅导专区队列");
        }
        System.out.println("删除成功!");
        return model;
    }

    @Override
    @Transactional
    public Model update(Integer modelId, Long userId, String title, String content) {
        Model model = modelMapper.selectById(modelId);
        if(model==null){
            throw new ModelNotFoundException("该动态不存在!");
        }
        if(!model.getUserId().equals(userId)){
            throw new PermissionDeniedException("权限不足.仅用户能够修改!");
        }
        model.setId(modelId);
        model.setTitle(title);
        model.setContent(content);

        int rows =modelMapper.updateById(model);
        //更新过后加入Rabbitmq队列中 进行es索引的变更
        Integer isPublic = model.getIsPublic();
        Integer isAnonymousness = model.getIsAnonymousness();
        if (isPublic ==1){
            rabbitTemplate.convertAndSend("BULLY.UPDATE.PUBLIC.EXCHANGE",
                    "public.update", modelId);
        }else if (isAnonymousness == 1){
            rabbitTemplate.convertAndSend("BULLY.UPDATE.PRIVATE.EXCHANGE",
                    "private.update", modelId);
        }else {
            rabbitTemplate.convertAndSend("BULLY.UPDATE.SOUL.EXCHANGE",
                    "soul.update", modelId);
        }
        return model;
    }

    @Override
    public Integer updateLikes(Integer modelId) {
        int row = modelMapper.updateLikes(modelId);
        return row;
    }

    //查询所有的发布过的公开动态
    //列表缓存
    public List<Model> publicModelAll = new CopyOnWriteArrayList<>();

    @Override
    public PageInfo<Model> getPublicAll(Integer page) {
        List<Model> publicAll = modelMapper.getPublicAll();
        PageHelper.startPage(page, 2);
        if (publicAll.isEmpty()){
            synchronized (CacheSchedule.LOCK_CACHE){
                publicModelAll.addAll(publicAll);
            }
        }
        return new PageInfo<>(publicAll);
    }

    //查询所有的发布过的匿名动态
    //列表缓存
    public List<Model> privateModelAll = new CopyOnWriteArrayList<>();

    @Override
    public PageInfo<Model> getPrivateAll(Integer page) {
        List<Model> privateAll = modelMapper.getPrivateAll();
        PageHelper.startPage(page, 2);
        if (privateAll.isEmpty()){
            synchronized (CacheSchedule.LOCK_CACHE){
                privateModelAll.addAll(privateAll);
            }
        }
        return new PageInfo<>(privateAll);
    }

    //根据用户id查询该用户发布过的所有的公开动态 用于个人信息的展示
    public List<Model> getPublicById(Integer id){
        List<Model> publicModels = modelMapper.selectPublicById(id);
        return publicModels;
    }

    @Override
    public Integer updateDislikes(Integer modelId) {
        int row = modelMapper.updateDislikes(modelId);
        return row;
    }
}

