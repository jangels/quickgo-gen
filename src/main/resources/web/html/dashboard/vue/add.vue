<template>
    <div class="db-view-new win-card-box">

        <div class="step1" v-if="!success">
            <validator name="af">
			    <ul class="win-card">
			    	  <li class="tit">
				      	<p ><i class="icon icon-set"></i> 创建项目</p>
				      </li>
			    	  <li >
				      	<input type="text" v-validate:project-name="{required:true,maxlength:20}" v-model="projectName" maxlength="20"
			                                   initial="off" class="text invalid" placeholder="项目名称：" /> 
				      	<span class="tip" v-if="$af.projectName.invalid">{{$af.projectName.errors[0].message}}</span>
				      </li>
			    	 <li>
				      <textarea rows="10" v-model="project.description" placeholder="请输入项目描述：" class="text" maxlength="300"></textarea>
				    </li>
				    
				    <li >
				      	<input type="text"  v-model="project.dbPath" 
			                                   initial="off" class="text invalid" placeholder="数据库地址：" /> 
				    </li>
				    
				      <li >
				      	<input type="text" v-model="project.dbPort" 
			                                   initial="off" class="text invalid" placeholder="数据库端口号：" /> 
				    </li>
				     <li >
				      	<input type="text"  v-model="project.dbName" 
			                                   initial="off" class="text invalid" placeholder="数据库名称：" /> 
				    </li>
				    <li >
				      	<input type="text"  v-model="project.dbUser" 
			                                   initial="off" class="text invalid" placeholder="数据库用户名：" /> 
				    </li>
				     <li >
				      	<input type="password"  v-model="project.dbPassword" 
			                                   initial="off" class="text invalid" placeholder="数据库密码：" /> 
				    </li>   
				    <li >
			            <p>
			                              项目属性：
			            <input type="radio" v-model="project.permission" value="PUBLIC" id="dvnr-private"><label for="dvnr-private">私有项目</label>
			            <input type="radio" v-model="project.permission" value="PRIVATE" id="dvnr-public"><label for="dvnr-public">公开项目</label>
			             <input type="submit" value="创建项目" v-on:click="create" class="btn-right btn btn-primary btn-lg">
			            </p>
				    </li>
			    </ul>        	
            </validator>
        </div>
        <div class="step2" v-else>
            <validator name="form">
                <p class="db-add-succeed">项目创建成功，添加一些成员吧！</p>
                <div class="form dvn-import-members">
                    <div class="spinner" v-if="status.loading">
                        <div class="rect1"></div>
                        <div class="rect2"></div>
                        <div class="rect3"></div>
                        <div class="rect4"></div>
                        <div class="rect5"></div>
                    </div>
                    <div class="item" v-if="users && users.length>1">
                        <div class="col-sm-2 label">邀请同事</div>
                        <div class="col-sm-10">
                            <ul class="cb dbv-chose-users">
                                <li v-for="item in users" v-on:click="inviteByUserId(item.id,$event)">
                                    <div class="dbv-user-icon">
                                        <img class="img" v-bind:src="item.avatar" v-if="item.avatar">
                                        <div class="img ta-c word" v-else>{{item.nickname.substring(0,3)}}</div>
                                        <p class="flag"></p>
                                    </div>
                                    <p>{{item.nickname}}</p>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="item">
                        <div class="col-sm-2 label">邮箱邀请</div>
                        <div class="col-sm-9">
                            <input type="text" class="text" v-model="email"
                                   v-validate:email="['email','exists']"  initial="off" placeholder="请输入成员的邮箱">
                            <div class="tip" v-if="$form.email.invalid">{{this.$form.email.errors[0].message}}</div>
                            <div class="dvn-new-users" v-if="invites.length>0">
                                <p>邀请了{{invites.length}}个同事</p><br/>
                                <ul class="cb">
                                    <li v-for="item in invites">
                                        {{item.substring(0,4)}}
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-1">
                            <input type="button" class="btn btn-danger" v-on:click="inviteByEmail"
                                                     value="邀请">
                        </div>
                    </div>
                    <div class="item">
                        <div class="col-sm-2 label"></div>
                        <div class="col-sm-10">
                            <a v-link="{path:'/' }" class="btn btn-primary">进入项目</a>
                        </div>
                    </div>

                </div>
            </validator>
        </div>
    </div>
</template>
<script>
    import '../../src/vue.ex.js';
    import utils from '../../src/utils.js'
    var invites=[];
    export default {
        data: function () {
            return {
                id: '',
                status: {
                    loading: false
                },
                success: false,
                projectName: '',
                project: {
                    name:'',
                    description: '',
                    permission: 'PUBLIC',
                    dbPath:'',
                    dbPort:'',
                    dbName:'',
                    dbUser:'',
                    dbPassword:'',
                    delFlag:"n"
                },
                email:'',
                invites: invites,
                fileAccess: '',
                users: []
            }
        },
        validators: {
            exists: {
                message: '该邮箱已存在邀请列表中',
                check: function (value) {
                    var exists;
                    invites.forEach(function (d) {
                        if (d == value) {
                            exists = true;
                        }
                        return false;
                    });
                    return !exists;
                }
            }
        },
        created: function () {
	         this.$parent.showProject=true;
	         this.$parent.title="创建项目";
	    },
        watch: {
            "success": function (value) {
                if (value) {
                    var self = this;
                    this.status.loading = true;
                    utils.get("/user/project_users.json", {}, function (rs) {
                        self.fileAccess = rs.data.fileAccess;
                        self.users = rs.data.users;
                    }, function () {
                        self.status.loading = false;
                    });
                }
            }
        },
        methods: {
            create: function () {
                this.$validate(true);
                if (this.$af.invalid) {
                    return false;
                }
                this.project.name = this.projectName;
                var self = this;
                utils.post('/project/create.json', this.project, function (rs) {
                	
                	location.href = utils.config.ctx + '/dashboard';
                	return ;
//              	//项目创建完成直接进入项目列表
//                  self.success = true;
//                  self.id = rs.data.id;
//                  self.$parent.reloadProject=true;
//                  self.$parent.projectId=rs.data.id;
                });
            },
            ok: function () {
            },
            inviteByUserId:function(userId,e){
                utils.post('/project/'+this.id+"/invite.json",{userId:userId},function(rs){
                    $(e.target).parents("li").addClass("active");
                });
            },
            inviteByEmail: function () {
                this.$validate(true);
                if (this.$form.invalid)
                    return false;
                if(this.email){
                    var self = this;
                    utils.post('/project/'+this.id+"/invite/email.json",{email:this.email},function(){
                        self.invites.push(self.email);
                        self.email='';
                    });
                }
            }
        }
    }
</script>