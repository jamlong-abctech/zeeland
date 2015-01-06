function selectOption(optionId) {
    document.getElementById(optionId).selected = true;
}

function checkSelectedOption(optionId) {
    return document.getElementById(optionId).selected;
}

function openerRedirect(url) {
    if (opener == null) {
        window.open(url, "fparentwindow", null, true);
    } else {
        opener.location = url;
    }
}

/*function displayContact(){
 var objectId = "boxaddnewcontact";

 if(document.getElementById(objectId).style.display == "none" || document.getElementById(objectId).style.display == ""){
 document.getElementById(objectId).style.display = "table-row";
 }else{
 document.getElementById(objectId).style.display = "none";
 }
 }*/

function displayContact1() {
    var objectId = "boxaddnewcontact";
    var table = document.getElementById();

    if (document.getElementById(objectId).style.display == "none" || document.getElementById(objectId).style.display == "") {
        document.getElementById(objectId).style.display = "table-row";
    } else {
        document.getElementById(objectId).style.display = "none";
        document.getElementById(objectId).style.display = "table-row";
    }
}


function removeContact(i) {
    var objectId = "tabale_company_contact";
    document.getElementById(objectId).deleteRow(i);

}


function findValueAndSelect(dropDownId, value) {
    var dropDown = document.getElementById(dropDownId);
    for (var i = 0; i < dropDown.length; i++) {
        if (dropDown.options[i].value == value) {
            dropDown.options[i].selected = true;
        }
    }
}

function findTextAndSelect(dropDownId, text) {
    var dropDown = document.getElementById(dropDownId);
    for (var i = 0; i < dropDown.length; i++) {
        if (dropDown.options[i].text == text) {
            dropDown.options[i].selected = true;
        }
    }
}

function changeAdObjectStatus(status) {
    switch (status) {
        case "inactive":
            findTextAndSelect('slctransactionstatus', 'Stengt')
            findTextAndSelect('slcpublishingstatus', 'Inaktiv')
            findTextAndSelect('slcadstatus', 'Inaktiv')
            break;
        case "active":
            findTextAndSelect('slctransactionstatus', 'Åpent')
            findTextAndSelect('slcpublishingstatus', 'Publisert')
            findTextAndSelect('slcadstatus', 'Aktiv')
            break;
        case "deleted":
            findTextAndSelect('slctransactionstatus', 'Stengt')
            findTextAndSelect('slcpublishingstatus', 'Slettet')
            findTextAndSelect('slcadstatus', 'Slettet')
            break;
        case "sold":
            var currentDate = new Date();
            currentDate.setDate(currentDate.getDate() + 3);
            var date = currentDate.getDate();
            if (date < 10) {
                date = "0" + date;
            }
            var month = currentDate.getMonth() + 1;
            var year = currentDate.getFullYear();
            var hour = currentDate.get
            if (month < 10) {
                month = "0" + month;
            }
            var hr = currentDate.getHours();
            if (hr < 10) {
                hr = "0" + hr;
            }
            var min = currentDate.getMinutes();
            if (min < 10) {
                min = "0" + min;
            }
            findTextAndSelect('slctransactionstatus', 'Stengt')
            findTextAndSelect('slcpublishingstatus', 'Solgt')
            findTextAndSelect('slcadstatus', 'Aktiv')
            document.getElementById("publishtotime").value = (year + "-" + month + "-" + date + " " + hr + ":" + min);
            break;
        default:
            break;
    }
}

function addMoreFile() {
    //alert("Add");
    var table = document.getElementById("fileTab");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);

    var cell1 = row.insertCell(0);
    var element1 = document.createElement("input");
    element1.type = "file";
    element1.name = "adImgs";
    cell1.appendChild(element1);
}


// for showuser.jsp


// for company.jsp
function changeCompanyStatus(status) {
    switch (status) {
        case "inactive":
            selectOption("companystatusinactiv");
            break;
        case "active":
            selectOption("companystatusaktiv");
            break;
        default:
            alert("this status is not support : " + status);
            break;
    }
}

function submitWithNoValidation() {
    document.getElementById("skipValidator").value = "true";
    document.getElementById("companyForm").submit();
}

