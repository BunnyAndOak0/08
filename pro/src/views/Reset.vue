 
 
<template >

  <div class="ds" >

     
    <mt-header title="修改密码">

      <router-link to="/Forget" slot="left">
        <mt-button icon="back"></mt-button>
      </router-link>

      <router-link to="/Login" slot="right">
        <mt-button > 返回登录 </mt-button>
      </router-link>

    </mt-header> 
   
    <mt-field
      class="f2"
        type="password"
        label="密码"
        placeholder="请输入密码"
        v-model="password"
        @blur.native.capture="checkPassword"
        :state="passwordState"
      ></mt-field>
      <mt-field
      class="f3"
        type="password"
        label="确认密码"
        placeholder="请再次输入密码"
        v-model="conpassword"
        @blur.native.capture="checkConpassword"
        :state="conpasswordState"
      >
      </mt-field>

     

      <mt-button class="b1" type="primary" size="normal" @click="register">修改密码</mt-button>

  </div>
</template>
<script>
 
export default {

  data() {
    return {
      
      //密码初始值
      password: "",
      //确认密码初始值
      conpassword: "",
     
      //密码状态的初始值
      passwordState: "",
      //确认密码状态的初始值
      conpasswordState: ""
    };
  },
  methods: {
    
    //检测密码
    checkPassword() {
      let passwordExp = /^[a-zA-Z0-9_]{8,20}$/;
      if (passwordExp.test(this.password)) {
        this.passwordState = "success";
        return true;
      } else {
        this.passwordState = "error";
        return false;
      }
    },
    //检测确认密码
    checkConpassword() {
      
      if (this.password != this.conpassword) {
        this.conpasswordState = "error";
        return false;
      } else {
        this.conpasswordState = "success";
        return true;
      }
      //可简化成
      //this.conpasswordState = this.password != this.conpassword ? 'error' : 'success';
      //return this.password != this.conpassword;
    },
    //用户单击注册按钮时,进行验证
    async register() {
      if (this.checkPassword() && this.checkConpassword()) {
        //console.log("要发送AJAX请求到服务器了");

        const res = await this.$http.post('/register',{
          username:this.username,         
        })

        Toast({
        message: '修改成功',
        iconClass: ''
        });
        //this.$router.push('/');
      } else {
        
        Toast({
        message: '修改失败',
        iconClass: ''
        });

      }
    }
  }
};
</script>


<style scoped>



.ds{
    margin:0;
    padding: 0;
    box-sizing: border-box;
    background-color: #f4f4f4;
    
    background-size: cover;
    background-image: url('../assets/b.jpg');
}


.f2{
    margin: 9.167vw 3.16vw;
    border-radius:45px;
    border:2px solid #ffffff;
    background:  rgba(255, 255, 255, 0.5);
    background-image: url('../assets/pow.png');
    background-repeat:no-repeat;
    background-position:left center;
}

.f3{
    margin: 9.167vw 3.16vw;
    border-radius:45px;
    border:2px solid #ffffff;
    background:  rgba(255, 255, 255, 0.5);
    background-image: url('../assets/pow.png');
    background-repeat:no-repeat;
    background-position:left center;
}





.b1{
     margin-left: 22.16vw;
     margin-bottom: 101vw;
     width: 200px;
    border-radius:45px;
    border:2px solid #ffffff;
    background:  rgba(98, 175, 248, 0.8);
}

</style>
