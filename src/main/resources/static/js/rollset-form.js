$(document).ready(function () {
  $.getJSON("/products/allRolls", function (data) {
    $.each(data, function (i, item) {
      $(".iqdropdown-menu").append(
        '<div class="iqdropdown-menu-option" data-id="' +
          item.id +
          '">\n' +
          "\t<div>\n" +
          '\t\t<p class="iqdropdown-item">' +
          item.name +
          "</p>\n" +
          '\t\t<p class="iqdropdown-description">' +
          item.price +
          " руб.</p>\n" +
          "\t</div>\n" +
          "</div>\n"
      );
    });
    $(".iqdropdown").iqDropdown();
  });
  $("#rollsetForm").submit(function (event) {
    $(".iqdropdown-menu-option").each(function (i, obj) {
        let id=$(obj).attr('data-id');
        let quantity=$(obj).find(".counter").html();
        console.log(id);
        console.log(quantity);
        $("#inputs").append("<input hidden name=\"id\" value=\""+id+"\"/>");
        $("#inputs").append("<input hidden name=\"quantity\" value=\""+quantity+"\"/>");
    })
  })
});
