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
            if(!check_image(this)) return;
            document.getElementById("uploadFile").value = this.value;
        };
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

    check_image : function (obj) {
        var ext = obj.value.slice(obj.value.lastIndexOf(".") + 1).toLowerCase();

        if(!(ext === "gif" || ext === "jpg" || ext === "png" || ext === "jpeg")) {
            alert('이미지파일 (.jpg, .png, .gif, .jpeg) 만 업로드 가능합니다.');
            return false;
        }

        return true;
    }
};

main.init();
