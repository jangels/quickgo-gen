<template>
	<div class="createCode">
		代码生成模板
	</div>
</template>

<script>
    import utils from '../../src/utils.js'
    var data={
    	
    };
    export default {
        data: function () {
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