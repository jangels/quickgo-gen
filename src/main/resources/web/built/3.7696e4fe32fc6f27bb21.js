webpackJsonp([3],{34:function(t,s,e){var i,c,o={};i=e(35),c=e(36),t.exports=i||{},t.exports.__esModule&&(t.exports=t.exports["default"]);var l="function"==typeof t.exports?t.exports.options||(t.exports.options={}):t.exports;c&&(l.template=c),l.computed||(l.computed={}),Object.keys(o).forEach(function(t){var s=o[t];l.computed[t]=function(){return s}})},35:function(t,s,e){"use strict";function i(t){return t&&t.__esModule?t:{"default":t}}function c(t){l["default"].get("/project/list.json",{},function(t){n.projects=t.data.projects},null,function(t){})}Object.defineProperty(s,"__esModule",{value:!0}),e(6);var o=e(9),l=i(o),n={selectPro:{},showContent:!1,projects:[{name:"无数据",id:""}],filter:""};s["default"]={data:function(){return n},route:{activate:function(){},deactivate:function(){},data:function(t){}},created:function(){c(this),$("body").removeClass("loading"),this.$parent.showProject=!0},watch:{},methods:{selectProFun:function(t){this.selectPro=t,this.$parent.projectId=t.id,this.$parent.projectName=t.name}}}},36:function(t,s){t.exports=' <div class=project> <ul class="list box" v-show=!selectPro.id transition=animateRightOut> <li :class="projects.length==1&&$index==0?\'t3\' :\'t\'+($index+1)  " v-for="item in projects | filterBy filter in \'name\'" @click=selectProFun(item)> <p class=tit> <span>{{item.name.substring(0,1) }}</span> </p> <p class=dis>{{item.name}}</p> </li> <li class="add t1" v-link="{path:\'/add\'}"> <p class=tit>+</p> <p class=dis>创建一个项目</p> </li> </ul> <ul class="list2 box" v-show=selectPro.id transition=animateRightOut> <li class=t3 v-link="\'/autoCode/genAdd/\'+selectPro.id"> <p class=icon><i class=icon-code></i> </p> <p class=dis>添加业务表</p> </li> <li class=t4 v-link="{ path: \'/project/\'+selectPro.id,params:{name:selectPro.name}}"> <p class=icon><i class=icon-apis></i> </p> <p class=dis>API管理</p> </li> <li class=t3 v-link="\'/project/\'+selectPro.id+\'/members\'"> <p class=icon><i class=icon-team></i> </p> <p class=dis>成员管理</p> </li> <li class=t2 v-link="\'/project/\'+selectPro.id+\'/settings\'"> <p class=icon><i class=icon-set></i> </p> <p class=dis>项目设置</p> </li> <li class=t1 v-link="\'/project/\'+selectPro.id+\'/transfer\'"> <p class=icon><i class=icon-team></i> </p> <p class=dis>项目转让和删除</p> </li> <li class="t6 tend" v-on:click="selectPro={} ,$parent.projectName =null "> <p class=icon><i class=icon-end></i> </p> <p class=dis>切换项目</p> </li> </ul> </div> '}});