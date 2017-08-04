/**
 * Created by boris feng on 2017/6/16.
 */

function activeMenuView(domId){
    //$('#previewMenu').removeClass('active');
    $('#' + domId).addClass('active').parent().css('display',
        'block').parent().addClass('active open');
}
