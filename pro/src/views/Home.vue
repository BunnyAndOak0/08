<template>
  <div>
    <!-- 顶部表头制作开始 -->
    <div id="topSeaech" v-if="selected == 'homeBar' || selected == 'homeBarx'">
      <div id="search">
        <router-link to="">
          <img src="../assets/home/top_search/s1.png" alt="" />
          <span>今年以来校园暴力频繁</span>
           <!-- <span>{{ publicData[1].title}}</span> -->
          <div>|</div>
          <span>校园暴力影响有哪些....</span>
          <!-- <span>{{ publicData[0].title }}</span> -->
        </router-link>
      </div>
      <div id="writer">
        <router-link to="/newsRelease">
          <img src="../assets/home/top_search/camera.png" alt="" />
          <p>写动态</p>
        </router-link>
      </div>
    </div>
    <!-- 顶部表头制作结束 -->

    <!-- 顶部表头制作开始2 -->
    <div id="topSeaech" v-if="selected == 'anonymousBar'">
      <div id="search">
        <router-link to="">
          <img src="../assets/home/top_search/s1.png" alt="" />
          <span>今年以来校园暴力频繁</span>
          <div>|</div>
          <span>校园暴力影响有哪些....</span>
        </router-link>
      </div>
      <div id="writer">
        <router-link to="/newsReleasex">
          <img src="../assets/home/top_search/anonymousBar.png" alt="" />
          <p>写匿名动态</p>
        </router-link>
      </div>
    </div>
    <!-- 顶部表头制作结束 -->

    <!-- 顶部选项卡开始2 -->
    <mt-navbar
      v-model="selected"
      v-if="selected == 'homeBar' || selected == 'homeBarx'"
      fixed
    >
      <mt-tab-item id="homeBar" @click.native="recommendNews">推荐</mt-tab-item>
      <mt-tab-item id="homeBarx">动态</mt-tab-item>
    </mt-navbar>
    <!-- 顶部选项卡结束-->

    <!-- 内容区域开始 -->
    <div v-if="publicData.length == 0" id="err">
      <h5>抱歉啊！亲</h5>
      <h5>服务器连接失败！</h5>
    </div>

    <mt-tab-container v-model="selected">
      <!-- 动态区域开始 -->
      <mt-tab-container-item
        id="homeBarx"
        v-infinite-scroll="loadMorePublic"
        infinite-scroll-disabled="loading"
        infinite-scroll-distance="10"
      >
        <div id="distance"></div>
        <div id="container" v-for="(item, index) in publicData" :key="index">
          <!-- <p>{{count}}</p> -->
          <div id="header">
            <!-- 头像 -->
            <div id="avatar">
              <router-link :to="`/person/${item.userId}`">
                <!-- <img src="../assets/home/contain/avator.jpg" alt="" /> -->
                <img
                  :src="
                    require('../assets/home/publicAvatar/' +
                      publicAvatar[index % 10])
                  "
                  alt=""
                />
              </router-link>
            </div>
            <!-- 时间 标题 型号 -->
            <div id="msg">
              <div class="msg_title">
                <!-- 昵称 -->
                <span class="span">{{ item.username }}</span>
                <img src="../assets/home/contain/crwon.png" alt="" />
              </div>
              <div class="msg_title">
                <!-- 时间 -->
                <span>{{ item.createTime | publicTime }}</span>
                <span>来自</span>
                <!-- <span style="color:blue">学生</span> -->
                <span style="color:blue">{{ job[index % 5] }}</span>
              </div>
            </div>
            <!-- 收藏展开 -->
            <div id="unflod" @click="isOpen(index)">
              <img src="../assets/home/contain/open.png" alt="" />
            </div>
            <!-- 弹出使用 -->
            <ul id="eject" v-show="index == num">
              <li>
                <img src="../assets/home/contain/disAdd.png" alt="" />
                <!-- <img src="../assets/home/contain/add.png" alt=""> -->

                <span>关注</span>
              </li>
              <li>
                <img src="../assets/home/contain/disStore.png" alt="" />
                <!-- <img src="../assets/home/contain/store.png.png" alt=""> -->
                <span>收藏</span>
              </li>
              <li @click="delPublic(item.id, index)">
                <img src="../assets/home/contain/delUser.png" alt="" />
                <span>删除</span>
              </li>
              <li>
                <img src="../assets/home/contain/disReport.png" alt="" />
                <!-- <img src="../assets/home/contain/report.png" alt=""> -->
                <span>举报</span>
              </li>
              <li>
                <span @click="close">关闭</span>
              </li>
            </ul>
          </div>

          <!-- 文章简介 -->
          <div id="shortArticle">
            <router-link :to="`/articleAnonymous/${item.id}`">
              <p>
                {{ item.title }}
              </p>
            </router-link>
          </div>

          <!-- 主图片 -->
          <div id="mainImag">
            <router-link :to="`/articleAnonymous/${item.id}`">
              <!-- <img src="../assets/home/contain/boys.jpg" alt="" /> -->
              <img
                :src="
                  require('../assets/home/publicMainImg/' +
                    publicMainImg[index % 10])
                "
                alt=""
              />
            </router-link>
          </div>

          <!-- 底部操作 -->
          <div id="bottcar">
            <div>
              <img src="../assets/home/contain/disShare.png" alt="" />
              <!-- <img src="../assets/home/contain/share.png" alt=""> -->
              <span>4</span>
            </div>
            <div @click="doAnswer(item.id, index)">
              <router-link to="">
                <img src="../assets/home/contain/discomment.png" alt="" />
                <!-- <img src="../assets/home/contain/comment.png" alt=""> -->
                <span>{{ answerData.length }}</span>
              </router-link>
            </div>
            <div @click="doProve(index, item.id)">
              <img
                src="../assets/home/contain/disAssist.png"
                alt=""
                v-if="isProvo != index"
              />
              <img
                src="../assets/home/contain/assist.png"
                alt=""
                v-if="isProvo == index"
              />
              <!-- 点赞数 -->
              <span>{{ item.likes }}</span>
            </div>
          </div>
          <!-- 评论区开始 -->

          <div id="comment" v-if="index == commentOff">
            <!-- 标题栏开始 -->
            <div id="title" @click="closeComment">
              <!-- 内容和撤销按钮 -->
              <img src="" alt="" />
              <p>{{ answerData.length }}条评论</p>
              <div>
                <img src="../assets/home/comment/cancel.png" alt="" />
              </div>
            </div>
            <!-- 标题栏结束 -->
            <!-- 底部发言区开始 -->
            <div id="comText">
              <div id="text">
                <input
                  type="text"
                  v-model="comMsg"
                  name="comText"
                  placeholder="发表评论"
                />
              </div>
              <div id="img">
                <img src="../assets/home/comment/add.png" alt="" />
              </div>
              <div id="img">
                <img src="../assets/home/comment/happy.png" alt="" />
              </div>
            </div>
            <!-- 底部发言区结束 -->
            <!-- 评论区头像，内容，点赞开始 -->
            <!-- v-for="count in 10" :key="count" -->
            <div class="comDis">
              <div
                id="comFloor2"
                v-for="(item, index) in answerData"
                :key="index"
              >
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
                  <p>{{ Math.ceil(Math.random() * 10000) }}</p>
                </div>
                <!-- 评论区头像，内容，点赞结束 -->
              </div>
            </div>
          </div>
          <div id="split"></div>
        </div>
        <!-- 评论区结束 -->

        <!-- 分割线 -->
      </mt-tab-container-item>
      <!-- 动态区域结束 -->

      <!-- 推荐区域开始 -->
      <mt-tab-container-item id="homeBar" v-if="recommendData">
        <div id="distance"></div>
        <div id="container" v-for="(item, index) in recommendData" :key="index">
          <!-- <p>{{count}}</p> -->
          <div id="header">
            <!-- 头像 -->
            <div id="avatar">
              <router-link :to="`/person/${item.userId}`">
                <!-- <img src="../assets/home/contain/avatorGril.jpg" alt="" /> -->
                <img
                  :src="
                    require('../assets/home/publicAvatar/' +
                      publicAvatar[index % 10])
                  "
                  alt=""
                />
              </router-link>
            </div>
            <!-- 时间 标题 型号 -->
            <div id="msg">
              <div class="msg_title">
                <!-- <span class="span">IPHONE 12 PRO MAX</span> -->
                <span class="span">{{ item.username }}</span>
                <img src="../assets/home/contain/crwon.png" alt="" />
              </div>
              <div class="msg_title">
                <!-- <span>2021-10-15</span> -->
                <span>{{ item.createTime | publicTime }}</span>
                <span>来自</span>
                <!-- <span style="color:blue">学生</span> -->
                <span style="color:blue">{{ job[index % 5] }}</span>
              </div>
            </div>
            <!-- 收藏展开 -->
            <div id="unflod" @click="isOpen(index)">
              <img src="../assets/home/contain/open.png" alt="" />
            </div>
            <!-- 弹出使用 -->
            <ul id="eject" v-show="index == num">
              <li>
                <img src="../assets/home/contain/disAdd.png" alt="" />
                <!-- <img src="../assets/home/contain/add.png" alt=""> -->

                <span>关注</span>
              </li>
              <li>
                <img src="../assets/home/contain/disStore.png" alt="" />
                <!-- <img src="../assets/home/contain/store.png.png" alt=""> -->
                <span>收藏</span>
              </li>
              <li @click="doDelRecom(item.id,index)">
                <img src="../assets/home/contain/delUser.png" alt="" />
                <span>删除</span>
              </li>
              <li>
                <img src="../assets/home/contain/disReport.png" alt="" />
                <!-- <img src="../assets/home/contain/report.png" alt=""> -->
                <span>举报</span>
              </li>
              <li>
                <span @click="close">关闭</span>
              </li>
            </ul>
          </div>

          <!-- 文章简介 -->
          <div id="shortArticle">
            <router-link :to="`/articleAnonymous/${item.id}`">
              <p>{{ item.title }}</p>
              <!-- <p>
               只见她身穿一袭大红色的新疆舞裙，上面用金线绣着带有浓郁维吾尔族民族风情的各种花纹，头上带着一顶俏皮可爱的朵帕小花帽，帽沿上还插着一根翠绿的羽毛--一看就知道是个维吾尔族的少数民族姑娘。
               只见她身穿一袭大红色的新疆舞裙，上面用金线绣着带有浓郁维吾尔族民族风情的各种花纹，头上带着一顶俏皮可爱的朵帕小花帽，帽沿上还插着一根翠绿的羽毛--一看就知道是个维吾尔族的少数民族姑娘。
              </p> -->
            </router-link>
          </div>

          <!-- 主图片 -->
          <div id="mainImag">
            <router-link :to="`/articleAnonymous/${item.id}`">
              <!-- <img src="../assets/home/contain/grilsA.jpg" alt="" /> -->
              <img
                :src="
                  require('../assets/home/publicMainImg/' +
                    publicMainImg[ index % 11 ])
                "
                alt=""
              />
            </router-link>
          </div>

          <!-- 底部操作 -->
          <div id="bottcar">
            <div>
              <img src="../assets/home/contain/disShare.png" alt="" />
              <!-- <img src="../assets/home/contain/share.png" alt=""> -->
              <span>4</span>
            </div>
            <div @click="openCommentRecom(item.id,index)">
              <router-link to="">
                <img src="../assets/home/contain/discomment.png" alt="" />
                <!-- <img src="../assets/home/contain/comment.png" alt=""> -->
                <span>{{ answerRecomData.length }}</span>
              </router-link>
            </div>
            <div @click="doProveRecom(item.id, index)">
              <img
                src="../assets/home/contain/disAssist.png"
                alt=""
                v-if="index != isProvoRecom"
              />
              <img
                src="../assets/home/contain/assist.png"
                alt=""
                v-if="index == isProvoRecom"
              />
              <span>{{ item.likes }}</span>
            </div>
          </div>
          <!-- 评论区开始 -->

          <div id="comment" v-if="index == commentOffRecom">
            <!-- 标题栏开始 -->
            <div id="title" @click="closeRecomComment">
              <!-- 内容和撤销按钮 -->
              <img src="" alt="" />
              <p>{{ answerRecomData.length }}条评论</p>
              <div>
                <img src="../assets/home/comment/cancel.png" alt="" />
              </div>
            </div>
            <!-- 标题栏结束 -->
            <!-- 底部发言区开始 -->
            <div id="comText">
              <div id="text">
                <input
                  type="text"
                  v-model="comMsg"
                  name="comText"
                  placeholder="发表评论"
                />
              </div>
              <div id="img">
                <img src="../assets/home/comment/add.png" alt="" />
              </div>
              <div id="img">
                <img src="../assets/home/comment/happy.png" alt="" />
              </div>
            </div>
            <!-- 底部发言区结束 -->
            <!-- 评论区头像，内容，点赞开始 -->
            <!-- v-for="count in 10" :key="count" -->
            <div class="comDis">
              <div
                id="comFloor2"
                v-for="(item, index) in answerRecomData"
                :key="index"
              >
                <!-- 头像 -->
                <div id="comAvatat">
                  <router-link to="">
                    <img src="../assets/home/comment/comAvatar.jpg" alt="" />
                  </router-link>
                </div>
                <!-- 呢称，评论内容，评论时间 -->
                <div id="comMain">
                  <!-- 昵称 -->
                  <p>{{ item.username }}</p>
                  <!-- 评论内容 -->
                  <h3>{{ item.content }}</h3>
                  <!-- 评论时间 -->
                  <p>{{ item.createTime }}</p>
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
                  <p>{{ Math.ceil(Math.random() * 10000) }}</p>
                </div>
                <!-- 评论区头像，内容，点赞结束 -->
              </div>
            </div>
          </div>
          <!-- 评论区结束 -->
          <!-- 分割线 -->
          <div id="split"></div>
        </div>
      </mt-tab-container-item>
      <!-- 推荐区域结束 -->

      <!-- 匿名区域开始 -->
      <mt-tab-container-item
        id="anonymousBar"
        v-infinite-scroll="loadMoreAnonymous"
        infinite-scroll-disabled="loading"
        infinite-scroll-distance="10"
      >
        <div class="distance"></div>
        <div id="container" v-for="(item, index) in anonymousData" :key="index">
          <!-- <p>{{count}}</p> -->
          <div id="header">
            <!-- 头像 -->
            <div id="avatar">
              <router-link to="">
                <!-- <img src="../assets/home/contain/bjx.jpg" alt="" /> -->
                <img
                  :src="
                    require('../assets/home/anonymousAvarar/' +
                      anonymousAvarar[index % 10])
                  "
                  alt=""
                />
              </router-link>
            </div>
            <!-- 时间 标题 型号 -->
            <div id="msg">
              <div class="msg_title">
                <!-- 昵称 -->
                <span class="span">{{ item.username }}</span>
                <img src="../assets/home/contain/crwon.png" alt="" />
              </div>
              <div class="msg_title">
                <!-- 时间 -->
                <span>{{ item.createTime | publicTime }}</span>
                <span>来自</span>
                <span style="color:blue">工人</span>
              </div>
            </div>
            <!-- 收藏展开 -->
            <div id="unflod" @click="isOpen(index)">
              <img src="../assets/home/contain/open.png" alt="" />
            </div>
            <!-- 弹出使用 -->
            <ul id="eject" v-if="index == num">
              <li>
                <img src="../assets/home/contain/disAdd.png" alt="" />
                <!-- <img src="../assets/home/contain/add.png" alt=""> -->

                <span>关注</span>
              </li>
              <li>
                <img src="../assets/home/contain/disStore.png" alt="" />
                <!-- <img src="../assets/home/contain/store.png.png" alt=""> -->
                <span>收藏</span>
              </li>
              <li @click="doDelAnonymous(item.id, index)">
                <img src="../assets/home/contain/delUser.png" alt="" />
                <span>删除</span>
              </li>
              <li>
                <img src="../assets/home/contain/disReport.png" alt="" />
                <!-- <img src="../assets/home/contain/report.png" alt=""> -->
                <span>举报</span>
              </li>
              <li>
                <span @click="close">关闭</span>
              </li>
            </ul>
          </div>

          <!-- 文章简介 -->
          <div id="shortArticle">
            <router-link :to="`/articleAnonymous/${item.id}`">
              <p>
                {{ item.title }}
              </p>
            </router-link>
          </div>

          <!-- 主图片 -->
          <div id="mainImag">
            <router-link :to="`/articleAnonymous/${item.id}`">
              <!-- <img src="../assets/home/contain/bjx2.jpg" alt="" /> -->
              <img
                :src="
                  require('../assets/home/anonymousMainImg/' +
                    anonymousMainImg[index % 10])
                "
                alt=""
              />
            </router-link>
          </div>

          <!-- 底部操作 -->
          <div id="bottcar">
            <div>
              <img src="../assets/home/contain/disShare.png" alt="" />
              <!-- <img src="../assets/home/contain/share.png" alt=""> -->
              <span>4</span>
            </div>
            <div @click="openCommentAno(item.id, index)">
              <router-link to="">
                <img src="../assets/home/contain/discomment.png" alt="" />
                <!-- <img src="../assets/home/contain/comment.png" alt=""> -->
                <span>{{ answerAnonyData.length }}</span>
              </router-link>
            </div>
            <div @click="doProveAnonymous(item.id, index)">
              <img
                src="../assets/home/contain/disAssist.png"
                alt=""
                v-if="index != isProvoAno"
              />
              <img
                src="../assets/home/contain/assist.png"
                alt=""
                v-if="index == isProvoAno"
              />
              <!-- 点赞数 -->
              <span>{{ item.likes }}</span>
            </div>
          </div>
          <!-- 评论区开始 -->

          <div id="comment" v-if="index == commentOffAnony">
            <!-- 标题栏开始 -->
            <div id="title" @click="closeCommentAnony">
              <!-- 内容和撤销按钮 -->
              <img src="" alt="" />
              <p>{{ answerAnonyData.length }}条评论</p>
              <div>
                <img src="../assets/home/comment/cancel.png" alt="" />
              </div>
            </div>
            <!-- 标题栏结束 -->
            <!-- 底部发言区开始 -->
            <div id="comText">
              <div id="text">
                <input
                  type="text"
                  v-model="comMsg"
                  name="comText"
                  placeholder="发表评论"
                />
              </div>
              <div id="img">
                <img src="../assets/home/comment/add.png" alt="" />
              </div>
              <div id="img">
                <img src="../assets/home/comment/happy.png" alt="" />
              </div>
            </div>
            <!-- 底部发言区结束 -->
            <!-- 评论区头像，内容，点赞开始 -->
            <!-- v-for="count in 10" :key="count" -->
            <div class="comDis">
              <div
                id="comFloor2"
                v-for="(item, index) in answerAnonyData"
                :key="index"
              >
                <!-- 头像 -->
                <div id="comAvatat">
                  <router-link to="">
                    <img src="../assets/home/comment/comAvatar.jpg" alt="" />
                  </router-link>
                </div>
                <!-- 呢称，评论内容，评论时间 -->
                <div id="comMain">
                  <!-- 昵称 -->
                  <p>{{ item.uname }}</p>
                  <!-- 评论内容 -->
                  <h3>{{ item.content | publicTime }}</h3>
                  <p>一小时前</p>
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
                  <p>{{ Math.ceil(Math.random() * 10000) }}</p>
                </div>
                <!-- 评论区头像，内容，点赞结束 -->
              </div>
            </div>
          </div>
          <div id="split"></div>
        </div>
        <!-- 评论区结束 -->

        <!-- 分割线 -->
      </mt-tab-container-item>
      <!-- 匿名区域结束 -->

      <!-- 心灵专区开始 -->
      <mt-tab-container-item
        id="heartBar"
        v-infinite-scroll="loadMoreHeart"
        infinite-scroll-disabled="loading"
        infinite-scroll-distance="10"
      >
        <!-- 轮播图开始 -->
        <div id="swiper">
          <div id="swiperImg">
            <mt-swipe
              :showIndicators="true"
              :auto="3000"
              :speed="300"
              :continuous="true"
            >
              <mt-swipe-item v-for="(item, index) in swipeImg" :key="index">
                <img
                  :src="require('../assets/home/swiper/' + item)"
                  alt=""
                  style="width:100%;height:100%"
                />
              </mt-swipe-item>
            </mt-swipe>
          </div>

          <div id="swiperImg">
            <mt-swipe
              :showIndicators="false"
              :auto="3000"
              :speed="300"
              :continuous="true"
            >
              <mt-swipe-item v-for="(item, index) in HeartImg" :key="index">
                <img
                  :src="require('../assets/home/HeartImg/' + item)"
                  alt=""
                  style="width:100%;height:100%"
                />
                <!-- <p>aaa</p> -->
              </mt-swipe-item>
            </mt-swipe>
          </div>
        </div>
        <!-- 轮播图结束 -->

        <div id="soulZoneContainer">
          <!-- 左边区域部分开始 -->
          <div id="soulZoneL">
            <!-- 第一个特使卡片开始 高度为39.6 图片大小为25px -->
            <div class="cartOne">
              <p>宁静致远</p>
              <p>
                生活给予我挫折的同时，也赐予了我坚强，我也就有了另一种阅历。
              </p>
              <p>
                <span class="cartDou">
                  <img src="../assets/home/heartBar/cartHeart.png" alt="" />
                  <span></span>
                </span>
                <img src="../assets/home/heartBar/threeOpen.png" alt="" />
              </p>
            </div>

            <div
              class="cartPublicA"
              v-for="(item, index) in heartData"
              :key="index"
            >
              <div class="cartPublic" v-show="index % 2 != 0">
                <div id="cartImg">
                  <img
                    :src="
                      require('../assets/home/HeartImg/' + HeartImg[index % 12])
                    "
                    alt=""
                  />
                  <!-- <img src="../assets/home/heartBar/cartImgx.jpg" alt="" /> -->
                </div>

                <div class="cartOne">
                  <!-- <p>作者</p> -->
                  <p>{{ item.username }}</p>
                  <p>主题{{ item.content }}</p>
                  <p>
                    <span class="cartDou">
                      <img src="../assets/home/heartBar/cartHeart.png" alt="" />
                      <span>{{ item.likes }}</span>
                    </span>
                    <img src="../assets/home/heartBar/threeOpen.png" alt="" />
                  </p>
                </div>
              </div>
            </div>
            <!-- 第一个特使卡片结束 -->
          </div>
          <!-- 左边区域部分结束 -->

          <!-- 右边区域部分开始 -->
          <div id="soulZoneL">
            <div
              class="cartPublicA"
              v-for="(item, index) in heartData"
              :key="index"
            >
              <div class="cartPublic" v-show="index % 2 == 0">
                <div id="cartImg">
                  <!-- <img :src="item.image" alt="" /> -->
                  <!-- <img src="../assets/home/heartBar/cartImgx.jpg" alt="" /> -->
                  <img
                    :src="
                      require('../assets/home/HeartImg/' + HeartImg[index % 12])
                    "
                    alt=""
                  />
                </div>

                <div class="cartOne">
                  <!-- <p>作者</p> -->
                  <p>{{ item.username }}</p>
                  <p>主题{{ item.title }}</p>
                  <p>
                    <span class="cartDou">
                      <img src="../assets/home/heartBar/cartHeart.png" alt="" />
                      <span>{{ item.likes }}</span>
                    </span>
                    <img src="../assets/home/heartBar/threeOpen.png" alt="" />
                  </p>
                </div>
              </div>
            </div>
          </div>
          <!-- 右边区域部分结束 -->
        </div>
      </mt-tab-container-item>
      <!-- 心灵专区结束 -->
    </mt-tab-container>
    <!-- 内容区域结束 -->

    <!-- 底部导航栏制作开始 -->
    <mt-tabbar v-model="selected" fixed>
      <!-- 首页指示 -->
      <mt-tab-item id="homeBar">
        <img
          src="../assets/home/navbar_icon/home.png"
          alt=""
          slot="icon"
          v-if="selected == 'homeBar' || selected == 'homeBarx'"
        />
        <img
          src="../assets/home/navbar_icon/dishome.png"
          alt=""
          slot="icon"
          v-if="selected != 'homeBar' && selected != 'homeBarx'"
        />
        <span
          :class="{
            tabbarColor: selected == 'homeBar' || selected == 'homeBarx',
            disTabbarColor: selected != 'homeBar' && selected != 'homeBarx',
          }"
          >首页</span
        >
      </mt-tab-item>

      <!-- 匿名动态指示 -->
      <mt-tab-item id="anonymousBar" @click.native="annoymousNews">
        <img
          src="../assets/home/navbar_icon/anonymous.png"
          alt=""
          slot="icon"
          v-if="selected == 'anonymousBar'"
        />
        <img
          src="../assets/home/navbar_icon/disAnonymous.png"
          alt=""
          slot="icon"
          v-if="selected != 'anonymousBar'"
        />
        <span
          :class="{
            tabbarColor: selected == 'anonymousBar',
            disTabbarColor: selected != 'anonymousBar',
          }"
          >匿名</span
        >
      </mt-tab-item>

      <!-- 心灵专区指示 -->
      <mt-tab-item id="heartBar" @click.native="heartNews">
        <img
          src="../assets/home/navbar_icon/heart.png"
          alt=""
          slot="icon"
          v-if="selected == 'heartBar'"
        />
        <img
          src="../assets/home/navbar_icon/disHeart.png"
          alt=""
          slot="icon"
          v-if="selected != 'heartBar'"
        />
        <span
          :class="{
            tabbarColor: selected == 'heartBar',
            disTabbarColor: selected != 'heartBar',
          }"
          >心灵专区</span
        >
      </mt-tab-item>

      <!-- 日记专区指示 -->
      <mt-tab-item id="noteBar" @click.native="noteBlock">
        <img
          src="../assets/home/navbar_icon/note.png"
          alt=""
          slot="icon"
          v-if="selected == 'noteBar'"
        />
        <img
          src="../assets/home/navbar_icon/disNote.png"
          alt=""
          slot="icon"
          v-if="selected != 'noteBar'"
        />
        <span
          :class="{
            tabbarColor: selected == 'noteBar',
            disTabbarColor: selected != 'noteBar',
          }"
          >日记</span
        >
      </mt-tab-item>

      <!-- 我的专区指示 -->
      <mt-tab-item id="meBar" @click.native="myBlock">
        <img
          src="../assets/home/navbar_icon/me.png"
          alt=""
          slot="icon"
          v-if="selected == 'meBar'"
        />
        <img
          src="../assets/home/navbar_icon/disMe.png"
          alt=""
          slot="icon"
          v-if="selected != 'meBar'"
        />
        <span
          :class="{
            tabbarColor: selected == 'meBar',
            disTabbarColor: selected != 'meBar',
          }"
          >我的</span
        >
      </mt-tab-item>
    </mt-tabbar>
    <!-- 底部导航栏制作结束 -->
  </div>