function changeCompanyAdStatus(status) {
    switch (status) {
        case "deactivate":
            selectOption("annonsestatusinactiv");
            break;
        case "activate":
            selectOption("annonsestatusaktiv");
            break;
        default:
            alert("this status is not support : " + status);
            break;
    }
}

// Create password in show user page
function randomPassword() {
    chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    pass = "";
    for (x = 0; x < 8; x++) {
        i = Math.floor(Math.random() * 62);
        pass += chars.charAt(i);
    }
    return pass;
}

//Get the password from ramdom password

function createPassword() {
    document.getElementById("txtpassbox").value = randomPassword();
}


//Save Password

function savePassword() {
    password = document.getElementById("txtpassbox").value
    document.getElementById("password").value = password;
    document.getElementById("submitpasswordform").submit();
}

function deleteContacts(contactId, companyId) {
//    alert("id : " + companyId);
    document.getElementById("hdcompanyid").value = companyId;
    document.getElementById("hdcontactid").value = contactId;
    document.getElementById("formdeletecontact").submit();
}



function createAdOwner(id) {


    if (document.getElementById("txtemailbox").value.length > 0) {

        var email = document.getElementById("txtemailbox").value;
        document.getElementById("hduserEmail").value = email;
        document.getElementById("assosicateCompanyForm").submit();

    } else if  (document.getElementById("txtTemporaryCompanyId").value.length > 0 && document.getElementById("txtAdditioanalCompanyIdList").value.length > 0) {

        var id = document.getElementById("txtTemporaryCompanyId").value;
        document.getElementById("companyOwnerId").value = id;
        var additionalid = document.getElementById("txtAdditioanalCompanyIdList").value ;
        document.getElementById("additionalcompanyId").value = additionalid ;
        document.getElementById("assosicateCompanyForm").submit();

    } else if (document.getElementById("txtTemporaryCompanyId").value.length > 0) {

        var id = document.getElementById("txtTemporaryCompanyId").value;
        document.getElementById("companyOwnerId").value = id;
        document.getElementById("assosicateCompanyForm").submit();
    } else {

        var additionalid = document.getElementById("txtAdditioanalCompanyIdList").value ;
        document.getElementById("additionalcompanyId").value = additionalid ;
        document.getElementById("assosicateCompanyForm").submit();

    }

}


function retrieveMap(id) {

    document.getElementById("mapcompanyid").value = id;
    document.getElementById("retrieveMapForm").submit();

}

function retrieveAdMap(id) {

    document.getElementById("mapadid").value = id;
    document.getElementById("retrieveAdMapForm").submit();

}

function reindexAds(id) {
    document.getElementById("img_preloadReindex").style.display = "inline";
    document.getElementById("reindexid").value = id;
    document.getElementById("reindexAds").submit();
}

function deactivateAds(id) {
    document.getElementById("img_preloadDeactive").style.display = "inline";
    document.getElementById("deactivateid").value = id;
    document.getElementById("deactivateAds").submit();
}

function setNewMainMedia(adId, mediaId) {
    document.getElementById("hdAdId").value = adId;
    document.getElementById("hdMediaId").value = mediaId;
    document.getElementById("setMainMedia").submit();
}

function deleteMedia(mediaId) {

    var r = confirm("Er du sikker på at du vill slette ?");
    if (r == true) {

        document.getElementById("mediaIdForDelete").value = mediaId;
        document.getElementById("deleteMediaAdForm").submit();

    }
}

function deleteCompanyLogo(logoId) {

    var agree = confirm("Er du sikker på at du vill slette ?");
    var deleted;

    if (agree)
        deleted = true;
    else
        deleted = false;

    if (deleted) {

        document.getElementById("hdmediaid").value = logoId;
        document.getElementById("formdeleteCompanyLogo").submit();
    }
    else
        return false;
}

function showDataNextPage() {

    page = parseInt(document.getElementById("searchCompanypage").value);
    page = page + 1;
    document.getElementById("pageSearch").value = page;
    title = document.getElementById("companytitle").value;
    document.getElementById("companyTitleSearch").value = title;
    document.getElementById("status").value = document.getElementById("filterStatus").value;
    document.getElementById("searchresultbypage").submit();

}


