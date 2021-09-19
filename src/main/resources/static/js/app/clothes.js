var main = {
    init : function () {
        const _this = this;
        $('#btn-cloth-save').on('click', function () {
            _this.save();
        });

        $('#btn-cloth-like-change').on('click', function () {
            _this.change_like();
        });

        $('#btn-record-save').on('click', function () {
            _this.record_save();
        });

        $('input[name=btn-record-delete]').on('click', function () {
            _this.delete_record($(this).attr("id").replace('btn-record-delete', ''))
        })

        document.getElementById("file").onchange = function () {
            if(!_this.check_image(this)) return;
            document.getElementById("uploadFile").value = this.value;
        };
    },

    save : function () {
        const data = {
            clothName: $('#cloth_name').val(),
            clothType: $('#cloth_type').val(),
            buyingWay: $('#buying_way').val(),
            buyingDate: $('#buying_date').val(),
            clothColor: $('#cloth_color').val(),
            clothBrand: $('#cloth_brand').val()
        };

        const formData = new FormData();
        formData.append('file', $('#file')[0].files[0]);
        formData.append('data', new Blob([JSON.stringify(data)], {type: "application/json"}));

        $.ajax({
            type: 'POST',
            url: '/api/v1/wardrobes/' + $('#wardrobe_id').val() +'/cloth',
            data: formData,
            processData: false,
            contentType: false
        }).done(function() {
            alert('옷장에 ' + $('#cloth_name').val() + '를 추가하였어요.');
            window.location.href = '/wardrobe/' + $('#wardrobe_id').val() + '/clothes';
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
            url: '/api/v1/posts/'+ $('#cloth_id').val() +'/comment',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            window.location.href = '/wardrobe/' + $('#wardrobe_id').val() + '/clothes';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },

    delete_comment : function (id) {
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+ $('#cloth_id').val() +'/comment/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function() {
            window.location.href = '/wardrobe/' + $('#wardrobe_id').val() + '/clothes';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },

    change_like : function () {
        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + $('#cloth_id').val() + '/like',
            dataType: 'text',
            contentType: 'application/json; charset=utf-8'
        }).done(function() {
            window.location.href = '/clothes/' + $('#cloth_id').val();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },

    record_save : function () {
        var data = {
            content : $('#record-content').val()
        };

        const formData = new FormData();
        formData.append('file', $('#file')[0].files[0]);
        formData.append('data', new Blob([JSON.stringify(data)], {type: "application/json"}));

        $.ajax({
            type: 'POST',
            url: '/api/v1/clothes/' + $('#cloth_id').val() + '/record',
            data: formData,
            processData: false,
            contentType: false
        }).done(function() {
            alert('코디 기록을 등록했어요.');
            window.location.href = '/clothes/' + $('#cloth_id').val();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    delete_record : function (id) {
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/clothes/'+ $('#cloth_id').val() +'/records/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function() {
            window.location.href = '/clothes/' + $('#cloth_id').val();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },

    check_image : function (obj) {
        const ext = obj.value.slice(obj.value.lastIndexOf(".") + 1).toLowerCase();

        if(!(ext === "gif" || ext === "jpg" || ext === "png" || ext === "jpeg")) {
            alert('이미지파일 (.jpg, .png, .gif, .jpeg) 만 업로드 가능합니다.');
            return false;
        }

        return true;
    }
};

main.init();
