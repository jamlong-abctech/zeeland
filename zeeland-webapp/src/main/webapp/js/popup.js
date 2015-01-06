/***************************/
//@Author: Adrian "yEnS" Mato Gondelle
//@website: www.yensdesign.com
//@email: yensamg@gmail.com
//@license: Feel free to use it, but keep this credits please!                  
/***************************/

//SETTING UP OUR POPUP
//0 means disabled; 1 means enabled;
var popupStatus = 0;
var popupMode;
var rowNumber;
var categoryId;
var transactionId;

//loading popup with jQuery magic!
function loadPopup(mode, row, categoryName, transactionName, editCategoryId, editTransactionId){
    //loads popup only if it is disabled
    rowNumber = row;
    popupMode = mode;
    if(popupStatus==0){
        $("#backgroundPopup").css({
            "opacity": "0.7"
        });
        $("#backgroundPopup").fadeIn("slow");
        $("#popupContact").fadeIn("slow");
        popupStatus = 1;
        if(mode != 'new') {
            categoryId = editCategoryId;
            transactionId = editTransactionId;
            $("#oldCategoryName").html(categoryName);
            $("#oldTransactionName").html(transactionName);
            $("#editCategory").removeClass("none_display");
        } else {
            $("#oldCategoryName").html('');
            $("#oldTransactionName").html('');
            $("#editCategory").addClass("none_display");
        }
    }
}

function submitForm() {
    if(popupMode == 'new') {
        document.getElementById("savecategoryform").action = "savecategory.html";
    } else {
        document.getElementById("savecategoryform").action = "editcategory.html";
        $('#rowNumber').val(rowNumber);
        var newCategoryId = $("#hdcategoy").val();
        var newTransactionId = $("#transactionId").val();
        if(newCategoryId == null || newCategoryId == '') {
            $("#hdcategoy").val(categoryId);
        }
        if(newTransactionId == null || newTransactionId == '') {
            $("#transactionId").val(transactionId);
        }
    }
    document.getElementById("savecategoryform").submit();
}

//disabling popup with jQuery magic!
function disablePopup(){
    //disables popup only if it is enabled
    if(popupStatus==1){
        $("#backgroundPopup").fadeOut("slow");
        $("#popupContact").fadeOut("slow");
        popupStatus = 0;
    }
}

//centering popup
function centerPopup(){
    //request data for centering
    var windowWidth = document.documentElement.clientWidth;
    var windowHeight = document.documentElement.clientHeight;
    var popupHeight = $("#popupContact").height();
    var popupWidth = $("#popupContact").width();
    //centering
    $("#popupContact").css({
 
        "position": "fix",
        "top": windowHeight/2-popupHeight/2,
        "left": windowWidth/2-popupWidth/2
    });
    //only need force for IE6
    
    $("#backgroundPopup").css({
        "height": windowHeight
    });
    
}


//CONTROLLING EVENTS IN jQuery
$(document).ready(function(){
    
    //LOADING POPUP
    //Click the button event!
    $("#button").click(function(){
        //centering with css
        centerPopup();
        //load popup
        loadPopup();
    });
                
    //CLOSING POPUP
    //Click the x event!
    $("#popupContactClose").click(function(){
        disablePopup();
    });
    //Click out event!
    $("#backgroundPopup").click(function(){
        disablePopup();
    });
    //Press Escape event!
    $(document).keypress(function(e){
        if(e.keyCode==27 && popupStatus==1){
            disablePopup();
        }
    });

});