function showDataPrePage() {
    page = parseInt(document.getElementById("searchCompanypage").value);
    page = page - 1;
    document.getElementById("pageSearch").value = page;
    title = document.getElementById("companytitle").value;
    document.getElementById("companyTitleSearch").value = title;
    document.getElementById("searchresultbypage").submit();

}


function showDataGoToPage() {


// alert("Current Page   " + parseInt(document.getElementById("currentPage").value) )
    page = parseInt(document.getElementById("searchCompanypage").value);

    document.getElementById("pageSearch").value = page;
    title = document.getElementById("companytitle").value;
    document.getElementById("companyTitleSearch").value = title;
    document.getElementById("searchresultbypage").submit();
}

function blockUser(useremail) {
    document.getElementById("hduseremail").value = useremail;
    document.getElementById("frmblockuser").submit();
}

function compositeString(all, token) {
    if (all == "") {
        all = token;
    } else {
        all = all + "," + token;
    }
    return all;
}

function deleteFraudBadWord(badWord) {
    var agree = confirm("Er du sikker på at du vill slette?");
    var deleted = "false";

    if (agree) {
        deleted = "true";
    }
    if (deleted == "true") {
        window.location = "deletebadword.html?word=" + badWord;
    } else {
        return false;
    }
}

function editFraudBadWord(badWord) {
    window.location = "editbadword.html?word=" + badWord;
}

function fraudLogProcess() {
    var approve = "";
    var deny = "";
    var email = "";

    for (i = 0; i < document.getElementsByTagName("input").length; i++) {
        if (document.getElementsByTagName("input")[i].getAttribute('type') == "radio") {
            radio = document.getElementsByTagName("input")[i];
            if (radio.checked) {
                if (radio.value == "approve") {
                    approve = compositeString(approve, radio.name);
                } else if (radio.value == "deny") {
                    deny = compositeString(deny, radio.name);
                }
            }
        } else if (document.getElementsByTagName("input")[i].getAttribute('type') == "checkbox") {
            checkbox = document.getElementsByTagName("input")[i];
            if (checkbox.checked) {
                email = compositeString(email, checkbox.value);
            }
        }
    }

    /*
     alert("approve = "+approve);
     alert("deny = "+deny);
     alert("blockuser = "+email);
     */
    document.getElementById("hdaprrovelist").value = approve;
    document.getElementById("hddenylist").value = deny;
    document.getElementById("hdblocklist").value = email;
    document.getElementById("frmfraudadprocess").submit();
}

function goToFraudEdit() {
    window.location = "fraudedit.html";
}

function goToCategoryEdit() {
    window.location = "categoryedit.html";
}

function jumpToFraudLogPage() {
    var page = document.getElementById("txtpage").value;
    window.location = "fraudlog.html?page=" + page;
}

function jumpToFraudEditPage() {
    var page = document.getElementById("txtpage").value;
    window.location = "fraudedit.html?page=" + page;
}

function jumpToAdSearchResultPage(keyword) {
    var page = document.getElementById("txtpage").value;
    var redirect = "searchadresultbypage.html?keyword=" + keyword + "&page=" + page;
    window.location = redirect;
}

function goToAdSearchResultByFilter(keyword,status) {

var page = document.getElementById("searchpage").value ;
var redirect = "searchAdByPageByFilter.html?keyword="+keyword+"&status="+status+"&page="+page ;
window.location = redirect ;
}




function retrieveDumpObject(){

    document.getElementById("retrieveDumpObjectForm").submit();

}

function retrieveDumpCompanyObject() {

    document.getElementById("retrieveCompanyDumpObjectForm").submit();

}


function deleteUser() {

    var r = confirm("Er du sikker på at du vill slette ?");
    if (r == true) {
        document.getElementById("deleteuserform").submit();
    }


}

function performBatchForAds(action) {
    if (confirm('Are you sure you want to ' + action + ' ad(s)?')) {
        document.getElementById("batchAction").value = action;
        document.getElementById("batchdeleteadForm").submit();
    }
}

