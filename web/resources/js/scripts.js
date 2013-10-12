
$(document).on( 'click', '#toggle-login-button', toggleLoginBox );
$(document).on( 'click', '#titleFacadeEquip', toggleEquipBox );

function toggleLoginBox(){
    
    $('.loginBox').fadeToggle();    
    $('.focusable').first().focus();    
}

function toggleEquipBox(){
    
    $('#equipBox').slideToggle();
}