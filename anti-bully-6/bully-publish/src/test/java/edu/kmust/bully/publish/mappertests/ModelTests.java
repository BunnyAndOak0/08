package edu.kmust.bully.publish.mappertests;

import edu.kmust.bully.common.model.Model;
import edu.kmust.bully.publish.mapper.ModelMapper;
import edu.kmust.bully.common.vo.ModelListItemVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Slf4j
public class ModelTests {
    @Autowired
    ModelMapper mapper;

    @Test
    void insertPublicModel() {
        LocalDateTime now = LocalDateTime.now();
        Model model = new Model();
        model.setUserId(1l);
        model.setUsername("艾克");
        model.setTitle("被欺负了怎么办?");
        model.setContent("在学校遭受到了欺凌");
        model.setCreateTime(now);
        model.setIsPublic(1);
        model.setIsAnonymousness(0);
        int rows = mapper.insert(model);
    }

    @Test
    void findMostLikes() {
        List<Model> models = mapper.findMostLikes();
        log.debug("model count={}", models.size());
        for (Model model : models) {
            log.debug(">>> {}", model);

        }
    }


    @Test
    void findListByIsNotAnonymousness(){
        long userId = 3;
        List<Model> models = mapper.findListByNotIsAnonymousness(userId);
        log.debug("model count={}", models.size());
        for (Model model : models) {
            log.debug(">>> {}", model);
        }

    }
    @Test
    void findListByIsAnonymousness(){
        long userId = 3;
        List<Model> models = mapper.findListByIsAnonymousness(userId);
        log.debug("model count={}", models.size());
        for (Model model : models) {
            log.debug(">>> {}", model);
        }
    }
    @Test
    void findById(){
        Integer id=1;
    Model model = mapper.findById(id);
        log.debug("model >>> {}", model);
    }

    @Test
    void deleteById(){
        Integer id = 4;
        int rows = mapper.deleteById(id);
        log.debug("rows >{}",rows);

    }

    @Test
    void updateById() {
        Model model = new Model();
        model.setId(7);
        model.setTitle("校园存在霸凌行为?");
        model.setContent("在生活中存在很多的例子");
        int rows = mapper.updateById(model);
        log.debug("update over. rows={}", rows);
    }
    @Test
    void updatelikes(){
        Integer id=7;
        int row = mapper.updateLikes(id);
        log.debug("row >>{}",row);
    }
}