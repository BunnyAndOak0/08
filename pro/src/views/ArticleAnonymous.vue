<template>
  <div>
    <!-- 页面顶部导航栏信息开始 -->
    <div id="header">
      <router-link to="/">
        <img src="../assets/Article/Header/back.png" alt="" />
      </router-link>

      <div v-if="isTitleShow == false">
        <span>08 文章专区</span>
        </div>

      <div id="brief" v-if="isTitleShow == true">
        <router-link to="">
          <img src="../assets/Article/Header/author.jpg" alt="" />
          <!-- <span>
                <img src="../assets/Article/Header/rank.png" alt="">
                </span> -->
        </router-link>

        <div id="msg">
          <h3>{{ articleDetail.username }}</h3>
          <span>{{ ranNum | publicTime }}粉丝</span>
        </div>
      </div>

      <!-- div# -->

      <router-link to="">
        <img src="../assets/Article/Header/search.png" alt="" />
      </router-link>

      <router-link to="">
        <img src="../assets/Article/Header/more.png" alt="" />
      </router-link>
    </div>
    <!-- 页面顶部信息导航栏结束 -->

    <!-- 中间主题部分内容开始 -->
    <div id="content" @click="doWritex">
      <!-- 标题文章简介开始 -->
      <p id="pDis">
        <!-- <span
          >校园霸凌页面测试数据！ 此处是文章简介部分， 此处是文字加图片组合成
          的页面文章内容简介部分！ -->
          <span>{{ articleDetail.title }}
          <img src="../assets/Article/Header/music.png" alt="" />
        </span>
      </p>
      <!-- 标题文章简介结束 -->

      <div id="textStart">
        <div id="avatat">
          <img src="../assets/Article/Header/author.jpg" alt="" />
        </div>
        <div id="msgx">
          <!-- <h3>中央网新闻</h3> -->
          <h3>{{ articleDetail.username }}</h3>
          <div>
            <span>原创</span>
            <!-- <span>2021-08-28</span> -->
            <span>{{ articleDetail.createTime | publicTime }}</span>
            <span>-</span>
            <span>软件青云获奖者 优质资源</span>
          </div>
        </div>
        <div id="btn">
          <span>关注</span>
        </div>
      </div>

      <!-- 文字主体内容开始 -->
      <p class="p2">
        <!-- <span
          >校园霸凌页面测试数据！ 此处是文章简介部分， 此处是文字加图片组合成
          的页面文章内容简介部分！ 校园霸凌页面测试数据！ 此处是文章简介部分，
          此处是文字加图片组合成 的页面文章内容简介部分 校园霸凌页面测试数据！
          此处是文章简介部分， 此处是文字加图片组合成 的页面文章内容简介部分
          校园霸凌页面测试数据！ 此处是文章简介部分， 此处是文字加图片组合成
          的页面文章内容简介部分 校园霸凌页面测试数据！ 此处是文章简介部分，
          此处是文字加图片组合成 的页面文章内容简介部分 校园霸凌页面测试数据！
          此处是文章简介部分， 此处是文字加图片组合成 的页面文章内容简介部分
        </span> -->
        <span>{{ articleDetail.content }}</span>
        <img src="../assets/Article/Header/contentIm.jpg" alt="" />
      </p>
      <!-- 文字主体内容结束 -->
    </div>
    <!-- 中间主题部分内容结束 -->

    <!-- 底部导航栏开始 -->
    <div id="btnNav">
      <div @click="doWrite">
        <img
          src="../assets/Article/Bottom/write.png"
          alt=""
        />&nbsp;&nbsp;写评论...
      </div>
      <div><img src="../assets/Article/Bottom/comment.png" alt="" /></div>

      <div><img src="../assets/Article/Bottom/disStore.png" alt="" /></div>
      <!-- <div><img src="../assets/Article/Bottom/store.png" alt=""></div> -->

      <div><img src="../assets/Article/Bottom/disPro.png" alt="" /></div>
      <!-- <div><img src="../assets/Article/Bottom/prove.png" alt=""></div> -->

      <div><img src="../assets/Article/Bottom/share.png" alt="" /></div>
    </div>
    <!-- 底部导航栏结束 -->

    <!-- 评论区开始输入区域开始 -->
    <div id="wCom" v-if="isWrite == true">
      <!-- textarea,fullScreen,send -->
      <div id="text1">
        <div>
          <textarea
            name=""
            id="textPlus"
            cols="30"
            rows="10"
            placeholder="写评论..."
            v-model="msg"
           @keyup.enter="myInformation"
          ></textarea>
        </div>
  <!-- @keydown="myInformation" -->
        <div>
          <img src="../assets/Article/Text/fullScreen.png" alt="" />
          <span @click="myInformation">发送</span>
        </div>
      </div>

      <div id="text2">
        <div>
          <input type="checkbox" name="vehicle" value="Car" />
          <span>同时转发</span>
        </div>

        <router-link to="">
          <img src="../assets/Article/Text/photo.png" alt="" />
        </router-link>

        <div>
          <img src="../assets/Article/Text/@.png" alt="" />
        </div>

        <div>
          <img src="../assets/Article/Text/gif.png" alt="" />
        </div>

        <div>
          <img src="../assets/Article/Text/like.png" alt="" />
        </div>

        <div>
          <img src="../assets/Article/Text/add.png" alt="" />
        </div>
      </div>
    </div>
    <!-- 评论区开始输入区域结束 -->

    <!-- 评论区头像，内容，点赞开始 -->
    <!-- v-for="count in 10" :key="count" -->
    <div class="comDis" @click="doWritex">
      <h2>评论</h2>
      <div id="comFloor2" v-for="(item, index) in answerData" :key="index">
        <!-- 头像 -->
        <div id="comAvatat">
          <router-link to="">
            <img src="../assets/home/comment/comAvatar.jpg" alt="" />
          </router-link>
        </div>
        <!-- 呢称，评论内容，评论时间 -->
        <div id="comMain">
          <!-- 昵称 -->
          <p>{{ item.userNmae }}</p>
          <!-- 评论内容 -->
          <h3>{{ item.content }}</h3>
          <p>{{ item.createdTime | publicTime }}</p>
        </div>
        <div id="loveNum" @click="doComAssist(index)">
          <img
            src="../assets/home/comment/disLoveNum.png"
            alt=""
            v-if="index != comAssist"
          />
          <img
            src="../assets/home/comment/loveNum.png"
            alt=""
            v-if="index == comAssist"
          />
          <p>222</p>
        </div>
        <!-- 评论区头像，内容，点赞结束 -->
      </div>
    </div>
  </div>
