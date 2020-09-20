package edu.kmust.bully.publish.serviceTests;

import com.github.pagehelper.PageInfo;
import edu.kmust.bully.common.exception.ServiceException;
import edu.kmust.bully.common.model.Model;
import edu.kmust.bully.common.dto.ModelDTO;
import edu.kmust.bully.publish.service.IModelService;
import edu.kmust.bully.common.vo.ModelListItemVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class ModelTests {
    @Autowired
    IModelService modelService;

    @Test
    void publicModel() {
        try {
            ModelDTO modelDTO = new ModelDTO();
            modelDTO.setTitle("被欺负了怎么办?");
            modelDTO.setContent("在学校收到了欺凌,想要寻求帮助!");

            long userId = 4;
            String username = "Tom";

            modelService.publicModel(modelDTO, userId, username);
            log.debug("create model ok.");
        } catch (ServiceException e) {
            log.debug("create model failure.");
        }

    }

    @Test
    void privateModel() {
        try {
            ModelDTO modelDTO = new ModelDTO();
            modelDTO.setTitle("霸凌行为在校园?");
            modelDTO.setContent("我们要拒绝此类活动!");

            long userId = 3;

            modelService.privateModel(modelDTO, userId);
            log.debug("create model ok.");
        } catch (ServiceException e) {
            log.debug("create model failure.");
        }

    }

    @Test
    void getModelLikes(){
        List<Model> models = modelService.getMostLikes();
        log.debug("model count={}",models.size());
        for (Model model : models) {
            log.debug("model>>>{}",model);

        }
    }


    @Test
    void getListByNotIsAnonymousness(){
        long userId = 3;
        Integer page = 0;
        PageInfo<Model> pageInfo = modelService.getListByNotIsAnonymousness(userId,page);
        log.debug("page info >>> {}", pageInfo);
    }



    @Test
    void getListByIsAnonymousness(){
        long userId = 3;
        Integer page = 0;
        PageInfo<Model> pageInfo = modelService.getListByNotIsAnonymousness(userId,page);
        log.debug("page info >>> {}", pageInfo);

    }

    @Test
    void getModelById(){
        Integer id = 2;
        Model model = modelService.getModelById(id);
        log.debug("model>>>{}",model);
    }

    @Test
    void deleteById(){
        Integer modelId =118 ;
        long userId = 5;
        Integer role = 0;
        Model model = modelService.delete(modelId,userId,role);
        log.debug("model >{}",model);
    }

    @Test
    void updateById(){
        Integer modelId = 7;
        long userId = 3;
        String title = "就这水平!";
        String content = "别说了";
        Model model = modelService.update(modelId,userId,title,content);
        log.debug("update over. model={}", model);
    }
    @Test
    void updatelikes(){
        Integer id=2;
        int row = modelService.updateLikes(id);
        log.debug("row >>{}",row);
    }

}