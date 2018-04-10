$(function() {


    $('#querySubmit').click(function(e) {
        $.post("/api/queries", "lala", function( data ) {
            result = Viz(data);
            $('#queryViewer').html(result);
        });
    });


});