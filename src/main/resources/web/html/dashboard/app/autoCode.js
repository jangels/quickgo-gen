//import  t from "../../src/t";
import "../../assets/jquery/jbox/jquery.jBox-2.3.min.js";
import "../../assets/jquery/jbox/jbox.min.css";
//import "../../assets/jquery/jquery-select2/3.4/select2.min.js";
//import "../../assets/jquery/jquery-select2/3.4/select2.min.css";
import utils from "../../src/utils";
export default {
	data: function() {
		return {
			projectId : "",
			transition : {},
			curView: 0,
			card: [{
					name: '业务表列表'
				}, {
					name: '业务表添加'
				}, {
					name: '生成方案列表'
				}, {
					name: '生成方案添加'
				}

			],
			//业务表列表
			genTableFilter : {
 				pageNo :1,
				pageSize :10,
				nameLike :"",
				comments :"",
				parentTable :"",
				orderBy:""
         	},
			genTableList:{},
			genTableFormFilter : {
				id:"",
				name:""
			},
			genTableFormList : {} ,
			saveGenTableFormFilter:{},
			genTableForm : {
				id:"",
				name:"",
				comments:"",
				className:"",
				parentTable:"",
				parentTableFk:"",
				remarks:"",
				columnList : [
					{
						id:"",
						name:"",
						delFlag:"",
						genTable :{id:""},
						comments:"",
						jdbcType:"",
						javaType:"",
						javaField:"",
						isPk:"",
						isInsert:"",
						isNull:"",
						isQuery:"",
						queryType:"",
						showType:"",
						dictType:"",
						sort:"",
						isEdit:"",
						isList:""
					}
				]
			},
			//业务表修改和添加
			genTableModify:{},
			/*生成方案*/
			genSchemeFilter:{
				pageNo :1,
				pageSize :10,
				name :""
				
			},
			genSchemeList:{},
			genSchemeModify:{},
			saveGenSchemeFilter:{genTable:{id:""}}
			
		}
	},
	route: {
		activate: function() {
			this.$parent.showProject = true;
		},
		deactivate: function() {
			this.$parent.showProject = false;
		},
		data: function(transition) {
			this.curView = transition.to.params.type;
			this.projectId = transition.to.params.id || this.$parent.projectId || "";
			this.transition = transition ;
			this.init();
		}
	},
	created: function() {
		$("body").removeClass('loading');
		this.$parent.showProject = true;
	},
	methods: {
		init: function() {
			//module.getGenTableForm({id:"9a8122400a5b45f28dd60fb00ade2d64"},this,true) ;
			this.saveGenSchemeFilter = {genTable:{}}
		},
		backTable:function(){
			if(history.length>1){
				history.go(-1);
			}else{
				self.$route.router.replace({path:'/autoCode/1'} ) ;
			}
		},
		//获取业务表列表
		getGenTableList : function(){
			this.genTableFilter.projectId = this.projectId ;
			module.getGenTableList(this.genTableFilter,this) ;
		},
		//修改业务表
		TableModifyById : function(id){
			//this.$route.router.go({path:'/autoCode/1.1'}) ;
			
			module.getGenTableForm({projectId : this.projectId,id:id},this,true) ;
		},
		//判断表是否存在 添加业务表
		genTableFormById : function(){
			this.genTableFormFilter.projectId = this.projectId ;
			module.getGenTableForm(this.genTableFormFilter,this,true) ;
		},
		//保存业务表
		saveGenTableForm : function(){
			this.saveGenTableFormFilter.projectId = this.projectId ;
			module.saveGenTableForm(this.saveGenTableFormFilter,this) ;
		},
		//配置方案列表
		genSchemeListForm : function(){
			this.genSchemeFilter.projectId = this.projectId ;
			module.genSchemeList(this.genSchemeFilter,this) ;
		},
		genSchemeModifyById : function(id){
			module.genSchemeModifyById({projectId : this.projectId,id:id},this,true) ;
		},
		saveGenSchemeForm: function(flag){
			this.saveGenSchemeFilter.flag = flag ;
			this.saveGenSchemeFilter.projectId = this.projectId ;
			module.saveGenSchemeForm(this.saveGenSchemeFilter,this) ;
		},
		deleteGenSchemeForm: function(id){
			confirm('确认要删除该业务表吗？',module.deleteGenSchemeForm({id:id},this));
		},
		deleteGenTableForm : function(id){
			confirm('确认要删除该业务表吗？',module.deleteGenTableForm({id:id},this));
		}
	},
	watch:{
		"curView": function (value) {
			var self = this ;
         	switch(parseFloat(value)){
         		case 0 : 
         			module.getGenTableList(self.genTableFilter,self) ;
         		break ;
         		case 1 : 
         			module.getGenTableForm({projectId:self.projectId},self) ;
         		break ;
         		case 2 :
         			module.genSchemeList({projectId:self.projectId},self) ;
         		break ;
         		case 3 : 
         			if(self.transition.to.query.id && !self.genSchemeModify.id ){
         				module.genSchemeModifyById({projectId:self.projectId,id:self.transition.to.query.id },this,true) ;
         			}else{
         				module.genSchemeModifyById({projectId:self.projectId},this,true) ;
         			}
         		break ;
         	}
        }
	}
}

