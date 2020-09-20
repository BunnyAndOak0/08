import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import qs from 'qs'
import MintUI from 'mint-ui';
import 'mint-ui/lib/style.min.css';
import { Toast } from 'mint-ui';

// import Vue$axios from 'vue-$axios'

// import uploader from 'vue-easy-uploader'
import Vuex from 'vuex'

// let storex = new Vuex.Store({})

// $axios.defaults.baseURL = '/api';
// $axios.defaults.headers.post['Content-Type'] = 'application/json';
Vue.use(MintUI,axios,Toast);
// axios.defaults.baseURL = 'http://f3346291w6.zicp.vip:20504';
// axios.defaults.baseURL = 'http://f3346291w6.zicp.vip/';
// axios.defaults.baseURL = 'http://yym2417581902.qicp.net';


Vue.config.productionTip = false
// step2：把$axios挂载到vue的原型中，在vue中每个组件都可以使用$axios发送请求,
// 不需要每次都 import一下 $axios了，直接使用 $$axios 即可
Vue.prototype.$axios = axios
// 使每次请求都会带一个 /api 前缀
// $axios.defaults.baseURL = '/api'
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')













Vue.prototype.$qs = qs


import VueAxios from 'vue-axios'
Vue.use(VueAxios,axios)


import Vant from 'vant';
import 'vant/lib/index.css';

Vue.use(Vant);

// import {postRequest} from "./utils/api"
// import {postKeyValueRequest} from "./utils/api"
// import {putRequest} from "./utils/api"
// import {deleteRequest} from "./utils/api"
// import {getRequest} from "./utils/api"

// Vue.prototype.postRequest = postRequest;
// Vue.prototype.postKeyValueRequest = postKeyValueRequest;
// Vue.prototype.putRequest = putRequest;
// Vue.prototype.deleteRequest = deleteRequest;
// Vue.prototype.getRequest = getRequest;


import { Uploader } from 'vant';

Vue.use(Uploader);

// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';


//  axios.defaults.baseURL = ''
 // axios.defaults.withCredentials=true;
// Vue.prototype.$axios = axios;


// Vue.config.productionTip = false

//导入Mint UI组件库


//导入Mint UI的样式表文件
import 'mint-ui/lib/style.min.css';

//通过Vue.use()方法将MintUI注册为组件
Vue.use(MintUI);

