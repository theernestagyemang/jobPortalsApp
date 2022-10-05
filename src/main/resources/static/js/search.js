/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


// This demo uses jQuery UI Autocomplete
// https://jqueryui.com/autocomplete

// Cannot style datalist elements yet, so get
// each option value and pass to jQuery UI Autocomplete
$('input[data-list]').each(function () {
    var availableTags = $('#' + $(this).attr("data-list")).find('option').map(function () {
        return this.value;
    }).get();

    $(this).autocomplete({
        source: availableTags
    }).on('focus', function () {
        $(this).autocomplete('search', ' ');
    }).on('search', function () {
        if ($(this).val() === '') {
            $(this).autocomplete('search', ' ');
        }
    });
});