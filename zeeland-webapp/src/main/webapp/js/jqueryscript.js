function displayBox(objectId){
    if($(objectId).is(':hidden')){
		$(objectId).slideDown('400');
    }else{
		$(objectId).slideUp('400');
	}
}
function displayContent(objectId){
    if($(objectId).is(':hidden')){
		$(objectId).slideDown('400');
                document.getElementById('showhide-text').innerHTML="Hide [-]";
    }else{
		$(objectId).slideUp('400');
                document.getElementById('showhide-text').innerHTML="Additional Company [+]";
	}
}

function disable_textbox(me) {
    var value = $("#"+me.id).val();
    //$(".input_text").attr("readonly", "readonly");
    $(".input_text").val("");
    //$("#"+me.id).removeAttr("readonly");
    $("#"+me.id).val(value);
}

// show/hide content
function displayAttribute(){
    displayBox('#add_attribute');
}

function displayContact(){
    displayBox('#add_contact');
}

function displayReadDetail(){
    displayBox('#read_detail');
}

function displayAdCategory(){
    displayBox('#ad_category');
}
function displayListCompany(){
    displayContent('#showhide-content');
}

function getChildCategory(catId){
    $.ajax({
        url:"getchildcategory.html?catId="+catId,
        success: function(data){
            var position = "#cat"+catId;
            $(position).html(data);
            selectCategory(catId);
        }
    });
}

function hideChildCategory(catId){
    $.ajax({
        url:"hidechildcategory.html?catId="+catId,
        success: function(data){
            var position = "#cat"+catId;
            $(position).html(data);
            selectCategory(catId);
        }
    });
}

function selectCategory(catId){
     $.ajax({
        url:"getfullcategory.html?catId="+catId,
        success: function(data){
            $("#txtselectedcategory").val(data);
            $("#hdselectedcategory").val(catId);
        }
    });
}

function getChildElementCategory(catId){
    if(catId >0){
        $.ajax({
            url:"getfullcategory.html?catId="+catId,
            success: function(data){
                $("#txtcategory").val(data);
            }
        });

        $.ajax({
            url:"getchildelementcategory.html?catId="+catId,
            success: function(content){
                var token = content.split("|");
                var level = parseInt(token[0]);
                if(level == 0){
                    level = 1;
                }
                var element = token[1];
                var size = $("#ad_category_list select").size();

                //remove old
                if(size > level){
                    for(i = level; i<=size;i++){
                        var pos = "#ad_category_list #catlv"+i.toString();
                        $(pos).remove();
                    }
                }

                //append new if current field has child
                if(element.trim() != ""){
                    $("#ad_category_list").append(element);
                }

                //assign current select to hidden field
                $("#hdcategoy").val(catId);
            }
        });
    }
}

function editbadword(badword){
    var spanid = "#span"+badword;
    var txtid = "#txt"+badword;
    var btnedit = "#btnedit"+badword;
    var btnsave = "#btnsave"+badword;
    $(spanid).hide();
    $(btnedit).hide();
    $(txtid).show();
    $(btnsave).show();
}

function saveNewBadWord(currentId){

    var spanid = "#span"+currentId;
    var hdid = "#hd"+currentId;
    var txtid = "#txt"+currentId;
    var btnedit = "#btnedit"+currentId;
    var btnsave = "#btnsave"+currentId;

    var oldWord = $(hdid).val();
    var newWord = $(txtid).val();

    $.ajax({
        url:"updatebadword.html?oldword="+oldWord+"&newword="+newWord,
        success: function(data){
            $(spanid).text(data);
            $(txtid).val(data);
            $(hdid).val(data);
            $(".show_updatemessage").text("Oppdatering av svindel ord var vellykket");
            $('.show_updatemessage').slideDown('400').delay('3000').slideUp('400');
        }
    });

    $(spanid).show();
    $(btnedit).show();
    $(txtid).hide();
    $(btnsave).hide();
}

function changeCategory(){
    if($("#hdcategoy").val()!="" && $("#txtcategory").val()!=""){
        $("#hdcategoy").val($("#hdselectedcategory").val());
        $("#txtcategory").val($("#txtselectedcategory").val());
    }
    displayCategoryBox();
}

function displayCategoryBox(){
	displayBox('#categorylist');
}

function displayMainMedia(){
	displayBox('.mainMediaPanel');
}

function displayUserTypeProperty(){
    var objectId = '#slcusertype';
    var displayObjectId = '#table_user_type';

    if($(objectId).val() == '2'){
        if($(displayObjectId).is(':hidden')){
            $(displayObjectId).slideDown('400')
        }
    }else{
        if(!$(displayObjectId).is(':hidden')){
            if($("#txtcompanyobjectid").val()=="" && $("#txtcompanyaccess").val()==""){
                $(displayObjectId).slideUp('400')
            }
        }
    }
}
function displayListPrintad(){
    if($("#showhide-printad").is(':hidden')){
		$("#showhide-printad").slideDown('400');
                document.getElementById('change-text').innerHTML="Hide [-]";
    }else{
		$("#showhide-printad").slideUp('400');
                document.getElementById('change-text').innerHTML="More [+]";
	}
}

function displayIFrame(){
    var objectId = '#iframeborder';
    var preLoadId = '#iframepreloader';

    if(!$(preLoadId).is(':hidden')){
		$(preLoadId).slideUp('400');
    }

    if($(objectId).is(':hidden')){
		$(objectId).slideDown('600');
    }
}

function testSwitchDropdown() {
    $("#nondisplayDropdown").removeClass("none_display")
}

$(document).ready(function(){

   var rowCount = $('#userAdsTable tr').length-1;
   $('#btnHidden').hide();
   if(rowCount > 5){
   $('#btnReadMore').click(function() {
       $('tr.detail').show();
       $('#btnHidden').show();
       $('#btnReadMore').hide();
     });
   $('#btnHidden').click(function() {
       $('tr.detail').hide();
       $('#btnReadMore').show();
       $('#btnHidden').hide();
     });
    }else{
    $('#btnReadMore').hide();
    }

     $('input').live("keyup", function(){
          if($('#txtemailbox').val().length > 0){
             $('#txtTemporaryCompanyId').attr("disabled", true) && $('#txtAdditioanalCompanyId').attr("disabled", true);;

          }
          else $('#txtTemporaryCompanyId').removeAttr("disabled") &&  $('#txtAdditioanalCompanyId').removeAttr("disabled");;

       });

      $('input').live("keyup", function(){
               if($('#txtTemporaryCompanyId').val().length > 0){
               console.log('click');
                  $('#txtemailbox').attr("disabled", true);
               }
               else $('#txtemailbox').removeAttr("disabled");
            });
      $('input').live("keyup", function(){
                     if($('#txtAdditioanalCompanyId').val().length > 0){
                        $('#txtemailbox').attr("disabled", true);
                     }
                     else $('#txtemailbox').removeAttr("disabled");
                  });


});



