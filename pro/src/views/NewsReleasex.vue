<template>
  <div>
    <!-- 正常动态页面编写 -->
    <mt-header title="书写动态页面" fixed>
      <router-link to="/" slot="left">
        <mt-button icon="back">返回</mt-button>
      </router-link>
      <mt-button slot="right" @click.native="send">发布</mt-button>
    </mt-header>

    <!-- 文章标题开始部分 -->
    <div id="titleArticle" class="titleArticle">
      <mt-field  placeholder="请写标题" type="textarea" rows="1" v-model="titleNews"></mt-field>
    </div>
    <!-- 文章标题结束部分 -->

    <div id="titleArticle">
      <mt-field  placeholder="内容" type="textarea" rows="10" v-model="titleContent"></mt-field>
    </div>

    <div id="fileImg"></div>

    <!-- 文章内容书写 -->

    <!-- <div id="inputTextarea"> -->
      <!-- <h2>开始书写动态！</h2> -->
      <!-- <textarea
        name=""
        id=""
        cols=" 5"
        rows="1"
        maxlength="300"
        autofocus
      ></textarea> -->      
    <!-- </div> -->

    <!-- 图片上传 -->
    <!-- <div id="ddd">
      <div id="img-group">
        <div class="img-item" v-for="(item,index) in imgArr" :key="index">
          <img :src="item.src" alt="" />
        </div>
      </div>
      <div class="add-pic" @click="addPic()" v-show="picFlag">
        +
        <input
          name="files"
          id="uploaderInput"
          type="file"
          accept="image/*"
          multiple
        />
      </div>
    </div> -->

    <!-- <input type="file" id="file" name="" multiple="multiple">
    <input type="button" name="" value="上传" id="button">  -->

    <form action="http://f3346291w6.zicp.vip/api/bully-upload/upload" method="post" enctype="multipart/form-data">
        <!--method必须为post enctype必须设置为multipart/form-data否则后台的HttpFileCollection的Count属性为0-->
        <input type="file" name="imageFile" id="file" multiple="multiple"/>
        <input type="submit"  value="上传" id="button" name=""/>
    </form>

  </div>
</template>

<style scoped>
#fileImg{
  width:100%;height:auto;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding:30px 0;
}

#fileImg img{
  display: block;
  height:100px;
  width:150px;
  padding: 0 10px;

}

/* 标题书写 */
.titleArticle{
  margin-top: 40px;
}
#titleArticle:first-child{
  margin-bottom: 10px;
}
/* 内容区域样式编写 */
#inputTextarea {
  width: 90%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-top: 50px;
  margin-left: 5%;
}

#inputTextarea > textarea {
  margin-top: 10px;
  width: 100%;
  height: 300px;
  color: black;
  font-size: 20px;
  vertical-align: top;
  resize: none;
  word-wrap: break-word;
  border: none;
  background: url(../assets/newsRelease/write.jpg) 0 3px;
  background-size: 100% 300px;
  opacity: 0.2;
  color: red;
}

#inputTextarea > div {
  clear: both;
}

/* 图片上传 */
.hide {
  display: none;
}

.img-item {
  width: 4.4rem;
  height: 3.75rem;
  float: left;
  margin-left: 1.5rem;
  margin-top: 0.5rem;
}

.img-item img {
  width: 100%;
  height: 100%;
}
.active-pic {
  font-size: 0.6rem;
  color: #9b9b9b;
  padding: 0 1rem 0 1.5rem;
  margin-top: 1rem;
}

.add-pic {
  background: #f3f3f3;
  width: 4.4rem;
  height: 3.75rem;
  float: left;
  margin-left: 1.5rem;
  margin-top: 0.5rem;
  text-align: center;
  font-size: 2rem;
  line-height: 3.5rem;
  color: #979797;
  position: relative;
}

.add-pic input {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  z-index: 10;
  opacity: 0;
}
</style>

<script>
import { Toast } from "mint-ui";
export default {
  data() {
    return {
      msg: "",
    //   图片上传
    imgArr: [],
    picFlag: true,

    // 标题信息
    titleNews:'',
    titleContent:'',
    };
  },

  mounted(){
    // 文件上传
    // this.loadFiles();
  },
  

  methods: {

    // // 文件上传
    // loadFiles(){
    //   window.onload = function(){
    //     var file = document.getElementById('file');
    //     var button = document.getElementById('button');
    //     var ofile = null;

    //     button.onclick = function(){
    //       if(file.files.length == 0){
    //         console.log('请选择文件');
    //         return false;         
    //      }
    //      // 这里只选择了一个文件
    //      ofile = file.files;
    //     //  console.log(ofile.length);
    //      console.log('-----------------');
    //      var reader = new FileReader();
    //      for(var i=0;i<ofile.length;i++){
    //       //  console.log(ofile[i]);
    //         // 创建一个读取文件对象 reader
    //        reader.readAsDataURL(ofile[i]);
    //        console.log(ofile[i]);
    //        reader.onload = function(){
    //          document.getElementById('fileImg').innerHTML +="<img src='"+ reader.result + "' width=100px height=100px/>";
    //          console.log(reader.result);
    //        }
    //      }
    //     }
    //   }
    // },

    //发送信息
    send(){
      if(this.titleNews != '' && this.titleContent !=''){
      let url = '/api/bully-publish/publish/models/private/send';
      let params = { 
          title:this.titleNews,
          content:this.titleContent,
          image:'',
      }
      this.$axios.post(url,params)
        .then(res=>{
          console.log('sendPublicNews',res);
          this.titleNews = '';
          this.titleContent = '',
          this.$router.push('/');
           Toast({
            message: "匿名动态发表成功！",
            position: "bottom",
            iconClass: "icon icon-success",
            className: "toast",
            duration: 500,
          });
        }).catch(err=>{
        Toast({
            message: "请登录或者去注册你的账户！",
            position: "bottom",
            iconClass: "icon icon-success",
            className: "toast",
            duration: 2000,
          });
      });
       }
    },

    //   图片上传
    goBack(){
        history.go(-1)
    },
    
},

};
</script>
