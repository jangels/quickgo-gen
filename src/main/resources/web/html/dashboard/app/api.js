import RequestHeadersVue from "../vue/api-editor-request-headers.vue";
import RequestArgsVue from "../vue/api-editor-request-args.vue";
import ResponseArgsVue from "../vue/api-editor-response-args.vue";
import utils from "../../src/utils";
import "../../assets/jsonformat/jsonFormater.js";
import "../../assets/jsonformat/jsonFormater.css";

 
//todo 浏览模式的环境切换样式
//todo 编辑模式仿造postman
RequestHeadersVue.name = 'request-headers-vue';
RequestHeadersVue.props = ['requestHeaders', 'editing'];

RequestArgsVue.name = 'request-args-vue';
RequestArgsVue.props = ['requestArgs', 'editing'];

ResponseArgsVue.name = 'response-args-vue';
ResponseArgsVue.props = ['responseArgs', 'editing'];
//
//this is nothing
var gdata = {
    status: {
        folderModal: false,
        moduleModal: false,
        importModal: false,
        envModal: false,
        loading: true,
        apiLoading: false,
        moveCopyModal: false,
        moveCopyId: '' ,
        showEnvs:false,
        showEnvValues:false
    },
    ws: {
        instance: null,
        connected: false,
        message: '',
        log: '',
        url: '',
        destroy(){
            if (this.instance) {
                this.instance.close();
                this.connected = false;
                this.log = '';
                this.url = '';
                this.message = '';
            }
        }
    },
    flag: {
        import: null,
        actionId: null,
        move: null,
        moveCopyName: null,
        moveCopySelectModuleId: null,
        moveCopySelectFolderId: null,
        moveCopyId: null,
        resultActive: 'content',
        tempEnv:{vars:[]}   ,
        prefix:'$prefix$',
        varname:'$变量名$',
        tab:'body',
        headers:["User-Agent","Accept","Accept-Charset","Accept-Encoding","Accept-Language","Accept-Datetime","Authorization","Cache-Control","Connection","Cookie","Content-Length","Content-MD5","Content-Type"],
        requests:["name","id","password","email","createtime","datetime","createTime","dateTime","user","code","status","type","msg","message","time","image","file","token","accesstoken","access_token","province","city","area","description","remark","logo"],
        responses:["name","id","password","email","createtime","datetime","createTime","dateTime","user","code","status","type","msg","message","error","errorMsg","test","fileAccess","image","require","token","accesstoken","accessToken","access_token","province","city","area","remark","description","logo"]
    },
    error: {
        projectNotExists: false,
        noModule: false,
        noInterface: false
    },
    envs:null,
    importValue: null,
    editing: false,
    folderName: '',
    moduleName: '',
    showGuide: true,
    modules: [],
    project: {},
    currentApi: {result: null},
    currentModule: {},
    currentFolder: null,
    currentEnv: null,
    id: '',
    extVer: false,
    collapse: false,
    results: {},
    genTableFormList : [],
    selectGenTable: {id:"",name:""}
};
var page = {
    x2js:new X2JS(),
    listener:{
        success:function (e) {
            new Result().resolve(e.detail, gdata.currentApi.contentType);
        },
        error:  function (e) {
            console.log("result.error");
        },
        complete:  function (e) {
            xhrComplete(gdata, e);
        }
    },
    //获取业务表详情
    getGenTableForm:function(data){
        utils.get('/gen/genTable/queryByProjectId', data, function (rs) {
        	gdata.genTableFormList = rs.data ;
        });
    },
    //获取业务表详情
    getGenTableFormDatial:function(data){
        utils.post('/gen/genTable/form', data, function (rs) {
        	var _rs = rs.data.genTable.columnList , 
        		_data = {
        			requestArgs:[],
        			responseArgs:[
        				{"children":[],"name":"module","type":"object","require":"true"}
        			]
        		} ;
        	_rs.forEach(function(item){
        		_data.requestArgs.push({
        			"require":""+item.isNull=="0"?"false":"true",
		            "children":[], 
		            "type": !("/LONG/INTEGER/DOUBLE/INT".indexOf(item.javaType.toUpperCase())<0) ?"number" : item.javaType ,
		            "name":item.name,
		            "description":item.comments ,
		            "defaultValue" :""
        		});
        		_data.responseArgs[0]["children"].push({
        			"require":""+item.isNull=="0"?"false":"true",
		            "children":[], 
		            "type": !("/LONG/INTEGER/DOUBLE/INT".indexOf(item.javaType.toUpperCase())<0) ?"number" : item.javaType ,
		            "name":item.name,
		            "description":item.comments ,
		            "defaultValue" :""
        		});
        		
        	});
        	gdata.currentApi.requestArgs = _data.requestArgs;
        	gdata.currentApi.responseArgs = _data.responseArgs;
        });
    },
    updateInterface:function(id){
        utils.get('/interface/update/'+id+'.json',{},function(rs){
            let isBreak;
            gdata.modules.forEach(function(m){
                m.folders.forEach(function(f){
                    let index = -1;
                    let originalFolderId;
                    f.children.forEach(function(item,i){
                        if(item.id == id){
                            index = i;
                            originalFolderId=item.folderId;
                            return true;
                        }
                    });
                    if(rs.data.interface.folderId == originalFolderId){
                        if(index>=0){
                            f.children.$set(index,rs.data.interface);
                            isBreak = true;
                            return true;
                        }
                    }
                });
                if(isBreak){
                    return true;
                }
            });
        });
    },
    createInterface:function(newInterfaceId,folderId){
        utils.get('/interface/'+newInterfaceId+'.json',{},function(rs){
            gdata.modules.forEach(function(m){
                let isBreak;
                m.folders.forEach(function(item,i){
                    if(item.id == folderId){
                        item.children.push(rs.data.interface);
                        isBreak = true;
                        return true;
                    }
                });
                if(isBreak){
                    return true;
                }
            });
        });
    },
    deleteInterface:function(id){
        gdata.modules.forEach(function(m){
            let isBreak;
            m.folders.forEach(function(f){
                var index = null;
                f.children.forEach(function(item,i){
                    if(item.id == id){
                        index = item;
                        isBreak=true;
                        return true;
                    }
                });
                if(index!=0){
                    if(gdata.currentApi.id == index.id){
                        gdata.showGuide = true;
                    }
                    f.children.$remove(index);
                    return true;
                }
            });
            if(isBreak){
                return true;
            }
        });
    },
    createFolder:function(newfolderId,moduleId){
        utils.get('/interfacefolder/'+newfolderId+'.json',{},function(rs){
            gdata.modules.forEach(function(item){
                if(item.id == moduleId){
                    item.folders.push(rs.data.folder);
                    return true;
                }
            });
        });
    },
    updateFolder:function(id){
        utils.get('/interfacefolder/'+id+'.json',{},function(rs){
            gdata.modules.forEach(function(m){
                let isBreak;
                m.folders.forEach(function(item,index){
                    if(item.id == id){
                        rs.data.folder.children = item.children;
                        let folder = $.extend(item,rs.data.folder);
                        m.folders.$set(index,folder);
                        isBreak= true;
                        return true;
                    }
                });
                if(isBreak){
                    return true;
                }
            });
        });
    },
    deleteFolder:function(folderId){
        gdata.modules.forEach(function(m){
            let folder = null;
            m.folders.forEach(function(item,index){
                if(item.id == folderId){
                    folder = item;
                    return true;
                }
            });
            if(folder!=null){
                if(gdata.currentApi.folderId == folder.id){
                    gdata.showGuide = true;
                }
                m.folders.$remove(folder);
                return true;
            }
        });
    },
    createModule:function(moduleId){
        utils.get('/module/'+moduleId+'.json',{},function(rs){
            gdata.modules.push(rs.data.module);
        });

    },
    updateModule:function(moduleId){
        utils.get('/module/'+moduleId+'.json',{},function(rs){
            gdata.modules.forEach(function(item,index){
                if(item.id == moduleId){
                    rs.data.module.folders = item.folders;
                    let module = $.extend(item,rs.data.module);
                    gdata.modules.$set(index,module);
                }
            });
        });
    },
    deleteModule:function(moduleId){
        let module;
        gdata.modules.forEach(function(item){
            if(item.id == moduleId){
                module = item;
                return true;
            }
        });
        if(module){
            if(gdata.currentModule.id == module.id){
                if (gdata.modules.length > 0) {
                    gdata.currentModule = gdata.modules[0];
                    gdata.showGuide = true;
                } else {
                    gdata.error.noModule = true;
                }
            }
            gdata.modules.$remove(module);
        }
    },
    task:{
        instance:null,
        num:0,
        init:function(){
            var ws = new WebSocket(utils.config.websocket+'/message');
            this.instance = ws;
            var self = this;
            function reconnect(){
                if(self.num<3){
                    ws = new WebSocket(utils.config.websocket+'/message');
                    console.log('reconnect');
                }
            }
            ws.onopen = function (evt) {
                ws.send("projectId:"+gdata.id);
                setInterval(function(){
                    if(ws.readyState == ws.OPEN){
                        ws.send("projectId:"+gdata.id);
                    }
                },55000);
            };
            ws.onclose = function (evt) {
                switch (evt.code){
                    case 1006:
                    case 1001:
                        setTimeout(reconnect,5000);
                        break
                }

            };
            ws.onmessage = function (evt) {
                var data = utils.toJSON(evt.data);
                if(data.projectId != gdata.id){
                    return;
                }
                if(data.token.substring(0,10) == utils.token().substring(0,10)){
                    return ;
                }
                switch (data.action){
                    case "interface.update":
                        page.updateInterface(data.interfaceId);
                        break;
                    case "interface.create":
                    case "interface.copy":
                        page.createInterface(data.interfaceId,data.ext[0]);
                        break;
                    case "interface.delete":
                        page.deleteInterface(data.interfaceId);
                        break;
                    case "folder.create":
                        page.createFolder(data.folderId,data.ext[0]);
                        break;
                    case "folder.update":
                        page.updateFolder(data.folderId);
                        break;
                    case "folder.delete":
                        page.deleteFolder(data.folderId);
                        break;
                    case "module.create":
                        page.createModule(data.moduleId);
                        break;
                    case "module.update":
                        page.updateModule(data.moduleId);
                        break;
                    case "module.delete":
                        page.deleteModule(data.moduleId);
                        break;

                }
            };
            ws.onerror = function (evt) {
                console.log('onerror');
            };
        },
        destroy:function(){
            if(this.instance){
                this.instance.close();
                this.instance = null;
            }
        }
    }
};
export default{
    components: {
        RequestHeadersVue: RequestHeadersVue,
        RequestArgsVue: RequestArgsVue,
        ResponseArgsVue: ResponseArgsVue
    },
    created: function () {
        $("body").removeClass('loading');
    },
    route: {
        data: function (transition) {
            //初始化
            this.currentFolder=null;
            //this.currentModule = {};
            this.currentApi={result:null}
            this.$parent.$data.pageName = '接口列表';
            this.id = transition.to.params.id;
            var self = this;
            //如果实时会出现获取不到的问题
            setTimeout(function () {
                self.extVer = document.body.getAttribute("data-ext-version");
                if (self.extVer) {
                    self.extVer = parseFloat(self.extVer);
                }
                console.log('extVer:' + self.extVer);
            }, 1000);

            //
            $(document).click(function () {
                self.flag.actionId = null;
                self.status.showEnvs=false;
            });
            (function () {
                var clipboard = new Clipboard('#api-result-copy', {
                    target: function () {
                        return document.querySelector('#api-result-content');
                    }
                });
                var headerClipboard = new Clipboard('#api-result-header-copy', {
                    target: function () {
                        return document.querySelector('#api-result-headers');
                    }
                });

                clipboard.on('success', function (e) {
                    //console.log(e);
                });
                clipboard.on('error', function (e) {
                    console.log(e);
                });
            })();


            (function(){
                $('body').undelegate('.xyj-dropdown-toggle','click').delegate('.xyj-dropdown-toggle','click',function(e){
                    $(this).next().toggle();
                    e.stopPropagation();
                }).click(function(){
                    $('.xyj-dropdown-list').hide();
                });

            })();
            page.task.destroy();
            page.task.init();
        },
        activate: function (transition) {
            this.$parent.showProject = true;
            $('.dashboard').addClass('max');
            transition.next();
            document.addEventListener('result.success', page.listener.success);
            document.addEventListener('result.complete',page.listener.complete);
            document.addEventListener('result.error',page.listener.error);
        },
        deactivate: function () {
            this.$parent.showProject = false;
            $('.dashboard').removeClass('max');
            window.editor = null;
            document.removeEventListener('result.success',page.listener.success,false);
            document.removeEventListener('result.error',page.listener.error,false);
            document.removeEventListener('result.complete',page.listener.complete);
            page.task.destroy();
        }
    },
    computed: {
        requestURL: function () {
            let temp = this.currentApi.url;
            if(!temp){return '';}
            if(this.currentEnv && this.currentEnv.vars){
                this.currentEnv.vars.forEach(function(item){
                    let reg=new RegExp('\\$'+item.name+'\\$','g');
                     temp = temp.replace(reg,item.value);
                });
                if(this.currentApi.urlArgs && this.currentApi.urlArgs.length>0){
                    this.currentApi.urlArgs.forEach(function(item){
                        let name = '{'+item.name+'}';
                        let reg = new RegExp(name,'g');
                        temp = temp.replace(reg,item.value || name)
                    });
                }
                return temp;
            }
            return "";
        },
        xmlpreview:function(){
            let args = utils.toJSON(this.currentApi.requestArgs);
            let text='<?xml version="1.0" encoding="UTF-8"?>\n<xml>\n';
            args.forEach(function(item){
                var name = item.name.replace(/\s/g,'');
                text += '    <'+name+'>' +' '+'</'+name+'>\n';
            });
            text+= '</xml>';
            return text;
        }
    },
    watch: {
    	"selectGenTable.id":function(value){
    		gdata.genTableFormList.forEach(function(item){
    			if(item.id==value){
    				gdata.selectGenTable.name = item.name ;
    			}
    		})
    		page.getGenTableFormDatial({id:value})
    	},
        "status.folderModal": function (value) {
            if (!value) {
                var self = this;
                /*setTimeout(function () {
                    self.$data.currentFolder = null;
                }, 100)*/
            }
            if (value) {
                $("body").addClass("modal-open");
            } else {
                $("body").removeClass("modal-open");
            }
        },
        "status.moduleModal": function (value) {
            if (!value) {
                var self = this;
                setTimeout(function () {
                    self.$data.moduleId = '';
                    self.$data.moduleName = '';
                }, 100)
            }

            if (value) {
                $("body").addClass("modal-open");
            } else {
                $("body").removeClass("modal-open");
            }
        },
        "status.importModal": function (value) {
            if (value) {
                $("body").addClass("modal-open");
            } else {
                $("body").removeClass("modal-open");
            }
        },
        "status.envModal":function(value){
            if (value) {
                $("body").addClass("modal-open");
            } else {
                $("body").removeClass("modal-open");
            }
        },
        "id": function (value) {
            this.$parent.projectId = value;
            this.showGuide= true;
            var self = this;

            reget(self);
            if (window._czc) {
                _czc.push(["_trackPageview", location.pathname + (location.hash), document.referrer]);
            }
        },
        "status.loading": function (value) {
            if (!value) {
                document.title = (this.project.name || '') + '-' + (this.currentModule.name || '');
                if (window.editor && window.editor) {
                    window.editor.editor.remove();
                    window.editor = null;
                }
                var value = this.project.details;
                if (this.editing && this.showGuide && !window.editor) {
                    initEditor(this.project.details, this);
                }
                if (!this.editing) {
                    renderViewBox(value);
                }
            }
        },
        "editing": function (value) {
            if (value) {
                if (!window.editor && this.showGuide) {
                    var desc = this.project.details;
                    initEditor(desc, this);
                }
                //请求表信息
                if( gdata.genTableFormList.length ==  0  ){page.getGenTableForm({projectId:gdata.id});}
            } else {
                renderViewBox(this.project.details);
                 
            }
            _czc.push(["_trackEvent", "接口", "切换模式",value+""]);
        },
        "showGuide": function (value) {
            if (value && this.editing) {
                if (!window.editor) {
                    var desc = this.project.details;
                    initEditor(desc, this);
                }
            }
        },
        "currentApi.result": function (value) {
            if (this.currentApi.id) {
                if (!this.results[this.currentApi.id]) {
                    this.results[this.currentApi.id] = {};
                }
                this.results[this.currentApi.id].content = value;
            }
        },
        "currentApi.resultHeaders": function (value) {
            if (this.currentApi.id) {
                if (!this.results[this.currentApi.id]) {
                    this.results[this.currentApi.id] = {};
                }
                this.results[this.currentApi.id].headers = value;
            }
        },
        "currentEnv": function (value) {
            if(value){
                localStorage.setItem(this.id+'_env', value.id);
            }
        }
    },
    data: function () {
        return gdata;
    },
    methods: {
        updateProject: function () {
            this.project.details = window.editor.getMarkdown();
            utils.post('/project/update/'+this.id+'.json',{details:this.project.details},function(){
                toastr.success('修改成功');
            })
        },
        apiDescClick: function () {
            this.showGuide = true;
            //this.currentFolder = {children: []};
            this.currentApi = {};
            this.ws.destroy();
        },
        envOver:function(data,e){
            this.status.showEnvValues=true;
            this.flag.tempEnv = $.extend(true,{},data);
            var top= $(e.target).offset().top - $(e.target).parent().offset().top;
            $('#api-env-content').css('top',top).show();
        },
        envClick:function(e){
            $('#api-env-details').css('left',$(e.currentTarget).offset().left);
            this.status.showEnvs=true;
            _czc.push(["_trackEvent",'接口','环境变量点击']);
        },
        envEdit:function(){
            this.status.envModal=true;
            this.flag.tempEnv.vars.push({});
            _czc.push(["_trackEvent",'接口','环境变量编辑']);
        },
        envNewLine:function(index){
            if(index == this.flag.tempEnv.vars.length-1){
                this.flag.tempEnv.vars.push({});
            }
        },
        createEnv:function () {
            this.status.envModal=true;
            this.flag.tempEnv={vars:[{}]};
            _czc.push(["_trackEvent",'接口','环境变量创建']);
        },
        envSave:function(){
            let self= this;
            if(!this.flag.tempEnv.name){
                toastr.error('请输入环境名称');
                return false;
            }
            if(this.flag.tempEnv.vars){
                this.flag.tempEnv.vars = this.flag.tempEnv.vars.filter(function(item){
                    return item.name!=undefined && item.name != null && item.name != '';
                });
            }
            if(!this.flag.tempEnv.vars){
                this.flag.tempEnv.vars=[];
            }
            if(this.flag.tempEnv.t){
                let t = this.flag.tempEnv.t;
                let index =this.envs.findIndex(function(item){
                    return item.t ==t;
                });
                if(index!=-1){
                    this.envs.$set(index,this.flag.tempEnv);
                }
            }else{
                this.flag.tempEnv.t = Date.now();
                this.envs.push(this.flag.tempEnv);
            }
            this.envs = this.envs.map(function(item){
                return {name:item.name,t:item.t,vars:item.vars}
            });
            utils.post('/project/'+this.id+'.json',{environments:JSON.stringify(this.envs)},function(rs){
               toastr.success('保存成功');
                self.status.envModal = false;
                if(self.envs.length == 1){
                    self.currentEnv = self.envs[0];
                }else{
                    self.envs.forEach(function(item){
                        if(item.t==self.flag.tempEnv.t){
                            self.currentEnv = item;
                        }
                    })
                }
            });
        },
        envRemove:function(){
            let self = this;
            let t= this.flag.tempEnv.t;
            let index = this.envs.findIndex(function(item){
                return item.t == self.flag.tempEnv.t;
            });
            this.envs.$remove(this.envs[index]);
            utils.post('/project/'+this.id+'.json',{environments:JSON.stringify(this.envs)},function(rs){
                toastr.success('移除成功');
                if(self.envs.length == 0){
                    self.currentEnv = {name:'环境切换',vars:[]};
                }else{
                    if(self.currentEnv.t == t){
                        self.currentEnv=self.envs[0];
                    }
                }
            });
            _czc.push(["_trackEvent",'接口','环境变量移除']);
        },
        envCopy:function(){
            var vars = $.extend(true,[],this.flag.tempEnv.vars);
            var temp = {t:Date.now(),name:this.flag.tempEnv.name+'copy',vars:vars};
            this.envs.push(temp);
            let self = this;
            utils.post('/project/'+this.id+'.json',{environments:JSON.stringify(this.envs)},function(rs){
                toastr.success('复制成功');
            });
            _czc.push(["_trackEvent",'接口','环境变量复制']);
        },
        folderNew: function (event) {
            this.status.folderModal = true;
            this.currentFolder = null;
            this.folderName = '';
            event.stopPropagation();
            focusFolderName();
            _czc.push(["_trackEvent",'接口','创建分类']);
        },
        folderEdit: function (item, event) {
            this.status.folderModal = true;
            this.currentFolder = item;
            this.folderName = item.name;

            event.stopPropagation();
            _czc.push(["_trackEvent",'接口','编辑分类']);
        },
        folderSave: function () {
            this.$validate(true);
            if (this.$ff.invalid) {
                return false;
            }
            if (!this.folderName) {
                toastr.error('文件夹名称为空');
                return false;
            }
            var name = this.folderName;
            var self = this;
            if (this.$data.currentFolder) {
                var id = self.currentFolder.id;
                utils.post('/interfacefolder/update/' + this.currentFolder.id + ".json", {name: name}, function (rs) {
                    if (rs.code == 0 || parseInt(rs.result)==200) {
                        self.currentModule.folders.forEach(function (item) {
                            if (item.id == id) {
                                item.name = name;
                            }
                        });
                    }
                });

            } else {
                utils.post('/interfacefolder/create.json', {
                    moduleId: self.currentModule.id,
                    projectId: self.currentModule.projectId,
                    name: name

                }, function (rs) {
                    console.log(rs);

                    if (rs.code == 0 || parseInt(rs.result)==200) {
                        self.currentModule.folders.push({
                            name: name, id: rs.data, children: []
                        });
                    }
                });
            }
            gdata.status.folderModal = false;
            //this.currentFolder=null;
            this.folderName = null;
            _czc.push(["_trackEvent",'接口','修改分类']);
        },
        folderDelete: function (item, event) {
            var self = this;
            utils.delete('/interfacefolder/delete/' + item.id + ".json", function (rs) {
                self.$data.currentModule.folders.$remove(item);
            });
            event.stopPropagation();
            _czc.push(["_trackEvent",'接口','删除分类']);
        },
        folderNewApi: function (item, event) {
            event.stopPropagation();
            this.flag.actionId = null;
            this.showGuide = false;
            if(item == null){
                if(this.currentFolder){
                    item = this.currentFolder;
                }else{
                    item = this.currentModule.folders[0];
                }
            }
            if(!item){
                toastr.error('请先添加一个分类。');
                this.showGuide = true;
                return false;
            }
            this.currentApi = {
                protocol: localStorage.getItem('form.protocol') || 'HTTP',
                requestMethod: localStorage.getItem('form.requestMethod') || 'GET',
                dataType: localStorage.getItem('form.dataType') || 'X-WWW-FORM-URLENCODED',
                contentType: localStorage.getItem('form.contentType') ||'JSON',
                requestHeaders: [],
                requestArgs: [],
                responseArgs: [],
                result: '',
                folderId:item.id
            };
            this.currentFolder = item;
            if (document.documentElement.scrollTop > 100) {
                document.documentElement.scrollTop = 110;
            }
            
            _czc.push(["_trackEvent",'接口','新建api']);
        },
        folderClick: function (event) {
            var $dom = $(event.currentTarget);
            $dom.toggleClass("open");
            $dom.next().slideToggle();
            _czc.push(["_trackEvent",'接口','文件夹点击']);
        },
        apiClick: function (item, folder) {
            _czc.push(["_trackEvent",'接口','点击',this.currentApi.name,this.currentApi.id]);
            this.currentFolder = folder;
            this.showGuide = false;
            this.currentApi = item;
             this.selectGenTable.id=item.tableId;
            initInterface(this, item);

            if (document.documentElement.scrollTop > 100) {
                document.documentElement.scrollTop = 110;
            }
        },
        apiDelete: function (item, arr, event) {

            event.stopPropagation();
            let self = this;
            utils.delete('/interface/delete/' + item.id + ".json", function (rs) {
                if(item.id == self.currentApi.id){
                    defaultView(self);
                }
                arr.$remove(item);
            });
            _czc.push(["_trackEvent",'接口','接口删除']);
        },
        apiSave: function () {
            let data = this.currentApi;//$.extend({},this.currentApi);
            if (!data.id) {
                data.moduleId = this.currentModule.id;
                data.projectId = this.currentModule.projectId;
                //data.folderId = this.currentFolder.id;
            }
            let temp = $.extend({}, data);
            temp.urlArgs = undefined;
            temp.requestArgs = JSON.stringify(temp.requestArgs);
            temp.responseArgs = JSON.stringify(temp.responseArgs);
            temp.requestHeaders = JSON.stringify(temp.requestHeaders);
            temp.tableName = gdata.selectGenTable.name;
            temp.tableId = gdata.selectGenTable.id;
            let self = this;
            let tempFolder = self.currentFolder;
            let tempModule = self.currentModule;
            utils.post('/interface/save.json', temp, function (rs) {
                toastr.success('保存成功', '', {timeOut: 2000, "positionClass": "toast-top-right"});
                let folder;
                tempModule.folders.forEach(function(item){
                    if(item.id == data.folderId){
                        folder = item;
                        return true;
                    }
                });
                //放置到folder中
                if (data.id) {
                    var index = -1;
                    tempFolder.children.forEach(function (item, i) {
                        if (item.id == data.id) {
                            index = i;
                            return true;
                        }
                    });
                    if(tempFolder.id == data.folderId){
                        if (index != -1) {
                            folder.children.$set(index, data);
                        }
                    }else{
                        folder.children.push(data);
                        tempFolder.children.$remove(tempFolder.children[index]);
                        if(tempFolder.id == self.currentFolder.id){
                            self.currentFolder = folder;
                        }
                    }
                } else {
                    data.id = rs.data;
                    folder.children.push(data);
                }
                //设置默认值
                localStorage.setItem('form.protocol',data.protocol);
                localStorage.setItem('form.requestMethod',data.requestMethod);
                localStorage.setItem('form.dataType',data.dataType);
                localStorage.setItem('form.contentType',data.contentType);
            });
            _czc.push(["_trackEvent",'接口','接口保存']);
        },
        apiVarsClick:function(name,e){

            this.currentApi.url = this.currentApi.url+('$'+name+'$')
        },
        apiSubmit: function () {

            var self = this;
            //var url = this.requestURL;
            var url = $('#requestURL').val();
            var args = getRequestArgs();
            for (let name in args) {
                let key = self.currentApi.id + ':args:' + name;
                let value = args[name];
                if (typeof value == 'string') {
                    localStorage.setItem(key, value);
                }
            }
            //如果是图片或二进制
            if (self.currentApi.contentType == "IMAGE" || self.currentApi.contentType == 'BINARY') {
                var params = '';
                for (var p in args) {
                    params += (p + '=' + args[p] + '&');
                }
                window.open(url + '?' + params);
                params = undefined;
                return true;
            }

            var headers = getRequestHeaders();
            for (let name in headers) {
                let key = self.currentApi.id + ':headers:' + name;
                let value = headers[name];
                if (typeof value == 'string') {
                    localStorage.setItem(key, value);
                }
            }


            var params = {
                url: url,
                cache: false,
                headers: headers,
                type: self.currentApi.requestMethod,
                data: args,
                beforeSend: function (xhr) {
                    xhr.beginTime = Date.now();
                },
                dataType: self.currentApi.contentType,
                crossDomain:true,
                xhrFields: {
                    withCredentials: true
                },
                jsonpCallback: self.currentApi.contentType == 'JSONP' ? 'callback' : undefined,
                complete(xhr, status){
                    var e = {
                        type: status,
                        text: (xhr.responseText || xhr.statusText),
                        headers: xhr.getAllResponseHeaders(),
                        readyState: xhr.readyState,
                        responseText: xhr.responseText,
                        status: xhr.status || 0,
                        statusText: xhr.statusText,
                        useTime: Date.now() - xhr.beginTime
                    };
                    if (status != 'success') {
                        var msg = (xhr.responseText || xhr.statusText);
                        if (status == 'error') {
                            msg = ('status:' + xhr.status + ' readyState:' + xhr.readyState + '  errorText:' + msg);
                        }
                        e.text = msg
                    }
                    xhrComplete(self, {detail: e});
                },
                success(rs){
                    new Result().resolve(rs, self.currentApi.contentType);
                    //self.result = rs;
                }


            };
            switch (this.currentApi.dataType) {
                case "FORM-DATA":
                    params.contentType = false;
                    params.processData = false;
                    break;
                case "RAW":
                    params.data = $('#rawBody').val() || '';
                    params.processData = false;
                    params.contentType = 'text/plain';
                    break;
                case "BINARY":
                    params.processData = false;
                    params.contentType = 'application/octet-stream';
                    params.data = $('#binaryBody')[0];
                default:
                    break;
            }
            self.status.apiLoading = true;
            // chrome 插件中jsonp 会出问题
            if (this.extVer && self.currentApi.contentType != 'JSONP') {
                delete params['complete'];
                delete params['success'];
                delete params['error'];
                delete params['beforeSend'];
                if (this.currentApi.dataType == 'BINARY') {
                    params.data = '#binaryBody';
                }
                var ce = new CustomEvent('request', {
                    detail: params
                });
                document.dispatchEvent(ce);
            } else {
                $.ajax(params);
            }
            _czc.push(["_trackEvent",'接口','测试',this.currentApi.name,this.currentApi.id]);
        },
        apiMock: function () {

            var self = this;
            //如果是图片或二进制
            if(self.currentApi.contentType == 'IMAGE'){
                window.open('http://www.xiaoyaoji.com.cn/test/image/test.jpg');
                return true;
            }else if(self.currentApi.contentType == 'BINARY'){
                window.open('http://www.xiaoyaoji.com.cn/test/binary/blank.exe');
                return true;
            }
            console.log(self.currentApi.responseArgs);
            let rs;
            switch (self.currentApi.contentType){
                case "JSON":
                case "JSONP":
                    rs = mockJSON(self.currentApi.responseArgs);
                    break;
                case "TEXT":
                    rs = "欢迎使用快狗接口文档管理工具。";
                    break;
                case "HTML":
                    rs = "<html><head><title>标题</title></head><body>欢迎使用快狗接口文档管理工具。</body></html>";
                    break;
                case "XML":
                    rs =mockJSON(self.currentApi.responseArgs);
                    rs = page.x2js.json2xml_str(rs);
                    break;
            }
            new Result().resolve(rs, self.currentApi.contentType);
            _czc.push(["_trackEvent",'接口','mock',this.currentApi.name,this.currentApi.id]);
        },
        moduleDelete: function (item) {
            if(this.modules.length<=1){
                toastr.error('至少需要保留一个模块');
                return false;
            }

            if (!confirm('是否确认删除?')) {
                return false;
            }
            this.modules.$remove(item);
            utils.delete('/module/delete/' + item.id + '.json');
            if (this.modules.length > 0) {
                this.currentModule = this.modules[0];
                this.showGuide = true;
            } else {
                this.error.noModule = true;
            }
            _czc.push(["_trackEvent",'接口','模块删除']);
        },
        moduleEdit: function (item) {
            this.status.moduleModal = true;
            this.moduleName = item.name;
            this.moduleId = item.id;
            focusModuleName();
        },
        moduleNew: function () {
            this.status.moduleModal = true;
            focusModuleName();
            this.moduleName = '';
        },
        moduleClick: function (item) {
            if (!item.folders) {
                item.folders = [];
            }
            this.currentModule = item;
            this.currentApi = {};
            this.currentFolder = null;
            this.showGuide = true;
            _czc.push(["_trackEvent",'接口','模块点击',item.name,item.id]);
        },
        moduleSave: function () {
            this.$validate(true);
            if (this.$mf.invalid) {
                return false;
            }
            if (!this.moduleName) {
                toastr.error('模块名称为空');
                return false;
            }
            var self = this;
            if (this.moduleId) {
                var moduleId = self.moduleId;
                var name = self.moduleName;
                utils.post('/module/' + moduleId + ".json", {name: name}, function (rs) {
                    if (rs.code == 0 || parseInt(rs.result)==200 ) {
                        self.modules.forEach(function (item) {
                            if (item.id == moduleId) {
                                item.name = name;
                            }
                        });
                    }
                });
            } else {
                var moduleName = this.moduleName;
                utils.post('/module.json', {projectId: self.id, name: moduleName}, function (rs) {
                    if (rs.code == 0 || parseInt(rs.result)==200) {
                        gdata.modules.push({
                            name: moduleName,
                            projectId: self.currentModule.projectId,
                            id: rs.data,
                            folders: []
                        })
                    }
                });
            }
            gdata.status.moduleModal = false;
            this.moduleName = '';
            this.moduleId = '';
            _czc.push(["_trackEvent",'接口','模块保存']);
        },
        insertNewResponseArgsRow: function () {
            gdata.currentApi.responseArgs.push({require: "true", children: [], type: 'string'});
        },
        insertNewRequestHeadersRow: function () {
            gdata.currentApi.requestHeaders.push({require: 'true', children: []});
        },
        insertNewRequestArgsRow: function () {
            gdata.currentApi.requestArgs.push({require: "false", children: [], type: 'string'});
        },
        import2RequestArgs(){
            this.status.importModal = true;
            this.flag.import = "requestArgs";
        },
        import2RequestHeaders(){
            this.status.importModal = true;
            this.flag.import = "requestHeaders";
        },
        import2ResponseArgs(){
            this.status.importModal = true;
            this.flag.import = "responseArgs";
        },
        importOk(){
            if (!this.importValue) {
                toastr.error('导入内容为空');
                return false;
            }

            var data = null;
            try {
                data = utils.toJSON(this.importValue)
            } catch (e) {
                alert('JSON格式有误');
                return;
            }
            var temp = [];
            parseImportData(data, temp);
            var self = this;
            temp.forEach(function (d) {
                if (self.flag.import == 'requestArgs') {
                    self.currentApi.requestArgs.push(d);
                } else if (self.flag.import == 'requestHeaders') {
                    self.currentApi.requestHeaders.push(d);
                } else if (self.flag.import == 'responseArgs') {
                    self.currentApi.responseArgs.push(d);
                }
            });
            this.status.importModal = false
            _czc.push(["_trackEvent",'接口','导入json']);
        },
        wsConnect(){
            //var url = this.ws.url;
            var url =$('#websocketRequestURL').val()
            var ws = new WebSocket(url);
            this.ws.instance = ws;
            var self = this;
            ws.onopen = function (evt) {
                self.ws.log = 'connected';
                self.ws.connected = true;
            };
            ws.onclose = function (evt) {
                self.ws.log += '\nonClose!';
                self.ws.connected = false;
            };
            ws.onmessage = function (evt) {
                self.ws.log += '\nonMessage:' + evt.data;
            };
            ws.onerror = function (evt) {
                self.ws.log += '\nonError:' + (evt.data || '');
            };
            _czc.push(["_trackEvent",'接口','websocket测试']);
        },
        wsDisconnect(){
            this.ws.instance.close()
        },
        wsSendMessage(){
            this.ws.instance.send(this.ws.message);
            this.ws.log += '\n sent message:' + this.ws.message;
        },
        listItemCopy: function (type, id, move) {
            if (type == 'api') {
                this.flag.moveCopyName = '接口';
            } else if (type == 'folder') {
                this.flag.moveCopyName = '分类';
            }
            this.flag.move = (move == 'move');
            this.status.moveCopyModal = true;
            this.flag.moveCopyId = id;
            _czc.push(["_trackEvent",'接口','复制移动'+type]);
        },
        copyMoveOk: function () {
            if (this.flag.move) {
                if (this.flag.moveCopyName == '分类') {
                    if (this.flag.moveCopySelectModuleId == this.currentModule.id) {
                        toastr.error('同一模块无须移动');
                        return false;
                    } else {
                        copyMove({type: 'folder', action: 'move', moduleId: this.flag.moveCopySelectModuleId}, this);
                    }
                } else {
                    copyMove({
                        type: 'api',
                        action: 'move',
                        moduleId: this.flag.moveCopySelectModuleId,
                        folderId: this.flag.moveCopySelectFolderId
                    },this);
                }
            } else {
                //copy
                if (this.flag.moveCopyName == '分类') {
                    copyMove({type: 'folder', action: 'copy', moduleId: this.flag.moveCopySelectModuleId},this);
                } else {
                    copyMove({
                        type: 'api',
                        action: 'copy',
                        moduleId: this.flag.moveCopySelectModuleId,
                        folderId: this.flag.moveCopySelectFolderId
                    },this);
                }
            }
        },
        openNewWindow:function(){
            let win = window.open('','new');
            win.document.documentElement.innerHTML='';
            win.document.write(utils.unescape(this.currentApi.result));
            win.document.close();
        }
    }
}