function deleteCategory(row) {

    if (confirm('Are you sure you want to delete?')) {
        $("#rowNumberForDelete").val(row);
        $("#deletecategoryform").submit();
    }
}

function clearPopUp() {
//    var level = $("category_list select").size();
//    alert(level);
    var parentDiv = document.getElementById("ad_category_list");
    var dropdown = document.getElementsByName("category_list");
    var level = dropdown.length;
    if(level > 1) {
        for (i=1;i<level;i++) {
            //alert(dropdown[i]);
            parentDiv.removeChild(dropdown[i]);
        }
    }

    resetForm($('#savecategoryform'));
}

function resetForm($form) {
    $form.find('input:text, input:password, input:file, select, textarea').val('');
    $form.find('input:radio, input:checkbox')
        .removeAttr('checked').removeAttr('selected');
}



// for showad

////////////////Auto complete for Attribute dropdown///////////////////

(function( $ ) {
    $.widget( "custom.combobox", {
    _create: function() {
    this.wrapper = $( "<span>" )
    .addClass( "custom-combobox" )
    .insertAfter( this.element );
    this.element.hide();
    this._createAutocomplete();
    this._createShowAllButton();
    },
    _createAutocomplete: function() {
    var selected = this.element.children( ":selected" ),
    value = selected.val() ? selected.text() : "velg attributt";
    this.input = $( "<input>" )
    .appendTo( this.wrapper )
    .val( value )
    .attr( "title", "" )
    .addClass( "custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left" )
    .autocomplete({
    delay: 0,
    minLength: 0,
    source: $.proxy( this, "_source" )
    })
    .tooltip({
    tooltipClass: "ui-state-highlight"
    });
    this._on( this.input, {
    autocompleteselect: function( event, ui ) {
    ui.item.option.selected = true;
    this._trigger( "select", event, {
    item: ui.item.option
    });
    generateAttribute();
    },
    autocompletechange: "_removeIfInvalid"
    });
    },
    _createShowAllButton: function() {
    var input = this.input,
    wasOpen = false;
    $( "<a>" )
    .attr( "tabIndex", -1 )
    .attr( "title", "Show All Items" )
    .tooltip()
    .appendTo( this.wrapper )
    .button({
    icons: {
    primary: "ui-icon-triangle-1-s"
    },
    text: false
    })
    .removeClass( "ui-corner-all" )
    .addClass( "custom-combobox-toggle ui-corner-right" )
    .mousedown(function() {
    wasOpen = input.autocomplete( "widget" ).is( ":visible" );
    })
    .click(function() {
    input.focus();
    // Close if already visible
    if ( wasOpen ) {
    return;
    }
    // Pass empty string as value to search for, displaying all results
    input.autocomplete( "search", "" );
    });
    },
    _source: function( request, response ) {
    var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
    response( this.element.children( "option" ).map(function() {
    var text = $( this ).text();
    if ( this.value && ( !request.term || matcher.test(text) ) )
    return {
    label: text,
    value: text,
    option: this
    };
    }) );
    },
    _removeIfInvalid: function( event, ui ) {
    // Selected an item, nothing to do
    if ( ui.item ) {
    return;
    }
    // Search for a match (case-insensitive)
    var value = this.input.val(),
    valueLowerCase = value.toLowerCase(),
    valid = false;
    this.element.children( "option" ).each(function() {
    if ( $( this ).text().toLowerCase() === valueLowerCase ) {
    this.selected = valid = true;
    return false;
    }
    });
    // Found a match, nothing to do
    if ( valid ) {
    return;
    }
    // Remove invalid value
    this.input
    .val( "" )
    .attr( "title", value + " didn't match any item" )
    .tooltip( "open" );
    this.element.val( "" );
    this._delay(function() {
    this.input.tooltip( "close" ).attr( "title", "" );
    }, 2500 );
    this.input.data( "ui-autocomplete" ).term = "";
    },
    _destroy: function() {
    this.wrapper.remove();
    this.element.show();
    }
    });
    })( jQuery );


$(function() {
  $( "#slcattribute" ).combobox();
  $( "#toggle" ).click(function() {
      $( "#slcattribute" ).toggle();
  });
});





