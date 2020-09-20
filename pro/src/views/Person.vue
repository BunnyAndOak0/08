<template>
  <div>
    <!-- 头部导航栏开始 -->
    <div id="header">
      <router-link to="/">
        <img src="../assets/Article/Header/back.png" alt="" />
      </router-link>

      <div>
        <span v-if="personDateArt.length !=0">{{ personDateArt[0].username }}</span>
      </div>

      <!-- <div id="brief">
        <router-link to="">
          <img src="../assets/Article/Header/author.jpg" alt="" /> -->
      <!-- <span>
                <img src="../assets/Article/Header/rank.png" alt="">
                </span> -->
      <!-- </router-link>

        <div id="msg">
          <h3>中央网新闻</h3>
          <span>2519万粉丝</span>
        </div> -->
      <!-- </div> -->
      <router-link to="">
        <img src="../assets/Article/Header/search.png" alt="" />
      </router-link>

      <router-link to="">
        <img src="../assets/Article/Header/more.png" alt="" />
      </router-link>
    </div>
    <!-- 头部导航栏结束 -->

    <!-- 个人简介部分开始 -->
    <div id="person">
      <div id="per">
        <img src="../assets/Person/perImg/avatar.jpg" alt="" />
        <div id="per_ul_li">
          <ul id="per_ul">
            <li>
              <span>1692</span>
              <span>文章</span>
            </li>
            <li>
              <span>25</span>
              <span>关注</span>
            </li>
            <li>
              <span>4.1万</span>
              <span>粉丝</span>
            </li>
            <li>
              <span>36万</span>
              <span>获赞</span>
            </li>
          </ul>
          <ul id="per_ulx">
            <li>
              <router-link to="">私信</router-link>
            </li>
            <li>
              <router-link to="">关注</router-link>
            </li>
            <li>
              <router-link to=""><div></div></router-link>
            </li>
          </ul>
        </div>
      </div>

      <p>
        <img src="../assets/Person/perImg/attestation.png" alt="" />
        <span>认证:</span>
        <span>优质08创作者</span>
      </p>

      <p>
        <img src="../assets/Person/perImg/place.png" alt="" />
        <span>位置:</span>
        <span>云南曲靖市</span>
      </p>

      <p>
        <img src="../assets/Person/perImg/synopsis.png" alt="" />
        <span>简介:</span>
        <span>事实 逻辑 客观 理性</span>
      </p>
    </div>
    <!-- 个人简介部分结束 -->

    <!-- 分割线开始 -->
    <div id="leave"></div>
    <!-- 分割线结束 -->

    <!-- 顶部选项卡开始2 -->
    <div id="navBorBot">
      <mt-navbar
        v-model="selected"
        v-if="selected == 'personalArticles' || selected == 'personalArticlesx'"
      >
        <mt-tab-item id="personalArticles">个人文章</mt-tab-item>
        <mt-tab-item id="personalArticlesx">问答</mt-tab-item>
      </mt-navbar>
    </div>
    <!-- 顶部选项卡结束-->

    <!-- 内容区开始 -->
    <mt-tab-container v-model="selected">
      <mt-tab-container-item id="personalArticles" v-if="personDateArt.length !=0">
        <div id="mainArticle" v-for="(item,index) in personDateArt" :key="index">
          <div id="artTitle">
            <img src="../assets/Person/perImg/avatar.jpg" alt="" />
            <div>
              <!-- <h4>此人很懒还没有网名</h4> -->
              <h4>{{ item.username }}</h4>
              <h4>
                <!-- <span>2021-08-28</span> -->
                <span>{{ item.createTime | personTime }}</span>
                <!-- <span>22:16</span> -->
                <span>{{ item.createTime | personTimex }}</span>
                <span>-</span>
                <span class="span">优秀08文章</span>
              </h4>
            </div>
            <div>
              <img src="../assets/Person/perImg/morex.png" alt="" />
            </div>
          </div>

          <!-- <h2>回答了：遇到校园暴力该如何处理！</h2> -->
          <h2>{{ item.title }}</h2>
          <!-- <p>
            学校是祖国花朵成长的地方，应该是充满学习氛围、正能量的地方，
            可是有些孩子却在这片祖国的花园里遭受着、来自同窗的欺凌，被羞辱、
            被殴打，一度不愿意上学，有的甚至是患上抑郁症，预防和处理校园霸
            凌是每一位家长和老师共同的责任。
          </p> -->
          <p>{{ item.content }}</p>

          <div id="articleImg">
            <img src="../assets/Person/perImg/swordsmanA.jpg" alt="" />
            <img src="../assets/Person/perImg/swordsmanB.jpg" alt="" />
            <img src="../assets/Person/perImg/swordsmanC.jpg" alt="" />
          </div>

          <!-- 底部导航栏开始 -->
          <ul id="foot">
          <li>
            <img src="../assets/Person/Foot/share.png" alt="">
            <span>6</span>
          </li>

          <li>
            <img src="../assets/Person/Foot/comment.png" alt="">
            <span>6</span>
          </li>

          <li>
            <img src="../assets/Person/Foot/disAssist.png" alt="">
            <span>6</span>
          </li>
        </ul>
        <!-- 底部导航栏结束 -->
         <!-- 分割线开始 -->
          <div id="leave" v-if="index != personDateArt.length - 1"></div>
         <!-- 分割线结束 -->
        </div>
      </mt-tab-container-item>
    </mt-tab-container>
    <!-- 内容区结束 -->
  </div>