</template>
<style scoped>
/* 清除样式 */
div,
span,
a,
img {
  padding: 0;
  margin: 0;
}

/* 导航栏 */
#err {
  margin: 300px auto;
  width: 100px;
  height: 100px;
  background: gray;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: url(../assets/err/errx_.jpg) 0 0 no-repeat;
  background-size: 100px 100px;
  position: relative;
}

#err h5 {
  color: red;
  position: absolute;
  top: 0px;
  left: 20px;
  font-size: 10px;
}

#err h5:first-child {
  top: 100px;
}

#err h5:last-child {
  top: 130px;
}

/* 顶部表头制作 */
#topSeaech {
  width: 100%;
  height: 50px;
  /* background-image: linear-gradient(to right, red, yellow); */
  /* background-image: linear-gradient(to right, #c1bfbd, #9a9897, white); */
  background: #fff;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  position: fixed;
  top: 0;
  z-index: 1999;
  border-bottom:1px solid #e8e8e8;
}

.mint-navbar.is-fixed {
  top: 50px;
}

#topSeaech > #search {
  width: 80%;
  height: 40px;
  background: #dcdbda;
  /* background: #fff; */
  border-radius: 4px;
  display: flex;
  justify-content: center;
  align-items: center;
}

#topSeaech > #search > a {
  display: block;
  text-decoration: none;
  width: 90%;
  height: 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