</template>
<style scoped>
/* 清除样式 */
div,
span,
a,
img,
textarea,
input {
  padding: 0;
  margin: 0;
}

/* 底部导航栏开始 */
#btnNav {
  width: 100%;
  height: 35px;
  position: fixed;
  bottom: 0px;
  left: 0;
  background: #fff;
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 10px 0;
  border-top: 1px solid #f0eeee;
}

#btnNav div {
  display: flex;
  justify-content: center;
  align-items: center;
}

#btnNav div:first-child img {
  width: 12px;
  height: 14px;
  margin: 0 15px;
}

#btnNav div img {
  width: 20px;
  height: 20px;
}

#btnNav div:last-child img {
  display: block;
  width: 100%;
  height: 100%;
}

#btnNav div:first-child {
  width: 150px;
  height: 85%;
  background: #f0eeee;
  border-radius: 15px;
  font-size: 14px;
  display: flex;
  justify-content: flex-start;
}
/* 底部导航栏结束 */

/* 中间主题内容开始 */
#content #textStart {
  width: 100%;
  height: 40px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 25px 0;
}

#content #textStart #avatat {
  width: 50px;
  height: 100%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

#content #textStart #avatat img {
  display: block;
  width: 40px;
  height: 100%;
  border-radius: 50%;
}

#content #textStart #btn {
  width: 60px;
  height: 100%;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

#content #textStart #btn span {
  display: block;
  width: 50px;
  height: 30px;
  text-align: center;
  line-height: 30px;
  color: #fff;
  background: red;
  border-radius: 5px;
}

#content #textStart #msgx {
  width: 242px;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-start;
}

#content #textStart #msgx > div {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

#content #textStart #msgx > h3 {
  font-size: 16px;
  font-weight: bold;
  font-family: 黑体;
}

#content #textStart #msgx > div span {
  font-size: 12px;
  color: gray;
  font-weight: normal;
}

#content #textStart #msgx > div span:first-child {
  font-size: 8px;
  border: 1px solid gray;
  padding: 1px 3px;
  border-radius: 2px;
  line-height: 10px;
  text-align: center;
}

#content #textStart #msgx > div span:nth-child(2) {
  padding-left: 5px;
  max-width: 67px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: clip;
}

#content #textStart #msgx > div span:nth-child(3) {
  padding: 0 3px;
}

#content #textStart #msgx > div span:last-child {
  max-width: 166px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

#content {
  padding-bottom: 30px;
  width: 94%;
  height: auto;
  margin: 0 auto;
  margin-top: 61px;
  background: #fff;
}

#content p {
  display: block;
  font-display: flex;
  justify-content: flex-start;
  align-items: center;
  width: 100%;
  height: auto;
}

