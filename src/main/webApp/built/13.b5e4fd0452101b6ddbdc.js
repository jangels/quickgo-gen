webpackJsonp([13],{137:function(s,e,t){var i,o,l={};i=t(138),o=t(139),s.exports=i||{},s.exports.__esModule&&(s.exports=s.exports["default"]);var a="function"==typeof s.exports?s.exports.options||(s.exports.options={}):s.exports;o&&(a.template=o),a.computed||(a.computed={}),Object.keys(l).forEach(function(s){var e=l[s];a.computed[s]=function(){return e}})},138:function(s,e,t){"use strict";function i(s){return s&&s.__esModule?s:{"default":s}}function o(s){var e=new FileReader;e.readAsDataURL(s),console.log(e),e.onload=function(s){console.log(this.result)}}Object.defineProperty(e,"__esModule",{value:!0});var l=t(9),a=i(l);e["default"]={data:function(){return{user:a["default"].user(),modify:!1,uploadAvatar:""}},methods:{ok:function(){var s=this;console.log(this.user),a["default"].post("/user/"+this.user.id+".json",this.user,function(){toastr.success("修改成功"),a["default"].user(s.user)})},uploadImg:function(){console.log(this.uploadAvatar),o(this.uploadAvatar)}}}},139:function(s,e,t){s.exports=' <div class="db-profile form"> <div class=item> <div class=col-sm-2 style="line-height: 100px">头像</div> <div class=col-sm-10> <div class=user-logo> <img v-if=user.avatar v-bind:src=user.avatar alt=""> <img src='+t(130)+' v-else> <div class=logo-edit title=修改头像> <i class="iconfont icon-edit3"></i> <input class=upload-img type=file v-model=uploadAvatar @change=uploadImg> </div> </div> </div> </div> <div class=item> <div class="col-sm-2 full-text">姓名</div> <div class=col-sm-4><input type=text v-on:keyup="modify=true" v-model=user.nickname value={{user.nickname}} class=text placeholder=请输入姓名></div> </div> <div class=item> <div class=col-sm-2>邮箱</div> <div class=col-sm-6>{{user.email}}</div> </div> <div class=item> <div class=col-sm-2>注册时间</div> <div class=col-sm-6>{{ user.createtime | timestampFormat }}</div> </div> <div class=item> <div class="col-sm-2 label"></div> <div class=col-sm-6><input type=button class="btn btn-primary" v-on:click=ok v-bind:disabled=!modify value=确认></div> </div> </div> '}});