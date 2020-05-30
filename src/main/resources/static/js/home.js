$(document).ready(function () {
  $.getJSON("/products/allProducts", function (data) {
    $.each(data, function (i, item) {
      $("#products").append("<div class=\"card\">\n" +
          "            <img src=\"/products/getImage?id="+item.id+"\" class=\"card-img-top\" alt=\""+item.name+"\">\n" +
          "            <div class=\"card-body\">\n" +
          "                <a href=\"/home/product?id="+item.id+"\">\n" +
          "                    <h5 class=\"card-title\">"+item.name+"</h5>\n" +
          "                </a>\n" +
          "            </div>\n" +
          "        </div>");
    });
  });
});
