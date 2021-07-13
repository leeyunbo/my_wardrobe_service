var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
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
    },

    save : function () {
        var data = {
            name: $('#name').val(),
            image : {
                imagePath: $('#image').val()
            },
            content: $('#content').val(),
            isPublic: $('#isPublic').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/wardrobes',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('옷장을 만들었어요.');
            window.location.href = '/';
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
    }
};

main.init();
