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

    },

    save : function () {
        const data = {
            clothName: $('#cloth_name').val(),
            images: [
                {
                    imagePath: $('#image').val()
                }
            ],
            clothType: $('#cloth_type').val(),
            buyingWay: $('#buying_way').val(),
            buyingDate: $('#buying_date').val(),
            clothColor: $('#cloth_color').val(),
            clothBrand: $('#cloth_brand').val()
        };

        const formData = new FormData();
        formData.append('file', $('#file')[0].files[0]);
        formData.append('clothSaveRequestDto', new Blob([JSON.stringify(data)], {type: "application/json"}));

        $.ajax({
            type: 'POST',
            url: '/api/v1/wardrobes/' + $('#wardrobe_id').val() +'/clothes',
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

    change_like : function () {
        $.ajax({
            type: 'PUT',
            url: '/api/v1/clothes/' + $('#cloth_id').val() + '/like_cnt',
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
            images : [
                {
                    imagePath: $('#record-image').val()
                }
            ],
            subject : $('#record-subject').val(),
            content : $('#record-content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/clothes/' + $('#cloth_id').val() + '/records',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
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
};

main.init();