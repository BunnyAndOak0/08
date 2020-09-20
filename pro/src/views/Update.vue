<template>
    <div>
        <div id="top">
            <span>修改日志</span>
        </div>
        <div id="bg">
            <mt-field  
                    placeholder="修改你的标题" 
                 type="textarea" 
                 rows="2" 
                 v-model="title"
                 
       >
       </mt-field>
             <mt-field  placeholder="修改你的日志" 
                 type="textarea" 
                 rows="10" 
                 v-model="diary"
       >
       </mt-field>
         </div>
         <div id="buttom">
             <mt-button type="primary" size="small" @click="confirm" id="left">确定</mt-button>
              <mt-button type="default" size="small" @click="cancel" id="right">取消</mt-button>
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
 #buttom>#left{
        margin-right: 15px;
        background-color: rgba(194, 139, 77, 0.6);
    }
    #buttom>#right{
        background-color: rgba(219, 161, 127, 0.6);;
    }
</style>
<script>
export default {
    data(){
        return{
            title:"",
            diary:"",
            results:{}
           
        };
    },
   
    mounted(){
    //    let diaryId = window.sessionStorage.getItem("id");
    // let url = 'http://yym2417581902.qicp.net/portal/diary/'+diaryId+'/details';
    
    //     this.axios.get(url)
    //     .then(res=>{
    //       console.log(res);
    //         this.results = res.data.obj;
    //         console.log("接收成功");
         
    //     })
    //     .catch(function (error) {
    //         throw error;
    //     })

    },
    methods: {
         confirm(){
      let diaryId = window.sessionStorage.getItem("id");
      let url =
        "api/bully-diary/diary/" +
        diaryId +
        "/update";

      this.$axios
        .post(url, {
          diaryTitle: this.title,
          diaryContent: this.diary,
        })
        .then(res=>{
          console.log(res);
          if (res.data.state == 2000) {
            console.log("修改成功");
            this.$router.push({ name: "MyRiji"});
          }
        })
        .catch(function (error) {
          throw error;
        });
    },
    cancel() {
      this.title = "";
      this.diary = "";
      this.$router.push({ name: "MyRiji"});
      console.log("取消修改");
    },
       
    }
};
</script>