function initInterface(self, item) {
	
	
    if (!item.requestArgs) {
        item.requestArgs = []
    }
    if (!item.requestHeaders) {
        item.requestHeaders = []
    }
    if (!item.responseArgs) {
        item.responseArgs = []
    }
    if (!item.resultHeaders) {
        item.resultHeaders = "";
    }
    if (item.requestArgs.constructor.name == 'String') {
        item.requestArgs = JSON.parse(self.currentApi.requestArgs);
    }
    if (item.responseArgs.constructor.name == 'String') {
        item.responseArgs = JSON.parse(self.currentApi.responseArgs);
    }
    if (item.requestHeaders.constructor.name == 'String') {
        item.requestHeaders = JSON.parse(self.currentApi.requestHeaders);
    }
    if (!item.results) {
        item.results = {};
    }
    if (self.results[item.id]) {
        item.result = self.results[item.id].content;
        item.resultHeaders = self.results[item.id].headers;
    } else {
        //必须需要
        item.result = undefined;
        item.resultHeaders = undefined;
        item.resultStatusCode = undefined;
        item.resultRunTime = undefined;
    }
    if (!item.protocol) {
        item.protocol = 'HTTP';
    }
    if (!item.requestMethod) {
        item.requestMethod = 'GET';
    }
    if (!item.dataType) {
        item.dataType = 'X-WWW-FORM-URLENCODED';
    }
    if (!item.contentType) {
        item.contentType = 'JSON';
    }
    if (!item.url) {
        item.url = "";
    }

    initDefaultData(item.requestHeaders);
    initDefaultData(item.requestArgs);
    initDefaultData(item.responseArgs);
    //从地址上获取
    item.urlArgs = [];
    var match = (self.requestURL).match(/(\{[a-zA-Z0-9_]+\})/g);
    if (match != null && match.length > 0) {
        item.urlArgs = match;
        item.urlArgs = item.urlArgs.map(function (d) {
            return {name:d.substring(1, d.length-1),value:null};
        });
    }
    item.requestArgs.forEach(function (d) {
        let key = item.id + ':args:' + d.name;
        let value = localStorage.getItem(key);
        if (value != undefined && value != '') {
            d.testValue = value;
        }
    });
    item.requestHeaders.forEach(function (d) {
        let key = item.id + ':headers:' + d.name;
        let value = localStorage.getItem(key);
        if (value != undefined && value != '') {
            d.testValue = value;
        }
    });
    if (!item.resultHeaders) {
        item.resultHeaders = '';
    }
    if (!item.resultStatusCode) {
        item.resultStatusCode = 0;
    }
    if (!item.resultRunTime) {
        item.resultRunTime = 0;
    }

    self.currentApi = Object.assign({}, item, item);
    
  	
  	console.log( JSON.stringify(self.currentApi) )
}
function initDefaultData(arr) {
    arr.forEach(function (d) {
        d.children = d.children || [];
        initDefaultData(d.children)
    });
}

