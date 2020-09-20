 
 
<template >

  <div class="ds" >

     
    <register-top/>
    <div class="w">Welcome to Register</div>
    <register-field/>

  

    
       
  </div>
</template>
<script>
 
      import registerTop from'../components/comment/RegisterTop.vue'
      import registerField from'../components/comment/RegisterField.vue'
      

export default {

  components:{
    registerTop,
    registerField,
   
  }

}
</script>


<style scoped>
.w{
  font-size: 49px;
  color: aliceblue;
  text-align: center;
  margin-top: 5vw;
}


.ds{
  margin:0;
    padding: 0;
    box-sizing: border-box;
    background-color: #f4f4f4;
    
    background-size: cover;
  background-image: url('../assets/2.jpg');
}

</style>
