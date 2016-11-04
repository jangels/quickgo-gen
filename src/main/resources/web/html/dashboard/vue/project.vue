<template>
<div class="project">
		<ul class="list box" v-show="!selectPro.id">
			<li :class="projects.length==1&&$index==0?'t3' :'t'+($index+1)  " v-for="item in projects | filterBy filter in 'name'" @click="selectPro=item" >
				<p class="tit" >{{item.name}}</p>
				<p class="dis" >{{item.name}}</p>
			</li>
			<li class="add t1" v-link="{path:'/add'}" >
				<p class="tit" >+</p>
				<p class="dis" >创建一个项目</p>
			</li>
		</ul>
		
		
		<ul class="list2 box" v-show="selectPro.id">
			 <li class="t1"  v-link="{ path: '/project/'+selectPro.id,params:{name:selectPro.name}}"  >
		    	<p class="tit" >API管理</p>
		    </li>
			 <li class="t2"   v-link="'/autoCode/0'"  >
		    	<p class="tit" >代码生成</p>
		    </li>
			<li class="t3"   v-link="'/project/'+selectPro.id+'/members'">
		    	<p class="tit" >成员管理</p>
		    </li>
		    <li class="t4"   v-link="'/project/'+selectPro.id+'/settings'">
		    	<p class="tit" >项目设置</p>
		    </li>
		    <li class="tend"   v-on:click="selectPro={}">
		    	<p class="tit" >退出当前项目</p>
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
        projects:[{"name":"智慧怀柔" , "id" : "2454215saf;kj"}],
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
//				projects={
//			    	selectPro:{},
//			        showContent:false,
//			        projects:[{"name":"智慧怀柔" , "id" : "2454215saf;kj"}],
//			        filter:''
//			    };
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
           
        }
    }
</script>