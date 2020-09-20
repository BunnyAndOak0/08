package edu.kmust.bully.search.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

/**
 * @Author BunnyAndOak0
 * @Description 将对象映射为ES中的一条json格式文档
 * indexName 指定这个对象转为json文档存入这个索引
 * type 指定当前索引下创建的类型名称 一个索引对应一个类型
 * @Date 2020/8/27 19:21
 **/
@Data
@Document(indexName = "public")
public class PublicModel {
    //type指定字段类型
    @Id   //用来将对象中id属性和文档中的_id属性一一对应
    private Long id;
    @Field(type = FieldType.Long)
    private Long userId;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String username;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;
    @Field(type = FieldType.Keyword, index = false)
    private String image;
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time_no_millis)
    private LocalDateTime createTime;

    @Override
    public String toString() {
        return "PublicModel{" +
                "id=" + id +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
