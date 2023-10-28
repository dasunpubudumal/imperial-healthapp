import React from 'react';
import {Box, Flex} from "@chakra-ui/react";

interface PageWrapperChildren {
    children: React.ReactNode
}

export const PageWrapper: React.FC<PageWrapperChildren> = ({children}) => {
    return (
        <>
            <Flex minHeight="50vh" w="100%" justifyContent="center" alignItems="center">
                <Box maxW="2400px" w="100%" p={4}>
                    {children}
                </Box>
            </Flex>
        </>
    );
};