<template>
    <li class="fl db-back db-item" v-if="path.indexOf('/profile')==0"><a v-link="'/'"><i class="iconfont icon-left"></i>返回控制台 </a></li>
    <template v-if="$parent.showProject">
     <li class="fl db-item"  :class="pageName=='1' ? 'current':'' "><a v-link="'/project/'+projectId+'/members'" v-on:click="pageName='1'">成员管理</a></li>
    <li class="fl db-item"  :class="pageName=='2' ? 'current':'' "><a v-link="'/project/'+projectId+'/settings'" v-on:click="pageName='2'">项目设置</a></li>
     <li class="fl db-item"  :class="(pageName=='3_1' || pageName=='3_2') ? 'current':'' " >
        <a v-on:click.stop.prevent="status.show='autoCode'">代码生成<i class="iconfont icon-angeldownblock"></i></a>
        <ul class="db-item-sub" v-show="status.show=='autoCode'">
            <li class="db-item" :class="pageName=='3_1' ? 'current':'' "> <a v-link="'/autoCode/0'" v-on:click="pageName='3_1' ">业务表配置</a></li>
            <li class="db-item" :class="pageName=='3_2' ? 'current':'' "><a v-link="'/autoCode/1'" v-on:click="pageName='3_2'">生成方案配置</a></li>
        </ul>
    </li>
    <li class="fl db-item" :class="(pageName=='4_1' || pageName=='4_2' || pageName=='4_3' || pageName=='4_4' ) ? 'current':'' ">
        <a v-on:click.stop.prevent="status.show='more'">更多操作<i class="iconfont icon-angeldownblock"></i></a>
        <ul class="db-item-sub" v-show="status.show=='more'">
            <li class="db-item" :class="pageName=='4_1' ? 'current':'' "><a v-link="'/project/'+projectId+'/quit'" v-on:click="pageName='4_1'"><i class="iconfont icon-shanchu"></i>退出项目</a>
            </li>
            <li class="db-item" :class="pageName=='4_2' ? 'current':'' "><a v-link="'/project/'+projectId+'/transfer'" v-on:click="pageName='4_2'"><i
                    class="iconfont icon-shanchu"></i>项目转让</a></li>
            <li class="db-item" :class="pageName=='4_3' ? 'current':'' " ><a v-link="'/project/'+projectId+'/release'" v-on:click="pageName='4_3'"><i
                    class="iconfont icon-shanchu"></i>删除项目</a></li>
            <li class="db-item" :class="pageName=='4_4' ? 'current':'' "><a v-link="'/project/'+projectId+'/export'" v-on:click="pageName='4_4'"><i class="iconfont icon-shanchu"></i>导出项目</a>
            </li>
            <!--<li class="db-item"><a v-link="'/project/'+projectId+'/record'" v-on:click="pageName='操作记录'"><i class="iconfont icon-shanchu"></i>操作记录</a>
            </li>-->
        </ul>
    </li>
    
    </template>
    <li class="fr db-item profile">
        <a v-on:click.stop.prevent="status.show='profile'" class="cb">
            <img v-bind:src="user.avatar" v-if="user.avatar" class="db-user-logo fl">
            <img v-else src="../../assets/img/defaultlogo.jpg" class="db-user-logo fl">
            <span class="fl">个人面板</span></a>
        <ul class="db-item-sub" v-show="status.show=='profile'" v-cloak>
            <li class="db-item db-profile-info">
                <div class="cb">
                    <img v-bind:src="user.avatar" v-if="user.avatar" class="db-user-logo fl">
                    <img src="../../assets/img/defaultlogo.jpg" v-else="user.avatar" class="db-user-logo fl">
                    <div class="fl db-nav-sub-profile">
                        <p class="db-nav-profile-name">{{user.nickname}}</p>
                        <p class="db-nav-profile-email">{{user.email}}</p>
                    </div>
                </div>
            </li>
            <li  class="db-item"><a v-link="'/profile'"><i class="iconfont icon-user"></i>个人中心</a></li>
            <li class="db-item"><a v-link="'/profile/security'"><i class="iconfont icon-setting"></i>安全设置</a></li>
            <!--<li class="db-item"><a v-link="'/profile/relation'"><i class="iconfont icon-relation"></i>关联账户</a></li>-->
            <li class="db-item"><a v-on:click.stop.prevent="logout"><i class="iconfont icon-logout"></i>退出登录</a></li>
        </ul>
    </li>
    <li class="fr db-item db-msg">
        <!--<a v-on:click.stop.prevent="status.show='message'"><i class="iconfont icon-bell"></i> 消息 </a>-->
        <span class="db-subscript" v-if="messages && messages.length>0">{{messages.length}}</span>
        <ul class="db-item-sub" v-show="status.show=='message'" v-if="messages.length>0">
            <li class="db-item item-title">
                <div class="cb db-nav-msg-box">
                    <span class="fl">站内消息通知</span>
                    <!--<i class="fr iconfont icon-setting"></i>-->
                </div>
            </li>
            <li class="db-item" v-for="item in messages">
                <a href="">{{item.content}}</a>
                <p><i class="iconfont icon-time"></i>{{item.createTime}}</p>
            </li>
        </ul>
    </li>
    <li class="fr db-item">
        <a href="/">首页</a>
    </li>
</template>
<script>
    document.addEventListener('route.click',function(e){
        data.path = e.detail.path;
    });
    import utils from '../../src/utils.js';
    var data={
        path:'',
        status:{
            show:''
        },
        user:utils.user(),
        messages:[
            /*{"id":"1","content":"消息1消息1消息1消息1消息1消1","createTime":Date.now()}*/
        ]
    };
    $(document).on('click',function(){
        data.status.show=''
    });
    export default {
        data:function(){
            return data;
        },
        watch:{
            "path":function(value){
                if(value && value.indexOf('/profile')==0){
                    $('body').addClass('profile');
                }else{
                    $('body').removeClass('profile');
                }
            }
        },
        methods: {
            go:function(path){
                this.$route.router.go({path:'/project'+this.projectId+path})
            },
            logout: function () {
                utils.logout();
            }
        }
    }
</script>