function focusFolderName() {
    setTimeout(function () {
        $("#folderName").focus();
    }, 100)
}
function focusModuleName() {
    setTimeout(function () {
        $("#moduleName").focus();
    }, 100)
}

/**
 * 获取数组类型
 * @param value
 * @returns {string}
 */
function getArrayValueType(value) {
    var type = 'array';
    if (value.length > 0) {
        var name = value[0].constructor.name;
        if (name == 'Array') {
            type = 'array[array]';
        } else if (name == 'Object') {
            type = 'array[object]';
        } else if (name == 'String') {
            type = 'array[string]'
        } else if (name == 'Number') {
            type = 'array[number]'
        } else if (name == 'Boolean') {
            type = 'array[boolean]'
        }
    }
    return type;
}
/**
 * 解析导入数据
 * @param data
 * @param temp
 */
function parseImportData(data, temp) {
    if (data.constructor.name == 'Array') {
        var fullObj = {};
        data.forEach(function (d) {
            if (d.constructor.name == 'Object') {
                for (var key in d) {
                    fullObj[key] = d[key];
                }
            } else if (d.constructor.name == 'Array') {
                parseImportData(d, temp);
            }
        });
        parseImportData(fullObj, temp);
    } else if (data.constructor.name == 'Object') {
        for (var key in data) {
            var v = data[key];
            if (v != undefined) {
                var t = {children: []};
                t.name = key;
                if (v.constructor.name == 'Object') {
                    t.type = 'object';
                    parseImportData(v, t.children);
                } else if (v.constructor.name == 'Array') {
                    t.type = getArrayValueType(v);
                    if (t.type == 'array[object]') {
                        parseImportData(v, t.children);
                    } else if (t.type == 'array[array]') {
                        parseImportData(v[0], t.children);
                    }
                } else if (v.constructor.name == 'String') {
                    t.type = 'string'
                } else if (v.constructor.name == 'Number') {
                    t.type = 'number'
                } else if (v.constructor.name == 'Boolean') {
                    t.type = 'boolean'
                }
                t.require = 'true';
                temp.push(t);
            }
        }
    }
}
//
function mockJSON(data){
    let rs={};
    if(!data){
        return [];
    }
     data.forEach(function(item){
         switch (item.type){
             case 'string':
                 rs[item.name]='mock';
                 break;
             case 'number':
                 rs[item.name]=parseInt(Math.random() * 100);
                 break;
             case 'boolean':
                 rs[item.name]=true;
                 break;
             case 'object':
                 if(item.children && item.children.length>0){
                     rs[item.name] = mockJSON(item.children);
                 }else{
                     rs[item.name]={};
                 }
                 break;
             case 'array':
                 rs[item.name]=[];
                 break;
             case 'array[number]':
                 rs[item.name]=[1,2,3,4,5];
                 break;
             case 'array[string]':
                 rs[item.name]=['1','2','3','4','5'];
                 break;
             case 'array[boolean]':
                 rs[item.name]=[true,false];
                 break;
             case 'array[object]':
                 if(item.children && item.children.length>0){
                     rs[item.name] = [mockJSON(item.children)];
                 }else{
                     rs[item.name]=[{}];
                 }
                 break;
             case 'array[array]':
                 rs[item.name]=[[1,2,3],[4,5,6]];
                 break;
         }
     });
    return rs;
}
//
function xhrComplete(self, e) {

    self.status.apiLoading = false;
    if (!e.detail) {
        e.detail = {};
    }
    self.currentApi.resultHeaders = e.detail.headers || '';
    self.currentApi.resultStatusCode = e.detail.status || 0;
    self.currentApi.resultRunTime = e.detail.useTime || 0;
    if (e.detail.type != 'success') {
        var error = e.detail.text;
        if (e.detail.type == 'parsererror') {
            //self.currentApi.result = '<div class="db-api-error">'+error+'</div>';
            new Result().resolve(error, self.currentApi.contentType);
            return true;
        }

        if (e.detail.status == 0) {
            if (self.extVer) {
                error = 'URL请求失败';
            } else {
                error = '请求地址错误,服务器无响应或JavaScript跨域错误';
            }
        }
        self.currentApi.result = '<div class="db-api-error">' + error + '</div>';
    }
}

