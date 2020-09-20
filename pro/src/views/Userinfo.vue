<template>

	<div>
		<!-- 顶部导航开始 -->
		<mt-header title="我">
			<router-link to="/" slot="left">
				<mt-button icon="back"></mt-button>
			</router-link>

			<mt-button icon="more" slot="right" @click="actionSheet">
			</mt-button>
		</mt-header>

		<mt-actionsheet :actions="data" v-model="sheetVisible">

		</mt-actionsheet>

		<!-- 顶部导航结束 -->
		<van-swipe class="my-swipe" :autoplay="3000" indicator-color="white">
        <van-swipe-item><img src="../assets/z.jpg" alt=""></van-swipe-item>
        <van-swipe-item><img src="../assets/x.jpg" alt=""></van-swipe-item>
        <van-swipe-item><img src="../assets/y.jpg" alt=""></van-swipe-item>
        <van-swipe-item><img src="../assets/w.jpg" alt=""></van-swipe-item>
        </van-swipe>
		<me-top :me="model" />
		<me-field />
	</div>

</template>

<script>
	import meField from '../components/geren/MeField.vue'
	import meTop from '../components/geren/MeTop.vue'

	export default {

		data() {
			return {
				model: {},

				// action sheet 选项内容

				data: [{

					name: '修改密码',

					method: this.getCamera // 调用methods中的函数

				}, {

					name: '退出登录',

					method: this.getLibrary // 调用methods中的函数

				}],

				// action sheet 默认不显示，为false。操作sheetVisible可以控制显示与隐藏

				sheetVisible: false

			}
		},
		components: {
			meField,
			meTop,
		},
		methods: {
			meData() {
				this.$axios.get("api/bully-user/user/getUser").then(resp => {
					console.log(resp);
					if (resp.status==200) {
						console.log(resp);
						this.model = resp.data.data
						console.log(resp.data)
						
					}
				})
			
		},

		actionSheet: function() {

			// 打开action sheet

			this.sheetVisible = true;

		},

		getCamera: function() {
			this.$router.push('/Find');
		},
		getLibrary: function() {

			console.log("退出登录")
		},
	},
	created() {
		this.meData()
	}
	}
</script>
<style scoped>
	img {
		width: 375px;
		height: 200px;
	}
</style>
