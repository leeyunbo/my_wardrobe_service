var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-file-save').on('click', function () {
            _this.file_save();
        });

        $('#btn-comment-save').on('click', function () {
            _this.save_comment();
        });

        $('#btn-likecnt-change').on('click', function () {
            _this.change_like();
        });

        $('input[name=btn-comment-delete]').on('click', function () {
            _this.delete_comment($(this).attr("id").replace('btn-comment-delete', ''));
        })

        $('#wardrobe_image_view').on('click', function () {
            _this.move_clothes();
        })

        document.getElementById("file").onchange = function () {
            if(!checkImage(this)) return;
            document.getElementById("uploadFile").value = this.value;
        };

        this.get_wardrobe();
        this.check_is_like();
    },

    //==  AJAX  ==//
    get_wardrobe : function () {
        $.ajax({
            type: 'GET',
            url: '/api/v1/member/wardrobe',
            dataType: 'json',
            success : function (data) {
                initWardrobe(data);
            }
        }).fail(function (error) {
            alert('옷장을 생성해주세요.')
            window.location.href = '/';
        });
    },

    get_comment : function () {

    },

    check_is_like : function () {
        $.ajax({
            type: 'GET',
            url: '/api/v1/Wardrobe/' + this.urlParam('id') + '/islike',
            dataType: 'json',
            success : function (data) {
                if(data.isLike === true) {
                    $('btn-likecnt-change').value="좋아요 취소";
                }
                else {
                    $('btn-likecnt-change').value="좋아요";
                }
            }
        }).fail(function (error) {
            alert('옷장을 생성해주세요.')
            window.location.href = '/';
        });
    },

    save : function () {
        var data = {
            name: $('#name').val(),
            content: $('#content').val(),
            isPublic: $('#isPublic').val()
        };

        var formData = new FormData();
        formData.append('file', $('#file')[0].files[0]);
        formData.append('wardrobeSaveRequestDto', new Blob([JSON.stringify(data)], {type: "application/json"}));

        $.ajax({
            type: 'POST',
            url: '/api/v1/wardrobes',
            data: formData,
            processData: false,
            contentType: false
        }).done(function() {
            alert('옷장을 만들었어요.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    file_save : async function () {
        var formData = new FormData();
        formData.append("file", $('#file')[0].files[0]);

        $.ajax({
            type: 'POST',
            enctype: 'multipart/form-data',
            url: '/api/v1/file',
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
        }).done(function() {
            alert('이미지를 업로드 했어요.');
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    save_comment : function () {
        var data = {
            content : $('#comment-content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/wardrobes/'+ $('#wardrobe_id').val() +'/comment',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            window.location.href = '/wardrobes/' + $('#wardrobe_id').val();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },

    delete_comment : function (id) {
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/wardrobes/'+ $('#wardrobe_id').val() +'/comment/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function() {
            window.location.href = '/wardrobes/' + $('#wardrobe_id').val();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },

    change_like : function () {
        $.ajax({
            type: 'PUT',
            url: '/api/v1/wardrobes/' + $('#wardrobe_id').val() + '/like_cnt',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function() {
            window.location.href = '/wardrobes/' + $('#wardrobe_id').val();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },

    move_clothes : function () {
        window.location.href = '/wardrobe/' + + $('#wardrobe_id').val() + '/clothes';
    },

    checkImage : function (obj) {
        var ext = obj.value.slice(obj.value.lastIndexOf(".") + 1).toLowerCase();

        if(!(ext === "gif" || ext === "jpg" || ext === "png" || ext === "jpeg")) {
            alert('이미지파일 (.jpg, .png, .gif, .jpeg) 만 업로드 가능합니다.');
            return false;
        }

        return true;
    },

    //== UTIL ==//
    initWardrobe : function (data) {
        const obj = JSON.parse(data);
        const name = obj.name;
        const imageServerPath = obj.imageServerPath;

        $('#name').text(name);
        $('#wardrobe_image_view').attr('src', imageServerPath);
    },

    urlParam : function(name){
        var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
        if (results==null){
            return null;
        }
        else{
            return results[1] || 0;
        }
    }


    //                <tbody>
    //                 <tr th:each="comment : *{comments}" >
    //                     <td th:text="${comment.memberName}"></td>
    //                     <td th:text="${comment.content}"></td>
    //                     <td th:text="${comment.getCreatedDate().getYear()} + '년 '
    //                     + ${comment.getCreatedDate().getMonthValue()} + '월 '
    //                     + ${comment.getCreatedDate().getDayOfMonth()} + '일'"></td>
    //                     <td>
    //                         <input type="button" th:if="${comment.email == session.user.email}" class="btn btn-dark small" name="btn-comment-delete" th:id="'btn-comment-delete' + ${comment.id}" value="삭제"/>
    //                     </td>
    //                 </tr>
    //                 </tbody>
};

main.init();
