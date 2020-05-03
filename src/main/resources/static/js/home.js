
$(document).ready(function () {
    $.getJSON("/design/users",function (data) {
        $.each(data,function (i,item) {
            $("#users").append(item.username);
        })
    })
});
