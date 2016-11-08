<template>
<div class="project">
	
		
		<ul class="list box" v-show="!selectPro.id"  transition="animateRightOut">
			<li :class="projects.length==1&&$index==0?'t3' :'t'+($index+1)  " v-for="item in projects | filterBy filter in 'name'" @click="selectProFun(item)" >
				<p class="tit" >
					<span>{{item.name.substring(0,1) }}</span>
				</p>
				<p class="dis" >{{item.name}}</p>
			</li>
			<li class="add t1" v-link="{path:'/add'}" >
				<p class="tit" >+</p>
				<p class="dis" >创建一个项目</p>
			</li>
		</ul>
		
		
		<ul class="list2 box" v-show="selectPro.id" transition="animateRightOut">
			 <li class="t4"  v-link="{ path: '/project/'+selectPro.id,params:{name:selectPro.name}}"  >
		    	<p class="icon" ><i class="icon-apis"></i> </p>
		    	<p class="dis" >API管理</p>
		    </li>
			 <li class="t3"   v-link="'/autoCode/0/'+selectPro.id"  >
			 	<p class="icon" ><i class="icon-code"></i> </p>
		    	<p class="dis" >代码生成</p>
		    </li>
		 	<li class="t3"   v-link="'/project/'+selectPro.id+'/members'">
				<p class="icon" ><i class="icon-team"></i> </p>
		    	<p class="dis" >成员管理</p>
		    </li>
		    <li class="t2"   v-link="'/project/'+selectPro.id+'/settings'">
		    	<p class="icon" ><i class="icon-set"></i> </p>
		    	<p class="dis" >项目设置</p>
		    </li>
		      <li class="t1"   v-link="'/project/'+selectPro.id+'/transfer'">
				<p class="icon" ><i class="icon-team"></i> </p>
		    	<p class="dis" >项目转让和删除</p>
		    </li>
		    <li class="t6 tend"   v-on:click="selectPro={}">
		    	<p class="icon" ><i class="icon-end"></i> </p>
		    	<p class="dis" >切换项目</p>
		    </li>
		   
		</ul>
</div>
  
</template>

<script>
    import '../../src/vue.ex.js';
    import utils from '../../src/utils.js'
    var projects={
    	selectPro:{},
        showContent:false,
        projects:[{"name":"无数据" , "id" : ""}],
        filter:''
    };
    function load(self){
        utils.get('/project/list.json',{},function(rs){
        	projects.projects=rs.data.projects;
        },null,function(rs){
            
        });
    }
    export default {
        data: function () {
            return projects
        },
        route: {
			activate: function() {
				
			},
			deactivate: function() {
				
			},
			data: function(transition) {

			}
		},
        created: function() {
        	load(this);
			$("body").removeClass('loading');
			this.$parent.showProject = true;
		},
        watch: {
      
        },
        methods: {
        	selectProFun:function(item){
        		this.selectPro=item ; 
        		this.$parent.projectId = item.id ;
        		this.$parent.projectName= item.name;
        	}
           
        }
    }
</script>