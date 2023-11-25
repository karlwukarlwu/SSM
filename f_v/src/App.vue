<template>
  <div >
    <Header/>
<!--    Home <el-button>我的按钮</el-button>-->
  </div>
  <div style="display: flex">
    <!--      侧边栏-->
    <Aside/>
<!--    内容区域 通过路由展示-->
<!--    当前的view就是 /  根据main.js配置 这里的router-view展示是homeView-->
    <router-view style="flex: 1"/>
  </div>

</template>

<script>
import Header from '@/components/Header'
import Aside from '@/components/Aside'
export default {
  name: 'Layout',
  components: {
    Header,
    Aside
  }
}
</script>
<style>

</style>
