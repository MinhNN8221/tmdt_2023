$(document).ready(function() {
    dl_owl();
    dl_BackToTop();
    dl_tab();
    dl_view_mode();
    $(".menu-bar").click(function(e) {
        $(".menu-offcanvas").toggleClass("open");
    });
    $(".menuclose").click(function(e) {
        $(".menu-offcanvas").removeClass("open");
    });
    $('footer .footer-widget h3').click(function (e) {
        e.preventDefault();
        $(this).parent().toggleClass('active');
    });
    $('[data-toggle="tooltip"]').tooltip();   
    window.onscroll = function(){
       var navbar = $("header nav");
       var sticky = 40;
       if(window.pageYOffset >= sticky){
          navbar.addClass("sticky");
       }else{
           navbar.removeClass("sticky");
       }
    };
});
function dl_owl(){
    $(".owl-carousel:not(.not-owl)").each(function(){
        var data_xss_items = $(this).attr("data-xss-items");
        var data_xs_items = $(this).attr("data-xs-items");
        var data_sm_items = $(this).attr("data-sm-items");
        var data_md_items = $(this).attr("data-md-items");
        var data_lg_items = $(this).attr("data-lg-items");
        var data_xl_items = $(this).attr("data-xl-items");
        var margin = $(this).attr("data-margin");
        var dot = $(this).attr("data-dot");
        var nav = $(this).attr("data-nav");
        var autoplay = $(this).attr("data-autoplay");
        var timeout = $(this).attr("data-timeout");
        var loop = $(this).attr("data-lopp");
        if(typeof margin !== typeof undefined && margin !== false){}
        else{
            margin = 30;
        }
        if(typeof data_xss_items !== typeof undefined && data_xss_items !== false){}
        else{
            data_xss_items = 1;
        }
        if(typeof data_xs_items !== typeof undefined && data_xs_items !== false){}
        else{
            data_xs_items = 1;
        }
        if(typeof data_sm_items !== typeof undefined && data_sm_items !== false){}
        else{
            data_sm_items = 3;
        }
        if(typeof data_md_items !== typeof undefined && data_md_items !== false){}
        else{
            data_md_items = 3;
        }
        if(typeof data_lg_items !== typeof undefined && data_lg_items !== false){}
        else{
            data_lg_items = 4;
        }
        if(typeof data_xl_items !== typeof undefined && data_xl_items !== false){}
        else{
            data_xl_items = data_lg_items;
        }
        if(typeof dot !== typeof undefined && dot !== true){
            dot = dot;
        }else{
            dot = false;
        }
        if(typeof nav !== typeof undefined && nav !== true){
            nav = nav;
        }else{
            nav = false;
        }
        if(typeof autoplay !== typeof undefined && autoplay !== true){
            autoplay = autoplay;
        }else{
            autoplay = false;
        }
        if(typeof loop !== typeof undefined && loop !== true){
            loop = loop;
        }else{
            loop = false;
        }
        if(typeof timeout !== typeof undefined && timeout !== false){
            timeout = timeout;
        }else{
            timeout = 5000;
        }
        $(this).owlCarousel({
            loop           : loop,
            autoplay       : autoplay,
            autoplayTimeout: timeout,
            margin         : Number(margin),
            responsiveClass: true,
            dots           : dot,
            autoHeight     : false,
            nav            : nav,
            navText        : ["<i class=\'fa fa-angle-left\'></i>", "<i class=\'fa fa-angle-right\'></i>"],
            responsive     :{
                0   : {
                    items : Number(data_xss_items),
                    margin: 15
                },
                543 : {
                    items : Number(data_xs_items),
                    margin: 15
                },
                768 : {
                    items: Number(data_sm_items)
                },
                992 : {
                    items: Number(data_md_items)
                },
                1200: {
                    items: Number(data_lg_items)
                },
                1500: {
                    items: Number(data_xl_items)
                }
            }
        })
    })
}
function dl_BackToTop(){
    var scrollrigger = 100;
    $(window).on('scroll', function(){
        var scrollTop = $(window).scrollTop();
        if(scrollTop > scrollrigger){
           $(".back-to-top").addClass("show");
        }else{
           $(".back-to-top").removeClass("show");
        }
    });
    $(".back-to-top").on('click',function(e){
        e.preventDefault();
        $("html,body").animate({
            scrollTop: 0
        },700); 
    });
}

function dl_tab(){
    $(".tabs-title .tab-link").click(function(){
        $(this).parent().find(".tab-link").removeClass("active");
    });
}

function dl_view_mode(){
    $("#grid-view").click(function(){
        $(this).find(".view-mode-grid").addClass("active");
        $("#list-view").find(".view-mode-list").removeClass("active");
        $(".product-grid").removeAttr("style");
        $(".product-list").css("display","none");
    });
    $("#list-view").click(function(){
        $(this).find(".view-mode-list").addClass("active");
        $("#grid-view").find(".view-mode-grid").removeClass("active");
        $(".product-list").removeAttr("style");
        $(".product-grid").css("display","none");
    });
}
