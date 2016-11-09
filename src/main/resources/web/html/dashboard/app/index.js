import Vue from 'vue'
import Router from 'vue-router'
import RouteConfig from './route.config'
import utils from '../../src/utils'
import ProjectLeft from '../vue/project-left.vue'
import ProjectNav from '../vue/project-nav.vue'
Vue.filter( 'timestampFormat' , function(value) {
    var _d  = new Date(parseInt(value)) ,date ="";
    if(_d == "Invalid Date") {return value };
    date +=  _d.getFullYear() + "-" + (_d.getMonth()+1) + "-"+ _d.getDate()  ;
    date +=  " " ;
    date +=  _d.getHours() + ":" + _d.getMinutes() + ":" + _d.getSeconds()  ;
    return date == "Invalid Date" ? value : date;
});
Vue.use(Router);

const router = window.router = new Router({
    root:utils.config.ctx+'/dashboard',
    history:utils.config.vue.history
});
router.map(RouteConfig);
Vue.config.debug = true;
var App = Vue.extend({
    methods:{
    }
});
ProjectNav.props=['pageName','welcome','projectId','projectName'];
ProjectLeft.props=["reloadProject"];
router.start({
    created:function(){
        this.loading=false;
    },
    data:function(){
        return {
            loading:true,
            pageName:'',
            welcome:false,
            showProject:false,
            projectId:null,
            projectName:null,
            reloadProject:false
        }
    },
	components:{
		App,ProjectLeft,ProjectNav
	}
}, '#dashboard');
var evt =document.createEvent('Event');
evt.initEvent('route.click',true,false);
router.afterEach(function(transition){
    document.dispatchEvent(new CustomEvent('route.click',{detail:{path:transition.to.path}}));
});
