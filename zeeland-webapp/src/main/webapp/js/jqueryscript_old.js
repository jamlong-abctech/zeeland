function displayBox(objectId) {
    if ($(objectId).is(':hidden')) {
        $(objectId).slideDown('400');
    } else {
        $(objectId).slideUp('400');
    }
}

function disable_textbox(me) {
    var value = $("#" + me.id).val();
    //$(".input_text").attr("readonly", "readonly");
    $(".input_text").val("");
    //$("#"+me.id).removeAttr("readonly");
    $("#" + me.id).val(value);
}

function displayAttribute() {
    displayBox('#add_attribute_table');
}

function getChildCategory(catId) {
    $.ajax({
        url:"getchildcategory.html?catId=" + catId,
        success: function(data) {
            var position = "#cat" + catId;
            $(position).html(data);
            selectCategory(catId);
        }
    });
}

function hideChildCategory(catId) {
    $.ajax({
        url:"hidechildcategory.html?catId=" + catId,
        success: function(data) {
            var position = "#cat" + catId;
            $(position).html(data);
            selectCategory(catId);
        }
    });
}

function selectCategory(catId) {
    $.ajax({
        url:"getfullcategory.html?catId=" + catId,
        success: function(data) {
            $("#txtselectedcategory").val(data);
            $("#hdselectedcategory").val(catId);
        }
    });
}

function changeCategory() {
    if ($("#hdcategoy").val() != "" && $("#txtcategory").val() != "") {
        $("#hdcategoy").val($("#hdselectedcategory").val());
        $("#txtcategory").val($("#txtselectedcategory").val());
    }
    displayCategoryBox();
}

function displayCategoryBox() {
    displayBox('#categorylist');
}

function displayMainMedia() {
    displayBox('.mainMediaPanel');
}

function displayUserTypeProperty() {
    var objectId = '#slcusertype';
    var displayObjectId = '#table_user_type';

    if ($(objectId).val() == '2') {
        if ($(displayObjectId).is(':hidden')) {
            $(displayObjectId).slideDown('400')
        }
    } else {
        if (!$(displayObjectId).is(':hidden')) {
            if ($("#txtcompanyobjectid").val() == "" && $("#txtcompanyaccess").val() == "") {
                $(displayObjectId).slideUp('400')
            }
        }
    }
}

function displayIFrame() {
    var objectId = '#iframeborder';
    var preLoadId = '#iframepreloader';

    if (!$(preLoadId).is(':hidden')) {
        $(preLoadId).slideUp('400');
    }

    if ($(objectId).is(':hidden')) {
        $(objectId).slideDown('600');
    }
}




