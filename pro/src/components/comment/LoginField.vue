<template>
	<div>
		<mt-field class="f1" type="text" label="手机号" placeholder="请输入用户名" v-model=phone value='phone' @blur.native.capture="checkPhone"
		 :state="phoneState">
		</mt-field>

		<mt-field class="f2" type="password" label="密码" placeholder="请输入密码" v-model=password value='password'
		 @blur.native.capture="checkPassword" :state="passwordState"></mt-field>

		<router-link to="/Forget" class="for" style="color:rgba(98,175,248)">
			忘记密码？
		</router-link>


		<mt-button class="b1" type="primary" size="normal" @click="register">登录</mt-button>

		<router-link to="/Register">
			<mt-button class="b1" type="primary" size="normal">注册</mt-button>
		</router-link>

	</div>

</template>

<script src="">
	import { Indicator } from 'mint-ui';  
	
	import Qs from 'qs'
	import {Toast} from "mint-ui";

	export default {

		data() {
			return {

				value: '',
				//用户名的初始值
				phone: '',
				//密码初始值
				password: '',
				//用户名状态的初始值
				phoneState: "",
				//密码状态的初始值
				passwordState: "",
			};
		},
		methods: {
			//检测用户名
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
			//用户单击注册按钮时,进行验证
			register() {

				Indicator.open({
                    text: '登录中...',
                    spinnerType: 'fading-circle'
                });
				if (this.checkPhone() && this.checkPassword()) {
					var url = 'http://f3346291w6.zicp.vip:20504/login';


					let param = new URLSearchParams()
					param.append('phone', this.phone)
					param.append('password', this.password)
					this.$axios({
						method: 'post',
						url: '/login',
						data: param
					}).then(res => {
						console.log(res)
						if (res.data.state==2000) {

					// console.log("点击了")
                    Indicator.close();
				
				    

					this.$router.push({
						path: "/"
					});

							Toast({
								message: '登陆成功',
								iconClass: ''

							});
						}else{
							Toast({
						message: '账号或密码错误',
						iconClass: ''
					});
					Indicator.close();
						}
					})

				} else {
					// console.log("错误");
					Toast({
						message: '账号或密码错误',
						iconClass: ''
					});
					Indicator.close();
				}
			}
			
		}
	};
</script>

<style scoped>
	.f1 {

		margin: 25vw 4.16vw 0;
		border-radius: 45px;
		border: 2px solid #ffffff;
		background: rgba(255, 255, 255, 0.5);
		background-image: url('../../assets/phone.png');
		background-repeat: no-repeat;
		background-position: left center;
	}

	.f2 {
		margin: 9.167vw 4.16vw 6vw;
		border-radius: 45px;
		border: 2px solid #ffffff;
		background: rgba(255, 255, 255, 0.5);
		background-image: url('../../assets/pow.png');
		background-repeat: no-repeat;
		background-position: left center;
	}

	.b1 {
		/* margin-left: 22.16vw; */
		margin-bottom: 9.5vw;
		width: 200px;
		border-radius: 45px;
		border: 2px solid #ffffff;
		background: rgba(98, 175, 248, 0.8);
	}

	.for {
		display: block;
		text-decoration: none;
		text-align: right;
		font-size: 14px;
		margin-bottom: 10vw;
		padding-right: 4.16vw;
	}
</style>
