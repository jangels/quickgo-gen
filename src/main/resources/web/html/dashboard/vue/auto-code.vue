<template>
	<div class="auto-code">
		<div class="data-body">
			<ul class="nav nav-tabs" style="height: 40px;">
				<li v-for="item in card" :class="curView == item.type || (item.type=='genAdd'&&curView=='genAdd2')  ?'active':'' ">
					<a  v-link="'/autoCode/'+item.type+'/'+projectId"  >{{item.name}}</a>
				</li>
			</ul> 
			
			<!--//业务表列表 开始-->
			<div class="table-box cardBox1 cardBox" :class="curView=='genList' ? 'on': '' ">
				<div id="searchForm" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" v-model="genTableFilter.pageNo" >
					<input id="pageSize" name="pageSize" type="hidden" v-model="genTableFilter.pageNo">
					<input id="orderBy" name="orderBy" type="hidden" v-model="genTableFilter.orderBy">
					<label>表名：</label><input  id="nameLike" name="nameLike" class="input-medium" type="text" v-model="genTableFilter.nameLike" maxlength="50">
					<label>说明：</label><input id="comments" name="comments" class="input-medium" type="text" v-model="genTableFilter.comments" maxlength="50">
					<label>父表表名：</label><input id="parentTable" name="parentTable" class="input-medium" type="text" v-model="genTableFilter.parentTable" maxlength="50"> &nbsp;
					<input id="btnSubmit" class="btn btn-primary" type="submit"  @click="getGenTableList" value="查询">
				</div>
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th class="name">表名</th>
							<th>说明</th>
							<th class=" class_name">类名</th>
							<th class="parent_table">父表</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for = "item in genTableList.list ">
							<td>
								<a  @click = "TableModifyById(item.id)"  >{{item.name}}</a>
							</td>
							<td>{{item.comments}}</td>
							<td>{{item.className}}</td>
							<td title="点击查询子表">
								<a href="javascript:" ></a>
							</td>
							<td>
								<a   @click ="TableModifyById(item.id)" >修改</a>
								<a   @click="deleteGenTableForm(item.id)">删除</a>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="pagination" v-html="genTableList.html"></div>
			</div>
			<!--业务表列表结束-->
			<!--业务表添加开始-->
			<div class="genTableAdd cardBox2 cardBox" :class="curView=='genAdd' ? 'on': '' ">
				<div id="inputForm" class="form-horizontal" novalidate="novalidate">
					<input id="id" name="id" type="hidden" >
					<br>
					<div class="control-group">
						<label class="control-label">表名:</label>
						<div class="controls">
							<div class="select2-container input-xxlarge " id="s2id_name">
								<select id="name" name="name" class="input-xxlarge select2-offscreen" tabindex="-1"   v-model="genTableFormFilter.name" >
									<option  v-for="item in genTableFormList"   :value="item.name" >{{item.nameAndComments}} </option>
 								</select>
							</div>
							<div class="form-actions">
								<input id="btnSubmit" class="btn btn-primary" type="submit" @click="genTableFormById" value="下一步">&nbsp;
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--业务表添加结束-->
 
			<!--业务表修改-->
			<div id="inputForm" class="form-horizontal genTableAdd cardBox2 cardBox" :class="curView=='genAdd2' ? 'on': '' ">
				<input id="id" name="id" type="hidden" v-model="saveGenTableFormFilter.id" >
				<legend>基本信息</legend>
				<div class="control-group">
					<label class="control-label">表名:</label>
					<div class="controls">
						<input id="name" name="name" class="required" disabled type="text" v-model="saveGenTableFormFilter.name" maxlength="200">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">说明:</label>
					<div class="controls">
						<input id="comments" name="comments" class="required valid" type="text" v-model="saveGenTableFormFilter.comments" maxlength="200">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">类名:</label>
					<div class="controls">
						<input id="className" name="className" class="required" type="text" v-model="saveGenTableFormFilter.className" maxlength="200">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">父表表名:</label>
					<div class="controls">
						<select id="parentTable" name="parentTable" class="input-xlarge select2-offscreen" tabindex="-1" v-model="saveGenTableFormFilter.parentTable" >
							<option  value="">无</option>
							<option  v-for="item in genTableFormList"   :value="item.name" >{{item.nameAndComments}} </option>
						</select>
					 
						&nbsp;当前表外键：
						<select id="parentTableFk" name="parentTableFk" class="input-xlarge select2-offscreen" tabindex="-1"   v-model="saveGenTableFormFilter.parentTableFk" > 
							<option   value="">无</option>
							<option v-for="item in genTableForm.columnList"   :value="item.name" >{{item.nameAndComments}} </option>
						</select>
						<span class="help-inline">如果有父表，请指定父表表名和外键</span>
					</div>
				</div>
				<div class="control-group hide">
					<label class="control-label">备注:</label>
					<div class="controls">
						<textarea id="remarks" name="remarks" maxlength="200" class="input-xxlarge" rows="4"  v-model="saveGenTableFormFilter.remarks" ></textarea>
					</div>
				</div>
				<legend>字段列表</legend>
				<div class="control-group">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th title="数据库字段名">列名</th>
								<th title="默认读取数据库字段备注">说明</th>
								<th title="数据库中设置的字段类型及长度">物理类型</th>
								<th title="实体对象的属性字段类型">Java类型</th>
								<th title="实体对象的属性字段（对象名.属性名|属性名2|属性名3，例如：用户user.id|name|loginName，属性名2和属性名3为Join时关联查询的字段）">Java属性名称 <i class="icon-question-sign"></i></th>
								<th title="是否是数据库主键">主键</th>
								<th title="字段是否可为空值，不可为空字段自动进行空值验证">可空</th>
								<th title="选中后该字段被加入到insert语句里">插入</th>
								<th title="选中后该字段被加入到update语句里">编辑</th>
								<th title="选中后该字段被加入到查询列表里">列表</th>
								<th title="选中后该字段被加入到查询条件里">查询</th>
								<th title="该字段为查询字段时的查询匹配放松">查询匹配方式</th>
								<th title="字段在表单中显示的类型">显示表单类型</th>
								<th title="显示表单类型设置为“下拉框、复选框、点选框”时，需设置字典的类型">字典类型</th>
								<th>排序</th>
							</tr>
						</thead>
						<tbody>

							<tr v-for="item in saveGenTableFormFilter.columnList"  >
								<td nowrap="">
									<input type="hidden" name="item.id" v-model="item.id" >
									<input type="hidden" name="item.delFlag" v-model="item.delFlag" >
									<input type="hidden" name="item.genTable.id" v-model="item.genTable.id" >
									<input type="hidden" name="item.name" v-model="item.name" >{{item.name}}
								</td>
								<td>
									<input type="text" name="item.comments" v-model="item.comments"  maxlength="200" class="required" style="width:100px;">
								</td>
								<td nowrap="">
									<input type="hidden" name="item.jdbcType" v-model="item.jdbcType"  >{{item.jdbcType}}
								</td>
								<td>
									<select name="" class="required input-mini select2-offscreen"    v-model="item.javaType"    style="width:85px;*width:75px" tabindex="-1">
										<option v-for="ik in  genTableForm.config.javaTypeList" :value="ik.value"  > {{ik.label}} </option>
 									</select>
								</td>
								<td>
									<input type="text" name="item.javaField" v-model="item.javaField" maxlength="200" class="required input-small">
								</td>
								<td>
									<input type="checkbox" v-model="item.isPk" >
								</td>
								<td>
									<input type="checkbox" v-model="item.isNull" >
								</td>
								<td>
									<input type="checkbox" v-model="item.isInsert" >
								</td>
								<td>
									<input type="checkbox" v-model="item.isEdit" >
								</td>
								<td>
									<input type="checkbox" v-model="item.isList">
								</td>
								<td>
									<input type="checkbox" v-model="item.isQuery">
								</td>
								<td>
									<select name="item.queryType" v-model="item.queryType"  class="required input-mini select2-offscreen" tabindex="-1">
										<option v-for="ik in  genTableForm.config.queryTypeList" :value="ik.value"  title=""> {{ik.label}} </option>
									</select>
								</td>
								<td>
								
									<select name="item.showType"   v-model="item.showType"   class="required select2-offscreen" style="width:100px;" tabindex="-1">

										<option v-for="ik in  genTableForm.config.showTypeList" :value="ik.value"   title=""> {{ik.label}} </option>

									</select>
								</td>
								<td>
									<input type="text" name="item.dictType" v-model="item.dictType" maxlength="200" class="input-mini">
								</td>
								<td>
									<input type="text" name="item.sort" v-model="item.sort" maxlength="200" class="required input-min digits">
								</td>
							</tr>

						

						</tbody>
					</table>
				</div>
				<div class="form-actions">
					<input id="btnSubmit" class="btn btn-primary" type="submit"  @click="saveGenTableForm"  value="保 存">&nbsp;
					<input id="btnCancel" class="btn" type="button" value="返 回" @click="backTable" >
				</div>
			</div>
			<!--业务表修改结束-->

			<!--//生成方案列表 开始-->
			<div class="table-box cardBox3 cardBox" :class="curView=='schemeList' ? 'on': '' ">
				<div id="searchForm" class="breadcrumb form-search">
 					<label>方案名称 ：</label>
 					<input id="name" v-model="genSchemeFilter.name" class="input-medium" type="text"  maxlength="50"> &nbsp;
					<input id="btnSubmit" class="btn btn-primary" type="button" @click="genSchemeListForm" value="查询">
				</div>
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>方案名称</th>
							<th>生成模块</th>
							<th>模块名</th>
							<th>功能名</th>
							<th>功能作者</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="item in genSchemeList.list"> 
							<td>
								<a  @click = "genSchemeModifyById(item.id)" >{{item.name}}</a>
							</td>
							<td>{{item.packageName}}</td>
							<td>{{item.moduleName}}</td>
							<td>{{item.functionName}}</td>
							<td>{{item.functionAuthor}}</td>
							<td>
								<a  @click = "genSchemeModifyById(item.id)" >修改</a>
								<a  @click="deleteGenSchemeForm(item.id)">删除</a>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="pagination" v-html="genSchemeList.html"></div>
			</div>
			<!--生成方案列表 结束-->

			<!--//生成方案添加 开始-->
			<div class="table-box cardBox4 cardBox" :class="curView=='schemeAdd' ? 'on': '' ">
				<div id="inputForm" class="form-horizontal">
					<input id="id" name="id" type="hidden" v-model="saveGenSchemeFilter.id">
					<input id="flag" name="flag" type="hidden" v-model="saveGenSchemeFilter.flag">
					<div class="control-group">
						<label class="control-label">方案名称:</label>
						<div class="controls">
							<input id="name" v-model="saveGenSchemeFilter.name" class="required" type="text"  maxlength="200">
							<span class="help-inline"></span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">模板分类:</label>
						<div class="controls">

							<select id="category" name="category"  v-model="saveGenSchemeFilter.category"  class="required input-xlarge select2-offscreen" tabindex="-1">
 									<option value="curd">增删改查（单表）</option>
									<option value="curd_many">增删改查（一对多）</option>
									<option value="dao">仅持久层（dao/entity/mapper）</option>
									<option value="treeTable">树结构表（一体）</option> 
									<option value="treeTableAndList">树结构表（左树右表）</option> 
 							</select>
							<span class="help-inline">
									生成结构：{包名}/{模块名}/{分层(dao,entity,service,web)}/{子模块名}/{java类}
							</span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">生成包路径:</label>
						<div class="controls">
							<input id="packageName"  v-model="saveGenSchemeFilter.packageName" class="required input-xlarge" type="text"  maxlength="500">
							<span class="help-inline">建议模块包：com.qitoon.modules</span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">生成模块名:</label>
						<div class="controls">
							<input id="moduleName"  v-model="saveGenSchemeFilter.moduleName" class="required input-xlarge" type="text"  maxlength="500">
							<span class="help-inline">可理解为子系统名，例如 sys</span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">生成子模块名:</label>
						<div class="controls">
							<input id="subModuleName" v-model="saveGenSchemeFilter.subModuleName" class="input-xlarge" type="text"   maxlength="500">
							<span class="help-inline">可选，分层下的文件夹，例如 </span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">生成功能描述:</label>
						<div class="controls">
							<input id="functionName" v-model="saveGenSchemeFilter.functionName" class="required input-xlarge" type="text"  maxlength="500">
							<span class="help-inline">将设置到类描述</span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">生成功能名:</label>
						<div class="controls">
							<input id="functionNameSimple" v-model="saveGenSchemeFilter.functionNameSimple" class="required input-xlarge" type="text"   maxlength="500">
							<span class="help-inline">用作功能提示，如：保存“某某”成功</span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">生成功能作者:</label>
						<div class="controls">
							<input id="functionAuthor" v-model="saveGenSchemeFilter.functionAuthor" class="required input-xlarge" type="text"  maxlength="500">
							<span class="help-inline">功能开发者</span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">业务表名:</label>
						<div class="controls">
							<select id="genTable.id" v-model="saveGenSchemeFilter.genTable.id" class="required input-xlarge select2-offscreen" tabindex="-1">
								<option  v-for="item in genTableFormList"   :value="item.id" >{{item.comments}} ( {{item.name}} ) </option>
							</select>
							<span class="help-inline">生成的数据表，一对多情况下请选择主表。</span>
						</div>
					</div>
					<div class="control-group hide">
						<label class="control-label">备注:</label>
						<div class="controls">
							<textarea id="remarks" v-model="saveGenSchemeFilter.remarks" maxlength="200" class="input-xxlarge" rows="4"></textarea>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">生成选项:</label>
						<div class="controls">
							<input id="replaceFile1" v-model="saveGenSchemeFilter.replaceFile" type="checkbox" >
							<label for="replaceFile1">是否替换现有文件</label>
							<input type="hidden" v-model="saveGenSchemeFilter._replaceFile" >
						</div>
					</div>
					<div class="form-actions">
						<input id="btnSubmit" class="btn btn-primary" type="button" value="保存方案" @click="saveGenSchemeForm(0)">&nbsp;
						<input id="btnSubmit" class="btn btn-warning" type="button" value="保存并生成代码" @click="saveGenSchemeForm(1)">&nbsp;
					</div>
				</div>
			</div>
			<!--生成方案添加 结束-->

		</div>
		<!--
		<div class="iframeBox">
			<iframe v-for="item in path" v-show="curView==$index" src="{{item.src}}"></iframe>
		</div>
		
		-->
	</div>
</template>

<script>
    import js from '../app/autoCode';
    export default js;
</script>