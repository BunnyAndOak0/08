<template class="ba">
  <div class="ds" >
    <login-top/>
    <div class="w">Welcome to Login</div>
    <login-field/>
  </div>
</template>
<script>
      import loginTop from'../components/comment/LoginTop.vue'
      import loginField from'../components/comment/LoginField.vue'
export default {

  components:{
    loginTop,
    loginField,
  }
}
</script>

<style scoped>
.w{
  font-size: 60px;
  color: aliceblue;
  text-align: center;
  margin-top: 10vw;
}
.ds{
    margin:0;
    padding: 0;
    box-sizing: border-box;
    background-color: #f4f4f4;
    background-size: cover;
    background-image: url('../assets/10.jpg');
}

</style>