</template>

<script>
export default {
  props:['id'],

  filters:{
    personTime(val){
      return val.substring(0,10);
    }
  },

  filters:{
    personTimex(val){
      return val.slice(-1,-6);
    }
  },

  data() {
    return {
      selected: "personalArticles",

      // 个人文章信息
      personDateArt:[],
    };
  },

  mounted(){
    // 个人页面数据显示
    this.personNews();
  },

  methods:{
    personNews(){
      let url = '/api/bully-user/user/getUserById?id=' + this.id;
      this.$axios.get(url)
        .then(res=>{
          this.personDateArt = res.data.data.modelMap.publicMap;
           console.log('personNews',this.personDateArt);
        })
    }
  }
};
</script>

<style scoped>
/* 清楚样式 */
div,
span,
a,
ul,
li,
h4 {
  list-style: none;
  padding: 0;
  margin: 0;
}

/* 个人文章开始部分 */




/* 个人简介部分开始 */
#person {
  width: 95%;
  height: auto;
  margin: 0 auto;
  margin-top: 61px;
  /* background: green; */
  padding: 14px 0;
}

#person #per {
  width: 100%;
  height: 70px;
  display: flex;
  justify-content: space-between;
  padding-bottom: 7px;
}

#person #per img {
  display: block;
  width: 70px;
  height: 70px;
  border-radius: 50%;
}

#person #per #per_ul_li {
  width: 200px;
  height: 70px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

#person #per #per_ul {
  display: block;
  width: auto;
  height: 40px;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

#person #per #per_ul li {
  display: block;
  width: 50px;
  height: 40px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}

#person #per #per_ul li span:first-child {
  font-size: 16px;
  color: #000;
  font-family: 黑体;
}

#person #per #per_ul li span:nth-child(2) {
  font-size: 14px;
  color: gray;
  font-family: 宋体;
}

#person #per #per_ulx {
  display: block;
  width: auto;
  height: 30px;
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
}

#person #per #per_ulx li {
  display: block;
  width: auto;
  height: 30px;
  margin-left: 8px;
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
}

#person #per #per_ulx li > a {
  display: block;
  width: 70px;
  height: 20px;
  text-align: center;
  text-decoration: none;
  font-size: 16px;
  font-family: 黑体;
  color: #fff;
  text-align: center;
  line-height: 20px;
  background: red;
  border-radius: 2px;
}

#person #per #per_ulx li > a:hover {
  cursor: pointer;
}

#person #per #per_ulx li:last-child {
  margin-right: 12px;
}

#person #per #per_ulx li:last-child > a {
  width: 25px;
  display: flex;
  justify-content: center;
  align-items: center;
}