#topSeaech > #search span {
  width: 35%;
  font-size: 14px;
  color: #fff;
  /* color: #000; */

  text-align: center;
  height: 30px;
  line-height: 30px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

#topSeaech > #search div{
  color:#fff;
}

#topSeaech > #search span:nth-child(2) {
  width: 45%;
}

#topSeaech > #search img {
  width: 10%;
  height: 20px;
}

#topSeaech > #writer {
  width: 17%;
  height: 40px;
}

#topSeaech > #writer > a {
  display: block;
  width: 100%;
  height: 100%;
  text-decoration: none;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

#topSeaech > #writer > a img {
  width: 30px;
  height: 30px;
}

#topSeaech > #writer > a p {
  color: #acacab;
  /* color: #000; */
  font-size: 10px;
}

/* 选项卡设置 */
.mint-navbar .mint-tab-item.is-selected {
  /* background: #9e9e9e; */
  color: red;
  border: none;
}

/* 选项卡高度 */
.mint-navbar .mint-tab-item {
  padding: 15px;
}

/* 内容区域样式开始 */
/* 设置于上边框距离 */
#distance {
  width: 100%;
  height: 95px;
}
.distance {
  width: 100%;
  height: 50px;
}
/* 内容区域 */
#container {
  width: 90%;
  height: auto;
  margin: 20px auto;
  /* background: green; */
}

