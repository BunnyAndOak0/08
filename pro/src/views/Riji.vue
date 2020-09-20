<template>
    <div>
        <div id="top">
            <span>发布日志</span>
        </div>
        <div id="bg">
            <mt-field  
                    placeholder="请书写你的标题" 
                 type="textarea" 
                 rows="2" 
                 v-model="title"
       >

       </mt-field>
             <mt-field  placeholder="开始编写日志" 
                 type="textarea" 
                 rows="10" 
                 v-model="diary"
       >

       </mt-field>
         </div>
         <div id="buttom">
             <mt-button type="primary" size="small" @click="submit" id="right">发表</mt-button>
              <mt-button type="default" size="small" @click="cancel">取消</mt-button>
         </div>
      
         
    </div>
</template>
<style scoped>
    #bg{
        height: 100%;
}
  #top{
      text-align: center;
      line-height: 35px;
      background-color: #e0a4a4;
      border-bottom: 2px;
      height: 35px;
  }
  #top>span{
      font-size: 15px;
      font-weight: 400;
  }
  #buttom{
      margin-top: 262px;
      background-color: rgba(224,164,164,0.7);
  }
    #right{
        margin-right: 5px;
    }
</style>
<script>
export default {
    data(){
        return{
            title:"",
            diary:"",
            //timer: "",//定义一个定时器的变量
            //currentTime: new Date(), // 获取当前时间
        };
    },
   
    mounted(){


    },
    methods: {
        /* created() {
    var _this = this; //声明一个变量指向Vue实例this，保证作用域一致
    this.timer = setInterval(function() {
      _this.currentTime = //修改数据date
        new Date().getFullYear() +
        "-" +
        (new Date().getMonth() + 1) +
        "-" +
        new Date().getDate() +
        " " +
        new Date().getHours() +
        ":" +
        new Date().getMinutes() +
        ": " +
        new Date().getSeconds();
    }, 1000);
  },
beforeDestroy() {
    if (this.timer) {
      clearInterval(this.timer); // 在Vue实例销毁前，清除我们的定时器
    }
  },*/
        submit(){
            let that = this;
           /*this.created();
           this.beforeDestroy();
        console.log(this.currentTime);*/
        
        let url = 'api/bully-diary/diary/write';
        
           this.$axios.post(url, {
               
                    diaryTitle:this.title,
                    diaryContent:this.diary,
                    /*currentTime:this.currentTime*/
               
           })
           .then(function (res) {
               console.log(res);
               //console.log(res.data.state);
               if(res.data.state==2000){
                  console.log("上传成功");
                   that.$router.push({name:"MyRiji"})
               }
           })
           .catch(function (error) {
               throw error;
           })
            
        },
        cancel(){
            this.title="";
            this.diary="";
            this.$router.push('/myriji')
            console.log("取消发表");
        }
    }
};
</script>