$(function() {
  $(".heart").on("click", function(e) {

  e.preventDefault();
  var $this = $(this);

  if ($this.attr('value') == "unlike") {
    $.ajax({
      url: "/likes/delete",
      headers: {
        'X-CSRF-TOKEN': $("*[name=_csrf]").val()
      },
      type: "POST",
      data: JSON.stringify({
        postId: $this.data('postid')
      }),
      contentType: "application/json"
    })
    .done(function() {
      $this.attr('value', 'like');
      $this.attr('class', 'heart fa-regular fa-heart');
    });
  } else {
    $.ajax({
      url: "/likes/create",
      headers: {
        'X-CSRF-TOKEN': $("*[name=_csrf]").val()
      },
      type: "POST",
      data: JSON.stringify({
        postId: $this.data('postid')
      }),
      contentType: "application/json"
    })
    .done(function() {
      $this.attr('value', 'unlike');
      $this.attr('class', 'heart fa-sharp fa-solid fa-heart');
    });
  }
});
// $(function() {
//     $("form[id^='like_form_']").on("submit", function(e) {
//       e.preventDefault(); // デフォルトのイベント(ページの遷移やデータ送信など)を無効にする

//       var heart = $(this);
//       var id = $(this).attr('id').replace(/[^0-9]/g, '');
//       var button = $(`form[id=like_form_${id}] button`);

//       $.ajax({
//         url: $(this).attr("action"), // リクエストを送信するURLを指定（action属性のurlを抽出）
//         type: "POST", // HTTPメソッドを指定（デフォルトはGET）
//         data: {
//           _csrf: $("*[name=_csrf]").val()  // CSRFトークンを送信
//         }
//       })
//       .done(function() {
//         console.log("いいね");
//         heart.attr('action', `/likes/${id}/delete`);
//         heart.attr('id', `unlike_form_${id}`);
//         button.attr('class', 'fa-sharp fa-solid fa-heart');
//       })
//       .fail(function() {
//         alert("error!");
//       })
//     });
// });
})