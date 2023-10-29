'use client';

import {Box, Text, Image, Flex, Heading, HStack, Center, Spacer, Button} from "@chakra-ui/react";
import React from "react";
import {FaSignOutAlt} from "react-icons/fa";
import {SESSION_STORAGE_KEY} from "../util/constants";
import {useNavigate} from "react-router-dom";

export const SiteHeader: React.FC = () => {

    const navigation = useNavigate();

    const onSignOut = () => {
        sessionStorage.removeItem(SESSION_STORAGE_KEY);
        navigation('/');
    }

  return (
    <Box borderBottom="1px solid" borderColor="gray.200"
          sx={{'background': 'transparent', 'backdropFilter': 'blur(10px)', 'position': 'sticky', 'top': 0, 'z-index': '100'}}>
        <Flex>
            <Box pl={2}>
                <Image src="/health-icon.png" />
            </Box>
            <Spacer />
            <Flex pr={2} justifyItems="center" alignItems="center">
                <Button rightIcon={<FaSignOutAlt />} colorScheme='blue' variant='solid'>
                    Logout
                </Button>
            </Flex>
        </Flex>
    </Box>
  );
};
