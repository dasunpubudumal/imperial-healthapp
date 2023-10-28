import React from 'react';
import {Box, Flex} from "@chakra-ui/react";
import {SiteHeader} from "./SiteHeader";

interface PageWrapperChildren {
    children: React.ReactNode
}

export const PageWrapper: React.FC<PageWrapperChildren> = ({children}) => {
    return (
        <>
            <SiteHeader/>
            <Flex
                minHeight="50vh"
                w="100%"
                h="100%"
                justifyContent="center" alignItems="center" alignContent="center" justifyItems="center">
                <Box maxW="4038px" w="100%" p={4}>
                    {children}
                </Box>
            </Flex>
        </>
    );
};