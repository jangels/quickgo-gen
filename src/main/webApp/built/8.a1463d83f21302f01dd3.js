webpackJsonp([8],{121:function(t,e,i){var o,r,a={};o=i(122),r=i(123),t.exports=o||{},t.exports.__esModule&&(t.exports=t.exports["default"]);var s="function"==typeof t.exports?t.exports.options||(t.exports.options={}):t.exports;r&&(s.template=r),s.computed||(s.computed={}),Object.keys(a).forEach(function(t){var e=a[t];s.computed[t]=function(){return e}})},122:function(t,e,i){"use strict";function o(t){return t&&t.__esModule?t:{"default":t}}Object.defineProperty(e,"__esModule",{value:!0}),i(6);var r=i(9),a=o(r),s={project:{},loading:!0,projectName:null};e["default"]={route:{activate:function(){this.$parent.showProject=!0},deactivate:function(){this.$parent.showProject=!1},data:function(){var t=this;a["default"].get("/project/"+this.$route.params.id+"/info.json",{},function(e){t.project=e.data.project},function(){t.loading=!1}),t.$parent.projectId=this.$route.params.id,_czc.push(["_trackEvent","接口","项目设置"])}},data:function(){return s},methods:{ok:function(){var t=this.project,e=this;delete this.project.createTime,a["default"].post("/project/up/"+this.project.id+".json",this.project,function(i){e.project=t,toastr.success("修改成功")})}}}},123:function(t,e){t.exports=' <div class="form db-view-form"> <template v-if=!loading> <validator name=form> <ul class=win-card> <li class=tit> <p><i class="icon icon-set"></i> 项目设置</p> </li> <li> <input v-model=project.name type=text v-validate:project-name="[\'required\']" maxlength=20 placeholder=项目名称: /> <span class=tip v-if=$form.projectName.invalid>{{$form.projectName.errors[0].message}}</span> </li> <li> <textarea rows=10 placeholder=请输入项目描述 maxlength=300 class=text v-model=project.description>{{project.description}}</textarea> </li> <li> <p> 项目属性： <input type=radio name=permission v-model=project.permission value=PRIVATE id=dvnr-private> <label for=dvnr-private>私有项目</label> <input type=radio name=permission v-model=project.permission id=dvnr-public value=PUBLIC> <label for=dvnr-public>公开项目</label> <input type=submit value=确认 v-on:click=ok class="btn-right btn btn-primary biggest"> </p> </li> </ul> </validator> </template> <template v-if=loading> <div class=spinner> <div class=rect1></div> <div class=rect2></div> <div class=rect3></div> <div class=rect4></div> <div class=rect5></div> </div> </template> </div> '}});