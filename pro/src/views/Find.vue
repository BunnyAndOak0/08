<template>

  <div  class="ds">     
    <mt-header title="忘记密码">

      <router-link to="/Login" slot="left">
        <mt-button icon="back"></mt-button>
      </router-link>
    </mt-header>

    <mt-field class="f1" type="text" label="手机号" placeholder="请输入手机号" v-model=phone value='phone'
		>
      <mt-button class="b2" type="primary" size="normal" @click="fasong">发送验证码</mt-button>
      </mt-field>

      <mt-field
      class="f5"
        type="text"
        label="验证码"
        placeholder="请输入验证码"
        v-model="code"
      >
      </mt-field>

      <!-- 密码 -->
      <mt-field
      class="f2"
        type="password"
        label="密码"
        placeholder="6~20位字符，区分大小写"
        v-model="password"
        @blur.native.capture="checkPassword"
        :state="passwordState"
      ></mt-field>

      <!-- 确认密码 -->

     
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

   
      <mt-button class="b1" type="primary" size="normal" @click="next">修改密码</mt-button>
  
  </div>
</template>
<script>
     
export default {
  
     data() {
    return {
      model:{},
      code:"",
      phone:"",
      phoneState:"",
      password: "",
      //确认密码初始值
      conpassword: "",
     
      
      //密码状态的初始值
      passwordState: "",
      //确认密码状态的初始值
      conpasswordState: "",
    };
  },



   methods: {
    
     
      Ph(){
          
			    
				this.$axios.get("api/bully-user/user/getUser").then(resp => {
					if (resp) {
						console.log(resp);
						this.model = resp.data.data
						console.log(resp.data)
						this.phone = this.model.phone
					}
				})
			
		
      },

      checkPassword() {
      let passwordExp = /^[a-zA-Z0-9_]{6,20}$/;
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
    
    },



      
      fasong(){
      
        // console.log("要发送AJAX请求到服务器了");
        //this.$router.push('/');
          let param = new URLSearchParams()
          
          param.append('phone', this.phone)
        
				 
					this.axios({
						method: 'post',
						url: 'http://f3346291w6.zicp.vip:20504/api/bully-user/user/forget/sendCode',
						data: param
					}).then(res => {
						console.log(res)
						if (res) {
                
                console.log(res)
                console.log(this.phone)

						   	
						}
					})

				

     },


     //修改
     next() {
      if ( this.checkPassword() && this.checkConpassword()) {
        // console.log("要发送AJAX请求到服务器了");
        //this.$router.push('/');
         
         


          let param = new URLSearchParams()
          
          param.append('password', this.password)
         param.append('phone', this.phone)
          param.append('code', this.code)
				
					this.axios({
						method: 'post',
						url: 'http://f3346291w6.zicp.vip:20504/api/bully-user/user/forget/password',
						data: param
					}).then(res => {
						console.log(res)
						if (res) {

					console.log("修改成功")

					this.$router.push({
						name: "login"
					});

							
						}
					})

				} else {
					// console.log("错误");
					Toast({
						message: '密码错误',
						iconClass: ''
					});
				}
    },

    


   },

  created (){
      this.Ph()
    },

}
</script>



<style scoped>



.ds{
    margin:0;
    padding: 0;
    box-sizing: border-box;
    background-color: #f4f4f4;
    
    background-size: cover;
    background-image: url('../assets/4.jpg');
}



.f1{
    
    margin:  10vw  4.16vw 0;
    border-radius:45px;
    border:2px solid #ffffff;
    background:  rgba(255, 255, 255, 0.5);
    background-image: url('../assets/user.png');
    background-repeat:no-repeat;
    background-position:left center;
    
     
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

.f5{
    margin: 9.167vw 3.16vw;
    border-radius:45px;
    border:2px solid #ffffff;
    background:  rgba(255, 255, 255, 0.5);
    background-image: url('../assets/yan.png');
    background-repeat:no-repeat;
    background-position:left center;
}



.b2{
     width: 80px; 
    border-radius:45px;
    border:0px solid #ffffff;
    background:  rgba(98, 175, 248, 0.8);
    display: inline-block;
    font-size: 13px;
}

.b1{
    /* margin-left: 22.16vw; */
    margin-bottom: 101vw;
    width: 200px;
    border-radius:45px;
    border:2px solid #ffffff;
    background:  rgba(98, 175, 248, 0.8);
}

</style>