<template>
	<div class="createCode ">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>分类</th>
					<th>选择</th>
					<th>接口名称</th>
					<th>接口地址</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>gen_table_column</td>
					<td>业务表字段</td>
					<td>aaa</td>
					<td>GenTableColumn</td>
				</tr>
			</tbody>
		</table>
	</div>
</template>

<script>
	import utils from '../../src/utils.js'
	var data = {

	};
	export default {
		data: function() {
			return data
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
			$("body").removeClass('loading');
			this.$parent.showProject = true;
		},
		watch: {

		},
		methods: {

		}
	}
</script>