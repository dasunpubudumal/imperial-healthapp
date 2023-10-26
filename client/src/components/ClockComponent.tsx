import React, {useState} from 'react';

const ClockComponent = () => {

    let time  = new Date().toLocaleTimeString()
    const [ctime,setTime] = useState(time)
    const UpdateTime=()=>{
        time =  new Date().toLocaleTimeString()
        setTime(time)
    }
    const date = new Intl.DateTimeFormat('en-US',
        { weekday: 'short', month: 'short' }).format(new Date())
    setInterval(UpdateTime);

    return (
        <>
            {`${date} ${ctime}`}
        </>
    );
};

export default ClockComponent;