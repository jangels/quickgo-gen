webpackJsonp([5],{40:function(e,t,i){var s,o,a={};s=i(41),o=i(42),e.exports=s||{},e.exports.__esModule&&(e.exports=e.exports["default"]);var r="function"==typeof e.exports?e.exports.options||(e.exports.options={}):e.exports;o&&(r.template=o),r.computed||(r.computed={}),Object.keys(a).forEach(function(e){var t=a[e];r.computed[e]=function(){return t}})},41:function(e,t,i){"use strict";function s(e){return e&&e.__esModule?e:{"default":e}}Object.defineProperty(t,"__esModule",{value:!0}),i(6);var o=i(9),a=s(o),r={project:{},loading:!0,projectName:null};t["default"]={route:{activate:function(){this.$parent.showProject=!0},deactivate:function(){this.$parent.showProject=!1},data:function(){var e=this;a["default"].get("/project/"+this.$route.params.id+"/info.json",{},function(t){e.project=t.data.project},function(){e.loading=!1}),e.$parent.projectId=this.$route.params.id,_czc.push(["_trackEvent","接口","项目设置"])}},data:function(){return r},methods:{ok:function(){var e=this.project,t=this;a["default"].post("/project/"+this.project.id+".json",this.project,function(i){t.project=e,toastr.success("修改成功")})}}}},42:function(e,t){e.exports=' <div class="form db-view-form"> <template v-if=!loading> <validator name=form> <div class=item> <div class="col-sm-2 label">项目名称</div> <div class=col-sm-10> <input type=text v-model=project.name v-validate:project-name="[\'required\']" maxlength=20 class="text invalid" placeholder=请输入项目名称> <div class=tip v-if=$form.projectName.invalid>{{$form.projectName.errors[0].message}}</div> </div> </div> <div class=item> <div class="col-sm-2 label">项目描述</div> <div class=col-sm-10> <textarea rows=10 placeholder=请输入项目描述 maxlength=300 class=text v-model=project.description>{{project.description}}</textarea> </div> </div> <div class=item> <div class="col-sm-2 label">项目公开性</div> <div class=col-sm-10> <p><input type=radio name=permission v-model=project.permission value=PRIVATE id=dvnr-private> <label for=dvnr-private>私有项目（只有加入项目后的成员才能看见）</label></p> <p><input type=radio name=permission v-model=project.permission id=dvnr-public value=PUBLIC> <label for=dvnr-public>公开项目（所有用户均能看见）</label></p> <div class=tip></div> </div> </div> <div class=item> <div class="col-sm-2 label"></div> <div class=col-sm-3> <input type=submit value=确认 v-on:click=ok class="btn btn-primary biggest"> </div> </div> </validator> </template> <template v-if=loading> <div class=spinner> <div class=rect1></div> <div class=rect2></div> <div class=rect3></div> <div class=rect4></div> <div class=rect5></div> </div> </template> </div> '}});