webpackJsonp([4],{37:function(t,e,o){var s,a,n={};s=o(38),a=o(39),t.exports=s||{},t.exports.__esModule&&(t.exports=t.exports["default"]);var c="function"==typeof t.exports?t.exports.options||(t.exports.options={}):t.exports;a&&(c.template=a),c.computed||(c.computed={}),Object.keys(n).forEach(function(t){var e=n[t];c.computed[t]=function(){return e}})},38:function(t,e,o){"use strict";function s(t){return t&&t.__esModule?t:{"default":t}}Object.defineProperty(e,"__esModule",{value:!0});var a=o(9),n=s(a);e["default"]={route:{activate:function(){this.$parent.showProject=!0},deactivate:function(){this.$parent.showProject=!1},data:function(){var t=this;n["default"].get("/project/"+this.$route.params.id+"/info.json",{},function(e){t.project=e.data.project}),t.$parent.projectId=this.$route.params.id,_czc.push(["_trackEvent","接口","删除项目"])}},data:function(){return{isOk:!1,project:null,projectName:"",status:{deleteModal:!1}}},watch:{"status.deleteModal":function(t){t&&setTimeout(function(){$("#projectName").focus()},100)}},methods:{ok:function(){var t=this.project,e=t.id,o=this;t.name==this.projectName?n["default"]["delete"]("/project/"+e+".json",function(t){toastr.success("删除成功"),o.$route.router.go({path:"/"}),o.$parent.reloadProject=!0}):this.isOk=!0}}}},39:function(t,e){t.exports=' <div class=db-view-release> <button class="btn btn-danger" v-on:click="status.deleteModal=true" style="padding: 10px 100px">删除项目</button> <div class=modal v-cloak v-if=status.deleteModal> <div class=modal-header> <i class="iconfont icon-close modal-close" v-on:click="status.deleteModal=false"></i> </div> <div class=modal-content> <div class="modal-layout1 form"> <p class=title>删除项目</p> <input type=text class="k1 text" id=projectName v-bind:class="{\'invalid\':isOk}" maxlength=20 initial=off v-model=projectName autofocus=autofocus tabindex=1 placeholder=请输入项目名称> <div class=tip>项目名称错误</div> <div class="ta-c actions"> <button class="btn btn-default-box middle" tabindex=3 v-on:click="status.deleteModal=false">取消 </button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <button class="btn btn-danger middle" v-on:click=ok tabindex=2>确定</button> </div> </div> </div> </div> </div> '}});