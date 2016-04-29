$(document).ready(function ( ) {
                $('.tree li').each(function () {
                    
                    if ($(this).children('ul').length > 0) {
                        $(this).addClass('parent');                       
                    }
                    
                });

                $('.tree li.parent > a').click(function ( ) {

                    $(this).parent().toggleClass('active');
                    $(this).parent().children('ul').slideToggle('slow');
                    $(this).parent().children('ul').collapsed('true');
                });

                $('#all').click(function () {
                    $('.tree li').each(function () {
                        $(this).toggleClass('active');
                        $(this).children('ul').slideToggle('slow');
                        $(this).parent().children('ul').collapsed('true');
                    });
                });

                $('.tree li').each(function () {
                    $(this).toggleClass('active');
                    $(this).children('ul').slideToggle('slow');
                    $(this).parent().children('ul').collapsed('true');
                });

            });
