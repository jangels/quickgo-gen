webpackJsonp([10],{127:function(s,e,i){var t,a,o={};t=i(128),a=i(129),s.exports=t||{},s.exports.__esModule&&(s.exports=s.exports["default"]);var n="function"==typeof s.exports?s.exports.options||(s.exports.options={}):s.exports;a&&(n.template=a),n.computed||(n.computed={}),Object.keys(o).forEach(function(s){var e=o[s];n.computed[s]=function(){return e}})},128:function(s,e,i){"use strict";function t(s){return s&&s.__esModule?s:{"default":s}}function a(s){n["default"].get("/project/"+s.id+"/users.json",{},function(s){r.users=s.data.users,0==r.users.length&&(r.showList=!1),r.fileAccess=s.data.fileAccess})}Object.defineProperty(e,"__esModule",{value:!0}),i(6);var o=i(9),n=t(o),r={status:{loading:!1},email:"",error:!1,showList:!0,fileAccess:"",users:[],projectUsers:[],invites:[],id:0,project:{}};e["default"]={route:{activate:function(){this.$parent.showProject=!0,$(".db-main").addClass("db-members-box")},deactivate:function(){this.$parent.showProject=!1,$(".db-main").removeClass("db-members-box")},data:function(){this.$parent.projectId=this.$route.params.id,this.id=this.$route.params.id,a(this);var s=this;this.status.loading=!0,n["default"].get("/user/project_users.json",{},function(e){s.fileAccess=e.data.fileAccess,s.projectUsers=e.data.users},function(){s.status.loading=!1}),_czc.push(["_trackEvent","接口","成员管理"])}},data:function(){return r},methods:{remove:function(s){n["default"]["delete"]("/project/"+this.id+"/pu/"+s.id+".json",function(){toastr.success("移除成功"),r.users.$remove(s)})},inviteByUserId:function(s,e){var i=this;n["default"].post("/project/"+this.id+"/invite.json",{userId:s.id},function(t){toastr.success("添加成功"),i.users.push(s),$(e.target).parents("li").addClass("active")})},inviteByEmail:function(){if(this.$validate(!0),this.$form.invalid)return!1;if(this.email){var s=this;n["default"].post("/project/"+this.id+"/invite/email.json",{email:this.email},function(){s.email="",toastr.success("邀请成功"),s.showList=!0,a(s)})}}}}},129:function(s,e,i){s.exports=' <div class="db-members cb"> <ul class="win-card db-m-list"> <li class=tit> <p><i class="icon icon-set"></i> 成员管理</p> </li> <li> <div class=item v-if="users && users.length>0"> <p>邀请同事:</p> <div> <ul class="cb dbv-chose-users"> <li v-for="item in projectUsers" v-on:click=inviteByUserId(item,$event)> <div class=dbv-user-icon> <img class=img v-bind:src=item.avatar v-if=item.avatar> <img class=user-logo v-else src='+i(130)+'> <p class=flag></p> </div> <p>{{item.nickname}}</p> </li> </ul> </div> </div> <validator name=form> <div> <input type=text class=text v-model=email v-validate:email="[\'email\']" initial=off placeholder=请输入成员的邮箱> <input type=button v-on:click=inviteByEmail value=邀请 class="btn-right btn btn-primary btn-lg"> <div class=tip v-if=$form.email.invalid>{{this.$form.email.errors[0].message}}</div> </div> </validator> </li> <li class=usersList v-for="item in users"> <div class=avatar> <img class=user-logo v-if=item.avatar v-bind:src=item.avatar> <img class=user-logo v-else src='+i(130)+'> </div> <div class=info> <p>昵称： {{item.nickname}}</p> <p><span v-if=item.name>姓名： {{item.name}} </span> <span v-if=item.name&&item.email> | </span> <span v-if=item.email>邮箱： {{item.email}}</span></p> </div> <div class="col-sm-1 del"> <i class="iconfont icon-close" v-on:click=remove(item)></i> </div> </li> </ul> <style> .db-members-box{\n            padding:20px 0 0 20px\n        } </style> </div>'}});