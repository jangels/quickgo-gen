webpackJsonp([1],{4:function(i,s,e){var t,a,l={};t=e(5),a=e(30),i.exports=t||{},i.exports.__esModule&&(i.exports=i.exports["default"]);var c="function"==typeof i.exports?i.exports.options||(i.exports.options={}):i.exports;a&&(c.template=a),c.computed||(c.computed={}),Object.keys(l).forEach(function(i){var s=l[i];c.computed[i]=function(){return s}})},5:function(i,s,e){"use strict";function t(i){return i&&i.__esModule?i:{"default":i}}Object.defineProperty(s,"__esModule",{value:!0}),e(6);var a=e(9),l=t(a),c=[];s["default"]={data:function(){return{id:"",status:{loading:!1},success:!1,projectName:"",project:{name:"",description:"",permission:"PUBLIC"},email:"",invites:c,fileAccess:"",users:[]}},validators:{exists:{message:"该邮箱已存在邀请列表中",check:function(i){var s;return c.forEach(function(e){return e==i&&(s=!0),!1}),!s}}},created:function(){this.$parent.showProject=!0},watch:{success:function(i){if(i){var s=this;this.status.loading=!0,l["default"].get("/user/project_users.json",{},function(i){s.fileAccess=i.data.fileAccess,s.users=i.data.users},function(){s.status.loading=!1})}}},methods:{create:function(){if(this.$validate(!0),this.$af.invalid)return!1;this.project.name=this.projectName;var i=this;l["default"].post("/project/create.json",this.project,function(s){i.success=!0,i.id=s.data,i.$parent.reloadProject=!0})},ok:function(){},inviteByUserId:function(i,s){l["default"].post("/project/"+this.id+"/invite.json",{userId:i},function(i){$(s.target).parents("li").addClass("active")})},inviteByEmail:function(){if(this.$validate(!0),this.$form.invalid)return!1;if(this.email){var i=this;l["default"].post("/project/"+this.id+"/invite/email.json",{email:this.email},function(){i.invites.push(i.email),i.email=""})}}}}},30:function(i,s){i.exports=' <div class=db-view-new> <div class=step1 v-if=!success> <validator name=af> <div class=dvn-title> <h2>添加项目</h2> <br/> <p>欢迎使用快狗，在这里您可以创建项目、接口，通过简单操作便可以完成对接口管理。</p> </div> <br/><br/> <div class="form db-view-form"> <div class=item> <div class="col-sm-2 label">项目名称</div> <div class=col-sm-10> <input type=text v-validate:project-name={required:true,maxlength:20} v-model=projectName maxlength=20 initial=off class="text invalid" placeholder=请输入项目名称> <div class=tip v-if=$af.projectName.invalid>{{$af.projectName.errors[0].message}}</div> </div> </div> <div class=item> <div class="col-sm-2 label">项目描述</div> <div class=col-sm-10> <textarea rows=10 v-model=project.description placeholder=请输入项目描述 class=text maxlength=300></textarea> <div class=tip></div> </div> </div> <div class=item> <div class="col-sm-2 label">项目公开性</div> <div class=col-sm-10> <p><input type=radio v-model=project.permission value=PUBLIC id=dvnr-private> <label for=dvnr-private>私有项目（只有加入项目后的成员才能看见）</label></p> <p><input type=radio v-model=project.permission value=PRIVATE id=dvnr-public> <label for=dvnr-public>公开项目（所有用户均能看见）</label></p> <div class=tip></div> </div> </div> <div class=item> <div class="col-sm-2 label"></div> <div class=col-sm-3> <input type=submit value=创建项目 v-on:click=create class="btn btn-primary btn-lg"> </div> </div> </div> </validator> </div> <div class=step2 v-else> <validator name=form> <p class=db-add-succeed>项目创建成功，添加一些成员吧！</p> <div class="form dvn-import-members"> <div class=spinner v-if=status.loading> <div class=rect1></div> <div class=rect2></div> <div class=rect3></div> <div class=rect4></div> <div class=rect5></div> </div> <div class=item v-if="users && users.length>1"> <div class="col-sm-2 label">邀请同事</div> <div class=col-sm-10> <ul class="cb dbv-chose-users"> <li v-for="item in users" v-on:click=inviteByUserId(item.id,$event)> <div class=dbv-user-icon> <img class=img v-bind:src=item.avatar v-if=item.avatar> <div class="img ta-c word" v-else>{{item.nickname.substring(0,3)}}</div> <p class=flag></p> </div> <p>{{item.nickname}}</p> </li> </ul> </div> </div> <div class=item> <div class="col-sm-2 label">邮箱邀请</div> <div class=col-sm-9> <input type=text class=text v-model=email v-validate:email="[\'email\',\'exists\']" initial=off placeholder=请输入成员的邮箱> <div class=tip v-if=$form.email.invalid>{{this.$form.email.errors[0].message}}</div> <div class=dvn-new-users v-if="invites.length>0"> <p>邀请了{{invites.length}}个同事</p><br/> <ul class=cb> <li v-for="item in invites"> {{item.substring(0,4)}} </li> </ul> </div> </div> <div class=col-sm-1> <input type=button class="btn btn-danger" v-on:click=inviteByEmail value=邀请> </div> </div> <div class=item> <div class="col-sm-2 label"></div> <div class=col-sm-10> <a v-link="{path:\'/project/\'+id,query:{\'n\':\'y\'}}" class="btn btn-primary">进入项目</a> </div> </div> </div> </validator> </div> </div> '}});