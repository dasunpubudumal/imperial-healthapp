import React from 'react';
import {Alert, AlertDescription, AlertIcon, AlertTitle} from "@chakra-ui/react";
import {PageWrapper} from "./PageWrapper";

interface ErrorComponentProps {
    message: string,
    title: string
}

const ErrorComponent: React.FC<ErrorComponentProps> = ({message, title}) => {
    return (
        <>
            <PageWrapper>
                <Alert status='error'>
                    <AlertIcon />
                    <AlertTitle>{title}</AlertTitle>
                    <AlertDescription>{message}</AlertDescription>
                </Alert>
            </PageWrapper>
        </>
    );
};

export default ErrorComponent;