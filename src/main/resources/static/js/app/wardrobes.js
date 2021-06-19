var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
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
    }


};

main.init();