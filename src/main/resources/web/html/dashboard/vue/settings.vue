<template>
<div class="form win-card-box">
    <template v-if="!loading">
    <validator name="form">
	    <ul class="win-card">
	    	  <li class="tit">
		      	<p >
		      		<i class="icon icon-set"></i> 
		      		项目信息  
		      	</p>
		      </li>
	    		
	    	  <li >
		      	<input v-model="project.name" type="text"   v-validate:project-name="['required']" maxlength="20"  placeholder="项目名称:"  /> 
		      	<span class="tip"  v-if="$form.projectName.invalid">{{$form.projectName.errors[0].message}}</span>
		      </li>
	    	 <li>
		      <textarea rows="10" placeholder="请输入项目描述" maxlength="300" class="text" v-model="project.description">{{project.description}}</textarea>
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
	            <input type="radio" name="permission" v-model="project.permission" value="PRIVATE" id="dvnr-private"> <label for="dvnr-private">私有项目</label>
	            <input type="radio" name="permission" v-model="project.permission" id="dvnr-public" value="PUBLIC"> <label for="dvnr-public">公开项目</label>
		    	<input type="submit" value="修改" v-on:click="ok" class="btn-right btn btn-primary biggest">
	            </p>
		    </li>
		    
        
	     </ul>	
    </validator>
    
    </template>
    <template v-if="loading">
        <div class="spinner">
            <div class="rect1"></div>
            <div class="rect2"></div>
            <div class="rect3"></div>
            <div class="rect4"></div>
            <div class="rect5"></div>
        </div>
    </template>
</div>
</template>
<script>
    import '../../src/vue.ex.js';
    import utils from '../../src/utils.js'
    var data={
        project:{
        	name:'',
            description: '',
            permission: 'PUBLIC',
            dbPath:'',
            dbPort:'',
            dbName:'',
            dbUser:'',
            dbPassword:''
        },
        loading:true,
        projectName:null
    };
    export default{
        route:{
            activate:function(){
                this.$parent.showProject=true;
            },
            deactivate:function(){
                this.$parent.showProject =false;
            },
            data(){
                var self = this;
                utils.get('/project/'+this.$route.params.id+'/info.json',{},function(rs){
                    self.project=rs.data.project;
                },function(){
                    self.loading=false;
                });
                self.$parent.projectId=this.$route.params.id;
                _czc.push(["_trackEvent",'接口','项目设置']);
            }
        },
        data:function(){
            return data;
        },
        methods:{
            ok(){
                var project=this.project;
                var self = this;
                delete this.project.createTime ;
                utils.post('/project/update/'+this.project.id+".json",this.project,function(rs){
                    self.project = project;
                   toastr.success('修改成功');
                })
            }
        }
    }
</script>