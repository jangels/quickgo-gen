webpackJsonp([3],{34:function(t,e,o){var n,r,s={};n=o(35),r=o(36),t.exports=n||{},t.exports.__esModule&&(t.exports=t.exports["default"]);var c="function"==typeof t.exports?t.exports.options||(t.exports.options={}):t.exports;r&&(c.template=r),c.computed||(c.computed={}),Object.keys(s).forEach(function(t){var e=s[t];c.computed[t]=function(){return e}})},35:function(t,e,o){"use strict";function n(t){return t&&t.__esModule?t:{"default":t}}Object.defineProperty(e,"__esModule",{value:!0});var r=o(9),s=n(r);e["default"]={data:function(){return{project:{}}},route:{activate:function(){this.$parent.showProject=!0},deactivate:function(){this.$parent.showProject=!1},data:function(){var t=this;t.$parent.projectId=this.$route.params.id,_czc.push(["_trackEvent","接口","退出项目"])}},methods:{ok:function(){s["default"]["delete"]("/project/"+this.$route.params.id+"/quit.json",function(){toastr.success("操作成功")})}}}},36:function(t,e){t.exports=' <div class=db-view-quit> <button class="btn btn-danger" v-on:click=ok style="padding: 10px 100px">退出项目</button> </div> '}});