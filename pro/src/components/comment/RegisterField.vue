<template>
<div>
 <mt-field
      
      
        class="f1"
        type="text"
        label="用户名"
        placeholder="请输入用户名"
        v-model="username"
        @blur.native.capture="checkUsername"
        :state="usernameState"
      >
    
      </mt-field>

      <mt-field
      class="f2"
        type="password"
        label="密码"
        placeholder="6~20位字符，区分大小写"
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

      <mt-field class="f1" type="text" label="手机号" placeholder="请输入用户名" v-model=phone value='phone' @blur.native.capture="checkPhone"
		 :state="phoneState">
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

      <mt-button class="b1" type="primary" size="normal" @click="register">快速注册</mt-button>
      </div>
    
</template>


<script>
 
import { Toast } from "mint-ui";

export default {

  data() {
    return {

      code:"",
      //用户名的初始值
      username: "",
      //密码初始值
      password: "",
      //确认密码初始值
      conpassword: "",
      //用户名状态的初始值
      usernameState: "",
      //密码状态的初始值
      passwordState: "",
      //确认密码状态的初始值
      conpasswordState: "",
      
      phone:"",

      phoneState:""
    };
  },
  methods: {

    //检测用户名
    checkUsername() {
      let usernameExp =  /^.{1,16}/;
      if (usernameExp.test(this.username)) {
        this.usernameState = "success";
        return true;
      } else {
        this.usernameState = "error";
        return false;
      }
    },

    checkPhone() {
				let phoneExp = /^[0-9_]{11}$/;
				if (phoneExp.test(this.phone)) {
					this.phoneState = "success";
					return true;
				} else {
					this.phoneState = "error";
					return false;
				}
			},

    //检测密码
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
      //可简化成
      //this.conpasswordState = this.password != this.conpassword ? 'error' : 'success';
      //return this.password != this.conpassword;
    },

      fasong(){
        
        
     
      if (this.checkPhone()) {
        // console.log("要发送AJAX请求到服务器了");
        //this.$router.push('/');
          let param = new URLSearchParams()
          
					param.append('phone', this.phone)
				
					this.axios({
						method: 'post',
						url: 'http://f3346291w6.zicp.vip:20504/api/bully-user/user/sendCode',
						data: param
					}).then(res => {
						console.log(res)
						if (res) {

					console.log(res)

							Toast({
								message: '发送成功',
								iconClass: ''

							});
						}
					})

				} else {
					// console.log("错误");
					Toast({
						message: '手机号码错误',
						iconClass: ''
					});
				}

     },

    //用户单击注册按钮时,进行验证
      register() {
      if (this.checkUsername() && this.checkPassword() && this.checkConpassword()) {
        // console.log("要发送AJAX请求到服务器了");
        //this.$router.push('/');
         
         


          let param = new URLSearchParams()
          param.append('username', this.username)
          param.append('password', this.password)
          param.append('phone', this.phone)
          param.append('code', this.code)
				
					this.axios({
						method: 'post',
						url: 'http://f3346291w6.zicp.vip:20504/api/bully-user/user/register',
						data: param
					}).then(res => {
						console.log(res)
						if (res) {

					console.log("注册成功")

			
					

					this.$router.push('/Login');

							rage.setItem('sessionId', res.data.data.sessionId)
							Toast({
								message: '注册成功',
								iconClass: ''

							});
						}
					})

				} else {
					// console.log("错误");
					Toast({
						message: '账号或密码错误',
						iconClass: ''
					});
				}
    }
  }
};
</script>

<style scoped>

.f1{
    
    margin:  10vw  4.16vw 0;
    border-radius:45px;
    border:2px solid #ffffff;
    background:  rgba(255, 255, 255, 0.5);
    background-image: url('../../assets/user.png');
    background-repeat:no-repeat;
    background-position:left center;
    
     
}

.f2{
    margin: 9.167vw 3.16vw;
    border-radius:45px;
    border:2px solid #ffffff;
    background:  rgba(255, 255, 255, 0.5);
    background-image: url('../../assets/pow.png');
    background-repeat:no-repeat;
    background-position:left center;
}

.f3{
    margin: 9.167vw 3.16vw;
    border-radius:45px;
    border:2px solid #ffffff;
    background:  rgba(255, 255, 255, 0.5);
    background-image: url('../../assets/pow.png');
    background-repeat:no-repeat;
    background-position:left center;
}

.f4{
    margin: 9.167vw 3.16vw;
    border-radius:45px;
    border:2px solid #ffffff;
    background:  rgba(255, 255, 255, 0.5);
    background-image: url('../../assets/phone.png');
    background-repeat:no-repeat;
    background-position:left center;
}

.f5{
    margin: 9.167vw 3.16vw;
    border-radius:45px;
    border:2px solid #ffffff;
    background:  rgba(255, 255, 255, 0.5);
    background-image: url('../../assets/yan.png');
    background-repeat:no-repeat;
    background-position:left center;
}

.b1{
     /* margin-left: 22.16vw; */
     margin-bottom: 15vw;
     width: 200px;
    border-radius:45px;
    border:2px solid #ffffff;
    background:  rgba(98, 175, 248, 0.8);
}

.b2{
    
    
     width: 80px; 
    border-radius:45px;
    border:0px solid #ffffff;
    background:  rgba(98, 175, 248, 0.8);
    display: inline-block;
    font-size: 13px;
}

</style>