#container #header {
  width: 100%;
  height: 70px;
  display: flex;
  justify-content: space-between;
}
/* 头像 简介 展开标志 */
#container #avatar {
  width: 20%;
  background: #fff;
}

#container #avatar > a {
  text-decoration: none;
  display: flex;
  width: 100%;
  height: 100%;
  justify-content: center;
  align-items: center;
}

#container #avatar > a img {
  width: 50px;
  height: 50px;
  border-radius: 3px;
}

#container #msg {
  width: 70%;
  /* background: orange; */
}

#container #msg .msg_title {
  width: 96%;
  margin-left: 4%;
  height: 25px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

#container #msg .msg_title .span {
  display: block;
  font-size: 17px;
  padding-top: 5px;
  width: 180px;
  text-align: left;
  color: orange;
  white-space: nowrap; /*溢出不换行*/
  overflow: hidden; /* 超出部分影藏 */
  text-overflow: none; /* 溢出部分用省略号显示 */
}

#container #msg .msg_title img {
  width: 25px;
  height: 25px;
  margin-right: 20px;
}
#container #msg .msg_title:nth-child(1) {
  margin-top: 5px;
}

#container #msg .msg_title:nth-child(2) {
  justify-content: flex-start;
  height: 35px;
  margin-top: 4px;
}

