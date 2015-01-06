$(document.body).ready(function () {
        if($('.show_validity').text().trim() != ""){
            $('.show_validity').slideDown('400');
        }

		if($('.show_updatemessage').text().trim() != ""){
			$('.show_updatemessage').slideDown('400').delay('3000').slideUp('400');
		}

});

