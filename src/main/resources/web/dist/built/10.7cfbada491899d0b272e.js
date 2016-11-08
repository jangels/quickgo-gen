webpackJsonp([10],{127:function(s,i,e){var t,a,l={};t=e(128),a=e(129),s.exports=t||{},s.exports.__esModule&&(s.exports=s.exports["default"]);var c="function"==typeof s.exports?s.exports.options||(s.exports.options={}):s.exports;a&&(c.template=a),c.computed||(c.computed={}),Object.keys(l).forEach(function(s){var i=l[s];c.computed[s]=function(){return i}})},128:function(s,i,e){"use strict";function t(s){return s&&s.__esModule?s:{"default":s}}function a(s){c["default"].get("/project/"+s.id+"/users.json",{},function(s){v.users=s.data.users,0==v.users.length&&(v.showList=!1),v.fileAccess=s.data.fileAccess})}Object.defineProperty(i,"__esModule",{value:!0}),e(6);var l=e(9),c=t(l),v={status:{loading:!1},email:"",error:!1,showList:!0,fileAccess:"",users:[],projectUsers:[],invites:[],id:0,project:{}};i["default"]={route:{activate:function(){this.$parent.showProject=!0,$(".db-main").addClass("db-members-box")},deactivate:function(){this.$parent.showProject=!1,$(".db-main").removeClass("db-members-box")},data:function(){this.$parent.projectId=this.$route.params.id,this.id=this.$route.params.id,a(this);var s=this;this.status.loading=!0,c["default"].get("/user/project_users.json",{},function(i){s.fileAccess=i.data.fileAccess,s.projectUsers=i.data.users},function(){s.status.loading=!1}),_czc.push(["_trackEvent","接口","成员管理"])}},data:function(){return v},methods:{remove:function(s){c["default"]["delete"]("/project/"+this.id+"/pu/"+s.id+".json",function(){toastr.success("移除成功"),v.users.$remove(s)})},inviteByUserId:function(s,i){var e=this;c["default"].post("/project/"+this.id+"/invite.json",{userId:s.id},function(t){toastr.success("添加成功"),e.users.push(s),$(i.target).parents("li").addClass("active")})},inviteByEmail:function(){if(this.$validate(!0),this.$form.invalid)return!1;if(this.email){var s=this;c["default"].post("/project/"+this.id+"/invite/email.json",{email:this.email},function(){s.email="",toastr.success("邀请成功"),s.showList=!0,a(s)})}}}}},129:function(s,i,e){s.exports=' <div class="db-members cb"> <ul class="win-card db-m-list"> <li class=tit> <p><i class="icon icon-set"></i> 成员管理</p> </li> <li> <div class=item v-if="users && users.length>0"> <p>邀请同事:</p> <div> <ul class="cb dbv-chose-users"> <li v-for="item in projectUsers" v-on:click=inviteByUserId(item,$event)> <div class=dbv-user-icon> <img class=img v-bind:src=item.avatar v-if=item.avatar> <img class=user-logo v-else src='+e(130)+'> <p class=flag></p> </div> <p>{{item.nickname}}</p> </li> </ul> </div> </div> </li> <li class=usersList v-for="item in users"> <div class=avatar> <img class=user-logo v-if=item.avatar v-bind:src=item.avatar> <img class=user-logo v-else src='+e(130)+'> </div> <div class=info> <p>昵称： {{item.nickname}}</p> <p><span v-if=item.name>姓名： {{item.name}} </span> <span v-if=item.name&&item.email> | </span> <span v-if=item.email>邮箱： {{item.email}}</span></p> </div> <div class="col-sm-1 del"> <i class="iconfont icon-close" v-on:click=remove(item)></i> </div> </li> </ul> <div class=fl> <div style="width: 400px"> <ul class="nav nav-tabs nav-justified"> <li v-bind:class="{\'active\':showList}" v-on:click="showList=true"><a>成员列表</a></li> <li v-bind:class="{\'active\':!showList}" v-on:click="showList=false"><a>添加</a></li> </ul> </div> <div v-show=showList class=db-m-list> <ul> <li class=cb v-for="item in users"> <div class=col-sm-2> <img class=user-logo v-if=item.avatar v-bind:src=item.avatar> <img class=user-logo v-else src='+e(130)+'> </div> <div class=col-sm-2> {{item.name}}</div> <div class=col-sm-2> {{item.nickname}}</div> <div class=col-sm-3> {{item.email}}</div> <div class=col-sm-1> <input type=button class="btn btn-danger" v-on:click=remove(item) value=移除> </div> </li> </ul> </div> <div v-else class="form dvn-import-members"> <div class=spinner v-if=status.loading> <div class=rect1></div> <div class=rect2></div> <div class=rect3></div> <div class=rect4></div> <div class=rect5></div> </div> <div class=item v-if="users && users.length>0"> <div class="col-sm-2 label">邀请同事</div> <div class=col-sm-10> <ul class="cb dbv-chose-users"> <li v-for="item in projectUsers" v-on:click=inviteByUserId(item,$event)> <div class=dbv-user-icon> <img class=img v-bind:src=item.avatar v-if=item.avatar> <div class="img ta-c word" v-else>{{item.nickname.substring(0,3)}}</div> <p class=flag></p> </div> <p>{{item.nickname}}</p> </li> </ul> </div> </div> <validator name=form> <div class=item> <div class="col-sm-2 label">邮箱邀请</div> <div class=col-sm-9> <input type=text class=text v-model=email v-validate:email="[\'email\']" initial=off placeholder=请输入成员的邮箱> <div class=tip v-if=$form.email.invalid>{{this.$form.email.errors[0].message}}</div> <div class=dvn-new-users v-if="invites.length>0"> <p>邀请了{{invites.length}}个同事</p><br/> <ul class=cb> <li v-for="item in invites"> {{item.substring(0,4)}} </li> </ul> </div> </div> <div class=col-sm-1> <input type=button class="btn btn-danger" v-on:click=inviteByEmail value=邀请> </div> </div> </validator> </div> </div> </div> <style> .db-members-box{\n            padding:20px 0 0 20px\n        } </style> '}});