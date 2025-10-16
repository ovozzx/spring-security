String.prototype.replaceAll = function(findText, replaceText) {
    return this.split(findText).join(replaceText);
};

/*
Date.prototype.appendDays = function(amount) {
    // 하루는 1440분 1분은 60초, 1초는 1000ms
    // 하루는 1440 * 60 * 1000
    return new Date(this + (amount * 1440 * 60 * 1000));
}

Date.prototype.toFormatString = function() {
    var year = this.getFullYear()
    var month = this.getMonth() + 1;
    var date = this.getDate();
    
    return year + "-" + (month < 10 ? "0" + month : month) + "-" + (date < 10 ? "0" + date : date);
}
*/