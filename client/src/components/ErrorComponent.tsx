import React from 'react';
import {Alert, AlertDescription, AlertIcon, AlertTitle} from "@chakra-ui/react";

interface ErrorComponentProps {
    message: string,
    title: string
}

const ErrorComponent: React.FC<ErrorComponentProps> = ({message, title}) => {
    return (
        <>
            <Alert status='error'>
                <AlertIcon/>
                <AlertTitle>{title}</AlertTitle>
                <AlertDescription>{message}</AlertDescription>
            </Alert>
        </>
    );
};

export default ErrorComponent;