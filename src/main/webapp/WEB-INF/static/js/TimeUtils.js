function dateFromLocalDateTime(time) {
    let dateMonth = time.monthValue < 10 ? "0" + time.monthValue : time.monthValue;
    let dayOfMonth = time.dayOfMonth < 10 ? "0" + time.dayOfMonth : time.dayOfMonth;
    return time.year + '.' +  dateMonth + '.' + dayOfMonth;
}
function dateTimeFromLocalDateTime(time) {
    let date = dateFromLocalDateTime(time);
    let hour = time.hour < 10 ? "0" + time.hour : time.hour;
    let minute = time.minute < 10 ? "0" + time.minute : time.minute;
    return date + " " + hour + ":" + minute;
}