$('.ddw').val(0);
$('.ddw2').val(0);

$(function() {
	$('.header ul li a').hover(function() {
		$(this).stop().animate({
			'margin-top': '-30px'
		}, 200)
	}, function() {
		$(this).stop().animate({
			'margin-top': '0px'
		}, 200)
	})
	$('.head ul li a').hover(function() {
		$(this).stop().animate({
			'margin-top': '-30px'
		}, 200)
	}, function() {
		$(this).stop().animate({
			'margin-top': '0px'
		}, 200)
	});
	$('.xiangying_nav').toggle(
			function() {
				$('.head ul,.header ul').css('right', '0')
			},
			function() {
				$('.head ul,.header ul').css('right', '-67px')
			}
		)
		// 轮播
	$(".slideBox").slide({
		mainCell: ".bd ul",
		effect: "leftLoop",
		autoPlay: false,
		startFun: function(i, c) {
			del();

			switch(i) {
				case 0:
					$(".banner1 #a3").addClass('animation3');
					break;
				case 1:
					$("#a1").addClass('animation1');
					break;
				default:
					break;
			}
		}
	});
	$('.server ul li').hover(function() {
		$(this).find('.server_mask').stop().animate({
			height: '100%'
		}, 'slow')
	}, function() {
		$(this).find('.server_mask').stop().animate({
			height: '0%'
		})
	})
	$(".mr_frbox1").slide({
		titCell: "",
		mainCell: ".mr_frUl1 ul",
		autoPage: true,
		effect: "leftLoop",
		autoPlay: true,
		vis: 1
	});
	$(".mr_frbox").slide({
		titCell: "",
		mainCell: ".mr_frUl ul",
		autoPage: true,
		effect: "leftLoop",
		autoPlay: true,
		vis: 4
	});
	$('.num_box').mousewheel(function(event, delta) {
		var aaaa = $('.ddw2').val();
		if(aaaa == 1) {
			return;
		}
		qpgd(delta);
	});
	$("body").on("touchstart", function(e) {

		startX = e.originalEvent.changedTouches[0].pageX,
			startY = e.originalEvent.changedTouches[0].pageY;
	});
	$(".num_box").on("touchmove", function(e) {
		e.preventDefault();
		moveEndX = e.originalEvent.changedTouches[0].pageX,
			moveEndY = e.originalEvent.changedTouches[0].pageY,
			X = moveEndX - startX,
			Y = moveEndY - startY;
		var aaaa = $('.ddw2').val();
		if(aaaa == 1) {
			return;
		}
		if(Math.abs(Y) > Math.abs(X) && Y > 0) {
			qpgd(1);
		} else if(Math.abs(Y) > Math.abs(X) && Y < 0) {
			qpgd(-1);
		}
	});
})

// setInterval(play,2000);

var num_index = $('.num').length;
var num_default = -1;

function play() {
	qpgd(-1);
	num_default -= 1;
	if(num_default <= -num_index) {

	}
}