#container #msg .msg_title:nth-child(2) span:nth-child(2) {
  padding: 0 10px;
}

#container #msg .msg_title:nth-child(2) span {
  font-size: 14px;
  height: 100%;
  text-align: center;
  line-height: 35px;
}

#container #msg .msg_title:nth-child(2) span:last-child {
  text-align: left;
  max-width: 84px;
  white-space: nowrap; /*溢出不换行*/
  overflow: hidden; /* 超出部分影藏 */
  text-overflow: ellipsis; /* 溢出部分用省略号显示 */
}

#container #unflod {
  width: 10%;
  /* background: red; */
  display: flex;
  justify-content: center;
  position: relative;
}

#container #unflod img {
  width: 25px;
  height: 25px;
  margin-top: 7px;
}

/* 弹出区域制作 */
#eject {
  width: 200px;
  height: auto;
  background: #000;
  opacity: 0.6;
  border-radius: 12px;
  position: absolute;
  right: 15px;
}

#eject li {
  width: 80%;
  height: 40px;
  border-bottom: 1px solid gray;
  margin: 0 auto;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

#eject li img {
  width: 25px;
  height: 25px;
  margin-left: 20px;
}

#eject li span {
  font-size: 15px;
  text-align: center;
  line-height: 30px;
  width: auto;
  padding-left: 30px;
  color: #fff;
}

