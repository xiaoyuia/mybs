export default  {
    scoreBind(el,bindings) {
        const typeNum = localStorage.getItem('typeNum')
        console.log("打印")
        console.log(typeNum,"typeNum");
        console.log(el,"el");
        console.log(bindings,"bindings");
        if(typeNum == 3) {
            el.style.display= 'none'
        }
    }
}