var module = {
	//获取业务表列表
    getGenTableList:function(data,self){
//   	self.genTableList = t.rs1.data ;
        utils.post('/gen/genTable/list', data, function (rs) {
        	self.genTableList =rs.data ;
        });
    },
    //获取业务表详情
    getGenTableForm:function(data,self , query){
        utils.post('/gen/genTable/form', data, function (rs) {
        	self.genTableForm.config =  rs.data.config ;
    		self.saveGenTableFormFilter =  rs.data.genTable ;
    		self.genTableFormList =rs.data.tableList ;
    		//默认选中值
    		self.genTableFormFilter.name = rs.data.tableList[0].name ;
    		for(var i in self.saveGenTableFormFilter.columnList){
    			var t = self.saveGenTableFormFilter.columnList[i] ;
    			t.isPk = t.isPk==1 ? true : false;
				t.isInsert=t.isInsert==1 ?  true : false;
				t.isNull=t.isNull==1 ?  true : false;
				t.isQuery=t.isQuery==1 ? true : false;
				t.isEdit=t.isEdit==1 ?  true : false;
				t.isList=t.isList==1 ?  true : false;
    		}
        	if(query){
        		self.$route.router.go({path:'/autoCode/1.1/'+self.projectId,query:data} ) ;
        	}
        });
    },
    //保存业务表
    saveGenTableForm:function(data,self ){
    	var _data = {
    		id:data.id ||  "",
			name:data.name,
			comments:data.comments,
			className:data.className,
			parentTable:data.parentTable,
			parentTableFk:data.parentTableFk,
			remarks:data.remarks,
			delFlag:data.delFlag ? 1 : 0,
    	}
    	
      	for(var i in data.columnList){
       		_data["columnList["+i+"].id"] = data.columnList[i].id==null?"":data.columnList[i].id;
			_data["columnList["+i+"].name"] = data.columnList[i].name;
			_data["columnList["+i+"].delFlag"] = data.columnList[i].delFlag  ? 1 : 0;
			_data["columnList["+i+"].comments"] = data.columnList[i].comments;
			_data["columnList["+i+"].jdbcType"] = data.columnList[i].jdbcType;
			_data["columnList["+i+"].javaType"] = data.columnList[i].javaType;
			_data["columnList["+i+"].javaField"] = data.columnList[i].javaField;
			_data["columnList["+i+"].isPk"] = data.columnList[i].isPk ? 1 : 0;
			_data["columnList["+i+"].isInsert"] = data.columnList[i].isInsert ? 1 : 0;
			_data["columnList["+i+"].isNull"] = data.columnList[i].isNull ? 1 : 0;
			_data["columnList["+i+"].isQuery"] = data.columnList[i].isQuery ? 1 : 0;
			_data["columnList["+i+"].isEdit"] = data.columnList[i].isEdit ? 1 : 0;
			_data["columnList["+i+"].isList"] = data.columnList[i].isList ? 1 : 0;
			_data["columnList["+i+"].queryType"] = data.columnList[i].queryType;
			_data["columnList["+i+"].showType"] = data.columnList[i].showType;
			_data["columnList["+i+"].dictType"] = data.columnList[i].dictType;
			_data["columnList["+i+"].sort"] = data.columnList[i].sort;
			_data["columnList["+i+"].genTable.id"] = data.columnList[i].genTable&&data.columnList[i].genTable.id ?data.columnList[i].genTable.id :  "" ;
      		
    	}
        utils.post('/gen/genTable/save',   _data   , function (rs) {
			 toastr.success(rs.msg);
			 setTimeout(function(){
			 	history.go(-1);
			 },600)
        });
    },
    //获取配置方案
    genSchemeList:function(data,self){
     	///self.genSchemeList = t.rs2.data ;
        utils.post('/gen/genScheme/list', data, function (rs) {
        	self.genSchemeList = rs.data ;
        });
    },
    genSchemeModifyById:function(data,self , query){
          utils.post('/gen/genScheme/form', data, function (rs) {
         	self.genSchemeModify.config =  rs.data.config ;
    		self.saveGenSchemeFilter =  rs.data.genScheme ;
    		self.genTableFormList =rs.data.tableList ;
         	if(query){
        		self.$route.router.go({path:'/autoCode/3/'+self.projectId,query:data} ) ;
        	}
        });
    },
    //保存业务表
    saveGenSchemeForm:function(data,self ){
    	if(data.genTable && data.genTable.id){
    		data["genTable.id"] = data.genTable.id ;
    	}
    	
    	delete data.genTable ;
        utils.post('/gen/genScheme/save',   data   , function (rs) {
			 toastr.success(rs.msg);
			 setTimeout(function(){
			 	history.go(-1);
			 },600) ;
        });
    },
    deleteGenSchemeForm: function(data,self){
		utils.post('/gen/genScheme/delete',   data   , function (rs) {
			 toastr.success(rs.msg);
        });
	},
	deleteGenTableForm : function(data,self){
		 utils.post('/gen/genTable/delete',   data   , function (rs) {
			 toastr.success(rs.msg);
        });
	}
}
 