#eject li:last-child {
  display: flex;
  justify-content: center;
}

#eject li:last-child span {
  text-align: center;
  padding-left: 0;
}

/* 文章简介 */
#shortArticle {
  width: 100%;
  height: auto;
  margin-bottom: 20px;
}
#shortArticle > a {
  display: block;
  width: 100%;
  height: auto;
  text-decoration: none;
}
#shortArticle a > p {
  font-size: 18px;
  line-height: 28px;
  font-family: 宋体;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
  overflow: hidden;
  color: #000;
  text-align: left;
}

/* 主图片区域 */
#mainImag {
  width: 100%;
  height: 300px;
}

#mainImag > a {
  display: block;
  width: 100%;
  height: 300px;
}
#mainImag > a img {
  width: 100%;
  height: 300px;
  display: block;
}

/* 底部选像区域 */
#bottcar {
  width: 100%;
  height: 60px;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

/* 内容区域样式结束 */
#bottcar div {
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
}

#bottcar div:nth-child(2) > a {
  display: block;
  text-decoration: none;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
}

#bottcar div img {
  width: 20px;
  height: 20px;
}

#bottcar div span {
  font-size: 17px;
  line-height: 17px;
  text-align: center;
  padding: 0 14px;
  width: auto;
}

/* 评论区开始  */
#comment {
  width: 90%;
  height: 427px;
  /* background: gray; */
  background: #fff;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  position: fixed;
  z-index: 2999;
  top: 200px;
}

/* 标题栏开始 */
#comment #title {
  width: 90%;
  height: 40px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  display: flex;
  justify-content: space-between;
  background: #c4c2c2;
  align-items: center;
  position: fixed;
  z-index: 200;
}

#comment #title img {
  width: 20px;
  height: 20px;
}

#comment #title > div {
  width: 40px;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  border-top-right-radius: 10px;
}

#comment #title p {
  font-size: 15px;
  text-align: center;
  /* color: rgba(255, 255, 255, 0.5); */
  color: #fff;
}

/* 评论区大小*/
.comDis {
  width: 100%;
  height: 349px;
  position: absolute;
  top: 40px;
  z-index: 5;
  overflow: scroll;
  /* background: rgba(255, 255, 255, 0.2); */
  background: #e8e4e4;
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

/* 底部评论区 */
#comText {
  width: 90%;
  height: 40px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  /* background: gray; */
  background: #c4c2c2;
  /* background: #f6f4f4; */
  position: fixed;
  bottom: 41px;
}

#comText #text {
  width: 50%;
  height: 20px;
  background: #fff;
  margin-left: 10px;
  border-radius: 3px;
}

#comText #text input {
  display: block;
  width: 100%;
  height: 100%;
  border-radius: 3px;
  border-width: 0;
  color: gray;
  font-size: 13px;
  text-align: left;
}

#comText #img {
  width: 40px;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
#comText #img:last-child {
  padding-left: 77px;
}
#comText #img img {
  width: 20px;
  height: 20px;
}

/* 分割线 */
#split {
  width: 90%;
  height: 1px;
  margin: 10px auto;
  background-image: linear-gradient(to right, gray, gray, black, gray, white);
}

/* 轮播图开始 */
#swiper {
  width: 100%;
  height: 130px;
  position: fixed;
  z-index: 1000;
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  background: #fff;
}

#swiper #swiperImg {
  width: 60%;
  height: 96%;
  background: red;
  /* opacity: 0.5; */
  /* margin:0 auto; */
}

#swiper #swiperImg:nth-child(2) {
  width: 40%;
  height: 96%;
  background: red;
  opacity: 0.5;
  /* margin:0 auto; */
}
/* 轮播图结束 */
/* 心灵专区样式开始 */
#soulZoneContainer {
  width: 90%;
  height: auto;
  background: #fffafa;
  margin: 0 auto;
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  margin-top: 130px;
  /* position: absolute; */
  /* top:125px; */
}

#soulZoneContainer #soulZoneL,
#soulZoneR {
  width: 50%;
  height: auto;
}

#soulZoneContainer #soulZoneL .cartOne {
  width: 100%;
  height: 119px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding-bottom: 10px;
  /* background: #c9fad8; */
  background: #f4f3f2;
}

#soulZoneContainer #soulZoneL .cartOne p {
  font-size: 15px;
  text-align: left;
  padding: 3px 7px;
}

#soulZoneContainer #soulZoneL .cartOne p:nth-child(2) {
  text-align: left;
  line-height: 20px;
  max-height: 55px;
  font-family: 宋体;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
  overflow: hidden;
  color: #000;
  padding-left: 7px;
}

#soulZoneContainer #soulZoneL .cartOne p:nth-child(3) {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
}

#soulZoneContainer #soulZoneL .cartOne p:nth-child(3) .cartDou {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

#soulZoneContainer #soulZoneL .cartOne p:nth-child(3) img {
  width: 15px;
  height: 15px;
}

#soulZoneContainer #soulZoneL .cartOne p:nth-child(3) .cartDou > span {
  padding: 0 6px;
}

#soulZoneContainer #soulZoneL .cartOne p img {
  width: 17px;
  height: 17px;
}

#soulZoneContainer .cartPublicA {
  width: 100%;
  height: auto;
  /* background:red ; */
}

#soulZoneContainer .cartPublic {
  width: 100%;
  height: 306px;
  /* background:red ; */
}

#soulZoneContainer .cartPublic #cartImg {
  width: 97%;
  margin: 0 auto;
  height: 187px;
}

#soulZoneContainer .cartPublic #cartImg img {
  display: block;
  width: 100%;
  height: 187px;
  border-radius: 5px;
}
/* 心灵专区样式结束 */

/* 底部选项卡颜色 */
.tabbarColor {
  color: green;
}
.disTabbarColor {
  color: #000;
}

/* 底部固定设置 */
.mint-tabbar.is-fixed {
  z-index: 199;
}
</style>

<style>
/* 吐司 */
.toast {
  background: #fff;
  font-size: 5px;
  margin-top: 7px;
}

.toastx {
  background: green;
  font-size: 5px;
  margin-top: 7px;
}

