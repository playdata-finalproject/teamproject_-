$(function(){
    var cnt=0;

    var countup = function (){
        cnt = cnt+1;
        $(".number").text(cnt)
    }

    var Counterreset = function(){
        cnt = 0
    }
})