function Result() {
    var jf = new JsonFormater({
        dom: '#api-result',
        imgCollapsed: '../assets/jsonformat/images/Collapsed.gif',
        imgExpanded: '../assets/jsonformat/images/Expanded.gif'
    });
    var fn = {
        JSON(data){
            try {
                gdata.currentApi.result = jf.doFormat(data);
            } catch (e) {
                gdata.currentApi.result = utils.escape(data);
            }
        },
        JSONP(data){
            gdata.currentApi.result = jf.doFormat(data);
        },
        TEXT(data){
            gdata.currentApi.result = data;
        },
        XML(data){
            if(!window.XMLDocument){
                toastr.error('该浏览器不支持XMLDocument');
                return;
            }
            if (data instanceof XMLDocument) {
                data = new XMLSerializer().serializeToString(data)
            }
            gdata.currentApi.result = utils.escape(data);
        },
        HTML(data){
            gdata.currentApi.result =utils.escape(data) ;
        }
    };

    return {
        resolve: function (data, type) {
            fn[type](data);
        }
    }
}

function getRequestArgs() {
    var args = {};
    $("#args-form input").each(function () {
        var type = this.type;
        var name = this.name;
        if (args[name]) {
            var temp = args[name];
            if (temp.constructor.name != 'Array') {
                args[name] = [];
                args[name].push(temp);
            }
            if (type == 'file') {
                args[name].push(this.files[0] || null)
            } else {
                args[name].push(this.value);
            }
        } else {
            if (type == 'file') {
                args[name] = this.files[0] || null;
            } else {
                args[name] = this.value;
            }
        }
    });
    return args;
}
function getRequestHeaders() {
    var headers = {};
    $("#header-form input").each(function () {
        headers[$(this).attr("name")] = $(this).val();
    });
    headers['Power-By'] = 'http://www.xiaoyaoji.com.cn';
    return headers;
}