.mint-toast {
  background-color: red !important;
}
</style>

<script>
import { Toast } from "mint-ui";
export default {
  filters: {
    publicTime(val) {
      return val.substring(0, 10);
      }
  },

  data() {
    return {
      selected: "homeBarx",
      isProvo: null,
      isProvoCom: null,
      isProvoAno: null,
      isProvoRecom: null,
      numProve: 4444,
      // 保存单击时序号
      num: null,
      // 评论区开关
      commentOff: null,
      commentOffAnony: null,
      commentOffRecom: null,
      // 评论区点赞
      comAssist: null,
      // 评论信息
      comMsg: "写评论",
      data: null,

      // 判断是否触底了
      loading: false,

      // 轮播图片数组
      swipeImg: [
        "swiper1.jpg",
        "swiper2.jpg",
        "swiper3.jpg",
        "swiper4.jpg",
        "swiper5.jpg",
      ],

      // 推荐分页
      recommendPno: 1,
      //推荐页数据
      recommendData: [],
      answerRecomData: [],

      // 公开(public)分页
      publicPno: 1,
      publicData: [],
      publicPageSize: null,

      // 匿名分页
      anonymousPno: 1,
      anonymousData: [],
      answerAnonyData: [],
      pageSizeAnony: 1,

      //评论
      answerData: [],

      //心灵辅导专区
      heartPno: 1,
      heartData: [],

      // 公开头像，校园霸凌
      publicAvatar: [
        "pubAvar01.jpg",
        "pubAvar02.jpg",
        "pubAvar03.jpg",
        "pubAvar04.jpg",
        "pubAvar05.jpg",
        "pubAvar06.jpg",
        "pubAvar07.jpg",
        "pubAvar08.jpg",
        "pubAvar09.jpg",
        "pubAvar10.jpg",
      ],

      // 匿名头像 霸凌
      anonymousAvarar: [
        "anonymousAvatar01.jpg",
        "anonymousAvatar02.jpg",
        "anonymousAvatar03.jpg",
        "anonymousAvatar04.jpg",
        "anonymousAvatar05.jpg",
        "anonymousAvatar06.jpg",
        "anonymousAvatar07.jpg",
        "anonymousAvatar08.jpg",
        "anonymousAvatar09.jpg",
        "anonymousAvatar10.jpg",
      ],

      // 公开主图片，校园霸凌
      publicMainImg: [
        "publicMainImg01.jpg",
        "publicMainImg02.jpg",
        "publicMainImg03.jpg",
        "publicMainImg04.jpg",
        "publicMainImg05.jpg",
        "publicMainImg06.jpg",
        "publicMainImg07.jpg",
        "publicMainImg08.jpg",
        "publicMainImg09.jpg",
        "publicMainImg10.jpg",
      ],

      // 匿名主图片，校园霸凌
      anonymousMainImg: [
        "annoyMainImg01.jpg",
        "annoyMainImg02.jpg",
        "annoyMainImg03.jpg",
        "annoyMainImg04.jpg",
        "annoyMainImg05.jpg",
        "annoyMainImg06.jpg",
        "annoyMainImg07.jpg",
        "annoyMainImg08.jpg",
        "annoyMainImg09.jpg",
        "annoyMainImg10.jpg",
      ],

      // 心灵专区图片
      HeartImg: [
        "heartYoung01.jpg",
        "heartYoung02.jpg",
        "heartYoung03.jpg",
        "heartYoung04.jpg",
        "heartYoung05.jpg",
        "heartYoung06.jpg",
        "heartYoung07.jpg",
        "heartYoung08.jpg",
        "heartYoung09.jpg",
        "heartYoung10.jpg",
        "heartYoung11.jpg",
        "heartYoung12.jpg",
      ],

      // 职称
      job: ["工程师", "设计师", "学生", "教师", "路人"],

      recomName: ["alice", "yuan", "艾克", "ma", "Lsy"],
    };
  },

  mounted() {
    //掉推荐页数据
    this.recommendNews();

    // 公开动态接口
    this.publicNews();

    // 匿名动态接口
    // this.annoymousNews();

    //心灵专区动态接口
    // this.heartNews();
  },

  methods: {
    // 判断页面所处位置
    isPageLocation() {
      if (this.isPageNum == null || this.isPageNum == 1) {
        this.selected = "homeBarx";
      } else if (this.isPageNum == 2) {
        this.selected = "homeBar";
      }
    },

    //公开评论
    doAnswer(id, index) {
      this.commentOff = index;
      let url = `/api/bully-publish/publish/answers/model/${id}?page=1`;
      this.$axios.get(url).then((res) => {
        console.log("resAnswer", res);
        this.answerData = res.data.data.list;
      });
    },

    // 心灵辅导专区
    heartNews() {
      let url =
        "/api/bully-publish/publish/models/soul/model?page=" + this.heartPno;
      this.$axios.get(url).then((res) => {
        this.heartData = this.heartData.concat(res.data.data.list);
        console.log("heartNews", this.heartData);
      });
    },

    loadMoreHeart() {
      if(this.selected == 'heartBar'){
      if(this.anonymousPno <= 7){
      this.loading = true;
      this.anonymousPno = ++this.anonymousPno;
      this.heartNews();
      }else{
        Toast({
            message: "没有更多了！",
            position: "bottom",
            iconClass: "icon icon-success",
            className: "toast",
            duration: 500,
          });
      }
      }
      this.loading = false;
    },

    
    // 匿名动态
    annoymousNews() {
      let url =
        "/api/bully-publish/publish/models/private/feignAll?page=" +
        this.anonymousPno;
      this.$axios.get(url).then((res) => {
        console.log("annoymousNews", res);
        let m = this.anonymousData;
        this.anonymousData = this.anonymousData.concat(res.data);
      });
    },

    loadMoreAnonymous() {
      if(this.selected == 'anonymousBar'){
      if (this.anonymousPno <= 10) {
        this.loading = true;
        this.anonymousPno = ++this.anonymousPno;
        this.annoymousNews();
      } else {
        Toast({
          message: "没有更多了",
          position: "center",
          className: "toast",
          duration: 500,
        });
      }
      }

      this.loading = false;
    },

    closeCommentAnony() {
      this.commentOffAnony = null;
    },

    openCommentAno(id, index) {
      this.commentOffAnony = index;
      let url = `/api/bully-publish/publish/answers/model/${id}?page=1`;
      this.$axios.get(url).then((res) => {
        console.log("answerAnonyData", res);
        this.answerAnonyData = res.data.data.list;
      });
    },

    //匿名点赞数设置
    doProveAnonymous(id, index) {
      this.isProvoAno = this.isProvoAno == null ? index : null;
      if (this.isProvoAno == index) {
        let url = `/api/bully-publish/publish/models/${id}/like`;
        this.$axios.get(url).then((res) => {
          console.log("proveAno", res);
          if (res.status == 200) {
            this.anonymousData[index].likes =
              this.anonymousData[index].likes + 1;
          } else {
            console.log("点赞失败！");
          }
        });
      }

      if (this.isProvoAno == null) {
        this.anonymousData[index].likes = this.anonymousData[index].likes - 1;
      }
    },

    // 显示推荐动态
    recommendNews() {
      let url = "/api/bully-publish/publish/models/likes";
      this.$axios.get(url).then((res) => {
        console.log("recommendNews", res);
        this.recommendData = res.data.data;
      });
    },

    closeRecomComment() {
      this.commentOffRecom = null;
    },

    openCommentRecom(id, index) {
      this.commentOffRecom = index;
      let url = `/api/bully-publish/publish/answers/model/${id}?page=1`;
      this.$axios.get(url).then((res) => {
        console.log("answerRecomData", res);
        this.answerRecomData = res.data.data.list;
      });
    },

    //推荐动态点赞
    doProveRecom(id, index) {
      this.isProvoRecom = this.isProvoRecom == null ? index : null;
      if (this.isProvoRecom == index) {
        let url = `/api/bully-publish/publish/models/${id}/like`;
        this.$axios.get(url).then((res) => {
          console.log("doProveRecom", res);
          if (res.status == 200) {
            this.recommendData[index].likes =
              this.recommendData[index].likes + 1;
          } else {
            console.log("点赞失败！");
          }
        });
      }

      if (this.isProvoRecom == null) {
        this.recommendData[index].likes = this.recommendData[index].likes - 1;
      }
    },

    // 删除公开动态
    delPublic(id, index) {
      let url = `/api/bully-publish/publish/models/${id}/delete`;
      console.log('delPublicUrl',url);
      this.$axios.get(url).then((res) => {
        console.log("delPuclic", res);
        if (res.data.state == 2000) {
            this.publicData.splice(index, 1);
          Toast({
            message: "删除成功！",
            position: "bottom",
            iconClass: "icon icon-success",
            className: "toast",
            duration: 500,
          });
        }
      }).catch(err=>{
        Toast({
            message: "权限不足，仅个人和管理员可删除！",
            position: "bottom",
            iconClass: "icon icon-success",
            className: "toast",
            duration: 2000,
          });
      });

      //
      // console.log(this.publicData, index);

      this.num = null;
    },

    // 删除推荐动态
    doDelRecom(id, index) {
        let url = `/api/bully-publish/publish/models/${id}/delete`;
      this.$axios.get(url).then((res) => {
        console.log("doDelRecom", res);
        if (res.data.state == 2000) {
            this.recommendData.splice(index, 1);
          Toast({
            message: "删除成功！",
            position: "bottom",
            iconClass: "icon icon-success",
            className: "toast",
            duration: 500,
          });
        }
      }).catch(err=>{
        Toast({
            message: "权限不足，仅个人和管理员可删除！",
            position: "bottom",
            iconClass: "icon icon-success",
            className: "toast",
            duration: 2000,
          });
      });;

      //
      // console.log(this.publicData, index);

      this.num = null;
    },

    // 删除匿名动态
    doDelAnonymous(id, index) {
      let url = `/api/bully-publish/publish/models/${id}/delete`;
      console.log('doDelAnonymousUrl',url);
      this.$axios.get(url).then((res) => {
        console.log("doDelAnonymous", res);
        if (res.data.state == 2000) {
            this.anonymousData.splice(index, 1);
          Toast({
            message: "删除成功！",
            position: "bottom",
            iconClass: "icon icon-success",
            className: "toast",
            duration: 500,
          });
        }
      }).catch(err=>{
        Toast({
            message: "权限不足，仅个人和管理员可删除！",
            position: "bottom",
            iconClass: "icon icon-success",
            className: "toast",
            duration: 2000,
          });
      });;

      //
      // console.log(this.publicData, index);

      this.num = null;
    },

    // 公开动态接口
    publicNews() {
      if (this.publicPno <= 9) {
        let url =
          "/api/bully-publish/publish/models/public/all?page=" + this.publicPno;
        this.$axios.get(url).then((res) => {
          // console.log("publicNews", res.data.data.publicAll.list);
          console.log("publicNews", res);
          this.publicData = this.publicData.concat(
            res.data.data.publicAll.list
          );
          this.publicPageSize = res.data.data.publicAll.pageSize;
        });
      }
    },

    doProve(index, id) {
      this.isProvo = this.isProvo == null ? index : null;
      if (this.isProvo == index) {
        let url = `/api/bully-publish/publish/models/${id}/like`;
        console.log("url=", url);
        this.$axios.get(url).then((res) => {
          console.log("prove", res);
          if (res.status == 200) {
            this.publicData[index].likes = this.publicData[index].likes + 1;
            console.log("publicData", this.publicData);
          } else {
            console.log("点赞失败！");
          }
        });
      }

      if (this.isProvo == null) {
        this.publicData[index].likes = this.publicData[index].likes - 1;
      }
    },
    // 确定展开值
    isOpen(index) {
      this.num = index;
      console.log(this.num);
    },

    // 关闭弹窗
    close() {
      this.num = null;
    },

    // 评论区开关
    openComment(index) {
      this.commentOff = index;
    },
    closeComment() {
      this.commentOff = null;
    },

    // 评论区点赞
    doComAssist(index) {
      this.comAssist = index;
    },

    loadMore() {
      this.loading = true;
      this.recommendPno = this.recommendPno + 1;
      this.recommend();
      this.loading = false;
    },

    // 公开动态分页
    loadMorePublic() {
         if(this.selected == 'homeBarx'){
      this.loading = true;
      this.publicPno = this.publicPno + 1;
      if (this.publicPno <= this.publicPageSize) {
        this.publicNews();
      } else {
        Toast({
          message: "没有更多了",
          position: "bottom",
          className: "toast",
          duration: 500,
        });
      }
      }

      this.loading = false;
    },

    // 跳到专区
    myBlock() {
      this.$router.push("/userinfo");
    },

    // 跳到 日记
    noteBlock() {
      this.$router.push("/myriji");
    },
  },
};
</script>
