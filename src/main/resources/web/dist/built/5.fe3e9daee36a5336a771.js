webpackJsonp([5],{112:function(t,e,i){var s,c,o={};s=i(113),c=i(114),t.exports=s||{},t.exports.__esModule&&(t.exports=t.exports["default"]);var a="function"==typeof t.exports?t.exports.options||(t.exports.options={}):t.exports;c&&(a.template=c),a.computed||(a.computed={}),Object.keys(o).forEach(function(t){var e=o[t];a.computed[t]=function(){return e}})},113:function(t,e,i){"use strict";function s(t){return t&&t.__esModule?t:{"default":t}}Object.defineProperty(e,"__esModule",{value:!0});var c=i(9),o=s(c);e["default"]={data:function(){return{loading:!0,project:{},fileAccess:"",userId:"",users:[]}},route:{activate:function(){this.$parent.showProject=!0},deactivate:function(){this.$parent.showProject=!1},data:function(){this.loading=!0;var t=this;o["default"].get("/project/"+this.$route.params.id+"/info.json",{},function(e){t.project=e.data.project}),t.$parent.projectId=this.$route.params.id,o["default"].get("/project/"+this.$route.params.id+"/users.json",{},function(e){t.users=e.data.users,t.fileAccess=e.data.fileAccess},function(){t.loading=!1}),_czc.push(["_trackEvent","接口","项目转移"])}},methods:{chose:function(t){t.id==this.userId?this.userId="":this.userId=t.id},ok:function(){o["default"].post("/project/"+this.project.id+"/transfer.json",{userId:this.userId},function(t){toastr.success("操作成功")})}}}},114:function(t,e){t.exports=' <div class="db-members cb win-card-box"> <div class="form dvn-import-members win-card"> <li class=tit> <p><i class="icon icon-set"></i> 项目转让</p> </li> <div class=spinner v-if=loading> <div class=rect1></div> <div class=rect2></div> <div class=rect3></div> <div class=rect4></div> <div class=rect5></div> </div> <template v-if="users.length>1"> <li> <div class=item> <div>选择成员 :</div> <div> <ul class="cb dbv-chose-users"> <li v-for="item in users" v-if="item.id != project.userId" v-bind:class="{\'active\':userId==item.id}" v-on:click=chose(item)> <div class=dbv-user-icon> <img class=img v-bind:src=item.avatar v-if=item.avatar> <div class="img ta-c word" v-else>{{item.nickname}}</div> <p class=flag></p> </div> <p>{{item.nickname}}</p> </li> </ul> </div> </div> <input type=button class="btn-right btn btn-primary biggest" v-on:click=ok v-bind:disabled=!userId value=确认> </li> </template> <template v-if="!loading && users.length<=1"> <div class="ta-c api-error-tip" v-cloak v-else> <i class="iconfont icon-api" style="font-size: 120px"></i> <p style="font-size: 24px">该项目暂无其他成员</p> </div> </template> </div> </div> '}});