<template>
    <div class="db-members cb">
    	<ul class="win-card db-m-list">
		    	  <li class="tit">
			      	<p ><i class="icon icon-set"></i> 成员管理</p>
			      </li>
			      <!--添加成员-->
		    	  <li >
		    	  	<div class="item" v-if="users && users.length>0">
	                    <p>邀请同事:</p>
	                    <div >
	                        <ul class="cb dbv-chose-users">
	                            <li v-for="item in projectUsers" v-on:click="inviteByUserId(item,$event)">
	                                <div class="dbv-user-icon">
	                                    <img class="img" v-bind:src="item.avatar" v-if="item.avatar">
	                                    <img class="user-logo" v-else src="../../assets/img/defaultlogo.jpg">
	                                    <p class="flag"></p>
	                                </div>
	                                <p>{{item.nickname}}</p>
	                            </li>
	                        </ul>
	                    </div>
	                </div>
			      	<input type="text"  v-model="email" v-validate:email="['email']" initial="off" placeholder="请输入成员的邮箱" >
			      	<input type="button"  v-on:click="inviteByEmail" value="邀请" class="btn-right btn btn-primary btn-lg">
			      	<div class="tip" v-if="$form.email.invalid">{{this.$form.email.errors[0].message}}</div>
			      </li>
			      <!--<成员列表-->
			    <li class="usersList"  v-for="item in users">
                    <div class="avatar">
                        <img class="user-logo" v-if="item.avatar" v-bind:src="item.avatar">
                        <img class="user-logo" v-else src="../../assets/img/defaultlogo.jpg">
                    </div>
                    <div class="info">
                    	<p>昵称： {{item.nickname}}</p>
                    	<p><span v-if="item.name">姓名： {{item.name}} </span >  <span v-if="item.name&&item.email"> | </span> <span v-if="item.email">邮箱： {{item.email}}</span></p>
                    </div>
                    <div class="col-sm-1 del">
                    	<i class="iconfont icon-close "  v-on:click="remove(item)"></i>
                    </div>
                </li>
		    
		    </ul>       
    </div>
    <style>
        .db-members-box{
            padding:20px 0 0 20px
        }
    </style>
</template>
<script>
    import '../../src/vue.ex.js';
    import utils from '../../src/utils.js'
    var data = {
        status:{
            loading:false
        },
        email: '',
        error: false,
        showList: true,
        fileAccess: '',
        users: [],
        projectUsers:[],
        invites: [],
        id: 0,
        project: {}
    };
    function loadUser(self){
        utils.get('/project/' + self.id + '/users.json', {}, function (rs) {
            data.users = rs.data.users;
            if (data.users.length == 0) {
                data.showList = false;
            }
            data.fileAccess = rs.data.fileAccess;
        });
    }
    export default {
        route: {
            activate: function () {
                this.$parent.showProject = true;
                $(".db-main").addClass("db-members-box")
            },
            deactivate: function () {
                this.$parent.showProject = false;
                $(".db-main").removeClass("db-members-box")
            },
            data: function () {
                this.$parent.projectId = this.$route.params.id;
                this.id = this.$route.params.id;
                loadUser(this);
                var self = this;
                this.status.loading = true;
                utils.get("/user/project_users.json", {}, function (rs) {
                    self.fileAccess = rs.data.fileAccess;
                    self.projectUsers = rs.data.users;
                }, function () {
                    self.status.loading = false;
                });
                _czc.push(["_trackEvent",'接口','成员管理']);
            }
        },
        data: function () {
            return data;
        },
        methods: {
            remove: function (item) {
                utils.delete('/project/' + this.id + '/pu/' + item.id + '.json', function () {
                    toastr.success('移除成功');
                    data.users.$remove(item);
                });
            },
            inviteByUserId:function(user,e){
                var self=this;
                utils.post('/project/'+this.id+"/invite.json",{userId:user.id},function(rs){
                    toastr.success('添加成功');
                    self.users.push(user);
                    $(e.target).parents("li").addClass("active");
                });
            },
            inviteByEmail: function () {
                this.$validate(true);
                if (this.$form.invalid)
                    return false;
                if (this.email) {
                    var self = this;
                    utils.post('/project/' + this.id + "/invite/email.json", {email: this.email}, function () {
                        self.email = '';
                        toastr.success('邀请成功');
                        self.showList=true;
                        loadUser(self);
                    });
                }
            }
        }
    };
</script>

