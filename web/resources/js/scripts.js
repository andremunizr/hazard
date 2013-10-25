
$(document).on( 'click', '#toggle-login-button', toggleLoginBox );
$(document).on( 'click', '#titleFacadeEquip', toggleEquipBox );
$(document).on( 'click', '#menu-button', toggleOptionsBox );

function toggleLoginBox(){
    
    $('.loginBox').fadeToggle();    
    $('.focusable').first().focus();    
}

function toggleEquipBox(){
    
    $('#equipBox').slideToggle();
}

function toggleOptionsBox(){
    
    $('#options').fadeToggle();
}