function initEditor(value) {
    if(!value){
        value = "";
    }
    var width = 904;
    if (document.documentElement.clientWidth >= 1800) {
        width = 1160;
    }
    window.editor = editormd("editorBox", {
        width: width,
        height: 740,
        path: '../assets/editor.md/lib/',
        theme: "default",
        previewTheme: "default",
        editorTheme: "mdn-like",
        markdown: value,
        codeFold: true,
        //syncScrolling : false,
        toolbarIcons: function () {
            return [
                "undo", "redo", "|",
                "bold", "del", "italic", "quote", "|",
                "h1", "h2", "h3", "h4", "h5", "h6", "|",
                "list-ul", "list-ol", "hr", "|",
                "link", "reference-link", "image", "code", "preformatted-text", "code-block", "table", "datetime", "watch", "preview", "fullscreen", "clear", "search", "|",
                "help", "info"
            ]
        },
        saveHTMLToTextarea: true,    // 保存 HTML 到 Textarea
        searchReplace: true,
        //watch : false,                // 关闭实时预览
        htmlDecode: "style,script,iframe|on*",            // 开启 HTML 标签解析，为了安全性，默认不开启
        //toolbar  : false,             //关闭工具栏
        //previewCodeHighlight : false, // 关闭预览 HTML 的代码块高亮，默认开启
        emoji: false,
        taskList: false,
        tocm: true,         // Using [TOCM]
        tex: false,                   // 开启科学公式TeX语言支持，默认关闭
        flowChart: false,             // 开启流程图支持，默认关闭
        toolbarAutoFixed: false,
        sequenceDiagram: false,       // 开启时序/序列图支持，默认关闭,
        dialogLockScreen: true,   // 设置弹出层对话框不锁屏，全局通用，默认为true
        dialogShowMask: false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为true
        dialogDraggable: false,    // 设置弹出层对话框不可拖动，全局通用，默认为true
        //dialogMaskOpacity : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为0.1
        //dialogMaskBgColor : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为#fff
        //暂时关闭图片上传
        imageUpload: false,
        imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUploadURL: "./php/upload.php",
        onload: function () {
            var self = this;
            /*setTimeout(function () {
                self.gotoLine(1);
            }, 100);*/
        }
    });
}

