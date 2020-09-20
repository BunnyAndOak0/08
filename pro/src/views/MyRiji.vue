<template>
  <div>
    <div id="top" @click="backhome">
      <mt-cell title="我的日志" id="left"></mt-cell>
      <router-link to='/'></router-link>
    </div>
    <div id="border"></div>

    <div id="xieriji">
      <mt-cell title="写日志"  is-link to="/riji">
        <img slot="icon" src="../assets/images/T.png" width="24" height="24" />
      </mt-cell>
    </div>
    <!--  
                 v-for="(result,index) of results"
                
                 :id="result.diary_id" 
                
    :key="index"-->

     <!-- <div id="update" v-show="up">
        <mt-field placeholder="请书写你的标题" type="textarea" rows="1" v-model="title"></mt-field>
        <mt-field placeholder="修改你的日志" type="textarea" rows="2" v-model="diary"></mt-field>
        <mt-button plain size="small" id="left" @click="confirm(index)">确定</mt-button>
        <mt-button plain size="small" id="right" @click="cancel">取消</mt-button>
      </div> -->
    <div class="body" v-for="(result,index) of results" :Id="result.diaryId" :key="index">
      <!-- 修改弹出框-->
     
      <div id="bt">
        <img src="../assets/images/head.png" alt="nini5200430" />
        <a href="#">alice</a>
      </div>
      <div id="bm" @click="toDetail(result)">
        <p id="pt">{{result.diaryTitle}}</p>
        <p id="pc">{{result.diaryContent}}</p>
      </div>
      <div id="bb">
        <mt-button plain size="small" id="bl" @click="dianzan">
          <img src="../assets/images/happy.png" height="20" width="20" slot="icon" />
          开心
        </mt-button>
        <mt-button plain size="small" @click="dislike">
          <img src="../assets/images/unhappy.png" height="20" width="20" slot="icon" />
          难过
        </mt-button>
        <mt-button plain size="small" id="br" @click="toUpdate(result)">
          <img src="../assets/images/xiugai.png" height="20" width="20" slot="icon" />
          修改
        </mt-button>
        <mt-button plain size="small" @click="shanchu(index)">
          <img src="../assets/images/huishouzhan.png" height="20" width="20" slot="icon" />
          删除
        </mt-button>
      </div>
    </div>
  </div>
</template>
<style scoped>
 /* #update > #right {
  background-color: rgba(194, 139, 77, 0.6);
}
 #update > #left {
  margin-right: 20px;
  background-color: rgba(219, 161, 127, 0.6);
}
#update {
  border: 1px solid gray;
  position: absolute;
  top: 0px;
  left: 0px;
  margin: 2.5px;
  width: 366px;
  height: 156px;
  background-color: white;
  padding-right: 2px;
} */
#top > #left {
  margin-left: 10px;
  margin-bottom: 2px;
}
#border {
  width: 100%;
  height: 2px;
  background-color: #d4237a;
}
#top {
  background-color: #d4237a;
}
#xieriji {
  margin-bottom: 0px;
}
.body {
    position: relative;
  background-color: #edebd0;
  margin-bottom: 5px;
}
#bt > img {
  padding-left: 10px;
  width: 34px;
  height: 36px;
}
#bt > a {
  text-decoration: none;
  color: #515151;
  margin-left: 15px;
  font-size: 18px;
}
#bm {
  margin-top: 8px;
}
#bm > #pt {
  font-size: 18px;
  font-weight: 500;
  display: block;
  margin-left: 10px;
  margin-right: 10px;
  text-indent: 2em;
  text-align: center;
  margin-bottom: 8px;
}
#bm > #pc {
  font-size: 16px;
  line-height: 1.3em;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  height: 80px;
  margin-left: 10px;
  margin-right: 10px;
  text-indent: 2em;
  text-align: center;
}
#bb > button {
  margin: 2px;
}
#bb > #bl {
  margin-left: 10px;
}
#bb > #br {
  margin-left: 30px;
}
#bb {
  margin-top: 10px;
}
.tosthappy {
  background-color: rgba(194, 136, 102, 0.6);
}
</style>
<script>
import { Toast } from "mint-ui";
export default {
  data() {
    return {
      results: [],
      title: "",
      diary: "",
      data1: "天天开心！",
      data2: "别难过，明天会更好！",
      data3:"删除成功",
      up: false,
    };
  },
  
  // {
  //         params: {
  //             id:this.results
  //         }
  mounted() {
    this.getData();
  }, 
  methods: {
    getData() {
      let url = "api/bully-diary/diary/";
      this.$axios.get(url).then((res) => {
        console.log(res);
        if (res) {
          this.results = res.data.obj;
        }
      });
    },
    backhome(){
      this.$router.push('/');
    },
    // confirm(index) {
    //   let that = this;
    //   console.log(this.results[index].diaryId);
    //   let url =
    //     "http://yym2417581902.qicp.net/portal/diary/" +
    //     this.results[index].diaryId +
    //     "/update";

    //   this.axios
    //     .post(url, {
    //       diaryTitle: this.title,
    //       diaryContent: this.diary,
    //     })
    //     .then(function (res) {
    //       console.log(res);
    //       if (res.data.state == 2000) {
    //         console.log("修改成功");

    //         that.up = false;
    //         that.getData();
    //       }
    //     })
    //     .catch(function (error) {
    //       throw error;
    //     });
    // },
    // cancel() {
    //   this.title = "";
    //   this.diary = "";
    //   this.up = false;
    //   console.log("取消修改");
    // },
    dianzan() {
      //    this.flag=this.results[index].flag;
      // this.show=!this.show;
      Toast({
        message: this.data1,
        position: "bottom",
        duration: 2000,
        className: "tosthappy",
      });
    },

    dislike() {
      //this.display=!this.display;
      Toast({
        message: this.data2,
        position: "bottom",
        duration: 2000,
        className: "tosthappy",
      });
    },
    toUpdate(result) {
      console.log(result.diaryId);
      window.sessionStorage.setItem("id", result.diaryId);
      this.$router.push({ name: "Update", query: { Id: result.diaryId } });
    },
    shanchu(index) {
      //console.log(id);
      let url =
        "api/bully-diary/diary/" +
        this.results[index].diaryId +
        "/delete";
      this.$axios
        .get(url, {
          id: this.results.diary_id,
        })
        .then((res) => {
          console.log("删除成功");
          this.getData();
          Toast({
        message: this.data3,
        position: "bottom",
        duration: 2000,
        className: "tosthappy",
      });
        })
        .catch(function (error) {
          throw error;
        });
    },
    toDetail(result) {
      console.log(result.diaryId);
      window.sessionStorage.setItem("id", result.diaryId);
      this.$router.push({ name: "Detail", query: { Id: result.diaryId } });
    },
  },
};
</script>