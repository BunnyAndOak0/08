<template>
  <div>
    <div id="top">
      <img src="../assets/images/head.png" alt />
      <div id="name">
        <span>迪士尼在逃公主</span>
        <img src="../assets/images/qiqiu.png" alt />
        <h6>
        {{this.diaryTime}}</h6>
      </div>
      <div id="shoucang" @click="shoucan">
        <img src="../assets/images/wujiaoxin.png" alt=""  v-show="display"/>
          <img src="../assets/images/wujiaoxred.png" alt=""  v-show="!display"/>
      </div>
    </div>
      <div id="rizhi">
        <p id="pt">{{this.results.diaryTitle}}</p>
        <p id="pc">{{this.results.diaryContent}}</p>
    </div>
    <div id="bottom">
        <mt-button 
          plain 
          size="normal" 
          id="back"
          @click="back"

        >
          <img src="../assets/images/back.png" height="20" width="20" slot="icon" />
          返回
        </mt-button>
     
        <mt-button plain size="normal" id="bl">
          <img src="../assets/images/share.png" height="20" width="20" slot="icon" />
          分享
        </mt-button>

        <mt-button plain id="br" size="normal"  @click="dislike">
          <img src="../assets/images/before.png" height="20" width="20" slot="icon" v-show="show"/>
           <img src="../assets/images/after.png" height="20" width="20" slot="icon" v-show="!show"/>
          点赞
        </mt-button>
      </div>
     
    </div>

</template>
<style scoped>
#test{
  position: relative;
}
#test>#test1{
  height: 200px;
  background-color: black;
  position: absolute;
  margin-bottom: 30px;
}
#top {
  background-color: #f8f5f5;
  position: relative;
  height: 70px;
}
#top > img {
  position: absolute;
  top: 0px;
  left: 0px;
  width: 50px;
  height: 50px;
  margin: 5px;
}
#top > #shoucang > img {
  position: absolute;
  top: 15px;
  right: 20px;
  width: 35px;
  height: 35px;
}
#top > #name {
  padding: 10px;
  position: relative;
  left: -35px;
}
#top > #name > img {
  width: 25px;
  height: 25px;
}

 #bottom {
  margin-bottom: 5px;
  background-color: #f8f5f5;
}
#bottom > #bl {
  margin-left: 30px;
  margin-right: 15px;
}
#bottom > #br {
  margin-left: 15px;
}
#rizhi {
  background-color: rgba(238, 228, 186, 0.5);
  text-indent: 2em;
  text-align: justify;
 
  margin-top: 2px;
  margin-bottom: 3px;
  height: 545px;
}
#rizhi>#pt{
  font-size:18px;
      font-weight: 500;
       display: block;
       margin-left: 10px;
       margin-right: 10px;
       text-indent: 2em;
       text-align: center;
       margin-bottom: 8px;
       padding-top: 5px;
      
}
#rizhi>#pc{
     font-weight: 500;
      font-size: 16px;
      line-height: 1.5em;
       margin-left: 10px;
       margin-right: 10px;
       text-indent: 2em;
       text-align: center;
}

</style>
<script>
export default {
  data() {
    return {
      date: "",
      display:true,
      show:true,
      results:{},
      diaryTime:""
    };
  },
  mounted() {
    let diaryId = window.sessionStorage.getItem("id");
    let url = 'api/bully-diary/diary/'+diaryId+'/details';
    
        this.$axios.get(url)
        .then(res=>{
          console.log(res);
            this.results = res.data.obj;
            console.log("接收成功");
            //console.log(this.results.diaryCreatetime);
            this.diaryTime=this.results.diaryCreatetime.replace('T',' ');
        })
        .catch(function (error) {
            throw error;
        })
  },
  methods: {
    back(){
      this.$router.push({name:"MyRiji"})
    },
    shoucan() {
        this.display=!this.display;
    },
    dislike(){
         this.show=!this.show;
    }
  },
};
</script>