
<template>
  <div>
    <!-- 正常动态页面编写 -->
    <mt-header title="书写匿名动态" fixed>
      <router-link to="/" slot="left">  
        <mt-button icon="back" @click.native="prev">返回</mt-button>
      </router-link>
      
      <mt-button slot="right">发布</mt-button>
    </mt-header>

    <!-- 文章内容书写 -->
    <div id="inputTextarea">
      <h2>编写匿名动态！</h2>
      <textarea
        name=""
        id=""
        cols=" 5"
        rows="1"
        maxlength="300"
        autofocus
      ></textarea>
    </div>

    <!-- 图片上传 -->
    <div id="ddd">
      <div id="img-group">
        <div class="img-item" v-for="(item,index) in imgArr" :key="index">
          <img :src="item.src" alt="" />
        </div>
      </div>
      <div class="add-pic" @click="addPic()" v-show="picFlag">
        +
        <input
          name="files"
          id="uploaderInput"
          type="file"
          accept="image/*"
          multiple
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 内容区域样式编写 */
#inputTextarea {
  width: 90%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-top: 50px;
  margin-left: 5%;
}

#inputTextarea > textarea {
  margin-top: 10px;
  width: 100%;
  height: 300px;
  color: black;
  font-size: 20px;
  vertical-align: top;
  resize: none;
  word-wrap: break-word;
  border: none;
  background: url(../assets/newsRelease/write.jpg) 0 3px;
  background-size: 100% 300px;
  opacity: 0.2;
  color: red;
}

#inputTextarea > div {
  clear: both;
}

/* 图片上传 */
.hide {
  display: none;
}

.img-item {
  width: 4.4rem;
  height: 3.75rem;
  float: left;
  margin-left: 1.5rem;
  margin-top: 0.5rem;
}

.img-item img {
  width: 100%;
  height: 100%;
}
.active-pic {
  font-size: 0.6rem;
  color: #9b9b9b;
  padding: 0 1rem 0 1.5rem;
  margin-top: 1rem;
}

.add-pic {
  background: #f3f3f3;
  width: 4.4rem;
  height: 3.75rem;
  float: left;
  margin-left: 1.5rem;
  margin-top: 0.5rem;
  text-align: center;
  font-size: 2rem;
  line-height: 3.5rem;
  color: #979797;
  position: relative;
}

.add-pic input {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  z-index: 10;
  opacity: 0;
}
</style>

<script>
export default {
  data() {
    return {
      msg: "",
    //   图片上传
    imgArr: [],
    picFlag: true,
    };
  },

   methods: {
    //    prev(){
    //     this.$router.go(-1);
    //    },

    //   图片上传
    goBack(){
        history.go(-1)
    },
    addPic(){
        var vm = this;
        var input = $("#uploaderInput");
        input.unbind('change').on('change', function (e) {
            var files = input[0].files;
            if (files.length == 6) vm.picFlag = false;
            for (var i = 0; i < files.length; i++) {
                var reader = new FileReader();
                reader.readAsDataURL(files[i]);
                reader.onload = function () {
                    if (vm.imgArr.length < 6) {
                        vm.imgArr.push({src: this.result});
                    }
                    if (vm.imgArr.length == 6) vm.picFlag = false
                    if (vm.imgArr.length > 6) {
                        Toast({
                            message: '您最多上传六张站点图片',
                            position: 'bottom',
                            duration: 2000
                        });
                        vm.picFlag = false
                    }

                };
            }
        });
    },
},

};
</script>
