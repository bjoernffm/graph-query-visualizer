$(function() {

    var editor = ace.edit("editor");
    editor.setTheme("ace/theme/monokai");
    editor.session.setMode("ace/mode/sql");

    editor.setValue(`PREFIX  dc: <http://purl.org/dc/elements/1.1/>
PREFIX  : <http://example.org/book/>
SELECT  $title
WHERE   { :book1  dc:title  $title}`);
    editor.clearSelection();

    var dotResultEditor = ace.edit("dotResultEditor");
    dotResultEditor.setTheme("ace/theme/monokai");
    dotResultEditor.session.setMode("ace/mode/sql");

    $('#querySubmit').click(function(e) {
        $('#querySubmit').attr("disabled", "disabled");
        $('#resultLoadingIndicator').show();
        $('#errorContainer').hide();
        $('#queryViewer').hide();
        $('#queryViewer').hide();
        e.preventDefault();

        $.post("/api/queries", editor.getValue(), function( data ) {
            dotResultEditor.setValue(data.data);
            result = Viz(data.data);
            $('#queryViewer').html(result);
            $('#queryViewer').show();
        }).fail(function(e) {
            message = e.responseJSON.message;
            message = message
                        .replace(new RegExp(" ", 'g'), "&nbsp;")
                        .replace(new RegExp("<", 'g'), "&lt;")
                        .replace(new RegExp(">", 'g'), "&gt;")
                        .replace(new RegExp("\n", 'g'), "<br />");

            $('#errorTitle').text(e.responseJSON.error);
            $('#errorMessage').html(message);
            $('#errorContainer').show();
        }).always(function() {
            $('#resultLoadingIndicator').hide();
            $('#querySubmit').removeAttr("disabled");
        });
    });
});