function renderViewBox(value) {
    $('#view-box').html('');
    setTimeout(function(){
        editormd.markdownToHTML('view-box', {
            htmlDecode: "style,script,iframe",  // you can filter tags decode
            markdown: (value || ''),
            emoji: false,
            taskList: false,
            tex: false,  // 默认不解析
            flowChart: false,  // 默认不解析
            sequenceDiagram: false  // 默认不解析
        });
    },10)
}

function copyMove(data, self) {
    data.targetId = gdata.flag.moveCopyId;
    gdata.status.moveCopyModal = false;
    data.projectId = gdata.id;
    utils.post('/project/' + gdata.id + '/copymove.json', data, function () {
        toastr.success('操作成功', '', {timeOut: 2000, "positionClass": "toast-top-right"});
        reget(self);
    });
}

function reget(self) {
    if (!self) {
        self = gdata;
    }
    self.status.loading = true;
    self.error.projectNotExists = false;
    self.error.noModule = false;
    self.error.noInterface = false;
    self.currentApi = {result: null};
    utils.get('/project/' + self.$parent.projectId + '.json', {}, function (rs) {
         if (rs.code == 0 || parseInt(rs.result)==200) {
            if (!rs.data.project) {
                self.error.projectNotExists = true;
                return;
            }
            self.project = rs.data.project;
            if(self.project.environments){
                self.envs = utils.toJSON(self.project.environments);
                if(!self.envs){
                    self.envs=[];
                }
                if(!Array.isArray(self.envs)){
                    self.envs=[];
                }
                var envId = localStorage.getItem(self.id+'_env');
                if(envId){
                    let temp;
                    self.envs.forEach(function(item){
                        if(item.id == envId){
                            temp = item;
                            return true;
                        }
                    });
                    if(!temp){
                        temp = self.envs[0];
                    }

                    self.currentEnv = temp;
                }else{
                   self.currentEnv = self.envs[0];
                }
                if(!self.currentEnv){
                    self.currentEnv = {name:'环境切换',vars:[]};
                }
                if(!self.currentEnv.vars){
                    self.currentEnv.vars =[];
                }


            }else{
                self.envs=[];
                self.currentEnv = {name:'环境切换',vars:[]};
            }
            if (rs.data.modules.length > 0) {
                gdata.modules = rs.data.modules;
                gdata.currentModule = gdata.modules[0];
            }
            var isNew = (self.$route.query.n == 'y');
            if (isNew) {
                self.editing = true;
                self.showGuide = false;
                self.currentFolder = gdata.modules[0].folders[0];
                self.currentApi = self.currentFolder.children[0];
                if (!self.currentApi) {
                    self.currentApi = {};
                }
                initInterface(self, self.currentApi);
            }
           
            
        }
    }, function () {
        self.status.loading = false;
    });
}

//
function defaultView(self){
    self.showGuide = true;
}