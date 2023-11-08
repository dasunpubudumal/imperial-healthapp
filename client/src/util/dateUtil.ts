export interface DateModel {
    h: string;
    m: string;
    s: string;
}

export const extractTime = (dateString: string): DateModel => {
    const date: Date = new Date(dateString);
    const hours = date.getUTCHours();
    const minutes = date.getUTCMinutes()
    const seconds = date.getUTCSeconds();
    return {
        h: hours > 9 ? hours.toString() : '0' + hours,
        m: minutes > 9 ? minutes.toString() : '0' + minutes,
        s: seconds > 9 ? seconds.toString() : '0' + seconds
    }
}

export const concatenateDate = (date: Date, hours: string, minutes: string): Date => {
    date.setHours(parseInt(hours))
    date.setMinutes(parseInt(minutes));
    return date;
}