function qpgd(a) {
	$('.num').addClass("on") ;
	var z = $('.ddw').val();
	b = parseInt(z);
	c = $('.num').length;
	if(a < 0) {
		if(-b == c - 1) {
			return;
		}
		b -= 1;
		$('.ddw2').val(1);
	} else if(a > 0) {
		if(-b == 0) {
			return;
		}
		b += 1;
		$('.ddw2').val(1);
	}

	if(-b > 0) {
		$('.head').css('display', 'none');
		$('.header').css('display', 'block');
	} else {
		$('.head').css('display', 'block');
		$('.header').css('display', 'none');
	}

	if(-b == 1) {
		setTimeout(function() {
			$('.server ul').removeClass('server_ul').addClass('server_ul_hover');
		}, 1000)

	} else {
		$('.server ul').removeClass('server_ul_hover').addClass('server_ul');
	}

	var incr = 902;
	if(-b == 2) {
		function incre() {
			if(incr < 1532) {
				incr += 15;
				$('.case-top-right span').text(incr);
			}
		}
		setInterval(incre, 40);
		setTimeout(function() {
			$('.case ul').removeClass('case_ul').addClass('case_ul_hover');
		}, 1000)
	} else {
		incr = 800;
		$('.case ul').removeClass('case_ul_hover').addClass('case_ul');
	}

	if(-b == 3) {
		setTimeout(function() {
			$('.teachshow').removeClass('cont-right-out').addClass('cont-right-in');
		}, 500)
	} else {
		$('.teachshow').removeClass('cont-right-in').addClass('cont-right-out');
	}

	if(-b == 4) {
		setTimeout(function() {
			$('.cont-right').removeClass('cont-right-out').addClass('cont-right-in');
			$('.connect').removeClass('connect-out').addClass('connect-in')
			$('.address').removeClass('address-out').addClass('address-in')
			$('.email').removeClass('email-out').addClass('email-in')
		}, 500)
	} else {
		$('.cont-right').removeClass('cont-right-in').addClass('cont-right-out');
		$('.connect').addClass('connect-out').removeClass('connect-in')
		$('.address').addClass('address-out').removeClass('address-in')
		$('.email').addClass('email-out').removeClass('email-in')
	}

	$('.ddw').val(b);
	$('.fixed_r li').eq(-b).addClass('on').siblings('li').removeClass('on');
	var single_hh = $(window).height();
	click_hh = -single_hh * b;
	$('.num_box').animate({
		'bottom': click_hh
	}, 1000);
	setTimeout(function() {
		$('.ddw2').val(0);
	}, 1400);

	$(".head li a").removeClass("active");
	$(".header li a").removeClass("active");
	$("li.nav_num" + (-b)).find("a").addClass("active");
}
// $('.fixed_r li').eq(0).addClass('on');
$('.head li,.header li').click(function() {
	var b = $(this).attr("data-id");
	$('.ddw').val(-b);
	goPage(b);
	//$(this).siblings("li").find("a").removeClass("active").end().end().find("a").addClass("active") ;
});

function goPage(b) {
	$('.num').addClass("on") ;
	$(".head li a").removeClass("active");
	$(".header li a").removeClass("active");
	$("li.nav_num" + b).find("a").addClass("active");
	if(b > 0) {
		$('.head').css('display', 'none');
		$('.header').css('display', 'block');
	} else {
		$('.head').css('display', 'block');
		$('.header').css('display', 'none');
	}

	if(b == 1) {
		setTimeout(function() {
			$('.server ul').removeClass('server_ul').addClass('server_ul_hover');
		}, 1000)

	} else {
		$('.server ul').removeClass('server_ul_hover').addClass('server_ul');
	}

	var incr = 902;
	if(b == 2) {
		function incre() {
			if(incr < 1532) {
				incr += 15;
				$('.case-top-right span').text(incr);
			}
		}
		setInterval(incre, 40);
		setTimeout(function() {
			$('.case ul').removeClass('case_ul').addClass('case_ul_hover');
		}, 1000)
	} else {
		incr = 800;
		$('.case ul').removeClass('case_ul_hover').addClass('case_ul');
	}

	if(b == 3) {
		setTimeout(function() {
			$('.teachshow').removeClass('cont-right-out').addClass('cont-right-in');
		}, 500)
	} else {
		$('.teachshow').removeClass('cont-right-in').addClass('cont-right-out');
	}

	if(b == 4) {
		setTimeout(function() {
			$('.cont-right').removeClass('cont-right-out').addClass('cont-right-in');
			$('.connect').removeClass('connect-out').addClass('connect-in')
			$('.address').removeClass('address-out').addClass('address-in')
			$('.email').removeClass('email-out').addClass('email-in')
		}, 500)
	} else {
		$('.cont-right').removeClass('cont-right-in').addClass('cont-right-out');
		$('.connect').addClass('connect-out').removeClass('connect-in')
		$('.address').addClass('address-out').removeClass('address-in')
		$('.email').addClass('email-out').removeClass('email-in')
	}

	var single_hh = $(window).height();
	click_hh = single_hh * b;
	$('.num_box').animate({
		'bottom': click_hh
	}, 1000);
}

function quanp() {
	var single_hh = $(window).height();
	var single_ww = $(window).width();
	$('.num').height(single_hh);
	// $('.num li').width(single_ww);
}
quanp();
$(window).resize(function() {
	if(typeof indexSlides != 'undefined' && indexSlides.reformat)
		indexSlides.reformat();
	quanp();
});