#content p img {
  margin-bottom: 6px;
}

#content p span {
  display: block;
  width: auto;
  height: auto;
  max-width: 352px;
  font-size: 16px;
  line-height: 30px;
  font-family: 黑体;
  /* font-weight: none; */
  word-break: normal;
  /* width:auto;  */
  white-space: pre-wrap;
  word-wrap: break-word;
  overflow: hidden;
  /* text-align: left; */
}

#content .p2 span {
  font-size: 14px;
  font-family: 宋体;
  font-weight: 7;
  color: gray;
}

#content .p2 img {
  display: block;
  width: 300px;
  height: 400px;
  margin: 0 auto;
}
/* 中间主题内容结束 */

/* 顶部样式开始 */
#header {
  width: 100%;
  height: 40px;
  background: #fff;
  display: flex;
  justify-content: space-around;
  align-items: center;
  border-bottom: 1px solid #f0eeee;
  padding: 10px 0;
  position: fixed;
  top: 0;
}

#header > a {
  display: block;
  width: 40px;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  text-decoration: none;
  color: #000;
}

#header > a img {
  width: 20px;
  height: 20px;
}

#header > div {
  width: 230px;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

#header #brief {
  width: 230px;
  height: 100%;
  display: flex;
  justify-content: flex-start;
}

#header #brief > a {
  display: block;
  width: 60px;
  height: 40px;
  display: flex;
  justify-content: center;
  position: relative;
}

#header #brief > a > span {
  position: absolute;
  right: 13px;
  bottom: 3px;
  z-index: 20px;
}

#header #brief > a > span img {
  width: 15px;
  height: 15px;
  display: block;
}

#header #brief > a img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

#header #brief > #msg {
  width: 170px;
  height: 100%;
}

#header #brief > #msg h3,
span {
  font-size: 16px;
  max-width: 170px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: left;
  font-weight: bold;
}

#header #brief > #msg span {
  display: block;
  margin-top: 10px;
  font-size: 14px;
  font-weight: normal;
}

#header > div > span {
  font-size: 17px;
  font-family: "Courier New", Courier, monospace;
  color: #000;
}
/* 顶部样式结束 */

/* 评论区开始输入区域开始 */
#wCom #text2 {
  width: 95%;
  height: 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0 auto;
  padding: 20px 0;
}

#wCom #text2 div:first-child {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

#wCom #text2 > a {
  display: block;
  width: 30px;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

#wCom #text2 > a img {
  width: 17px;
  height: 17px;
}

#wCom #text2 div:not(div:first-child) {
  width: 30px;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

#wCom #text2 div:not(div:first-child) img {
  width: 17px;
  height: 17px;
}

#wCom #text2 div:last-child {
  margin-right: 40px;
}
#wCom #text2 > span {
  font-size: 14px;
  font-weight: 1px;
  color: gray;
}

#wCom #text2 input {
  display: block;
  width: 16px;
  height: 16px;
}

#wCom {
  width: 100%;
  height: auto;
  position: fixed;
  left: 0px;
  bottom: 0;
  z-index: 200;
  background: #e7e5e5;
}

#wCom #text1 {
  width: 95%;
  height: 60px;
  display: flex;
  justify-content: flex-start;
  margin: 0 auto;
  padding-top: 20px;
}

#wCom #text1 div:first-child {
  width: 70%;
  height: 100%;
}

#wCom #text1 div:first-child textarea {
  display: block;
  width: 100%;
  height: 100%;
  font-size: 13px;
  line-height: 30px;
  padding-left: 20px;
  border: none;
  color: #f97c0d;
  border-radius: 10px;
  background: #fff;
}

#wCom #text1 div:last-child {
  width: 30%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}

#wCom #text1 div:last-child > img {
  width: 25px;
  height: 25px;
}

#wCom #text1 div:last-child > span {
  color: orange;
  font-size: 15px;
  font-weight: bold;
}
/* 评论区开始输入区域结束 */

/* 评论区拷贝 */
/* 评论区大小*/
.comDis {
  width: 100%;
  height: auto;
  /* background: rgba(255, 255, 255, 0.2); */
  background: #f4f3f3;
  padding-bottom: 70px;
}

.comDis > h2 {
  text-align: center;
  line-height: 80px;
}

/* 评论区头像，内容，点赞开始 */
#comFloor2 {
  padding: 10px 0;
  margin-left: 10px;
  display: flex;
  justify-content: space-between;
  z-index: 0;
}
#comFloor2 #comMain {
  width: 70%;
  height: auto;
}

#comFloor2 #comMain p,
h3 {
  text-align: left;
  font-size: 15px;
  /* color: rgba(255, 255, 255, 0.5); */
  color: #747272;
}

