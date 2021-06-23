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
            _this.change_likecnt();
        });

        _this.change_likecnt_button();
    },

    save : function () {
        var data = {
            name: $('#name').val(),
            image: $('#image').val(),
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
            type: 'PUT',
            url: '/api/v1/wardrobes/'+ $('#wardrobe_id').val() +'/comment',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('댓글을 달았어요.');
            window.location.href = '/wardrobes/' + $('#wardrobe_id').val();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },

    change_likecnt : function () {
        var is_like_user = $('#is_like_user').val();
        $.ajax({
            type: 'PUT',
            url: '/api/v1/wardrobes/' + $('#wardrobe_id').val() + '/like_cnt',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function() {
            if(is_like_user == false) {
                alert('좋아요를 눌렀어요.');
            }
            else {
                alert('종아요를 취소했어요.');
            }
            window.location.href = '/wardrobes/' + $('#wardrobe_id').val();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    },

    change_likecnt_button : function () {
        var is_like_user = $('#is_like_user').val();

        if(is_like_user == false) {
            $('#btn-likecnt-change').innerText('좋아요');
        }
        else {
            $('#btn-likecnt-change').innerText('좋아요 취소');
        }
    }
};

main.init();