<template>



  <div class="userdetail">
    <div>   
      <router-link to="/edit">  
      
      <div class="user_img">
       <img :src="me.user_img" alt="" v-if="me.portrait"> 
        <img src="../../assets/icon25.png" alt v-else/>
      </div>
      
      </router-link>
      <div class="user_edit">
        <div>
          
        </div>
        <div  @click="$router.push('/Login')">
          <div class="user_editBtn" v-if="me.id">Hello，my friend</div>
          <div class="user_editBtn" v-else>登录/注册</div>
        </div>
      </div>
    </div>
    <div>
      
      <h2 style="font-size:25px;color:blue;" v-if="me.username">{{me.username}}</h2> 
      <h2 v-else>还没有登录哦！</h2>

      <p v-if="me.sentence">{{me.sentence}}</p> 
      <p v-else>这个人很神秘，什么都没有写</p>
      
    </div>
  </div>
</template>

<script>

import { Uploader } from 'vant';

export default {
     props:['me'],
};


</script>

<style scoped lang="less">
.userdetail {
  background-color: white;
  padding: 2vw 3.333vw;
  > div:nth-child(1) {
    display: flex;
    .user_img {
      margin-right: 5.556vw;
   
      img {
        height: 23.611vw;
        width: 23.611vw;
        border-radius: 50%;
        
      }
    }
    .bu{
      
        height: 23.611vw;
        width: 23.611vw;
        border-radius: 100%;
       border: 0;
        background-color: white;
    }

    .user_edit {
      flex: 1;
      div:nth-child(1) {
        display: flex;
       align-items: center;
        
      }
      div:nth-child(2) {
        padding: 0.556vw 2.778vw;
        .user_editBtn {
          border: 0.278vw solid #fb7a9f;
          padding: 0.833vw;
          margin: 15%;
          align-items: center;
          justify-content: center;
          color: #fb7a9f;
          border-radius: 1.111vw;
        }
      }
    }
  }
  
  >div:nth-child(2){
      h2{
          margin: 2.778vw 0 0.833vw 0;
          font-weight: 400;
      }
     p{
         padding: 0;
         margin: 2.778vw 0 0 0;
         color: #999;
     }
  }
}


</style>