#comFloor2 #comMain h3 {
  color: #000;
  font-size: 16px;
  padding: 7px 0;
}

#comFloor2 #comAvatat {
  justify-content: center;
  align-items: center;
}

#comFloor2 #comAvatat,
#loveNum {
  width: 40px;
  height: 40px;
}

#comFloor2 #comAvatat > a {
  display: block;
  width: 100%;
  height: 100%;
  text-decoration: none;
  justify-content: center;
  align-items: center;
}

#comFloor2 #comAvatat > a img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

#comFloor2 #loveNum {
  flex-direction: column;
  justify-content: center;
}
#comFloor2 #loveNum img {
  width: 17px;
  height: 17px;
}
#comFloor2 #loveNum p {
  font-size: 15px;
  text-align: center;
  /* color: rgba(255, 255, 255, 0.5); */
  color: #000;
  padding-top: 5px;
}
</style>
<script>
import { Toast } from 'mint-ui';
</script>

<script>
export default {
  filters: {
    publicTime(val) {
      if(typeof(val) == 'string'){
      return val.substring(0, 10);
      }else if(typeof(val) == 'number'){
        if(val >= 10000){
        return Math.ceil(val/10000) + '万';
      }
      }
    },
  },

  props: ["anonyId"],

  data() {
    return {
      //用户评论信息
      msg: "",
      imgList: [],
      //评论区开关
      isWrite: false,

      // 评论区点赞
      comAssist:null,

      //评论
      answerData: [],

      // 文章内容
      articleDetail:{},

      // 标题隐藏
      isTitleShow:false,

      // 随机数
      ranNum:null,
    };
  },

  mounted() {
    this.doAnswer();
    // 文章内容
    this.artDetailNews();

     let pDis = document.getElementById('pDis');
    if(pDis){
      window.addEventListener("scroll", this.handleScroll);
    }

    this.ranNum =  Math.ceil(Math.random()*1000000);
  },

  methods: {
    // 页面滚动事件
    handleScroll() {
     
      let scrollTop = window.pageYOffset || document.documentElement.scrollTop ||  document.body.scrollTop;
      let dpDis = document.getElementById("pDis").clientHeight;
      let dpDisText = document.getElementById("textStart").clientHeight;

      let yDis = dpDis + dpDisText;

      // console.log('dpDis',dpDis);
      // console.log('dpDisText',dpDisText);

      if(scrollTop>=yDis){
        this.isTitleShow = true;
      }else{
        this.isTitleShow = false;
      }
    },

    //公开评论
    doAnswer() {
      let url = `/api/bully-publish/publish/answers/model/${this.anonyId}?page=1`;
      this.$axios.get(url).then((res) => {
        console.log("Lsy", res);
        this.answerData = res.data.data.list;
      });
    },

    // 获取文章详情
    artDetailNews() {

      let url = `/api/bully-publish/publish/models/${this.anonyId}`;
      console.log("artAnnoyNewsUrl", url);
      this.$axios.get(url).then(res => {
        
        this.articleDetail = res.data.data;
        console.log("artDetailNews", this.articleDetail);
      });
    },

    myInformation() {
      let reg = /^[我|卧|窝][cao|草|操]$/;
      if (reg.test(this.msg) == -1) {
        // console.log(this.msg);
      } else {
        this.msg = this.msg.replace(/[我|卧|窝][cao|草|操]/, "**");
        // console.log(this.msg);
      }

      let date = new Date();
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      if(month<10){
        month = '0' + month;
      }
      let day = date.getDate();
      if(day < 10){
        day = '0' + day;
      }
      let hours = date.getHours();
      let minutes = date.getMinutes();
      let time = year + '-' + month + '-' + day + hours +':' + minutes;


      let textareax = document.getElementById('textPlus');
     

      let myComment = {
        comments:[],
        content:this.msg,
        countOfLikes:0,
        createdTime:time,
        id:this.anonyId,
        modelId:4,
        statusOfAccept:0,
        userId:5,
        userName:'李双元',
      }
      let url = '/api/bully-publish/publish/answers/post';
      let params = {
        modelId:this.anonyId,
         content:this.msg
      };
     this.$axios.post(url,params)
      .then(res=>{
        console.log('answerNews',res);
        if(res.data.state === 2000){
          this.answerData.unshift(myComment);
        }
      })
      this.msg = "";
      this.isWrite = false;
    },

    //评论区开关设置
    doWrite() {
      this.isWrite = true;
    },
    doWritex() {
      this.isWrite = false;
    },

    //评论区
    // 评论区点赞
    doComAssist(index) {
      this.comAssist = index;
    },
  },
};
</script>