#person #per #per_ulx li:first-child > a {
  width: 68px;
  height: 18px;
  background: #fff;
  color: #000;
  border: 1px solid gray;
}

#person #per #per_ulx li:last-child > a > div {
  width: 0;
  height: 0;
  border: 5px solid transparent;
  border-top-color: white;
  margin-top: 6px;
}

#person p {
  text-align: left;
  line-height: 20px;
  font-weight: 黑体;
  color: #353535;
  display: block;
  justify-content: flex-start;
  align-items: center;
}

#person p span {
  font-size: 13px;
  font-weight: 2px;
  max-width: 300px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: clip;
}

#person p img {
  width: 15px;
  height: 15px;
}

#person p span:nth-child(2) {
  padding: 0 5px;
}

/* 分割线 */
#leave {
  width: 100%;
  height: 4px;
  background: #d4d3d2;
}

/* 底部导航栏开始 */
#mainArticle #foot{
  width:100%;height:30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 10px;
}

#mainArticle #foot li{
  width:33%;height:30px;
  display:flex;
  justify-content: center;
  align-items: center;
}

#mainArticle #foot li span{
  font-size: 15px;
  padding: 0 10px;
}

#mainArticle #foot li:last-child{
  width:34%;height:30px;
}

/* 选项卡下边线设置 */
#navBorBot {
  border-bottom: 1px solid #f0eeee;
}
#mainArticle #articleImg {
  width: 100%;
  height: 87px;
  display: flex;
  justify-content: flex-start;
  padding: 10px 0;
}

#mainArticle #articleImg img {
  display: block;
  width: 33%;
  height: 100%;
}

#mainArticle #articleImg img:last-child {
  display: block;
  width: 34%;
  height: 100%;
}

#mainArticle h2 {
  text-align: left;
  font-family: 黑体;
  color: #000;
  font-weight: bold;
  font-size: 19px;
  line-height: 38px;
  padding-left: 13px;
}

#mainArticle p {
  width: 90%;
  height: 60px;
  line-height: 20px;
  font-size: 17px;
  font-family: 宋体;
  padding-left: 13px;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
  overflow: hidden;
  text-align: left;
}

#mainArticle {
  width: 100%;
  height: auto;
  padding-top: 15px;
  z-index: 20;
}

#mainArticle:last-child{
  margin-bottom: 40px;
}

#mainArticle #artTitle {
  width: 95%;
  height: 40px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

#mainArticle #artTitle img {
  display: block;
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

#mainArticle #artTitle img:last-child {
  width: 20px;
  width: 20px;
}

#mainArticle #artTitle > div {
  width: 276px;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-start;
}

#mainArticle #artTitle > div h4 {
  width: 266px;
  height: auto;
  text-align: left;
  padding-left: 10px;
  font-size: 17px;
  font-weight: bold;
  font-family: "Courier New", Courier, monospace;
  color: #000;
}

#mainArticle #artTitle > div h4:last-child {
  color: gray;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

#mainArticle #artTitle > div h4:last-child span {
  width: auto;
  height: 14px;
  font-size: 14px;
  line-height: 18px;
}

#mainArticle #artTitle > div h4:last-child span:nth-child(2) {
  padding: 0 7px;
}

#mainArticle #artTitle > div h4:last-child span:nth-child(3) {
  padding-right: 7px;
}

#mainArticle #artTitle > div h4:last-child .span {
  max-width: 108px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: clip;
  /* color:red; */
}

#mainArticle #artTitle div:last-child {
  width: 40px;
  height: 100%;
  display: flex;
  align-items: flex-end;
}

#mainArticle #artTitle div:last-child img {
  width: 30px;
  height: 30px;
}

/* 个人文章结束部分 */
/* 底部导航栏结束 */

/* 个人简介部分结束 */

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
  z-index: 300;
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

/* 选项卡设置 */
.mint-navbar .mint-tab-item.is-selected {
  /* background: #9e9e9e; */
  color: red;
  border: none;
}
/* 顶部样式结束 */
</style>
