$(function() {
    result = Viz("digraph { a -> b; }");
    $('#queryViewer').html(result);
});